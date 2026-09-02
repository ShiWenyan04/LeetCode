class Solution {
    int ans = 0;
    public int countMaxOrSubsets(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max|=num;
        }
        Method(nums,max,0,0);
        return ans;
    }
    public void Method(int []nums,int max,int value , int index){
        if(index == nums.length){
            if (max == value){
                ans ++;
            }
            return ;
        }
        Method(nums,max,value|nums[index],index+1);
        Method(nums,max,value,index+1);
    }
}
