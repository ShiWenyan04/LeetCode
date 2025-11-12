package Java;

public class _2169_countOperations {
    public static void main(String[] args) {
        int x = 5;
        int y = 9;
        System.out.println();
    }
    public static int countOperations(int x,int y){
        int ans = 0;
        int temp ;
        while(y > 0){
            ans+=x/y;
            temp = x%y;
            x = y;
            y = temp;
        }
        return ans;
    }
}
