package com.nttdata.steps;

import com.nttdata.screens.AplicacionScreen;
import org.junit.Assert;

public class AplicacionSteps {

    AplicacionScreen aplicacionScreen;

    public void validarPantallaLogo() {
        Assert.assertTrue(aplicacionScreen.validarPantallaLogo());
    }
}
