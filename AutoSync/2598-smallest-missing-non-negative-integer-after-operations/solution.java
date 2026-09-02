class Solution {
    public int findSmallestInteger (int[] nums,int k){
        int []cnt = new int[k];
        for(int i : nums){
            cnt[((i%k)+k)%k]++;
        }
        int mex = 0;
        while(cnt[mex%k]!=0){
            cnt[mex%k]--;
            mex++;
        }
        return mex;
    }
}
