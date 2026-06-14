package myle.testcases;

import myle.pages.CategoryPage;
import myle.pages.CategorySidebarPage;
import myle.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CategoryViewTest extends BaseTest {
    @Test
    public void testViewCategoryProducts_TC18() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible");

        // Sidebar
        CategorySidebarPage sidebar = new CategorySidebarPage(driver);
        Assert.assertTrue(sidebar.isSidebarVisible(), "Category sidebar is not visible");

        // Step 4: Click 'Women' category
        sidebar.clickCategory("Women");
        Assert.assertTrue(sidebar.isCategoryVisible("Women"), "Women category not visible after click");

        // Step 5: Click sub-category 'Dress' under 'Women'
        sidebar.clickSubCategory("Women", "Dress");
        CategoryPage categoryPage = new CategoryPage(driver);
        Assert.assertTrue(categoryPage.isCategoryTitleVisible("WOMEN - DRESS PRODUCTS"), "Category page title incorrect or not visible");
        Assert.assertTrue(categoryPage.isProductListVisible(), "Product list not visible for WOMEN - DRESS");

        // Step 7: Click sub-category of 'Men' (e.g. 'Tshirts')
        sidebar.clickCategory("Men");
        sidebar.clickSubCategory("Men", "Tshirts");
        Assert.assertTrue(categoryPage.isCategoryTitleVisible("MEN - TSHIRTS PRODUCTS"), "Category page title incorrect for MEN - TSHIRTS");
        Assert.assertTrue(categoryPage.isProductListVisible(), "Product list not visible for MEN - TSHIRTS");
    }
}

