package org.greatlogic.gxtexamples.shared.dto;

public class Person {
//--------------------------------------------------------------------------------------------------
private final String _archiveDate;
private final int    _currentOrgId;
private String       _dateOfBirth;
private String       _displayName;
private String       _emailAddress;
private String       _firstName;
private String       _lastName;
private String       _loginName;
private String       _passwordHash;
private Integer      _personId;
private final String _phoneNumberHome;
private final String _phoneNumberMobile;
private final String _phoneNumberOffice;
private String       _version;
//--------------------------------------------------------------------------------------------------
public Person(final String archiveDate, final Integer currentOrgId, final String dateOfBirth,
              final String displayName, final String emailAddress, final String firstName,
              final String lastName, final String loginName, final String passwordHash,
              final Integer personId, final String phoneNumberHome, final String phoneNumberMobile,
              final String phoneNumberOffice, final String version) {
  _archiveDate = archiveDate;
  _currentOrgId = currentOrgId;
  _dateOfBirth = dateOfBirth;
  _displayName = displayName;
  _emailAddress = emailAddress;
  _firstName = firstName;
  _lastName = lastName;
  _loginName = loginName;
  _passwordHash = passwordHash;
  _personId = personId;
  _phoneNumberHome = phoneNumberHome;
  _phoneNumberMobile = phoneNumberMobile;
  _phoneNumberOffice = phoneNumberOffice;
  _version = version;
}
//--------------------------------------------------------------------------------------------------
public String getDateOfBirth() {
  return _dateOfBirth == null ? "" : _dateOfBirth;
}
//--------------------------------------------------------------------------------------------------
public void setDateOfBirth(final String dateOfBirth) {
  _dateOfBirth = dateOfBirth;
}
//--------------------------------------------------------------------------------------------------
public String getDisplayName() {
  return _displayName == null ? "" : _displayName;
}
//--------------------------------------------------------------------------------------------------
public void setDisplayName(final String displayName) {
  _displayName = displayName;
}
//--------------------------------------------------------------------------------------------------
public String getEmailAddress() {
  return _emailAddress == null ? "" : _emailAddress;
}
//--------------------------------------------------------------------------------------------------
public void setEmailAddress(final String emailAddress) {
  _emailAddress = emailAddress;
}
//--------------------------------------------------------------------------------------------------
public String getFirstName() {
  return _firstName == null ? "" : _firstName;
}
//--------------------------------------------------------------------------------------------------
public void setFirstName(final String firstName) {
  _firstName = firstName;
}
//--------------------------------------------------------------------------------------------------
public String getLastName() {
  return _lastName == null ? "" : _lastName;
}
//--------------------------------------------------------------------------------------------------
public void setLastName(final String lastName) {
  _lastName = lastName;
}
//--------------------------------------------------------------------------------------------------
public String getLoginName() {
  return _loginName == null ? "" : _loginName;
}
//--------------------------------------------------------------------------------------------------
public void setLoginName(final String loginName) {
  _loginName = loginName;
}
//--------------------------------------------------------------------------------------------------
public String getPasswordHash() {
  return _passwordHash == null ? "" : _passwordHash;
}
//--------------------------------------------------------------------------------------------------
public void setPasswordHash(final String passwordHash) {
  _passwordHash = passwordHash;
}
//--------------------------------------------------------------------------------------------------
public Integer getPersonId() {
  return _personId == null ? 0 : _personId;
}
//--------------------------------------------------------------------------------------------------
public void setPersonId(final Integer personId) {
  _personId = personId;
}
//--------------------------------------------------------------------------------------------------
public String getVersion() {
  return _version == null ? "" : _version;
}
//--------------------------------------------------------------------------------------------------
public void setVersion(final String version) {
  _version = version;
}
//--------------------------------------------------------------------------------------------------
}