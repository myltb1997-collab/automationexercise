package myle.testcases;

import myle.pages.HomePage;
import myle.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductTest extends BaseTest {
    HomePage homePage;
    ProductPage productPage;

    @Test(priority = 1)//TC8
    public void testProductAllandProductDetail() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        productPage = homePage.movToProductPage();
        Assert.assertTrue(productPage.isAllProductPageVisible());
        String actualTitle = productPage.getPageTitle();
        Assert.assertEquals(actualTitle, "Automation Exercise - All Products");
        Assert.assertTrue(productPage.isProductsListVisible(), "Products list is NOT visible!");
        System.out.println("So luong product: " + productPage.getProductList().size());
        productPage.clickViewFirstProduct();
        Assert.assertTrue(driver.getCurrentUrl().contains("product_details"), "This is not product detail page! ");
        Assert.assertEquals(productPage.getName(), "Blue Top");
        Assert.assertEquals(productPage.getCategory(), "Women > Tops");
        Assert.assertEquals(productPage.getPrice(), "Rs. 500");
        Assert.assertEquals(productPage.getAvailability(), "In Stock");
        Assert.assertEquals(productPage.getCondition(), "New");
        Assert.assertEquals(productPage.getBrand(), "Polo");
    }

    @Test(priority = 2)
    public void testSearchProduct() {
        homePage = new HomePage(driver);
        String nameProduct = "white";

        Assert.assertTrue(homePage.isHomePageVisible());
        productPage = homePage.movToProductPage();
        Assert.assertTrue(productPage.isAllProductPageVisible());
        productPage.searchProduct(nameProduct);
        productPage.clickSearchBtn();
        Assert.assertEquals(productPage.isSearchedProductsVisible(),"SEARCHED PRODUCTS");
        Assert.assertTrue(productPage.isAllProductRelatedVisible(nameProduct));

    }
}
