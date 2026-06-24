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
        productPage = homePage.navigation.moveToProductPage();

        productPage.addProductsToCart(2, true);
        cartPage = new CartPage(driver);
        List<WebElement> items = cartPage.getCartItems();
        Assert.assertEquals(items.size(), 2);

        for (WebElement item : items) {
            double price = cartPage.getPriceProduct(item);
            int quantity = cartPage.getQuantityProduct(item);
            double expectedTotal = price * quantity;
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

    @Test(priority = 3) //TC 17: Remove Products From Cart
    public void testRemoveProductsFromCart() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");

        homePage.clickAddToCartBtn();
        cartPage = homePage.clickViewCartBtn();
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Verify that cart page is displayed");
        Assert.assertTrue(cartPage.getCartItemCount() > 0, "Verify product is added to cart");

        String productId = cartPage.getProductIdByIndex(0);
        cartPage.removeProductById(productId);

        Assert.assertTrue(cartPage.isProductRemoved(productId), "Verify that product is removed from the cart");
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Verify cart has no products after removal");
        Assert.assertTrue(cartPage.isEmptyCartMessageVisible(), "Verify empty cart message is displayed");
    }


}
