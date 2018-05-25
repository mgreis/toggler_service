package com.mgreis.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The application class.
 * Since Cassandra is configured for by default for the docker environment, it will only work if you edit the /etc/hosts
 * and add the the running instance of Cassandra to it.
 * @author Mario Pereira
 * @since since 1.0.0
 */
@SpringBootApplication
@ComponentScan(value = "com.mgreis.delivery")
public class Application {

    /**
     * The main method.
     * @param args  a {@link String} array of arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
