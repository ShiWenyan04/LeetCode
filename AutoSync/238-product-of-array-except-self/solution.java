class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int right[] = new int [n];
        right[n-1] = 1;
        for(int i = n-2;i>=0;i--){
            right[i] = right[i+1]*nums[i+1];
        }

        int val = 1;
        for(int i = 0; i < n;i++){
            right[i] = right[i]*val;
            val*=nums[i];
        }
        return right;
    }
}
