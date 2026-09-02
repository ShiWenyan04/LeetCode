class Solution {
    public int orangesRotting(int[][] grid) {
        return Method(grid);
    }
    public static int Method(int [][]grid){
        //1.定义2个int数组，2个一组来记录腐烂橘子的上下左右位置。腐烂橘子(0，0)
        //在矩阵中 上{-1,0}   下{1,0}  左{0,-1}   右{0,1}
        int []dx = {-1,1,0,0};
        int []dy = {0,0,-1,1};
        int step = 0;//感染次数
        int fresh = 0;//新鲜橘子数（后面用于判定是否为-1）
        int n = grid.length;
        int m = grid[0].length;
        Queue<int []> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});//将所有的腐烂橘子入队
                } else if (grid[i][j] == 1) {
                    fresh++;//记录初始新鲜橘子数
                }
            }
        }
        while(fresh>0 && !queue.isEmpty()){//有橘子且队列不空
            step++;
            //队列中现有的所有腐烂橘子都要进行一次感染
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int []index = queue.poll();//腐烂橘子的索引
                for (int j = 0; j < 4; j++) {
                    int x = index[0]+dx[j];
                    int y = index[1]+dy[j];
                    //xy不越界，并且要感染的地方是 新鲜橘子
                    if(x<n && x >=0 && y < m && y >= 0 && grid[x][y] == 1){
                        grid[x][y] = 2;
                        queue.offer(new int[]{x,y});
                        fresh--;
                    }
                }
            }
        }
        if (fresh > 0){
            return -1;
        }
        return step;
    }
}
