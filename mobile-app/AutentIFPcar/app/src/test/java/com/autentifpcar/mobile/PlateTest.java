package com.autentifpcar.mobile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlateTest {
    @Test public void normalizesInputBeforeSearching() {
        assertEquals("1234ABC", Plate.normalize(" 1234 abc "));
    }

    @Test public void rejectsInputThatCannotBeUsedInTheApiPath() {
        assertTrue(Plate.isValid("1234ABC"));
        assertFalse(Plate.isValid("X"));
        assertFalse(Plate.isValid("1234/ABC"));
    }
}
