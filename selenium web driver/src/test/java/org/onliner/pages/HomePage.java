package org.onliner.pages;

import org.openqa.selenium.WebDriver;

public class HomePage {
    protected WebDriver driver;
    public NavigationBar navigationBar;

    public HomePage(WebDriver driver, String titleShouldContain) {
        this.driver = driver;
        this.navigationBar = new NavigationBar(driver);
        if(!isCorrectPageOpened(titleShouldContain)) {
            throw new IllegalStateException("Not correct page opened, " +
                    " current page title is: " + driver.getTitle());
        }
    }

    public boolean isCorrectPageOpened(String titleShouldContain) {
        return driver.getTitle().contains(titleShouldContain);
    }
}
