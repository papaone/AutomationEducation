import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;


public class Leason7 {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(FirstTest.class);

    String urlOtus = "https://otus.ru/";
    String urlTele2 = "https://msk.tele2.ru/shop/number";


    @Before
    public void WebDriverStart() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        logger.info("================");
        logger.info("driver start");
    }

    @Test
    public void GetContactPageInfo() {
        String expectedAddressText = "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02";
        String expectedPageTittle = "Контакты | OTUS";

        driver.get(urlOtus);
        driver.findElement(By.xpath("//header/div[1]/div[2]/div[1]/a[5]")).click();
        String actualAddressText = driver.findElement(By.xpath("//body/div[@id='__next']/div[3]/div[1]/div[3]/div[1]/div[3]/div[2]")).getText();
        Assert.assertEquals(expectedAddressText, actualAddressText);
        driver.manage().window().fullscreen();
        String actualPageTittle = driver.getTitle();
        Assert.assertEquals(expectedPageTittle, actualPageTittle);
    }

    @Test
    public void CheckPhoneNumbers() {
        int defaultNumberCount = 24;
        By searchField = By.cssSelector("#searchNumber");
        By loadIcon = By.xpath("//*[@id='root']/div/div[1]//div[2]/div[1]//span/div");
        By phoneNumber = By.className("phone-number");

        driver.get(urlTele2);
        WebDriverWait wait = new WebDriverWait(driver, 10L, 125L);
        wait.until(driver -> driver.findElement(searchField));
        driver.findElement(searchField).sendKeys("97");
        wait.until(driver -> driver.findElements(loadIcon).size() == 0);
        Assert.assertEquals(driver.findElements(phoneNumber).size(), defaultNumberCount);
    }

    @Test
    public void CheckFaqText() {
        By faqButton = By.xpath("//header/div[1]/div[2]/div[1]/a[3]");
        By question = By.xpath("//div[contains(text(),'Где посмотреть программу интересующего курса?')]");
        By answer = By.xpath("//div[contains(text(),'Программу курса в сжатом виде можно увидеть на стр')]");
        String expectedText = "Программу курса в сжатом виде можно увидеть на странице курса " +
                "после блока с преподавателями. Подробную программу курса можно " +
                "скачать кликнув на “Скачать подробную программу курса”";

        driver.get(urlOtus);
        driver.findElement(faqButton).click();
        driver.findElement(question).click();
        String actualText = driver.findElement(answer).getText();

        Assert.assertEquals(expectedText, actualText);
    }

    @Test
    public void CheckSubscribeFunction() {
        String email = System.currentTimeMillis() + "@test.com";
        By emailInputField = By.name("email");
        By subscribeButton = By.xpath("//body/div[1]/div[1]/footer[1]//div[1]/div[3]/form[1]/button[1]");
        By successText = By.xpath("//p[contains(text(),'Вы успешно подписались')]");

        driver.get(urlOtus);
        driver.findElement(emailInputField).sendKeys(email);
        driver.findElement(subscribeButton).click();
        driver.findElement(successText).isDisplayed();
    }

    @After
    public void DriverClose() {
        if (driver != null) {
            driver.close();
        }
        logger.info("driver end");
    }

}
