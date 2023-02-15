package com.suslov.jetbrains.models;

import static com.suslov.jetbrains.CinemaManager.*;

/**
 * @author Mikhail Suslov
 */
public class TicketManager {
    private final CinemaRoom cinemaRoom;
    private int currentIncome;
    private int purchasedTickets;

    public TicketManager(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public int getPurchasedTickets() {
        return purchasedTickets;
    }

    public void buyTicketForChosenSeat() {
        purchasedTickets++;
        cinemaRoom.bookTheChosenSeat();
        outputTicketPrice(cinemaRoom.getRowLastBooking());
    }

    protected void outputTicketPrice(int row) {
        int price = getTicketPrice(row);
        currentIncome += price;
        System.out.printf("Ticket price: $%d\n", price);
    }

    protected int getTicketPrice(int row) {
        if (cinemaRoom.getTotalSeats() < AVERAGE_ROOM_SIZE) {
            return TICKET_PRICE_FRONT_HALF;
        } else {
            return (cinemaRoom.getRowsNumber() / 2) < row ? TICKET_PRICE_BACK_HALF : TICKET_PRICE_FRONT_HALF;
        }
    }

    public void showStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", calculatePercentageOfPurchasedTickets());
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", calculateProfitFromSoldTicket());
    }

    protected double calculatePercentageOfPurchasedTickets() {
        return 100.0 * purchasedTickets / cinemaRoom.getTotalSeats();
    }

    protected int calculateProfitFromSoldTicket() {
        if (cinemaRoom.getTotalSeats() < AVERAGE_ROOM_SIZE) {
            return cinemaRoom.getTotalSeats() * TICKET_PRICE_FRONT_HALF;
        } else {
            int frontHalf = (cinemaRoom.getRowsNumber() / 2) * cinemaRoom.getSeatsNumber();
            return frontHalf * TICKET_PRICE_FRONT_HALF + (cinemaRoom.getTotalSeats() - frontHalf) * TICKET_PRICE_BACK_HALF;
        }
    }
}
