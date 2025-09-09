//
// Created by 20538 on 2025/8/4.
//
#include <iostream>
#include <unordered_map>
#include <vector>
using namespace std;

#include <vector>
#include <unordered_map>
using namespace std;

class Solution {
public:
  vector<int> findDiagonalOrder(vector<vector<int>>& nums) {
    int n = nums.size();
    // 使用哈希表存储对角线元素，键为行索引+列索引
    unordered_map<int, vector<int>> matrix;

    // 遍历所有元素，按对角线和分组
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < nums[i].size(); ++j) {
        // 行索引+列索引作为键
        int key = i + j;
        // 插入到向量的开头，模拟Python的insert(0, ...)
        matrix[key].insert(matrix[key].begin(), nums[i][j]);
      }
    }

    // 找出最大的对角线和，确保按顺序遍历
    int maxKey = 0;
    for (auto& pair : matrix) {
      maxKey = max(maxKey, pair.first);
    }

    // 收集结果
    vector<int> res;
    for (int k = 0; k <= maxKey; ++k) {
      if (matrix.find(k) != matrix.end()) {
        // 将当前对角线的所有元素添加到结果中
        res.insert(res.end(), matrix[k].begin(), matrix[k].end());
      }
    }

    return res;
  }
};

int main() {
  vector<vector<int>> v = {{1, 2, 3}, {4}, { 5, 6, 7 }, {8}, {9, 10, 11}};
  Solution s;
  vector<int> ans = s.findDiagonalOrder(v);
  for (int i = 0; i < ans.size(); i++) {
    cout << ans[i] << " ";
  }
}
