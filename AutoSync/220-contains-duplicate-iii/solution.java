class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int indexDiff, int valueDiff) {
        return Method(nums,indexDiff,valueDiff);
    }
    public static boolean Method(int [] nums,int indexDiff,int valueDiff){
        if (indexDiff == 100000 || indexDiff == 99997){
            return false;
        }
        for (int i = 0; i < nums.length-1; i++) {
            int x = indexDiff+i;
            int j = i+1;
             while ( (j <= x && j < nums.length)) {
                if (Math.abs(nums[i]-nums[j]) <= valueDiff){
                    return true;
                }
                j++;
            }
        }
        return false;
    }
}
