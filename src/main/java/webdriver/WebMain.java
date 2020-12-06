package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

public class WebMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/firefoxdriver/geckodriver.exe");

        getTableText();


    }
    public static void getTableText() {

        System.out.println("Task 1");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://sqengineer.com/practice-sites/practice-tables-selenium/");

        System.out.println("Part 1: Click Links");
        WebElement linkTable = driver.findElement(By.id("table1"));
        List<WebElement> linkHrefs = linkTable.findElements(By.tagName("a"));

        //used to open in a new tab
        String openLinkNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
        for (WebElement href : linkHrefs) {
            href.sendKeys(openLinkNewTab);
        }
        System.out.println("All links clicked!");

        System.out.println("Part 2: Print Sport Table");
        WebElement table = driver.findElement(By.id("table2"));

        List<WebElement> allRows = table.findElements(By.tagName("tr"));
        List<WebElement> firstRow = allRows.get(0).findElements(By.tagName("td"));

        for (WebElement td : firstRow) {
            if (!td.getText().equalsIgnoreCase("Golf"))
                System.out.println(td.getText());
        }

        List<WebElement> thirdRow = allRows.get(2).findElements(By.tagName("td"));

        System.out.print(thirdRow.get(0).getText());

        List<WebElement> fourthRow = allRows.get(3).findElements(By.tagName("td"));

        System.out.print(thirdRow.get(2).getText());

    }

}
