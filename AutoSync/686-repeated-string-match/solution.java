class Solution {
    public int repeatedStringMatch(String a, String b) {
        return Method(a,b);
    }
    public static int Method(String a,String b){
        StringBuilder sb = new StringBuilder();
       int ans = 0;
       while(sb.length()<b.length()){
           sb.append(a);
           ans++;
       }
        sb.append(a);
       int index = sb.indexOf(b);
        if (index == -1) return -1;
       return index+b.length() > ans * a.length() ? ans+1:ans;
    }
}
