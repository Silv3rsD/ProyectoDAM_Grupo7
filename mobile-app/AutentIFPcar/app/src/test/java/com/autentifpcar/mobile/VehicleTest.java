
package com.autentifpcar.mobile;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class VehicleTest {

    @Test
    public void testCrearVehiculo() {

        Vehicle coche = new Vehicle("WVWZZZ1JZXW000001", "1234ABC", "Toyota", "Corolla", 2020, 68500);

        assertEquals("WVWZZZ1JZXW000001", coche.getVin());
        assertEquals("1234ABC", coche.getLicensePlate());
        assertEquals("Toyota", coche.getBrand());
        assertEquals("Corolla", coche.getModel());
        assertEquals(2020, coche.getManufacturingYear());
        assertEquals(68500, coche.getCurrentMileage());
    }
}
