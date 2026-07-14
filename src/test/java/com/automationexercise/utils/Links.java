package com.automationexercise.utils;

import config.ConfigurationManager;

/**
 * Links - URL constants for the application.
 *
 * The base domain is loaded from ConfigurationManager (config.properties).
 * This ensures URLs can be switched between environments without code changes.
 */
public class Links {

    // Base domain loaded from config.properties
    public static final String DOMAIN = ConfigurationManager.getBaseUrl();

    // URL endpoints (derived from DOMAIN)
    public static final String URL_LOGIN = DOMAIN + "/login";
    public static final String URL_ACCOUNT_CREATED = DOMAIN + "/account_created";
    public static final String URL_ACCOUNT_DELETE = DOMAIN + "/delete_account";
    public static final String URL_PRODUCTS = DOMAIN + "/products";
}

