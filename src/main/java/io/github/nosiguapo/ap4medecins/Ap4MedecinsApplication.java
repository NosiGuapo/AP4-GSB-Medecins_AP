package io.github.nosiguapo.ap4medecins;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.services.AppUserService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class Ap4MedecinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ap4MedecinsApplication.class, args);
    }

/*    @Bean
    CommandLineRunner run(AppUserService appUserService){
        return args -> {
            appUserService.saveUser(new AppUser(null, "John", "Doe", "jdoe", "JIAJFOKOFJkfoejf", true, new ArrayList<>()));
        };
    }*/
}

