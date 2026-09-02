class Solution {
    public List<List<Integer>> combine(int n, int k) {
        return Method1(n,k);
    }
      public static List<List<Integer>> Method1(int n, int k){
        ArrayList<List<Integer>> list=new ArrayList<List<Integer>>();//创建包含数组的list,即可看成是二位数组;//创建包含数组的list,即可看成是二位数组
        ArrayList<Integer> combination = new ArrayList<>();

        Method2(list, combination, n, k, 0);

        return list;
    }
    public static void Method2(ArrayList<List<Integer>> list, ArrayList<Integer> combinaton, int n, int k, int index){
        if (combinaton.size() == k){
            list.add(new ArrayList<>(combinaton));
            return;
        }
        while (index < n) {
            combinaton.add(index+1);
            Method2(list, combinaton, n, k,index+1);
            combinaton.remove(combinaton.size()-1);
            index++;
        }
    }
}
