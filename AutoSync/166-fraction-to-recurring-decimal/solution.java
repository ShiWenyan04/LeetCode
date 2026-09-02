class Solution {
public static String fractionToDecimal(int numerator, int denominator) {
        long a =  numerator;
        long b = denominator;

        String fuhao = a*b<0?"-":"";//判断正负

        //化成绝对值好计算
        a = Math.abs(a);
        b = Math.abs(b);
        long q = a/b,r = a%b;//q是商，r是余数
        if(r == 0){//余数为0时
            return fuhao+q;
        }

        HashMap<Long, Integer> map = new HashMap<>();
        StringBuilder ans = new StringBuilder(fuhao).append(q).append('.');
        map.put(r,ans.length());
        while(r>0){
            r*=10;
            q=r/b;//求商，也是小数部分
            r%=b;//更新余数
            ans.append(q);
            if(map.containsKey(r)){
                int pos =  map.get(r);//循环节的位置
                return ans.substring(0,pos)+"("+ans.substring(pos)+")";//拼接无限小数的循环节
            }
            map.put(r,ans.length());//存余数和小数位置
        }
        return ans.toString();//有限小数直接返回
    }
}
