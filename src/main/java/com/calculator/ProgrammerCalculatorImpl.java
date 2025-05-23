package com.calculator;

public class ProgrammerCalculatorImpl extends BaseCalculatorImpl implements ProgrammerCalculator {

    @Override
    public String toBinary(int n) {
        return Integer.toBinaryString(n);
    }

    @Override
    public String toOctal(int n) {
        return Integer.toOctalString(n);
    }

    @Override
    public String toHexadecimal(int n) {
        return Integer.toHexString(n);
    }

    @Override
    public int bitwiseAnd(int a, int b) {
        return a & b;
    }

    @Override
    public int bitwiseOr(int a, int b) {
        return a | b;
    }

    @Override
    public int bitwiseXor(int a, int b) {
        return a ^ b;
    }

    @Override
    public int bitwiseNot(int n) {
        return ~n;
    }
}
