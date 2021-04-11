import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTest {
    protected static WebDriver driver;
    private final Logger logger = LogManager.getLogger(FirstTest.class);

    String url = "https://otus.ru/";
    String expectedTitle = "Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям";

    @Before
    public void WebDriverStart() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("================");
        logger.info("driver start");
    }

    @Test
    public void WebDriverTest(){
        try {
            driver.get(url);
            logger.info("page is opened");
        } catch (Exception e) {
            logger.info("page is not opened");
        }
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @After
    public void DriverClose() {
        if (driver != null) {
            driver.close();
        }
        logger.info("driver end");
    }

}
