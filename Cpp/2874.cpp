#include <iostream>

#include <ostream>
#include <vector>

// 令数组 nums 的长度为 n。根据值公式 (nums[i]−nums[j])×nums[k] 可知，当固定 j
// 时，nums[i] 和 nums[k] 分别取 [0,j) 和 [j+1,n)
// 的最大值时，三元组的值最大。我们使用 leftMax[j] 和 rightMax[j] 维护前缀 [0,j)
// 最大值和后缀 [j+1,n) 最大值，依次枚举 j，计算值
// (leftMax[j]−nums[j])×rightMax[j]，返回最大值（若所有值都为负数，则返回 0）。


using namespace std;
class Solution {
public:
  long long maximumTripletValue(vector<int> &nums) {
    int n = nums.size();
    vector<int> left_max(n, 0);
    vector<int> right_max(n, 0);
    for (int i = 1; i < n; i++) {
      left_max[i] = max(left_max[i - 1],
                        nums[i - 1]); // left_max[i]表示从[0,j)的最大值
      right_max[n - 1 - i] =
          max(right_max[n - i],
              nums[n - i]); // right_max[i]表示从[j+1,n)的最大值
    }
    long long result = 0;
    for (int j = 1; j < n -1; j++) {
      result = max(result, (long long)(left_max[j]-nums[j])*right_max[j]);
    }
    return result;
  }
};


int main() {
  vector<int> nums = {12,6,1,2,7};
  Solution s;
  cout << s.maximumTripletValue(nums) << endl;
}