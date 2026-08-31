class Solution {
     public static int minimumOperations(int[] nums) {
       
        int cnt = 0;
        for (int x : nums) {
            cnt+=Math.min(x%3,3-x%3);
        }
        return cnt;
    }
}
