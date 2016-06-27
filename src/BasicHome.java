import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import com.sun.corba.se.spi.orbutil.fsm.Action;


public class BasicHome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		WebDriver driver = new FirefoxDriver();
		driver.get("http://10.0.1.86/tatoc");
		driver.manage().timeouts().implicitlyWait(10
				, TimeUnit.SECONDS);
		System.out.println(driver.getTitle());

		// Explicit Wait conditions
		//WebDriverWait wait=new WebDriverWait(driver, 10);
		BasicCourse(driver);


		System.out.println(driver.getTitle());
		driver.close();


	}

	public static void BasicCourse(WebDriver driver)
	{
		driver.findElement(By.cssSelector("a")).click();
		System.out.println("Page Title is: "+driver.getTitle());

		String className="greenbox";
		greenBox(driver,className);

		BoxFrame(driver);

		DragBox(driver);

		String name = "M J";
		WindowFrame(driver,name);

		CookieHandle(driver);
	}

	public static void CookieHandle(WebDriver driver)
	{

		System.out.println("Page Title is: "+driver.getTitle());
		List <WebElement> we = driver.findElements(By.cssSelector(".page a"));
		we.get(0).click();

		String Token = driver.findElement(By.id("token")).getText();

		Token=Token.substring(7);
		System.out.println("Token is: "+Token);

		Cookie cookie = new Cookie("Token", Token);
		driver.manage().addCookie(cookie);

		System.out.println("Tokens Added in Cookie");
		we.get(1).click();
	}

	public static void WindowFrame(WebDriver driver,String name)
	{

		System.out.println("Page Title is: "+driver.getTitle());

		driver.findElement(By.cssSelector("a")).click();
		String parentWindowHandler = driver.getWindowHandle(); // Store your parent window
		String subWindowHandler = null;

		Set<String> win = driver.getWindowHandles();  // get all window handles by WindowHandles
		Iterator<String> iterator = win.iterator();
		while (iterator.hasNext()){
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler); // switch to popup window

		System.out.println(subWindowHandler);

		//driver.switchTo().window(subWindowHandler); // switch to popup window
		// perform operations on popup
		//WebElement element = driver.findElement(By.cssSelector("input#name"));

		driver.findElement(By.cssSelector("input#name")).sendKeys(name);
		driver.findElement(By.cssSelector("input#submit")).click();
		
		driver.switchTo().window(parentWindowHandler);  // switch back to parent window
		List <WebElement> we = driver.findElements(By.cssSelector(".page a"));
		we.get(1).click();

	}

	public static void DragBox (WebDriver driver)
	{ 
		System.out.println("Page Title is: "+driver.getTitle());

		WebElement element = driver.findElement(By.id("dragbox"));

		WebElement target = driver.findElement(By.id("dropbox"));

		new Actions(driver).dragAndDrop(element, target).perform();
		
		//		Actions builder = new Actions(driver);
		//
		//		Action dragAndDrop = (Action) builder.clickAndHold(element)
		//		   .release(target)
		//		   .build();
		//
		//		((Actions) dragAndDrop).perform();

		System.out.println("Box Dragged and Dropped");
		driver.findElement(By.cssSelector("a")).click();

	}


	public static void BoxFrame (WebDriver driver)
	{ 
		System.out.println("Page Title is: "+driver.getTitle());

		driver.switchTo().frame(0);
		String Box1Color = driver.findElement(By.id("answer")).getAttribute("class");
		driver.switchTo().frame(driver.findElement(By.cssSelector("#child")));
		String Box2Color = driver.findElement(By.id("answer")).getAttribute("class");
		driver.switchTo().defaultContent();

		WebElement link;

		while(!(Box1Color.equals(Box2Color)))
		{

			driver.switchTo().frame(0);
			link=  driver.findElement(By.cssSelector("a"));
			link.click();
			driver.switchTo().frame(driver.findElement(By.cssSelector("#child")));
			Box2Color = driver.findElement(By.id("answer")).getAttribute("class");
			driver.switchTo().defaultContent();

		}

		driver.switchTo().frame(0);
		driver.findElement(By.cssSelector("a:last-child")).click();



	}


	public static void greenBox(WebDriver driver, String className)
	{
		System.out.println("Page title is: " + driver.getTitle());
		WebElement element= driver.findElement(By.className(className));
		element.click();
	}

}
