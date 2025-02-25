package cn.edu.xtu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public void testEdge(){
        System.setProperty("webdriver.edge.driver", "driver/msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.get("https://scholar.google.com/");
        WebElement text = driver.findElement(By.id("gs_hp_giants"));
        System.out.println(text.getText());
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
