package com.suslov.jetbrains;

/**
 * @author Mikhail Suslov
 */
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    private static final int MAX_SIZE_SCREEN_ROOM = 9;
    private static final int TICKET_PRICE_FRONT_HALF = 10;
    private static final int TICKET_PRICE_BACK_HALF = 8;
    private static final int AVERAGE_ROOM_SIZE = 60;
    private static final String SELECT_MENU = "\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit";
    private int rowsNumber;
    private int seatsNumber;
    private int totalSeats;
    private final Scanner scanner;
    private String[][] screenRoom;
    private int purchasedTickets;
    private int currentIncome;

    public static void main(String[] args) {
        Cinema cinema = new Cinema();
        cinema.inputScreenRoomSize();
        cinema.initializeMenu();
    }

    public Cinema() {
        scanner = new Scanner(System.in);
    }

    public void inputScreenRoomSize() {
        rowsNumber = enterRowNumber(scanner, "Enter the number of rows:", MAX_SIZE_SCREEN_ROOM);
        seatsNumber = enterSeatsNumber(scanner, "Enter the number of seats in each row:", MAX_SIZE_SCREEN_ROOM);
        totalSeats = rowsNumber * seatsNumber;

        screenRoom = new String[rowsNumber][seatsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            Arrays.fill(screenRoom[i], "S");
        }
    }

    private int enterRowNumber(Scanner scanner, String message, int maxValue) {
        int rowNumber = 0;
        do {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                rowNumber = scanner.nextInt();
            }
        } while (!checkInputData(rowNumber, maxValue));

        return rowNumber;
    }

    private int enterSeatsNumber(Scanner scanner, String message, int maxValue) {
        int seatNumber = 0;
        do {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();
            }
        } while (!checkInputData(seatNumber, maxValue));

        return seatNumber;
    }

    private boolean checkInputData(int seatNumber, int maxValue) {
        if (seatNumber <= 0 || seatNumber > maxValue) {
            System.out.println("Wrong input!");
            return false;
        } else {
            return true;
        }
    }

    public void initializeMenu() {
        int actionNumber = selectAction();
        processSelectedAction(actionNumber);
    }

    private int selectAction() {
        int index = -1;
        do {
            System.out.println(SELECT_MENU);
            if (scanner.hasNextInt()) {
                index = scanner.nextInt();
            }
        } while (index < 0 || index > 3);

        return index;
    }

    public void processSelectedAction(int actionNumber) {
        switch (actionNumber) {
            case 1:
                representCinemaHall();
                initializeMenu();
                break;
            case 2:
                chooseSeatCoordinates();
                initializeMenu();
                break;
            case 3:
                showStatistics();
                initializeMenu();
                break;

            default:
                closeProgram();
        }
    }

    public void representCinemaHall() {
        System.out.println("Cinema:");
        for (int i = 0; i < rowsNumber + 1; i++) {
            if (i == 0) {
                System.out.print(" ");
            } else {
                System.out.print(i + " ");
            }
            for (int j = 1; j < seatsNumber + 1; j++) {
                if (i == 0) {
                    System.out.print(j + " ");
                } else {
                    System.out.print(screenRoom[i - 1][j - 1] + " ");
                }
            }
            System.out.println();
        }
    }

    public void chooseSeatCoordinates() {
        int row = 0;
        int seat = 0;
        do {
            row = enterRowNumber(scanner, "Enter a row number:", rowsNumber);
            seat = enterSeatsNumber(scanner, "Enter a seat number in that row:", seatsNumber);
        } while (!checkTicketForPurchase(row, seat));

        outputTicketPrice(row);
        screenRoom[row - 1][seat - 1] = "B";
        purchasedTickets++;
    }

    private boolean checkTicketForPurchase(int row, int seat) {
        if ("S".equals(screenRoom[row - 1][seat - 1])) {
            return true;
        } else {
            System.out.println("That ticket has already been purchased!");
            return false;
        }
    }

    private void outputTicketPrice(int row) {
        int price = getTicketPrice(row);
        currentIncome += price;
        System.out.printf("Ticket price: $%d\n", price);
    }

    private int getTicketPrice(int row) {
        if (totalSeats < AVERAGE_ROOM_SIZE) {
            return TICKET_PRICE_FRONT_HALF;
        } else {
            return (rowsNumber / 2) < row ? TICKET_PRICE_BACK_HALF : TICKET_PRICE_FRONT_HALF;
        }
    }

    private void showStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", calculatePercentageOfPurchasedTickets());
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", calculateProfitFromSoldTicket());
    }

    private double calculatePercentageOfPurchasedTickets() {
        return 100.0 * purchasedTickets / totalSeats;
    }

    private int calculateProfitFromSoldTicket() {
        if (totalSeats < AVERAGE_ROOM_SIZE) {
            return totalSeats * TICKET_PRICE_FRONT_HALF;
        } else {
            int frontHalf = (rowsNumber / 2) * seatsNumber ;
            return frontHalf * TICKET_PRICE_FRONT_HALF + (totalSeats - frontHalf) * TICKET_PRICE_BACK_HALF;
        }
    }

    private void closeProgram() {
        scanner.close();
    }
}