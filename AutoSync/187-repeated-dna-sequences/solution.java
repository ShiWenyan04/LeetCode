class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        return Method(s);
    }
    public static List<String> Method(String s){
        HashSet<String> hashSet = new HashSet<>();
        List<String> list =new ArrayList<>();
        if (s.length() == 10){
             return list;
        }
        for (int i = 0; i <= s.length()-10; i++) {
            if(hashSet.contains(s.substring(i,i+10)) && !list.contains(s.substring(i,i+10))){
                list.add(s.substring(i,i+10));
            }else {
                hashSet.add(s.substring(i,i+10));
            }
        }
        return list;
    }
}
