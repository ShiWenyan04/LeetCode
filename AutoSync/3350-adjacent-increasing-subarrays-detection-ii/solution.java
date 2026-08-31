class Solution {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int n = nums.size();
        int cnt = 1,precnt = 0,ans = 0;
        for(int i = 1 ; i < n;i++){
            if(nums.get(i) > nums.get(i-1)){
                cnt++;
            }else{
                precnt = cnt;
                cnt= 1;
            }
            ans = Math.max(ans,Math.min(cnt,precnt));
            ans = Math.max(ans,(int)Math.floor(cnt/2));
        }
        return ans;
    }
}
