class Solution {
    public static int maxArea(int[] height){
        int n=height.length;
        int right = n-1,left=0,max=Integer.MIN_VALUE;
        while(left<right){
            int val = (right-left) * Math.min(height[left],height[right]);
            max =  Math.max(max,val);
            if(height[left]<height[right]){
                left++;
            }else {
                right--;
            }
        }
        return max;
    }
}
