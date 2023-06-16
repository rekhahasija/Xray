import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class XrayTest {
WebDriver driver;

    @BeforeTest
    public void firstTestSetUp(){
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.manage().window().maximize();

    }


    @Test
    public void FirstTest(){
        System.out.println("Hello 1");
    }

    @Test
    public void secondTest(){
        System.out.println("second");
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
