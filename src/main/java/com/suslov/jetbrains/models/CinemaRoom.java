package com.suslov.jetbrains.models;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Mikhail Suslov
 */
public class CinemaRoom {
    static final int MAX_SIZE_SCREEN_ROOM = 9;
    static final int AVERAGE_ROOM_SIZE = 60;
    static final char EMPTY_SEAT = 'S';
    static final char PURCHASED_SEAT = 'B';

    private final Scanner console;
    private int rowsNumber;
    private int seatsNumber;
    private int totalSeats;
    private char[][] roomScheme;
    private int rowLastBooking;
    private int seatLastBooking;

    public CinemaRoom(Scanner console) {
        this.console = console;
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

    public int getSeatLastBooking() {
        return seatLastBooking;
    }

    public char[][] getRoomScheme() {
        return roomScheme;
    }

    public void initialize() {
        inputCinemaRoomSize();
    }

    void inputCinemaRoomSize() {
        rowsNumber = enterRowsNumber("Enter the number of rows:", MAX_SIZE_SCREEN_ROOM);
        seatsNumber = enterSeatsNumber("Enter the number of seats in each row:", MAX_SIZE_SCREEN_ROOM);
        totalSeats = rowsNumber * seatsNumber;

        roomScheme = new char[rowsNumber][seatsNumber];
        for (int i = 0; i < rowsNumber; i++) {
            Arrays.fill(roomScheme[i], EMPTY_SEAT);
        }
    }

    int enterRowsNumber(String message, int maxValue) {
        int rowNumber = 0;
        do {
            System.out.println(message);
            if (console.hasNextInt()) {
                rowNumber = console.nextInt();
            }
        } while (!checkInputNumber(rowNumber, maxValue));

        return rowNumber;
    }

    int enterSeatsNumber(String message, int maxValue) {
        int seatNumber = 0;
        do {
            System.out.println(message);
            if (console.hasNextInt()) {
                seatNumber = console.nextInt();
            }
        } while (!checkInputNumber(seatNumber, maxValue));

        return seatNumber;
    }

    boolean checkInputNumber(int seatNumber, int maxValue) {
        if (seatNumber <= 0 || seatNumber > maxValue) {
            System.out.println("Wrong input!");
            return false;
        } else {
            return true;
        }
    }

    public void chooseSeatCoordinates() {
        do {
            rowLastBooking = enterRowsNumber("Enter a row number:", rowsNumber);
            seatLastBooking = enterSeatsNumber("Enter a seat number in that row:", seatsNumber);
        } while (!checkTicketForPurchase(rowLastBooking, seatLastBooking));
    }

    boolean checkTicketForPurchase(int row, int seat) {
        if (EMPTY_SEAT == roomScheme[row][seat]) {
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
        roomScheme[rowLastBooking][seatLastBooking] = PURCHASED_SEAT;
    }
}
