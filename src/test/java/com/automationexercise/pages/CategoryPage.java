package com.automationexercise.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;

public class CategoryPage extends BasePage {
    public final NavigationComponent navigation;

    // Sửa selector: tiêu đề category thực tế là h2.title.text-center hoặc h2.title.text-center + strong
    @FindBy(css = ".features_items h2.title.text-center, .features_items h2.title.text-center strong")
    WebElement categoryTitle;

    public CategoryPage(WebDriver driver) {
        super(driver);
        this.navigation = new NavigationComponent(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isCategoryTitleVisible(String expectedTitle) {
        return categoryTitle.isDisplayed() && categoryTitle.getText().trim().toUpperCase().contains(expectedTitle.toUpperCase());
    }

    public String getCategoryTitleText() {
        return categoryTitle.getText().trim();
    }

    public boolean isProductListVisible() {
        return !driver.findElements(By.cssSelector(".features_items .product-image-wrapper")).isEmpty();
    }
}
