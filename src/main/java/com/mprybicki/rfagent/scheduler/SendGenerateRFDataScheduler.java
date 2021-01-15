package com.mprybicki.rfagent.scheduler;

import com.mprybicki.rfagent.service.SendGeneratedRFDataService;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class SendGenerateRFDataScheduler {

    private SendGeneratedRFDataService sendGeneratedRFDataService;

    @Scheduled(fixedRateString = "${scheduler.rf.data.generator.fixed.rate}")
    public void sendGeneratedRFData() {
        sendGeneratedRFDataService.sendGeneratedRFData();
    }
}

