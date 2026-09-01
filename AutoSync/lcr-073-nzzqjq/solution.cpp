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
