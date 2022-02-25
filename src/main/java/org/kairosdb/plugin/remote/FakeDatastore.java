package org.kairosdb.plugin.remote;

import org.kairosdb.core.datastore.Datastore;
import org.kairosdb.core.datastore.DatastoreMetricQuery;
import org.kairosdb.core.datastore.QueryCallback;
import org.kairosdb.core.datastore.TagSet;
import org.kairosdb.core.datastore.TagSetImpl;
import org.kairosdb.core.exception.DatastoreException;

import java.util.Collections;

public class FakeDatastore implements Datastore
{
	@Override
	public void close() throws InterruptedException, DatastoreException
	{

	}

	@Override
	public Iterable<String> getMetricNames(String prefix) throws DatastoreException
	{
		return Collections.emptyList();
	}

	@Override
	public Iterable<String> getTagNames() throws DatastoreException
	{
		return Collections.emptyList();
	}

	@Override
	public Iterable<String> getTagValues() throws DatastoreException
	{
		return Collections.emptyList();
	}

	@Override
	public void queryDatabase(DatastoreMetricQuery query, QueryCallback queryCallback) throws DatastoreException
	{

	}

	@Override
	public void deleteDataPoints(DatastoreMetricQuery deleteQuery) throws DatastoreException
	{

	}

	@Override
	public TagSet queryMetricTags(DatastoreMetricQuery query) throws DatastoreException
	{
		return new TagSetImpl();
	}

	@Override
	public void indexMetricTags(DatastoreMetricQuery datastoreMetricQuery) throws DatastoreException
	{

	}

	@Override
	public long getMinTimeValue()
	{
		return 0;
	}

	@Override
	public long getMaxTimeValue()
	{
		return 0;
	}
}
