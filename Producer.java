import java.util.Random;
import java.util.concurrent.Semaphore;

public class Producer implements  Runnable{
    //proucer thread type

    private Account acc;
    private int money=0;
    private String name="";

    public Producer(Account acc,String name)
    {
        //constructor
        this.acc=acc;
        this.name=name;

    }

    public Producer(Account acc,int money,String name)
    {
        //handles contructor when money is set
        this.acc=acc;
        this.money=money;
        this.name=name;

    }

    public void run() {

            try {

                //sets the passed parameter name as the thread name
                Thread.currentThread().setName(name);
                //adds a random delay for thread
                Thread.sleep((int)Math.round(Math.random() * 20));
                if (money==0) {
                    //if money is not set does deposit with random number
                    acc.deposit((int) Math.round(Math.random() * 75));
                }
                //if money is set just do deposit
                else
                    acc.deposit(money);
            }
            catch(InterruptedException ex) {
                //ex.printStackTrace();
                System.out.println(ex);
            }

}
}
