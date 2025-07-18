#include <bits/stdc++.h>
/*
题目
某公司有3个商店A、B、C，拟将新招聘的5名员工分配给这3个商店，各商店得到新员工后，每年的赢利情况如下表所示，求分配给各商店各多少员工才能使公司的赢利最大。
输入格式:
第一行输入商店数m及员工人数n，再依次输入m+1行，每行为n+1个数，每个数（i,j）表示i商店分配j人赢利值0≤i≤m,0≤j≤n。

输出格式:
输出前m行每行两个数，分别表示商店编号及分配人数，最后一行表示公司最大赢利。
*/
using namespace std;
#define Max_N 101
int m, n;
int arr[Max_N][Max_N];
int res[Max_N][Max_N];
int dp[Max_N][Max_N];//前 i 个商店 n 个人的最大收益 dp[i][n]=dp[i-1][n-k]+dp[i][k]
int run() {
  for (int i = 1; i <= m; ++i) {
    for (int j = 1; j <= n; ++j) {
      // 前 i 个商店 分配 n 个员工的情况下，进行拆分，遍历
      for (int k = 1; k <= j; ++k) {
        int sum = dp[i - 1][j - k] + arr[i][k];
        if (sum >= dp[i][j]) {
          dp[i][j] = sum;
          // res 是存储结果的，前 i 个商店分配 n 个员工的情况下，第 i 个商店分配了 k 个人
          res[i][j] = k;
        }
      }
    }
  }
  return dp[m][n];
}

int main() {
  cin >> m >> n;
  for (int i = 0; i <= m; ++i) {
    for (int j = 0; j <= n; ++j) {
      cin >> arr[i][j];
    }
  }

  int sum = run();
  // 输出分配详情，最开始肯定是第m个商店分配了n个人
  for (int i = m; i > 0; --i) {
    cout << i << " " << res[i][n] << endl;
    //把分配的人减去，n 就变成了前 i - 1 个商店分配的人数了
    n -= res[i][n];
  }
  cout << sum;
  return 0;
}