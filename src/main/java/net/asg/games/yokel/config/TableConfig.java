package net.asg.games.yokel.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import net.asg.games.yokel.data.RoomConfigDTO;
import net.asg.games.yokel.data.RoomConfigDTO.RoomDTO;
import net.asg.games.yokel.objects.YokelRoom;
import net.asg.games.yokel.persistence.YokelRoomRepository;
import net.asg.games.yokel.services.impl.YokelGameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Configuration
@ImportResource("classpath:rooms.xml")
public class TableConfig {

    @Autowired
    YokelGameServiceImpl yokelGameServiceImpl;

    //private @Value("#{Lounge}") Object rooms;
    @Value("classpath:rooms.xml")
    private Resource resource;

    public TableConfig()  {
    }

    @Bean
    public void loadRooms() throws IOException {
        File file = resource.getFile();
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(file));
        RoomConfigDTO value = xmlMapper.readValue(xml, RoomConfigDTO.class);
        createDatabaseLounges(value);
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

    public void createDatabaseLounges(RoomConfigDTO roomConfig) {
        if (roomConfig != null) {
            RoomConfigDTO.LoungeDTO[] dto = roomConfig.getLoungeDTO();

            if (dto != null) {
                for (RoomConfigDTO.LoungeDTO loungeDTO : dto) {
                    if (loungeDTO != null) {
                        if (!loungeDTO.getDisabled()) {
                            String loungeName = loungeDTO.getName();
                            //System.out.println("Creating " + loungeName + " Lounge.");

                            RoomDTO[] roomDtos = loungeDTO.getRoomDTO();

                            if (roomDtos != null) {
                                for (RoomDTO roomDTO : roomDtos) {
                                    if (roomDTO != null) {
                                        if (!roomDTO.getDisabled()) {
                                            String roomName = roomDTO.getName();
                                            //System.out.println("Creating " + roomName + " Room.");

                                            if(yokelGameServiceImpl.getRoom(roomName) == null) {
                                                yokelGameServiceImpl.saveObject(new YokelRoom(roomName, loungeName));
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
    }
}