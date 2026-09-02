class Solution {
    public int sumOfBeauties(int[] nums) {
        return Method(nums);
    }
    public static int Method(int [] nums) {
		int sum = 0;
		int n = nums.length;
		int sufMin[] = new int [n];// 后缀最小值
		sufMin[n-1] = nums[n-1];
		for (int j = n-2; j >= 1; j--) {
			sufMin[j] = Math.min(sufMin[j+1], nums[j]);
		}
		int preMax=nums[0];// 前缀最大值
		for (int j = 1; j <= n-2; j++) {
			int num = nums[j];
			 // 此时 preMax 表示 [0, i-1] 中的最大值
			if(preMax<num && num < sufMin[j+1]) {
				sum+= 2;
			}else if(nums[j-1] < num && num < nums[j+1]) {
				sum+= 1;
			}
			  // 更新后 preMax 表示 [0, i] 中的最大值
			preMax = Math.max(num, preMax);
		}
		return sum;
	}
}
