package net.asg.games.yokel.controllers;

import lombok.extern.slf4j.Slf4j;
import net.asg.games.utils.YokelUtilities;
import net.asg.games.yokel.objects.YokelLounge;
import net.asg.games.yokel.objects.YokelRoom;
import net.asg.games.yokel.objects.YokelTable;
import net.asg.games.yokel.services.impl.YokelGameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class YokelGameController {
    @Autowired
    private YokelGameServiceImpl yokelGameService;
    private final Map<String, List<YokelRoom>> roomMap;
    private boolean runOnce = false;

    public YokelGameController(){
        roomMap = new HashMap<>();
    }

    @Autowired
    private YokelGameServiceImpl yokelGameServiceImpl;

    private void buildRoomMap (){
        if(!runOnce) {
            runOnce = true;
            List<YokelRoom> rooms = yokelGameService.getAllRooms();

            if(!YokelUtilities.isEmpty(rooms)) {
                for(YokelRoom room : YokelUtilities.safeIterable(rooms)) {
                    if(room != null) {
                        String loungeName = room.getLoungeName();
                        List<YokelRoom> listOfRooms = roomMap.get(loungeName);
                        if(listOfRooms == null) {
                            listOfRooms = new ArrayList<>();
                        }
                        listOfRooms.add(room);
                        roomMap.put(loungeName, listOfRooms);
                    }
                }
            }
        }
    }

    private void addRoomsToModel(Model model) {
        buildRoomMap();
        model.addAttribute("allSocialRooms", roomMap.get(YokelRoom.SOCIAL_GROUP));
        model.addAttribute("allBeginnerRooms", roomMap.get(YokelRoom.BEGINNER_GROUP));
        model.addAttribute("allIntermediateRooms", roomMap.get(YokelRoom.INTERMEDIATE_GROUP));
        model.addAttribute("allAdvancedRooms", roomMap.get(YokelRoom.ADVANCED_GROUP));
    }

    @GetMapping("/")
    public String index(Model model) {
        addRoomsToModel(model);
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        addRoomsToModel(model);
        return "home";
    }

    @GetMapping("/room/{id}")
    public String room(@PathVariable(value = "id") String id, Model model) {
        YokelRoom room = yokelGameService.getObjectById(YokelRoom.class, id);
        if(room != null) {
            model.addAttribute("roomTitle", room.getName());
            System.out.println(room);
        }
        return "room";
    }

    @RequestMapping(value="/#",params="playNew",method=RequestMethod.POST)
    public void playNew()
    {
        System.out.println("Action1 block called");
    }

    @ResponseBody
    @RequestMapping(value = "/game.html")
    public String open(@RequestParam(value="id") Integer id) {
        //Link link = linkDAO.get(id);
        //linkDAO.click(id);
        return "goToGame";
    }
}

