package Java;
//给你一个整数数组 target 和一个数组 initial ，initial 数组与 target  数组有同样的维度，且一开始全部为 0 。
//请你返回从 initial 得到  target 的最少操作次数，每次操作需遵循以下规则：
//在 initial 中选择 任意 子数组，并将子数组中每个元素增加 1 。
//答案保证在 32 位有符号整数以内。
//示例 1：
//输入：target = [1,2,3,2,1]
//输出：3
//解释：我们需要至少 3 次操作从 intial 数组得到 target 数组。
//[0,0,0,0,0] 将下标为 0 到 4 的元素（包含二者）加 1 。
//[1,1,1,1,1] 将下标为 1 到 3 的元素（包含二者）加 1 。
//[1,2,2,2,1] 将下表为 2 的元素增加 1 。
//[1,2,3,2,1] 得到了目标数组。
public class _1526_minNumberOperations {
    public static void main(String[] args) {
        int [] target = {1,2,3,2,1};
        System.out.println(minNumberOperations(target));
    }
//    使用爬坡思路
//    遇到上坡就爬坡
//    遇到下坡可以滑下去
//    最终统计爬了多少上坡
    public static int minNumberOperations(int[] target) {
        int pre= 0;
        int ans = Integer.MIN_VALUE;
        for(int x:target){
            ans+=Math.max(0,x-pre);
            pre=x;
        }
        return ans;
    }
}
