package Java;

public class _3190_minimumOperations {
    public static void main(String[] args) {
        int [] nums = {1,2,3,4};
        System.out.println(minimumOperations(nums));
    }
    public static int minimumOperations(int[] nums) {

        int cnt = 0;
        for (int x : nums) {
            cnt+=Math.min(x%3,3-x%3);
        }
        return cnt;
    }
}
