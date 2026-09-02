class Solution {
    public int countPrefixes(String[] words, String s) {
        return Method (words,s);
    }
    public static int Method(String []words,String s) {
		int n = words.length;
		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < s.length()+1; j++) {
				if(words[i].equals(s.substring(0,j))) {
					count ++;
					break;
				}
			}
		}
		return count ;
	}
}
