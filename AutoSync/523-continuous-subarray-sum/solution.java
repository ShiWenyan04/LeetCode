class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        return Method(nums,k);
    }
    public static boolean Method(int []nums,int k){
        int n = nums.length;
        HashMap<Integer,Integer>hashMap = new HashMap<>();
        int []add = new int[n+1];
        for (int i = 1; i < n+1; i++) {
            add[i] = nums[i-1]+add[i-1];
        }
        for (int i = 0; i < n+1; i++) {
            int mod = add[i]%k;
            if (!hashMap.containsKey(mod)){
                hashMap.put(mod,i);
            }else if(i -hashMap.get(mod)>= 2){
                return true;
            }
        }
        return false;
    }
}
