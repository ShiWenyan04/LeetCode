class Solution {
    public boolean increasingTriplet(int[] nums) {
        return Method(nums);
    }
   public static boolean Method(int [] nums){
        int n = nums.length;
        if (n < 3) {
            return false;
        }
        int firstNum = nums[0], secondNum = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++){
            int thirdNum = nums [i];
            if(secondNum < thirdNum){//f,s,t,分别代表三个数，如果第三个数大于第二个数return true
                return true;
            }else if(thirdNum > firstNum ){//在第三个数小于第二个数的时候，第三个数大于第二个数，说明第三个数的位置原本因该在第二位
                secondNum = thirdNum;
            }else {//如果第三个数也小于第一个数，说明第三个数应该是排第一位的
                firstNum = thirdNum;
            }
        }
        return false;
    }
}
