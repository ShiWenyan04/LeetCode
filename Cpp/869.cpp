#include <iostream>
#include <array>
#include <cmath>
/*
给定正整数 n ，我们按任何顺序（包括原始顺序）将数字重新排序，注意其前导数字不能为零。

如果我们可以通过上述方式得到 2 的幂，返回 true；否则，返回 false。
 */
using namespace std;

class Solution {
public:
  bool reorderedPowerOf2(int n) {
    //lambda表达式
    //接收一个整数 n 作为参数，返回一个 array容器（大小为 10 的数组），其中存储了数字 0-9 在 n 中出现的次数。
    auto hash = [](int n) {
      array<int, 10>cnt = {0};
      while (n) {
        cnt[n%10]++;
        n/=10;
      }
      return cnt;
    };

    auto cnt = hash(n);
    //在1e9以内  找出2的幂，然后调用lambda表达式，查看是否和n的数组相等，若相等，说明n的数字可以组成2的幂
    for (int i = 0;pow(2,i) <= 1e9;i++) {
      if (cnt == hash(pow(2,i))) {
        return true;
      }
    }
    return false;
  }
};
int main() {
  int n =1;
  cout << Solution().reorderedPowerOf2(n) << endl;
}