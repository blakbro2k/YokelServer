package net.asg.games.yokel.services.impl;

import com.badlogic.gdx.utils.ObjectMap;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import net.asg.games.utils.YokelUtilities;
import net.asg.games.yokel.managers.GameManager;
import net.asg.games.yokel.objects.YokelLounge;
import net.asg.games.yokel.objects.YokelObject;
import net.asg.games.yokel.objects.YokelObjectJPAVisitor;
import net.asg.games.yokel.objects.YokelPlayer;
import net.asg.games.yokel.objects.YokelRoom;
import net.asg.games.yokel.objects.YokelSeat;
import net.asg.games.yokel.objects.YokelTable;
import net.asg.games.yokel.persistence.AbstractStorage;
import net.asg.games.yokel.persistence.RepoMap;
import net.asg.games.yokel.persistence.YokelPlayerRepository;
import net.asg.games.yokel.persistence.YokelRepository;
import net.asg.games.yokel.persistence.YokelRoomRepository;
import net.asg.games.yokel.persistence.YokelSeatRepository;
import net.asg.games.yokel.persistence.YokelTableRepository;
import net.asg.games.yokel.services.YokelGameServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class YokelGameServiceImpl extends AbstractStorage implements YokelGameServices {
    private static final String ATT_REPO = "Repository";
    private RepoMap<String, YokelRepository<?, String>> repos = new RepoMap<>();

    @Autowired
    YokelRoomRepository yokelRoomRepository;

    @Autowired
    YokelTableRepository yokelTableRepository;

    @Autowired
    YokelPlayerRepository yokelPlayerRepository;

    @Autowired
    YokelSeatRepository yokelSeatRepository;

    @Override
    public void dispose() {
        //Spring takes care of destroying repository
    }

   private <T extends JpaRepository<T, String>> T getRepoInstance(Class<T> clazz) {
       YokelRepository<?, String> repo = repos.get(clazz.getSimpleName() + YokelGameServiceImpl.ATT_REPO);

        return (T) repos.get(clazz.getSimpleName() + YokelGameServiceImpl.ATT_REPO);
   }

    @Override
    @NotNull
    public <T extends YokelObject> T getObjectByName(Class<T> clazz, String name) {
        //System.out.println(getRepoInstance(YokelRoom.class));
        //System.out.println(getRepoInstance(YokelTable.class));
       // System.out.println(getRepoInstance(YokelSeat.class));
        //System.out.println(getRepoInstance(YokelPlayer.class));

        if (clazz.equals(YokelRoom.class)) {
            return clazz.cast(yokelRoomRepository.findByName(name));
        } else if (clazz.equals(YokelTable.class)) {
            return clazz.cast(yokelTableRepository.findByName(name));
        } else if (clazz.equals(YokelSeat.class)) {
            return clazz.cast(yokelSeatRepository.findByName(name));
        } else if (clazz.equals(YokelPlayer.class)) {
            return clazz.cast(yokelPlayerRepository.findByName(name));
        }
        return null;
    }

    @Override
    public <T extends YokelObject> T getObjectById(Class<T> clazz, String id) {
        if (clazz.equals(YokelRoom.class)) {
            Optional<YokelRoom> object = yokelRoomRepository.findById(id);
            return object.map(clazz::cast).orElse(null);
        } else if (clazz.equals(YokelTable.class)) {
            Optional<YokelTable> object = yokelTableRepository.findById(id);
            return object.map(clazz::cast).orElse(null);
        } else if (clazz.equals(YokelSeat.class)) {
            Optional<YokelSeat> object = yokelSeatRepository.findById(id);
            return object.map(clazz::cast).orElse(null);
        } else if (clazz.equals(YokelPlayer.class)) {
            Optional<YokelPlayer> object = yokelPlayerRepository.findById(id);
            return object.map(clazz::cast).orElse(null);
        }
        return null;
    }

    @Override
    public <T extends YokelObject> List<T> getObjects(Class<T> clazz) {
        if (clazz.equals(YokelRoom.class)) {
            return (List<T>) yokelRoomRepository.findAll();
        } else if (clazz.equals(YokelTable.class)) {
            return (List<T>) yokelTableRepository.findAll();
        } else if (clazz.equals(YokelSeat.class)) {
            return (List<T>) clazz.cast(yokelSeatRepository.findAll());
        } else if (clazz.equals(YokelPlayer.class)) {
            return (List<T>) clazz.cast(yokelPlayerRepository.findAll());
        }
        return Collections.emptyList();
    }

    @Override
    public <T extends YokelObject> int countObjects(Class<T> clazz) {
        if (clazz.equals(YokelRoom.class)) {
            return (int) yokelRoomRepository.count();
        } else if (clazz.equals(YokelTable.class)) {
            return (int) yokelTableRepository.count();
        } else if (clazz.equals(YokelSeat.class)) {
            return (int) yokelSeatRepository.count();
        } else if (clazz.equals(YokelPlayer.class)) {
            return (int) yokelPlayerRepository.count();
        }
        return 0;
    }

    private void save(Object object){
        if (object instanceof YokelRoom) {
            yokelRoomRepository.save(((YokelRoom) object));
        } else if (object instanceof YokelTable) {
            yokelTableRepository.save(((YokelTable) object));
        } else if (object instanceof YokelSeat) {
            yokelSeatRepository.save(((YokelSeat) object));
        } else if (object instanceof YokelPlayer) {
            yokelPlayerRepository.save(((YokelPlayer) object));
        }
    }

    @Override
    public void saveObject(Object object) {
        if(object instanceof YokelObjectJPAVisitor){
            ((YokelObjectJPAVisitor)object).visitSave(this);
        }
        save(object);
    }

    @Override
    public void commitTransactions() {

    }

    @Override
    public void rollBackTransactions() {

    }

    @Override
    public void putLounge(YokelLounge yokelLounge) throws Exception {

    }

    @Override
    public YokelLounge getLounge(String s) {
        return null;
    }

    @Override
    public ObjectMap.Values<YokelLounge> getAllLounges() {
        return null;
    }

    @Override
    public void putRoom(YokelRoom room) throws Exception {
        saveObject(room);
    }

    @Override
    public YokelRoom getRoom(String nameOrId) {
        YokelRoom room = getObjectByName(YokelRoom.class, nameOrId);
        if (room == null) {
            room = getObjectById(YokelRoom.class, nameOrId);
        }
        return room;
    }

    @Override
    public void putTable(YokelTable table) throws Exception {
        saveObject(table);
    }

    @Override
    public YokelTable getTable(String nameOrId) {
        YokelTable table = getObjectByName(YokelTable.class, nameOrId);
        if (table == null) {
            table = getObjectById(YokelTable.class, nameOrId);
        }
        return table;
    }

    @Override
    public List<YokelTable> getAllTables() {
        return getObjects(YokelTable.class);
    }

    @Override
    public void putAllTables(Iterable<YokelTable> iterable) {
        for (YokelTable table : YokelUtilities.safeIterable(iterable)) {
            if (table != null) {
                saveObject(table);
            }
        }
    }

    @Override
    public void putSeat(YokelSeat seat) throws Exception {
        saveObject(seat);
    }

    @Override
    public YokelSeat getSeat(String nameOrId) {
        YokelSeat seat = getObjectByName(YokelSeat.class, nameOrId);
        if (seat == null) {
            seat = getObjectById(YokelSeat.class, nameOrId);
        }
        return seat;
    }

    @Override
    public List<YokelSeat> getAllSeats() {
        return getObjects(YokelSeat.class);
    }

    @Override
    public void putAllSeats(Iterable<YokelSeat> iterable) {
        for (YokelSeat seat : YokelUtilities.safeIterable(iterable)) {
            if (seat != null) {
                saveObject(seat);
            }
        }
    }

    @Override
    public void putPlayer(YokelPlayer player) throws Exception {
        saveObject(player);
    }

    @Override
    public YokelPlayer getPlayer(String nameOrId) {
        YokelPlayer player = getObjectByName(YokelPlayer.class, nameOrId);
        if (player == null) {
            player = getObjectById(YokelPlayer.class, nameOrId);
        }
        return player;
    }

    @Override
    public List<YokelPlayer> getAllPlayers() {
        return getObjects(YokelPlayer.class);
    }

    @Override
    public void putAllPlayers(Iterable<YokelPlayer> iterable) {
        for (YokelPlayer player : YokelUtilities.safeIterable(iterable)) {
            if (player != null) {
                saveObject(player);
            }
        }
    }


    @Override
    public void putGame(String s, GameManager gameManager) {

    }

    @Override
    public GameManager getGame(String s) {
        return null;
    }

    @Override
    public ObjectMap.Values<GameManager> getAllGames() {
        return null;
    }

    @Override
    public void putAllGames(Iterable<GameManager> iterable) {

    }

    @Override
    public List<YokelRoom> getAllRooms() {
        return getObjects(YokelRoom.class);
    }

    @Override
    public void putAllRooms(Iterable<YokelRoom> iterable) {
        for (YokelRoom room : YokelUtilities.safeIterable(iterable)) {
            if (room != null) {
                saveObject(room);
            }
        }
    }

    @Override
    public void registerPlayer(String s, YokelPlayer yokelPlayer) throws Exception {

    }

    @Override
    public void unRegisterPlayer(String s) throws Exception {

    }

    @Override
    public YokelPlayer getRegisteredPlayerGivenId(String s) {
        return null;
    }

    @Override
    public YokelPlayer getRegisteredPlayer(YokelPlayer yokelPlayer) {
        return null;
    }

    @Override
    public ObjectMap.Values<YokelPlayer> getAllRegisteredPlayers() {
        return null;
    }

    @Override
    public boolean isClientRegistered(String s) {
        return false;
    }

    @Override
    public boolean isPlayerRegistered(String s) {
        return false;
    }
}
