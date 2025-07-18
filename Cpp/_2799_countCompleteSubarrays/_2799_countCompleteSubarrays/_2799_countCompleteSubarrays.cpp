#include < iostream>
#include <vector>
#include <unordered_map>
/*����һ���� �� ������ɵ����� nums ��
��������е�ĳ�������������������������֮Ϊ ��ȫ������ ��
�������� ��ͬ Ԫ�ص���Ŀ�����������鲻ͬԪ�ص���Ŀ��
���������� ��ȫ������ ����Ŀ��
������ �������е�һ�������ǿ����С�
ʾ�� 1��
���룺nums = [1,3,1,2,2]
�����4
���ͣ���ȫ�������У�[1,3,1,2]��[1,3,1,2,2]��[3,1,2] �� [3,1,2,2] ��*/
using namespace std;

class Solution {
public:
    int countCompleteSubarrays(vector<int>& nums) {
        int n = nums.size();
        int ans = 0;
        // �ҳ� nums �в�ͬ��Ԫ�صĸ���
        unordered_map <int,int> set;
        for (int i = 0; i < n; i++) {
            set[nums[i]]++;
        }
        int cnt = set.size();

        // ��ʼ����������
        int left = 0, right = 0;
        unordered_map <int, int> map;
        // ��������
        while (right < n) {
            map[nums[right]]++;

            // ���㵱ǰ�����еĲ�ͬԪ�ظ���
            while (map.size() == cnt) {
                ans+=(n-right); //����������ȷ�������Ժ�res ����ֻ + 1 ����Ӧ�� right �� nums.size() ���ǺϷ���β
                                         //������ += (nums.size() - right)

                // �������
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