package com.mgreis.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The application's executable Class.
 *
 * @author Mario Pereira
 * @since 1.0.0-SNAPSHOT
 */
@SpringBootApplication
@ComponentScan(value = "com.mgreis.delivery")
public class Application {

    /**
     * The main class.
     *
     * @param args This class can receive {@link String} containing arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
