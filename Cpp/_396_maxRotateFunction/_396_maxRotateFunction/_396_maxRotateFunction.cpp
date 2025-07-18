#include<iostream>
#include <vector>
#include <numeric>
#include<algorithm>
/*����һ������Ϊ n ���������� nums ��

���� arrk ������ nums ˳ʱ����ת k ��λ�ú�����飬���Ƕ��� nums �� ��ת����  F Ϊ��

F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1]
���� F(0), F(1), ..., F(n-1)�е����ֵ ��

���ɵĲ��������ô𰸷��� 32 λ ������

 

ʾ�� 1:

����: nums = [4,3,2,6]
���: 26
����:
F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
���� F(0), F(1), F(2), F(3) �е����ֵ�� F(3) = 26 ��
ʾ�� 2:

����: nums = [100]
���: 0*/
using namespace std;

class Solution {
public ://���ɣ�������תһ�Σ���һ����������ֵ ���� ��ǰ��������ֵ + ����֮�� -���鳤��*��ǰ��������һ��ֵ
	int maxRotateFunction(vector<int >& nums) {
		int n = nums.size();
		vector<int> f(n);
		for (int i = 0; i < n; i++) {//�����ʼ�����ֵ
			f[0] += nums[i] * i;
		}
		int sum = accumulate(nums.begin(), nums.end(), 0);//��������֮��
		for (int i = 1; i < n; i++) {
			f[i] = f[i - 1] + sum - n * nums[n - i];
		}

		return *max_element(f.begin(),f.end());//max_element()���ص���һ������������Ҫ��ָ��ָ��Ԫ��
	}
};

int main() {
	vector<int>nums = { 4,3,2,6 };
	Solution s;
	cout << s.maxRotateFunction(nums) << endl;
}