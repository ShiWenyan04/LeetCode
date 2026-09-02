class Solution {
    public int maximumRemovals(String s, String p, int[] removable) {
        return (Method(s,p,removable));
    }
    public static int Method(String s,String p,int[] removable){
        int left = 0;
        int right = removable.length-1;
        int mid = 0;
        while(left<=right){
            mid = (left+right)/2;
            if (Method2(s,p,removable,mid)){
                left=mid+1;
            }else {
                right=mid-1;
            }
        }
        return right+1;
    }
    public static boolean Method2(String s,String p, int [] removable, int mid){
        int len1 = s.length();
        int len2 = p.length();
        boolean []judge = new boolean[len1];
        for (int i = 0; i <= mid; i++) {
            judge[removable[i]] = true;
        }
        int j = 0, k =0;
       while(j<len1 && k<len2) {
            if (s.charAt(j) == p.charAt(k) && !judge[j]){
                k++;
            }
            j++;
       }
       return k == len2;
    }

}
