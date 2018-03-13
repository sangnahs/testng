package TestCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class TestA {
	
	public static WebDriver driver;

	//method to write test result into excel file
	public void test_result(String testid,String testname,String Result,String Reason) throws IOException{
		
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
		cell1.setCellValue(testname);
		Cell cell2 = sheet.getRow(rowCount+1).getCell(2);
		cell2.setCellValue(Result);
		Cell cell3 = sheet.getRow(rowCount+1).getCell(3);
		cell3.setCellValue(Reason);
		FileOutputStream fos = new FileOutputStream("C://Users//c5253597//workspace//Automation_assignment//Excel_Files//Result.xlsx");
		wb.write(fos);
		fos.close();
	}
	
	
	//test case 1: to search "pen drive" and apply filters
	@Test(priority=1)
	public void GoToAmazon_search_Filter() throws InterruptedException, IOException{
		
		
		String testid="1";
		String testName="Verify Search result by applying filters";
		String Result;
		String Reason="";
		
		boolean result_val;
		
		//give path of price_data.xlsx file to fetch low  and high price values
		FileInputStream fis = new FileInputStream("C://Users//c5253597//workspace//Automation_assignment//Excel_Files//Price_data.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet("Sheet1");
		XSSFCell low_price =  sheet.getRow(1).getCell(0);
		XSSFCell high_price = sheet.getRow(1).getCell(1);
		DataFormatter df = new DataFormatter(); // to convert from XSSFCell to string type
		
		
		String lowPrice = df.formatCellValue(low_price);
		String highPrice = df.formatCellValue(high_price);
	
		 driver = new ChromeDriver();
		
		// navigate to amazon website
		driver.get(" https://www.amazon.in");
		driver.manage().window().maximize();
		if(driver.getTitle().equalsIgnoreCase("Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in")){
			Result ="Success";
			Assert.assertTrue(true);
		}else{
			Reason ="Error in loading amazon website";
			test_result(testid, testName, "Failed", Reason);

			Assert.assertTrue(false);

		}
		
		
		
		
		//search for pen drive
		
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Pen drive");
		driver.findElement(By.xpath("//span[@id='nav-search-submit-text']/following-sibling::input")).click();
		String item = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[contains(.,'Pen Drives')]")).getText();
		if(item.equalsIgnoreCase("Pen Drives")){
			Assert.assertTrue(true);
		}
		else{
			Reason ="Error in search for Pen Drive";
			test_result(testid, testName, "Failed", Reason);

			Assert.assertTrue(false);
		}
		//Apply filter	
		//input price range 300-1000

				WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@name='low-price']"))));

				driver.findElement(By.xpath("//input[@name='low-price']")).sendKeys(lowPrice);
				driver.findElement(By.xpath("//input[@name='high-price']")).sendKeys(highPrice);
				driver.findElement(By.xpath("//span[contains(.,'Go')]/span/input[@value='Go']")).click();
				
				//select Brand: SanDisk
				
				Thread.sleep(5000);
				
				String price = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[contains(.,'₹300 - ₹1,000')]")).getText();
				if(price.equalsIgnoreCase("₹300 - ₹1,000")){
					
					Assert.assertTrue(true);
					result_val = true;
				}
				else{
					
					result_val = false;
					Reason = "Error in Price Range";
					test_result(testid, testName, "Failed", Reason);

					Assert.assertTrue(false);

				}
				
				//select checkbox sandisk
				
						List checkBox = driver.findElements(By.xpath("//h4[contains(text(),'Brand')]/following::ul[1]/div"));
						int size_Brand = checkBox.size();
						for(int i=1;i<=size_Brand;i++){
					WebElement ele = driver.findElement(By.xpath("//h4[contains(text(),'Brand')]/following::ul[1]/div/li["+i+"]/descendant::span[3]/span"));		
					String val = ele.getText();
					if(val.equalsIgnoreCase("SanDisk") ){
						
						ele.click();
						
						
					}
						
						}
		//Select user rating 4 and up
				
			Thread.sleep(5000);
			
			String Brand = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[contains(.,'SanDisk')]")).getText();
			
			if(Brand.equalsIgnoreCase("SanDisk")){
				
				Assert.assertTrue(true);
				result_val = true;
			}
			else{
				
				result_val = false;
				Reason = "Error in selecting Brand";
				test_result(testid, testName, "Failed", Reason);

				Assert.assertTrue(false);


			}

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
		
	
		Thread.sleep(5000);
		
		String rating = driver.findElement(By.xpath("//div[@id='searchTemplate']/descendant::a[contains(.,'4 Stars & Up')]")).getText();
		
		if(rating.equalsIgnoreCase("4 Stars & Up")){
			
			Assert.assertTrue(true);
			result_val = true;

		}
		else{
			
			result_val = false;
			Reason = "Error in selecting user Rating";
			test_result(testid, testName, "Failed", Reason);

			Assert.assertTrue(false);

		}
		
		
		if(result_val){
			Result = "Success";
		}
		else{
			Result ="Failed";
		}
		//write testresult to excel file
		test_result(testid, testName, Result, Reason);

	
		
	}
	
	
	
	@Test(dependsOnMethods={"GoToAmazon_search_Filter"})
	public void Verify_Add_item() throws InterruptedException, IOException{
		
		String testid="2";
		String testName="Verify user can add item to cart";
		String Result;
		String Reason="";
		
		
	List resultList = driver.findElements(By.xpath("//div[@id='atfResults']/ul/li"));
	
	//System.out.println(resultList.size());
	// to find lowest price pen drive
	String min = driver.findElement(By.xpath("//div[@id='atfResults']/ul/li[1]/descendant::span[@class='currencyINR'][1]/parent::span")).getAttribute("innerText").trim();
	min = min.replace(",", "");
	min = min.substring(2);
	double min_1 = Double.valueOf(min);


	for(int i=1;i< resultList.size();i++){		
		
		Thread.sleep(1000);
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
Thread.sleep(5000);
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
    Thread.sleep(5000);
    
    driver.findElement(By.xpath("//input[@title='Add to Shopping Cart']")).click();
    
for(String window: windowHandles){
    	
    	if(window != currentWindowHandle){
   		driver.switchTo().window(window);
   	}
   }

String item_added = driver.findElement(By.xpath("//h1")).getText();
if(item_added.equalsIgnoreCase("Added to Cart")){
	Assert.assertTrue(true);
	Result="Success";

}
else{
	Result="Failed";
	Reason="Error in adding product to cart";
	test_result(testid, testName, Result, Reason);

	Assert.assertTrue(false);

}
//write test result to excel file
test_result(testid, testName, Result, Reason);
	
	}
	
	
	@Test(dependsOnMethods={"Verify_Add_item"})
	public void checkout_product() throws IOException{
		String testid="3";
		String testName="verify user promt to login while check out ";
		String Result;
		String Reason="";
		
		
		
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
			Assert.assertTrue(true);
			Result="Success";

		}
		else{
			
			
			Result="Failed";
			Reason="Error while checkout";
			test_result(testid, testName, Result, Reason);

			Assert.assertTrue(false);
			
		}
		test_result(testid, testName, Result, Reason);
		
		
	}
		
	

}
