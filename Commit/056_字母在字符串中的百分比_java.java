class Solution {
    public int percentageLetter(String s, char letter) {
        int len = s.length();
        int count = 0;
        for(int i = 0;i <len;i++){
            if(s.charAt(i) == letter){
                count++;
            }
        }
        return (int)(((double)count*1.0/len)*100);
    }
}