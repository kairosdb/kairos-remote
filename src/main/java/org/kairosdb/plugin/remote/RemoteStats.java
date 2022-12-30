package org.kairosdb.plugin.remote;

import org.kairosdb.metrics4j.annotation.Key;
import org.kairosdb.metrics4j.collectors.DurationCollector;
import org.kairosdb.metrics4j.collectors.LongCollector;

public interface RemoteStats
{
	LongCollector error(@Key("cause")String cause);
	LongCollector fileSize();
	LongCollector zipFileSize();
	LongCollector writeSize();
	DurationCollector timeToSend();
	LongCollector deletedZipFileSize();

}
