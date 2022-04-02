package net.asg.games.yokel;

import lombok.extern.slf4j.Slf4j;
import net.asg.games.yokel.objects.YokelRoom;
import net.asg.games.yokel.objects.YokelTable;
import net.asg.games.yokel.persistence.YokelRoomRepository;
import net.asg.games.yokel.persistence.YokelTableRepository;
import net.asg.games.yokel.services.impl.YokelGameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class YokelApplication extends SpringBootServletInitializer {
	@Autowired
	private YokelGameServiceImpl yokelGameService;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YokelApplication.class);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				//System.out.println(beanName);
			}

		};
	}

	@Bean
	public CommandLineRunner loadData(YokelRoomRepository repo) {
		return args -> {
			//System.out.println("Saving new Tables");
			//yokelGameService.saveObject(new YokelTable());
			//yokelGameService.saveObject(new YokelTable());
			//yokelGameService.saveObject(new YokelTable());
			//System.out.println("Creating Rooms");
			//System.out.println(yokelGameService.getAllRooms());

			//yokelGameService.saveObject(new YokelRoom("Empire State Building", YokelLounge.BEGINNER_GROUP));
			//yokelGameService.saveObject(new YokelRoom("Leaning Tower of Pisa", YokelLounge.SOCIAL_GROUP));
			//yokelGameService.saveObject(new YokelRoom("Eiffel", YokelLounge.SOCIAL_GROUP));
		};

	}

	public static void main(String[] args) {
		SpringApplication.run(YokelApplication.class, args);
	}
}