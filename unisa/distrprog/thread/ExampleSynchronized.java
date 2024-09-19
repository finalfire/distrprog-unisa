package unisa.distrprog.thread;

class CurrentAccount {
    private double balance;
    public CurrentAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    public synchronized double getBalance() { return balance; }
    public synchronized void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposito di " + amount + "; Saldo = " + balance);
        }
    }
}

public class ExampleSynchronized {
    public static void main(String[] args) {
        CurrentAccount account = new CurrentAccount(100.00);

        Thread t1 = new Thread(() -> account.deposit(200.00));
        Thread t2 = new Thread(() -> account.deposit(300.00));

        t1.start();
        t2.start();
    }
}

