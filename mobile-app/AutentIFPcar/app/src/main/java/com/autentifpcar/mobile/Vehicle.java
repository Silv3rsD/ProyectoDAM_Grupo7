package com.autentifpcar.mobile;

public class Vehicle {

    private final String vin;
    private final String licensePlate;
    private final String brand;
    private final String model;
    private final int manufacturingYear;
    private final int currentMileage;

    //Constructor
    public Vehicle(String iVin, String iPlate, String iBrand, String iModel, int iManufacturerYear, int iCurrentMileage) {
        this.vin = iVin;
        this.licensePlate = iPlate;
        this.brand = iBrand;
        this.model = iModel;
        this.manufacturingYear = iManufacturerYear;
        this.currentMileage = iCurrentMileage;
    }

    public String getVin() {
        return this.vin;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    public int getManufacturingYear() {
        return this.manufacturingYear;
    }

    public int getCurrentMileage() {
        return this.currentMileage;
    }

}
