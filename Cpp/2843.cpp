#include <iostream>
#include <string>
//
// Created by 20538 on 2025/8/12.
//
using namespace std;
class Solution {
public:
  bool isValid(int n) {
    string s = to_string(n);
    if (s.size() & 1) {
      return false;
    }
    int diff = 0;
    //若两边的和都相等，那么他们的差肯定为0
    for (int i = 0;i<s.size()/2;i++) {
      diff += s[i]-s[s.size()-1-i];
    }
    return diff == 0;
  }
  int countSymmetricIntegers(int low, int high) {
    int ans = 0;
    for (int i = low; i <= high; i++) {
      if (isValid(i)) {
        ans++;
      }
    }
    return ans;
  }
};


int main() {
  int low = 0, high = 100;
  Solution s;
  cout << s.countSymmetricIntegers(low, high) << endl;
}