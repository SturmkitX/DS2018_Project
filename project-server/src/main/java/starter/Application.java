package starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import utils.ChannelStatus;
import utils.MailSender;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "service", "utils", "config"})
@EntityScan(basePackages = {"entity"})
@EnableJpaRepositories(basePackages = {"repository"})
public class Application {
    public static void main(String[] args) {
        // initialize timers
        Timer purgeTimer = new Timer();
        Timer syncTimer = new Timer();
        Timer mailTimer = new Timer();

        purgeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ChannelStatus.purgeDisconnected();
            }
        }, 60000, 60000);

        syncTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ChannelStatus.synchronizePlaylists();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 5000, 5000);

        // mailTimer.schedule(new MailSender(), 60000, 60000);

        SpringApplication.run(Application.class, args);
    }
}