package com.ecui.ErnestsVilla;

import com.ecui.ErnestsVilla.service.timer.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class ErnestsVillaApplication {
    @Autowired
    private static TimerService timerService;

    public static void main(String[] args) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerService.clearExpiredUnpaidPurchases();
            }
        }, 0, 30 * 1000L);

        SpringApplication.run(ErnestsVillaApplication.class, args);
    }

}
