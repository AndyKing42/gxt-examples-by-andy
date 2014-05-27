package org.greatlogic.gxtexamples.client;

import org.greatlogic.gxtexamples.shared.dto.Person;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface PersonPropertyAccess extends PropertyAccess<Person> {
//--------------------------------------------------------------------------------------------------
ValueProvider<Person, String> dateOfBirth();
ValueProvider<Person, String> displayName();
ValueProvider<Person, String> firstName();
ValueProvider<Person, String> lastName();
ValueProvider<Person, String> loginName();
ModelKeyProvider<Person> personId();
//--------------------------------------------------------------------------------------------------
}