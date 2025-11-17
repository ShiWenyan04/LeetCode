class Solution {
    public static String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int n = num1.length();
        int m = num2.length();
        int []arr = new int[n+m];
        for (int i = n-1; i >= 0; i--) {
            int p = num1.charAt(i)-'0';
            for (int j = m-1; j >= 0; j--) {
                int q = num2.charAt(j)-'0';
                arr[i+j+1] += p*q;
            }
        }
        for (int i = arr.length-1; i > 0 ; i--) {
            arr[i-1] += arr[i]/10;
            arr[i] %= 10;

        }
        int idx = arr[0] == 0 ? 1 : 0;
        StringBuilder sb = new StringBuilder();
        for(int i = idx; i < arr.length; i++){
           sb.append(arr[i]);
        }
        return sb.toString();
    }
}