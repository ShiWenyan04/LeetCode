class Solution {
    public int minimizedStringLength(String s) {
        return Method(s);
    }
    public static int Method(String string) {
		HashSet<Character> hashSet = new HashSet<Character>();
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			if(!hashSet.contains(string.charAt(i))) {
				count++;
				hashSet.add(string.charAt(i));
			}
		}
		return count;
	}
}