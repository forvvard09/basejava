package main.java.app.Demo;

public class Bank {
    private int balance;

    public Bank() {
        this.balance = 0;
    }

    public synchronized void add(){
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 7; i++) {
                balance += 50;
                System.out.println("Текущий баланс: " + balance);
                try {
                    Thread.sleep(777);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notify();
            while (balance > 100) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public synchronized void def() {
        for (int j = 0; j < 5; j++) {
            while (balance < 200) {
                try {
                    System.out.println("Спим 3");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 50; i++) {
                balance -= 5;
                System.out.println("Текущий баланс: " + balance);
                try {
                    Thread.sleep(333);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            notify();
            while (balance < 100) {
                try {
                    System.out.println("Спим 4: ");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        Thread t1 = new Thread(bank::add);

        Thread t2 = new Thread(bank::def);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Баланс: " + bank.balance);
    }
}
