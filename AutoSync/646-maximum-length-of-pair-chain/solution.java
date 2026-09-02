class Solution {
    public int findLongestChain(int[][] pairs) {
        return Method(pairs);
    }
    public static int Method(int [][]pairs){
        Arrays.sort(pairs,(a,b)->Integer.compare(a[0],b[0]));
        int n = pairs.length;
        int [] f = new int[n+1];
        Arrays.fill(f,1);
        int maxLen = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if(pairs[j][1] < pairs[i][0]){
                    f[i] = Math.max(f[i],f[j]+1);
                }
                maxLen = Math.max(maxLen,f[i]);
            }
        }
        return maxLen;
    }
}
