package test;

import gameLogic.map.Connection;
import gameLogic.map.Position;
import gameLogic.map.Station;
import junit.framework.TestCase;
import org.junit.Test;

public class ConnectionTest extends TestCase {

    @Test
    public void testIsBlocked() throws Exception {
        Position pos = new Position(0,0);
        Station station1 = new Station("London",pos);
        Station station2 = new Station("Paris",pos);
        Connection testConnection = new Connection(station1, station2);

        //test connections default to unblocked
        assertFalse(testConnection.isBlocked());

        //set connection to blocked - test returns true
        testConnection.setBlocked(1);
        assertTrue(testConnection.isBlocked());
    }

    @Test
    public void testDecrementBlocked() throws Exception {
        Position pos = new Position(0,0);
        Station station1 = new Station("London",pos);
        Station station2 = new Station("Paris",pos);
        Connection testConnection = new Connection(station1, station2);

        //test decrementBlocked works
        testConnection.setBlocked(3);
        testConnection.decrementBlocked();
        assertEquals(2, testConnection.getTurnsBlocked());

        //checks decrementBlocked does not decrement past 0
        testConnection.setBlocked(0);
        testConnection.decrementBlocked();
        assertEquals(0,testConnection.getTurnsBlocked());


    }

}