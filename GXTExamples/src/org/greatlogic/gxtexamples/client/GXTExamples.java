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
import org.greatlogic.gxtexamples.client.glgwt.GLGridWidget;
import org.greatlogic.gxtexamples.client.glgwt.GLListStore;
import org.greatlogic.gxtexamples.client.glgwt.GLSQL;
import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.client.glgwt.IGLSQLSelectCallback;
import org.greatlogic.gxtexamples.client.widget.GridWidgetManager;
import org.greatlogic.gxtexamples.client.widget.MainLayoutWidget;
import org.greatlogic.gxtexamples.client.widget.PetGridWidget;
import org.greatlogic.gxtexamples.shared.IDBEnums.EGXTExamplesTable;
import org.greatlogic.gxtexamples.shared.IDBEnums.Pet;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public class GXTExamples implements EntryPoint {
//--------------------------------------------------------------------------------------------------
private void loadPets() {
  try {
    final PetGridWidget gridWidget = GridWidgetManager.getPetGrid("Main");
    final GLSQL petSQL = GLSQL.select();
    petSQL.from(EGXTExamplesTable.Pet);
    petSQL.orderBy(EGXTExamplesTable.Pet, Pet.PetName, true);
    petSQL.execute(gridWidget.getListStore(), new IGLSQLSelectCallback() {
      @Override
      public void onFailure(final Throwable t) {

      }
      @Override
      public void onSuccess(final GLListStore listStore) {

      }
    });
  }
  catch (final GLDBException dbe) {

  }
}
//--------------------------------------------------------------------------------------------------
private void login() {
  GLUtil.getRemoteService().login("name", "password", new AsyncCallback<Integer>() {
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
  GLUtil.initialize();
  final MainLayoutWidget mainLayoutWidget = new MainLayoutWidget();
  final boolean loadTestData = false;
  final GLGridWidget gridWidget = GridWidgetManager.getPetGrid("Main");
  if (loadTestData) {
    final GLListStore petTypeListStore = new GLListStore();
    TestData.loadPetTypeTestData(petTypeListStore);
    TestData.loadPetTestData(gridWidget.getListStore());
  }
  mainLayoutWidget.getCenterPanel().setWidget(gridWidget);
  RootPanel.get().add(mainLayoutWidget);
  loadPets();
}
//--------------------------------------------------------------------------------------------------
}