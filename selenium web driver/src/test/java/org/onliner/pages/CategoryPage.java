package org.onliner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class CategoryPage extends HomePage {
    private final String catalogNavigationItemLocator =
            "//span[@class='catalog-navigation-classifier__item-title'][contains(.,'%s')]";
    private final String catalogAsideListItemLocator =
            "//div[@class='catalog-navigation-list__aside-item'][contains(.,'%s')]";
    private final String catalogAsideListDropdownItemLocator =
            "//a[@class='catalog-navigation-list__dropdown-item'][contains(.,'%s')]";

    private WebElement catalogNavigationItem;
    private WebElement catalogAsideListItem;
    private WebElement catalogAsideListDropdownItem;

    public CategoryPage(WebDriver driver, String titleShouldContain){
        super(driver,titleShouldContain);
    }

    public CategoryPage selectCatalogNavigationItem(String selectedCatalogNavigationItem) {
        catalogNavigationItem = driver.findElement(By.xpath(String.format(catalogNavigationItemLocator,selectedCatalogNavigationItem)));
        catalogNavigationItem.click();
        return this;
    }

    public ProductsPage moveToAndSelectCatalogAsideListDropdownItem(String selectedCatalogAsideListItem, String selectedCatalogAsideListDropdownItem) {
        catalogAsideListItem = driver.findElement(By.xpath(String.format(catalogAsideListItemLocator,selectedCatalogAsideListItem)));
        catalogAsideListDropdownItem = driver.findElement(By.xpath(String.format(catalogAsideListDropdownItemLocator,selectedCatalogAsideListDropdownItem)));
        Actions action = new Actions(driver);
        action.moveToElement(catalogAsideListItem).click().moveToElement(catalogAsideListDropdownItem).click().perform();
        return new ProductsPage(driver,selectedCatalogAsideListDropdownItem);
    }
}
