#include <cmath>
//
// Created by 20538 on 2025/8/7.
//
using namespace std;
#include <iostream>
#include <algorithm>
#include <vector>
#include <unordered_map>


class Solution {
public:
  int numOfUnplacedFruits(vector<int>& fruits, vector<int>& baskets) {
    int n = fruits.size();
    int m = sqrt((n));//分块的大小
    int section =  (n+m-1)/m;//分块的个数
    int ans = n;
    vector<int > maxV(section);

    //每个块的最大值分别存放
    for (int i = 0;i < n;i++) {
      maxV[i/m] = max(maxV[i/m], baskets[i]);
    }

    for (int i = 0;i < n;i++) {
      //遍历分块
      for (int idx = 0; idx < section; idx++) {
        if (maxV[idx] < fruits[i]) {//不满足条件  遍历下一个分块
          continue;
        }

        maxV[idx] = -1;//当前块最大值为-1，等会重新定义最大值
        int st = idx*m;//当前快的首尾，考虑块为最后一块时，结尾应为n-1
        int end = min((idx + 1) * m - 1, n - 1);
        bool found = false;//默认为该水果没找到适合的篮子
        for (int j = st; j <= end; j++) {
          //如果可以放，则把篮子改为-1，表示已经放过
          if (baskets[j] >= fruits[i] && !found) {
            baskets[j] = -1;
            ans--;
            found = true;
          }
          maxV[idx] = max(maxV[idx], baskets[j]);
        }
        if (found) {
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