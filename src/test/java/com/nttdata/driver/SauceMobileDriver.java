package com.nttdata.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import net.thucydides.core.webdriver.DriverSource;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SauceMobileDriver implements DriverSource {
    private final EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();

    @Override
    public WebDriver newDriver() {

        // -------- leer flags Maven ----------
        String environment = prop("environment", null);
        String deviceName = prop("appium.deviceName", null);
        String platformName = prop("appium.platformName", null);
        String platformVersion = prop("appium.platformVersion", null);
        String app = prop("appium.app", null);
        String hub = prop("appium.hub",
                null
        );
        String appiumVersion = prop("appium.appiumVersion", "appium2-20250901");

        try {
            if (environment == null || environment.isBlank()) {
                // modo local: decidimos por el nombre del device
                if (platformName.equalsIgnoreCase("iOS")) {
                    String uid = prop("appium.udid", "9DC30D07-CEB2-4A39-A6E3-DD640D053C08");
                    return buildLocalIOS(
                            prop("appium.hub", "http://127.0.0.1:4723/"),
                            deviceName,
                            platformVersion,
                            app,
                            uid
                    );
                } else {
                    return buildLocalAndroid(
                            prop("appium.hub", "http://127.0.0.1:4723/"),
                            deviceName,
                            platformVersion,
                            app
                    );
                }

            } else {
                if (environment.equalsIgnoreCase("ios")) {
                    // remoto iOS (Sauce)
                    return buildIOS(hub, deviceName, platformVersion, app, appiumVersion);

                } else if (environment.equalsIgnoreCase("android")) {
                    // remoto Android (Sauce)
                    return buildAndroid(hub, deviceName, platformVersion, app, appiumVersion);

                } else {
                    throw new RuntimeException("Entorno no soportado: " + environment);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("URL inválida: " + hub, e);
        }
    }

    // =================================================================================
    // REMOTO ANDROID (SAUCE)
    // =================================================================================
    private WebDriver buildAndroid(String hub,
                                   String deviceName,
                                   String platformVersion,
                                   String app,
                                   String appiumVersion) throws MalformedURLException {

        UiAutomator2Options opts = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName)
                .setPlatformVersion(platformVersion);

        if (app != null && !app.isBlank()) {
            opts.setApp(app);
        }

        // estos 2 los dejo parametrizables por si cambian en tu pipeline
        String appPackage = prop("appium.appPackage", "com.saucelabs.mydemoapp.android");
        String appActivity = prop("appium.appActivity", "com.saucelabs.mydemoapp.android.view.activities.MainActivity");
        opts.setAppPackage(appPackage);
        opts.setAppActivity(appActivity);

        // extras
        opts.amend("appium:noReset", true);
        opts.amend("appium:autoGrantPermissions", true);
        opts.amend("appium:newCommandTimeout", 400);
        opts.amend("appium:waitForIdleTimeout", 3);

        // sauce:options
        Map<String, Object> sauceOpts = new HashMap<>();
        sauceOpts.put("name", "CT-Android-remote");
        sauceOpts.put("appiumVersion", "appium2-20250901");
        opts.setCapability("sauce:options", sauceOpts);

        return new AndroidDriver(new URL("https://username:password@ondemand.us-west-1.saucelabs.com:443/wd/hub"), opts);
    }

    // =================================================================================
    // REMOTO IOS (SAUCE)
    // =================================================================================
    private WebDriver buildIOS(String hub,
                               String deviceName,
                               String platformVersion,
                               String app,
                               String appiumVersion) throws MalformedURLException {

        XCUITestOptions opts = new XCUITestOptions()
                .setPlatformName("iOS")
                .setAutomationName("XCUITest")
                .setDeviceName(deviceName)
                .setPlatformVersion(platformVersion);

        if (app != null && !app.isBlank()) {
            opts.setApp(app);
        }

        opts.amend("appium:newCommandTimeout", 400);
        opts.amend("appium:waitForIdleTimeout", 3);
        opts.amend("appium:autoDismissAlerts", true);
        opts.amend("appium:autoAcceptAlerts", true);

        Map<String, Object> sauceOpts = new HashMap<>();
        sauceOpts.put("name", "CT-iOS-remote");
        //sauceOpts.put("appiumVersion", "2.11.0");
        opts.setCapability("sauce:options", sauceOpts);

        return new IOSDriver(new URL("https://username:password@ondemand.us-west-1.saucelabs.com:443/wd/hub"), opts);
    }

    // =================================================================================
    // LOCAL ANDROID (APPIUM V2 LOCAL)
    // =================================================================================
    private WebDriver buildLocalAndroid(String hub,
                                        String deviceName,
                                        String platformVersion,
                                        String app) throws MalformedURLException {

        UiAutomator2Options opts = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(deviceName)
                .setPlatformVersion(platformVersion);

        if (app != null && !app.isBlank()) {
            opts.setApp(app);
        }

        // estos 2 los dejo parametrizables por si cambian en tu pipeline
        String appPackage = prop("appium.appPackage", "com.saucelabs.mydemoapp.android");
        String appActivity = prop("appium.appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
        opts.setAppPackage(appPackage);
        opts.setAppActivity(appActivity);

        opts.amend("appium:autoGrantPermissions", true);
        opts.amend("appium:newCommandTimeout", 400);
        opts.amend("appium:waitForIdleTimeout", 3);

        return new AndroidDriver(new URL(hub), opts);
    }

    // =================================================================================
    // LOCAL IOS (APPIUM V2 LOCAL)
    // =================================================================================
    private WebDriver buildLocalIOS(String hub,
                                    String deviceName,
                                    String platformVersion,
                                    String app,
                                    String uid) throws MalformedURLException {

        XCUITestOptions opts = new XCUITestOptions()
                .setPlatformName("iOS")
                .setUdid(uid)
                .setAutomationName("XCUITest")
                .setDeviceName(deviceName)
                .setPlatformVersion(platformVersion);

        if (app != null && !app.isBlank()) {
            opts.setApp(app);
        }

        opts.amend("appium:newCommandTimeout", 400);
        opts.amend("appium:waitForIdleTimeout", 3);
        opts.amend("appium:autoDismissAlerts", true);
        opts.amend("appium:autoAcceptAlerts", true);
        opts.amend("appium:UIFileSharingEnabled", true);

        return new IOSDriver(new URL(hub), opts);
    }

    // =================================================================================
    // UTILIDADES
    // =================================================================================

    /**
     * Orden de resolución:
     * 1. System.getProperty (Maven: -Dclave=valor)
     * 2. serenity.properties / serenity.conf (EnvironmentVariables)
     * 3. default del código
     */
    private String prop(String key, String def) {
        // 1) maven / system
        String fromSystem = System.getProperty(key);
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem;
        } else {
            // 2) serenity.properties / serenity.conf
            String fromSerenity = env.getProperty(key);
            if (fromSerenity != null && !fromSerenity.isBlank()) {
                return fromSerenity;
            }
        }
        // 3) default
        return def;
    }

    // quita ".0" porque Sauce/local a veces esperan "14" no "14.0"
    private String strip(String v) {
        if (v == null) return null;
        return v.endsWith(".0") ? v.substring(0, v.length() - 2) : v;
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }
}
