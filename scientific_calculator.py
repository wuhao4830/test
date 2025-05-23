import math
from base_calculator import BaseCalculator

class ScientificCalculator(BaseCalculator):
    def power(self, base, exponent):
        return base ** exponent

    def sqrt(self, n):
        if n < 0:
            raise ValueError("Cannot calculate the square root of a negative number.")
        return math.sqrt(n)

    def log10(self, n):
        if n <= 0:
            raise ValueError("Cannot calculate base-10 logarithm for non-positive numbers.")
        return math.log10(n)

    def ln(self, n):
        if n <= 0:
            raise ValueError("Cannot calculate natural logarithm for non-positive numbers.")
        return math.log(n)

    def sin(self, angle_radians):
        return math.sin(angle_radians)

    def cos(self, angle_radians):
        return math.cos(angle_radians)

    def tan(self, angle_radians):
        return math.tan(angle_radians)
