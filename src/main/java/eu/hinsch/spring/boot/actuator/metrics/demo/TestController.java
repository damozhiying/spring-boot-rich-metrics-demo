package eu.hinsch.spring.boot.actuator.metrics.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lukas.hinsch on 06.05.2015.
 */
@Controller
public class TestController {

    private final CounterService counterService;
    private final GaugeService gaugeService;

    @Autowired
    public TestController(final CounterService counterService, final GaugeService gaugeService) {
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    @RequestMapping("/count")
    @ResponseBody
    public String count() {
        counterService.increment("test-counter");
        return "count";
    }

    @RequestMapping("/status")
    public ResponseEntity<Integer> status(@RequestParam int status) {
        return new ResponseEntity<>(status, HttpStatus.valueOf(status));
    }

    @RequestMapping("/gauge")
    @ResponseBody
    public String guage(@RequestParam int value) {
        gaugeService.submit("test-gauge", value);
        return "gauge";
    }
}
