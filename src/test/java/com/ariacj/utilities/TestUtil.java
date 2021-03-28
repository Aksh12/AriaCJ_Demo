package com.ariacj.utilities;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.ariacj.base.TestBase;


public class TestUtil extends TestBase {

	public static String screenshotPath;
	public static String screenshotName;

	public static void captureScreenshot() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".png";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName));

	}

	@DataProvider(name="LocationDataInput")
	public Object[][] inputData() {
		//System.out.println("inside input data");
		List<LinkedHashMap<String, String>> d = ExcelReader.readData();
			//System.out.println("d.size : " + d.size());
		Object[][] input = new Object[d.size()][1];
		for(int i=0; i<d.size(); i++) {
			input[i][0]=d.get(i);
			//System.out.println("input : " + input[i][0] + ":" + d.get(i).values());
		}

		return input;

	}



}
