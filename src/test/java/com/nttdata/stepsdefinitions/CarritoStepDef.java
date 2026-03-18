package com.nttdata.stepsdefinitions;

import com.nttdata.steps.AplicacionSteps;
import com.nttdata.steps.CarritoSteps;
import com.nttdata.steps.ProductoSteps;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class CarritoStepDef {
    @Steps
    AplicacionSteps aplicacionSteps;
    @Steps
    ProductoSteps productoSteps;

    @Steps
    CarritoSteps carritoSteps ;

    @Given("estoy en la aplicación de SauceLabs")
    public void estoyEnLaAplicaciónDeSauceLabs() {
        aplicacionSteps.validarPantallaLogo();

    }

    @And("valido que carguen correctamente los productos en la galeria")
    public void validoQueCarguenCorrectamenteLosProductosEnLaGaleria() {
        productoSteps.validarProductos();

    }

    @When("agrego {int} del siguiente producto {string}")
    public void agregoUNIDADESDelSiguienteProducto(int unidades, String producto) {
        productoSteps.seleccionarProducto(producto);
        productoSteps.agregarProductoAlCarrito(producto , unidades);


    }

    @Then("valido el carrito de compra actualice correctamente")
    public void validoElCarritoDeCompraActualiceCorrectamente() {
        carritoSteps.hacerClicCarrito();
        carritoSteps.validarCarrito();

    }
}
