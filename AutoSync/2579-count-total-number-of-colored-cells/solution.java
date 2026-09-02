class Solution {
    public long coloredCells(int n) {
        return Method(n);
    }
    public static long Method(int n){
        long ans = 1;
        int i = 0;
        while(i<n && n>=2){
            ans += 4*i;
            i++;
        }
        return ans;
    }
}
