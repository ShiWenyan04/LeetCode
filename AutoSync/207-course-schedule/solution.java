class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List <Integer> [] g = new ArrayList[numCourses];
        Arrays.setAll(g,i->new ArrayList<>());
        for(int [] p : prerequisites){
            g[p[1]].add(p[0]);
        }

        int [] status = new int[numCourses];
        for(int i = 0;i<numCourses;i++){
            if(status[i] == 0&&dfs(i,g,status)){
                return false;
            }
        }
        return true;
    }

    //主要是判断是否有环，有环为真
    public boolean dfs(int x,List<Integer>[] g,int [] status){
        status[x] = 1;//表示正在访问中
        for(int val: g[x]){
            //0表示未访问过
            //1表示正在访问
            //2表示已经探查过，直接跳过无需浪费时间
            if(status[val] == 1 || status[val] == 0&&dfs(val,g,status)){
                return true;//说明确实有环
            }
        }
        status[x] = 2;
        return false;
    }
}
