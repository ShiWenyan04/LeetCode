class Solution {
    public int lastRemaining(int n) {
        return Method(n);
    }
    public static int Method(int n){
        int a1 = 1;//首项
        int d = 1;//公差
        int len = n ;//数列长度
        int k = 1;//按次数记录删除，第一次从左到右，第二次从右到左，以此类推
        while(len>1){
            if (k % 2 != 0){//按次数算，如果是第奇次删除数据，则从左往右开始，首项一定会变为当前数列第二个
            a1 = a1 + d;
            }else {//按次数算，如果是第偶次删除数据，则从右往左开始
                if (len%2!=0){//从左往右，如果长度为偶数，那么首项就不会改变，为奇数，首项变为当前数列的第二个
                    a1 = a1+d;
                }
            }
            k++;
            len /= 2;//每一次删除数字，得到的新数列，长度都会减半
            d *= 2;///每一次删除数字，得到新数列的公差是上一次的二倍
        }
        return a1;
    }
}
