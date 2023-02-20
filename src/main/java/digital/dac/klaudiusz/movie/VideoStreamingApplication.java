package digital.dac.klaudiusz.movie;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
@Slf4j
@AllArgsConstructor
public class VideoStreamingApplication {

    private final Storage storage;

    public static void main(String[] args) {
        SpringApplication.run(VideoStreamingApplication.class, args);
    }

    @Scheduled(fixedDelay = 10,timeUnit = TimeUnit.SECONDS)
    public void printMemoryInformation(){
        log.info(Information.getMemoryInformation(storage.getTotalNoHeapMemoryUsage()));
    }
}
