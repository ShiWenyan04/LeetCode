class Solution {
    public int maxOperations(int[] nums, int k) {
        return Method(nums,k);
    }
    public static int Method(int []nums,int k){
        int n = nums.length;
        HashMap < Integer,Integer> hashMap = new HashMap<>();
        int ans = 0 ;
        for (int i = 0; i < n; i++) {
            int c = hashMap.getOrDefault(k-nums[i],0);
            if(c > 0 ){
                hashMap.put(k-nums[i],c-1);
                ans ++;
            }else {
                hashMap.put(nums[i],hashMap.getOrDefault(nums[i],0)+1);
            }
        }
        return ans;
    }
}
