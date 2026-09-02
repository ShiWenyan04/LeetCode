class Solution {
    public int wiggleMaxLength(int[] nums) {
        return Method(nums);
    }
    public static int Method(int[] nums){
        int len = 0;
        int pre = 0 , sub;
        for (int i = 0; i < nums.length-1; i++) {
            sub = nums[i+1] - nums[i];//差值
            if(i != 0 && (sub>0 && pre > 0 || sub<0 && pre<0 )|| sub == 0){//根据题意，如果两个相邻的差值同号，或者为0，则不符合摆动这一说法，只需要跳过即可
                continue;
            }
            pre = sub;//倘若符合摆动，那么保存当前值，便于下一次判别
            len +=1;//长度加一
        }
        return len+1;
    }
}
