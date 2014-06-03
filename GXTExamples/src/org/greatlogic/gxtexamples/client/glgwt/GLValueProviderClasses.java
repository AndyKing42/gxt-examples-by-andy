package org.greatlogic.gxtexamples.client.glgwt;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import com.sencha.gxt.core.client.ValueProvider;

abstract class GLValueProviderClasses {
//==================================================================================================
public static class GLBigDecimalValueProvider implements ValueProvider<GLRecord, BigDecimal> {
private final IGLColumn _column;
private final int       _numberOfDecimalPlaces;
public GLBigDecimalValueProvider(final IGLColumn column, final int numberOfDecimalPlaces) {
  _column = column;
  _numberOfDecimalPlaces = numberOfDecimalPlaces;
}
@Override
public BigDecimal getValue(final GLRecord record) {
  try {
    return record.asDec(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return BigDecimal.ZERO;
  }
}
@Override
public void setValue(final GLRecord record, final BigDecimal value) {
  try {
    record.put(_column, value.setScale(_numberOfDecimalPlaces, RoundingMode.HALF_UP));
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLBooleanValueProvider implements ValueProvider<GLRecord, Boolean> {
private final IGLColumn _column;
public GLBooleanValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public Boolean getValue(final GLRecord record) {
  try {
    return record.asBoolean(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return false;
  }
}
@Override
public void setValue(final GLRecord record, final Boolean value) {
  try {
    record.put(_column, value);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLIntegerValueProvider implements ValueProvider<GLRecord, Integer> {
private final IGLColumn _column;
public GLIntegerValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public Integer getValue(final GLRecord record) {
  try {
    return record.asInt(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return 0;
  }
}
@Override
public void setValue(final GLRecord record, final Integer value) {
  try {
    record.put(_column, value);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
public static class GLStringValueProvider implements ValueProvider<GLRecord, String> {
private final IGLColumn _column;
public GLStringValueProvider(final IGLColumn column) {
  _column = column;
}
@Override
public String getValue(final GLRecord record) {
  try {
    return record.asString(_column);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    return "";
  }
}
@Override
public void setValue(final GLRecord record, final String value) {
  try {
    record.put(_column, value);
  }
  catch (final GLInvalidFieldOrColumnException ifoce) {
    //
  }
}
@Override
public String getPath() {
  return _column.toString();
}
}
//==================================================================================================
}