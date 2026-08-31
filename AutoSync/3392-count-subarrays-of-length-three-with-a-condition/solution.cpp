class Solution {
public:
    int countSubarrays(vector<int>& nums) {//普通暴力解决
        int ans = 0;
        for (int i = 0; i < nums.size()-2; i++)
        {
            if((nums[i+2] +nums[i]) *2 == nums[i+1]){//题目要求
                ans++;
            }
        }
        return ans;
    }
};
