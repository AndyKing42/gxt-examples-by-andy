package org.greatlogic.gxtexamples.client.glgwt;


public interface IGLSQLSelectCallback {
//--------------------------------------------------------------------------------------------------
public void onFailure(final Throwable t);
public void onSuccess(final GLListStore listStore);
//--------------------------------------------------------------------------------------------------
}