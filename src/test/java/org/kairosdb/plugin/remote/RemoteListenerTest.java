package org.kairosdb.plugin.remote;

import com.google.common.collect.ImmutableSortedMap;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kairosdb.core.datapoints.LongDataPoint;
import org.kairosdb.core.exception.DatastoreException;
import org.kairosdb.eventbus.FilterEventBus;
import org.kairosdb.eventbus.Publisher;
import org.kairosdb.events.DataPointEvent;
import org.kairosdb.metrics4j.MetricSourceManager;
import org.kairosdb.metrics4j.collectors.LongCollector;
import org.mockito.ArgumentMatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class RemoteListenerTest
{
	private RemoteHost mockRemoteHost;
	private DiskUtils mockDiskUtils;
	private File tempDir;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws IOException
	{
		tempDir = Files.createTempDirectory("RemoteDatastoreTestTempDir").toFile();
		mockRemoteHost = mock(RemoteHost.class);
		mockDiskUtils = mock(DiskUtils.class);
	}

	@After
	public void tearDown() throws IOException
	{
		FileUtils.deleteDirectory(tempDir);
	}

	@SuppressWarnings("ConstantConditions")
	@Test
	public void test_cleanup() throws IOException, DatastoreException
	{
		LongCollector deletedSize = mock(LongCollector.class);
		MetricSourceManager.setCollectorForSource(deletedSize, RemoteStats.class).deletedZipFileSize();

		when(mockDiskUtils.percentAvailable(any())).thenReturn(4L).thenReturn(4L).thenReturn(20L);
		RemoteListener remoteListener = new RemoteListener(tempDir.getAbsolutePath(), "95",
				2000, mockRemoteHost, mockDiskUtils);

		// Create zip files
		createZipFile("zipFile1.gz");
		createZipFile("zipFile2.gz");
		createZipFile("zipFile3.gz");
		createZipFile("zipFile4.gz");

		remoteListener.sendData();
		// Note that sendData() will create an additional zip file (so 5 zips)
		// Double Note, it doesn't any more if there is not data so only 4

		// assert that temp dir only contains x number of zip files
		File[] files = tempDir.listFiles((dir, name) -> name.endsWith(".gz"));
		assertThat(files.length).isEqualTo(4);
		//verify(deletedSize, times(2)).put(14L);
	}

	@Test
	public void test_sendData() throws IOException, DatastoreException
	{
		when(mockDiskUtils.percentAvailable(any())).thenReturn(20L);
		RemoteListener remoteListener = new RemoteListener(tempDir.getAbsolutePath(), "95",
				2000, mockRemoteHost, mockDiskUtils);

		remoteListener.putDataPoint(new DataPointEvent("Test", ImmutableSortedMap.of(), new LongDataPoint(0L, 0L)));
		remoteListener.flushMap();
		remoteListener.sendData();

		verify(mockRemoteHost, times(1)).sendZipFile(any());
	}

	private DataPointEvent createDataPoint(String metricName, long value)
	{
		ImmutableSortedMap<String, String> tags = ImmutableSortedMap.of("host", "localhost");
		return new DataPointEvent(metricName, tags, new LongDataPoint(System.currentTimeMillis(), value));
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private void createZipFile(String name) throws IOException
	{
		File file = new File(tempDir, name);
		file.createNewFile();
		Files.write(file.toPath(), "This is a test".getBytes());
	}

	private class DataPointEventMatcher implements ArgumentMatcher<DataPointEvent>
	{
		private DataPointEvent event;
		private String errorMessage;

		DataPointEventMatcher(DataPointEvent event)
		{
			this.event = event;
		}

		@Override
		public boolean matches(DataPointEvent dataPointEvent)
		{
			if (!event.getMetricName().equals(dataPointEvent.getMetricName()))
			{
				errorMessage = "Metric names don't match: " + event.getMetricName() + " != " + dataPointEvent.getMetricName();
				return false;
			}
			if (!event.getTags().equals(dataPointEvent.getTags()))
			{
				errorMessage = "Tags don't match: " + event.getTags() + " != " + dataPointEvent.getTags();
				return false;
			}
			if (event.getDataPoint().getDoubleValue() != dataPointEvent.getDataPoint().getDoubleValue())
			{
				errorMessage = "Data points don't match: " + event.getDataPoint().getDoubleValue() + " != " + dataPointEvent.getDataPoint().getDoubleValue();
				return false;
			}
			return true;
		}

		@Override
		public String toString()
		{
			if (errorMessage != null)
			{
				return errorMessage;
			}
			return "";
		}
	}
}