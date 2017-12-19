package com;

import io.appium.java_client.TouchAction;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import listener.AppiumListener;
import model.AppiumSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.AndroidDevice;
import utils.BaseTestCase;
import utils.LogCatHelper;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.testng.Assert.assertFalse;

public class TestCoachCase extends BaseTestCase {

    WebElement element;
    List setupeliment;
    List<WebElement> elements;
    HashMap<String, By> map;
    public Logger logger = LoggerFactory.getLogger(AppTest.class);
    LogCatHelper helper;
    WebDriverWait webDriverWait;


    //电话
    @BeforeTest
    public void setUp() throws MalformedURLException, InterruptedException {
        AppiumSettings appiumSettings=new AppiumSettings();
        appiumSettings.setApkPath("C:\\Users\\Administrator\\.jenkins\\workspace\\BodyPlus_CoachClient_realese\\app\\build\\outputs\\apk");
        appiumSettings.setApk_file_name("app-necess-debug.apk");
        appiumSettings.setPlantform("Android");
        //0715f7bdaaec1938 192.168.93.101:5555
        appiumSettings.setDevice_name("0715f7bdaaec1938");
        appiumSettings.setPlatform_version("7.0");
        appiumSettings.setAppPackage("cc.coach.bodyplus");
        appiumSettings.setAppActivity(".mvp.view.login.activity.SplashActivity");

        driver = new AndroidDevice(new URL("http://127.0.0.1:4723/wd/hub"), settings(appiumSettings));
        action = new TouchAction(driver);
        driver.context("NATIVE_APP");
        setdriver(driver);
        elements = new LinkedList();
        map = new LinkedHashMap<String, By>();
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new AppiumListener(driver));
        //初始化日志记录
        helper=new LogCatHelper("cc.coach.bodyplus");
        helper.start();
    }
    @Test
    public void testLogin(){
        driver.findElement(By.id("cc.coach.bodyplus:id/tv_login")).click();
        driver.findElement(By.id("cc.coach.bodyplus:id/edit_login_phone")).sendKeys("13728963515");
        driver.findElement(By.id("cc.coach.bodyplus:id/edit_login_password")).sendKeys("fzc8802653");
        driver.findElement(By.id("cc.coach.bodyplus:id/tv_login")).click();
        String toast="聊天服务器登录失败";
        if (toastIsExist(5,toast)){
            AppiumListener.erro_list.add("Chat Server Erro");
        }
        erroCall();
    }
    @Test
    public void testClassBegin() throws InterruptedException {
        driver.findElement(By.id("cc.coach.bodyplus:id/view_tab1")).click();
        driver.findElement(By.id("cc.coach.bodyplus:id/view_choose1")).click();
        driver.findElement(By.id("cc.coach.bodyplus:id/tv_name")).click();
        if (!isElementExist("new UiSelector().text(\"13728963515\")")){
            AppiumListener.erro_list.add("add student erro");
        }
        driver.findByUiautomator_text("选择Core").click();
        waitElement(7,0,"则成").click();
        driver.findElement(By.id("cc.coach.bodyplus:id/tv_bond")).click();
        if (!toastIsExist(6,"绑定成功")){
            AppiumListener.erro_list.add("Bind Core erro");
        }
        if(isElementExist("new UiSelector().text(\"连接已断开\")")){
            AppiumListener.erro_list.add("Bind Core erro,Connection erro");
        }
        driver.findElement(By.id("cc.coach.bodyplus:id/tv_start_course")).click();
        Thread.sleep(40000);

        if (isElementExist("new UiSelector().text(\"- -\")")){
            AppiumListener.erro_list.add("No data");
        }
        driver.findElement(By.id("cc.coach.bodyplus:id/tv_finish")).click();
        driver.findElement(By.id("cc.coach.bodyplus:id/confirm")).click();
        waitElement(5,1,"cc.coach.bodyplus:id/base_title_left_imageButton").click();
        erroCall();


    }    @AfterTest
    public void tearDown() throws IOException, MessagingException {
        helper.stop();
        driver.removeApp("cc.coach.bodyplus");
        driver.quit();
//        sendMail();
    }
}
