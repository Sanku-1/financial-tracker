package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Capstone skeleton – personal finance tracker.
 * ------------------------------------------------
 * File format  (pipe-delimited)
 *     yyyy-MM-dd|HH:mm:ss|description|vendor|amount
 * A deposit has a positive amount; a payment is stored
 * as a negative amount.
 */
public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final ArrayList<Search> searches = new ArrayList<>();

    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);


    /* ------------------------------------------------------------------
       Main menu
       ------------------------------------------------------------------ */
    public static void main(String[] args) throws IOException {
//        try {
            loadTransactions(FILE_NAME);


            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("Welcome to TransactionApp");
                System.out.println("Choose an option:");
                System.out.println("D) Add Deposit");
                System.out.println("P) Make Payment (Debit)");
                System.out.println("L) Ledger");
                System.out.println("X) Exit");

                String input = scanner.nextLine().trim();

                switch (input.toUpperCase()) {
                    case "D" -> addDeposit(scanner);
                    case "P" -> addPayment(scanner);
                    case "L" -> ledgerMenu(scanner);
                    case "X" -> running = false;
                    default -> System.out.println("Invalid option");
                }
            }
            scanner.close();
        }


    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */

    /**
     * Load transactions from FILE_NAME.
     * • If the file doesn’t exist, create an empty one so that future writes succeed.
     * • Each line looks like: date|time|description|vendor|amount
     */
    public static void loadTransactions(String fileName) throws IOException {
        // TODO: create file if it does not exist, then read each line,
        //       parse the five fields, build a Transaction object,
        //       and add it to the transactions list.
        FileReader fileReader = new FileReader(fileName);
        BufferedReader trackerBufReader = new BufferedReader(fileReader);
//        FileWriter fileWriter = new FileWriter(fileName);
//        BufferedWriter trackerBufWriter = new BufferedWriter(fileWriter);
        String line;
        while ((line = trackerBufReader.readLine()) != null) {
            String[] tokens = line.split("\\|");
            LocalDate date = LocalDate.parse(tokens[0], DATE_FMT);
            LocalTime time = LocalTime.parse(tokens[1], TIME_FMT);
            String description = tokens[2];
            String vendor = tokens[3];
            double amount = Double.parseDouble(tokens[4]);

            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            transactions.add(transaction);



        }
    }

    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */

        /**
         * Prompt for ONE date+time string in the format
         * "yyyy-MM-dd HH:mm:ss", plus description, vendor, amount.
         * Validate that the amount entered is positive.
         * Store the amount as-is (positive) and append to the file.
         */
        private static void addDeposit (Scanner scanner) throws IOException {
            // TODO
            System.out.println("Please enter the date/time of your deposit in the following format (yyyy-MM-dd HH:mm:ss)");
            String depositDateTime = scanner.nextLine();
            System.out.println("Please enter a description of your deposit");
            String depositDescription = scanner.nextLine();
            System.out.println("Please enter the vendor of your deposit");
            String depositVendor = scanner.nextLine();
            boolean validDepositAmount;
            double depositAmount;
            do {
                System.out.println("Please enter the amount of your deposit");
                depositAmount = scanner.nextDouble();
                scanner.nextLine();
                validDepositAmount = depositAmount > 0;
                if (!validDepositAmount) {
                    System.out.println("Invalid deposit amount");
                }
            } while (!validDepositAmount);
            LocalDateTime depositDateTimeParsed = LocalDateTime.parse(depositDateTime, DATETIME_FMT);
            String depositDateParsed = depositDateTimeParsed.format(DATE_FMT);
            String depositTimeParsed = depositDateTimeParsed.format(TIME_FMT);
            LocalDate depositDateConverted = LocalDate.parse(depositDateParsed, DATE_FMT);
            LocalTime depositTimeConverted = LocalTime.parse(depositTimeParsed, TIME_FMT);
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            BufferedWriter trackerBufWriter = new BufferedWriter(fileWriter);
            trackerBufWriter.write(depositDateParsed + "|" + depositTimeParsed + "|" + depositDescription + "|" + depositVendor + "|" + depositAmount);
            trackerBufWriter.newLine();
            Transaction deposit = new Transaction(depositDateConverted, depositTimeConverted, depositDescription, depositVendor, depositAmount);
            transactions.add(deposit);
            trackerBufWriter.close();
            fileWriter.close();
        }


        /**
         * Same prompts as addDeposit.
         * Amount must be entered as a positive number,
         * then converted to a negative amount before storing.
         */
        private static void addPayment (Scanner scanner) throws IOException {
            // TODO
            System.out.println("Please enter the date/time of your payment in the following format (yyyy-MM-dd HH:mm:ss)");
            String paymentDateTime = scanner.nextLine();
            System.out.println("Please enter a description of your payment");
            String paymentDescription = scanner.nextLine();
            System.out.println("Please enter the vendor of your payment");
            String paymentVendor = scanner.nextLine();
            boolean validPaymentAmount;
            double paymentAmount;
            do {
                System.out.println("Please enter the amount of your payment");
                paymentAmount = scanner.nextDouble();
                scanner.nextLine();
                validPaymentAmount = paymentAmount > 0;
                if (!validPaymentAmount) {
                    System.out.println("Invalid payment amount");
                }
            } while (!validPaymentAmount);
            paymentAmount = paymentAmount * -1;
            LocalDateTime paymentDateTimeParsed = LocalDateTime.parse(paymentDateTime, DATETIME_FMT);
            String paymentDateParsed = paymentDateTimeParsed.format(DATE_FMT);
            String paymentTimeParsed = paymentDateTimeParsed.format(TIME_FMT);
            LocalDate paymentDateConverted = LocalDate.parse(paymentDateParsed, DATE_FMT);
            LocalTime paymentTimeConverted = LocalTime.parse(paymentTimeParsed, TIME_FMT);
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            BufferedWriter trackerBufWriter = new BufferedWriter(fileWriter);
            trackerBufWriter.write(paymentDateParsed + "|" + paymentTimeParsed + "|" + paymentDescription + "|" + paymentVendor + "|" + paymentAmount);
            trackerBufWriter.newLine();
            Transaction payment = new Transaction(paymentDateConverted, paymentTimeConverted, paymentDescription, paymentVendor, paymentAmount);
            transactions.add(payment);
            trackerBufWriter.close();
            fileWriter.close();
        }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
        private static void ledgerMenu (Scanner scanner){
            boolean running = true;
            while (running) {
                System.out.println("Ledger");
                System.out.println("Choose an option:");
                System.out.println("A) All");
                System.out.println("D) Deposits");
                System.out.println("P) Payments");
                System.out.println("R) Reports");
                System.out.println("H) Home");

                String input = scanner.nextLine().trim();

                switch (input.toUpperCase()) {
                    case "A" -> displayLedger();
                    case "D" -> displayDeposits();
                    case "P" -> displayPayments();
                    case "R" -> reportsMenu(scanner);
                    case "H" -> running = false;
                    default -> System.out.println("Invalid option");
                }
            }
        }

    /* ------------------------------------------------------------------
       Display helpers: show data in neat columns
       ------------------------------------------------------------------ */
        private static void displayLedger () { /* TODO – print all transactions in column format */
            System.out.println("All Transactions:");
            System.out.println("Date" + " | " + "Time" + " | " + "Description" + " | " + "Vendor" + " | " + "Amount");
            for (Transaction transaction : transactions) {
                System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
            }
        }


        private static void displayDeposits () { /* TODO – only amount > 0               */
            System.out.println("Deposits:");
            System.out.println("Date" + " | " + "Time" + " | " + "Description" + " | " + "Vendor" + " | " + "Amount");
            int depositCounter = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getAmount() > 0) {
                    System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                    depositCounter++;
                }
            }
            if (depositCounter == 0) {
                System.out.println("No deposits have been made to this account");
            }
        }

        private static void displayPayments () { /* TODO – only amount < 0               */
            System.out.println("Payments:");
            System.out.println("Date" + " | " + "Time" + " | " + "Description" + " | " + "Vendor" + " | " + "Amount");
            int paymentCounter = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getAmount() < 0) {
                    System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                    paymentCounter++;
                }
            }
            if (paymentCounter == 0) {
                System.out.println("No payments have been made from this account");
            }
        }

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
        private static void reportsMenu (Scanner scanner){
            boolean running = true;
            while (running) {
                System.out.println("Reports");
                System.out.println("Choose an option:");
                System.out.println("1) Month To Date");
                System.out.println("2) Previous Month");
                System.out.println("3) Year To Date");
                System.out.println("4) Previous Year");
                System.out.println("5) Search by Vendor");
                System.out.println("6) Custom Search");
                System.out.println("0) Back");

                String input = scanner.nextLine().trim();

                switch (input) {
                    case "1" -> {/* TODO – month-to-date report */
                        LocalDate present = LocalDate.now();
                        int currentMonth = present.getMonthValue();
                        int currentYear = present.getYear();
                        String currentMonthString = Integer.toString(currentMonth);
                        String currentYearString = Integer.toString(currentYear);
                        LocalDate startOfMonth = LocalDate.parse(currentYearString + "-" + currentMonthString + "-01", DATE_FMT);
                        filterTransactionsByDate(startOfMonth, present);
                    }
                    case "2" -> {/* TODO – previous month report */
                        LocalDate present = LocalDate.now();
                        int currentMonth = present.getMonthValue();
                        int currentYear = present.getYear();
                        int previousMonth = 0;
                        int previousYear = currentYear;
                        String previousMonthString;
                        if (currentMonth == 1) {
                            previousMonth = 12;
                            previousYear = currentYear - 1;
                            previousMonthString = Integer.toString(previousMonth);
                        } else if (currentMonth > 10){
                            previousMonth = currentMonth - 1;
                            previousMonthString = Integer.toString(previousMonth);
                        } else {
                            previousMonth = currentMonth - 1;
                            previousMonthString = "0" + Integer.toString(previousMonth);
                        }
//                        String previousMonthString = Integer.toString(previousMonth);
                        String previousYearString = Integer.toString(previousYear);
                        LocalDate previousMonthStart = LocalDate.parse(previousYearString + "-" + previousMonthString + "-01" , DATE_FMT);
                        LocalDate previousMonthEnd = previousMonthStart.withDayOfMonth(previousMonthStart.lengthOfMonth());
                        filterTransactionsByDate(previousMonthStart, previousMonthEnd);
                    }
                    case "3" -> {
                        LocalDate present = LocalDate.now();
                        int currentYear = present.getYear();
                        String currentYearString = Integer.toString(currentYear);
                        LocalDate startOfYear = LocalDate.parse(currentYearString + "-01-01", DATE_FMT);
                        filterTransactionsByDate(startOfYear, present);
                    }
                    case "4" -> {
                        int previousYear = LocalDate.now().getYear() - 1;
                        String previousYearString = Integer.toString(previousYear);
                        LocalDate previousYearStart = LocalDate.parse(previousYearString + "-01-01", DATE_FMT);
                        LocalDate previousYearEnd = LocalDate.parse(previousYearString + "-12-31", DATE_FMT);
                        filterTransactionsByDate(previousYearStart, previousYearEnd);
                    }
                    case "5" -> {
                        System.out.println("Please enter the name of the vendor you would like to search your transactions for:");
                        filterTransactionsByVendor(scanner.nextLine());
                    }
                    case "6" -> customSearch(scanner);
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option");
                }
            }
        }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
        private static void filterTransactionsByDate (LocalDate start, LocalDate end){
            // TODO – iterate transactions, print those within the range
            System.out.println("Transactions from " + start + " to " + end + ":");
            System.out.println("Date" + " | " + "Time" + " | " + "Description" + " | " + "Vendor" + " | " + "Amount");
            int dateComparison = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getDate().compareTo(start) >= 0 && transaction.getDate().compareTo(end) <= 0) {
                    System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                }
            }
        }

        private static void filterTransactionsByVendor (String vendor){
            // TODO – iterate transactions, print those with matching vendor
            System.out.println("Vendor Searched: " + vendor);
            System.out.println("Date" + " | " + "Time" + " | " + "Description" + " | " + "Vendor" + " | " + "Amount");
            for (Transaction transaction : transactions) {
                if (vendor.equalsIgnoreCase(transaction.getVendor())) {
                    System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                }
            }
        }

        private static void customSearch (Scanner scanner) {
            LocalDate endDateInputParsed = null;
            LocalDate startDateInputParsed = null;
            double amountInputParsed = 0;
//            try {
            System.out.println("Please enter the date range you would like to search your transactions for:");
            System.out.println("Start date in the following format (yyyy-MM-dd):");
            String startDateInput = scanner.nextLine();
            if (!startDateInput.isEmpty()) {
                startDateInputParsed = LocalDate.parse(startDateInput, DATE_FMT);
            }
            System.out.println("End date in the following format (yyyy-MM-dd):");
            String endDateInput = scanner.nextLine();
            if (!endDateInput.isEmpty()) {
                endDateInputParsed = LocalDate.parse(endDateInput, DATE_FMT);
            }
            System.out.println("Please enter the name of the description you would like to search your transactions for:");
            String descriptionInput = scanner.nextLine();
            System.out.println("Please enter the name of the vendor you would like to search your transactions for:");
            String vendorInput = scanner.nextLine();
            System.out.println("Please enter the name of the amount you would like to search your transactions for:");
            String amountInput = scanner.nextLine();
            if (!amountInput.isEmpty()){
                amountInputParsed = Double.parseDouble(amountInput);
                }
            LocalDateTime searchTimeStamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            Search newSearch = new Search(startDateInputParsed, endDateInputParsed, descriptionInput, vendorInput, amountInputParsed, searchTimeStamp);
            searches.add(newSearch);
            int searchCounter = 0;
            System.out.println("Date" + " | " + "Time" + " | " + "Description" + " | " + "Vendor" + " | " + "Amount");
            for (Transaction transaction : transactions) {
                if (startDateInputParsed != null && endDateInputParsed != null) {
                    if (transaction.getDate().compareTo(startDateInputParsed) >= 0 && transaction.getDate().compareTo(endDateInputParsed) <= 0 && (transaction.getDescription().equalsIgnoreCase(descriptionInput) || descriptionInput.isEmpty()) && (transaction.getVendor().equalsIgnoreCase(vendorInput) || vendorInput.isEmpty()) && (transaction.getAmount() == amountInputParsed || amountInputParsed == 0)) {
                            System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                            searchCounter++;
                    }
                } else if (startDateInputParsed != null && endDateInputParsed == null) {
                    if (transaction.getDate().compareTo(startDateInputParsed) >= 0 && (transaction.getDescription().equalsIgnoreCase(descriptionInput) || descriptionInput.isEmpty()) && (transaction.getVendor().equalsIgnoreCase(vendorInput) || vendorInput.isEmpty()) && (transaction.getAmount() == amountInputParsed || amountInputParsed == 0)) {
                        System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                        searchCounter++;
                    }
                } else if (startDateInputParsed == null && endDateInputParsed != null) {
                    if (transaction.getDate().compareTo(endDateInputParsed) <= 0 && (transaction.getDescription().equalsIgnoreCase(descriptionInput) || descriptionInput.isEmpty()) && (transaction.getVendor().equalsIgnoreCase(vendorInput) || vendorInput.isEmpty()) && (transaction.getAmount() == amountInputParsed || amountInputParsed == 0)) {
                        System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                        searchCounter++;
                    }
                } else if (startDateInputParsed == null && endDateInputParsed == null) {
                    if ((transaction.getDescription().equalsIgnoreCase(descriptionInput) || descriptionInput.isEmpty()) && (transaction.getVendor().equalsIgnoreCase(vendorInput) || vendorInput.isEmpty()) && (transaction.getAmount() == amountInputParsed || amountInputParsed == 0)) {
                        System.out.println(transaction.getDate() + " | " + transaction.getTime() + " | " + transaction.getDescription() + " | " + transaction.getVendor() + " | " + transaction.getAmount());
                        searchCounter++;
                    }

                }
            }
            if (searchCounter == 0) {
                System.out.println("No matching transactions found");
            }
//            } catch (Exception e) {
//                System.err.println("Error processing your search request");
//            }
    }



    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
        private static LocalDate parseDate (String s){
            /* TODO – return LocalDate or null */
            return null;
        }

        private static Double parseDouble (String s){
            /* TODO – return Double   or null */
            return null;
        }
    }
