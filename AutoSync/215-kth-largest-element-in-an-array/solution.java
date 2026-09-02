class Solution {
    public int findKthLargest(int[] nums, int k) {
        return Method(nums,0,nums.length-1,nums.length-k);
    }
    public static int Method(int [] nums, int i,int j,int k){
        int start = i-1;
        int end = j+1;
        int base = nums[i];
        if(i == j ){
            return nums[k];
        }
        while(start < end){
            do start ++;while (nums[start] < base);
            do end --;while (nums[end] > base);
            if(start<end){
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
            }
        }
        if(k <= end){
           return Method(nums,i,end,k);
        }else return Method(nums,end+1,j,k);
    }
}
