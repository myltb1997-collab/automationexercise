package myle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class CategorySidebarPage extends BasePage {
    @FindBy(css = ".left-sidebar .panel-group")
    WebElement sidebarPanel;

    @FindBy(css = ".left-sidebar .panel-group .panel")
    List<WebElement> categoryPanels;

    public CategorySidebarPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isSidebarVisible() {
        return sidebarPanel.isDisplayed();
    }

    public void clickCategory(String categoryName) {
        for (WebElement panel : categoryPanels) {
            WebElement header = panel.findElement(By.cssSelector(".panel-heading a[data-toggle='collapse']"));
            if (header.getText().trim().equalsIgnoreCase(categoryName)) {
                safeClick(header);
                return;
            }
        }
        throw new RuntimeException("Category not found: " + categoryName);
    }

    public void clickSubCategory(String parentCategory, String subCategoryName) {
        for (WebElement panel : categoryPanels) {
            WebElement header = panel.findElement(By.cssSelector(".panel-heading a[data-toggle='collapse']"));
            if (header.getText().trim().equalsIgnoreCase(parentCategory)) {
                // Expand if not expanded
                if (!panel.getAttribute("class").contains("in")) {
                    safeClick(header);
                }
                List<WebElement> subLinks = panel.findElements(By.cssSelector(".panel-collapse li a"));
                for (WebElement sub : subLinks) {
                    if (sub.getText().trim().equalsIgnoreCase(subCategoryName)) {
                        safeClick(sub);
                        return;
                    }
                }
                throw new RuntimeException("Sub-category not found: " + subCategoryName);
            }
        }
        throw new RuntimeException("Parent category not found: " + parentCategory);
    }

    public boolean isCategoryVisible(String categoryName) {
        for (WebElement panel : categoryPanels) {
            WebElement header = panel.findElement(By.cssSelector(".panel-heading a[data-toggle='collapse']"));
            if (header.getText().trim().equalsIgnoreCase(categoryName)) {
                return header.isDisplayed();
            }
        }
        return false;
    }

    public boolean isSubCategoryVisible(String parentCategory, String subCategoryName) {
        for (WebElement panel : categoryPanels) {
            WebElement header = panel.findElement(By.cssSelector(".panel-heading a[data-toggle='collapse']"));
            if (header.getText().trim().equalsIgnoreCase(parentCategory)) {
                List<WebElement> subLinks = panel.findElements(By.cssSelector(".panel-collapse li a"));
                for (WebElement sub : subLinks) {
                    if (sub.getText().trim().equalsIgnoreCase(subCategoryName)) {
                        return sub.isDisplayed();
                    }
                }
            }
        }
        return false;
    }
}

