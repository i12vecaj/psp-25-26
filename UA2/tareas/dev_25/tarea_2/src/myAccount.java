class myAccount extends Thread  {

    private String nameAccount;
    private double accountBalance;
    private CuentaCorriente account;


    myAccount(String nameAccount, double accountBalance, CuentaCorriente account) {
        this.nameAccount = nameAccount;
        this.accountBalance = accountBalance;
        this.account = account;
    }


    @Override
    public void run() {
        account.earnMoney(accountBalance,nameAccount);
    }
}

