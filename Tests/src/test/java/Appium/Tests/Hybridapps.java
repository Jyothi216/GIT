package Appium.Tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Set;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Hybridapps {
	public static AndroidDriver<MobileElement> driver;
    public static String destDir;
    public static DateFormat dateFormat;
    
	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}

    
	public static void takeScreenShot() throws IOException{
		destDir= "screenshots";
		File scrFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		dateFormat= new SimpleDateFormat("dd-MMM-yyyy_hh_mm_ssaa");
		
		new File(destDir).mkdir();
		String destFile= dateFormat.format(new Date()) +".png";
		FileUtils.copyFile(scrFile, new File(destDir+"/"+destFile));
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		/*AppiumDriverLocalService service= AppiumDriverLocalService.
				buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File("C:\\Program Files (x86)\\Appium\\node.exe"))
				.withAppiumJS(new File("C:\\Program Files (x86)\\Appium\\node_modules\\appium\\bin\\appium.js"))
				.withLogFile(new File("C:\\Users\\Jyothi\\Desktop\\log.txt")));
		service.start();*/
		DesiredCapabilities capabilities= new DesiredCapabilities();
		//File app = new File("C:\\Users\\Jyothi\\Desktop\\test.apk");
		capabilities.setCapability("deviceName","ASUS");
		capabilities.setCapability("appPackage", "com.att.myWirelessTest");
		capabilities.setCapability("appActivity", "com.att.myWireless.activity.login.SplashScreenActivity");
		//capabilities.setCapability("app", app.getAbsolutePath());
		driver= new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		takeScreenShot();
		driver.findElementByXPath("//android.widget.TextView[contains(@text,'different')]").click();
		driver.findElementById("com.att.myWirelessTest:id/wirelessNumField").sendKeys("uatdemoa336@att.net");
		driver.findElementById("com.att.myWirelessTest:id/passwordField").sendKeys("test1ng");
		driver.hideKeyboard();
		takeScreenShot();
		driver.findElementById("com.att.myWirelessTest:id/loginBtn").click();
		WebDriverWait wait= new WebDriverWait(driver,50);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.att.myWirelessTest:id/alerts_counter_menu_button")));
		Thread.sleep(5000);
		/*System.out.println(driver.getContextHandles());
		Thread.sleep(5000);*/
		//driver.context("WEBVIEW_com.att.myWirelessTest");
		//driver.findElementById("com.att.myWirelessTest:id/alerts_counter_menu_button").click();
		
		Set<String> contextNames = driver.getContextHandles();
		for(String context: contextNames){
			
			System.out.println(context);
			if(context.contains("WEBVIEW")){
				System.out.println(context);
				driver.context(context);
			}
		}
		WebDriverWait wait2= new WebDriverWait(driver,50);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("//android.widget.Button[contains(@content-desc,'Manage AutoPay')]")));
		driver.findElementById("//android.widget.Button[contains(@content-desc,'Manage AutoPay')]").click();
		//System.out.println(driver.getPageSource());
		Thread.sleep(5000);

		driver.quit();

	}

}