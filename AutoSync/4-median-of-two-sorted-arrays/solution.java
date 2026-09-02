class Solution {
   public double findMedianSortedArrays(int[] strs1, int[] strs2) {
        int [] strs3 = Merge(strs1,strs2);//合并
        Arrays.sort(strs3);//排序
        double median;
        int a = (strs3.length)/2;
        if (strs3.length%2 == 0){//奇数还是偶数
            median = (strs3[a] + strs3[a-1])/2.0;
        }else {
            median = strs3[a];
        }
        return median;
    }
    public static int[] Merge(int [] strs1, int [] strs2){
        int [] both = new int[strs1.length + strs2.length];
        System.arraycopy(strs1, 0, both,0, strs1.length);//strs1 存入 strs3
        System.arraycopy(strs2, 0, both, strs1.length, strs2.length);//strs2 存入 strs3
        return both;
    }
}
