package org.greatlogic.gxtexamples.shared;

import org.greatlogic.gxtexamples.client.glgwt.IGLColumn;
import org.greatlogic.gxtexamples.client.glgwt.IGLTable;

public interface IDBEnums {
//--------------------------------------------------------------------------------------------------
enum EGXTExamplesTable implements IGLTable {
Person
}
//--------------------------------------------------------------------------------------------------
enum Person implements IGLColumn {
CurrentOrgId,
DisplayName,
EmailAddress,
FirstName,
LastName,
LoginName,
PersonID,
Version
}
//--------------------------------------------------------------------------------------------------
}