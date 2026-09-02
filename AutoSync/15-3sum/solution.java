class Solution {
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> list = new ArrayList<>();
        for(int i = 0; i < nums.length-2; i++){
            if(i>0 && nums[i]==nums[i-1]){
                continue;
            }
            int right = n-1;
            int left = i+1;
            int target = -nums[i];
            while(left<right){
                int sum = nums[left]+nums[right];
                if(sum==target){
                    list.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                }else if(sum<target){
                    left ++;
                }else {
                    right --;
                }
            }
            
        }
        return list;
    }
}
