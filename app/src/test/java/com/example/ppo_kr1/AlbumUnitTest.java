package com.example.ppo_kr1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AlbumUnitTest {

    private Album album;

    @Before
    public void setUp() throws Exception {
        album = new Album("","",0,0);
    }

    @Test
    public void setName_isCorrect() throws Exception {
        album.setName("name");
        assertEquals("name", album.getName());
    }

    @Test
    public void setGroup_isCorrect() throws Exception {
        album.setGroup("group");
        assertEquals("group", album.getGroup());
    }

    @Test
    public void setYear_isCorrect() throws Exception {
        album.setYear(5);
        assertEquals(5, album.getYear());
    }

    @Test
    public void setDrawable_isCorrect() throws Exception {
        album.setDrawable(5);
        assertEquals(5, album.getDrawable());
    }

    @Test
    public void setUri_isCorrect() throws Exception {
        album.setUri(null);
        assertNull(album.getUri());
    }

    @Test
    public void setChecked_isCorrect() throws Exception {
        album.setChecked(true);
        assertTrue(album.getChecked());
    }

    @Test
    public void setPathToImage_isCorrect() throws Exception {
        album.setPathToImage("pathToImage");
        assertEquals("pathToImage", album.getPathToImage());
    }

    @After
    public void tearDown() throws Exception {
        album = null;
    }
}
