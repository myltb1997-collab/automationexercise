package myle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.TimeoutException;

import static myle.utilities.Links.URL_PRODUCTS;

public class ProductPage {
    WebDriver driver;

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

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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

    public String normalizeText(WebElement element, String textToRemove) {
        return element.getText().replace(textToRemove, "").trim();
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
