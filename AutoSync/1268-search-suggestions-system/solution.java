class Solution {
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        return Method(products,searchWord);
    }
    public static List<List<String>> Method(String [] p,String s){
        Arrays.sort(p);
        StringBuilder sb = new StringBuilder();
        List<List<String>> ans = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            List<String> list = new ArrayList<>();
            for (int j = 0; j < p.length; j++) {
                if(p[j].startsWith(sb.toString())){
                    list.add(p[j]);
                }
                if(list.size() == 3){
                    break;
                }
            }
            ans.add(list);
        }
        return ans;
    }
}
