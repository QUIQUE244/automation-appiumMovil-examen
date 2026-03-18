package com.nttdata.screens;

import io.appium.java_client.pagefactory.AndroidFindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CarritoScreen extends PageObject {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartRL")
    private WebElement botonVerCarrito;

    public void irAlCarrito() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(botonVerCarrito));
        botonVerCarrito.click();
    }

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/itemsTV")
    private WebElement lblTotalItems;

    public String obtenerCantidadDeItems() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        // Esperamos a que el texto sea visible para que el test no falle por lentitud
        return wait.until(ExpectedConditions.visibilityOf(lblTotalItems)).getText();
    }
}
