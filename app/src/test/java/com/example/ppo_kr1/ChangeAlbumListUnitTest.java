package com.example.ppo_kr1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class ChangeAlbumListUnitTest {

    private ChangeAlbumListActivity changeAlbumListActivity;

    @Before
    public void setUp() throws Exception {
        changeAlbumListActivity = Mockito.mock(ChangeAlbumListActivity.class);
    }

    @Test
    public void clearInput_isCorrect() throws Exception {
        changeAlbumListActivity.clearInput();
        verify(changeAlbumListActivity).clearInput();
    }

    @Test
    public void checkInput_isCorrect() throws Exception {
        changeAlbumListActivity.checkInput();
        verify(changeAlbumListActivity).checkInput();
    }

    @Test
    public void backToMainMenu_isCorrect() throws Exception {
        changeAlbumListActivity.backToMainMenu();
        verify(changeAlbumListActivity).backToMainMenu();
    }

    @After
    public void tearDown() throws Exception {
        changeAlbumListActivity = null;
    }
}
