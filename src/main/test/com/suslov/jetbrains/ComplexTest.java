package com.suslov.jetbrains;

import com.suslov.jetbrains.models.CinemaRoomTest;
import com.suslov.jetbrains.models.TicketManagerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Mikhail Suslov
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CinemaManagerTest.class,
        CinemaRoomTest.class,
        TicketManagerTest.class})
public class ComplexTest {
}
