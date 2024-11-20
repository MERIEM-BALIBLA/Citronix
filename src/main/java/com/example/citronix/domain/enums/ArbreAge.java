package com.example.citronix.domain.enums;

public enum ArbreAge {
    YOUNG(2.5),
    MATURE(12.0),
    OLD(20.0);

    private double v;

    ArbreAge(double v) {
        this.v = v;
    }

    public double getV() {
        return v;
    }
}
