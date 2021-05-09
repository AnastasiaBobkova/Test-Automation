package framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Browser {
    private WebDriver driver;
    private ConfigFileReader configFileReader;

    public Browser() {
        configFileReader = new ConfigFileReader();
        System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(configFileReader.getImplicitlyWait(), configFileReader.getTimeUnit());
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }

    public void closeBrowser() {
        driver.close();
    }
}
