class Solution {
 public long maxRunTime(int n, int[] batteries) {
        Arrays.sort(batteries);

        long sum = 0;
        for (int b : batteries) {
            sum += b;
        }

        for (int i = batteries.length - 1; ; i--) {
            if (batteries[i] <= sum / n) {
                return sum / n;
            }
            sum -= batteries[i];
            n--;
        }
    }
}
