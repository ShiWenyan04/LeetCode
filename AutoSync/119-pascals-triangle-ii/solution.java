class Solution {
    public List<Integer> getRow(int rowIndex) {
        return Method(rowIndex, new ArrayList<>());
    }
     public static List<Integer> Method(int rowIndex, List<Integer> list) {
        int len = rowIndex+1;
        int [][]nums = new int[len][len];
        for (int i = 0; i < len; i++) {
            nums[i][0] = 1;
            nums[i][i] = 1;
        }
        list.add(nums[rowIndex][0]);
        if (rowIndex == 0){
            return list;
        }else if(rowIndex == 1){
            list.add(1);
            return list;
        }
        for (int i = 2; i < len; i++) {
            for (int j = 1; j < i; j++) {
                nums[i][j] = nums[i-1][j] + nums[i-1][j-1];
                if (i == rowIndex){
                    list.add(nums[i][j]);
                }
            }
            if (i == len-1){
                list.add(nums[rowIndex][rowIndex]);
            }
        }
        return list;
    }
}
