import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.io.*;

public class FileDownload {

	/**
	 * @param args
	 */

	public void Download(WebDriver driver) {

		System.out.println("Page Title is: "+driver.getTitle());

		driver.findElement(By.cssSelector("a")).click();

		// The name of the file to open.
		String fileName = "/home/mukuljain/Downloads/file_handle_test.dat";

		// This will reference one line at a time
		String line = null;
		String signature = null;
		
		try {
			// FileReader reads text files in the default encoding.
			
			File file = new File(fileName);
			
			System.out.println("Is File Downloaded: "+file.exists());
			
			// Wait for File to get downloaded
			while (!file.exists()) {
				/*
			    try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			}
			
			System.out.println("Is File Downloaded: "+file.exists());
			
			System.out.println("\nFile Contets Are:\n");
			
			FileReader fileReader = 
					new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				
				System.out.println(line);
				if(line.contains("Signature"))
					signature=line.substring(line.indexOf(' ')+1);
				
			}   
			
			System.out.println("Sugnature of file is: "+signature);
			// Always close files.
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");                  
			// Or we could just do this: 
			// ex.printStackTrace();
		}
		
		driver.findElement(By.id("signature")).sendKeys(signature);
		driver.findElement(By.className("submit")).click();

	}

}
