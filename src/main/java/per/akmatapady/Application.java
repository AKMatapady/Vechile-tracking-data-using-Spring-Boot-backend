package per.akmatapady;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(Application.class);
        springApp.run();
    }
}
