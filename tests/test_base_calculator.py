import unittest
from base_calculator import BaseCalculator

class TestBaseCalculator(unittest.TestCase):
    def setUp(self):
        self.calc = BaseCalculator()

    def test_add(self):
        self.assertEqual(self.calc.add(1, 2), 3)
        self.assertEqual(self.calc.add(-1, 1), 0)
        self.assertEqual(self.calc.add(0, 0), 0)
        self.assertEqual(self.calc.add(-1, -1), -2)
        self.assertEqual(self.calc.add(1.5, 2.5), 4.0)

    def test_subtract(self):
        self.assertEqual(self.calc.subtract(5, 3), 2)
        self.assertEqual(self.calc.subtract(3, 5), -2)
        self.assertEqual(self.calc.subtract(0, 0), 0)
        self.assertEqual(self.calc.subtract(5, 0), 5)
        self.assertEqual(self.calc.subtract(0, 5), -5)
        self.assertEqual(self.calc.subtract(-1, -1), 0)
        self.assertEqual(self.calc.subtract(2.5, 1.5), 1.0)

    def test_multiply(self):
        self.assertEqual(self.calc.multiply(3, 4), 12)
        self.assertEqual(self.calc.multiply(-1, 5), -5)
        self.assertEqual(self.calc.multiply(0, 100), 0)
        self.assertEqual(self.calc.multiply(-2, -3), 6)
        self.assertEqual(self.calc.multiply(1.5, 2), 3.0)

    def test_divide(self):
        self.assertEqual(self.calc.divide(8, 2), 4)
        self.assertEqual(self.calc.divide(5, 2), 2.5)
        self.assertEqual(self.calc.divide(0, 5), 0)
        self.assertEqual(self.calc.divide(-10, 2), -5)
        self.assertEqual(self.calc.divide(7.5, 2.5), 3.0)

    def test_divide_by_zero(self):
        with self.assertRaises(ValueError):
            self.calc.divide(5, 0)
        with self.assertRaises(ValueError):
            self.calc.divide(0, 0)

if __name__ == '__main__':
    unittest.main()
