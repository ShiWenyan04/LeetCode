class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        if(nums.length<4){
            return list;
        }
        if ((long)nums[0] + nums[1] + nums[2] + nums[3] > target){
            return list;
        }
        for (int i = 0; i < nums.length-3; i++) {
            if (i==0 || ((i>0) && nums[i-1] != nums[i])){
                combination.add(nums[i]);
                for (int j = i+1; j < nums.length-2; j++) {
                    if (j==i+1 || ((j>i+1) && nums[j-1] != nums[j])) {
                        combination.add(nums[j]);
                        int left = j+1;
                        int right = nums.length-1;
                        while (left<right){
                            long sum = (long)nums[i] + nums[j] + nums[left] + nums[right];
                            if (sum == target){
                                while(left != right-1 && left<right && nums[left+1] == nums[left]){
                                    left++;
                                }
                                while (left+1 != right && left<right && nums[right-1] == nums[right]){
                                    right--;
                                }
                                combination.add(nums[left]);
                                combination.add(nums[right]);
                                list.add(new ArrayList<>(combination));
                                combination.remove(3);
                                combination.remove(2);
                                right--;
                                left++;
                            } else if (sum>target) {
                                right--;
                            }else {
                                left++;
                            }
                        }
                        combination.remove(1);
                    }
                }
                combination.remove(0);
            }
        }
       return list;
    }
}
