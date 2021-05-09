package org.onliner.tests;

import framework.Browser;
import framework.ConfigFileReader;
import org.onliner.pages.HomePage;
import org.onliner.pages.ProductsPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TestOnlinerFilter {
    Browser browserDriver = new Browser();
    WebDriver driver = browserDriver.getDriver();
    ConfigFileReader configFileReader= new ConfigFileReader();

    @BeforeTest
    public void setup(){
        browserDriver.navigateToUrl(configFileReader.getApplicationUrl());
    }

    @Parameters({"homePageTitle","navigationItem",
            "catalogSection","catalogAsideListItem","catalogAsideDropdownListItem",
            "filterTypeProducer","filterValueProducer",
            "filterTypePrice","filterTypeMaxPrice","filterInputValueMaxPrice",
            "filterTypeResolution","filterValueResolution",
            "filterTypeDiagonal","filterValueMinDiagonal","filterValueMaxDiagonal"
    })
    @Test
    public void checkProductsPageFilters(String homePageTitle, String navigationItem,
                                         String catalogSection, String catalogAsideListItem, String catalogAsideDropdownListItem,
                                         String filterTypeProducer, String filterValueProducer,
                                         String filterTypePrice, String filterTypeMaxPrice, String filterInputValueMaxPrice,
                                         String filterTypeResolution, String filterValueResolution,
                                         String filterTypeDiagonal, String filterValueMinDiagonal, String filterValueMaxDiagonal){
        HomePage homePage = new HomePage(driver, homePageTitle);
        ProductsPage productsPage = homePage.navigationBar
                .selectNavigationItem(navigationItem)
                .selectCatalogNavigationItem(catalogSection)
                .moveToAndSelectCatalogAsideListDropdownItem(catalogAsideListItem, catalogAsideDropdownListItem)
                .checkboxFilter(filterTypeProducer,filterValueProducer)
                .inputFilter(filterTypePrice,filterTypeMaxPrice, filterInputValueMaxPrice)
                .checkboxFilter(filterTypeResolution,filterValueResolution)
                .checkboxFilter(filterTypeDiagonal,filterValueMinDiagonal)
                .checkboxFilter(filterTypeDiagonal,filterValueMaxDiagonal);

        SoftAssert softAssert = new SoftAssert();
        String ERROR_MSG_TITLE_NOT_MATCH = "Not each product title contains selected producer: %s";
        String ERROR_MSG_PRICE_NOT_MATCH_RANGE = "Not each product price is within selected range: %s %s";
        String ERROR_MSG_DESCRIPTION_NOT_MATCH = "Not each product description contains filtered value: %s";
        String ERROR_MSG_DESCRIPTION_NOT_MATCH_RANGE = "Not each product description contains value within selected range: %s %s";

        softAssert.assertTrue(productsPage.isEachProductTitleContainsFilterValue(filterValueProducer),
                String.format(ERROR_MSG_TITLE_NOT_MATCH,filterValueProducer));
        softAssert.assertTrue(productsPage.isEachProductPriceMatchesFilterValue(filterInputValueMaxPrice),
                String.format(ERROR_MSG_PRICE_NOT_MATCH_RANGE,filterTypeMaxPrice,filterInputValueMaxPrice));
        softAssert.assertTrue(productsPage.isEachProductDescriptionContainsFilterValue(filterValueResolution),
                String.format(ERROR_MSG_DESCRIPTION_NOT_MATCH,filterValueResolution));
        softAssert.assertTrue(productsPage.isEachProductDescriptionContainsFilterValueInRange(filterValueMinDiagonal,filterValueMaxDiagonal),
                String.format(ERROR_MSG_DESCRIPTION_NOT_MATCH_RANGE,filterValueMinDiagonal,filterValueMaxDiagonal));
        softAssert.assertAll();
    }

    @AfterTest
    public void tearDown(){
        browserDriver.closeBrowser();
    }
}
