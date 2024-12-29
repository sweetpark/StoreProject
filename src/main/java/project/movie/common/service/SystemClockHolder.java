package project.movie.common.service;

import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public Long millis() {
        return Clock.systemUTC().millis();
    }
}
