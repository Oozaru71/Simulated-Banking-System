import java.util.concurrent.Semaphore;

public class Consumer implements  Runnable{
    //consumer thread

    private Account acc;
    private int money=0;
    private String name="";

    public Consumer(Account acc,int money,String name)
    {
        //handles contructor when money is set
        this.acc=acc;
        this.money=money;
        this.name=name;
    }
    public Consumer(Account acc,String name)
    {
        //constructor
        this.acc=acc;
        this.name=name;

    }
    public void run() {


        try {
           //sets the passed parameter name as the thread name
            Thread.currentThread().setName(name);
            //adds delay for thread
            Thread.sleep((int)Math.round(Math.random() * 20));

            //if money is set do deposit with money
            if (money!=0)
            {
                acc.withdraw(money);
            }
            else if((Thread.currentThread().getName()).equals("PayCredit"))
            {
                //if money is set and the name is "Paycredit" uses speccial withdrawl
                acc.payCredit(acc.getCreditDebt());
            }
            //else does withdrawl with random number
            else
                acc.withdraw((int) Math.round(Math.random() * 50));
        }
        catch(InterruptedException ex) {
            //ex.printStackTrace();
            System.out.println(ex);
        }

    }
}
