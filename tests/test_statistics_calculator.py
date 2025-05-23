import unittest
import math
from statistics_calculator import StatisticsCalculator # Assuming statistics_calculator.py is in the parent directory or PYTHONPATH
import collections

class TestStatisticsCalculator(unittest.TestCase):
    def setUp(self):
        self.calc = StatisticsCalculator()
        self.epsilon = 1e-9 # Tolerance for floating point comparisons

    # Mean tests
    def test_mean_basic(self):
        self.assertAlmostEqual(self.calc.mean([1, 2, 3, 4, 5]), 3.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.mean([10, 20, 30]), 20.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.mean([-1, 0, 1]), 0.0, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.mean([2.5, 3.5, 4.0]), 10.0/3.0, delta=self.epsilon)

    def test_mean_single_element(self):
        self.assertAlmostEqual(self.calc.mean([5]), 5.0, delta=self.epsilon)

    def test_mean_empty_list(self):
        with self.assertRaises(ValueError):
            self.calc.mean([])

    # Median tests
    def test_median_odd_length(self):
        self.assertEqual(self.calc.median([1, 2, 3, 4, 5]), 3)
        self.assertEqual(self.calc.median([1, 5, 2, 8, 3]), 3) # Unsorted

    def test_median_even_length(self):
        self.assertAlmostEqual(self.calc.median([1, 2, 3, 4, 5, 6]), 3.5, delta=self.epsilon)
        self.assertAlmostEqual(self.calc.median([1, 6, 2, 5, 3, 4]), 3.5, delta=self.epsilon) # Unsorted

    def test_median_single_element(self):
        self.assertEqual(self.calc.median([5]), 5)

    def test_median_empty_list(self):
        with self.assertRaises(ValueError):
            self.calc.median([])

    # Mode tests
    def test_mode_single_mode(self):
        self.assertEqual(sorted(self.calc.mode([1, 2, 2, 3, 4])), [2])

    def test_mode_multiple_modes(self):
        self.assertEqual(sorted(self.calc.mode([1, 2, 2, 3, 3, 4])), [2, 3])

    def test_mode_all_elements_same(self):
        self.assertEqual(sorted(self.calc.mode([2, 2, 2, 2])), [2])

    def test_mode_no_mode_unique_elements(self):
        # Mode returns all elements if all have same frequency (i.e. all unique)
        self.assertEqual(sorted(self.calc.mode([1, 2, 3, 4, 5])), [1, 2, 3, 4, 5])

    def test_mode_single_element(self):
        self.assertEqual(sorted(self.calc.mode([5])), [5])

    def test_mode_empty_list(self):
        with self.assertRaises(ValueError):
            self.calc.mode([])

    # Variance tests
    def test_variance_basic(self):
        data = [1, 2, 3, 4, 5] # mean is 3
        # ( (1-3)^2 + (2-3)^2 + (3-3)^2 + (4-3)^2 + (5-3)^2 ) / (5-1)
        # = ( (-2)^2 + (-1)^2 + 0^2 + 1^2 + 2^2 ) / 4
        # = ( 4 + 1 + 0 + 1 + 4 ) / 4
        # = 10 / 4 = 2.5
        self.assertAlmostEqual(self.calc.variance(data), 2.5, delta=self.epsilon)
        data_float = [2.0, 4.0, 6.0, 8.0] # mean is 5
        # ( (2-5)^2 + (4-5)^2 + (6-5)^2 + (8-5)^2 ) / (4-1)
        # = ( (-3)^2 + (-1)^2 + 1^2 + 3^2 ) / 3
        # = ( 9 + 1 + 1 + 9 ) / 3
        # = 20 / 3
        self.assertAlmostEqual(self.calc.variance(data_float), 20.0/3.0, delta=self.epsilon)

    def test_variance_insufficient_data(self):
        with self.assertRaises(ValueError):
            self.calc.variance([5])
        with self.assertRaises(ValueError):
            self.calc.variance([])

    # Standard Deviation tests
    def test_std_dev_basic(self):
        data = [1, 2, 3, 4, 5]
        self.assertAlmostEqual(self.calc.std_dev(data), math.sqrt(2.5), delta=self.epsilon)
        data_float = [2.0, 4.0, 6.0, 8.0]
        self.assertAlmostEqual(self.calc.std_dev(data_float), math.sqrt(20.0/3.0), delta=self.epsilon)

    def test_std_dev_insufficient_data(self):
        with self.assertRaises(ValueError):
            self.calc.std_dev([5])
        with self.assertRaises(ValueError):
            self.calc.std_dev([])

    # Test inheritance from BaseCalculator (for add method, as an example)
    # This is relevant if StatisticsCalculator is supposed to inherit,
    # but based on the problem description, it's a standalone calculator.
    # If it were inheriting:
    # def test_inherited_add(self):
    #    self.assertEqual(self.calc.add(1, 2), 3)

if __name__ == '__main__':
    unittest.main()
