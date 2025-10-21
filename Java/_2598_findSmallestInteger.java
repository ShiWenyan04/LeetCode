package Java;

public class _2598_findSmallestInteger {
    public static void main(String[] args) {
        int []nums = {1,-10,7,13,6,8};
        int value = 5;
    }
//    由于同一个数可以加减任意倍的 k,将nums【i】变为与其相关的最小非负整数
//    公式为(nums[i] mod m + m) mod m
    public static int findSmallest(int[] nums,int k){
        int []cnt = new int[k];
        //获得相同余数的个数
        for(int i : nums){
            cnt[((i%k)+k)%k]++;
        }
        int mex = 0;
//        此处判断从0开始的，与之相关的最小非负整数，判断是否存在与之同余的数
        while(cnt[mex%k]!=0){
            cnt[mex%k]--;
            mex++;
        }
        return mex;
    }
}
