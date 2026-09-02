class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        return Method1(nums);
    }
     public static List<List<Integer>> Method1(int[] nums){

        List<List<Integer>> list = new ArrayList<>();
        ArrayList<Integer> subset = new ArrayList<>();
        for (int i = nums.length; i >= 0; i--) {
            Method2(list, subset, nums,i, 0);

        }
        return list;
    }
    public static void Method2(List<List<Integer>> list, ArrayList<Integer> subset,int[] nums, int times,int index){
        if (times == subset.size()){
            list.add(new ArrayList<>(subset));
            return;
        }
       while(index < nums.length && times <= nums.length) {
           subset.add(nums[index]);
           Method2(list, subset, nums, times, index+1);
           subset.remove(subset.size()-1);
           index++;
        }
    }
}
