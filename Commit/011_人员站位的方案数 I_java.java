class Solution {
    public int numberOfPairs(int[][] points) {
      return numberOfPairs2(points);
    }
    public static int numberOfPairs2(int[][] points) {
        int n = points.length;
        Arrays.sort(points, (a,b) -> a[0]!=b[0] ? a[0]-b[0]:b[1]-a[1]);
        int ans = 0;
        for(int i = 0 ; i < n; i ++){
            int y1 = points[i][1];
            int maxY = Integer.MIN_VALUE;
            for(int j = i + 1 ; j < n && maxY < y1 ; j ++) {
                int y2 = points[j][1];
                if (y2 <= y1 && y2 > maxY) {
                    ans++;
                    maxY = y2;
                }
            }
        }
        return ans;
    }
};