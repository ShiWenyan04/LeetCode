#include < iostream>
#include <vector>
/*����һ���������� nums �����㷵�س���Ϊ 3 �� ������ �������������һ�����͵��������ĺ�ǡ��Ϊ�ڶ�������һ�롣
������ ָ����һ������������ �ǿ� ��Ԫ�����С�
ʾ�� 1��
���룺nums = [1,2,1,4,1]
�����1
���ͣ�
ֻ�������� [1,4,1] ���� 3 ��Ԫ���ҵ�һ���͵���������֮�����м����ֵ�һ�롣number.*/
using namespace std;

class Solution {
public:
    int countSubarrays(vector<int>& nums) {//��ͨ�������
        int ans = 0;
        for (int i = 0; i < nums.size()-2; i++)
        {
            if((nums[i+2] +nums[i]) *2 == nums[i+1]){//��ĿҪ��
                ans++;
            }
        }
        return ans;
    }
};
int main() {
    vector<int> nums = { 1,2,1,4,1 };
    Solution s;
    cout << s.countSubarrays(nums) << endl;
}