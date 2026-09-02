class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> ans = new ArrayList<>();
        HashSet<String> set = new HashSet<>(wordDict);
        dfs(s,0,ans,new LinkedList<>(),set);
        return ans;
    }
    public static void dfs(String s, int index,List<String> ans, Deque<String> path,HashSet<String> set){
        if (index == s.length()){
            ans.add(String.join(" ",path));
            return ;
        }
        for (int i = index; i < s.length(); i++) {
            String str = s.substring(index,i+1);
            if(set.contains(str)){
                path.add(str);
                dfs(s,i+1,ans,path,set);
                path.removeLast();
            }
        }
    }
}
