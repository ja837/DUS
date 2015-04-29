package test;

import gameLogic.map.Map;
import gameLogic.map.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;




public class TrackModifyTest extends LibGdxTest{
    private Map map;

    @Before
    public void mapSetup() throws Exception {
        map = new Map();
    }
	
	@Test
    public void addRemoveConnectionTest() throws Exception {
    	
    	Map map = new Map();
        String name1 = "station1";
        String name2 = "station2";

        map.addStation(name1, new Position(9999, 9999));
        map.addStation(name2, new Position(200, 200));
        
        map.addConnection(name1, name2);
        
        assertTrue("Connection addition failed", map.doesConnectionExist(name2, name1));

        map.removeConnection(map.getConnection(map.getStationByName(name1),map.getStationByName(name2)));
        
        assertFalse("Connection remove failed", map.doesConnectionExist(name2, name1));


    }
}