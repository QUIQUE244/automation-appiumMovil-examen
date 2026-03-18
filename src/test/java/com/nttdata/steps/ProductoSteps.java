package com.nttdata.steps;

import com.nttdata.screens.ProductosScreen;
import org.junit.Assert;

public class ProductoSteps {

    ProductosScreen productosScreen;

    public void validarProductos() {
        Assert.assertTrue(productosScreen.validarProductosCargados());
    }


    // 2️⃣ Seleccionar un producto de manera dinámica
    public void seleccionarProducto(String nombreProducto) {
        productosScreen.seleccionarProducto(nombreProducto);
    }

    // 3️⃣ Agregar un producto al carrito con la cantidad indicada
    public void agregarProductoAlCarrito(String nombreProducto, int unidades) {
        productosScreen.agregarProductoAlCarrito(nombreProducto, unidades);


    }



}
