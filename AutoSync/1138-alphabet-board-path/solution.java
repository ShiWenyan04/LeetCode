class Solution {
    public String alphabetBoardPath(String target) {
        return Method(target);
    }
    public static String Method(String s){
        String str = "";
        String[][] map = {{"abcde"},{"fghij"},{"klmno"},{"pqrst"},{"uvwxy"},{"z"}};
        int j = 0;
        String[] temp = {"","0"};
        for (int i = 0; i < s.length(); i++) {
            for (; j < 6; j++) {
                if ( (j<5 && s.charAt(i) <= map[j][0].charAt(4) && s.charAt(i) >= map[j][0].charAt(0)) || (( j==5) && (s.charAt(i) == 'z'))){
                    Method2(s.charAt(i),map,j,Integer.parseInt(temp[1]),temp);
                    str+=temp[0];
                    break;
                }else if(Integer.parseInt(temp[1])!=0&&s.charAt(i) == 'z'){
                    Method2(s.charAt(i),map,j,Integer.parseInt(temp[1]),temp);
                    str+=temp[0];
                    j--;
                } else if (s.charAt(i) < map[j][0].charAt(0)) {
                    str+="U";
                    j=j-2;
                } else if (s.charAt(i) > map[j][0].charAt(4)) {
                    str+="D";
                }
            }
        }
        return str;
    }
    public static String[] Method2(char ch, String[][] map, int i,int j,String[] temp) {
        String str = "";
        int  index = j;
        if (ch == 'z' && i != 5){
            for (int k = j; k >0 ; k--) {
                str+="L";
                index--;
            }
        }else if(ch == 'z'&& i == 5){
            str+="!";
        }else {
            for (int k = j; k < map[i][0].length(); k++) {
                if (map[i][0].charAt(k) < ch) {
                    str += "R";
                    index++;
                } else if (map[i][0].charAt(k) > ch) {
                    str += "L";
                    index--;
                    k-=2;
                } else {
                    str += "!";
                    break;
                }
            }
        }
        temp[0] = str;
        temp[1] = String.valueOf(index);
        return temp;
    }
}
