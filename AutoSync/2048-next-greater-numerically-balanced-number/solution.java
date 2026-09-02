class Solution {
     public static  int nextBeautifulNumber(int n){
        for(int i = n+1; i <= 1224444; i++){
            if(judge(i)){
                return i;
            }
        }
        return -1;
    }
    public static  boolean judge (int x){
        int [] cnt = new int[10];
        while(x > 0){
            cnt[x%10]++;
            x/=10;
        }
        for(int i = 0; i < cnt.length; i++ ){
            if(cnt[i] != 0 && cnt[i]!=i){
                return false;
            }
        }
        return true;
    }
}
