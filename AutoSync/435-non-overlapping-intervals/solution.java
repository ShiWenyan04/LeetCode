class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        return Method(intervals);
    }
    public static int Method(int [][] nums){
        Arrays.sort(nums, new Comparator<int[]>() {//
            @Override
            public int compare(int[] a, int[] b) {
                return a[1]-b[1];//比较第二维的,使数组的第二个数字呈现递增趋势，这样只需要配判断每一组数据的第一个数字是否在前一个区间里
            }
        });
        int len = 1;
        int start =0;
        for (int j = 1; j < nums.length; j++) {
            if(nums[start][1] <= nums[j][0]){//因为第二个数字永远升序，只需判断当前区间的第一个数字是否包含于前一个区间的
                start = j;
                len ++;
            }
        }
        return nums.length-len;
    }
}
