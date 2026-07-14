package com.automationexercise.utils;

import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.SignUpPage;
import org.openqa.selenium.WebDriver;

public class AccountUtils {
    public static SignUpPage createAccount(WebDriver driver, String name, String email, String password) {
        return createAccount(driver, name, email, password,
                TestDataReader.getString("accounts.defaults.day"),
                TestDataReader.getString("accounts.defaults.month"),
                TestDataReader.getString("accounts.defaults.year"),
                TestDataReader.getString("accounts.defaults.firstName"),
                TestDataReader.getString("accounts.defaults.lastName"),
                TestDataReader.getString("accounts.defaults.address"),
                TestDataReader.getString("accounts.defaults.country"),
                TestDataReader.getString("accounts.defaults.state"),
                TestDataReader.getString("accounts.defaults.city"),
                TestDataReader.getString("accounts.defaults.zipcode"),
                TestDataReader.getString("accounts.defaults.mobile"));
    }

    public static SignUpPage createAccount(WebDriver driver, String name, String email, String password,
                                           String day, String month, String year,
                                           String firstName, String lastName, String address, String country,
                                           String state, String city, String zip, String mobile) {
        HomePage homePage = new HomePage(driver);
        SignUpPage signUpPage = homePage.openLoginPage();
        signUpPage.enterSignUpName(name)
                .enterSignUpEmail(email)
                .clickToSubmitSignUpBtn();
        signUpPage.inputFormAcc(password, day, month, year)
                .inputFormAddress(firstName, lastName, address, country, state, city, zip, mobile);
        signUpPage.clickContinueCreateBtn();
        return signUpPage;
    }
}

