package org.greatlogic.gxtexamples.client.glgwt;

public interface IGLEnums {
//--------------------------------------------------------------------------------------------------
public enum EGLDBConj {
And,
Or
}
//--------------------------------------------------------------------------------------------------
public enum EGLDBException {
InvalidSQLRequest,
ParameterIsNull
}
//--------------------------------------------------------------------------------------------------
public enum EGLDBOp {
Equals("="),
EqualsExp("="),
GreaterThan(">"),
GreaterThanExp(">"),
GreaterThanOrEqual(">="),
GreaterThanOrEqualExp(">="),
In("in"),
IsNotNull("is not null"),
IsNotNullOrBlank("is not null"),
IsNull("is null"),
IsNullOrBlank("is null"),
LessThan("<"),
LessThanExp("<"),
LessThanOrEqual("<="),
LessThanOrEqualExp("<="),
Like("like"),
NotEqual("<>"),
NotEqualExp("<>"),
NotLike("not like");
private final String _sql;
private EGLDBOp(final String sql) {
  _sql = sql;
}
public String getSQL() {
  return _sql;
}
}
//--------------------------------------------------------------------------------------------------
public enum EGLJoinType {
Inner,
Left,
Outer,
Right
}
//--------------------------------------------------------------------------------------------------
public enum EGLSQLType {
Delete,
Insert,
Select,
Update
}
//--------------------------------------------------------------------------------------------------
}