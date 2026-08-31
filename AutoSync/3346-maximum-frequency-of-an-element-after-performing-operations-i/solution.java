class Solution {
    public static int maxFrequency(int[] nums, int k,int numOperations) {
        Arrays.sort(nums);
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int x : nums) {
            map.put(x,map.getOrDefault(x,0)+1);
        }
        int right = 0;
        int left = 0;
        int ans = 0;
        int min = nums[0];
        int max = nums[nums.length-1];
        for(int target = 0 ; target<=nums[nums.length-1] ;target++){
            while(nums[left] <target-k){
                left++;
            }
            while(right < nums.length && nums[right] <= target+k){
                right++;
            }
            // 可以被转化成target的元素总数
            int len = right-left;
            // 初始target个数
            int has = map.getOrDefault(target,0);
            // 可以转换成target的数量
            int convert1 = len-has;
            // 实际能转换的数量
            int convert2 = Math.min(numOperations, convert1);
            // 真正的总数 = 初始频率 + 新转换过来的数量
            int total = has + convert2;
            ans = Math.max(ans,total);
        }
        return ans;
    }
}
