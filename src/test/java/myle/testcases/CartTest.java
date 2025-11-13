package myle.testcases;

import myle.pages.CartPage;
import myle.pages.HomePage;
import myle.pages.ProductPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {
    HomePage homePage;
    ProductPage productPage;
    CartPage cartPage;

    @Test(priority = 1) //TC12 Add Products in Cart
    public void testAddProductInCart() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        productPage = homePage.movToProductPage();

//        productPage.clickAddToCartFirstProduct();
//        productPage.clickContinueShoppingBtn();
//        productPage.clickAddToCartSecondProduct();
//        productPage.clickViewCartBtn();
        productPage.addProductsToCart(2, true);
        cartPage = new CartPage(driver);
        List<WebElement> items = cartPage.getCartItems();
        Assert.assertEquals(items.size(), 2);

        for (WebElement item : items) {
            double price = cartPage.getPriceProduct(item);
            int quatity = cartPage.getQuantityProduct(item);
            double expectedTotal = price * quatity;
            double actualTotal = cartPage.getTotalProduct(item);
            Assert.assertEquals(actualTotal, expectedTotal);
        }
    }

    @Test(priority = 2) //TC 13: Verify Product quantity in Cart
    public void verifyProductQuantityInCart() {
        int indexNumber = 1;
        int numberQuantity = 4;
        homePage = new HomePage(driver);
        homePage.isHomePageVisible();
        productPage = homePage.clickViewFirstProduct(indexNumber);
        Assert.assertTrue(driver.getCurrentUrl().contains("product_details/"), "This is not product detail page!!!");
        productPage.inputQuantity(numberQuantity);
        productPage.clickAddToCart();
        cartPage = productPage.clickViewCartBtn();

        int actualQuantity = cartPage.getProductQuantity();
        Assert.assertEquals(actualQuantity,numberQuantity,"Incorrect product quantity in cart");
    }


}
