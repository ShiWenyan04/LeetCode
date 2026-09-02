class Solution {
    public int integerReplacement(int n) {
        return Method(n);
    }
    public static int Method(int n){//运用深度优先搜索，递归将当前值判断之后，进行处理，然后进行下一次递归
        if (n == 1 ){
            return 0;
        }
        if(n%2 ==0){
            return 1 + Method(n/2) ;//一直递归求解
        }
        return 2 + Math.min(Method(n/2),Method(n/2+1));//一直递归求解
    }
}
