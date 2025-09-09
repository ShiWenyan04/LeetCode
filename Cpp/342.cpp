#include <iostream>
using namespace std;
/*
给定一个整数，写一个函数来判断它是否是 4 的幂次方。如果是，返回 true ；否则，返回 false 。
整数 n 是 4 的幂次方需满足：存在整数 x 使得 n == 4x
 */


/*如果 n 是 4 的幂，那么 n 一定也是 2 的幂。因此我们可以首先判断 n 是否是 2 的幂，在此基础上再判断 n 是否是 4 的幂。
 * 通过 n 除以 3 的余数是否为 1 来判断 n 是否是 4 的幂
 */
class Solution {
public:
  bool isPowerOfFour(int n) {
    return n > 0 && (n & (n - 1)) == 0 && n % 3 == 1;
  }
};

int main() {
  Solution s;
  cout << s.isPowerOfFour(12) << endl;
}