#include <iostream>
#include <math.h>
#include <ostream>
#include <vector>

using namespace std;
/*
 *利用三进制来判断，因为如果一个数能表示成不同三的幂之和，那么它的三进制表示中每一位只能是 0 或者 1
 *（每一位对应是否使用相应的三的幂 ，1 表示使用，0 表示不使用 ）
 */

class Solution {
public:
  bool checkPowersOfThree(int n) {
    while (n > 0) {
      if (n % 3 == 2) { // 三进制位出现 2 ，说明不能由不同三的幂组成
        return false;
      }
      n /= 3;
    }
    return true;
  }
};
/*
class Solution {
public:
  bool judge = false;
  bool checkPowersOfThree(int n) {
    vector<bool> visited (10, false);
    dfs(n,visited);
    return judge;
  }
  void dfs(int n,vector<bool> &visited) {
    if (n == 0) {
      judge = true;
      return;
    }
    if (n < 0) {
      return;
    }
    for (int i = 0; i <10; ++i) {
      if (visited[i]) {
        continue;
      }
      visited[i] = true;
      dfs(n-pow(3,i),visited);
      visited[i] = false;
    }
  }
};
*/
int main() {
  int n =12;
  Solution s;
  cout << s.checkPowersOfThree(n) << endl;
}
