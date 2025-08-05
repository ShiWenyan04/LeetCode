#include <iostream>
#include <unordered_map>
//
// Created by 20538 on 2025/8/5.
//
using namespace std;
#include <vector>
/*
* 每个水果优先使用第一个能容纳它的篮子，通过不断减少可用篮子的数量来模拟实际放置过程，最终得到未被放置的水果数量。
在每次找到合适篮子后会修改篮子数组（删除已使用的篮子），这会影响后续的遍历过程，正是这种修改保证了每个篮子只能使用一次。
 */

class Solution {
public:
  int numOfUnplacedFruits(vector<int>& fruits, vector<int>& baskets) {
    int n = fruits.size();
    int ans = n;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < baskets.size(); j++) {
        if (baskets[j] >= fruits[i]) {
          baskets.erase(baskets.begin()+j);
          ans--;
          break;
        }
      }
    }
    return ans;
  }
};
int main() {
  vector<int> fruits = {4,2,5};
  vector<int> baskets = {3,5,4};
  Solution solution;
  cout<< solution.numOfUnplacedFruits(fruits, baskets) <<endl;
}