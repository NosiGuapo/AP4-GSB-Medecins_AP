package io.github.nosiguapo.ap4medecins;

import io.github.nosiguapo.ap4medecins.entities.AppUser;
import io.github.nosiguapo.ap4medecins.services.AppUserService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Ap4MedecinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ap4MedecinsApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(AppUserService appUserService) {
//        return args -> {
//            appUserService.saveUser(
//                    new AppUser(null, "John", "Doe", "jdoe", "aaa", true, new ArrayList<>())
//            );
//            appUserService.saveUser(
//                    new AppUser(null, "Blaise", "Bailey", "bfinnegan", "12345", false, new ArrayList<>())
//            );
//        };
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

