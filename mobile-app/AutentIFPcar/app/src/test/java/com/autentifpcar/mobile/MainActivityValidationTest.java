package com.autentifpcar.mobile;

import static org.junit.Assert.*;
import org.junit.Test;

public class MainActivityValidationTest {

    @Test
    public void normalizaMatricula() {

        String resultado = MainActivity.normalizePlate(
                " 1234 abc "
        );

        assertEquals("1234ABC", resultado);
    }

    @Test
    public void compruebaMatriculaValida() {

        assertTrue(
                MainActivity.isValidPlate("1234ABC")
        );

        assertFalse(
                MainActivity.isValidPlate("X")
        );

        assertFalse(
                MainActivity.isValidPlate("1234/ABC")
        );
    }
}
