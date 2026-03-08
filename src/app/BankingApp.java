package app;

import entity.Account;
import entity.Customer;
import entity.Transaction;
import service.BankingService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class BankingApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BankingService bankingService = new BankingService();

    public static void main(String[] args) {

        while (true) {

            printMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    createCustomer();
                    break;

                case 2:
                    createAccount();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    withdraw();
                    break;

                case 5:
                    transfer();
                    break;

                case 6:
                    viewBalance();
                    break;

                case 7:
                    viewTransactions();
                    break;

                case 0:
                    System.out.println("Exiting system...");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {

        System.out.println("\n====== BANKING SYSTEM ======");
        System.out.println("1. Create Customer");
        System.out.println("2. Open Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer");
        System.out.println("6. View Balance");
        System.out.println("7. Transaction History");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private static void createCustomer() {

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, email, phone, address);

        boolean result = bankingService.createCustomer(customer);

        if (result)
            System.out.println("Customer created successfully");
        else
            System.out.println("Failed to create customer");
    }

    private static void createAccount() {

        System.out.print("Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Account Type (SAVINGS/CURRENT): ");
        String type = scanner.nextLine();

        long accountNumber = System.currentTimeMillis();

        Account account = new Account(
                accountNumber,
                customerId,
                type,
                new BigDecimal("100"),
                "ACTIVE",
                LocalDateTime.now()
        );

        boolean result = bankingService.createAccount(account);

        if (result)
            System.out.println("Account created successfully");
        else
            System.out.println("Failed to create account");
    }

    private static void deposit() {

        System.out.print("Account ID: ");
        int accountId = scanner.nextInt();

        System.out.print("Amount: ");
        BigDecimal amount = scanner.nextBigDecimal();

        boolean result = bankingService.deposit(accountId, amount);

        if (result)
            System.out.println("Deposit successful");
        else
            System.out.println("Deposit failed");
    }

    private static void withdraw() {

        System.out.print("Account ID: ");
        int accountId = scanner.nextInt();

        System.out.print("Amount: ");
        BigDecimal amount = scanner.nextBigDecimal();

        boolean result = bankingService.withdraw(accountId, amount);

        if (result)
            System.out.println("Withdraw successful");
        else
            System.out.println("Withdraw failed");
    }

    private static void transfer() {

        System.out.print("From Account ID: ");
        int fromId = scanner.nextInt();

        System.out.print("To Account ID: ");
        int toId = scanner.nextInt();

        System.out.print("Amount: ");
        BigDecimal amount = scanner.nextBigDecimal();

        boolean result = bankingService.transfer(fromId, toId, amount);

        if (result)
            System.out.println("Transfer successful");
        else
            System.out.println("Transfer failed");
    }

    private static void viewBalance() {

        System.out.print("Account ID: ");
        int accountId = scanner.nextInt();

        Account account = bankingService.getAccountById(accountId);

        if (account == null)
            System.out.println("Account not found");
        else
            System.out.println("Balance: " + account.getBalance());
    }

    private static void viewTransactions() {

        System.out.print("Account ID: ");
        int accountId = scanner.nextInt();

        List<Transaction> transactions =
                bankingService.getTransactionsByAccountId(accountId);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction t : transactions) {

            System.out.println(
                    t.getTransactionType() +
                            " | Amount: " + t.getAmount() +
                            " | Date: " + t.getCreatedAt()
            );
        }
    }
}