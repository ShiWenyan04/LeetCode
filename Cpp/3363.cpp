//
// Created by 20538 on 2025/8/8.
//
#include <iostream>

#include <ostream>
#include <vector>

using namespace std;
const int INF = 0x3f3f3f3f;
class Solution {
public:
  int maxCollectedFruits(vector<vector<int>>& fruits) {
    int n = fruits.size();
    int ans = 0;
    //（0，0）位置的小孩一定会走对角线，因为步数限制了n-1
    for (int i = 0; i < n; i++) {
      ans+=fruits[i] [i];
      fruits[i][i] = 0;
    }
    //右上角的小孩  三角矩阵dp
    vector<vector<int>> dp1(n, vector<int>(n, -INF));
    dp1[0][n-1] = fruits[0][n-1];
    for (int i = 1; i < n; i++) {
      for (int j = n-1; j >= i; j--) {
        dp1[i][j] = dp1[i-1][j];
        if (j > 0) dp1[i][j] = max(dp1[i][j],dp1[i-1][j-1]);
        if (j+1<n) dp1[i][j] = max(dp1[i][j],dp1[i-1][j+1]);
        dp1[i][j]+=fruits[i][j];
      }
    }

    //左下角的小孩  三角矩阵dp 旋转一下
    vector<vector<int>> dp2(n, vector<int>(n, -INF));
    dp2[n-1][0] = fruits[n-1][0];
    for (int j = 1; j < n; j++) {
      for (int i = n-1; i >= j; i--) {
        dp2[i][j] = dp2[i][j-1];
        if (i > 0) dp2[i][j] = max(dp2[i][j],dp2[i-1][j-1]);
        if (i+1 < n) dp2[i][j] = max(dp2[i][j],dp2[i+1][j-1]);
        dp2[i][j]+=fruits[i][j];
      }
    }
    ans += (dp1[n-1][n-1]+dp2[n-1][n-1]);
    return ans;
  }
};
int main() {
  vector<vector<int>> fruits = {{ 1, 2,3,4},{5,6,8,7},{9,10,11,12},{13,14,15,16} };

  Solution solution;
  cout << solution.maxCollectedFruits(fruits) << endl;
}