import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Testing {
    private static  final  String imagesPath = "src/main/resources";

    public  Testing() {}

    private void takeScreenshot(WebDriver driver, String fileName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(), Paths.get(Testing.imagesPath + fileName));
    }

    private boolean checkSorted(List<String> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void TC01() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\OneDrive\\Desktop\\SWT\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://live.techpanda.org/");
        var MobileLink = driver.findElement(By.cssSelector("[href='http://live.techpanda.org/index.php/mobile.html']"));
        MobileLink.click();
        var SortDropdown = driver.findElement(By.cssSelector("[title='Sort By']"));
        Select SortSelect = new Select(SortDropdown);
        SortSelect.selectByVisibleText("Name");
        List<WebElement> productLinkLists = driver.findElements(By.cssSelector(".product-name a"));
        var nameList = new ArrayList<String>();
        for (var anchor : productLinkLists) {
            nameList.add(anchor.getText());
        }
        try {
            takeScreenshot(driver, "TC01.png");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        boolean isSorted = checkSorted(nameList);
        Assertions.assertTrue(isSorted);
        driver.quit();
    }
    private double getActualPrice(String text) {
        String result = text.substring(1);
        return Double.parseDouble(result);
    }

    @Test
    public void TC02() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ADMIN\\OneDrive\\Desktop\\SWT\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://live.techpanda.org/");
        var MobileLink = driver.findElement(By.cssSelector("[href='http://live.techpanda.org/index.php/mobile.html']"));
        MobileLink.click();
        //<a href="http://live.techpanda.org/index.php/mobile/sony-xperia.html" title="Sony Xperia">Sony Xperia</a>
        var SonyXperiaLink = driver.findElement(By.cssSelector("[href='http://live.techpanda.org/index.php/mobile/sony-xperia.html']"));
        SonyXperiaLink.click();
        try {
            takeScreenshot(driver, "TC02.png");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        var phonePriceElement = driver.findElement(By.cssSelector("span .price"));
        double phonePrice = getActualPrice(phonePriceElement.getText());
        Assertions.assertEquals(phonePrice, 100);
        driver.quit();
    }
}
