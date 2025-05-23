package com.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class StatisticsCalculatorImplTest {
    private final StatisticsCalculator calculator = new StatisticsCalculatorImpl();
    private static final double DELTA = 0.000000001; // Precision for double comparisons

    // Mean Tests
    @Test
    void testMean() {
        assertEquals(3.0, calculator.mean(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)), DELTA);
        assertEquals(20.0, calculator.mean(Arrays.asList(10.0, 20.0, 30.0)), DELTA);
        assertEquals(0.0, calculator.mean(Arrays.asList(-1.0, 0.0, 1.0)), DELTA);
        assertEquals(3.3333333333333335, calculator.mean(Arrays.asList(2.5, 3.5, 4.0)), DELTA);
    }

    @Test
    void testMeanSingleElement() {
        assertEquals(5.0, calculator.mean(Collections.singletonList(5.0)), DELTA);
    }

    @Test
    void testMeanEmptyList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.mean(new ArrayList<>());
        });
        assertEquals("Data list cannot be null or empty for mean calculation.", exception.getMessage());
    }

    @Test
    void testMeanNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.mean(null);
        });
        assertEquals("Data list cannot be null or empty for mean calculation.", exception.getMessage());
    }

    // Median Tests
    @Test
    void testMedianOddLength() {
        assertEquals(3.0, calculator.median(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)), DELTA);
        assertEquals(3.0, calculator.median(Arrays.asList(5.0, 1.0, 4.0, 2.0, 3.0)), DELTA); // Unsorted
    }

    @Test
    void testMedianEvenLength() {
        assertEquals(3.5, calculator.median(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)), DELTA);
        assertEquals(3.5, calculator.median(Arrays.asList(6.0, 1.0, 5.0, 2.0, 4.0, 3.0)), DELTA); // Unsorted
    }

    @Test
    void testMedianSingleElement() {
        assertEquals(5.0, calculator.median(Collections.singletonList(5.0)), DELTA);
    }

    @Test
    void testMedianEmptyList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.median(new ArrayList<>());
        });
        assertEquals("Data list cannot be null or empty for median calculation.", exception.getMessage());
    }
    
    @Test
    void testMedianNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.median(null);
        });
        assertEquals("Data list cannot be null or empty for median calculation.", exception.getMessage());
    }

    // Mode Tests
    @Test
    void testModeSingleMode() {
        List<Double> expected = Collections.singletonList(2.0);
        assertEquals(expected, calculator.mode(Arrays.asList(1.0, 2.0, 2.0, 3.0, 4.0)));
    }

    @Test
    void testModeMultipleModes() {
        List<Double> expected = Arrays.asList(2.0, 3.0); // Sorted
        List<Double> actual = calculator.mode(Arrays.asList(1.0, 2.0, 2.0, 3.0, 3.0, 4.0));
        Collections.sort(actual); // Ensure order for comparison
        assertEquals(expected, actual);
    }

    @Test
    void testModeAllElementsSame() {
        List<Double> expected = Collections.singletonList(2.0);
        assertEquals(expected, calculator.mode(Arrays.asList(2.0, 2.0, 2.0, 2.0)));
    }

    @Test
    void testModeNoDistinctModeUniqueElements() {
        List<Double> data = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<Double> actual = calculator.mode(data);
        Collections.sort(actual); // Ensure order for comparison
        assertEquals(data, actual); // All elements are modes
    }
    
    @Test
    void testModeMixedOrderMultipleModes() {
        List<Double> data = Arrays.asList(5.0, 1.0, 5.0, 2.0, 1.0, 5.0, 2.0, 2.0); // 5s:3, 1s:2, 2s:3
        List<Double> expected = Arrays.asList(2.0, 5.0);
        List<Double> actual = calculator.mode(data);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }


    @Test
    void testModeSingleElement() {
        List<Double> expected = Collections.singletonList(5.0);
        assertEquals(expected, calculator.mode(Collections.singletonList(5.0)));
    }

    @Test
    void testModeEmptyList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.mode(new ArrayList<>());
        });
        assertEquals("Data list cannot be null or empty for mode calculation.", exception.getMessage());
    }

    @Test
    void testModeNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.mode(null);
        });
        assertEquals("Data list cannot be null or empty for mode calculation.", exception.getMessage());
    }


    // Variance Tests
    @Test
    void testVariance() {
        // ( (1-3)^2 + (2-3)^2 + (3-3)^2 + (4-3)^2 + (5-3)^2 ) / (5-1) = 2.5
        assertEquals(2.5, calculator.variance(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)), DELTA);
        // ( (2-5)^2 + (4-5)^2 + (6-5)^2 + (8-5)^2 ) / (4-1) = 20/3
        assertEquals(20.0/3.0, calculator.variance(Arrays.asList(2.0, 4.0, 6.0, 8.0)), DELTA);
    }

    @Test
    void testVarianceTwoElements() {
        // ( (1-1.5)^2 + (2-1.5)^2 ) / (2-1) = ( (-0.5)^2 + (0.5)^2 ) / 1 = (0.25 + 0.25) / 1 = 0.5
        assertEquals(0.5, calculator.variance(Arrays.asList(1.0, 2.0)), DELTA);
    }
    
    @Test
    void testVarianceInsufficientDataSingle() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.variance(Collections.singletonList(5.0));
        });
        assertEquals("Data list must contain at least two elements for variance calculation.", exception.getMessage());
    }

    @Test
    void testVarianceInsufficientDataEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.variance(new ArrayList<>());
        });
        assertEquals("Data list must contain at least two elements for variance calculation.", exception.getMessage());
    }
    
    @Test
    void testVarianceNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.variance(null);
        });
        assertEquals("Data list must contain at least two elements for variance calculation.", exception.getMessage());
    }

    // Standard Deviation Tests
    @Test
    void testStandardDeviation() {
        assertEquals(Math.sqrt(2.5), calculator.standardDeviation(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)), DELTA);
        assertEquals(Math.sqrt(20.0/3.0), calculator.standardDeviation(Arrays.asList(2.0, 4.0, 6.0, 8.0)), DELTA);
    }

    @Test
    void testStandardDeviationTwoElements() {
        assertEquals(Math.sqrt(0.5), calculator.standardDeviation(Arrays.asList(1.0, 2.0)), DELTA);
    }

    @Test
    void testStandardDeviationInsufficientDataSingle() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.standardDeviation(Collections.singletonList(5.0));
        });
        assertEquals("Data list must contain at least two elements for standard deviation calculation.", exception.getMessage());
    }

    @Test
    void testStandardDeviationInsufficientDataEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.standardDeviation(new ArrayList<>());
        });
        assertEquals("Data list must contain at least two elements for standard deviation calculation.", exception.getMessage());
    }

    @Test
    void testStandardDeviationNullList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.standardDeviation(null);
        });
        assertEquals("Data list must contain at least two elements for standard deviation calculation.", exception.getMessage());
    }
}
