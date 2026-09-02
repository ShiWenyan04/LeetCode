class Solution {
    public String removeStars(String s) {
        return Method(s);
    }
    public static String Method(String s){
        int i =0;
        StringBuilder ans = new StringBuilder();
        while(i < s.length()){
            if (s.charAt(i) == '*'){
              ans.setLength(ans.length()-1);
            }else {
                ans.append(s.charAt(i));
            }
            i++;
        }
        return ans.toString();
    }
}
