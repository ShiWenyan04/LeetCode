class Solution {
    public double minimumAverage(int[] nums) {
        return Method(nums);
    }
    public static double Method(int [] nums){
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        double ans = Integer.MAX_VALUE;
        for (int i: nums){
            list.add(i);
        }
        for (int i = 0; i < nums.length/2; i++) {
            ans = Math.min(ans, (double) (list.get(0) + list.get(list.size() - 1)) /2);
            list.remove(0);
            list.remove(list.size()-1);
        }
        return ans;
    }
}
