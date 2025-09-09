//
// Created by 20538 on 2025/6/2.
//
#include <iostream>
#include <vector>
#include <algorithm>
#include <unordered_set>
using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(NULL);
  cout.tie(NULL);

  int h,w,m;
  cin >> h >> w >> m;

  vector<pair<int, int>> targets(m); // 存储所有目标位置
  vector<int> a (h+1,0);//行
  vector<int> b (w+1,0);//列
  /*m组数据*/
  for(int i=1;i<=m;i++) {
    int x,y;
    cin >> x >> y;
    targets[i] = {x,y};
    a[x]++;
    b[y]++;
  }


  /*找出 行的最大  和 列的最大  的数目*/
  int max_a = *max_element(a.begin() + 1, a.end());
  int max_b = *max_element(b.begin() + 1, b.end());
  /*to统计最大行最大列的数量*/
  int count_a = 0, count_b = 0;
  for (int i = 1; i <= h; i++) {
    if (a[i] == max_a) count_a++;
  }
  for (int j = 1; j <= w; j++) {
    if (b[j] == max_b) count_b++;
  }
  // 统计同时出现在最大行和最大列中的目标数量
  int cnt = 0;
  for (const auto& [x, y] : targets) {
    if (a[x] == max_a && b[y] == max_b) {
      cnt++;
    }
  }

  if (cnt < count_a*count_b) {
    cout <<  max_a + max_b << endl;
  }else {
    cout <<  max_b + max_a-1 << endl;
  }
}
