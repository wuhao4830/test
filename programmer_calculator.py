from base_calculator import BaseCalculator

class ProgrammerCalculator(BaseCalculator):
    def to_binary(self, n):
        if not isinstance(n, int):
            raise TypeError("Input must be an integer.")
        return bin(n)

    def to_octal(self, n):
        if not isinstance(n, int):
            raise TypeError("Input must be an integer.")
        return oct(n)

    def to_hexadecimal(self, n):
        if not isinstance(n, int):
            raise TypeError("Input must be an integer.")
        return hex(n)

    def bitwise_and(self, a, b):
        if not (isinstance(a, int) and isinstance(b, int)):
            raise TypeError("Inputs must be integers.")
        return a & b

    def bitwise_or(self, a, b):
        if not (isinstance(a, int) and isinstance(b, int)):
            raise TypeError("Inputs must be integers.")
        return a | b

    def bitwise_xor(self, a, b):
        if not (isinstance(a, int) and isinstance(b, int)):
            raise TypeError("Inputs must be integers.")
        return a ^ b

    def bitwise_not(self, n):
        if not isinstance(n, int):
            raise TypeError("Input must be an integer.")
        return ~n
