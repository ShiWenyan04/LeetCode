class Solution {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
            int j = m+n-1,k = n-1,i = nums1.length-1;
            for(;i>=0&&k>=0;){
                if(i >= m&&nums1[i] == 0){
                    i--;
                    continue;
                }
                if(nums1[i] >= nums2[k]){
                    nums1[j] = nums1[i];
                    i--;
                    j--;
                }else{
                    nums1[j] = nums2[k];
                    k--;
                    j--;
                }
            }
            if(i < 0 && k >= 0){
                for(;k >=0;k--){
                    nums1[j] = nums2[k];
                    j--;
                }
            }
            
        }
}
