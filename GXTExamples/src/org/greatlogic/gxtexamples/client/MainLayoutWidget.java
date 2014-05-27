package org.greatlogic.gxtexamples.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class MainLayoutWidget extends Composite {
//--------------------------------------------------------------------------------------------------
@UiField
ContentPanel centerPanel;
//==================================================================================================
interface MainLayoutWidgetUiBinder extends UiBinder<Widget, MainLayoutWidget> { //
}
//==================================================================================================
public MainLayoutWidget() {
  super();
  final MainLayoutWidgetUiBinder uiBinder = GWT.create(MainLayoutWidgetUiBinder.class);
  initWidget(uiBinder.createAndBindUi(this));
}
//--------------------------------------------------------------------------------------------------
public ContentPanel getCenterPanel() {
  return centerPanel;
}
//--------------------------------------------------------------------------------------------------
}