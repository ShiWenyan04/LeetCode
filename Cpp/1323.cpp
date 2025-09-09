#include <iostream>

#include <ostream>
#include <string>
//
// Created by 20538 on 2025/8/16.
//
using namespace std;

class Solution {
public:
  int maximum69Number (int num) {
        string s = to_string(num);
    for (int i = 0; i < s.size(); i++) {
      if (s[i] == '6') {
        s[i] = '9';
        break;
      }
    }
    return stoi(s);
  }
};
int main() {
  int num = 9669;
  Solution s;
  cout << s.maximum69Number(num) << endl;
}