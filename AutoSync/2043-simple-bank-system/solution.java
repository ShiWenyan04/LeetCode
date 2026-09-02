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
