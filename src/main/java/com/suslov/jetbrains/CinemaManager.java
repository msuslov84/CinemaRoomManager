package com.suslov.jetbrains;

import com.suslov.jetbrains.models.CinemaRoom;
import com.suslov.jetbrains.models.TicketManager;

import java.util.Scanner;

/**
 * @author Mikhail Suslov
 */

public class CinemaManager {
    private static final String SELECT_MENU = "\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit";
    private static final String CLOSE_APP = "Manager is closed.";

    private final Scanner scanner;
    private final CinemaRoom cinemaRoom;
    private final TicketManager ticketManager;

    public static void main(String[] args) {
        CinemaManager cinemaManager = new CinemaManager();
        cinemaManager.initializeUserMenu();
    }

    public CinemaManager() {
        this(new Scanner(System.in));
    }

    public CinemaManager(Scanner scanner) {
        this.scanner = scanner;
        this.cinemaRoom = new CinemaRoom(scanner);
        this.ticketManager = new TicketManager(cinemaRoom);

        cinemaRoom.initialize();
    }

    public void initializeUserMenu() {
        int actionNumber = selectAction();
        processSelectedAction(actionNumber);
    }

    int selectAction() {
        int index = -1;
        do {
            System.out.println(SELECT_MENU);
            if (scanner.hasNextInt()) {
                index = scanner.nextInt();
            }
        } while (index < 0 || index > 3);

        return index;
    }

    void processSelectedAction(int actionNumber) {
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

    public void closeManager() {
        System.out.println(CLOSE_APP);
        scanner.close();
    }
}