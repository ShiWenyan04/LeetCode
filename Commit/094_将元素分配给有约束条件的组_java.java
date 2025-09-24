class Solution {
    public int[] assignElements(int[] groups, int[] elements) {
        return Method(groups,elements);
    }
   public static int[] Method(int []g,int []e){
       int max = -1;
        for (int i : g) {
            max = Math.max(max,i);
        }
        int []target = new int[max+1];
        Arrays.fill(target,-1);
        for (int i = 0; i < e.length; i++) {
            int x = e[i];
            if(x > max || target[x] >= 0){
                continue;
            }
            for (int j = x; j <= max; j+=x) {
                if(target[j] < 0){
                    target[j] = i;
                }
            }
        }
        for (int i = 0; i < g.length; i++) {
            g[i] = target[g[i]];
        }
        return g;
    }
}