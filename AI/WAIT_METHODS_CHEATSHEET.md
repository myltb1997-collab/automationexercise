# 🚀 Wait Methods Cheat Sheet

## Quick Reference - Dùng Nhanh

### Import
```java
// Chỉ cần này trong test file
import myle.pages.*; // Page objects

// By chỉ cần qualify nếu cần
org.openqa.selenium.By
```

---

## 📋 All Wait Methods Available in BasePage

### **1. Wait for Visibility**
```java
// Element phải hiển thị trên màn hình
waitForElementVisible(By by)
// Ví dụ:
homePage.waitForElementVisible(By.id("message"));
```

### **2. Wait for Presence**
```java
// Element phải có trong DOM (chưa cần visible)
waitForElementPresent(By by)
// Ví dụ:
homePage.waitForElementPresent(By.xpath("//h2[text()='Dashboard']"));
```

### **3. Wait for Clickable**
```java
// Element phải có thể click được
waitForElementClickable(By by)
// Ví dụ:
checkoutPage.waitForElementClickable(By.id("checkout-btn"));
```

### **4. Wait for Invisible**
```java
// Element phải biến mất khỏi DOM
waitForElementInvisible(By by)
// Ví dụ:
homePage.waitForElementInvisible(By.id("loading-spinner"));
```

### **5. Wait for Text**
```java
// Text phải xuất hiện trong element
waitForTextInElement(By by, String text)
// Ví dụ:
homePage.waitForTextInElement(By.id("message"), "Order Confirmed");
```

### **6. Wait for URL**
```java
// URL phải chứa text
waitForUrlContains(String urlPart)
// Ví dụ:
homePage.waitForUrlContains("/dashboard");
```

### **7. Custom Timeout**
```java
// Tạo wait với timeout khác (default = 15s)
createWaitWithTimeout(int seconds)
// Ví dụ:
WebDriverWait customWait = homePage.createWaitWithTimeout(30);
customWait.until(ExpectedConditions.visibilityOfElementLocated(by));
```

### **8. Page Stability**
```java
// Chờ page load xong (document.readyState = complete)
waitForPageStable()
// Ví dụ:
homePage.waitForPageStable();
```

---

## 🎯 Usage Examples from TC_16

### Step 6: Chờ Username
```java
HomePage homePage2 = new HomePage(driver);
homePage2.waitForElementPresent(org.openqa.selenium.By.xpath("//ul[@class='navbar-nav']//li//b"));
String username = signUpPage.getLoggedInUsername();
```

### Step 11: Chờ Address Details
```java
CheckoutPage checkoutPage2 = new CheckoutPage(driver);
checkoutPage2.waitForElementVisible(org.openqa.selenium.By.xpath("//h2[contains(text(),'Address Details')]"));
```

### Step 15: Chờ Order Complete
```java
PaymentPage paymentPage2 = new PaymentPage(driver);
paymentPage2.waitForElementVisible(org.openqa.selenium.By.xpath("//div[@class='order-complete']"));
```

### Step 17: Chờ Account Deleted
```java
signUpPage.waitForElementVisible(org.openqa.selenium.By.xpath("//h2[contains(text(),'Account Deleted')]"));
```

---

## ⚡ Quick Decision Matrix

| Need | Method | Timeout |
|------|--------|---------|
| Element visible | `waitForElementVisible(by)` | 15s |
| Element in DOM | `waitForElementPresent(by)` | 15s |
| Element clickable | `waitForElementClickable(by)` | 15s |
| Element gone | `waitForElementInvisible(by)` | 15s |
| Text appears | `waitForTextInElement(by, text)` | 15s |
| URL changes | `waitForUrlContains(part)` | 15s |
| Custom time | `createWaitWithTimeout(sec)` | Varies |
| Page ready | `waitForPageStable()` | 15s |

---

## 📝 Common Patterns

### Pattern 1: Create Page → Wait → Verify
```java
HomePage homePage = new HomePage(driver);
homePage.waitForElementVisible(By.id("welcome-message"));
Assert.assertTrue(homePage.isWelcomeDisplayed(), "Welcome should show");
```

### Pattern 2: Wait for Navigation
```java
checkoutPage.clickCheckoutBtn();
checkoutPage.waitForUrlContains("/payment");
// Now on payment page
```

### Pattern 3: Wait for Dynamic Content
```java
searchPage.search("product");
searchPage.waitForElementVisible(By.className("product-item"));
List<WebElement> results = searchPage.getSearchResults();
```

### Pattern 4: Wait for Modal/Dialog
```java
homePage.addToCart();
homePage.waitForElementVisible(By.id("add-to-cart-modal"));
homePage.clickConfirmBtn();
```

---

## 🔒 REMEMBER

✅ **DO:**
- Use page object methods for waits
- Default timeout = 15s
- Create custom timeout when needed
- Use semantic method names

❌ **DON'T:**
- Never use Thread.sleep()
- Never create inline WebDriverWait in tests
- Never hardcode timeout values scattered
- Never ignore wait exceptions

---

## 📞 Support

For more details:
- 📄 WAIT_UTILITIES_GUIDE.md
- 📄 OPTIMIZATION_BEFORE_AFTER.md
- 📄 FINAL_SUMMARY_TC16.md

---

**Last Updated:** May 9, 2026  
**Version:** 1.0

