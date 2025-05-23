package com.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScientificCalculatorImplTest {
    private final ScientificCalculator calculator = new ScientificCalculatorImpl();
    private static final double DELTA = 0.000000001; // Precision for double comparisons

    @Test
    void testPower() {
        assertEquals(8.0, calculator.power(2.0, 3.0), DELTA);
        assertEquals(1.0, calculator.power(5.0, 0.0), DELTA);
        assertEquals(0.0, calculator.power(0.0, 5.0), DELTA); // 0^positive is 0
        assertEquals(1.0, calculator.power(0.0, 0.0), DELTA); // 0^0 is conventionally 1 in this context
        assertEquals(0.25, calculator.power(2.0, -2.0), DELTA);
        assertEquals(2.0, calculator.power(4.0, 0.5), DELTA);
        assertEquals(Math.pow(2.5, 1.5), calculator.power(2.5, 1.5), DELTA);
    }

    @Test
    void testSqrt() {
        assertEquals(2.0, calculator.sqrt(4.0), DELTA);
        assertEquals(0.0, calculator.sqrt(0.0), DELTA);
        assertEquals(Math.sqrt(2.0), calculator.sqrt(2.0), DELTA);
        assertEquals(Math.sqrt(98765.4321), calculator.sqrt(98765.4321), DELTA);
    }

    @Test
    void testSqrtNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.sqrt(-1.0);
        });
        assertEquals("Cannot calculate square root of a negative number.", exception.getMessage());
    }

    @Test
    void testLog10() {
        assertEquals(2.0, calculator.log10(100.0), DELTA);
        assertEquals(0.0, calculator.log10(1.0), DELTA);
        assertEquals(-1.0, calculator.log10(0.1), DELTA);
        assertEquals(Math.log10(12345.0), calculator.log10(12345.0), DELTA);
    }

    @Test
    void testLog10NonPositive() {
        Exception exceptionZero = assertThrows(IllegalArgumentException.class, () -> {
            calculator.log10(0.0);
        });
        assertEquals("Argument for log10 must be positive.", exceptionZero.getMessage());

        Exception exceptionNegative = assertThrows(IllegalArgumentException.class, () -> {
            calculator.log10(-10.0);
        });
        assertEquals("Argument for log10 must be positive.", exceptionNegative.getMessage());
    }

    @Test
    void testLn() {
        assertEquals(1.0, calculator.ln(Math.E), DELTA);
        assertEquals(0.0, calculator.ln(1.0), DELTA);
        assertEquals(Math.log(3.5), calculator.ln(3.5), DELTA); // Math.log is natural log
        assertEquals(Math.log(12345.0), calculator.ln(12345.0), DELTA);
    }

    @Test
    void testLnNonPositive() {
        Exception exceptionZero = assertThrows(IllegalArgumentException.class, () -> {
            calculator.ln(0.0);
        });
        assertEquals("Argument for ln must be positive.", exceptionZero.getMessage());

        Exception exceptionNegative = assertThrows(IllegalArgumentException.class, () -> {
            calculator.ln(-1.0);
        });
        assertEquals("Argument for ln must be positive.", exceptionNegative.getMessage());
    }

    @Test
    void testSin() {
        assertEquals(Math.sin(0.0), calculator.sin(0.0), DELTA);
        assertEquals(Math.sin(Math.PI / 2), calculator.sin(Math.PI / 2), DELTA);
        assertEquals(Math.sin(Math.PI), calculator.sin(Math.PI), DELTA);
        assertEquals(Math.sin(Math.PI / 6), calculator.sin(Math.PI / 6), DELTA);
        assertEquals(Math.sin(1.234), calculator.sin(1.234), DELTA);
    }

    @Test
    void testCos() {
        assertEquals(Math.cos(0.0), calculator.cos(0.0), DELTA);
        assertEquals(Math.cos(Math.PI / 2), calculator.cos(Math.PI / 2), DELTA);
        assertEquals(Math.cos(Math.PI), calculator.cos(Math.PI), DELTA);
        assertEquals(Math.cos(Math.PI / 3), calculator.cos(Math.PI / 3), DELTA);
        assertEquals(Math.cos(1.234), calculator.cos(1.234), DELTA);
    }

    @Test
    void testTan() {
        assertEquals(Math.tan(0.0), calculator.tan(0.0), DELTA);
        assertEquals(Math.tan(Math.PI / 4), calculator.tan(Math.PI / 4), DELTA);
        assertEquals(Math.tan(1.234), calculator.tan(1.234), DELTA);
        // Test case for tan(PI/2) would be problematic as it's undefined.
        // Values close to PI/2 result in very large numbers.
        // Example: tan(Math.PI/2 - 0.00001) should be a large positive number.
        // Example: tan(Math.PI/2 + 0.00001) should be a large negative number.
        // The implementation uses Math.tan which handles these cases correctly.
    }

    // Test inherited methods from BaseCalculatorImpl
    @Test
    void testInheritedAdd() {
        assertEquals(5.0, calculator.add(2.0, 3.0), DELTA);
    }

    @Test
    void testInheritedDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(1.0, 0.0);
        });
    }
}
