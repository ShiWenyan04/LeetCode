class Solution {
    public int rob(int[] nums) {
        if(nums.length == 1){
            return nums[0];
        }else if (nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }  
         return (Math.max(Method(nums,0,nums.length-2),Method(nums,1,nums.length-1)));
        //        如果不偷窃最后一间房屋，则偷窃房屋的下标范围是 [0,n−2],  如果不偷窃第一间房屋，则偷窃房屋的下标范围是 [1,n−1]
    }
    public static int Method(int [] nums,int index,int length){
        int []ans = new int[nums.length];//
        ans[index] = nums[index];
        ans[index+1] = Math.max(nums[index],nums[index+1]);
        for (int i = index+2; i <= length; i++) {
            ans[i] = Math.max(ans[i-1],ans[i-2]+nums[i]);
        }
        if (ans[ans.length-1 ]== 0 && ans[ans.length-2] != 0){
            return ans[ans.length-2];
        }
        return ans[ans.length-1];
    }
}
