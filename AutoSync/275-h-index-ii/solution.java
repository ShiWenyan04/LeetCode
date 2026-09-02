class Solution {
    public int hIndex(int[] citations) {
        return Method(citations);
    }
     public static int Method(int[] citations){
        int left = 0;
        int right = citations.length-1;
        int mid = 0;
        if (citations.length == 1 && citations[0] != 0){
            return 1;
        }
        if (citations.length == 1 && citations[0] == 0){
            return 0;
        }
         int ans = 0;
        while(left <= right){//二分
            mid = (left + right)/2;

            if (citations[mid] >= citations.length-mid){//此时个数小于最高指数
                ans = citations.length-mid;
                right = mid-1;//需得让指数变小，个数变多，界限向右缩进
            }
            if (citations[mid] < citations.length-mid){//此时个数大于最高指数
                left = mid+1;//需得让指数变大，个数变小，界限向左缩进
            }
        }
        return ans;//
    }
}
