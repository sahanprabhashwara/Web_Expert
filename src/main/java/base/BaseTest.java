package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import utils.Constants;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    public static WebDriver driver;
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest logger;


    @BeforeTest

public void beforeTestMethod() {
    sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "SAHANTestExtentReport.html");
    extent = new ExtentReports();
    extent.attachReporter(sparkReporter);
    sparkReporter.config().setTheme(Theme.DARK);
    extent.setSystemInfo("Hostname", "RHEL8");
    extent.setSystemInfo("Username", "root");
    sparkReporter.config().setDocumentTitle("Automation Report");
    sparkReporter.config().setReportName("Automation Test Results by Sahan");
}

@BeforeMethod
@Parameters("browser")
public void beforeMethodMethod(String browser, Method testMethod) {
    logger = extent.createTest(testMethod.getName());
    setupDriver(browser);
    driver.manage().window().maximize();
    driver.get(Constants.url);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
}
    @AfterTest
    public void afterTest() {
        extent.flush();
    }

    public void setupDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();

            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();

            driver = new EdgeDriver();
        }
    }

}