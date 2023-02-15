package com.suslov.jetbrains.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static com.suslov.jetbrains.CinemaManager.TICKET_PRICE_BACK_HALF;
import static com.suslov.jetbrains.CinemaManager.TICKET_PRICE_FRONT_HALF;

/**
 * @author Mikhail Suslov
 */
public class TicketManagerTest {
    private TicketManager ticketManager;
    private CinemaRoom cinemaRoom;
    private Scanner console;

    @Before
    public void setUp() {
        console = new Scanner("6 6 2 5 2 5 3 1");
        cinemaRoom = new CinemaRoom(console);
        cinemaRoom.initialize();
        ticketManager = new TicketManager(cinemaRoom);
    }

    @Test
    public void calculatePercentageOfPurchasedTicketsFromZero() {
        double expected = 100.0 * 0 / cinemaRoom.getTotalSeats();

        Assert.assertEquals(expected, ticketManager.calculatePercentageOfPurchasedTickets(), 0.01);
    }

    @Test
    public void calculatePercentageOfPurchasedTicketsAfterPurchase() {
        cinemaRoom.chooseSeatCoordinates();
        ticketManager.buyTicketForChosenSeat();
        double expected = 100.0 * 1 / cinemaRoom.getTotalSeats();

        Assert.assertEquals(expected, ticketManager.calculatePercentageOfPurchasedTickets(), 0.01);
    }

    @Test
    public void calculateProfitFromSoldTicketForSmallRoom() {
        int expected = cinemaRoom.getTotalSeats() * TICKET_PRICE_FRONT_HALF;

        Assert.assertEquals(expected, ticketManager.calculateProfitFromSoldTicket());
    }

    @Test
    public void calculateProfitFromSoldTicketForLargeRoom() {
        cinemaRoom = new CinemaRoom(new Scanner("9 9"));
        cinemaRoom.initialize();
        ticketManager = new TicketManager(cinemaRoom);

        int frontHalf = (cinemaRoom.getRowsNumber() / 2) * cinemaRoom.getSeatsNumber();
        int expected = frontHalf * TICKET_PRICE_FRONT_HALF + (cinemaRoom.getTotalSeats() - frontHalf) * TICKET_PRICE_BACK_HALF;

        Assert.assertEquals(expected, ticketManager.calculateProfitFromSoldTicket());
    }

    @Test
    public void getTicketPriceForSmallRoom() {
        Assert.assertEquals(TICKET_PRICE_FRONT_HALF, ticketManager.getTicketPrice(5));
    }

    @Test
    public void getTicketPriceForFrontHalfLargeRoom() {
        cinemaRoom = new CinemaRoom(new Scanner("9 9"));
        cinemaRoom.initialize();
        ticketManager = new TicketManager(cinemaRoom);

        Assert.assertEquals(TICKET_PRICE_FRONT_HALF, ticketManager.getTicketPrice(4));
    }

    @Test
    public void getTicketPriceForBackHalfLargeRoom() {
        cinemaRoom = new CinemaRoom(new Scanner("9 9"));
        cinemaRoom.initialize();
        ticketManager = new TicketManager(cinemaRoom);

        Assert.assertEquals(TICKET_PRICE_BACK_HALF, ticketManager.getTicketPrice(5));
    }

    @Test
    public void outputTicketPriceWithoutPurchase() {
        Assert.assertEquals(0, ticketManager.getCurrentIncome());
    }

    @Test
    public void outputTicketPriceForSomePurchases() {
        cinemaRoom = new CinemaRoom(new Scanner("9 9"));
        cinemaRoom.initialize();
        ticketManager = new TicketManager(cinemaRoom);

        ticketManager.outputTicketPrice(4);
        ticketManager.outputTicketPrice(5);

        Assert.assertEquals(18, ticketManager.getCurrentIncome());
    }

    @Test
    public void buyTicketForChosenSeat() {
        cinemaRoom.chooseSeatCoordinates();
        ticketManager.buyTicketForChosenSeat();

        Assert.assertEquals(1, ticketManager.getPurchasedTickets());
    }

    @After
    public void tearDown() {
        console.close();
    }
}