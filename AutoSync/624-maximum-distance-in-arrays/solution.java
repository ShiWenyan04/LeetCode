class Solution {
    public int maxDistance(List<List<Integer>> arrays) {
        return Method(arrays);
    }
    public static int Method(List<List<Integer>> nums){
        int min = nums.get(0).get(0);
        int max = nums.get(0).get(nums.get(0).size()-1);
        int res = 0;
        for (int i = 1; i < nums.size(); i++) {
            int len = nums.get(i).size();
            int end = nums.get(i).get(len-1);
            int abs1 = Math.abs(end - min);
            int abs2 = Math.abs(max - nums.get(i).get(0));
            res = Math.max(res,Math.max(abs1,abs2));
            max = Math.max(max,end);
            min = Math.min(min,nums.get(i).get(0));
        }
        return res;
    }
}
