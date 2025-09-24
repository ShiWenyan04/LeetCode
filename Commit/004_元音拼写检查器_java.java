class Solution {
    public String[] spellchecker(String[] wordlist, String[] queries) {
        int n = wordlist.length;
        Set<String> origin = new HashSet<>(Arrays.asList(wordlist));
        Map<String, String> lowerToOrigin = new HashMap<>(n); // 预分配空间
        Map<String, String> vowelToOrigin = new HashMap<>(n);

        for (int i = n - 1; i >= 0; i--) {
            String s = wordlist[i];
            String lower = s.toLowerCase();
            lowerToOrigin.put(lower, s); // 例如 kite -> KiTe
            vowelToOrigin.put(replaceVowels(lower), s); // 例如 k?t? -> KiTe
        }

        for (int i = 0; i < queries.length; i++) {
            String q = queries[i];
            if (origin.contains(q)) { // 完全匹配
                continue;
            }
            String lower = q.toLowerCase();
            if (lowerToOrigin.containsKey(lower)) { // 不区分大小写的匹配
                queries[i] = lowerToOrigin.get(lower);
            } else { // 不区分大小写+元音模糊匹配
                queries[i] = vowelToOrigin.getOrDefault(replaceVowels(lower), "");
            }
        }
        return queries;
    }

    private String replaceVowels(String str) {
        char[] s = str.toCharArray();
        for (int i = 0; i < s.length; ++i) {
            char c = s[i];
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                s[i] = '?';
            }
        }
        return new String(s);
    }
}
