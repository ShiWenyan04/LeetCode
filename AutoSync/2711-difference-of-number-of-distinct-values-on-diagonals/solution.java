class Solution {
    public int[][] differenceOfDistinctValues(int[][] grid) {
        return Method(grid);
    }
    public static int [][]Method(int [][]grid){
		int [][]ans = new int [grid.length][grid[0].length];
		for (int i = 0; i < ans.length; i++) {
			for (int j = 0; j < ans[0].length; j++) {
				HashSet<Integer>hashSet1 = new HashSet<Integer>();
				int x = i-1,y = j-1;
				while(x >=0 && y >=0) {
					hashSet1.add(grid[x][y]);
					x-=1;
					y-=1;
				}
				HashSet<Integer>hashSet2 = new HashSet<Integer>();
				x = i+1;
				y = j+1;
				while(x <grid.length && y <grid[0].length) {
					hashSet2.add(grid[x][y]);
					x+=1;
					y+=1;
				}
				ans[i][j] = Math.abs(hashSet1.size()-hashSet2.size());
			}
		}
		return ans;
	}
}
