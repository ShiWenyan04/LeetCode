package Java;
//银行内部的防盗安全装置已经激活。给你一个下标从 0 开始的二进制字符串数组 bank ，表示银行的平面图，这是一个大小为 m x n 的二维矩阵。 bank[i] 表示第 i 行的设备分布，由若干 '0' 和若干 '1' 组成。'0' 表示单元格是空的，而 '1' 表示单元格有一个安全设备。
//
//对任意两个安全设备而言，如果同时 满足下面两个条件，则二者之间存在 一个 激光束：
//
//两个设备位于两个 不同行 ：r1 和 r2 ，其中 r1 < r2 。
//满足 r1 < i < r2 的 所有 行 i ，都 没有安全设备 。
//激光束是独立的，也就是说，一个激光束既不会干扰另一个激光束，也不会与另一个激光束合并成一束。
//
//返回银行中激光束的总数量。
public class _2125_numberOfBeams {
    public static void main(String[] args) {
        String [] bank = {"011001","000000","010100","001000"};
        System.out.println(numberOfBeams(bank));
    }
    public static int numberOfBeams(String [] bank) {
        int n = bank.length;
        int sum = 0;
        int pre = 0;
        for (int i = 0; i < n; i++) {
            String s = bank[i];
            int cnt = 0;
//            计算相邻行之间的激光束的数量之和，即 3×2+2×1=8。
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
