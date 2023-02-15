package com.suslov.jetbrains;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

/**
 * @author Mikhail Suslov
 */
public class CinemaManagerTest {
    private CinemaManager cinemaManager;

    @Before
    public void setUp() {
        cinemaManager = new CinemaManager(new Scanner("6 6 -3 -2 5 6 2"));
    }

    @Test
    public void selectActionForIncorrectSequence() {
        Assert.assertEquals(2, cinemaManager.selectAction());
    }

    @Test
    public void selectActionForCorrectSequence() {
        cinemaManager = new CinemaManager(new Scanner("6 6 1"));
        Assert.assertEquals(1, cinemaManager.selectAction());
    }

    @After
    public void tearDown() {
        cinemaManager.closeManager();
    }
}