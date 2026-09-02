class Solution {
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        return Method(obstacles);
    }
    public static int[] Method(int [] ob){
        int n = ob.length;
        int [] f = new int[n];
        int []ans = new int[n];
        int piles = 0;
        for (int i = 0; i < n; i++) {
           int left = 0,right = piles;
           int poker = ob[i];
           while(left < right){
               int mid = (left+right)/2;
               if(f[mid] <= poker){
                   left = mid+1;
               }else {
                   right = mid;
               }
           }
            f[left] = poker;
           if(left == piles){
               piles ++;
           }
           ans[i] = left+1;
        }
        return ans;
    }
}
