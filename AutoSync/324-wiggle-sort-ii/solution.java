class Solution {
    public void wiggleSort(int[] nums) {
         Method(nums);
    }
     public static int[] Method(int []nums){
        int n = nums.length;
        int max = Arrays.stream(nums).max().getAsInt();
        int [] count = new int[max+1];
        for (int num : nums) {
            count[num]++;
        }
        for (int i = 1; i < n; i+=2) {
            while(count[max] == 0){
                max--;
            }
            count[max]--;
            nums[i] = max;
        }
        for (int i = 0; i < n; i+=2) {
            while(count[max] == 0){
                max--;
            }
            count[max]--;
            nums[i] = max;
        }
        return nums;
    }
}
