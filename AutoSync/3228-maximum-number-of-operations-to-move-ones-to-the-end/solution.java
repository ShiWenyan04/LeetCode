class Solution {
    public int maxOperations(String s) {
        return Method(s);
    }
    public static int Method(String s){
        int ans = 0;
        int oper = 0;
        boolean zero = false;
        for (int i = s.length()-1; i >=0 ; i--) {
            if (s.charAt(i) == '0'){
                zero =true;
            }else  {
                if (zero){
                    oper ++ ;
                    zero = false;
                }
                ans+=oper;
            }
        }
        return ans;
    }
}
