package org.greatlogic.gxtexamples.client.glgwt;

import com.sencha.gxt.data.shared.ListStore;

public interface IGLSQLSelectCallback {
//--------------------------------------------------------------------------------------------------
public void onFailure(final Throwable t);
public void onSuccess(final ListStore<GLValueMap> listStore);
//--------------------------------------------------------------------------------------------------
}