class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        return Method(envelopes);
    }
    public static int Method(int [][]nums){
        int m = nums .length;
        int [] f = new int[m];
        Arrays.sort(nums, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0]?
                        o2[1]-o1[1]:o1[0]-o2[0];
            }
        });
        int []hight = new int[m];
        for (int i = 0; i < m; i++) {
            hight[i] = nums[i][1];
        }
        int piles = 0;
        for (int i = 0; i < m; i++) {
            int poker = hight[i];
            int left = 0,right = piles;
            while(left < right){
                int mid = (left + right)/2;
                if(f[mid] >= poker){
                    right = mid;
                } else if (f[mid] < poker) {
                    left = mid+1;
                }
            }
            if(left == piles){
                piles++;
            }
            f[left] = poker;
        }
        return piles;
    }
}
