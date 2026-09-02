class Solution {
    public int nextGreaterElement(int n) {
        return Method1(n);
    }
    public static int Method1(int n){
       char[] ch = String.valueOf(n).toCharArray();
       int i = ch.length-2;
       while(i>=0 && ch[i] >= ch[i+1]){
           i--;
       }
       if(i < 0){
           return -1;
       }
       int j = ch.length-1;
       while (j >= 0 && ch[j]<=ch[i]){
           j--;
       }
       swap(ch,i,j);
       reverse(ch,i+1);
       long ans = Long.parseLong(new String(ch));
       return ans>Integer.MAX_VALUE ? -1:(int)ans;
    }
//    交换函数
    public static void swap(char[] num , int i , int j){
        char temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
//    在已经划分的区域里将元素重组，排成最小值
    public static void reverse(char[] num,int start){
        int end = num.length-1;
        while (start<end){
            swap(num,start,end);
            end--;
            start++;
        }
    }
}
