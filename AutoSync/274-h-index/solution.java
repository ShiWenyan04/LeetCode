class Solution {
    // public static int hIndex(int[] citations) {
    //     int n = citations.length;
    //     for (int i = n; i >0 ; i--) {
    //         int cnt = 0;
    //         for (int j = 0; j < n; j++) {
    //             if(citations[j] >= i) {
    //                 cnt++;
    //             }
    //         }
    //         if(cnt >= i){
    //             return i;
    //         }
    //     }
    //     return 0;
    // }

    public static int hIndex(int[] nums){
        int n = nums.length;
        int [] cnt = new int [n+1];
        for(int x:nums){
            cnt[Math.min(x,n)]++;
        }
        int s = 0;
        for(int i = n; ;i--){
            s+=cnt[i];
            if(s >= i){
                return i;
            }
        }
    }
}
