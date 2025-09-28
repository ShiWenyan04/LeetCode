package Java;

import java.util.List;

public class _120_minimumTotal {
    public static void main(String[] args) {
        int[][] triangle = {{2}, {3, 4}, {6, 5, 7}, {}};
        System.out.println();
    }
    public static int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        for (int i = n-2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++) {
                int cur = triangle.get(i).get(j);
                int newNum = Math.min(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j+1));
                triangle.get(i).set(j,cur+newNum)  ;
            }
        }
        return triangle.get(0).get(0);
    }
}
