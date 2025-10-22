package Java;

import java.util.Arrays;

public class _3397_maxDistinctElements {
    public static void main(String[] args) {
        int [] nums = {};
        int k;
        System.out.println();
    }
//     nums 所有元素都相同的情况,可以把元素 x 变成 [x−k,x+k] 中的整数，
//     这一共有 2k+1 个。如果 2k+1≥n，就可以让所有元素互不相同。
    public static int maxDistinctElements(int[] nums, int k) {
        int n = nums.length;
        if(n <= 2*k+1){
            return n;
        }
//        排序，站好队，从最左边的同学开始，始终保持前一个数小于后一个，
        Arrays.sort(nums);
        int ans = 0;
        int pre = Integer.MAX_VALUE;
        for(int x : nums){
//            贪心的将当前数字变成最小（x-k），当等于时就变pre+1，
//            但是同时要确定不能超过范围（x+k）
            x = Math.min(Math.max(pre+1,x-k),x+k);
            if(x > pre){
                pre = x;
                ans++;
            }
        }
        return ans;
    }
}
