package org.onliner.pages;

import framework.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class ProductsPage extends HomePage {
    ConfigFileReader configFileReader= new ConfigFileReader();

    private final String checkboxFilterLocator =
            "//div[@class='schema-filter__label'][contains(.,'%s')]/following-sibling::div//span[contains(text(),'%s')]";
    private final String inputFilterLocator =
            "//div[@class='schema-filter__label'][contains(.,'%s')]/following-sibling::div//input[@placeholder='%s']";
    private final String filterButtonLocator =
            "//div[@class='schema-filter-button__inner-container']";
    private final String productPriceLocator =
            "//div[@class='schema-product__price']/a/span";
    private final String productTitleLocator =
            "//div[@class='schema-product__title']";
    private final String productDescriptionLocator =
            "//div[@class='schema-product__description']";

    private WebElement filter;

    public ProductsPage(WebDriver driver,String titleShouldContain) {
        super(driver,titleShouldContain);
    }

    public ProductsPage checkboxFilter (String filterType, String filterValue) {
        filter = driver.findElement(By.xpath(String.format(checkboxFilterLocator, filterType, filterValue)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",filter);
        WebDriverWait wait = new WebDriverWait(driver, configFileReader.getTimeOut());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(filterButtonLocator)));
        filter.click();
        return this;
    }

    public ProductsPage inputFilter (String filterType, String filterValue, String filterInputValue) {
        filter = driver.findElement(By.xpath(String.format(inputFilterLocator, filterType, filterValue)));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();",filter);
        WebDriverWait wait = new WebDriverWait(driver, configFileReader.getTimeOut());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(filterButtonLocator)));
        filter.sendKeys(filterInputValue);
        return this;
    }

    public boolean isEachProductTitleContainsFilterValue(String filterValue) {
        List<WebElement> productsTitles = driver.findElements(By.xpath(productTitleLocator));
        for (WebElement element : productsTitles) {
            if(!element.getText().contains(filterValue)){
                return false;
            };
        }
        return true;
    }

    public boolean isEachProductPriceMatchesFilterValue(String filterValue) {
        List<WebElement> productsPrices = driver.findElements(By.xpath(productPriceLocator));
        for (WebElement element : productsPrices) {
            Double price = Double.parseDouble(element.getText().replaceAll(" р.","").replace(',','.'));
            if(!(price <= Double.parseDouble(filterValue))){
                return false;
            };
        }
        return true;
    }

    public boolean isEachProductDescriptionContainsFilterValue(String filterValue){
        List<WebElement> productsDescriptions = driver.findElements(By.xpath(productDescriptionLocator));
        for (WebElement element : productsDescriptions) {
            if(!element.getText().contains(filterValue)){
                return false;
            };
        }
        return true;
    }

    public boolean isEachProductDescriptionContainsFilterValueInRange(String filterValueStartRange, String filterValueEndRange){
        List<WebElement> productsDescriptions = driver.findElements(By.xpath(productDescriptionLocator));
        for (WebElement element : productsDescriptions) {
            Double diagonal = Double.parseDouble(element.getText().substring(0,2));
            if(!(diagonal >= Double.parseDouble(filterValueStartRange) && diagonal <= Double.parseDouble(filterValueEndRange))){
                return false;
            };
        }
        return true;
    }
}

