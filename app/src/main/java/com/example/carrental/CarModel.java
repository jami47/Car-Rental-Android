package com.example.carrental;

public class CarModel {
    String carName;
    String carPrice;
    //Nicherta pore lagaisi
    String avgrating = "4.2";

    public CarModel() {
    }

    public CarModel(String carName, String carPrice) {
        this.carName = carName;
        this.carPrice = carPrice;
    }

    //Nicherta pore lagaisi
    public CarModel(String avgrating, String carName, String carPrice) {
        this.carName = carName;
        this.carPrice = carPrice;
        this.avgrating = avgrating;
    }
}
