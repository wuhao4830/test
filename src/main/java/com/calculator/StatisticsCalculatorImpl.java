package com.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCalculatorImpl implements StatisticsCalculator {

    @Override
    public double mean(List<Double> data) throws IllegalArgumentException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be null or empty for mean calculation.");
        }
        double sum = 0;
        for (double val : data) {
            sum += val;
        }
        return sum / data.size();
    }

    @Override
    public double median(List<Double> data) throws IllegalArgumentException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be null or empty for median calculation.");
        }
        List<Double> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData);
        int size = sortedData.size();
        if (size % 2 == 0) {
            return (sortedData.get(size / 2 - 1) + sortedData.get(size / 2)) / 2.0;
        } else {
            return sortedData.get(size / 2);
        }
    }

    @Override
    public List<Double> mode(List<Double> data) throws IllegalArgumentException {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Data list cannot be null or empty for mode calculation.");
        }
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double val : data) {
            frequencyMap.put(val, frequencyMap.getOrDefault(val, 0) + 1);
        }

        int maxFrequency = 0;
        boolean allUnique = true;
        if (!frequencyMap.isEmpty()) {
            maxFrequency = Collections.max(frequencyMap.values());
            if (maxFrequency > 1) {
                allUnique = false;
            }
        }


        List<Double> modes = new ArrayList<>();
        // If all elements are unique (maxFrequency is 1) and there's more than one element,
        // or if the list is not empty and all elements are the same (maxFrequency equals data.size() and size > 1 implies not all unique)
        // the original Python version returned all elements as modes if all frequencies are 1.
        // Here, we adjust to return elements that have the max frequency.
        // If all elements are unique (maxFrequency == 1 and data.size() > 1), it means all elements are modes.
        // The provided Java code has a condition: `if (maxFrequency == 1 && data.size() > 1)` returns empty list.
        // Let's stick to the behavior of returning elements with max frequency.
        // If all elements are unique and data.size() > 1, then maxFrequency will be 1.
        // All elements will be added to modes.

        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFrequency) {
                modes.add(entry.getKey());
            }
        }
        
        // If all elements were unique and list was not empty, all elements are modes.
        // If the list was empty, it's handled by the initial check.
        // If the list has one element, that element is the mode.
        // The current logic correctly handles these cases by adding all elements with maxFrequency.
        // If all are unique (maxFrequency = 1), all are added.
        
        Collections.sort(modes); // Optional: return modes in sorted order
        return modes;
    }

    @Override
    public double variance(List<Double> data) throws IllegalArgumentException {
        if (data == null || data.size() < 2) { // Variance requires at least two data points for sample variance
            throw new IllegalArgumentException("Data list must contain at least two elements for variance calculation.");
        }
        double mean = mean(data);
        double temp = 0;
        for (double val : data) {
            temp += (val - mean) * (val - mean);
        }
        return temp / (data.size() - 1); // Sample variance
    }

    @Override
    public double standardDeviation(List<Double> data) throws IllegalArgumentException {
        if (data == null || data.size() < 2) { // Std dev requires at least two data points
            throw new IllegalArgumentException("Data list must contain at least two elements for standard deviation calculation.");
        }
        return Math.sqrt(variance(data));
    }
}
