package org.grant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootConfiguration
@ComponentScan
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class);
    }
}
