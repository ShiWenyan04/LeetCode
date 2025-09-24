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
