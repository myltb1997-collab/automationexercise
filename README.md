# Automation Exercise - QA Automation Framework

A professional **Selenium 4 + TestNG + Allure** test automation framework for e-commerce platform testing, following industry best practices with **Page Object Model** pattern and **listener-based screenshot automation**.

**Website Under Test:** https://automationexercise.com

---

## 📋 Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Running Tests](#running-tests)
- [Viewing Reports](#viewing-reports)
- [Technologies](#technologies)
- [Test Coverage](#test-coverage)
- [Jenkins CI/CD](#jenkins-cicd)

## ✨ Features

- ✅ **Page Object Model (POM)** - Maintainable, scalable architecture
- ✅ **Selenium 4** - Latest WebDriver API with cross-browser support
- ✅ **TestNG Framework** - Powerful test execution with listeners & parallel testing
- ✅ **Allure Reports** - Beautiful, detailed test reports with screenshots & timeline
- ✅ **Automatic Screenshots** - Captured on failures, successes, and skips (listener-based)
- ✅ **WebDriverManager** - No manual driver management needed
- ✅ **Listener Framework** - Centralized test event handling
- ✅ **Configuration Management** - Environment-specific settings
- ✅ **Test Data Management** - Centralized data handling
- ✅ **17+ Test Cases** - Complete e-commerce flow coverage

## 📁 Project Structure

```
automationexercise/
├── src/
│   ├── main/java/
│   │   ├── config/               # Configuration management
│   │   ├── driver/               # WebDriver factory & lifecycle
│   │   └── utilities/            # Common utilities
│   │
│   └── test/java/com/automationexercise/
│       ├── framework/            # TestNG listeners & framework logic
│       │   └── TestExecutionListener.java
│       │
│       ├── pages/                # Page Object Model classes
│       │   ├── BasePage.java
│       │   ├── HomePage.java
│       │   ├── ProductPage.java
│       │   ├── CartPage.java
│       │   ├── CheckoutPage.java
│       │   ├── SignUpPage.java
│       │   └── [13 POM classes total]
│       │
│       ├── tests/                # Test cases
│       │   ├── BaseTest.java     # Base class: setUp/tearDown + fail detection
│       │   ├── SignUpTest.java
│       │   ├── HomeTest.java
│       │   ├── ProductTest.java
│       │   ├── CartTest.java
│       │   ├── CheckoutCompleteTest.java
│       │   └── [7 test classes]
│       │
│       └── utils/                # Utility classes
│           ├── ScreenshotUtil.java  # Auto-screenshot capture
│           ├── WaitUtil.java
│           ├── TestDataReader.java
│           ├── TestDataGenerator.java
│           └── [7 utility classes]
│
├── pom.xml                        # Maven dependencies & plugins
├── testng.xml                     # TestNG execution config (listeners, parallel)
├── allure-results/                # Allure results (auto-generated)
├── allure-report/                 # Allure HTML report (auto-generated)
└── README.md                      # Documentation
```

## 🛠️ Prerequisites

- **Java 11+** (`java -version`)
- **Maven 3.6+** (`mvn -version`)
- **Git** (for cloning)
- **Chrome/Firefox/Edge** browser

## 🚀 Quick Start

```bash
# 1. Clone & setup
git clone <repository-url>
cd automationexercise
mvn clean install

# 2. Run tests
mvn clean test

# 3. View Allure report (auto-opens in browser)
mvn allure:serve
```

## 📊 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn clean test -Dtest=SignUpTest
mvn clean test -Dtest=CartTest
```

### Run Multiple Specific Tests
```bash
mvn clean test -Dtest=SignUpTest,HomeTest,ProductTest
```

### Run with Parallel Execution
```bash
mvn clean test -DthreadCount=4
```

### Generate & View Report
```bash
mvn allure:serve
```
Opens Allure report in browser with:
- ✅ Test execution timeline
- ✅ Screenshots (failures, successes, skips)
- ✅ Error logs & stack traces
- ✅ Test duration metrics

## 🔧 Technologies

| Component | Version | Purpose |
|-----------|---------|---------|
| **Selenium WebDriver** | 4.20.0 | Browser automation |
| **TestNG** | 7.10.2 | Test framework & execution |
| **Allure TestNG** | 2.24.0 | Test reporting & screenshots |
| **WebDriverManager** | 5.5.3 | Automatic driver management |
| **AspectJ Weaver** | 1.9.22.1 | Allure integration |
| **Maven** | 3.6+ | Build & dependency management |
| **Java** | 11+ | Programming language |

## ✅ Test Coverage

### Test Cases (17+):
- **SignUpTest** - Registration, email verification, account creation
- **HomeTest** - Homepage load, navigation, search functionality
- **ProductTest** - Product catalog, filtering, details view
- **CartTest** - Add/remove items, quantity update, cart persistence
- **CategoryViewTest** - Category navigation, filters, sorting
- **ContactUsTest** - Contact form submission, validation
- **CheckoutCompleteTest** - End-to-end purchase flow

### Scenarios:
- ✅ User Registration & Login
- ✅ Product Browsing & Search
- ✅ Category Navigation & Filtering
- ✅ Shopping Cart Management
- ✅ Checkout & Payment
- ✅ Account Management
- ✅ Form Validation

## 📸 Automatic Screenshot Capture

Screenshots are **automatically captured** on test events:
- 🔴 **Test Failure** → Attached to Allure report
- 🟢 **Test Success** → Final state captured for verification
- 🟡 **Test Skip** → Skip state documented

**No manual screenshot code needed!** Handled by `TestExecutionListener.java`

Location: `target/screenshots/` (or view in Allure report)

## 🏗️ Architecture & Design Patterns

### Base Classes
- **BaseTest.java**
  - Common setUp/tearDown lifecycle
  - Driver initialization & cleanup
  - Fail detection for reporting
  
- **BasePage.java**
  - Element wait utilities
  - Common page actions
  - Implicit/explicit waits

### Design Patterns
- **Page Object Model** - Encapsulate page interactions
- **Singleton Pattern** - WebDriver instance management
- **Factory Pattern** - Driver creation via DriverFactory
- **Listener Pattern** - TestNG listeners for cross-cutting concerns

### Best Practices
✅ Explicit waits (no Thread.sleep)  
✅ Meaningful test names & logging  
✅ Comprehensive error handling  
✅ Proper resource cleanup  
✅ Configuration externalization  
✅ DRY principle throughout  

## ⚙️ Configuration

Edit `src/main/resources/config.properties`:
```properties
base.url=https://automationexercise.com
browser=chrome
implicit.wait=10
explicit.wait=15
environment=staging
```

## 🐛 Troubleshooting

### Tests Won't Compile
```bash
# Clear cache & rebuild
mvn clean install
# Check Java version
java -version  # Should be 11+
```

### WebDriver Issues
- WebDriverManager handles driver setup automatically
- Check internet connection (drivers downloaded online)
- Clear browser cache: `target/` folder

### Allure Report Not Generating
```bash
# Install Allure CLI (Windows)
choco install allure

# Generate & serve
mvn allure:serve
```

## 🔄 CI/CD Integration

### Jenkins Pipeline Setup

**1. Create Maven Job:**
- New Item → Maven project
- Source Code Management → GitHub URL

**2. Build Configuration:**
```
clean test
```

**3. Post-Build Actions:**
- Add Allure plugin
- Allure results location: `allure-results/`

**4. Plugins Required:**
- Allure Jenkins Plugin v2.32.0+
- Maven Plugin
- Git Plugin

**5. Jenkins Configuration:**
- Manage Jenkins → Tools
- Install Maven & Allure via tool management

### GitHub Actions (Alternative)

```yaml
name: Run Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: 11
      - run: mvn clean test
```

## 📝 Contributing

1. Create feature branch: `git checkout -b feature/my-test`
2. Add test cases following POM pattern
3. Ensure all tests pass: `mvn clean test`
4. Commit with meaningful message
5. Push & create Pull Request

## 📄 License

MIT License - Feel free to use for learning & development

## 👤 Author

QA Automation Engineer | Automation Exercise Project

---

**Last Updated:** July 2024  
**Framework Version:** 1.0.0  
**Status:** ✅ Production Ready
