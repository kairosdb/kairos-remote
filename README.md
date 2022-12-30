# Kairos Remote

This plugin used to ship as part of Kairos and was separated out for ease of maintenance.

The remote plugin is for forwarding metric data to a remote Kairos instance.
Metric data is gathered locally on the filesystem where it is compressed and uploaded to the 
remote Kairos on specified intervals.  (see kairos-remote.properties for options)

## Remote Listener
The remote plugin comes with a data points listener class and in order to laod 
it you load the `ListenerModule` in your kairos configuration:

```properties
kairosdb.datastore.remote.service.remote=org.kairosdb.plugin.remote.ListenerModule
```

The `ListenerModule` adds a listener to the data point events coming into kairos and 
forwards the events on to a remote Kairos instance.  Effectively letting you fork the data.

For a pure remote Kairos instance you can comment out the datastore modules and just
use the `ListenerModule`, effectively making the Kairos instance a write only node
that forwards data on to another Kairos instance.  This is useful so clients
can report metrics locally and the metrics are compressed and forwarded ot a 
remote Kairos instance.


## Metrics

Sample metrics4j.conf file to add to your kairosdb metrics4j.conf file
```hocon
metrics4j: {
  sources: {
    #Kairos Remote
    org.kairosdb.plugin.remote.RemoteStats: {
      _collector: ["timeStats", "counter"]
      _formatter: "remoteFormatter"
    }
  }
  
  collectors: {
    counter: {
      _class: "org.kairosdb.metrics4j.collectors.impl.LongCounter"
      reset: true
      report-zero: false
    }

    timeStats: {
      _class: "org.kairosdb.metrics4j.collectors.impl.SimpleTimerMetric"
      report-unit: "MILLIS"
    }
  }
  
  formatters: {
    remoteFormatter: {
      _class: "org.kairosdb.metrics4j.formatters.MethodToSnakeCase"
      template: "kairosdb.remote.%{metricName}.%{field}"
    }
  }
}
```