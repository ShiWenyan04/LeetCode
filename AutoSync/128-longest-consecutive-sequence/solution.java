class Solution {
   public static int longestConsecutive(int[] nums) {
        int ans = 0;
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        for(Integer x :set){
            if(set.contains(x-1)){
                continue;
            }
            int y = x+1;
            while (set.contains(y)){
                y++;
            }
            ans = Math.max(ans,y-x);
            if (ans * 2 >= nums.length) {
                break;
            }
        }

        return ans;
    }
}
