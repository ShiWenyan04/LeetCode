class Solution {
    public long mostPoints(int[][] questions) {
        return Method(questions);
    }
    public static long Method(int [][]questions){
        int n = questions.length;
        long [] f = new long[n+1];
        for (int i = n-1; i >= 0; i--) {
            f[i] = Math.max(f[i+1],f[Math.min(n,i+questions[i][1]+1)]+questions[i][0]);
        }
        return f[0];
    }
}
