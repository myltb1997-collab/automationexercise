package myle.testcases;

import io.qameta.allure.Step;
import myle.pages.HomePage;
import myle.pages.ProductPage;
import myle.utilities.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {
    HomePage homePage;
    ProductPage productPage;

    @Test(priority = 1)
    @Step("View all products and verify product details")
    public void testProductAllandProductDetail() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        productPage = homePage.openProductsPage();
        Assert.assertTrue(productPage.isAllProductPageVisible());

        String actualTitle = productPage.getPageTitle();
        Assert.assertEquals(actualTitle, TestDataReader.getString("product.allProductsTitle"));
        Assert.assertTrue(productPage.isProductsListVisible(), "Products list is NOT visible!");
        System.out.println("So luong product: " + productPage.getProductList().size());

        productPage.clickViewFirstProduct();
        Assert.assertTrue(driver.getCurrentUrl().contains("product_details"), "This is not product detail page! ");
        Assert.assertEquals(productPage.getName(), TestDataReader.getString("product.detail.name"));
        Assert.assertEquals(productPage.getCategory(), TestDataReader.getString("product.detail.category"));
        Assert.assertEquals(productPage.getPrice(), TestDataReader.getString("product.detail.price"));
        Assert.assertEquals(productPage.getAvailability(), TestDataReader.getString("product.detail.availability"));
        Assert.assertEquals(productPage.getCondition(), TestDataReader.getString("product.detail.condition"));
        Assert.assertEquals(productPage.getBrand(), TestDataReader.getString("product.detail.brand"));
    }

    @Test(priority = 2)
    @Step("Search for product and verify search results")
    public void testSearchProduct() {
        homePage = new HomePage(driver);
        String nameProduct = TestDataReader.getString("product.search.term");

        Assert.assertTrue(homePage.isHomePageVisible());
        productPage = homePage.openProductsPage();
        Assert.assertTrue(productPage.isAllProductPageVisible());
        productPage.searchProduct(nameProduct);
        productPage.clickSearchBtn();
        Assert.assertEquals(productPage.isSearchedProductsVisible(), TestDataReader.getString("messages.searchedProducts"));
        Assert.assertTrue(productPage.isAllProductRelatedVisible(nameProduct));
    }
}
