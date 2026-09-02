class Solution {
    public String simplifyPath(String path) {
        List<String> str = new ArrayList<>();
        for(String s : path.split("/")){
            if(s .isEmpty() || s.equals( ".")){
                continue;
            }

            if(!s.equals( "..")){
                str.add(s);
            }else if(!str.isEmpty()){
                str.remove(str.size()-1);
            }
        }
        return "/"+String.join("/",str);
    }
}
