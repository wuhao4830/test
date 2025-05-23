package com.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseCalculatorImplTest {
    private final BaseCalculator calculator = new BaseCalculatorImpl();
    private static final double DELTA = 0.00001; // Precision for double comparisons

    @Test
    void testAdd() {
        assertEquals(5.0, calculator.add(2.0, 3.0), DELTA);
        assertEquals(-1.0, calculator.add(-2.0, 1.0), DELTA);
        assertEquals(0.0, calculator.add(0.0, 0.0), DELTA);
        assertEquals(-5.0, calculator.add(-2.0, -3.0), DELTA);
        assertEquals(3.7, calculator.add(1.2, 2.5), DELTA);
    }

    @Test
    void testSubtract() {
        assertEquals(-1.0, calculator.subtract(2.0, 3.0), DELTA);
        assertEquals(-3.0, calculator.subtract(-2.0, 1.0), DELTA);
        assertEquals(0.0, calculator.subtract(0.0, 0.0), DELTA);
        assertEquals(1.0, calculator.subtract(-2.0, -3.0), DELTA);
        assertEquals(5.0, calculator.subtract(5.0, 0.0), DELTA);
        assertEquals(-1.3, calculator.subtract(1.2, 2.5), DELTA);
    }

    @Test
    void testMultiply() {
        assertEquals(6.0, calculator.multiply(2.0, 3.0), DELTA);
        assertEquals(-2.0, calculator.multiply(-2.0, 1.0), DELTA);
        assertEquals(0.0, calculator.multiply(0.0, 5.0), DELTA);
        assertEquals(0.0, calculator.multiply(5.0, 0.0), DELTA);
        assertEquals(6.0, calculator.multiply(-2.0, -3.0), DELTA);
        assertEquals(3.0, calculator.multiply(1.2, 2.5), DELTA);
    }

    @Test
    void testDivide() {
        assertEquals(2.0, calculator.divide(6.0, 3.0), DELTA);
        assertEquals(-2.0, calculator.divide(-6.0, 3.0), DELTA);
        assertEquals(0.0, calculator.divide(0.0, 5.0), DELTA);
        assertEquals(2.5, calculator.divide(5.0, 2.0), DELTA);
        assertEquals(0.48, calculator.divide(1.2, 2.5), DELTA);
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(1.0, 0.0);
        });
        assertEquals("Cannot divide by zero.", exception.getMessage());
    }

    @Test
    void testDivideZeroByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(0.0, 0.0);
        });
        assertEquals("Cannot divide by zero.", exception.getMessage());
    }
}
