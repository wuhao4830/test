import unittest
import math
from scientific_calculator import ScientificCalculator # Assuming scientific_calculator.py is in the parent directory or PYTHONPATH

class TestScientificCalculator(unittest.TestCase):
    def setUp(self):
        self.calc = ScientificCalculator()
        self.epsilon = 1e-9 # Tolerance for floating point comparisons

    def test_power(self):
        self.assertEqual(self.calc.power(2, 3), 8)
        self.assertEqual(self.calc.power(5, 0), 1)
        self.assertEqual(self.calc.power(0, 5), 0)
        self.assertAlmostEqual(self.calc.power(2, -2), 0.25)
        self.assertAlmostEqual(self.calc.power(4, 0.5), 2.0)
        self.assertAlmostEqual(self.calc.power(2.5, 1.5), 2.5**1.5, delta=self.epsilon)


    def test_sqrt(self):
        self.assertAlmostEqual(self.calc.sqrt(4), 2.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.sqrt(0), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.sqrt(2), math.sqrt(2), delta=self.epsilon)
        self.assertAlmostEqual(self.calc.sqrt(98765.4321), math.sqrt(98765.4321), delta=self.epsilon)

    def test_sqrt_negative(self):
        with self.assertRaises(ValueError):
            self.calc.sqrt(-1)
        with self.assertRaises(ValueError):
            self.calc.sqrt(-100)

    def test_log10(self):
        self.assertAlmostEqual(self.calc.log10(100), 2.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.log10(1), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.log10(0.1), -1.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.log10(12345), math.log10(12345), delta=self.epsilon)

    def test_log10_non_positive(self):
        with self.assertRaises(ValueError):
            self.calc.log10(0)
        with self.assertRaises(ValueError):
            self.calc.log10(-10)

    def test_ln(self):
        self.assertAlmostEqual(self.calc.ln(math.e), 1.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.ln(1), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.ln(math.exp(3.5)), 3.5, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.ln(12345), math.log(12345), delta=self.epsilon)


    def test_ln_non_positive(self):
        with self.assertRaises(ValueError):
            self.calc.ln(0)
        with self.assertRaises(ValueError):
            self.calc.ln(-1)

    def test_sin(self):
        self.assertAlmostEqual(self.calc.sin(0), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.sin(math.pi / 2), 1.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.sin(math.pi), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.sin(math.pi / 6), 0.5, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.sin(1.234), math.sin(1.234), delta=self.epsilon)

    def test_cos(self):
        self.assertAlmostEqual(self.calc.cos(0), 1.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.cos(math.pi / 2), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.cos(math.pi), -1.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.cos(math.pi / 3), 0.5, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.cos(1.234), math.cos(1.234), delta=self.epsilon)

    def test_tan(self):
        self.assertAlmostEqual(self.calc.tan(0), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.tan(math.pi / 4), 1.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.tan(1.234), math.tan(1.234), delta=self.epsilon)
        # Tan(pi/2) is undefined (approaches infinity), so we test a value close to it
        # and expect a large result, or handle potential OverflowError if the implementation raises it.
        # For now, let's test a value where tan is defined and large.
        self.assertAlmostEqual(self.calc.tan(math.atan(1000)), 1000, delta=self.epsilon)


if __name__ == '__main__':
    unittest.main()
