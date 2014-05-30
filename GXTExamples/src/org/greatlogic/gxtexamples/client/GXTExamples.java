package org.greatlogic.gxtexamples.client;

import org.greatlogic.gxtexamples.client.glgwt.GLDBException;
import org.greatlogic.gxtexamples.client.glgwt.GLSQL;
import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMap;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMapGridWidget;
import org.greatlogic.gxtexamples.client.glgwt.IGLEnums.EGLDBOp;
import org.greatlogic.gxtexamples.client.widget.GridWidgetManager;
import org.greatlogic.gxtexamples.client.widget.MainLayoutWidget;
import org.greatlogic.gxtexamples.shared.IDBEnums.EGXTExamplesTable;
import org.greatlogic.gxtexamples.shared.IDBEnums.Pet;
import org.greatlogic.gxtexamples.shared.IDBEnums.PetType;
import org.greatlogic.gxtexamples.shared.IRemoteService;
import org.greatlogic.gxtexamples.shared.IRemoteServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public class GXTExamples implements EntryPoint {
//--------------------------------------------------------------------------------------------------
private IRemoteServiceAsync _remoteService;
//--------------------------------------------------------------------------------------------------
private void loadPersons() {
  try {
    final GLSQL personSQL = GLSQL.select();
    personSQL.from(EGXTExamplesTable.Pet);
    personSQL.whereAnd(Pet.PetId, EGLDBOp.LessThan, 10);
    personSQL.orderBy(EGXTExamplesTable.Pet, Pet.PetName, true);
    select(personSQL);
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
private void login() {
  _remoteService.login("name", "password", new AsyncCallback<Integer>() {
    @Override
    public void onFailure(final Throwable t) {

    }
    @Override
    public void onSuccess(final Integer userId) {
      final MessageBox messageBox = new MessageBox("login result:" + userId);
      messageBox.show();
    }
  });
}
//--------------------------------------------------------------------------------------------------
@Override
public void onModuleLoad() {
  _remoteService = GWT.create(IRemoteService.class);
  final MainLayoutWidget mainLayoutWidget = new MainLayoutWidget();
  final boolean loadTestData = true;
  final GLValueMapGridWidget gridWidget = GridWidgetManager.getPersonGrid("Main");
  if (loadTestData) {
    final ListStore<GLValueMap> petTypeListStore = GLUtil.createListStore(PetType.PetTypeId);
    TestData.loadPetTypeTestData(petTypeListStore);
    TestData.loadPetTestData(gridWidget.getListStore());
  }
  mainLayoutWidget.getCenterPanel().setWidget(gridWidget);
  RootLayoutPanel.get().add(mainLayoutWidget);
  loadPersons();
}
//--------------------------------------------------------------------------------------------------
private void select(final GLSQL sql) {
  _remoteService.select(sql.toXMLSB().toString(), new AsyncCallback<String>() {
    @Override
    public void onFailure(final Throwable t) {

    }
    @Override
    public void onSuccess(final String responseXML) {
      final MessageBox messageBox = new MessageBox("select result:" + responseXML);
      messageBox.show();
    }
  });
}
//--------------------------------------------------------------------------------------------------
}