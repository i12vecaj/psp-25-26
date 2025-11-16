import java.util.Random;

class CuentaCorriente {

    private double accountBalance;


     CuentaCorriente(double accountBalance) {
         try{
             Thread.sleep(10);
             this.accountBalance = accountBalance;
         }catch(InterruptedException e){
            throw  new RuntimeException(e);
         }

     }

     public double getAccountBalance() {
         try{
             Thread.sleep(10);
             return accountBalance;
         }catch(InterruptedException e){
             throw  new RuntimeException(e);
         }
     }

     public void setAccountBalance(double accountBalance) {

         try{
             Thread.sleep(10);
             this.accountBalance = accountBalance;
         }catch(InterruptedException e){
             throw  new RuntimeException(e);
         }
     }

     public synchronized void earnMoney (double addAccountBalance, String nameAccount){

         try{
             Thread.sleep(10);
             double value=getAccountBalance()+addAccountBalance;
             System.out.println("La cuenta "+nameAccount+" añadió "+ addAccountBalance+ " a la cuenta que antes tenía "+getAccountBalance()+" por lo tanto ahora tiene "+value);

             setAccountBalance(getAccountBalance()+addAccountBalance);
         }catch(InterruptedException e){
             throw  new RuntimeException(e);
         }

     }

 }
