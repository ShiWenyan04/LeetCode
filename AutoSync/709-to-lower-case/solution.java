class Solution {
    public String toLowerCase(String s) {
        return Method(s);
        }
  public static String Method(String s){
        int  i = 0 ;
        StringBuilder str = new StringBuilder();
        while(i<s.length()){
            char c = s.charAt(i);
            if (s.charAt(i) <= 90 && s.charAt(i) >= 65){
                c += 32;
            }
            str.append(c) ;
            i++;
        }
        return str.toString();
    }
}
