package com.mprybicki.rfagent.scheduler;

import com.mprybicki.rfagent.service.SendGeneratedRFDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class SendGenerateRFDataScheduler {

    private SendGeneratedRFDataService sendGeneratedRFDataService;

    //TODO move ms value to properties
    @Scheduled(fixedRate = 15000)
    public void sendGeneratedRFData() {
        sendGeneratedRFDataService.sendGeneratedRFData();
    }
}

