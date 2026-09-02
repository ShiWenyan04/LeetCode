class Solution {
    public int snakesAndLadders(int[][] board) {
        List<Integer> q = new ArrayList<>();
        q.add(1);
        int n = board.length;
        boolean[] vis = new boolean[n * n + 1];
        vis [1] = true;

        for(int step = 0;  !q.isEmpty();step++){
            List<Integer> temp = q;
            q = new ArrayList<>();
            
            for(Integer z : temp ){
                if(z == n*n){
                    return step;
                }

                for(int c =z+1;c <= Math.min(n*n,z+6);c++){
                    //把c变成下标
                    int x = (c-1)/n;
                    int y = (c-1)%n;
                    if(x%2 > 0){
                        y = n-y-1;
                    }
                    int target =board[n-1-x][y];
                    if(board[n-1-x][y] < 0){
                        target = c;
                    }

                    if(!vis[target]){
                        q.add(target);
                        vis[target] = true;
                    }



                }
            }
        }
        return -1;
    }
}
