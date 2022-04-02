package net.asg.games.yokel;

import net.asg.games.yokel.objects.YokelRoom;
import net.asg.games.yokel.services.impl.YokelGameServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

//@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StorageTest {
    //@Autowired
    //YokelGameServiceImpl yokelGameServiceImpl;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testMemoryStorage() throws Exception {
        //yokelGameServiceImpl.getObjectByName(YokelRoom.class, "kl");
    }
}