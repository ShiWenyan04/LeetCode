class Solution {
    public boolean canReach(int[] arr, int start) {
        boolean [] visited = new boolean[arr.length];
        return Method(arr,start,visited);
    }
     public static boolean Method(int []arr,int start,boolean [] visited){
        if(start > arr.length-1 || start < 0){//起始位置超过数组最大索引
            return false;
        }
        if (arr[start] == 0){//当前位置为0
            return true;
        }
        if (visited[start]){//如果当前位置访问过，就直接打断，即visited[start]=true
            return false;
        }else visited[start] = true;//没访问过就归为访问过
        return Method(arr,arr[start]+start,visited) || Method(arr,start-arr[start],visited);
//        以上都不满足，就进入递归，分别为 i + arr[i]和 i - arr[i] 到达的位置，将这个位置设为start，然后判断是否为0
    }
}
