class Solution {
    public boolean isCircularSentence(String sentence) {
        return Method(sentence);
    }
    public static boolean Method(String s){
        if(s.charAt(0)!=s.charAt(s.length()-1)){
            return false;
        }
        for (int i = 1; i < s.length(); i++) {
            if(s.charAt(i) == ' '){
                if(s.charAt(i-1) != s.charAt(i+1)){
                    return false;
                }
            }
        }
        return true;
    }
}
