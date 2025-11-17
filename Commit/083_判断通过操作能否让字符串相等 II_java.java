class Solution {
    public boolean checkStrings(String s1, String s2) {
        return Method(s1,s2);
    }
   public static boolean Method(String s1,String s2){
        int n = s1.length();
        char []c1 = s1.toCharArray();
        char []c2 = s2.toCharArray();
        int [][]ch1 = new int[2][26];
        int [][]ch2 = new int[2][26];
        for (int i = 0; i < n; i++) {
            ch1[i%2][c1[i]-'a']++;
            ch2[i%2][c2[i]-'a']++;
        }
        return Arrays.deepEquals(ch1, ch2);
    }
}