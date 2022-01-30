import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Account {

    //states the properties of an Account

    private int balance = 0;
    private String Name="";
    private int credit=1000;
    private int creditMax=1000;
    private int creditDebt=0;



    //Sets mutexs

    Semaphore sem = new Semaphore(1);

    Semaphore creditSem=new Semaphore(1);




    //account constructor

    public Account(int balance, String name){
        this.balance=balance;
        this.Name=name;
    }

    public int getBalance() {
        return balance;
    }

    public int getCredit(){return credit;}



     public int getCreditDebt(){

        //gets creditDebt

        return creditDebt;
    }


    public void withdraw(int amount) {

        try{

           //mutex acquire

            sem.acquire();

            //critical section of thread
            if(balance-amount<0){
                System.out.println(Thread.currentThread().getName()+" withdraws $"+amount+"-- Stopped: Insufficient Funds---");
                useCredit(amount);
                sem.release();
                //mutex release
                return;
            }

            //withdraws from balance

            balance-=amount;

            System.out.println(Thread.currentThread().getName()+" withdraws $"+amount+"\nBalance: $"+balance+"\n");
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
        //mutex release
        sem.release();
    }

    public void deposit(int amount) {
        try{

            //mutex acquire
            sem.acquire();

            //deposit from balance
            balance+=amount;

            System.out.println(Thread.currentThread().getName()+" deposits $"+amount+"\nBalance: $"+balance+"\n");
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
        //mutex release
        sem.release();

    }

    public void useCredit(int amount)
    {
        try {

            //a seperate mutex is utulized for when credit is changed
            creditSem.acquire();

            //allow credit withdrawl
            if((creditDebt+amount)<creditMax) {

                //you gainn debt based on amount
                creditDebt += amount;


                //credit goes down
                credit = credit - amount;
                String threadName = Thread.currentThread().getName();
                System.out.println("You have used your credit card to pay for " + threadName + "\nYou owe $" + creditDebt +" in credit. \nAmount of credit left this month: $" + credit+"\n");
            }

            //handles not having enough credit
            else
                System.out.println("You have MAXED OUT or will MAX OUT your credit card on this transaction. Figure out another way to pay!\nYou owe $"+creditDebt+" in credit.\n" );

            creditSem.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void payCredit(int amount)
    {
        //handles special function that does a withdrwal and credit "deposit"
        try {
            //handles withdrawl
            withdraw(amount);

            //mutex acquire
            creditSem.acquire();

            //pays credit
            credit=credit+amount;

            //clears debt
            creditDebt=creditDebt-amount;

            //lets you know if debt is paid
            if(creditDebt==0) {
                System.out.println("You have paid your credit debt!");
            }


            System.out.println("You owe $"+creditDebt+"\n" );

            //mutex release
            creditSem.release();


        }   catch (InterruptedException ex) {

        ex.printStackTrace();
    }

    }

    public void finalAccount()
    {
        System.out.println("Your final balance is $"+balance );
    }

}

