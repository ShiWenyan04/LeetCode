class Solution {
    public int minNumberOperations(int[] target) {

int ans = 0, prev = 0;

for (int n : target) {

ans += Math.max(0, n-prev);

prev = n;

}

return ans;

}
}
