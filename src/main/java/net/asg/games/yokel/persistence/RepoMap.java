package net.asg.games.yokel.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RepoMap<String, V> extends HashMap<String, V> {
    public static final java.lang.String ATT_REPO = "Repository";

    @Autowired
    YokelRoomRepository yokelRoomRepository;

    @Autowired
    YokelTableRepository yokelTableRepository;

    @Autowired
    YokelPlayerRepository yokelPlayerRepository;

    @Autowired
    YokelSeatRepository yokelSeatRepository;

    public RepoMap() {
        super();

        //this.put("YokelRoomRepository", yokelRoomRepository);
        //repos.put("YokelTableRepository", yokelTableRepository);
        //repos.put("YokelPlayerRepository", yokelPlayerRepository);
        //repos.put("YokelSeatRepository", yokelSeatRepository);
    }
}
