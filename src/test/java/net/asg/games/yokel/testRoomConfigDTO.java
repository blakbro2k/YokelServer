package net.asg.games.yokel;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import net.asg.games.yokel.data.RoomConfigDTO;
import net.asg.games.yokel.data.RoomConfigDTO.LoungeDTO;
import net.asg.games.yokel.data.RoomConfigDTO.RoomDTO;
import net.asg.games.yokel.objects.*;
import net.asg.games.yokel.persistence.*;
import net.asg.games.yokel.services.impl.YokelGameServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//ExtendWith(Spring.class)
public class testRoomConfigDTO {
    private Map<String, YokelRepository<? extends YokelObject, ? extends String>> repos = new HashMap<>();

    @Autowired
    YokelRoomRepository yokelRoomRepository;

    @Autowired
    YokelTableRepository yokelTableRepository;

    @Autowired
    YokelPlayerRepository yokelPlayerRepository;

    @Autowired
    YokelSeatRepository yokelSeatRepository;

    @Test
    public void getRepoInstanceTest() {
        System.out.println(getRepoInstance(YokelRoom.class));
        System.out.println(getRepoInstance(YokelTable.class));
        System.out.println(getRepoInstance(YokelSeat.class));
        System.out.println(getRepoInstance(YokelPlayer.class));
    }

    private <T extends YokelRepository<? extends YokelObject, ? extends String>> T getRepoInstance(Class<? extends YokelObject> clazz) {
        repos.put("YokelRoomRepository", yokelRoomRepository);
        repos.put("YokelTableRepository", yokelTableRepository);
        repos.put("YokelPlayerRepository", yokelPlayerRepository);
        repos.put("YokelSeatRepository", yokelSeatRepository);

        return (T) repos.get(clazz.getSimpleName()/* + YokelGameServiceImpl.ATT_REPO*/);
    }

    @Test
    public void testGetRoomLounge() throws Exception {
        String pathName = "U:\\yokel\\src\\main\\resources\\rooms.xml";
        //File file = new File(pathName);
        //File file = resource.getFile();
        //XmlMapper xmlMapper = new XmlMapper();
        //String xml = inputStreamToString(new FileInputStream(file));
        //RoomConfigDTO value = xmlMapper.readValue(xml, RoomConfigDTO.class);
        //assertTrue(value.getX() == 1 && value.getY() == 2);
        //System.out.println("xml: \n" + xml);
        //System.out.println("value: " + value.getRoomName());
        //System.out.println("value: " + value);
        //System.out.println("value: " + value.getRoomName());

        RoomDTO eiffel_tower = new RoomDTO("Eiffel Tower");
        RoomDTO leaningTowerOfPisa = new RoomDTO("Leaning Tower of Pisa");
        RoomDTO sunshine = new RoomDTO("Sunshine 60 Tower");
        RoomDTO towerOfBabelVoiceChat = new RoomDTO("Tower of Babel Voice Chat", true);
        RoomDTO twoUnionSquare = new RoomDTO("Two Union Square", true);
        RoomDTO changTower = new RoomDTO("Chang Tower");
        RoomDTO empireStateBuilding = new RoomDTO("Empire State Building", true);
        RoomDTO keyTower = new RoomDTO("Key Tower", true);
        RoomDTO olympiaCentre = new RoomDTO("Olympia Centre", true);
        RoomDTO oneAstorPlace = new RoomDTO("One Astor Place", true);
        RoomDTO searsTower = new RoomDTO("Sears Tower", true);
        RoomDTO worldTrade = new RoomDTO("World Trade Center (in memory)");
        RoomDTO cnTower = new RoomDTO("CN Tower");
        RoomDTO advanced_tournament_advanced = new RoomDTO("Advanced Tournament");
        RoomDTO beginnerTournamentBeginner = new RoomDTO("Beginner Tournament");
        RoomDTO gregariousGroupSocial = new RoomDTO("Gregarious Group");

        LoungeDTO social = new LoungeDTO("Social", eiffel_tower, leaningTowerOfPisa, sunshine, towerOfBabelVoiceChat, twoUnionSquare);
        LoungeDTO beginner = new LoungeDTO("Beginner", false, changTower, empireStateBuilding, keyTower, olympiaCentre, oneAstorPlace, searsTower);
        LoungeDTO intermediate = new LoungeDTO("Intermediate", true, worldTrade);
        LoungeDTO expert = new LoungeDTO("Advanced", true, cnTower);
        LoungeDTO allstar = new LoungeDTO("All-Star", true, advanced_tournament_advanced, beginnerTournamentBeginner, gregariousGroupSocial);

        RoomConfigDTO dto = new RoomConfigDTO(social);

        System.out.println(dto);

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(dto);
        System.out.println(xml);

        ObjectMapper objectMapper = new XmlMapper();
        //objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // Reads from XML and converts to POJO
        RoomConfigDTO roomConfigDTO = objectMapper.readValue(StringUtils.toEncodedString(Files.readAllBytes(Paths.get(pathName)), StandardCharsets.UTF_8), RoomConfigDTO.class);
        Object fieldSet = new Object();

        createDatabaseLounges(roomConfigDTO);
        // Reads from POJO and converts to XML
        //objectMapper.writeValue(, dto);
        //System.out.println(fieldSet);


    }

    public void createDatabaseLounges(RoomConfigDTO roomConfig) {
        if (roomConfig != null) {
            LoungeDTO[] dto = roomConfig.getLoungeDTO();

            if (dto != null) {
                for (LoungeDTO loungeDTO : dto) {
                    if (loungeDTO != null) {
                        if (!loungeDTO.getDisabled()) {
                            String loungeName = loungeDTO.getName();
                            System.out.println("Creating " + loungeName + " Lounge.");

                            RoomDTO[] roomDtos = loungeDTO.getRoomDTO();

                            if (roomDtos != null) {
                                for (RoomDTO roomDTO : roomDtos) {
                                    if (roomDTO != null) {
                                        if (!roomDTO.getDisabled()) {
                                            String roomName = roomDTO.getName();
                                            System.out.println("Creating " + roomName + " Room.");

                                        }
                                    }
                                }
                            }


                        }
                    }
                }
            }
        }
    }

    public String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    static void print(String value) {
        System.out.print(value);
    }

    /**
     * Function to find the missing number in a array
     * @param arr : given array
     * @param max : value of maximum number in the series
     */
    static int findMissingNumber(int[] arr, int max){
        //3
        Arrays.sort(arr);
        //4
        int currentValue = 1;
        int missingValue = -1;
        boolean foundFirstMissing = false;

        //5
        for (int i = 0; i < arr.length; i++) {
            //6
            System.out.println("currentVal: " + currentValue);
            System.out.println("arr[i]: " + arr[i]);
            if (arr[i] != currentValue) {
                for (int j = currentValue; j < arr[i]; j++) {
                    missingValue = j;
                    foundFirstMissing = true;
                    break;

                }
            }
            currentValue = arr[i] + 1;
            if(foundFirstMissing) {
                break;
            }
        }
        return missingValue;
    }


    @Test
    public void main() {

        String seatId = "db4902c93a904830445-34";
        System.out.println(StringUtils.split(seatId,"-")[1]);

        //1
        int[] givenArr = {1,2,3,4,5,6,7,8,11,9,10};
        Assert.assertEquals(findMissingNumber(givenArr, givenArr[givenArr.length - 1]), -1);
        int[] givenArr2 = {1,2,3,4,5,6,7,8,11,10};
        Assert.assertEquals(findMissingNumber(givenArr2, givenArr[givenArr.length - 1]), 9);
        int[] givenArr3 = {1,2,3,4,5,6,7,8,10};
        Assert.assertEquals(findMissingNumber(givenArr3, givenArr[givenArr.length - 1]), 9);
        int[] givenArr4 = {1,3,4,5,6,7,8,10};
        Assert.assertEquals(findMissingNumber(givenArr4, givenArr[givenArr.length - 1]), 2);
        int[] givenArr5 = {3,4,5,6,7,8,10,11};
        Assert.assertEquals(findMissingNumber(givenArr5, givenArr[givenArr.length - 1]), 1);

    }
}
