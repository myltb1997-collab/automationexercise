package myle.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static myle.utilities.Links.URL_PRODUCTS;

public class ProductPage extends BasePage {

    @FindBy(css = "div.product-image-wrapper")
    List<WebElement> productList;
    @FindBy(css = "div[class='product-information'] h2")
    WebElement productName;
    @FindBy(xpath = "//p[text()='Category: Women > Tops']")
    WebElement productCategory;
    @FindBy(css = "div[class='product-information'] span span")
    WebElement productPrice;
    @FindBy(xpath = "//p[b[text()='Availability:']]")
    WebElement productAvailability;
    @FindBy(xpath = "//p[b[text()='Condition:']]")
    WebElement productCondition;
    @FindBy(xpath = "//p[b[text()='Brand:']]")
    WebElement productBrand;
    @FindBy(css = "#search_product")
    WebElement searchProductField;
    @FindBy(css = ".fa.fa-search")
    WebElement searchBtn;
    @FindBy(xpath = "//h2[normalize-space()='Searched Products']")
    WebElement searchedProductsText;
    @FindBy(css = ".btn.btn-success.close-modal.btn-block")
    WebElement continueShoppingBtn;
    @FindBy(xpath = "//u[normalize-space()='View Cart']")
    WebElement viewCartBtn;

    @FindBy(xpath = "//h2[normalize-space()='All Products']")
    WebElement allProductsTitle;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAllProductPageVisible() {
        /*Assert.assertEquals(driver.getCurrentUrl(), URL_PRODUCTS);*/
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.urlContains(URL_PRODUCTS));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

//    public boolean isAllProductPageVisible() {
//        try {
//            return allProductsTitle.isDisplayed();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isProductsListVisible() {

        if (productList.isEmpty()) {
            return false;
        }
        //Check tung san pham hien thi
        for (WebElement product : productList) {
            if (!product.isDisplayed()) {
                return false;
            }
        }
        return true;
    }

    public List<WebElement> getProductList() {
        return productList;
    }

    public void clickViewFirstProduct() {
        if (productList.isEmpty()) {
            throw new RuntimeException("No products found on this page!");
        }
        WebElement firstProduct = productList.get(0);
        WebElement viewProductBtn = firstProduct.findElement(By.cssSelector(".choose a"));
        viewProductBtn.click();
    }

    public void clickAddToCartFirstProduct() {
        if (productList.isEmpty()) {
            throw new RuntimeException("No products found on this page!");
        }
        WebElement firstProduct = productList.get(0);
        hoverElement(firstProduct);
        WebElement addToCartBtn = firstProduct.findElement(By.cssSelector("a.btn.btn-default.add-to-cart"));

        waitToBeClickable(addToCartBtn);
        safeClick(addToCartBtn);
        //addToCartBtn.click();
    }

    public void addProductsToCart(int count, boolean viewCartAfter) {
        if (productList.isEmpty()) {
            throw new RuntimeException("No product found on this page!");
        }
        if (count > productList.size()) {
            throw new RuntimeException("Not enough products on this page!");
        }

        for (int i = 0; i < count; i++) {
            WebElement product = productList.get(i);
            hoverElement(product);
           // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);

            WebElement addToCartBtn = product.findElement(By.cssSelector(".overlay-content a.btn.btn-default.add-to-cart"));
            // waitToBeClickable(addToCartBtn);
            safeClick(addToCartBtn);
            if (i < count - 1) {
                clickContinueShoppingBtn();
            } else {
                if (viewCartAfter) {
                    clickViewCartBtn();
                } else {
                    clickContinueShoppingBtn();
                }
            }

        }
    }

    public void clickContinueShoppingBtn() {
        waitToBeClickable(continueShoppingBtn);
        continueShoppingBtn.click();
    }

    public void clickAddToCartSecondProduct() {
        if (productList.isEmpty()) {
            throw new RuntimeException("No product found on this page!");
        }
        WebElement secondProduct = productList.get(1);
        hoverElement(secondProduct);
        WebElement addToCartBtn = secondProduct.findElement(By.cssSelector(".overlay-content a.btn.btn-default.add-to-cart"));
        waitToBeClickable(addToCartBtn);
        addToCartBtn.click();
    }

    public void clickViewCartBtn() {
        waitToBeClickable(viewCartBtn);
        viewCartBtn.click();

    }

    public String getName() {
        return productName.getText().trim();
    }

    public String getCategory() {
        return normalizeText(productCategory, "Category:");
    }

    public String getPrice() {
        return productPrice.getText().trim();
    }

    public String getAvailability() {
        return normalizeText(productAvailability, "Availability:");
    }

    public String getCondition() {
        return normalizeText(productCondition, "Condition:");
    }

    public String getBrand() {
        return normalizeText(productBrand, "Brand:");
    }

    public void searchProduct(String nameProduct) {
        searchProductField.isDisplayed();
        searchProductField.clear();
        searchProductField.sendKeys(nameProduct);

    }

    public void clickSearchBtn() {
        searchBtn.isEnabled();
        searchBtn.click();
    }

    public String isSearchedProductsVisible() {
        return searchedProductsText.getText();
    }

    public boolean isAllProductRelatedVisible(String keyword) {
        if (productList.isEmpty()) {
            return false;
        }
        //Check tung san pham hien thi
        for (WebElement product : productList) {
            String productName = product.getText().toLowerCase();
            if (!productName.contains(keyword.toLowerCase())) {
                return false; // list product khong relate voi sp da search
            }
            if (!product.isDisplayed()) {
                return false;
            }
        }
        return true;
    }
}
