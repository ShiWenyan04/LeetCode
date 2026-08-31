class Solution {
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();
        int last_idx = -1;
        // 
        for(int i = 0;i < n;){
            int j = i+1;
            while(j < n && nums.get(j) > nums.get(j-1)){
                j ++;
            }
            // 
            int len = j-i;
            if(len < k){
                i=j;
                continue;
            }else if(len >= 2*k){
                return true;
            }else{
                if(last_idx == i-1 && last_idx != -1){
                    return true;
                }
                last_idx = j-1;
            }
            i = j;
        }
        return false;
    }
}
