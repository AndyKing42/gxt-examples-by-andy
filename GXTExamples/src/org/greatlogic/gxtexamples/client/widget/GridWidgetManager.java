package org.greatlogic.gxtexamples.client.widget;

import java.util.TreeMap;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMapGridWidget;

public class GridWidgetManager {
//--------------------------------------------------------------------------------------------------
private static TreeMap<String, GLValueMapGridWidget> _gridWidgetMap; // grid name -> GLValueMapGridWidget

//--------------------------------------------------------------------------------------------------
static {
  _gridWidgetMap = new TreeMap<String, GLValueMapGridWidget>();
}
//--------------------------------------------------------------------------------------------------
public static PersonGridWidget getPersonGrid(final String gridName) {
  PersonGridWidget result = (PersonGridWidget)_gridWidgetMap.get(gridName);
  if (result == null) {
    result = new PersonGridWidget();
    _gridWidgetMap.put(gridName, result);
  }
  return result;
}
//--------------------------------------------------------------------------------------------------
}