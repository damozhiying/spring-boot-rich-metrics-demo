package eu.hinsch.spring.boot.actuator.metrics.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.MetricReaderPublicMetrics;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.repository.InMemoryMetricRepository;
import org.springframework.boot.actuate.metrics.rich.InMemoryRichGaugeRepository;
import org.springframework.boot.actuate.metrics.writer.DefaultCounterService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class SpringBootRichMetricsTestApplication {

    private final InMemoryMetricRepository counterMetricRepository = new InMemoryMetricRepository();

    @Bean
    @Primary
    public InMemoryRichGaugeRepository inMemoryRichGaugeRepository() {
        return new InMemoryRichGaugeRepository();
    }

    @Bean
    public CounterService counterService() {
        return new DefaultCounterService(counterMetricRepository);
    }

    // bean must not be named metricReaderPublicMetrics, one with that name already exists and the other one silently wins
    @Bean
    public MetricReaderPublicMetrics counterMetricReaderPublicMetrics() {
        return new MetricReaderPublicMetrics(counterMetricRepository);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRichMetricsTestApplication.class, args);
    }

}
