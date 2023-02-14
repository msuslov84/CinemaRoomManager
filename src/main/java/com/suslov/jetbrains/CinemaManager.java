package com.suslov.jetbrains;

import com.suslov.jetbrains.models.CinemaRoom;
import com.suslov.jetbrains.models.TicketManager;

import java.util.Scanner;

/**
 * @author Mikhail Suslov
 */

public class CinemaManager {
    public static final int MAX_SIZE_SCREEN_ROOM = 9;
    public static final int TICKET_PRICE_FRONT_HALF = 10;
    public static final int TICKET_PRICE_BACK_HALF = 8;
    public static final int AVERAGE_ROOM_SIZE = 60;

    private static final String SELECT_MENU = "\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit";

    private final Scanner scanner;
    private final CinemaRoom cinemaRoom;
    private final TicketManager ticketManager;


    public static void main(String[] args) {
        CinemaManager cinemaManager = new CinemaManager();
        cinemaManager.initializeUserMenu();
    }

    public CinemaManager() {
        this.scanner = new Scanner(System.in);
        this.cinemaRoom = new CinemaRoom(scanner);
        this.ticketManager = new TicketManager(cinemaRoom);

        cinemaRoom.initialize();
    }

    public void initializeUserMenu() {
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
                cinemaRoom.representCinemaRoom();
                initializeUserMenu();
                break;
            case 2:
                cinemaRoom.chooseSeatCoordinates();
                ticketManager.buyTicketForChosenSeat();
                initializeUserMenu();
                break;
            case 3:
                ticketManager.showStatistics();
                initializeUserMenu();
                break;
            default:
                closeManager();
        }
    }

    private void closeManager() {
        System.out.println("Manager is closed.");
        scanner.close();
    }
}