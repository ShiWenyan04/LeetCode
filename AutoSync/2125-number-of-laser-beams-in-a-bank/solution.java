class Solution {
     public static int numberOfBeams(String [] bank) {
        int n = bank.length;
        int sum = 0;
        int pre = 0;
        for (int i = 0; i < n; i++) {
            String s = bank[i];
            int cnt = 0;
            
            for (int j = 0; j < s.length(); j++) {
                if(s.charAt(j)=='1'){
                    cnt++;
                }
            }
            if(cnt > 0){
                sum+=pre*cnt;
                pre = cnt;
            }
        }
        return sum;
    }
}
