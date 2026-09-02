class Solution {
public:
  bool reorderedPowerOf2(int n) {
        auto hash = [](int n) {
          array<int, 10>cnt = {0};
          while (n) {
            cnt[n%10]++;
            n/=10;
          }
          return cnt;
        };

    auto cnt = hash(n);
    for (int i = 0;pow(2,i) <= 1e9;i++) {
      if (cnt == hash(pow(2,i))) {
        return true;
      }
    }
    return false;
  }
};
