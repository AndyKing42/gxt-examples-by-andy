package org.greatlogic.gxtexamples.client.glgwt;

import com.sencha.gxt.core.client.ValueProvider;

abstract class GLValueProviderClasses {
//==================================================================================================
public static class GLIntegerValueProvider implements ValueProvider<GLValueMap, Integer> {
private final IGLColumn _column;
public GLIntegerValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public Integer getValue(final GLValueMap valueMap) {
  return valueMap.asInt(_column);
}
@Override
public void setValue(final GLValueMap valueMap, final Integer value) {
  valueMap.put(_column, value);
}
@Override
public String getPath() {
  return null;
}
}
//==================================================================================================
public static class GLStringValueProvider implements ValueProvider<GLValueMap, String> {
private final IGLColumn _column;
public GLStringValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public String getValue(final GLValueMap valueMap) {
  return valueMap.asString(_column);
}
@Override
public void setValue(final GLValueMap valueMap, final String value) {
  valueMap.put(_column, value);
}
@Override
public String getPath() {
  return null;
}
}
//==================================================================================================
}