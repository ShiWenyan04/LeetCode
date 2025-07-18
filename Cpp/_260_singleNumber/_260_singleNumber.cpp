#include <iostream>
#include <unordered_map>

using namespace std;
/*����һ���������� nums������ǡ��������Ԫ��ֻ����һ�Σ���������Ԫ�ؾ��������Ρ� �ҳ�ֻ����һ�ε�������Ԫ�ء�����԰� ����˳�� ���ش𰸡�

�������Ʋ�ʵ������ʱ�临�Ӷȵ��㷨�ҽ�ʹ�ó�������ռ�����������⡣

 

ʾ�� 1��

���룺nums = [1,2,1,3,2,5]
�����[3,5]
���ͣ�[5, 3] Ҳ����Ч�Ĵ𰸡�*/

class Solution {
public :
	vector<int> singleNumber(vector<int>& nums) {
		unordered_map <int,int> map;
		for (int i : nums) {//�����
			map[i]++;
		}
		vector<int> ans;
		for (const auto& [num, occ] : map) {//����Ϊ1 �����
			if (occ == 1) {
				ans.push_back(num);
			}
		}
		return ans ;
	}
};

int main() {
	vector<int> nums = { 2,2,1,1,8,3 };

	Solution s;
	vector<int>ans = s.singleNumber(nums);
	for (int i : ans) {
		cout << i << endl;
	}
	
}