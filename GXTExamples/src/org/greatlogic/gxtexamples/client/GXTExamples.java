package org.greatlogic.gxtexamples.client;
/*
 * Copyright 2006-2014 Andy King (GreatLogic.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public class GXTExamples implements EntryPoint {
//--------------------------------------------------------------------------------------------------
private IRemoteServiceAsync _remoteService;
//--------------------------------------------------------------------------------------------------
private void loadPets() {
  try {
    final GLSQL petSQL = GLSQL.select();
    petSQL.from(EGXTExamplesTable.Pet);
    petSQL.whereAnd(Pet.PetId, EGLDBOp.LessThan, 10);
    petSQL.orderBy(EGXTExamplesTable.Pet, Pet.PetName, true);
    select(petSQL);
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
  final GLValueMapGridWidget gridWidget = GridWidgetManager.getPetGrid("Main");
  if (loadTestData) {
    final ListStore<GLValueMap> petTypeListStore = GLUtil.createListStore(PetType.PetTypeId);
    TestData.loadPetTypeTestData(petTypeListStore);
    TestData.loadPetTestData(gridWidget.getListStore());
  }
  mainLayoutWidget.getCenterPanel().setWidget(gridWidget);
  RootPanel.get().add(mainLayoutWidget);
  loadPets();
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