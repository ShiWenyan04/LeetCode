class Solution {
public:
    vector<vector<int>> kSmallestPairs(vector<int>& nums1, vector<int>& nums2, int k) {
        priority_queue < tuple<int, int, int >> q;
        int n = nums1.size();
        int m = nums2.size();
        for (int i = 0; i < min(n,k); i++) {// 至多 k 个
            q.emplace(-nums1[i] - nums2[0],i,0);// 取相反数变成小顶堆
        }
        
        vector<vector<int>> ans;
        while (ans.size() < k) {
            auto [_, i, j] = q.top();
            q.pop();
            ans.push_back({ nums1[i],nums2[j] });
            if (j + 1 < m) {
                q.emplace(-nums1[i] - nums2[j + 1], i, j + 1);
            }

        }
        return ans;
    }
};
