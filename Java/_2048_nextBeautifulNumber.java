package Java;

public class _2048_nextBeautifulNumber {
    public static void main(String[] args) {
        int n= 1;
        System.out.println(nextBeautifulNumber(n));
    }

    public static  int nextBeautifulNumber(int n){
//        122444是范围里中最大的平衡数
        for(int i = n+1; i <= 1224444; i++){
            if(judge(i)){
                return i;
            }
        }
        return -1;
    }

    public static  boolean judge (int x){
//        取余求每一位的数字，记录每种数字的个数
        int [] cnt = new int[10];
        while(x > 0){
            cnt[x%10]++;
            x/=10;
        }
//        而后在数组中判断，每个数字i是否具有与之形同的i个，若有一种数字不符合，就不是平衡数
        for(int i = 0; i < cnt.length; i++ ){
            if(cnt[i] != 0 && cnt[i]!=i){
                return false;
            }
        }
        return true;
    }
}
