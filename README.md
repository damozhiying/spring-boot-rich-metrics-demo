# spring-boot-rich-metrics-demo

Demonstrate how to use the (rather hidden) [InMemoryRichGaugeRepository](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/metrics/rich/InMemoryRichGaugeRepository.java) repository from the spring boot actuator project 
and restore the full functionality of CounterService. 

When enabling the InMemoryRichGaugeRepository all measurements exposed via [GaugeService](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/metrics/GaugeService.java) now expose not only 
a single value (the last measurement) but rather a nice histogram (min/max/avg/count/last) of the values.

Unfortunately, as the new implementation also takes over the counting of measurements, 
at the same time it effectively disables the [CounterService](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/metrics/CounterService.java) (implements the increment method as no-op).
So whenever CounterService is used to track something without also logging a value via GaueService, these values are lost.
This also effects the MetricsFilter which tracks all http requests to the app, by default it groups the counted requests by 
http status, a feature that is also lost with InMemoryRichGaugeRepository.

In this example, CounterService is re-enabled and pointed to the default [InMemoryMetricRepository](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/metrics/repository/InMemoryMetricRepository.java) to track the counts,
and an instance of [MetricReaderPublicMetrics](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/endpoint/MetricReaderPublicMetrics.java) is created that adds those values to the metrics endpoint.
