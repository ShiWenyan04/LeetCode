class Solution {
    public String stringHash(String s, int k) {
        return Method(s,k);
    }
    public static String Method(String s,int k) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i+=k) {
            int sum = 0;
            for (int j = i; j < i+k; j++) {
                sum += s.charAt(j)-'a';
            }
           result.append((char)('a'+sum%26));
        }
        return result.toString();
    }
}
