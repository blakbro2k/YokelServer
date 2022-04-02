package net.asg.games.yokel;

import com.badlogic.gdx.utils.OrderedMap;
import com.github.czyzby.kiwi.util.gdx.collection.GdxMaps;

import net.asg.games.utils.TestingUtils;
import net.asg.games.utils.YokelUtilities;
import net.asg.games.yokel.objects.YokelBlock;
import net.asg.games.yokel.objects.YokelClock;
import net.asg.games.yokel.objects.YokelLounge;
import net.asg.games.yokel.objects.YokelObject;
import net.asg.games.yokel.objects.YokelPiece;
import net.asg.games.yokel.objects.YokelPlayer;
import net.asg.games.yokel.objects.YokelRoom;
import net.asg.games.yokel.objects.YokelSeat;
import net.asg.games.yokel.objects.YokelTable;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class YokelTowersObjectsTest {

    @DataProvider
    public static Object[][] yokelObjectsProvider() {
        YokelLounge l1 = new YokelLounge();
        YokelRoom r1 = new YokelRoom();
        YokelPlayer p1 = new YokelPlayer();
        YokelClock c1 = new YokelClock();
        YokelSeat s1 = new YokelSeat();
        TestingUtils.setUpYokelObjects(l1, r1, p1, c1, s1);
        return new Object[][]{
                {l1},
                {l1},
                {p1},
                {c1},
                {s1}
        };
    }

    //Overkill test, but it give piece of mind
    @Test(dataProvider = "yokelObjectsProvider")
    public void YokelObjectInterfaceTest(Object o) throws Exception {
        if (o instanceof YokelObject) {
            YokelObject actual = (YokelObject) o;
            Assert.assertEquals(actual.getName(), TestingUtils.TEST_NAME);
            Assert.assertEquals(actual.getId(), TestingUtils.TEST_ID);
            Assert.assertEquals(actual.getModified(), TestingUtils.TEST_MODIFIED);
            Assert.assertEquals(actual.getCreated(), TestingUtils.TEST_CREATED);
        } else {
            throw new Exception("Provided object was not YokelObject.class!");
        }
    }


    @DataProvider
    public static Object[][] yokelObjectsEqualsProvider() {
        return new Object[][]{
                {new YokelRoom(), new YokelRoom(), new YokelRoom()},
                {new YokelBlock(), new YokelBlock(), new YokelBlock()},
                {new YokelClock(), new YokelClock(), new YokelClock()},
                {new YokelSeat(), new YokelSeat(), new YokelSeat()},
                {new YokelPlayer(), new YokelPlayer(), new YokelPlayer()},
                {new YokelPiece(), new YokelPiece(), new YokelPiece()},
                {new YokelTable(), new YokelTable(), new YokelTable()},
                {new YokelLounge(), new YokelLounge(), new YokelLounge()}
        };
    }
    @Test(dataProvider = "yokelObjectsEqualsProvider")
    public void YokelObjectsEqualsTest(Object o1, Object o2, Object o3) throws Exception {
        YokelObject actual = (YokelObject) o1;
        YokelObject different = (YokelObject) o2;
        YokelObject same = (YokelObject) o3;
        TestingUtils.setUpYokelObjects(actual, different, same);
        different.setName("different");
        TestingUtils.setTestIds(different);

        Assert.assertFalse(actual.equals(null));
        //different object
        Assert.assertFalse(actual.equals(new YokelRoom()));
        //same object, different name
        Assert.assertFalse(actual.equals(different));
        Assert.assertTrue(actual.equals(same));

        //set test
        Set<Object> set = new HashSet<>();
        set.add(actual);
        set.add(same);
        set.add(different);
        Assert.assertEquals(set.size(), 2);

        actual.setId(null);
        different.setId(null);
        same.setId(null);

        Assert.assertFalse(actual.equals(null));
        //different object
        Assert.assertFalse(actual.equals(new YokelRoom()));
        //same object, different name
        Assert.assertFalse(actual.equals(different));
        Assert.assertTrue(actual.equals(same));

        //set test
        set = new HashSet<>();
        set.add(actual);
        set.add(same);
        set.add(different);
        Assert.assertEquals(set.size(), 2);

    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void exceptionTestYokelLounge() throws Exception {
        YokelLounge lounge = new YokelLounge(null);
        lounge = new YokelLounge("name");
        lounge.addRoom(null);
    }

    @Test
    public void YokelLoungeObjectTest() throws Exception {
        YokelLounge actual = new YokelLounge("Lounge1");
        String actualRoomName = "Room1";
        YokelRoom actualRoom = new YokelRoom(actualRoomName);
        TestingUtils.setTestIds(actual, actualRoom);

        //Test addRoom
        //Test getAllRooms
        Assert.assertEquals(actual.getAllRooms().size, 0);
        actual.addRoom(actualRoom);
        Assert.assertEquals(actual.getAllRooms().size, 1);
        //Test getRoom
        Assert.assertEquals(actual.getRoom(actualRoomName), actualRoom);
        //Test removeRoom
        actual.removeRoom(actualRoomName);
        Assert.assertEquals(actual.getAllRooms().size, 0);
        //Test dispose
        actual.addRoom(actualRoom);
        actual.dispose();
        Assert.assertEquals(actual.getAllRooms().size, 0);
        Assert.assertNull(actual.getName());
    }

    @Test
    public void YokelRoomTest() throws Exception {
        String actualRoomName = "Room1";

        YokelRoom actual = new YokelRoom(actualRoomName);
        YokelPlayer player1 = new YokelPlayer("Player1");
        YokelPlayer player2 = new YokelPlayer("Player2");
        YokelPlayer player3 = new YokelPlayer("Player3");
        TestingUtils.setTestIds(player1, player2, player3);

        //Test getAllTables
        Assert.assertEquals(actual.getAllTables().size, 0);
        //Test getAllPlayers
        Assert.assertEquals(actual.getAllPlayers().size, 0);
        //joinRoom
        actual.joinRoom(player1);
        Assert.assertEquals(actual.getAllPlayers().size, 1);
        //leaveRoom
        actual.leaveRoom(player1);
        Assert.assertEquals(actual.getAllPlayers().size, 0);
        //Multi join and leave
        actual.joinRoom(player1);
        actual.joinRoom(player2);
        Assert.assertEquals(actual.getAllPlayers().size, 2);
        actual.leaveRoom(player1);
        Assert.assertEquals(actual.getAllPlayers().size, 1);
        actual.joinRoom(player3);
        Assert.assertEquals(actual.getAllPlayers().size, 2);
        //remove player not there
        actual.leaveRoom(player1);
        Assert.assertEquals(actual.getAllPlayers().size, 2);
        actual.leaveRoom(player2);
        actual.leaveRoom(player3);
        Assert.assertEquals(actual.getAllPlayers().size, 0);
/*
        //addTable
        Assert.assertNull(actual.getTable(1));
        actual.addTable(null);
        Assert.assertNotNull(actual.getTable(1));
        OrderedMap<String, Object> args = GdxMaps.newOrderedMap();
        args.put("rated", true);
        actual.addTable(args);
        Assert.assertNotNull(actual.getTable(2));
        actual.removeTable(3);
        Assert.assertNotNull(actual.getTable(2));
        actual.removeTable(2);
        Assert.assertNull(actual.getTable(2));
        actual.removeTable(1);
        Assert.assertEquals(actual.getAllTables().size, 0);
*/
        actual.addTable(null);
        actual.joinRoom(player1);
        Assert.assertEquals(actual.getAllPlayers().size, 1);
        Assert.assertEquals(actual.getAllTables().size, 1);

        actual.dispose();
        Assert.assertEquals(actual.getAllPlayers().size, 0);
        Assert.assertEquals(actual.getAllTables().size, 0);
    }

    @Test
    public void YokelPlayerTest() {
        String actualPlayerName1 = "blakbro2k";
        YokelPlayer player = new YokelPlayer(actualPlayerName1);;
        YokelPlayer player2 = new YokelPlayer(actualPlayerName1, 2, 2);;
        YokelPlayer player3 = new YokelPlayer(actualPlayerName1, 2);;
        player2.dispose();

        Assert.assertEquals(YokelPlayer.DEFAULT_RATING_NUMBER, player.getRating());
        player.increaseRating(5);
        Assert.assertEquals(YokelPlayer.DEFAULT_RATING_NUMBER + 5, player.getRating());
        player.decreaseRating(3);
        Assert.assertEquals(YokelPlayer.DEFAULT_RATING_NUMBER + 2, player.getRating());
        player.setRating(2300);
        Assert.assertEquals(2300, player.getRating());
        player3.setIcon(3);
        Assert.assertEquals(3, player3.getIcon());
        TestingUtils.setUpYokelObject(player);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void exceptionTestYokelSeat() {
        //YokelSeat seat = new YokelSeat(-1);
        //YokelSeat seat2 = new YokelSeat(11);
        //YokelSeat seat3 = new YokelSeat(0);
    }
/*
    @Test
    public void YokelSeatTest() {
        YokelSeat seat;
        seat = new YokelSeat(1);
        Assert.assertFalse(seat.isOccupied());
        YokelPlayer player1 = new YokelPlayer("blakbro2k");
        //sitDown
        Assert.assertTrue(seat.sitDown(player1));
        //isOccupied
        Assert.assertTrue(seat.isOccupied());
        Assert.assertFalse(seat.sitDown(player1));
        //getSeated
        Assert.assertEquals(player1, seat.getSeatedPlayer());
        //getSeatNumber
        Assert.assertEquals(1, seat.getSeatNumber());
        //standup
        //seatready
        //is seat ready
        Assert.assertFalse(seat.isSeatReady());
        seat.setSeatReady(true);
        Assert.assertTrue(seat.isSeatReady());
        seat.standUp();
        Assert.assertFalse(seat.isSeatReady());
        //dispose
        seat.sitDown(player1);
        seat.setSeatReady(true);
        seat.dispose();
        Assert.assertFalse(seat.isSeatReady());
        Assert.assertFalse(seat.isOccupied());
        Assert.assertEquals(getJSON(seat), seat.toString());
    }
*/
    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void exceptionTestYokelTable() {
        YokelTable table1 = new YokelTable();
    }

    @Test
    public void YokelTableTest() {
        //enum values
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PRIVATE.getValue(), YokelTable.ENUM_VALUE_PRIVATE);
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PUBLIC.getValue(), YokelTable.ENUM_VALUE_PUBLIC);
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PROTECTED.getValue(), YokelTable.ENUM_VALUE_PROTECTED);

        YokelTable table1 = new YokelTable();
        Assert.assertNull(table1.getName());
        table1.setName("#1");
        Assert.assertNotNull(table1.getName());

        Assert.assertEquals(YokelTable.ACCESS_TYPE.PUBLIC, table1.getAccessType());
        Assert.assertFalse(table1.isRated());
        Assert.assertTrue(table1.isSoundOn());
        //table1.setTableNumber(3);
        //Assert.assertEquals(table1.getTableNumber(), 3);

        table1.makeTableUnready();
        Assert.assertFalse(table1.isSeatReady(1));

        Assert.assertFalse(table1.isSeatReady(null));
        table1.setRated(true);
        Assert.assertTrue(table1.isRated());
        table1.setRated(false);
        Assert.assertFalse(table1.isRated());

        table1.setSound(true);
        Assert.assertTrue(table1.isSoundOn());
        table1.setSound(false);
        Assert.assertFalse(table1.isSoundOn());

        table1.setAccessType("private");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PRIVATE, table1.getAccessType());
        table1.setAccessType("PriVAte");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PRIVATE, table1.getAccessType());
        table1.setAccessType("PRIVATE");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PRIVATE, table1.getAccessType());

        table1.setAccessType("protected");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PROTECTED, table1.getAccessType());
        table1.setAccessType("prOTecTed");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PROTECTED, table1.getAccessType());
        table1.setAccessType("PROTECTED");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PROTECTED, table1.getAccessType());

        table1.setAccessType("public");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PUBLIC, table1.getAccessType());
        table1.setAccessType("PubLIc");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PUBLIC, table1.getAccessType());
        table1.setAccessType("PUBLIC");
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PUBLIC, table1.getAccessType());

        table1.setAccessType(YokelTable.ACCESS_TYPE.PRIVATE);
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PRIVATE, table1.getAccessType());
        table1.setAccessType(YokelTable.ACCESS_TYPE.PROTECTED);
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PROTECTED, table1.getAccessType());
        table1.setAccessType(YokelTable.ACCESS_TYPE.PUBLIC);
        Assert.assertEquals(YokelTable.ACCESS_TYPE.PUBLIC, table1.getAccessType());

        Assert.assertEquals(8, table1.getSeats().size);
        Assert.assertFalse(table1.isTableStartReady());
        Assert.assertFalse(table1.isGroupReady(-1));
        Assert.assertFalse(table1.isGroupReady(5));

        YokelPlayer player1 = new YokelPlayer("blakbro2k");
        YokelPlayer player2 = new YokelPlayer("lholtham");
        YokelPlayer player3 = new YokelPlayer("notit");

        /**
         * Test table grouping
         * If one of the seats grouped are occupied with
         * another seperate group occupied, then you can start the game
         */

        //Check each ready method for each seat
        Assert.assertFalse(table1.isGroupReady(0));
        sitAndReadyPlayer(table1, 0, player1);
        Assert.assertTrue(table1.isGroupReady(0));
        table1.getSeat(0).standUp();
        Assert.assertFalse(table1.isGroupReady(0));

        Assert.assertFalse(table1.isGroupReady(0));
        sitAndReadyPlayer(table1, 1, player1);
        Assert.assertTrue(table1.isGroupReady(0));
        table1.getSeat(1).standUp();
        Assert.assertFalse(table1.isGroupReady(0));

        Assert.assertFalse(table1.isGroupReady(1));
        sitAndReadyPlayer(table1, 2, player1);
        Assert.assertTrue(table1.isGroupReady(1));
        table1.getSeat(2).standUp();
        Assert.assertFalse(table1.isGroupReady(1));

        Assert.assertFalse(table1.isGroupReady(1));
        sitAndReadyPlayer(table1, 3, player1);
        Assert.assertTrue(table1.isGroupReady(1));
        table1.getSeat(3).standUp();
        Assert.assertFalse(table1.isGroupReady(1));

        Assert.assertFalse(table1.isGroupReady(2));
        sitAndReadyPlayer(table1, 4, player1);
        Assert.assertTrue(table1.isGroupReady(2));
        table1.getSeat(4).standUp();
        Assert.assertFalse(table1.isGroupReady(2));

        Assert.assertFalse(table1.isGroupReady(2));
        sitAndReadyPlayer(table1, 5, player1);
        Assert.assertTrue(table1.isGroupReady(2));
        table1.getSeat(5).standUp();
        Assert.assertFalse(table1.isGroupReady(2));

        Assert.assertFalse(table1.isGroupReady(3));
        sitAndReadyPlayer(table1, 6, player1);
        Assert.assertTrue(table1.isGroupReady(3));
        table1.getSeat(6).standUp();
        Assert.assertFalse(table1.isGroupReady(3));

        Assert.assertFalse(table1.isGroupReady(3));
        sitAndReadyPlayer(table1, 7, player1);
        Assert.assertTrue(table1.isGroupReady(3));
        table1.getSeat(7).standUp();
        Assert.assertFalse(table1.isGroupReady(3));

        /**
         * Test table ready
         */
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 0, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 2, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(0).standUp();
        table1.getSeat(2).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 0, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 3, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(0).standUp();
        table1.getSeat(3).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 0, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 4, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(0).standUp();
        table1.getSeat(4).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 0, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 5, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(0).standUp();
        table1.getSeat(5).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 0, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 6, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(0).standUp();
        table1.getSeat(6).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 0, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 7, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(0).standUp();
        table1.getSeat(7).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 1, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 2, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(1).standUp();
        table1.getSeat(2).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 4, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 2, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(4).standUp();
        table1.getSeat(2).standUp();

        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 6, player2);
        Assert.assertFalse(table1.isTableStartReady());
        sitAndReadyPlayer(table1, 1, player3);
        Assert.assertTrue(table1.isTableStartReady());
        table1.getSeat(6).standUp();
        table1.getSeat(1).standUp();

        Assert.assertEquals(getJSON(table1), table1.toString());

        table1.dispose();
        Assert.assertEquals(table1.getSeats().size, 0);

        OrderedMap<String, Object> arguments = GdxMaps.newOrderedMap();
        arguments.put(YokelTable.ARG_TYPE, YokelTable.ENUM_VALUE_PROTECTED);
        arguments.put(YokelTable.ARG_RATED, false);
        YokelTable table2 = new YokelTable();
        Assert.assertEquals(table2.getAccessType().getValue(), YokelTable.ENUM_VALUE_PROTECTED);
        Assert.assertFalse(table2.isRated());
    }

    private void sitAndReadyPlayer(YokelTable table, int seat, YokelPlayer player){
        if(table != null && player != null){
            table.getSeat(seat).sitDown(player);
            table.getSeat(seat).setSeatReady(true);
        }
    }

    private String getJSON(Object o) {
        return YokelUtilities.getJsonString(o);
    }

    /*
        public void start(){
        isRunning = true;
        start = TimeUtils.millis();
    }

    public int getElapsedSeconds(){
        return (int) ((TimeUtils.millis() - start) / 1000);
    }

    public void stop(){
        resetTimer();
    }

    private void resetTimer(){
        start = 0;
        this.isRunning = false;
    }

    @Override
    public void dispose() {}
     */
    @Test
    public void YokelClockTest() {
        YokelClock clock = new YokelClock();
    }
/*
    @Test
    public void getType() {
        YokelBlock exp1 = new YokelBlock();
        System.out.println("Testing getType():" + exp1);
        Assert.assertEquals(YokelBlockType.class, exp1.getType().getClass());
        System.out.println("End getType() test:");
    }

    @Test
    public void setBroken() {
        YokelBlock exp1 = new YokelBlock();
        System.out.println("Testing setBroken():" + exp1);
        exp1.setBroken(true);
        Assert.assertTrue(exp1.isBroken());
        exp1.setBroken(false);
        Assert.assertFalse(exp1.isBroken());
        System.out.println("End setBroken() test:");
    }

    @Test
    public void isBroken() {
        YokelBlock exp1 = new YokelBlock();
        System.out.println("Testing getType():" + exp1);
        Assert.assertFalse(exp1.isBroken());
        System.out.println("End getType() test:");
    }

    @Test
    public void matchesType() {
        System.out.println("Testing matchesType():");
        YokelBlock exp1 = new YokelBlock(YokelBlockType.AttackY);
        YokelBlock exp2 = new YokelBlock(YokelBlockType.AttackO);
        YokelBlock exp3 = new YokelBlock(YokelBlockType.AttackY);

        System.out.println(exp1.matchesType(exp3.getType()));
        Assert.assertTrue(exp1.matchesType(exp3.getType()));
        Assert.assertTrue(exp3.matchesType(exp1.getType()));
        Assert.assertFalse(exp2.matchesType(exp1.getType()));
        Assert.assertFalse(exp2.matchesType(exp3.getType()));

        System.out.println("End matchesType() test:");
    }

    @Test
    public void matchesGenericType() {
        System.out.println("Testing matchesGenericType():");
        YokelBlock exp1 = new YokelBlock(YokelBlockType.NormalY);
        YokelBlock exp2 = new YokelBlock(YokelBlockType.AttackY);
        YokelBlock exp3 = new YokelBlock(YokelBlockType.DefenseY);
        YokelBlock exp4 = new YokelBlock(YokelBlockType.BrokenY);

        YokelBlock exp5 = new YokelBlock(YokelBlockType.NormalO);
        YokelBlock exp6 = new YokelBlock(YokelBlockType.AttackO);
        YokelBlock exp7 = new YokelBlock(YokelBlockType.DefenseO);
        YokelBlock exp8 = new YokelBlock(YokelBlockType.BrokenO);

        YokelBlock exp9 = new YokelBlock(YokelBlockType.NormalK);
        YokelBlock exp10 = new YokelBlock(YokelBlockType.AttackK);
        YokelBlock exp11 = new YokelBlock(YokelBlockType.DefenseK);
        YokelBlock exp12 = new YokelBlock(YokelBlockType.BrokenK);

        YokelBlock exp13 = new YokelBlock(YokelBlockType.NormalE);
        YokelBlock exp14 = new YokelBlock(YokelBlockType.AttackE);
        YokelBlock exp15 = new YokelBlock(YokelBlockType.DefenseE);
        YokelBlock exp16 = new YokelBlock(YokelBlockType.BrokenE);

        YokelBlock exp17 = new YokelBlock(YokelBlockType.NormalL);
        YokelBlock exp18 = new YokelBlock(YokelBlockType.AttackL);
        YokelBlock exp19 = new YokelBlock(YokelBlockType.DefenseL);
        YokelBlock exp20 = new YokelBlock(YokelBlockType.BrokenL);

        YokelBlock exp21 = new YokelBlock(YokelBlockType.NormalEx);
        YokelBlock exp22 = new YokelBlock(YokelBlockType.AttackEx);
        YokelBlock exp23 = new YokelBlock(YokelBlockType.DefenseEx);
        YokelBlock exp24 = new YokelBlock(YokelBlockType.BrokenEx);

        Assert.assertTrue(exp1.matchesGenericType(exp1));
        Assert.assertTrue(exp1.matchesGenericType(exp2));
        Assert.assertTrue(exp1.matchesGenericType(exp3));
        Assert.assertTrue(exp1.matchesGenericType(exp4));
        Assert.assertFalse(exp1.matchesGenericType(exp5));

        Assert.assertTrue(exp5.matchesGenericType(exp5));
        Assert.assertTrue(exp5.matchesGenericType(exp6));
        Assert.assertTrue(exp5.matchesGenericType(exp7));
        Assert.assertTrue(exp5.matchesGenericType(exp8));
        Assert.assertFalse(exp5.matchesGenericType(exp1));

        Assert.assertTrue(exp9.matchesGenericType(exp9));
        Assert.assertTrue(exp9.matchesGenericType(exp10));
        Assert.assertTrue(exp9.matchesGenericType(exp11));
        Assert.assertTrue(exp9.matchesGenericType(exp12));
        Assert.assertFalse(exp9.matchesGenericType(exp5));

        Assert.assertTrue(exp13.matchesGenericType(exp13));
        Assert.assertTrue(exp13.matchesGenericType(exp14));
        Assert.assertTrue(exp13.matchesGenericType(exp15));
        Assert.assertTrue(exp13.matchesGenericType(exp16));
        Assert.assertFalse(exp13.matchesGenericType(exp5));

        Assert.assertTrue(exp17.matchesGenericType(exp17));
        Assert.assertTrue(exp17.matchesGenericType(exp18));
        Assert.assertTrue(exp17.matchesGenericType(exp19));
        Assert.assertTrue(exp17.matchesGenericType(exp20));
        Assert.assertFalse(exp17.matchesGenericType(exp5));

        Assert.assertTrue(exp21.matchesGenericType(exp21));
        Assert.assertTrue(exp21.matchesGenericType(exp22));
        Assert.assertTrue(exp21.matchesGenericType(exp23));
        Assert.assertTrue(exp21.matchesGenericType(exp24));
        Assert.assertFalse(exp21.matchesGenericType(exp5));
        System.out.println("End matchesGenericType() test:");
    }

    @Test
    public void testToString() {
        System.out.println("Testing testToString():");
        YokelBlock exp1 = new YokelBlock();
        Assert.assertNotNull(exp1);
        Assert.assertEquals(String.class, exp1.toString().getClass());
        System.out.println("End testToString() test:");
    }

    @Test
    public void testRandomInitializer() {
        System.out.println("Testing testRandomInitializer():");
        YokelBlock exp1 = new YokelBlock();
        Assert.assertNotNull(exp1);
        Assert.assertEquals(YokelBlock.class, exp1.getClass());
        System.out.println("End testRandomInitializer() test:");
    }

    @Test
    public void testObjectEquals() {
        //YokelBlock
        YokelBlock exp1 = new YokelBlock(YokelBlockType.DefenseE);
        YokelBlock exp2 = new YokelBlock(YokelBlockType.DefenseY);
        YokelBlock exp3 = new YokelBlock(YokelBlockType.DefenseE);
        String str = "block";

        Assert.assertNotSame(null, exp1);
        Assert.assertNotSame("Test Block", exp1, exp2);
        Assert.assertEquals(exp1, exp3);
        Assert.assertEquals(exp1.getType(), YokelBlockType.DefenseE);
        Assert.assertNotSame(exp1, str);

        Array<YokelBlock> expectedBlocks = createAllBlockArray();
        Array<YokelBlock> testingBlocks = createAllBlockArray();

        for(int i = 0; i < testingBlocks.size; i++){
            Assert.assertEquals(expectedBlocks.get(i), testingBlocks.get(i));
        }

        //YokelLounge
        YokelLounge expected = new YokelLounge("testing");
        YokelLounge expected2 = new YokelLounge("testing2");
        YokelLounge testing = new YokelLounge("testing");
        YokelLounge testing2 = new YokelLounge("testing2");

        Assert.assertEquals(expected, testing);
        Assert.assertNotSame(expected2, testing);
        expected2.addRoom(new YokelRoom("room"));
        Assert.assertNotSame(expected2, testing);
        expected.addRoom(new YokelRoom("room"));
        testing.addRoom(new YokelRoom("room"));
        Assert.assertEquals(expected, testing);
        Assert.assertEquals(testing2, testing2);

        //YokelPlayer
        YokelPlayer player1 = new YokelPlayer("ReadyPlwyer1");
        YokelPlayer player2 = new YokelPlayer("ReadyPlwyer2");
        YokelPlayer player3 = new YokelPlayer("ReadyPlwyer1");
        YokelPlayer player4 = new YokelPlayer("ReadyPlwyer2");
        Assert.assertEquals(player1, player3);
        Assert.assertNotSame(player1, player2);
        Assert.assertEquals(player4, player2);


        //YokelRoom
        YokelRoom room1 = new YokelRoom("Room1");
        //throw new AssertionError("YokelRoom not finished.");


        //YokelSeat
        YokelSeat seat1 = new YokelSeat(1);
        YokelSeat seat2 = new YokelSeat(1);
        seat2.sitDown(player1);
        YokelSeat seat3 = new YokelSeat(1);
        YokelSeat seat4 = new YokelSeat(2);
        Assert.assertEquals(seat1, seat3);
        Assert.assertNotSame(seat1, seat2);
        seat2.standUp();
        Assert.assertEquals(seat1, seat2);
        seat2.sitDown(player1);
        seat1.sitDown(player2);
        seat3.sitDown(player1);

        Assert.assertEquals(seat2, seat3);
        Assert.assertNotSame(player2, player1);
        Assert.assertNotSame(seat1, seat2);
        Assert.assertNotSame(seat1, seat4);
        seat4.sitDown(player2);
        Assert.assertNotSame(seat1, seat4);

        //YokelTable
        throw new AssertionError("YokelTable not finished.");
    }

    @Test
    public void testSpecifiedRandomInitializer() {
        System.out.println("Testing testSpecifiedRandomInitializer():");
        YokelBlock exp1 = new YokelBlock(YokelBlock.INIT_TYPE.ANY);
        Assert.assertNotNull(exp1);
        Assert.assertEquals(YokelBlock.class, exp1.getClass());

        YokelBlock exp2 = new YokelBlock(YokelBlock.INIT_TYPE.POWER);
        Assert.assertNotNull(exp2);
        Assert.assertEquals(YokelBlock.class, exp2.getClass());
        Assert.assertTrue(Util.isPowerBlock(exp2));

        YokelBlock exp3 = new YokelBlock(YokelBlock.INIT_TYPE.DEFENSE);
        Assert.assertNotNull(exp3);
        Assert.assertEquals(YokelBlock.class, exp3.getClass());
        Assert.assertTrue(Util.isDefenseBlock(exp3));
        System.out.println("End testSpecifiedRandomInitializer() test:");
    }

    @Test
    public void testSpecifiedInitializer() {
        System.out.println("Testing testSpecifiedInitializer():");

        Array<YokelBlock> blocks = createAllBlockArray();
        Array<YokelBlockType> types = completeYokelTypeList();

        for(int i = 0; i < types.size; i++){
            System.out.println("Asserting type=" + types.get(i) + " against block=" + blocks.get(i));
            Assert.assertEquals(types.get(i), blocks.get(i).getType());
        }

        System.out.println("End testSpecifiedInitializer() test:");
    }

    @Test
    public void promote() {
        YokelBlock exp1 = new YokelBlock(YokelBlock.INIT_TYPE.NORMAL);
        exp1.promote(true);
        Assert.assertTrue(Util.isPowerBlock(exp1));
        exp1.promote(false);
        Assert.assertTrue(Util.isDefenseBlock(exp1));
    }

    @Test
    public void demote() {
        YokelBlock exp1 = new YokelBlock(YokelBlock.INIT_TYPE.NORMAL);
        exp1.promote(false);
        Assert.assertTrue(Util.isDefenseBlock(exp1));
        exp1.demote();
        Assert.assertTrue(!Util.isPowerBlock(exp1) && !Util.isDefenseBlock(exp1));
        exp1.promote(true);
        Assert.assertTrue(Util.isPowerBlock(exp1));
        exp1.demote();
        Assert.assertTrue(!Util.isPowerBlock(exp1) && !Util.isDefenseBlock(exp1));
    }

    @Test
    public void testBreakBlock() {
        YokelBlock exp1 = new YokelBlock(YokelBlock.INIT_TYPE.NORMAL);
        YokelBlock power = exp1.breakBlock();

        Assert.assertNull(power);
        Assert.assertTrue(Util.isBrokenBlock(exp1));

        YokelBlock exp2 = new YokelBlock(YokelBlock.INIT_TYPE.POWER);
        YokelBlock power2 = exp2.breakBlock();

        Assert.assertTrue(Util.isBrokenBlock(exp2));
        exp2.promote(true);
        Assert.assertEquals(power2, exp2);

        YokelBlock exp3 = new YokelBlock(YokelBlock.INIT_TYPE.DEFENSE);
        YokelBlock power3 = exp3.breakBlock();

        Assert.assertTrue(Util.isBrokenBlock(exp3));
        exp3.promote(false);
        Assert.assertEquals(power3, exp3);

        YokelBlock any = new YokelBlock(YokelBlock.INIT_TYPE.NORMAL);
        YokelBlock anyPower = any.breakBlock();

        Assert.assertNull(anyPower);
        Assert.assertTrue(Util.isBrokenBlock(any));

        any.promote(true);
        Assert.assertTrue(Util.isPowerBlock(any));
        anyPower = any.breakBlock();
        Assert.assertTrue(Util.isBrokenBlock(any));
        any.promote(true);
        Assert.assertEquals(anyPower, any);

        any.promote(false);
        Assert.assertTrue(Util.isDefenseBlock(any));
        anyPower = any.breakBlock();
        Assert.assertTrue(Util.isBrokenBlock(any));
        any.promote(false);
        Assert.assertEquals(anyPower, any);
    }

    private Array<YokelBlock> createAllBlockArray(){
        Array<YokelBlock> array = new Array<>();
        Array<YokelBlockType> types = completeYokelTypeList();

        for(YokelBlockType type : Util.toIterable(types)){
            array.add(new YokelBlock(type));
        }

        return array;
    }

    public static Array<YokelBlockType> completeYokelTypeList(){
        Array<YokelBlockType> array = new Array<>();
        array.add(YokelBlockType.AttackY);
        array.add(YokelBlockType.AttackO);
        array.add(YokelBlockType.AttackK);
        array.add(YokelBlockType.AttackE);
        array.add(YokelBlockType.AttackL);
        array.add(YokelBlockType.AttackEx);
        array.add(YokelBlockType.DefenseY);
        array.add(YokelBlockType.DefenseO);
        array.add(YokelBlockType.DefenseK);
        array.add(YokelBlockType.DefenseE);
        array.add(YokelBlockType.DefenseL);
        array.add(YokelBlockType.DefenseEx);
        array.add(YokelBlockType.NormalY);
        array.add(YokelBlockType.NormalO);
        array.add(YokelBlockType.NormalK);
        array.add(YokelBlockType.NormalE);
        array.add(YokelBlockType.NormalL);
        array.add(YokelBlockType.NormalEx);
        array.add(YokelBlockType.Clear);
        array.add(YokelBlockType.Midas);
        array.add(YokelBlockType.Medusa);
        array.add(YokelBlockType.Stone);
        return array;
    }*/
}