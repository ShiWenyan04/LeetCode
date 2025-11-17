class Solution {
    public boolean isZeroArray(int[] nums, int[][] queries) {
        return Method(nums,queries);
    }
    public static boolean Method(int []nums, int [][]queries) {
		int n = nums.length;
		int [] diff = new int[n+1];
		for(int []q:queries) {
//			区间[l，r]中的数都加一
			diff[q[0]]++;
			diff[q[1]+1]--;
		}
		int sumD = 0;
		for(int i = 0; i < n;i++) {
			sumD += diff[i];
//			此时sumD表示nums[i]要减掉多少
			if(nums[i] > sumD) {//无法变成0
				return false;
			}
		}
		return true;
	}
}
