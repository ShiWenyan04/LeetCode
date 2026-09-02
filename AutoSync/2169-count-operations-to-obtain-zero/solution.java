class Solution {
    public static int countOperations(int x,int y){
        int ans = 0;
        while(y > 0){
            ans+=x/y;
            int temp = x % y;
            x = y;
            y = temp;
        }
        return ans;
    }
}
