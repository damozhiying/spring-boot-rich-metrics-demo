# spring-boot-rich-metrics-demo

Demonstrate how to use the (rather hidden) InMemoryRichGaugeRepository repository from the spring boot actuator project 
and restore the full functionality of CounterService. 

When enabling the InMemoryRichGaugeRepository all measurements exposed via GaugeService now expose not only 
a single value (the last measurement) but rather a nice histogram (min/max/avg/count/last) of the values.

Unfortunately, as the new implementation also takes over the counting of measurements, 
at the same time it effectively disables the CounterService (implements the increment method as no-op).
So whenever CounterService is used to track something without also logging a value via GaueService, these values are lost.
This also effects the MetricsFilter which tracks all http requests to the app, by default it groups the counted requests by 
http status, a feature that is also lost with InMemoryRichGaugeRepository.

In this example, CounterService is re-enabled and pointed to the default InMemoryMetricRepository to track the counts,
and an instance of MetricReaderPublicMetrics is created that adds those values to the metrics endpoint.
