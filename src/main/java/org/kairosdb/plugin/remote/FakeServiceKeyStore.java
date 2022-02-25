package org.kairosdb.plugin.remote;

import org.kairosdb.core.datastore.ServiceKeyStore;
import org.kairosdb.core.datastore.ServiceKeyValue;
import org.kairosdb.core.exception.DatastoreException;

import java.util.Collections;
import java.util.Date;

public class FakeServiceKeyStore implements ServiceKeyStore
{
	@Override
	public void setValue(String service, String serviceKey, String key, String value) throws DatastoreException
	{

	}

	@Override
	public ServiceKeyValue getValue(String service, String serviceKey, String key) throws DatastoreException
	{
		return null;
	}

	@Override
	public Iterable<String> listServiceKeys(String service) throws DatastoreException
	{
		return Collections.emptyList();
	}

	@Override
	public Iterable<String> listKeys(String service, String serviceKey) throws DatastoreException
	{
		return Collections.emptyList();
	}

	@Override
	public Iterable<String> listKeys(String service, String serviceKey, String keyStartsWith) throws DatastoreException
	{
		return Collections.emptyList();
	}

	@Override
	public void deleteKey(String service, String serviceKey, String key) throws DatastoreException
	{

	}

	@Override
	public Date getServiceKeyLastModifiedTime(String s, String s1) throws DatastoreException
	{
		return null;
	}
}
