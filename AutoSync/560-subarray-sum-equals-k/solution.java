class Solution {
    public int subarraySum(int[] nums, int k) {
        return Method(nums,k);
    }
    public static int Method(int [] nums,int k){
        int []add = new int[nums.length];
        int ans = 0;
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        hashMap.put(0,1);
        for (int i = 0;i < nums.length ; i++) {
            if (i == 0){
                add[0] = nums[0];
            }else {
                add[i] = nums[i] + add[i-1];
            }
            if (hashMap.containsKey(add[i] - k)) {
//                因为假设从i到j的和为k，因为之前计算了前缀和，所以只需要满足add[j]-add[i]=k，就可以，移项得add[j]-k = add[i],只需判断add[i]出现的次数就可
                ans += hashMap.get(add[i]-k);
            }
            hashMap.put(add[i], hashMap.getOrDefault(add[i], 0) + 1);
        }
        return ans;
    }
}
