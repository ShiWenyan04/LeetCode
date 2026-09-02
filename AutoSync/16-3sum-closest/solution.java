class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int closestNum = nums[0] + nums[1] + nums[2];
        if (closestNum > target){
            return closestNum;
        }
        for (int first = 0; first < nums.length; first++) {
            if (first>0&&nums[first] == nums[first-1]){
                continue;
            }else {
                int second = first+1;
                int third = nums.length-1;
                while(second<third){
                    int sum = nums[first] + nums[second] + nums [third];
                    if (sum == target){
                       return sum;
                    }else if (sum < target){
                       second ++;
                    } else if (sum > target) {
                       third--;
                    }
                    if (Math.abs(sum - target) < Math.abs(closestNum - target)) {
                        closestNum = sum;
                    }
                }
            }
        }
        return closestNum;
    }
}
