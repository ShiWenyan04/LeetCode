import java.util.StringTokenizer;
class Solution {
    public int isPrefixOfWord(String sentence, String searchWord) {
      return Method(sentence,searchWord);  
    }
    public static int Method(String sen,String sear) {
		StringTokenizer stringTokenizer = new StringTokenizer(sen," ");
		int idx = 1;
        boolean judge = false;
		while(stringTokenizer.hasMoreElements()) {
			String string = stringTokenizer.nextToken();
			if(string.startsWith(sear)) {
                judge = true;
				break;
			}
			idx++;
		}
		return judge?idx:-1;
	}
}
