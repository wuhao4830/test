package com.calculator;

import java.lang.Math; // Math is already in java.lang, explicit import not strictly needed but okay

public class ScientificCalculatorImpl extends BaseCalculatorImpl implements ScientificCalculator {

    @Override
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    @Override
    public double sqrt(double n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of a negative number.");
        }
        return Math.sqrt(n);
    }

    @Override
    public double log10(double n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException("Argument for log10 must be positive.");
        }
        return Math.log10(n);
    }

    @Override
    public double ln(double n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException("Argument for ln must be positive.");
        }
        return Math.log(n); // Math.log is natural logarithm (base e)
    }

    @Override
    public double sin(double angleRadians) {
        return Math.sin(angleRadians);
    }

    @Override
    public double cos(double angleRadians) {
        return Math.cos(angleRadians);
    }

    @Override
    public double tan(double angleRadians) {
        return Math.tan(angleRadians);
    }
}
