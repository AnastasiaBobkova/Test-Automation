package org.onliner.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavigationBar {
    private WebDriver driver;
    private final String navigationItemLocator = "//ul[@class='b-main-navigation']//span[text() = '%s']";
    private WebElement navigationItem;

    public NavigationBar(WebDriver driver) {
        this.driver = driver;
    }

    public CategoryPage selectNavigationItem(String selectedNavigationItem) {
        navigationItem = driver.findElement(By.xpath(String.format(navigationItemLocator, selectedNavigationItem)));
        navigationItem.click();
        return new CategoryPage(driver,selectedNavigationItem);
    }
}
