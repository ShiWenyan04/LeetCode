class Solution {
    public int[] getNoZeroIntegers(int n) {
        return getNoZeroIntegers2(n);
    }
    public static int [] getNoZeroIntegers2(int n) {
        int mid = n/2;
        int ans[] = new int[2];
        for(int i = 1 ; i <= mid ; i++){
            int j = n-i;
            if (!String.valueOf(i).contains("0") && !String.valueOf(j).contains("0")){
                ans[0] = i;
                ans[1] = j;
                return ans;
            }
        }
        return ans;
    }
}
