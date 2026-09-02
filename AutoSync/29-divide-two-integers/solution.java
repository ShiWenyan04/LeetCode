class Solution {
    public int divide(int dividend, int divisor) {
        return Method(dividend,divisor);
    }
    public static int Method(int dd,int ds){
         // 考虑被除数为最小值的情况
        if (dd == Integer.MIN_VALUE) {
            if (ds == 1) {
                return Integer.MIN_VALUE;
            }
            if (ds == -1) {
                return Integer.MAX_VALUE;
            }
        }
        // 考虑除数为最小值的情况
        if (ds == Integer.MIN_VALUE) {
            return dd == Integer.MIN_VALUE ? 1 : 0;
        }
        // 考虑被除数为 0 的情况
        if (dd == 0) {
            return 0;
        }
        
        // 一般情况，使用类二分查找
        // 将所有的正数取相反数，这样就只需要考虑一种情况
        boolean rev = false;
        if(ds>0 && dd > 0 || ds < 0 && dd <0){
            rev = true;
        }
        if (ds > 0) {
            ds = -ds;
        }
        if (dd > 0) {
            dd = -dd;
        }
        int ans = 0;
        List<Integer> list = new ArrayList<>();
        int index = 0;
        list.add(ds);
//
        while( list.get(index) >= dd - list.get(index)){
            list.add(list.get(index) + list.get(index));
            index++;
        }
//
        for (int i = list.size()-1; i >= 0 ; i--) {
            if(list.get(i)>=dd){
                ans+=1<<i;
                dd-=list.get(i);
            }
        }
        return rev ? ans : -ans;
    }
}
