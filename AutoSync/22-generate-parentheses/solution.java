class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        Method(list, n, 0,0, "");
        return list;
    }
    public static void Method(List<String> list, int n, int left,int right, String str){
        if (left == n && right == left ){
            list.add(str);
            return;
        }
        if (left < n ){
            Method(list,n,left+1,right,str+"(");
        }
        if (right < left){
            Method(list,n,left,right+1,str+")");
        }
    }
}
