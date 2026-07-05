package myle.testcases;

import io.qameta.allure.Step;
import myle.pages.CategoryPage;
import myle.pages.CategorySidebarPage;
import myle.pages.HomePage;
import myle.utilities.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CategoryViewTest extends BaseTest {
    @Test
    @Step("View products by categories (Women and Men)")
    public void testViewCategoryProducts_TC18() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible");

        CategorySidebarPage sidebar = new CategorySidebarPage(driver);
        Assert.assertTrue(sidebar.isSidebarVisible(), "Category sidebar is not visible");

        String womenCategory = TestDataReader.getString("category.women");
        String womenSubCategory = TestDataReader.getString("category.womenSubCategory");
        String womenTitle = TestDataReader.getString("category.womenTitle");
        String menCategory = TestDataReader.getString("category.men");
        String menSubCategory = TestDataReader.getString("category.menSubCategory");
        String menTitle = TestDataReader.getString("category.menTitle");

        sidebar.clickCategory(womenCategory);
        Assert.assertTrue(sidebar.isCategoryVisible(womenCategory), "Women category not visible after click");

        sidebar.clickSubCategory(womenCategory, womenSubCategory);
        CategoryPage categoryPage = new CategoryPage(driver);
        Assert.assertTrue(categoryPage.isCategoryTitleVisible(womenTitle), "Category page title incorrect or not visible");
        Assert.assertTrue(categoryPage.isProductListVisible(), "Product list not visible for WOMEN - DRESS");

        sidebar.clickCategory(menCategory);
        sidebar.clickSubCategory(menCategory, menSubCategory);
        Assert.assertTrue(categoryPage.isCategoryTitleVisible(menTitle), "Category page title incorrect for MEN - TSHIRTS");
        Assert.assertTrue(categoryPage.isProductListVisible(), "Product list not visible for MEN - TSHIRTS");
    }
}
