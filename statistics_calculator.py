import math
import collections

class StatisticsCalculator:
    def mean(self, data):
        if not data:
            raise ValueError("Data list cannot be empty.")
        return sum(data) / len(data)

    def median(self, data):
        if not data:
            raise ValueError("Data list cannot be empty.")
        sorted_data = sorted(data)
        n = len(sorted_data)
        mid = n // 2
        if n % 2 == 0:
            return (sorted_data[mid - 1] + sorted_data[mid]) / 2
        else:
            return sorted_data[mid]

    def mode(self, data):
        if not data:
            raise ValueError("Data list cannot be empty.")
        counts = collections.Counter(data)
        max_count = max(counts.values())
        modes = [num for num, count in counts.items() if count == max_count]
        return modes

    def variance(self, data):
        if not data or len(data) < 2:
            raise ValueError("Data list must contain at least two elements to calculate variance.")
        n = len(data)
        mean_val = self.mean(data)
        return sum((x - mean_val) ** 2 for x in data) / (n - 1) # Sample variance

    def std_dev(self, data):
        if not data or len(data) < 2:
            raise ValueError("Data list must contain at least two elements to calculate standard deviation.")
        return math.sqrt(self.variance(data))
