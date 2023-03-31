import java.io.File;
import java.io.FileNotFoundException;
import java.time.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
  
public class YoutubeUploader { 
	
	//create random instance
	private static Random rand;
	
	//create driver instance
	private static WebDriver driver = new ChromeDriver();
	
    public static void main(String[] args) throws InterruptedException {  
    	
    	//get the arguments
    	String chromedriverLocation = args[0];
    	String channelID = args[1];
    	String email = args[2];
    	String password = args[3];
    	String videoTitle = args[4];
    	String descriptionLocation = args[5];
    	String videoLocation = args[6];
    	String thumbnailLocation = args[7];
    	
    	//set the driver
	    System.setProperty("webdriver.chrome.driver", chromedriverLocation); //"C:\\Users\\samlb\\Downloads\\chromedriver_win32\\chromedriver.exe"
	    String studioLink = "https://studio.youtube.com/channel/"+channelID+"/videos/upload?d=ud";
	    
	    //check if video exists
	    File videoFile = new File(videoLocation);
	    if (videoFile.exists() == false) {
	    	System.out.println("Video file is invalid. Quitting.");
	    	System.exit(0);
	    }
	    
	    //navigate to YouTube studio
	    driver.navigate().to(studioLink);
	    
	    //check if login is required
	    try {
	    	
	    	//if login is required, login
	        while(true) {
	            try {
	            	new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("identifierId"))).sendKeys(email);
	            	randomSleep();
	            	break;
	            } catch(StaleElementReferenceException e) {
	            	//selenium has a known bug where StaleElementReferenceException's can be thrown at seemingly random times (has to do with the element being dissatached from the DOM at basically unpredictable moments)
	                randomSleep();
	            } catch(ElementNotInteractableException e) {
	                try {
	                    new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']"))).sendKeys(email);
	                    randomSleep();
	                    break;
	                } catch(ElementNotInteractableException e1) {
	                    new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='identifier']"))).sendKeys(email);
	                    randomSleep();
	                    break;
	                } catch(StaleElementReferenceException e1) {
	                    randomSleep();
	                }
	            }
	        }
	        
	        //hit next
	        while(true) {
	            try {
	                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("identifierNext"))).click();
	                randomSleep();
	                break;
	            } catch(StaleElementReferenceException e) {
	                randomSleep();
	            }
	        }
	        
	        //input password
	        while(true) {
	            try {
	                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("password"))).sendKeys(password);
	                randomSleep();
	                break;
	            } catch(StaleElementReferenceException e) {
	                randomSleep();
	            } catch(ElementNotInteractableException e) {
	                try {
	                    new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='password']"))).sendKeys(password);
	                    randomSleep();
	                    break;
	                } catch(ElementNotInteractableException e1) {
	                    new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='password']"))).sendKeys(password);
	                    randomSleep();
	                    break;
	                } catch(StaleElementReferenceException e1) {
	                    randomSleep();
	                }
	            }
	        }
	        
	        //hit next
	        while(true) {
	            try {
	                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.id("passwordNext"))).click();
	                randomSleep();
	                break;
	            } catch(StaleElementReferenceException e) {
	                randomSleep();
	            }
	        }
	        
	    } catch(TimeoutException e) {
	        ;
	    }
	    
	    //extract the description
	    String description = "";
        try {
            File file = new File(descriptionLocation);
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringBuilder.append(line);
            }
            scanner.close();
            description = stringBuilder.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Description file not found. Default description is empty.");
        }
	    
        //reset studio position
	    driver.get(studioLink);
	    
	    //upload video file
	    while(true) {
	        try {
	            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']"))).sendKeys(videoLocation);
	            randomSleep();
	            break;
	        } catch(StaleElementReferenceException e) {
	            randomSleep();
	        }
	    }
	    
	     
	    //insert title and description
	    while(true) {
	        try {
	            List<WebElement> textBoxes = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("textbox")));
	            Thread.sleep(20000);
	            textBoxes.get(0).clear();
	            randomSleep();
	            textBoxes.get(0).sendKeys(videoTitle);
	            randomSleep();
	            textBoxes.get(1).clear();
	            randomSleep();
	            textBoxes.get(1).sendKeys(description);
	            randomSleep();
	            break;
	        } catch(TimeoutException e) {
	        	randomSleep();
	        }
	    }
	    
	    //upload thumbnail
	    File thumbnailFile = new File(thumbnailLocation);
	    if (thumbnailFile.exists() == false) {
	    	System.out.println("Thumbnail file not found. Using default thumbnail.");
	    } else {
		    while(true) {
		        try {
		            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(By.id("file-loader"))).sendKeys(thumbnailLocation);
		            randomSleep();
		            break;
		        } catch(StaleElementReferenceException e) {
		        	randomSleep();
		        }
		    }
	    }

	    //hit next
	    int nextCounter = 0;
	    while(nextCounter != 3) {
	        try {
	            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.id("next-button"))).click();
	            Thread.sleep(10000);
	            nextCounter++;
	        } catch(StaleElementReferenceException e) {
	        	randomSleep();
	        }
	    }
	    
	    //hit done
	    while(true) {
	        try {
	            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(By.id("done-button"))).click();
	            randomSleep();
	            break;
	        } catch(StaleElementReferenceException e) {
	        	randomSleep();
	        }
	    }
    
    }
    
    //sleep a random amount of time
	public static void randomSleep() {
        try {
            Thread.sleep((rand.nextInt((7 - 4) + 1) + 2) * 1000);
        } catch (InterruptedException e) {
            ;
        }
    }
}  