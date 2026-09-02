class Solution {
    public int minimumTotal(List<List<Integer>> f) {
        for (int i = f.size() - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                f.get(i).set(j, f.get(i).get(j) + Math.min(f.get(i + 1).get(j), f.get(i + 1).get(j + 1)));
            }
        }
        return f.get(0).get(0);
    }

}
