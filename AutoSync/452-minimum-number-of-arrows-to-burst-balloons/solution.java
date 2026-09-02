class Solution {
    public int findMinArrowShots(int[][] points) {
        return Method(points);
    }
    public static int Method(int [][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        int pose = points[0][1];
        int ans = 1;
        for (int[] point : points) {
            if(point[0] > pose){
                pose = point[1];
                ans++;
            }
        }
        return ans;
    }
}
