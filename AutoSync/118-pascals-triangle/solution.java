class Solution {
    public List<List<Integer>> generate(int numRows) {
        return Method(new ArrayList(), numRows);
    }
     public static List<List<Integer>> Method(List<Integer> com, int numRows){
        List<List<Integer>> list = new ArrayList<>();
        int[][] nums = new int[numRows][numRows];
        if(numRows == 0) return list;//行数为0，返回空
        for (int i = 0; i < numRows; i++) {//杨辉三角的首位和末尾都为1
            nums[i][0] = 1;//首
            nums[i][i] = 1;//尾
        }
        com.add(1);//第一行只有1
        list.add(new ArrayList<>(com));//第一行添加到list
        com.remove(0);//删除
        for (int i = 1; i < numRows; i++) {
            com.add(nums[i][0]);//每一行的首位1
            for (int j = 1; j < i; j++) {
                nums[i][j] = nums[i-1][j]+nums[i-1][j-1];
                com.add(nums[i][j]);
            }
            com.add(1);//末尾1
            list.add(new ArrayList<>(com));//com添加完毕，将com加到list中，形成二维数组
            com.removeAll(com);//删除,便于更新
        }
        return list;
    }
}
