package Java;

import java.sql.PreparedStatement;

public class _3147_maximumEnergy {
    public static void main(String[] args) {
        int [] energy = {5,2,-10,-5,1};
        int k = 3;
        System.out.println();
    }
    public static int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int ans = Integer.MIN_VALUE; // 记录最大能量值
        int []sum = new int[k];// 存储每个组的累计能量
        // 从后往前遍历
        for (int i = n-1; i >= 0; i--) {
            // 当前能量加到对应组中(余数相同的为一组，因为题目可以理解为每隔k个为一组)
            sum[i%k] += energy[i];
            // 更新最大能量值
            ans = Math.max(ans, sum[i%k]);
        }
        return ans;
    }
}
