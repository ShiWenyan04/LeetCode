#include <vector>
#include <unordered_map>

class Solution {
public:
    long countInterestingSubarrays(std::vector<int>& nums, int modulo, int k) {
        long ans = 0;
        int n = nums.size();
        std::vector<int> a(n + 1, 0), b(n + 1, 0);

        for (int i = 1; i <= n; ++i) {
            if (nums[i - 1] % modulo == k) a[i] = 1;
            b[i] = b[i - 1] + a[i];
        }

        std::unordered_map<int, int> map;
        map[0] = 1;

        for (int i = 1; i <= n; ++i) {
            int t = map[(b[i] + modulo - k) % modulo];
            ans += t;
            map[b[i] % modulo]++;
        }

        return ans;
    }
};
