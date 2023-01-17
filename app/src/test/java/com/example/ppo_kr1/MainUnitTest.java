package com.example.ppo_kr1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class MainUnitTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Mockito.mock(MainActivity.class);
    }

    @Test
    public void changeIsChecked_isCorrect() throws Exception {
        mainActivity.changeIsChecked();
        verify(mainActivity).changeIsChecked();
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}
