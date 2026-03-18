package com.nttdata.screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductosScreen extends PageObject {

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@content-desc=\"Displays all products of catalog\"]/android.view.ViewGroup")
    private List<WebElement> listaProductos;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/plusIV")
    private WebElement botonMas;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement botonAddToCart;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement nombreProductoDetalle;

    //validar que haya los productos
    public boolean validarProductosCargados() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfAllElements(listaProductos));

        System.out.println("Cantidad productos: " + listaProductos.size());

        return listaProductos.size() > 0;
    }



    //hacer click en los productos selccionados

    public void seleccionarProducto(String nombreProducto) {
        WebElement producto = getDriver().findElement(
                AppiumBy.androidUIAutomator("new UiSelector().description(\"" + nombreProducto + "\")")
        );

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(producto));



        producto.click();
    }

    // agregar producto y hacer click en el bton añadir al carrito
    public void agregarProductoAlCarrito(String nombreProducto, int unidades) {
        // 3️⃣ hacer scroll en detalle (por si el botón + y Add to Cart no se ven)
        getDriver().findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().description(\"Increase item quantity\"))"
        ));


        //  agregar las unidades necesarias (ya que la app empieza con 1 por defecto)
        for (int i = 1; i < unidades; i++) {
            botonMas.click();
        }

        //  click en Add to Cart
        botonAddToCart.click();
    }







}
