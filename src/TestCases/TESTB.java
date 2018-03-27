package TestCases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import TestCases.ExcelUtils;

public class TESTB {
	
	

	public static WebDriver driver;
	String testid;
	String testName;
	String Result;
	String Reason;
		
		@BeforeClass
		public void Go_to_amazon(){
			
			System.setProperty("webdriver.chrome.driver","C://Users//c5253597//Downloads//chromedriver_win32 (6)//chromedriver.exe");
			driver = new ChromeDriver();
			
			// navigate to amazon website
			driver.get(" https://www.amazon.in");
			driver.manage().window().maximize();
			if(driver.getTitle().equalsIgnoreCase("Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in")){
				//Result ="Success";
				AssertJUnit.assertTrue(true);
			}else{
				//Reason ="Error in loading amazon website";
				//Result="Failed";
				AssertJUnit.assertTrue(false);

			}
			
		}
		
		@DataProvider
		 
	    public Object[][] Data() throws Exception{
	 
	         Object[][] testObjArray = ExcelUtils.getTableArray("C://Users//c5253597//workspace//Automation_assignment//Excel_Files//data.xlsx","Sheet1");
	 
	         return (testObjArray);
	 
			}	
		
		@Test(dataProvider="Data")
		public void GoToAmazon_search_Filter(String device,String Brand,String lowPrice,String HighPrice,String UserRating) throws InterruptedException{
			
			testid ="1";
			testName ="Search For Pendrive with filters";
			
			//Search for device Pendrive
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys(device);
			driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/following-sibling::input")).click();
			String item = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[3]")).getText();
			System.out.println(item);
			if(item.equalsIgnoreCase("Pen Drives")){
				Result = "Passed";
				Assert.assertTrue(true);
			}
			else{
				Result ="Failed";
				Reason ="Error in search for Pen Drive";
				
				Assert.assertTrue(false);
			}
			
			//Search for sandisk
			
					List checkBox = driver.findElements(By.xpath("//h4[contains(text(),'Brand')]/following::ul[1]/div"));
					int size_Brand = checkBox.size();
					
					for(int i=1;i<=size_Brand;i++){
							
							WebElement ele = driver.findElement(By.xpath("//h4[contains(text(),'Brand')]/following::ul[1]/div/li["+i+"]/descendant::span[3]/span"));		
							String val = ele.getText();
							if(val.equalsIgnoreCase(Brand) ){
					
									ele.click();
									}
					
					}
					Thread.sleep(5000);
					
					//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					
					String Brandname = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[4]")).getText();
					
					if(Brandname.equalsIgnoreCase(Brand)){
						
						Result="Passed";
						
						Assert.assertTrue(true);
					}
					else{
						Result = "Failed";
						
						Reason = "Error in selecting Brand";

						Assert.assertTrue(false);


					}
					
				/*	//input price range 300-1000

			         //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						Thread.sleep(1000);

					driver.findElement(By.xpath("//input[@name='low-price']")).sendKeys(lowPrice);
					driver.findElement(By.xpath("//input[@name='high-price']")).sendKeys(HighPrice);
					driver.findElement(By.xpath("//span[contains(.,'Go')]/span/input[@value='Go']")).click();
					
					
					//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(2000);
					
					String price = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[5]")).getText();
					if(price.equalsIgnoreCase("₹300 - ₹1,000")){
						Result = "Passed";
						Assert.assertTrue(true);
					}
					else{
						Result ="Failed";
						Reason = "Error in Price Range";

						Assert.assertTrue(false);

					}
					*/
					// Select user rating 4 and up		
					List userRating = driver.findElements(By.xpath("//h4[contains(text(),'Avg. Customer Review')]/following::ul[1]/div"));
					int size_userRating = userRating.size();
					
					for(int i=1;i<=size_userRating;i++){
						
						WebElement ele1 = driver.findElement(By.xpath("//h4[contains(text(),'Avg. Customer Review')]/following::ul[1]/div/li["+i+"]/descendant::span[2]/parent::i/parent::a"));
						String val_userRating = ele1.getText();
						if(val_userRating.equalsIgnoreCase("& Up")){
							ele1.click();
							
						}
					}
					
				
					//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					Thread.sleep(2000);
					
					String rating = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[5]")).getText();
					
					if(rating.equalsIgnoreCase(UserRating)){
						Result="Passed";
						Assert.assertTrue(true);

					}
					else{
						
						Reason = "Error in selecting user Rating";
						Result ="Failed";
						Assert.assertTrue(false);

					}
					
			
			
		}
		
		
		@Test(dependsOnMethods={"GoToAmazon_search_Filter"})
		public void Verify_Add_item() throws InterruptedException, IOException{
			
			 testid="2";
			 testName="Verify user can add item to cart";
			
			
			
		List resultList = driver.findElements(By.xpath("//div[@id='atfResults']/ul/li"));
		
		//System.out.println(resultList.size());
		// to find lowest price pen drive
		String min = driver.findElement(By.xpath("//div[@id='atfResults']/ul/li[1]/descendant::span[@class='currencyINR'][1]/parent::span")).getAttribute("innerText").trim();
		min = min.replace(",", "");
		min = min.substring(2);
		double min_1 = Double.valueOf(min);


		for(int i=1;i< resultList.size();i++){		
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//Thread.sleep(500);
			WebElement ele = driver.findElement(By.xpath("//div[@id='atfResults']/ul/li["+i+"]/descendant::span[@class='currencyINR'][1]/parent::span"));
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(ele));
			
			String val = ele.getAttribute("innerText").trim();
			//System.out.println(val.trim().replace(",", ""));
			val = val.replace(",", "");
			val = val.substring(2);
			double v1 = Double.parseDouble(val);
			//System.out.println("V1 Value: " + v1);
			
			if(v1 < min_1){
				min_1=v1;
				//System.out.println(min_1);

			}
			
		}

		

		//add selected pen drive

	String currentWindowHandle = driver.getWindowHandle();
	driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

	double min_2 = min_1 - (long)min_1;
	if(min_2 == 0){
		WebElement ele1 = driver.findElement(By.xpath("//div[@id='atfResults']/ul/li/descendant::span[@class='currencyINR'][1]/parent::span[contains(.,'"+(int)min_1+"')]"));
		//System.out.println(ele1.getText());	
		ele1.click();
	}
	else{
		WebElement ele1 = driver.findElement(By.xpath("//div[@id='atfResults']/ul/li/descendant::span[@class='currencyINR'][1]/parent::span[contains(.,'"+min_1+"')]"));
		//System.out.println(ele1.getText());	
		ele1.click();
	}
			
	    ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
	   
	    for(String window: windowHandles){
	    	
	    	if(window != currentWindowHandle){
	   		driver.switchTo().window(window);
	   	}
	   }
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    Thread.sleep(1000);
	    
	    driver.findElement(By.xpath("//input[@title='Add to Shopping Cart']")).click();
	    
	for(String window: windowHandles){
	    	
	    	if(window != currentWindowHandle){
	   		driver.switchTo().window(window);
	   	}
	   }

	String item_added = driver.findElement(By.xpath("//h1")).getText();
	if(item_added.equalsIgnoreCase("Added to Cart")){
		Result="Passed";

		Assert.assertTrue(true);

	}
	else{
		Result="Failed";
		Reason="Error in adding product to cart";

		Assert.assertTrue(false);

	}
		}
		
		@Test(dependsOnMethods={"Verify_Add_item"})
		public void checkout_product() throws IOException{
		 testid="3";
		 testName="verify user promt to login while check out ";
			
			
			
			
			String CurrentWindowHandle = driver.getWindowHandle();
			driver.findElement(By.partialLinkText("Proceed to checkout")).click();
			
			ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
			for (String window: windowHandles){
					
					if(window != CurrentWindowHandle){
						
						driver.switchTo().window(window);
					}
				
			}
			
			String login_text = driver.findElement(By.xpath("//h1")).getText();
			if(login_text.equalsIgnoreCase("Login")){
				Result="Passed";

				Assert.assertTrue(true);

			}
			else{
				
				
				Result="Failed";
				Reason="Error while checkout";

				Assert.assertTrue(false);
				
			}
			
			
		}

		@AfterMethod
		public void test_result() throws IOException{
			
			
			
			//give path of Result.xlsx file to write to publish output
			FileInputStream fis = new FileInputStream("C://Users//c5253597//workspace//Automation_assignment//Excel_Files//Result.xlsx");
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheet("Sheet1");
			int rowCount = sheet.getLastRowNum();
			Row newrow = sheet.createRow(rowCount+1);
			for(int i=0;i<4;i++){	
					newrow.createCell(i);
			}
			
			Cell cell0 = sheet.getRow(rowCount+1).getCell(0);
			cell0.setCellValue(testid);
			Cell cell1 = sheet.getRow(rowCount+1).getCell(1);
			cell1.setCellValue(testName);
			Cell cell2 = sheet.getRow(rowCount+1).getCell(2);
			cell2.setCellValue(Result);
			Cell cell3 = sheet.getRow(rowCount+1).getCell(3);
			cell3.setCellValue(Reason);
			FileOutputStream fos = new FileOutputStream("C://Users//c5253597//workspace//Automation_assignment//Excel_Files//Result.xlsx");
			wb.write(fos);
			fos.close();
		}
		
		
		@AfterClass
		public void closeBrowser(){
			driver.quit();
		}
	
}
