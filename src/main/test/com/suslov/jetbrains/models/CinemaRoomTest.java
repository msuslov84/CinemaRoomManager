package com.suslov.jetbrains.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

import static com.suslov.jetbrains.models.CinemaRoom.MAX_SIZE_SCREEN_ROOM;
import static com.suslov.jetbrains.models.CinemaRoom.EMPTY_SEAT;
import static com.suslov.jetbrains.models.CinemaRoom.PURCHASED_SEAT;

/**
 * @author Mikhail Suslov
 */
public class CinemaRoomTest {
    private CinemaRoom cinemaRoom;
    private Scanner console;

    @Before
    public void setUp() {
        console = new Scanner("6 6 2 5");
        cinemaRoom = new CinemaRoom(console);
    }

    @Test
    public void checkInputNegativeNumber() {
        Assert.assertFalse(cinemaRoom.checkInputNumber(-2, 9));
    }

    @Test
    public void checkInputBigNumber() {
        Assert.assertFalse(cinemaRoom.checkInputNumber(11, 9));
    }

    @Test
    public void checkInputCorrectNumber() {
        Assert.assertTrue(cinemaRoom.checkInputNumber(6, 9));
    }

    @Test
    public void enterRowsNumberFromNotCorrectSequence() {
        cinemaRoom = new CinemaRoom(new Scanner(" -3 -1 11 6"));
        Assert.assertEquals(6, cinemaRoom.enterRowsNumber("Enter the number of rows:", MAX_SIZE_SCREEN_ROOM));
    }

    @Test
    public void enterRowsNumberFromCorrectSequence() {
        Assert.assertEquals(6, cinemaRoom.enterRowsNumber("Enter the number of rows:", MAX_SIZE_SCREEN_ROOM));
    }

    @Test
    public void enterSeatsNumberFromNotCorrectSequence() {
        cinemaRoom = new CinemaRoom(new Scanner(" -4 0 12 5"));
        Assert.assertEquals(5, cinemaRoom.enterSeatsNumber("Enter the number of seats in each row:", MAX_SIZE_SCREEN_ROOM));
    }

    @Test
    public void enterSeatsNumberFromCorrectSequence() {
        Assert.assertEquals(6, cinemaRoom.enterSeatsNumber("Enter the number of seats in each row:", MAX_SIZE_SCREEN_ROOM));
    }

    @Test
    public void inputCinemaRoomSize() {
        char[][] roomScheme = new char[6][6];
        for (char[] chars : roomScheme) {
            Arrays.fill(chars, EMPTY_SEAT);
        }

        cinemaRoom.inputCinemaRoomSize();

        Assert.assertArrayEquals(cinemaRoom.getRoomScheme(), roomScheme);
    }

    @Test
    public void checkFreeTicketForPurchase() {
        cinemaRoom.initialize();
        cinemaRoom.getRoomScheme()[3][3] = PURCHASED_SEAT;
        Assert.assertTrue(cinemaRoom.checkTicketForPurchase(2, 1));
    }

    @Test
    public void checkPurchasedTicketForPurchase() {
        cinemaRoom.initialize();
        cinemaRoom.getRoomScheme()[3][3] = PURCHASED_SEAT;
        Assert.assertFalse(cinemaRoom.checkTicketForPurchase(3, 3));
    }

    @Test
    public void chooseSeatCoordinatesFromNotCorrectSequence() {
        cinemaRoom = new CinemaRoom(new Scanner("6 6 -2 0 3 0 -1 12 4"));
        cinemaRoom.initialize();
        cinemaRoom.chooseSeatCoordinates();
        Assert.assertEquals(3, cinemaRoom.getRowLastBooking());
        Assert.assertEquals(4, cinemaRoom.getSeatLastBooking());
    }

    @Test
    public void chooseSeatCoordinatesFromCorrectSequence() {
        cinemaRoom.initialize();
        cinemaRoom.chooseSeatCoordinates();
        Assert.assertEquals(2, cinemaRoom.getRowLastBooking());
        Assert.assertEquals(5, cinemaRoom.getSeatLastBooking());
    }

    @Test
    public void bookTheChosenSeat() {
        cinemaRoom.initialize();
        cinemaRoom.chooseSeatCoordinates();
        cinemaRoom.bookTheChosenSeat();
        Assert.assertEquals(cinemaRoom.getRoomScheme()[2][5], PURCHASED_SEAT);
    }

    @Test
    public void bookTheChosenSeatNotCorrectCoordinates() {
        cinemaRoom.initialize();
        cinemaRoom.chooseSeatCoordinates();
        cinemaRoom.bookTheChosenSeat();
        Assert.assertNotEquals(cinemaRoom.getRoomScheme()[1][4], PURCHASED_SEAT);
    }

    @After
    public void tearDown() {
        console.close();
    }
}