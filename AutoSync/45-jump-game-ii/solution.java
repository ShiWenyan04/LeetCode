class Solution {
    // public static int jump(int[] nums) {
    //     int n = nums.length;
    //     int pos = n-1;
    //     int ans = 0;
    //     for(;pos > 0;){
    //         for(int i = 0;i < pos;i++){
    //             if(i+nums[i] >= pos){
    //                 ans++;
    //                 pos = i;
    //                 break;
    //             }
    //         }
    //     }
    //     return ans;
    // }
    public static int jump ( int [] nums){
        int n = nums.length;
        int curEnd = 0;
        int nextEnd = 0;
        int ans = 0;
        for(int i = 0 ; i < n-1;i++){
            nextEnd = Math.max(nextEnd,i+nums[i]);
            if(i == curEnd){
                curEnd = nextEnd;
                ans++;
            }
        }
        return ans;
    }
}
