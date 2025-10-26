package Java;
//简易银行系统
//实现 Bank 类：
//
//Bank(long[] balance) 使用下标从 0 开始的整数数组 balance 初始化该对象。
//boolean transfer(int account1, int account2, long money) 从编号为 account1 的账户向编号为 account2 的账户转帐 money 美元。如果交易成功，返回 true ，否则，返回 false 。
//boolean deposit(int account, long money) 向编号为 account 的账户存款 money 美元。如果交易成功，返回 true ；否则，返回 false 。
//boolean withdraw(int account, long money) 从编号为 account 的账户取款 money 美元。如果交易成功，返回 true ；否则，返回 false 。
public class _2043_Bank {
    public static void main(String[] args) {
        System.out.println();
    }
}
class Bank{
    long [] b;
    long n ;
    public  Bank(long[] balance) {
        b = balance;
        n = balance.length;
    }

    public boolean transfer(int account1, int account2, long money) {
        int a1 = account1-1;
        int a2 = account2-1;
        if (a1 >= 0 && a1 < n && a2 >= 0 && a2 < n && b[a1] >= money) {
            b[a1] -= money;
            b[a2] += money;
            return true;
        }
        return false;
    }

    public boolean deposit(int account, long money) {
        int a = account-1;
        if(a >= 0 && a < n && money >= 0){
            b[a] += money;
            return true;
        }
        return false;
    }

    public boolean withdraw(int account, long money) {
        int a = account-1;
        if(a >= 0 && a < n && b[a] >= money){
            b[a] -= money;
            return true;
        }
        return false;
    }
}
