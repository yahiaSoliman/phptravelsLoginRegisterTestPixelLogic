import static org.testng.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Phptravels {
	
	ChromeDriver driver;
	JavascriptExecutor js;
	//path of the selenium chrome driver file
	String chromeDriverPath = "C:/Users/yahia/Downloads/chromedriver_win32/chromedriver.exe";
	//path of screenshots folder
	String screenShotsPath = "C:\\Users\\yahia\\eclipse-workspace\\yahia\\screenShots\\";
	//excel file path
	String excelFilePath = "C:/Users/yahia/Downloads/pixelTest.xlsx";
	//counter for generic screenshots' names
	int x = 0;
		//-------------------------------------------------------
		//----------------------------------------- open new window
		//----------------------------------------- maximize the window
		//----------------------------------------- open the website
		//----------------------------------------- BEFORE EACH TEST CASE
		//-------------------------------------------------------
		
		
		@BeforeMethod
		public void setup() {
			System.setProperty( "webdriver.chrome.driver", chromeDriverPath);
			driver = new ChromeDriver();
			js = (JavascriptExecutor) driver;
			driver.manage().window().maximize();
			driver.get("https://phptravels.net/home");
			
		}//end setup method
		
		//-------------------------------------------------------
		//------------------------------------------ taking screen shots for every failure	
		//------------------------------------------ AFTER EACH FAILED TEST CASE
		//-------------------------------------------------------
		
		
		@AfterMethod
		public void off(ITestResult result) throws IOException {
			
			//check if the test case is failed
			if(ITestResult.FAILURE == result.getStatus()) {
				
				x++;
				File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile,new File( screenShotsPath +x+".png"));
			}//end if method
				
			driver.quit();
		}//end off method
		
		//-------------------------------------------------------------------
		//-----------------------------------------------------valid and invalid register test cases
		//-------------------------------------------------------------------

		
		@Test (priority = 1, dataProvider = "validRegisterData", enabled = true)
		public void validRegister(String firstName, String lastName, String mobileNumber, String email, String password) {
			
			driver.findElement(By.xpath("(//a[@id='dropdownCurrency'])[2]")).click();
			driver.findElement(By.xpath("//a[@class='dropdown-item tr']")).click();
			
			
			driver.findElement(By.name("firstname")).sendKeys(firstName);
			driver.findElement(By.name("lastname")).sendKeys(lastName);
			driver.findElement(By.name("phone")).sendKeys(mobileNumber);
			driver.findElement(By.name("email")).sendKeys(email);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.name("confirmpassword")).sendKeys(password);
			js.executeScript("window.scrollBy(0,100)");
			driver.findElement(By.xpath("//button[@class='signupbtn btn_full btn btn-success btn-block btn-lg']")).click();
			
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

			//-my profile- label show up
			boolean actualResult = driver.findElement(By.xpath("//a[@class='nav-link go-text-right']")).isDisplayed();
			assertTrue(actualResult); 
			
		}//end method
		
		//***************************************************************************

		
		@Test (priority = 4, dataProvider = "invalidRegisterData", enabled = true)
		public void invalidRegister(String firstName, String lastName, String mobileNumber, String email, String password) {
			
			driver.findElement(By.xpath("(//a[@id='dropdownCurrency'])[2]")).click();
			driver.findElement(By.xpath("//a[@class='dropdown-item tr']")).click();
			
			
			driver.findElement(By.name("firstname")).sendKeys(firstName);
			driver.findElement(By.name("lastname")).sendKeys(lastName);
			driver.findElement(By.name("phone")).sendKeys(mobileNumber);
			driver.findElement(By.name("email")).sendKeys(email);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.name("confirmpassword")).sendKeys(password);
			js.executeScript("window.scrollBy(0,100)");
			driver.findElement(By.xpath("//button[@class='signupbtn btn_full btn btn-success btn-block btn-lg']")).click();
			
			driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

			//alert message
			boolean actualResult = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).isDisplayed();
			assertTrue(actualResult); 
			
		}//end method
		
		//-------------------------------------------------------------------
		//-----------------------------------------------------valid and invalid login test cases
		//-------------------------------------------------------------------
		
		@Test(priority = 3, dataProvider = "invalidLoginData", enabled = true)
		public void Invalidlogin(String email, String password) {

			driver.findElement(By.xpath("(//a[@id='dropdownCurrency'])[2]")).click();
			driver.findElement(By.xpath("//a[@class='dropdown-item active tr']")).click();
			
			
			driver.findElement(By.name("username")).sendKeys(email);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block loginbtn']")).click();
			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			boolean actualResult = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).isDisplayed();
			assertTrue(actualResult);
		}//end method
		
		//*********************************************************************

		
		@Test(priority = 2, dataProvider = "validLoginData", enabled = true)
		public void validlogin(String email, String password) {

			driver.findElement(By.xpath("(//a[@id='dropdownCurrency'])[2]")).click();
			driver.findElement(By.xpath("//a[@class='dropdown-item active tr']")).click();
			//https://phptravels.net/login
			
			driver.findElement(By.name("username")).sendKeys(email);
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.xpath("//button[@class='btn btn-primary btn-lg btn-block loginbtn']")).click();

			driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
			
			//my profile label show up
			boolean actualResult = driver.findElement(By.xpath("//a[@class='nav-link go-text-right']")).isDisplayed();
			assertTrue(actualResult); 
			
		}//end method

		//----------------------------------------------------------------------
		//------------------------------------------------------data providers
		//----------------------------------------------------------------------
		
		@DataProvider
		public Object[][] invalidLoginData() throws Exception {
			
			return extractFromExcelSheet("invalidLogin");
		
		}//end invalidLoginData method
		
		//*******************************************************************
		
		@DataProvider
		public Object[][] validLoginData() throws Exception {
			

			
			return extractFromExcelSheet("validLogin");
			
		}//end invalidRegisterData method
		
		//*******************************************************************
		
		@DataProvider
		public Object[][] invalidRegisterData() throws Exception {
			

			
			return extractFromExcelSheet("invalidRegister");
			
		}//end invalidRegisterData method
		
		//*******************************************************************

		
		@DataProvider
		public Object[][] validRegisterData() throws Exception {
			

			
			return extractFromExcelSheet("validRegister");
			
		}//end invalidRegisterData method
		
		//----------------------------------------------------------------------
		//------------------------------------------------------ method to extract data from excel file sheets
		//----------------------------------------------------------------------

		public Object[][] extractFromExcelSheet(String sheetName) throws Exception{
			
			File file = new File(excelFilePath);
			FileInputStream fis = new FileInputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheet(sheetName);
			
			//how many rows and columns to be copied. counted by the last filled cell
			int rows = sheet.getLastRowNum()+1; 
			int columns = sheet.getRow(0).getLastCellNum();
			
			Object data[][] = new Object[rows][columns];
			
			//copy the data from the excel sheet to a two dimensional array
			for(int i = 0; i<rows; i++) {
				for(int j = 0; j<columns; j++) {
					data[i][j] = sheet.getRow(i).getCell(j).toString();
					
				}//end inner for
			}//end outer for
			
			workbook.close();
			
			return data;
		}//end extractFromExcelSheet method

	
}//end class Phptravels
