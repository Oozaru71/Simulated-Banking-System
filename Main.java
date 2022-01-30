import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class GFG {
    public static void main(String[] args) {

        //String array with deposti events

        String[] dEvents = new String[]{"Mom allowance", "Tutoring", "Found money", "Others"};

        //String array with withdrawl events

        String[] wEvents = new String[]{"Host Pizza party", "Family troubles", "UBER ride", "MC Donalds", "Snacks", "Computer game", "New Socks"};

        //String array with days of week

        String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        //String array with food times

        String[] food = new String[]{"Breakfast", "Lunch", "Dinner"};

        //An account object is made

        Account mike = new Account(500, "mike");

        //First for loop handles 3 semesters
for(int y=0;y<3;y++) {
    System.out.println("A School Semester for 2020 has started!\n");
    //loop handles 4 months for each semester
    Thread Financial = new Thread(new Producer(mike, 20200, "FinancialAid"));
    Financial.start();
    //This should work as a way to give priority to this thread over the others
    try {
        Financial.join();
    } catch (Exception ex) {
        System.out.println(ex);
    }

    for (int m = 0; m < 4; m++) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++\n");
        System.out.println("A new month has started!\n");
        System.out.println("++++++++++++++++++++++++++++++++++++++++\n");


        for (int w = 1; w < 5; w++) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");

            System.out.println("Week " + w + "\n");

            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            for (int k = 0; k < 7; k++) {

                //creates a pool for worker threads

                ExecutorService app = Executors.newCachedThreadPool();
                System.out.println(days[k] + "\n");


                try {
                    if(w==1&&k==0)
                    {
                        //creates a dorm and bill consumer at the beginning of every month
                        app.execute(new Thread(new Consumer(mike,300,"Dorm")));

                        app.execute(new Thread(new Consumer(mike, 300,"Food Bill")));
                    }
                    if(w==1&&k==0&&m==0)
                    {
                        //creates a tuition, books, and fees consumer at the beginning of every semester
                        app.execute(new Thread(new Consumer(mike,12000,"Tuition")));
                        app.execute(new Thread(new Consumer(mike,500,"Books")));
                        app.execute(new Thread(new Consumer(mike,500,"Fees")));
                    }

                    for (int i = 0; i < 4; i++) {
                        if (i == 1) {
                            //a Thread food Consumer is added to the pool
                            app.execute(new Thread(new Consumer(mike, food[i])));
                        }


                        //a withdrawal thread is added to the pool, the name is randomized and sent to function
                        Random rand2 = new Random();
                        int randomWEvent = rand2.nextInt(wEvents.length);
                        app.execute(new Thread(new Consumer(mike, wEvents[randomWEvent])));
                    }
                    if (w == 4 && k == 6) {

                        //creat a specific producer object

                        Thread prod = new Thread(new Producer(mike, 1200, "Part-time paycheck"));
                        prod.start();

                        //makes it so that the part time is desposited before the pay credit
                        try {
                            prod.join();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }

                        //create Consumer thread that pays credit card at the end of the month
                        Thread credit = new Thread(new Consumer(mike, "PayCredit"));
                        credit.start();

                        //makes it so that credit is paid at the end of the month
                        try {
                            credit.join();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    } else {
                        //creatres a daily producer
                        Random rand = new Random();
                        int randomDEvent = rand.nextInt(dEvents.length);
                        //execute producer thread
                        app.execute(new Thread(new Producer(mike, dEvents[randomDEvent])));

                        //shuts down working threads(does not allownew threads to join)
                        app.shutdown();

                        //Makes sure it waits for the daily threads to finish before going to the next day
                        while (!app.isTerminated()) {

                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //shuts down working threads(does not allow new threads to join)
                app.shutdown();
                //Makes sure it waits for the daily threads to finish before going to the next day
                while (!app.isTerminated()) {

                }

                System.out.println("-------------------------------------------------");
                    }
                }

            }
        }

        //shows final balance
        System.out.println("The year has ended!");
        mike.finalAccount();
    }
}
