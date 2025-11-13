package Java;

public class _3228_maxOperations {
    public static void main(String[] args) {
        String s = "1001110";
        System.out.println();
    }
    //    堵车模型，从右往左获得最小操作次数，从左往右获得最大操作次数
    public static int maxOperations(String s) {
        int ans = 0;
        int n = s.length();
        int cnt = 0;//车数
        for(int i= 0; i < n; i++){
            if(s.charAt(i) == '1'){//计算车辆
                cnt++;
            }else if( i>0&&s.charAt(i-1) == '1' ){
                //如果遇见“0”，且左边首位为1，说明左边的每辆车都要移动一次，总操作次数要加上每辆车移动的次数（即车的数量）
                ans+=cnt;
            }
        }
        return ans;
    }
}
