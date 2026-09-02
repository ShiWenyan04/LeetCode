class Solution {
     public static List<List<String>> groupAnagrams(String[] strs) {
        
        HashMap<String, List<String>> map = new HashMap<>();
        for(String s: strs){
            char [] ch =  s.toCharArray();
            Arrays.sort(ch);
            String key = Arrays.toString(ch);
            List<String> list =map.getOrDefault(key,new ArrayList<>());
            list.add(s);
            map.put(key,list);
        }
        return  new ArrayList<>(map.values());
    }
}
