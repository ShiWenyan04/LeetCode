class Solution {
    public int triangleNumber(int[] nums) {
        return Method(nums);
    }
    public static int Method(int [] nums){
        Arrays.sort(nums);
        int ans = 0;
        for (int i = nums.length-1; i >= 2; i--) {
            int a = nums[i];
            int j = i-1,k=0;
            while(k < j){
                if(nums[j] + nums[k] > a){//因为数组已经排过序，从小到大，所以规定好一个数值，另外两个数值可以通过双指针来判断是否能够和a组成三角形
                    ans+=j-k;//倘若当前的j已经无法满足条件，则j之前的所有都无法满足，此时需要将k向后移动，使最小值变大
                    j--;
                }else {//因为数组已经排过序，从小到大，所以如果当前索引的值与j和a无法构成三角形，说明k对应的值太小了，需要变大
                    k++;
                }
            }
        }
        return ans;
    }
}
