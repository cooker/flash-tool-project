package org.grant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * grant
 * 15/8/2019 5:48 PM
 * 描述：
 */
@Component
public class Run implements CommandLineRunner {

    @Autowired Czk czk;

    @Override
    public void run(String... args) throws Exception {
        List<Czk> x = new ArrayList<>();
        x.add(new Czk());
        x.add(new Czz());
        czk.say();
    }

    public void sax(){

    }
}
