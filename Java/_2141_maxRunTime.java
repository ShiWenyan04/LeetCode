package Java;

import java.util.Arrays;

/**
 * 核心思想：大电池的「超额部分」无法被充分利用，不如单独分配给一台电脑。
 * 排序：先将电池按电量从大到小排序，优先处理最大的电池（因为大电池最可能存在「超额」）。
 * 总电量判断：计算当前剩余电池的总电量 sum，如果当前最大电池的电量 ≤ sum / n（即所有电池能平均支撑 n 台电脑），则 sum / n 就是答案（因为平均分配是最优解）。
 * 排除超额电池：如果当前最大电池的电量 > sum / n，说明这颗电池的「超额部分」无法被 n 台电脑同时利用（比如 n=2，sum=9，最大电池 3，3≤9/2=4.5，可平均；若最大电池 5，sum=10，n=2，5>5（10/2），则 5 只能支撑 1 台电脑跑 5 小时，剩下的电池支撑另一台，此时 n 减 1，sum 减去这颗电池）。
 */
public class _2141_maxRunTime {
    public static void main(String[] args) {
        int n = 2;
        int [] batteries = {3,3,3};
        System.out.println(maxRunTime(n,batteries));
    }
    public static long maxRunTime(int n, int[] batteries) {
        // 1. 电池按电量从小到大排序，方便从大到小处理
        Arrays.sort(batteries);

        // 2. 计算所有电池的总电量（用long避免溢出，因为电池电量可能很大）
        long sum = 0;
        for (int b : batteries) {
            sum += b;
        }

        // 3. 从最大的电池开始倒序遍历
        for (int i = batteries.length - 1; ; i--) {
            // 关键判断：当前最大电池是否能被n台电脑「平均利用」
            if (batteries[i] <= sum / n) {
                return sum / n; // 能则返回平均时间
            }
            // 不能则：排除这颗超额电池（它单独支撑1台电脑到没电）
            sum -= batteries[i]; // 总电量减去这颗电池
            n--; // 电脑数量减1（因为这颗电池已经「绑定」给1台电脑了）
        }
    }
}
