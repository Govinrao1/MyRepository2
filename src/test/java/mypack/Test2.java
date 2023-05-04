package mypack;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.Tesseract;

public class Test2
{
	public static void main(String[] args) throws Exception
	{
		//Open browser and launch site(SWD)
		WebDriverManager.chromedriver().setup();
		ChromeOptions co=new ChromeOptions();
		co.addArguments("--disable-notifications");
		ChromeDriver driver=new ChromeDriver(co);
		driver.manage().window().maximize();
		driver.get("https://www.irctc.co.in");
		Thread.sleep(5000);
		//Close banner(SWD)
		driver.findElement(By.xpath("//button[text()='OK']")).click();
		Thread.sleep(5000);
		//Click on menu icon(SWD)
		driver.findElement(By.xpath("(//div[contains(@class,'menu_drop_button')]/a/i)[1]"))
																				.click();
		Thread.sleep(5000);
		//Click on login(SWD)
		driver.findElement(By.xpath("//button[text()='LOGIN']")).click();
		Thread.sleep(5000);
		//Locate catcha image element(SWD) and save as an image file
		try
		{
			File src=driver.findElement(By.id("nlpCaptchaImg"))
					                   .getScreenshotAs(OutputType.FILE);
			
			Tesseract t=new Tesseract();
			t.setDatapath("src\\test\\resources");//which consists of trained data                             
			String x=t.doOCR(src); //OCR on given image with the help of "eng.traineddata"
			System.out.println(x);
		}
		catch(Exception ex)
		{
			File src=driver.findElement(By.xpath("//input[@name='nlpRefCnt']/following::img[2]"))
				                                           .getScreenshotAs(OutputType.FILE);
			//Get Text in that captcha image(using Tess4j)
			//http://tessdata.projectnaptha.com/
			Tesseract t=new Tesseract();
			t.setDatapath("src\\test\\resources");//which consists of trained data                             
			String x=t.doOCR(src); //OCR on given image with the help of "eng.traineddata"
			System.out.println(x);
			//Reenter captcha image's text(SWD)
			String y=x.substring(x.length()-8);
			y=y.replaceAll(" ","");
			driver.findElement(By.name("nlpAnswer")).sendKeys(y);
			System.out.println("Executed");
		}
	}
}
