package org.kairosdb.plugin.remote;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

/**
 Created by bhawkins on 8/29/16.
 */
public class ListenerModule extends AbstractModule
{
	@Override
	protected void configure()
	{
		bind(RemoteListener.class).in(Scopes.SINGLETON);
		bind(RemoteSendJob.class).in(Scopes.SINGLETON);
		bind(RemoteDatastoreHealthCheck.class).in(Scopes.SINGLETON);
		bind(DiskUtils.class).to(DiskUtilsImpl.class);
		bind(RemoteHost.class).to(RemoteHostImpl.class);
	}
}
