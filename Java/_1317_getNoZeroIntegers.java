import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class _1317_getNoZeroIntegers {
    public static void main(String[] args) {
        int n = 9;
        System.out.println(Arrays.toString(Arrays.stream(getNoZeroIntegers(n)).toArray()));
    }
    public static int [] getNoZeroIntegers(int n) {
        int mid = n/2;
        int ans[] = new int[2];
        for(int i = 1 ; i <= mid ; i++){
            int j = n-i;

            if (!String.valueOf(i).contains("0") && !String.valueOf(j).contains("0")){
                ans[0] = i;
                ans[1] = j;
            }
        }
        return ans;
    }
}