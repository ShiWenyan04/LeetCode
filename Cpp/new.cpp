#define _CRT_SECURE_NO_WARNINGS
#include<iostream>
using namespace std;
#define MAX 100
/*
问题描述
给定n种物品和一个容量为C的背包。物品i的重量是wi，其价值为vi。问应如何选择装入背包的物品，使得装入背包中物品的总价值最大？

问题分析
0-1背包问题是一个经典的组合优化问题，属于NP完全问题。它可以通过回溯法来解决，回溯法是一种系统地搜索问题解的方法。在回溯法中，我们通过构建解空间树来探索所有可能的解，并通过剪枝策略来减少搜索空间。
*/
int n; // 物品数量
int w[MAX]; // 物品重量
int v[MAX]; // 物品价值
int c; // 背包容量
int best = 0; // 最大价值
int bestx[MAX]; // 记录最优解
int x[MAX]; // 当前解
int cw = 0; // 当前重量
int cv = 0; // 当前价值
int r = 0; // 剩余物品的总价值

void backtrack(int i) {
  if (i > n) {
    best = cv;
    for (int j = 1; j <= n; j++) {
      bestx[j] = x[j];
    }
    return;
  }
  r -= v[i];
  if (w[i] + cw <= c) { // 进入左子树
    cw += w[i];
    cv += v[i];
    x[i] = 1;
    backtrack(i + 1);
    cw -= w[i];
    cv -= v[i];
    x[i] = 0;
  }
  if (r + cv >= best) { // 进入右子树
    x[i] = 0;
    backtrack(i + 1);
  }
  r += v[i];
}

int main() {
  cin >> n >> c;
  for (int i = 1; i <= n; i++) {
    cin >> w[i] >> v[i];
    r += v[i];
  }
  backtrack(1);
  cout << best << endl;
  //选择的物品
  for (int i = 1; i <= n; i++)
    cout << bestx[i] << " ";
  return 0;
}