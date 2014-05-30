package org.greatlogic.gxtexamples.client.glgwt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.sencha.gxt.core.client.ValueProvider;

abstract class GLValueProviderClasses {
//==================================================================================================
public static class GLBigDecimalValueProvider implements ValueProvider<GLValueMap, BigDecimal> {
private final IGLColumn _column;
private final int       _numberOfDecimalPlaces;
public GLBigDecimalValueProvider(final IGLColumn column, final int numberOfDecimalPlaces) {
  _column = column;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
}
@Override
public BigDecimal getValue(final GLValueMap valueMap) {
  return valueMap.asDec(_column);
}
@Override
public void setValue(final GLValueMap valueMap, final BigDecimal value) {
  valueMap.put(_column, value.setScale(_numberOfDecimalPlaces, RoundingMode.HALF_UP));
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLBooleanValueProvider implements ValueProvider<GLValueMap, Boolean> {
private final IGLColumn _column;
public GLBooleanValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public Boolean getValue(final GLValueMap valueMap) {
  return valueMap.asBoolean(_column);
}
@Override
public void setValue(final GLValueMap valueMap, final Boolean value) {
  valueMap.put(_column, value);
}
@Override
public String getPath() {
  return _column.toString();
}
}
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
  return _column.toString();
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
  return _column.toString();
}
}
//==================================================================================================
}