package com.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProgrammerCalculatorImplTest {
    private final ProgrammerCalculator calculator = new ProgrammerCalculatorImpl();
    private static final double DELTA = 0.00001; // For inherited double methods

    @Test
    void testToBinary() {
        assertEquals("1010", calculator.toBinary(10));
        assertEquals("0", calculator.toBinary(0));
        assertEquals("11111111", calculator.toBinary(255));
        assertEquals("-1010", calculator.toBinary(-10)); // Integer.toBinaryString behavior
    }

    @Test
    void testToOctal() {
        assertEquals("12", calculator.toOctal(10));
        assertEquals("0", calculator.toOctal(0));
        assertEquals("377", calculator.toOctal(255));
        assertEquals("-12", calculator.toOctal(-10)); // Integer.toOctalString behavior
    }

    @Test
    void testToHexadecimal() {
        assertEquals("a", calculator.toHexadecimal(10));
        assertEquals("0", calculator.toHexadecimal(0));
        assertEquals("ff", calculator.toHexadecimal(255));
        assertEquals("-a", calculator.toHexadecimal(-10)); // Integer.toHexString behavior
    }

    @Test
    void testBitwiseAnd() {
        assertEquals(1, calculator.bitwiseAnd(5, 3));  // 101 & 011 = 001
        assertEquals(0, calculator.bitwiseAnd(0, 15));
        assertEquals(4, calculator.bitwiseAnd(5, 4));  // 101 & 100 = 100
        assertEquals(0, calculator.bitwiseAnd(10, -5)); // Example with negative
    }

    @Test
    void testBitwiseOr() {
        assertEquals(7, calculator.bitwiseOr(5, 3));   // 101 | 011 = 111
        assertEquals(15, calculator.bitwiseOr(0, 15));
        assertEquals(5, calculator.bitwiseOr(5, 4));   // 101 | 100 = 101
        assertEquals(-1, calculator.bitwiseOr(10, -5)); // Example with negative
    }

    @Test
    void testBitwiseXor() {
        assertEquals(6, calculator.bitwiseXor(5, 3));  // 101 ^ 011 = 110
        assertEquals(15, calculator.bitwiseXor(0, 15));
        assertEquals(1, calculator.bitwiseXor(5, 4));  // 101 ^ 100 = 001
        assertEquals(-11, calculator.bitwiseXor(10, -5)); // Example with negative
    }

    @Test
    void testBitwiseNot() {
        assertEquals(-6, calculator.bitwiseNot(5));   // ~0...0101 = 1...1010
        assertEquals(-1, calculator.bitwiseNot(0));
        assertEquals(4, calculator.bitwiseNot(-5));
    }

    // Test inherited methods from BaseCalculatorImpl
    @Test
    void testInheritedAdd() {
        // ProgrammerCalculator extends BaseCalculatorImpl, so it has add, subtract etc.
        // These methods take doubles.
        assertEquals(5.0, calculator.add(2.0, 3.0), DELTA);
    }

    @Test
    void testInheritedDivide() {
        assertEquals(2.0, calculator.divide(4.0, 2.0), DELTA);
    }

    @Test
    void testInheritedDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(1.0, 0.0);
        });
    }
}
