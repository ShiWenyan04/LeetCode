class Solution {
    public int findLUSlength(String[] strs) {
        return Method(strs);
    }
    public static int Method(String [] str){
        boolean judge = false;
        int len = -1;
        for (int i = 0; i < str.length; i++) {
            judge = true;
            for (int j = 0; j < str.length; j++) {
                if (i!=j && isSubseq(str[i],str[j])){
                    judge = false;
                    break;
                }
            }
            if (judge){
                len = Math.max(len,str[i].length());
            }
        }
        return len;
    }
    public static boolean isSubseq(String s1,String s2){
       int i=0,j=0;
        while (i < s1.length()&&j<s2.length()){
            if (s1.charAt(i) == s2.charAt(j)){
                i++;
            }
            j++;
        }
        return i == s1.length();
    }
}
