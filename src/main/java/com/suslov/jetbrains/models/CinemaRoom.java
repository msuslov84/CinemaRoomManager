package com.suslov.jetbrains.models;

import java.util.Arrays;
import java.util.Scanner;

import static com.suslov.jetbrains.CinemaManager.MAX_SIZE_SCREEN_ROOM;

/**
 * @author Mikhail Suslov
 */
public class CinemaRoom {
    private static final char EMPTY_SEAT = 'S';
    private static final char PURCHASED_SEAT = 'B';

    private final Scanner console;
    private int rowsNumber;
    private int seatsNumber;
    private int totalSeats;
    private char[][] roomScheme;
    private int rowLastBooking;
    private int seatLastBooking;

    public CinemaRoom(Scanner console) {
        this.console = console;
        inputCinemaRoomSize();
    }

    public int getRowsNumber() {
        return rowsNumber;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getRowLastBooking() {
        return rowLastBooking;
    }

    private void inputCinemaRoomSize() {
        rowsNumber = enterRowsNumber(console, "Enter the number of rows:", MAX_SIZE_SCREEN_ROOM);
        seatsNumber = enterSeatsNumber(console, "Enter the number of seats in each row:", MAX_SIZE_SCREEN_ROOM);
        totalSeats = rowsNumber * seatsNumber;

        roomScheme = new char[rowsNumber][seatsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            Arrays.fill(roomScheme[i], EMPTY_SEAT);
        }
    }

    private int enterRowsNumber(Scanner scanner, String message, int maxValue) {
        int rowNumber = 0;
        do {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                rowNumber = scanner.nextInt();
            }
        } while (!checkInputNumber(rowNumber, maxValue));

        return rowNumber;
    }

    private int enterSeatsNumber(Scanner scanner, String message, int maxValue) {
        int seatNumber = 0;
        do {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                seatNumber = scanner.nextInt();
            }
        } while (!checkInputNumber(seatNumber, maxValue));

        return seatNumber;
    }

    private boolean checkInputNumber(int seatNumber, int maxValue) {
        if (seatNumber <= 0 || seatNumber > maxValue) {
            System.out.println("Wrong input!");
            return false;
        } else {
            return true;
        }
    }

    public void chooseSeatCoordinates() {
        do {
            rowLastBooking = enterRowsNumber(console, "Enter a row number:", rowsNumber);
            seatLastBooking = enterSeatsNumber(console, "Enter a seat number in that row:", seatsNumber);
        } while (!checkTicketForPurchase(rowLastBooking, seatLastBooking));
    }

    private boolean checkTicketForPurchase(int row, int seat) {
        if (EMPTY_SEAT == roomScheme[row - 1][seat - 1]) {
            return true;
        } else {
            System.out.println("That ticket has already been purchased!");
            return false;
        }
    }

    public void representCinemaRoom() {
        System.out.println("Cinema:");
        for (int i = 0; i < rowsNumber + 1; i++) {
            if (i == 0) {
                System.out.print("\t");
            } else {
                System.out.print(i + "\t");
            }
            for (int j = 1; j < seatsNumber + 1; j++) {
                if (i == 0) {
                    System.out.print(j + "\t");
                } else {
                    System.out.print(roomScheme[i - 1][j - 1] + "\t");
                }
            }
            System.out.println();
        }
    }

    public void bookTheChosenSeat() {
        roomScheme[rowLastBooking - 1][seatLastBooking - 1] = PURCHASED_SEAT;
    }
}
