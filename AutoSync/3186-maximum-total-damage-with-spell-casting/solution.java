class Solution {
    public long maximumTotalDamage(int[] power) {
        return Method(power);
    }
     public static long Method(int [] power){
        HashMap<Long,Long> hashMap = new HashMap<>();
        for (int i : power){
            hashMap.put((long)i,hashMap.getOrDefault((long)i,0l)+(long)i);//相同聚集
        }
        List<Map.Entry<Long,Long>> list = new ArrayList<>(hashMap.entrySet());//创建新的数组
        Collections.sort(list, Map.Entry.comparingByKey());//按照伤害值排序
        
        long [] f = new long[hashMap.size()];
        f[0] = list.get(0).getValue();
        
        for (int i = 1; i < list.size(); i++) {
            f[i] = f[i-1];// 假设不选 arr[i]
            int j = i-1;
            while(j >= 0 && list.get(j).getKey() >= list.get(i).getKey()-2){
                j--;
            }
            if (j >= 0){
                f[i] = Math.max(f[i],f[j]+list.get(i).getValue());
            }else {
                f[i] = Math.max(f[i],list.get(i).getValue());
            }
        }
        return f[list.size()-1];
    }
}
