package com.calculator;

import java.util.List;
// import java.util.Map; // Not needed if mode returns List<Double>

public interface StatisticsCalculator {
    double mean(List<Double> data) throws IllegalArgumentException;
    double median(List<Double> data) throws IllegalArgumentException;
    List<Double> mode(List<Double> data) throws IllegalArgumentException; // Assuming mode can return multiple values
    double variance(List<Double> data) throws IllegalArgumentException;
    double standardDeviation(List<Double> data) throws IllegalArgumentException;
}
