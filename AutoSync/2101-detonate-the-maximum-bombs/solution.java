class Solution {
    public int maximumDetonation(int[][] bombs) {
        return Method(bombs);
    }
    public static int Method(int [][] bombs){
        int n = bombs.length;
        List<Integer> [] map = new List[n];
        // 手动建图，认为 A 到 B 可达的条件是 AB 两点距离小于 A 的爆炸范围
        for (int i = 0; i < n; i++) {//先预留顶点的位置
            map[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                // 计算两点直线距离
                double len = Math.sqrt(Math.pow(bombs[i][0] - bombs[j][0], 2) + Math.pow(bombs[i][1] - bombs[j][1], 2));
                // 直线距离小于炸弹i的范围，那么i到j有边
                if(len <= bombs[i][2]){
                    map[i].add(j);
                }
                // 直线距离小于炸弹j的范围，那么j到i有边
                if (len <= bombs[j][2]){
                    map[j].add(i);
                }
            }
        }
        // 对 graph 计算最大的连通分量
        int res = 0;
        int[] visited = new int[n];
        Arrays.fill(visited,-1);
        for (int i = 0; i < n; i++) {//遍历当前炸弹，然后dfs能够引爆的所有点，也就是当前顶点能够指向的顶点
            res = Math.max(res,dfs(map,i,visited,i));//获得每个连接的的最长联通分量
        }
        return res;
    }
    public static int dfs(List<Integer> [] map,int temp,int []visited,int start){
        visited[temp] = start;//打标记，已经引爆过的标记为start的值，未引爆过的标记为-1
        int res = 1;
        for (Integer ele : map[temp]) {//ele是temp顶点所有的连接点
            if(visited[ele] != start ){//判断当前这个连接点是否被引爆过
                res+=dfs(map,ele,visited,start);
            }
        }
        return res;
    }
}
