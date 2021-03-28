package com.ariacj.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ariacj.base.TestBase;
import com.ariacj.base.TestBase.*;
import com.ariacj.utilities.TestUtil;

public class LocationAvailableTest extends TestBase {

	public static FileInputStream fis;
	public static XSSFWorkbook wb;
	public static XSSFSheet  sheet1 ;
	public static File src;
	public static FileOutputStream fout;
	public static int count=0;
	public static boolean flag=false;
	public static String testName;


	@BeforeTest
	public void loginIn() throws IOException  {

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']"))).sendKeys(config.getProperty("username"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']"))).sendKeys(config.getProperty("password"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='submit']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='submit']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='rcc-confirm-button']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//span[@class='MuiFab-label']"))));
		driver.findElement(By.xpath("//span[@class='MuiFab-label']")).click();
		driver.findElement(By.xpath("//li[@class='MuiButtonBase-root MuiListItem-root MuiMenuItem-root MuiMenuItem-gutters MuiListItem-gutters MuiListItem-button'][1]")).click();
		driver.navigate().refresh();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='size-small-standard']"))).isDisplayed();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		src = new File("C:\\Users\\Apurva\\eclipse\\ariacj_1.0\\src\\test\\resources\\excel\\BuildigsList_1.xlsx");
		fis = new FileInputStream(src);
		wb = new XSSFWorkbook(fis);
		sheet1 = wb.getSheetAt(0);
	}


	@Test(dataProviderClass=TestUtil.class,dataProvider="LocationDataInput")
	public void LocationAvaliablityTest(Map<String, String> data) throws InterruptedException, IOException {
		if(count==0) {
			Thread.sleep(15000);
		}
	


		if(flag==true) {
			driver.findElement(By.xpath("//h5[@class='MuiTypography-root MuiTypography-h5']")).click();
			flag=false;
		}
		
		count= count+1;
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiAutocomplete-endAdornment']"))).isDisplayed();

		driver.findElement(By.xpath("//div[@class='MuiAutocomplete-endAdornment']")).click();

		testName=data.get("Building data from excel").toString();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='size-small-standard']")).sendKeys(data.get("Building data from excel").toString());
		List<WebElement> dropDown = driver.findElements(By.xpath("//li[@class='MuiAutocomplete-option']"));


		int x1 = dropDown.size();
	
		if(dropDown.size()>0) {
			if(dropDown.get(0).getText().equals(data.get("Building data from excel").toString())) {
				dropDown.get(0).click();

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='MuiTypography-root MuiLink-root MuiLink-underlineHover jss18 MuiLink-button MuiTypography-colorPrimary'][2]"))).isDisplayed();
				driver.findElement(By.xpath("//button[@class='MuiTypography-root MuiLink-root MuiLink-underlineHover jss18 MuiLink-button MuiTypography-colorPrimary'][2]")).click();

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ol[@class='MuiBreadcrumbs-ol']/preceding::input[@value=\""+data.get("Building data from excel").toString()+"\"]"))).isDisplayed();
					String currentLocation = driver.findElement(By.xpath("//ol[@class='MuiBreadcrumbs-ol']/preceding::input[@value=\""+data.get("Building data from excel").toString()+"\"]")).getAttribute("value");

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ol[@class='MuiBreadcrumbs-ol']/following::input[@value=\""+data.get("Building data from excel").toString()+"\"]"))).isDisplayed();
					String defaultLocation = driver.findElement(By.xpath("//ol[@class='MuiBreadcrumbs-ol']/following::input[@value=\""+data.get("Building data from excel").toString()+"\"]")).getAttribute("value");
					if(currentLocation.equals(defaultLocation)) {
						System.out.print(count+") CurrentLocation : " +  currentLocation);
						System.out.print(" , DefaultLocation : " + defaultLocation);
						if(currentLocation.contentEquals(defaultLocation)) {

							System.out.println(" = Pass"); 
							//System.out.println(" = Pass : if : " +  count + ", Size = " + dropDown.size());

							sheet1.getRow(count).createCell(2).setCellValue("Yes");
							sheet1.getRow(count).createCell(3).setCellValue(x1);
							sheet1.getRow(count).createCell(4).setCellValue(defaultLocation);

							Assert.assertEquals(currentLocation, defaultLocation);

						}
					}}catch(Exception e) {
						sheet1.getRow(count).createCell(2).setCellValue("Either Took time to load, current location or default location");
						sheet1.getRow(count).createCell(3).setCellValue(x1);

					}
			}

		}else {

			sheet1.getRow(count).createCell(2).setCellValue("No");
			sheet1.getRow(count).createCell(4).setCellValue(data.get("Building data from excel").toString());
			//System.out.println("No building found with Name" + " :" + data.get("Building data from excel").toString() + " = Fail : else : " + count + ", Size = " + dropDown.size());
			System.out.println(count+") No building found with Name" + " :" + data.get("Building data from excel").toString() + " = Fail ");
			flag=true;
			Assert.fail("No building available with name  : " + data.get("Building data from excel").toString());
			


		}
	
}
	

	
	@AfterTest
	public void tearDown() {
	try {
			fout=new FileOutputStream(src);
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		}
		try {
			wb.write(fout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}	

}