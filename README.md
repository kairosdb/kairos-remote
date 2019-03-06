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
use the `ListenerModule`, effectively making the Kairos instance a write only node.