#include < iostream>
#include <vector>
#include <unordered_map>
/*给你一个由 正 整数组成的数组 nums 。
如果数组中的某个子数组满足下述条件，则称之为 完全子数组 ：
子数组中 不同 元素的数目等于整个数组不同元素的数目。
返回数组中 完全子数组 的数目。
子数组 是数组中的一个连续非空序列。
示例 1：
输入：nums = [1,3,1,2,2]
输出：4
解释：完全子数组有：[1,3,1,2]、[1,3,1,2,2]、[3,1,2] 和 [3,1,2,2] 。*/
using namespace std;

class Solution {
public:
    int countCompleteSubarrays(vector<int>& nums) {
        int n = nums.size();
        int ans = 0;
        // 找出 nums 中不同的元素的个数
        unordered_map <int,int> set;
        for (int i = 0; i < n; i++) {
            set[nums[i]]++;
        }
        int cnt = set.size();

        // 初始化滑动窗口
        int left = 0, right = 0;
        unordered_map <int, int> map;
        // 滑动窗口
        while (right < n) {
            map[nums[right]]++;

            // 计算当前窗口中的不同元素个数
            while (map.size() == cnt) {
                ans+=(n-right); //！！！！！确定左右以后，res 不能只 + 1 而是应该 right 到 nums.size() 都是合法结尾
                                         //所以是 += (nums.size() - right)

                // 收缩左边
                map[nums[left]]--;
                if (map[nums[left]] == 0) {
                    map.erase(nums[left]);
                }
                left++;
            }
            right++;
        }
        return ans;
    }
};
int main() {
    vector<int> nums = { 1,3,1,2,2 };
    Solution s;
    cout << s.countCompleteSubarrays(nums) << endl;
}