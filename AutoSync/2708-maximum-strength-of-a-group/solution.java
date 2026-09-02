class Solution {
    public long maxStrength(int[] nums) {
        return (Method(nums));
    }
     public static long Method(int[] nums) {
         if(nums.length == 1){
            return nums[0];
        }
        long ans = 1,max = Integer.MIN_VALUE;//ans为乘积之和，max为负数中绝对值最大的负数
        int zero = 0;//zero记录0的个数
        int temp = Integer.MIN_VALUE;//temp记录数组中的最大值，确保最大值大于0；
        for (int i =0;i<nums.length;i++){
            if(nums[i]==0) {//元素为0，就记录元素数量，且不进行乘法运算
                zero++;
                continue;
            }
            ans *= nums [i];//乘积
        }
        if (zero == nums.length){//0的数量等于数组长度，直接返回0
            return  0;
        }
        //ans大于零，直接返回
        if (ans > 0){
            return ans;
        }else {
            if(zero > nums.length-2){//如果0的数量很多，使得非零数的个数凑不到2,例如{0，0，0，0，-1}
                for (int i = 0; i < nums.length; i++) {//遍历寻找非零数，并判断其大小
                    if (nums[i] > 0){
                        return nums[i];
                    }else {
                        return 0;
                    }
                }
            }else {//否则除以 绝对值最小的负数，即最大的负数。
                for (int i =0;i<nums.length;i++){
                    if (nums[i]<0){
                        max = Math.max(max,nums [i]);
                    }
                }
            }
        }
        ans/=max;
        return ans;
    }
}
