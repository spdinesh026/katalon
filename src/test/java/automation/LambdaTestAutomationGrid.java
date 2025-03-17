package automation;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LambdaTestAutomationGrid {
    private WebDriverWait wait;
    private List<String> windowsList;
    String username="spdinesh026";
    String accesskey ="LT_WYGSTc4jUMxdFDWRAC1jpmfvXzy4kQB4P1XNM0WHoB77zb6";
    static RemoteWebDriver driver =null;
    String gridURL = "@hub.lambdatest.com/wd/hub";

    
    @Parameters({"browser", "version", "platform"})
    @BeforeClass
    public void setup(String browser, String version, String platform) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setVersion(version);
        capabilities.setCapability("platformName", platform);
        capabilities.setBrowserName(browser);
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("visual", true);
        ltOptions.put("video", true);
        ltOptions.put("build", "Build04");
        ltOptions.put("name", "Lambda Test Execution");
        ltOptions.put("project", "LambdaTest");
        ltOptions.put("selenium_version", "4.27.0");
        ltOptions.put("driver_version", version);
      //  ltOptions.put("console", "true");
      //  ltOptions.put("timezone", "Kolkata");

        ltOptions.put("w3c", true);
       capabilities.setCapability("LT:Options", ltOptions);
        
        driver = new RemoteWebDriver(new URL("https://"+username+":"+accesskey+gridURL), capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }
    
    @Test(priority = 1)
    public void navigateToLambdaTest() {
        driver.get("https://www.lambdatest.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    @Test(priority = 2)
    public void clickExploreAllIntegrations() {
        WebElement exploreIntegrations = driver.findElement(By.xpath("//a[text()='Explore all Integrations']"));
        JavascriptExecutor js = (JavascriptExecutor) driver; 
        js.executeScript("javascript:window.scrollBy(250,350)");
        js.executeScript("arguments[0].setAttribute('target', '_blank');", exploreIntegrations);
        exploreIntegrations.click();
    }

    @Test(priority = 3)
    public void handleNewTab() {
    	
    	Set<String> windowHandles = driver.getWindowHandles();
        windowsList = windowHandles.stream().toList();
        System.out.println("Window Handles: " + windowsList);
        driver.switchTo().window(windowsList.get(1));
    }

    @Test(priority = 4)
    public void verifyIntegrationsPageURL() {
        String expectedURL = "https://www.lambdatest.com/integrations";
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL, "URL does not match expected URL!");
    }

    @Test(priority = 5)
    public void scrollToCodelessAutomation() {
        WebElement codelessAutomation = driver.findElement(By.xpath("//h2[contains(text(),'Codeless Automation')]") );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", codelessAutomation);
    }

    @Test(priority = 6)
    public void clickTestingWhizIntegration() {
    	By locator = By.xpath("//a[normalize-space()='Integrate Testing Whiz with LambdaTest']");

        ((JavascriptExecutor) driver).executeScript("javascript:window.scrollBy(250,350)");
        
        // Use WebDriverWait to locate the element again before clicking
        WebElement testingWhizLink = wait.until(ExpectedConditions.elementToBeClickable(locator));
        testingWhizLink.click();
    }

    @Test(priority = 7)
    public void verifyTestingWhizPageTitle() {
    	String expectedTitle = "TestingWhiz Integration With LambdaTest";
        String actualTitle = driver.findElement(By.cssSelector("div[class='theme-doc-markdown markdown'] h1")).getText();
        System.out.println(actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle, "Title does not match!");
    }

    @Test(priority = 8)
    public void closeNewTabAndSwitchBack() {
        driver.close();
        driver.switchTo().window(windowsList.get(0));
        System.out.println("Remaining Windows Count: " + driver.getWindowHandles().size());
    }

    @Test(priority = 9)
    public void navigateToBlog() {
        driver.get("https://www.lambdatest.com/blog");
    }

    @Test(priority = 10)
    public void clickCommunityLinkAndVerify() {
        WebElement communityLink = driver.findElement(By.xpath("//a[text()='Community']"));
        communityLink.click();
        wait.until(ExpectedConditions.urlToBe("https://community.lambdatest.com/"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://community.lambdatest.com/", "Community page URL does not match expected URL!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
    }

