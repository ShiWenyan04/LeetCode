class Solution {
public:
  typedef struct Node {
    int x,y,health;
  }node;

  bool findSafeWalk(vector<vector<int>>& grid, int health) {
    vector<vector<int>> path = {{-1,0},{0,1},{1,0},{0,-1}};
    int n = grid.size();
    int m = grid[0].size();
   vector<vector<bool>>visited(n,vector<bool>(m,false));
    deque<node> dq;
    visited[0][0] = true;
    dq.push_back({0,0,grid[0][0]});
    int minhealth = -1;
    while (!dq.empty() && minhealth < 0) {
      node nd = dq.front();
      dq.pop_front();
      int x = nd.x;
      int y = nd.y;
      if (x == n-1 && y == m-1 ) {
        minhealth = nd.health;
      }
      for (int i = 0; i < 4; i++) {
        int tx = x + path[i][0];
        int ty = y + path[i][1];
        if (tx >= 0 && tx < n && ty >= 0 && ty < m && !visited[tx][ty]) {
          visited[tx][ty] = true;
          int newhealth = nd.health+grid[tx][ty];
          if (grid[tx][ty] == 0) {
            dq.push_front({tx, ty, newhealth});
          }else {
            dq.push_back({tx,ty,newhealth});
          }
        }
      }
    }
    return minhealth < health;
  }
};
