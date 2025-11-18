package Java;
//有两种特殊字符：
//第一种字符可以用一比特 0 表示
//第二种字符可以用两比特（10 或 11）表示
//给你一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一个一比特字符，则返回 true 。
public class _717_isOneBitCharacter {
    public static void main(String[] args) {
        int[] bits = {0,0,0,1,1,0};
        System.out.println();
    }
//    由于一比特为0，二比特为10或11，所以只需要判断第一位为0还是1
//    如果为0则说明当前为一比特，只需要跳过当前位
//    如果为1，则说明时二比特打头的数字，要调过当前的二比特，需要跳过两位
//    到最后，如果剩余一个数字，则说明符合条件，如果不剩余，则说明不符合条件
    public static boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        int i = 0;
        while(i < n-1){// 循环直到剩下至多一个数字
            i+=bits[i]+1;// 如果 bits[i] == 1 则跳过当前位和下一位
        }
        return i == n-1;
    }
}
