class Solution {
   public static int[][] insert(int[] []interval, int []newInterval) {
        List<int []> list = new ArrayList<>();
        int i = 0;
        int n =  interval.length;
//        在插入新集合之前，有一些没有交集的集合可以直接插入
        while(i < n && interval[i][1] < newInterval[0] ){
            list.add(interval[i]);
            i++;
        }
//        处理合并集，把合并后的新集合，更新成newInterval的值就可以一直遍历
        while(i < n && interval[i][0]<=newInterval[1]){
            newInterval[0]=Math.min(interval[i][0],newInterval[0]);
            newInterval[1]=Math.max(interval[i][1],newInterval[1]);
            i++;
        }
        list.add(newInterval);
//        处理合并集之后的集合，没有交叉所以可以直接加进去
        while(i < n && interval[i][0]>newInterval[1]){
            list.add(interval[i]);
            i++;
        }
        return  list.toArray(new int[list.size()][]);
    }
}
