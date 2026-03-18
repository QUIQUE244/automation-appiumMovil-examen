package com.nttdata.steps;

import com.nttdata.screens.CarritoScreen;
import org.junit.Assert;

public class CarritoSteps {
    CarritoScreen carritoScreen;

    public void hacerClicCarrito() {
        carritoScreen.irAlCarrito();
    }

    public void validarCarrito() {
        String textoActual = carritoScreen.obtenerCantidadDeItems();

        // 3. Assertion: Validar que el carrito NO esté vacío
        Assert.assertTrue("no actualizó. Texto encontrado: " + textoActual, textoActual.matches(".*\\d+.*"));

        System.out.println("Validación exitosa: El carrito muestra -> " + textoActual);
    }
}