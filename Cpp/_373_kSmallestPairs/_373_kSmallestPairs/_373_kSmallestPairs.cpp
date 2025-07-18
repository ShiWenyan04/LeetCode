#include<iostream>
#include<queue>
/*���������� �ǵݼ�˳������ ���������� nums1 �� nums2 , �Լ�һ������ k ��
����һ��ֵ (u,v)�����е�һ��Ԫ������ nums1���ڶ���Ԫ������ nums2 ��
���ҵ�����С�� k ������ (u1,v1),  (u2,v2)  ...  (uk,vk) ��
ʾ�� 1:
����: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
���: [1,2],[1,4],[1,6]
����: ���������е�ǰ 3 ������
     [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
ʾ�� 2:
����: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
���: [1,1],[1,1]
����: ���������е�ǰ 2 ������
     [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]*/
using namespace std;

class Solution {
public:
    vector<vector<int>> kSmallestPairs(vector<int>& nums1, vector<int>& nums2, int k) {
        priority_queue < tuple<int, int, int > > q;//���ȶ��д洢����һ����Ԫ��
        int n = nums1.size();
        int m = nums2.size();
        for (int i = 0; i < min(n,k); i++) {// ���� k ��
            q.emplace(nums1[i] - nums2[0],i,0);// ȡ�෴�����С����
        }
        //���ȶ���Ĭ�������ѣ�Ϊ���ܹ�������С��Ԫ�أ���Ҫ����ȡ��
        //���ڣ�0��1���ͣ�1��0�����漰���ظ�ȡֵ������Ĭ�Ϲ涨ȡ��1��0���������

        

        vector<vector<int>> ans;
        while (ans.size() < k) {
            auto [sum, i, j] = q.top();
            q.pop();//ÿ�δӶ��е�1��Ԫ�أ����Ԫ�ض�Ӧһ����С�͵���ԣ�i��j��
            ans.push_back({ nums1[i],nums2[j] });//������������ans��
            if (j + 1 < m) {//nums2����Եĵڶ���Ԫ���Ƿ��Ѿ����꣬δ���������µ� ����nums[i],nums[j+1])�� 
                q.emplace(-nums1[i] - nums2[j + 1], i, j + 1);
            }
        }
        return ans;
    }
};
int main() {
    int k = 3;
    vector<int> nums1 = { 1,7,11 };
    vector<int> nums2 = { 2,4,6 };
    Solution s;
    vector<vector<int>> ans = s.kSmallestPairs(nums1, nums2, k);
    for (vector<int> v : ans) {
        cout << "[" << endl;
        cout<< v[0] << ","<<v[1] << endl;
        cout << "]" << endl;
        
    }
    return 0;
}
