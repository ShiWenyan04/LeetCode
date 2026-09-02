class Solution {
   public static int [] getnext(String pattern){
        int n = pattern.length();
        if(n < 2){
            return new int[0];
        }
        int [] next = new int [n];
        next [0] = 0;
        next [1] = 0;
        int comp = 0;
        int cur = 2;
        while(cur < n){
            if(pattern.charAt(comp) == pattern.charAt(cur-1)){
                next[cur] = comp + 1;
                comp++;
                cur++;
            }else if(comp>0){
                comp = next[comp];
            }else{
                next[cur] = 0;
                cur++;
            }
        }
        return next;
    }
    public static int strStr(String str, String pattern){
        int []next = getnext(pattern);
        int p = 0;
        int s = 0;
        while(p<pattern.length() && s<str.length()){
            if(str.charAt(s) == pattern.charAt(p)){
                p++;
                s++;
            }else if(p>0){
                p = next[p];
            }else{
                s ++;
            }
        }
        return p ==  pattern.length() ? s-pattern.length() : -1;
    }
}
