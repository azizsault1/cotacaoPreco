package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "cotacaoEscolar" })
@SpringBootApplication()
public class Application {

   public static void main(final String[] args) {
      SpringApplication.run(Application.class, args);
   }

}
