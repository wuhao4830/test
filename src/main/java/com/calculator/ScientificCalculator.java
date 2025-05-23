package com.calculator;

public interface ScientificCalculator extends BaseCalculator {
    double power(double base, double exponent);
    double sqrt(double n) throws IllegalArgumentException;
    double log10(double n) throws IllegalArgumentException;
    double ln(double n) throws IllegalArgumentException;
    double sin(double angleRadians);
    double cos(double angleRadians);
    double tan(double angleRadians);
}
