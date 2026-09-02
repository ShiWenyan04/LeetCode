class Solution {
    public static int [][] merge(int [][] matrix) {
        int n = matrix.length;
        Arrays.sort(matrix, (p,q)->p[0]-q[0]);
        List<int[]> list = new ArrayList<>();
        for(int[] x :  matrix){
            int left = x[0];
            int m = list.size();
            if(m> 0 && left <= list.get(m- 1)[1] ) {
                int right = list.get(m - 1)[1];
                list.get(m-1)[1] = Math.max(right,x[1]);
            }else{
                list.add(x);
            }
        }
        return list.toArray(new int[list.size()][]);
    }
}
