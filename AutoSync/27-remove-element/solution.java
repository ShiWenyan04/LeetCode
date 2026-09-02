class Solution {
    public static int removeElement(int[] nums,int val){
        int n = nums.length;
        int i=0,j=0;
        
        for(;j < n; j++){
            if(nums[j] != val){
                nums[i] = nums[j];

                i++;
            }
        }

        return i;
    }
}
