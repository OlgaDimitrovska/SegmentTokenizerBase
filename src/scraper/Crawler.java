package scraper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Crawler {

	public static void main(String[] args) throws IOException {
	
		
		//File input = new File("C:\\Users\\olgad\\Documents\\mkb strani od 2017 tabovi\\Службен весник на Република Македонија (1).html");
		//org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			
		
			System.setProperty("webdriver.chrome.driver","D:\\2017 изданија\\chromedriver_win32\\chromedriver.exe");
		 	WebDriver driver = new ChromeDriver();
		 	
		    driver.manage().window().maximize();
		
		    String baseUrl = "C:\\Users\\olgad\\Documents\\mkb strani od 2017 tabovi\\Службен весник на Република Македонија (1).html";
		    driver.get(baseUrl);
		    driver.findElement(By.cssSelector("#ctl00_MainContent_ddlIzdanija")).sendKeys(Keys.CONTROL +"t");;
		    
		//org.jsoup.nodes.Document doc = Jsoup.connect("file:///C:/Users/olgad/Documents/mkb%20strani%20od%202017%20tabovi/%D0%A1%D0%BB%D1%83%D0%B6%D0%B1%D0%B5%D0%BD%20%D0%B2%D0%B5%D1%81%D0%BD%D0%B8%D0%BA%20%D0%BD%D0%B0%20%D0%A0%D0%B5%D0%BF%D1%83%D0%B1%D0%BB%D0%B8%D0%BA%D0%B0%20%D0%9C%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D0%B8%D1%98%D0%B0%20(2).html").get();

    	//org.jsoup.select.Elements newsHeadlines = doc.select("");
    	//for (Element headline : newsHeadlines) {
    		
    		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
    	    driver.switchTo().window(tabs.get(1));
    		
    		//}
    	
    	
	}

	

}
