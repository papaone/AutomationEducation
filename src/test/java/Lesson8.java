import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lesson8 {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(Lesson8.class);

    String url220 = "https://www.220-volt.ru/";


    @Before
    public void webDriverStart() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().fullscreen();
        logger.info("================");
        logger.info("driver start");
    }

    @Test
    public void getContactPageInfo() {
        By categoryPowerTools = By.cssSelector("[title='Электроинструменты']");
        By categoryPerforators = By.xpath("//span[contains(text(),'Перфораторы')]");
        By checkBoxMakitaOption = By.xpath("//input[contains(@title,'MAKITA')]");
        By checkBoxZybrOption = By.xpath("//input[contains(@title,'ЗУБР')]");
        By compateItemZybr = By.xpath("//a[contains(text(),'ЗУБР')]");
        By compateItemMakita = By.xpath("//a[contains(text(),'MAKITA')]");
        By itemMakitaLocator = By.xpath("//a[contains(text(),'MAKITA')]/../preceding-sibling::div[@class='new-item-list-compare custom-ui-compare compare']");
        By itemZybrLocator = By.xpath("//a[contains(text(),'ЗУБР')]/../preceding-sibling::div[@class='new-item-list-compare custom-ui-compare compare']");
        By startCompareBtn = By.xpath("//a[contains(@title, 'В сравнении')]");
        By closeBtnCompareModal = By.xpath("//a[contains(text(),'Продолжить просмотр')]");
        By priceFilterLocator = By.xpath("//select[contains(@class,'listing-select-sort')]");
        By filterSubmitBtn = By.cssSelector("#filterSubm");

        WebDriverWait wait = new WebDriverWait(driver, 15L);

        driver.get(url220);
        driver.findElement(categoryPowerTools).click();
        wait.until(driver -> driver.findElement(categoryPerforators)).click();
        wait.until(ExpectedConditions.elementToBeClickable(checkBoxMakitaOption));
        driver.findElement(checkBoxMakitaOption).click();
        driver.findElement(checkBoxZybrOption).click();
        driver.findElement(filterSubmitBtn).click();
        Select priceFilter = new Select(driver.findElement(priceFilterLocator));
        priceFilter.selectByIndex(0);
        driver.findElement(itemZybrLocator).click();
        wait.until(ExpectedConditions.elementToBeClickable(closeBtnCompareModal));
        driver.findElement(closeBtnCompareModal).click();
        driver.findElement(itemMakitaLocator).click();
        wait.until(ExpectedConditions.elementToBeClickable(closeBtnCompareModal));
        driver.findElement(closeBtnCompareModal).click();
        wait.until(ExpectedConditions.elementToBeClickable(startCompareBtn));
        driver.findElement(startCompareBtn).click();
        String itemZybrTitles = driver.findElement(compateItemZybr).getText();
        Assert.assertThat(itemZybrTitles, CoreMatchers.containsString("ЗУБР"));
        String itemMakitaTitles = driver.findElement(compateItemMakita).getText();
        Assert.assertThat(itemMakitaTitles, CoreMatchers.containsString("MAKITA"));
    }

    @After
    public void driverClose() {
        if (driver != null) {
            driver.close();
        }
        logger.info("driver end");
    }

}
