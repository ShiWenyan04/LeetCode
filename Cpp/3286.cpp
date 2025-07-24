//
// Created by 20538 on 2025/7/24.
//

/*
* 给你一个 m x n 的二进制矩形 grid 和一个整数 health 表示你的健康值。
你开始于矩形的左上角 (0, 0) ，你的目标是矩形的右下角 (m - 1, n - 1) 。
你可以在矩形中往上下左右相邻格子移动，但前提是你的健康值始终是 正数 。
对于格子 (i, j) ，如果 grid[i][j] = 1 ，那么这个格子视为 不安全 的，会使你的健康值减少 1 。
如果你可以到达最终的格子，请你返回 true ，否则返回 false 。
注意 ，当你在最终格子的时候，你的健康值也必须为 正数
 */
#include <iostream>
#include <ostream>
#include <queue>
#include <vector>

using namespace std;

class Solution {
public:
  typedef struct Node {
    int x,y,health;
  }node;

  bool findSafeWalk(vector<vector<int>>& grid, int health) {
    // 四个方向：上、下、左、右
    vector<vector<int>> path = {{-1,0},{0,1},{1,0},{0,-1}};
    int n = grid.size();
    int m = grid[0].size();
   vector<vector<bool>>visited(n,vector<bool>(m,false));
    // 双端队列存储状态：行、列、累计代价
    deque<node> dq;
    visited[0][0] = true;
    dq.push_back({0,0,grid[0][0]});
    int minhealth = -1;
    while (!dq.empty() && minhealth < 0) {
      node nd = dq.front();
      dq.pop_front();
      int x = nd.x;
      int y = nd.y;
      // 到达终点，记录最小代价
      if (x == n-1 && y == m-1 ) {
        minhealth = nd.health;
      }
      // 探索四个方向
      for (int i = 0; i < 4; i++) {
        int tx = x + path[i][0];
        int ty = y + path[i][1];
        // 检查是否在网格范围内且未访问过
        if (tx >= 0 && tx < n && ty >= 0 && ty < m && !visited[tx][ty]) {
          visited[tx][ty] = true;
          int newhealth = nd.health+grid[tx][ty];
          // 0代价的路径放在队首，1代价的放在队尾
          if (grid[tx][ty] == 0) {
            dq.push_front({tx, ty, newhealth});
          }else {
            dq.push_back({tx,ty,newhealth});
          }
        }
      }
    }
    // 判断最小代价是否小于健康值
    return minhealth < health;
  }
};
int main() {
  Solution s;
  vector<vector<int>> grid = {{0,1,0,0,0},{0,1,0,1,0},{0,0,0,1,0}};
  int health = 1;
  cout << s.findSafeWalk(grid, health) << endl;
}