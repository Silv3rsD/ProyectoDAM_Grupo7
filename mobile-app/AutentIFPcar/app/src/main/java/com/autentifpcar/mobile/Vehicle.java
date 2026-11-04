package com.autentifpcar.mobile;

public class Vehicle {

    private final String vim;
    private final String licensePlate;
    private final String brand;
    private final String model;
    private final int manufacturingYear;
    private final int currentMileage;

    //Constructor
    public Vehicle(String iVim, String iPlate, String iBrand, String iModel, int iManufacturerYear, int iCurrentMileage) {
        this.vim = iVim;
        this.licensePlate = iPlate;
        this.brand = iBrand;
        this.model = iModel;
        this.manufacturingYear = iManufacturerYear;
        this.currentMileage = iCurrentMileage;
    }

    public String getVim() {
        return this.vim;
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
