class Solution {
    public int differenceOfSum(int[] nums) {
        return Method(nums);
    }
     public static int Method(int[] nums){
        int ans1 = 0,ans2 = 0;
        for (int i : nums){
            ans1 += i;
            while(i > 9){
                ans2 += i%10;
                i/=10;
            }
            ans2 += i;
        }
        return Math.abs(ans2-ans1);
    }
}
