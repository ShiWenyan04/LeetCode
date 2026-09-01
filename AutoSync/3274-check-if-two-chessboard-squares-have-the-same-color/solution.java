class Solution {
    public boolean checkTwoChessboards(String coordinate1, String coordinate2) {
        return Method(coordinate1,coordinate2);
    }
    public static boolean Method(String coordinate1 ,String coordinate2 ){
      int a = (coordinate1.charAt(0) + coordinate1.charAt(1))%2;
      int b = (coordinate2.charAt(0) + coordinate2.charAt(1))%2;
        return a==b;
    }
}
