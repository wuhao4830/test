package com.calculator;

public interface ProgrammerCalculator extends BaseCalculator {
    String toBinary(int n);
    String toOctal(int n);
    String toHexadecimal(int n);
    int bitwiseAnd(int a, int b);
    int bitwiseOr(int a, int b);
    int bitwiseXor(int a, int b);
    int bitwiseNot(int n);
}
