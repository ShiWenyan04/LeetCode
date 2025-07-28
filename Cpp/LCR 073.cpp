
#include <iostream>
#include <vector>
using namespace std;
/*
狒狒喜欢吃香蕉。这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。
狒狒可以决定她吃香蕉的速度 K （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉，下一个小时才会开始吃另一堆的香蕉。
狒狒喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。
 */
class Solution {
public:
  int minEatingSpeed(vector<int>& piles, int h) {
    int left = 1;
    int right ;
    for (int p:piles) {
      right = max(right, p);
    }
    int k = right;
    while (left < right) {
      int mid = left + (right - left) / 2;
      long times = timeMethod(piles,mid);
      if (times > h ) {
        left = mid + 1;
      }else {
        k = mid;
        right = mid;
      }
    }
    return k;
  }
  long timeMethod(vector<int>& piles,int v) {
    long times = 0;
    for (int p:piles) {
      int cur = (p + v-1)/v;
      times+= cur;
    }
    return times;
  }
};
int main() {
  vector<int> piles = {30,11,23,4,20};
  int h = 5;
  Solution solution;
  cout << solution.minEatingSpeed(piles, h) << endl;
}