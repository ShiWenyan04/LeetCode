class Solution {
    public int maxBottlesDrunk(int numBottles, int numExchange) {
        return Method(numBottles,numExchange);
    }
    public static int Method(int nb,int ne){
         int empty = nb;
         int sum = nb;
         while(ne <= empty){
             empty-=ne;
             sum ++;
             empty++;
             ne++;
         }
         return sum;
    }
}
