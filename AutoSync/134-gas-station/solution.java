class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int ans = 0;
        int minS = 0; // 最小油量
        int s = 0; // 油量
        for (int i = 0; i < gas.length; i++) {
            s += gas[i] - cost[i]; // 在 i 处加油，然后从 i 到 i+1
            if (s < minS) {
                minS = s; // 更新最小油量
                ans = i + 1; // 注意 s 减去 cost[i] 之后，汽车在 i+1 而不是 i
            }
        }
        // 循环结束后，s 即为 gas 之和减去 cost 之和
        return s < 0 ? -1 : ans;
    }
}
