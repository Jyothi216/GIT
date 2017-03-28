package Appium.Tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class hybridtonativecontext {
	
	

	public static AndroidDriver<MobileElement> driver;
	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		
		/*AppiumDriverLocalService service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File("c:/Program Files/nodejs/node.exe"))
				.withAppiumJS(new File("C:/Program Files (x86)/Appium/node_modules/appium/bin/appium.js"))
				.withLogFile(new File("c:/appiumlogs/logs.txt"))); 
	
service.start();
		*/
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Android");

		capabilities.setCapability("appPackage", "com.html5test.webview");
		capabilities.setCapability("appActivity", "main.java.MainActivity");

		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

		driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
		
		driver.findElement(By.id("com.html5test.webview:id/et")).clear();
		
		driver.findElement(By.id("com.html5test.webview:id/et")).sendKeys("http://google.com");
		
		driver.findElement(By.id("com.html5test.webview:id/go")).click();
		
		Thread.sleep(3000);
		Set<String> contextNames = driver.getContextHandles();
		Thread.sleep(3000);
		for(String context: contextNames){
			
			System.out.println(context);
			if(context.contains("WEBVIEW")){
				
				driver.context(context);
			}
		}
		Thread.sleep(3000);
		//driver.context(webview);
		//driver.context((String) contextNames.toArray()[1]); 
		driver.findElement(By.id("lst-ib")).sendKeys("Inside webview !!!");
		
		
		
		
		Thread.sleep(3000);
		driver.quit();
	}



}
