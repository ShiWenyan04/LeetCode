#include<iostream>
#include <vector>
#include <numeric>
#include<algorithm>
/*给定一个长度为 n 的整数数组 nums 。

假设 arrk 是数组 nums 顺时针旋转 k 个位置后的数组，我们定义 nums 的 旋转函数  F 为：

F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1]
返回 F(0), F(1), ..., F(n-1)中的最大值 。

生成的测试用例让答案符合 32 位 整数。

 

示例 1:

输入: nums = [4,3,2,6]
输出: 26
解释:
F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
所以 F(0), F(1), F(2), F(3) 中的最大值是 F(3) = 26 。
示例 2:

输入: nums = [100]
输出: 0*/
using namespace std;

class Solution {
public ://规律：向右轮转一次，下一个数组计算的值 等于 当前数组计算的值 + 数组之和 -数组长度*当前数组的最后一个值
	int maxRotateFunction(vector<int >& nums) {
		int n = nums.size();
		vector<int> f(n);
		for (int i = 0; i < n; i++) {//计算初始数组的值
			f[0] += nums[i] * i;
		}
		int sum = accumulate(nums.begin(), nums.end(), 0);//计算数组之和
		for (int i = 1; i < n; i++) {
			f[i] = f[i - 1] + sum - n * nums[n - i];
		}

		return *max_element(f.begin(),f.end());//max_element()返回的是一个迭代器，需要用指针指向元素
	}
};

int main() {
	vector<int>nums = { 4,3,2,6 };
	Solution s;
	cout << s.maxRotateFunction(nums) << endl;
}