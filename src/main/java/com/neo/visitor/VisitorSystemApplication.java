package com.neo.visitor;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VisitorSystemApplication extends SpringBootServletInitializer implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(VisitorSystemApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VisitorSystemApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
    }
}
