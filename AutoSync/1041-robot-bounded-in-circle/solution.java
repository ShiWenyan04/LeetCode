class Solution {
    public boolean isRobotBounded(String instructions) {
        return Method(instructions);
    } public static boolean Method(String instructions){
        int []dx = {0,1,0,-1};
        int []dy = {1,0,-1,0};
        int x = 0,y = 0;
        int times = 0;
        int k = 0;
        while(k<4){
            for (int i = 0; i < instructions.length(); i++) {
                if (instructions.charAt(i) == 'G'){
                    x += dx[times % 4];
                    y += dy[times % 4];
                }
                if (instructions.charAt(i) == 'L'){
                    times=(times-1+4);
                }
                if (instructions.charAt(i) == 'R'){
                    times=(times+1+4);
                }
            }
            if (k == 3 && x!=0 && y!=0){
                break;
            }else if (x==0&&y==0){
                return true;
            }
            k++;
        }
        return false;
    }
}
