class Solution {
    public boolean hasIncreasingSubarrays(List<Integer> nums, int k) {
        int n = nums.size();
        int last_idx = -1;
        // 双指针做法，i为左指针，j为右指针
        for(int i = 0;i < n;){
            // 右指针移动，确定递增的数组的结束位置
            int j = i+1;
            while(j < n && nums.get(j) > nums.get(j-1)){
                j ++;
            }
            // 左指针与右指针的距离为递增数组长度
            int len = j-i;
            if(len < k){
                i=j;
                continue;
                
            }// 如果长度直接大于等于2*k说明两个递增数组都满足大于k的条件
            else if(len >= 2*k){
                return true;
            }
            else{
                //last_idx是上一个满足条件的递增数组的结束位置，默认值-1，表示不存在递增数组
                // 不等于默认值说明存在，last_pos==i-1表示上一个子数组的结束位置正好是当前子数组开始位置的前一个位置，满足连续条件，即返回true
                if(last_idx == i-1 && last_idx != -1){
                    return true;
                }
                // 记录当前合格子数组的结束位置
                last_idx = j-1;
            }
            // 移动到下一个位置继续查找
            i = j;
        }
        return false;
    }
}
