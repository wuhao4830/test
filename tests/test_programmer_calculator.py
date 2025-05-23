import unittest
from programmer_calculator import ProgrammerCalculator # Assuming programmer_calculator.py is in the parent directory or PYTHONPATH

class TestProgrammerCalculator(unittest.TestCase):
    def setUp(self):
        self.calc = ProgrammerCalculator()

    def test_to_binary(self):
        self.assertEqual(self.calc.to_binary(10), bin(10))
        self.assertEqual(self.calc.to_binary(0), bin(0))
        self.assertEqual(self.calc.to_binary(255), bin(255))
        self.assertEqual(self.calc.to_binary(-10), bin(-10)) # bin() handles negative numbers

    def test_to_binary_type_error(self):
        with self.assertRaises(TypeError):
            self.calc.to_binary(10.5)
        with self.assertRaises(TypeError):
            self.calc.to_binary("10")

    def test_to_octal(self):
        self.assertEqual(self.calc.to_octal(10), oct(10))
        self.assertEqual(self.calc.to_octal(0), oct(0))
        self.assertEqual(self.calc.to_octal(255), oct(255))
        self.assertEqual(self.calc.to_octal(-10), oct(-10)) # oct() handles negative numbers

    def test_to_octal_type_error(self):
        with self.assertRaises(TypeError):
            self.calc.to_octal(8.0)
        with self.assertRaises(TypeError):
            self.calc.to_octal("abc")

    def test_to_hexadecimal(self):
        self.assertEqual(self.calc.to_hexadecimal(255), hex(255))
        self.assertEqual(self.calc.to_hexadecimal(0), hex(0))
        self.assertEqual(self.calc.to_hexadecimal(4096), hex(4096))
        self.assertEqual(self.calc.to_hexadecimal(-255), hex(-255)) # hex() handles negative numbers

    def test_to_hexadecimal_type_error(self):
        with self.assertRaises(TypeError):
            self.calc.to_hexadecimal(255.0)
        with self.assertRaises(TypeError):
            self.calc.to_hexadecimal("ff")

    def test_bitwise_and(self):
        self.assertEqual(self.calc.bitwise_and(5, 3), 1) # 101 & 011 = 001
        self.assertEqual(self.calc.bitwise_and(0, 15), 0)
        self.assertEqual(self.calc.bitwise_and(10, -5), 10 & -5) # Python handles bitwise on negatives
        self.assertEqual(self.calc.bitwise_and(0b1100, 0b1010), 0b1000)

    def test_bitwise_and_type_error(self):
        with self.assertRaises(TypeError):
            self.calc.bitwise_and(5.0, 3)
        with self.assertRaises(TypeError):
            self.calc.bitwise_and(5, "3")

    def test_bitwise_or(self):
        self.assertEqual(self.calc.bitwise_or(5, 3), 7)  # 101 | 011 = 111
        self.assertEqual(self.calc.bitwise_or(0, 15), 15)
        self.assertEqual(self.calc.bitwise_or(10, -5), 10 | -5)
        self.assertEqual(self.calc.bitwise_or(0b1100, 0b1010), 0b1110)

    def test_bitwise_or_type_error(self):
        with self.assertRaises(TypeError):
            self.calc.bitwise_or(5.0, 3)
        with self.assertRaises(TypeError):
            self.calc.bitwise_or(5, "3")

    def test_bitwise_xor(self):
        self.assertEqual(self.calc.bitwise_xor(5, 3), 6)  # 101 ^ 011 = 110
        self.assertEqual(self.calc.bitwise_xor(0, 15), 15)
        self.assertEqual(self.calc.bitwise_xor(10, -5), 10 ^ -5)
        self.assertEqual(self.calc.bitwise_xor(0b1100, 0b1010), 0b0110)

    def test_bitwise_xor_type_error(self):
        with self.assertRaises(TypeError):
            self.calc.bitwise_xor(5.0, 3)
        with self.assertRaises(TypeError):
            self.calc.bitwise_xor(5, "3")

    def test_bitwise_not(self):
        self.assertEqual(self.calc.bitwise_not(5), ~5)
        self.assertEqual(self.calc.bitwise_not(0), ~0)
        self.assertEqual(self.calc.bitwise_not(-5), ~-5)

    def test_bitwise_not_type_error(self):
        with self.assertRaises(TypeError):
            self.calc.bitwise_not(5.0)
        with self.assertRaises(TypeError):
            self.calc.bitwise_not("5")

if __name__ == '__main__':
    unittest.main()
