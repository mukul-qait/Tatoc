
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;


public class AdvanceHome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//WebDriver driver = new FirefoxDriver();
		String downloadDir = System.getProperty("user.home") + "//Downloads";
		System.setProperty("webdriver.chrome.driver", downloadDir+"//chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc");
		//Rest Service Example	
		//driver.get("http://10.0.1.86/tatoc/advanced/rest");
		driver.manage().timeouts().implicitlyWait(10
				, TimeUnit.SECONDS);
		System.out.println(driver.getTitle());
		//
		// Explicit Wait conditions
		//WebDriverWait wait=new WebDriverWait(driver, 10);
		AdvanceCourse(driver);

		System.out.println(driver.getTitle());
		driver.close();


	}

	public static void AdvanceCourse(WebDriver driver)
	{
		driver.findElement(By.cssSelector(".page a:last-child")).click();
		System.out.println("Page Title is: "+driver.getTitle());

		MouseHover(driver);

		QueryGate(driver);

		Video(driver);

		try {
			new RestfulService().Restful(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try
		{
			new FileDownload().Download(driver);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	
	
	public static void Video(WebDriver driver) 
	{	

		System.out.println("Page Title is: "+driver.getTitle());
		//driver.findElement(By.id("ooyalaplayer")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		// TO Let the Page LOAD
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
		
		js.executeScript("player.play();");
		
		//	THIS JAVA SCRIPT ALSO WORKS
		//js.executeScript("document.getElementsByClassName('video')[0].getElementsByTagName('object')[0].playMovie();");

		//WebDriverWait wait=new WebDriverWait(driver, 25);
		
		try {
			Thread.sleep(25000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
		driver.findElement(By.cssSelector("a")).click();

	}
	
	public static void MouseHover(WebDriver driver)
	{

		System.out.println("Page Title is: "+driver.getTitle());

		Actions actions = new Actions(driver);
		WebElement mainMenu = driver.findElement(By.className("menutitle"));

		//Could be implemented with xpath too as many parameters are missing
		//WebElement mainMenu = driver.findElement(By.xpath("html/body/div[1]/div[2]/div[2]"));

		actions.moveToElement(mainMenu);

		//alternatively canbe done by 
		//actions.build().perform();		first build action then perform or directly perform
		actions.perform();

		List <WebElement> we = driver.findElements(By.cssSelector(".menuitem"));
		we.get(3).click();

	}

	public static void QueryGate(WebDriver driver)
	{	
		String symbol,name = null,pass = null;

		System.out.println("Page Title is: "+driver.getTitle());

		symbol=driver.findElement(By.id("symboldisplay")).getText();


		try{  
			Class.forName("com.mysql.jdbc.Driver");  

			Connection con=DriverManager.getConnection(  
					"jdbc:mysql://10.0.1.86/tatoc","tatocuser","tatoc01");  


			Statement stmt=con.createStatement();  

			ResultSet rs=stmt.executeQuery("select id from identity where symbol = \'"+symbol+"\'");  

			int id = 1;
			while(rs.next())
				id=rs.getInt(1);

			rs=stmt.executeQuery("select * from credentials where id="+id);

			while(rs.next())
			{
				System.out.println("User Credentials are: "+rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
				name=rs.getString(2);
				pass=rs.getString(3);
			}

			con.close();  

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);

		}  

		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("passkey")).sendKeys(pass);
		driver.findElement(By.id("submit")).click();


	}

}
