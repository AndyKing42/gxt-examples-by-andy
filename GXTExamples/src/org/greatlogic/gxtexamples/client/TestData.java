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
import org.greatlogic.gxtexamples.client.glgwt.GLUtil;
import org.greatlogic.gxtexamples.client.glgwt.GLValueMap;
import org.greatlogic.gxtexamples.shared.IDBEnums.Pet;
import org.greatlogic.gxtexamples.shared.IDBEnums.PetType;
import com.sencha.gxt.data.shared.ListStore;

public class TestData {
//--------------------------------------------------------------------------------------------------
private static final String[] PetNamesAndSex = new String[] {"Angel,F", "Ashley,F", "Bandit,M",
    "Beau,M", "Bella,F", "Bo,M", "Boomer,M", "Bubba,M", "Buddy,M", "Buster,M", "Callie,F",
    "Calvin,M", "Cassie,F", "Champ,M", "Chelsea,F", "Chester,M", "Cleopatra,F", "Cody,M",
    "Cookie,F", "Cooper,M", "Dexter,M", "Diesel,M", "Dixie,F", "Duke,M", "Duncan,M", "Dusty,M",
    "Emily,F", "Emma,F", "Felix,M", "Female,F", "Fred,M", "Grace,F", "Guinness,M", "Haley,F",
    "Harry,M", "Hunter,M", "Invisible Pink Unicorn,U", "Isabella,F", "Jack,M", "Jasmine,F",
    "Joey,M", "Junior,M", "Kitty,F", "Kobe,M", "Leo,M", "Loki,U", "Lucy,F", "Maddie,F", "Mandy,F",
    "Marley,M", "Max,M", "Maximus,M", "Maxwell,M", "Maya,F", "Merlin,M", "Mia,F", "Mickey,M",
    "Mikey,M", "Millie,F", "Milo,U", "Misty,F", "Mocha,U", "Moose,M", "Morgan,U", "Murphy,M",
    "Nala,U", "Nikki,F", "Olivia,F", "Oreo,U", "Pebbles,F", "Penny,F", "Rex,M", "Roxie,F",
    "Rufus,M", "Sabrina,F", "Sadie,F", "Sam,U", "Samantha,F", "Sarah,F", "Sassy,U", "Scooter,M",
    "Sebastian,M", "Sheba,F", "Simba,M", "Snowball,U", "Socks,U", "Sophia,F", "Sophie,F",
    "Spencer,M", "Sunny,F", "Sylvester,M", "Taz,M", "Thomas,M", "Tinkerbell,F", "Toby,M",
    "Tommy,M", "Tucker,M", "Winston,M", "Ziggy,M", "Zoe,F", "Zoey,F"};
public static void loadPetTestData(final ListStore<GLValueMap> listStore) {
  listStore.clear();
  int nextPetId = 1;
  for (final String petNameAndSex : PetNamesAndSex) {
    final String[] nameAndSex = petNameAndSex.split(",");
    final String intakeDate = GLUtil.dateAddDays("20130501", GLUtil.getRandomInt(365));
    final int hour = 6 + GLUtil.getRandomInt(12);
    final int minute = GLUtil.getRandomInt(4) * 15;
    final String intakeTime = (hour < 10 ? "0" : "") + hour + (minute < 10 ? "0" : "") + minute;
    final String fosterDate = GLUtil.dateAddDays(intakeDate, 60);
    final GLValueMap valueMap = new GLValueMap(false);
    valueMap.put(Pet.AdoptionFee, GLUtil.getRandomInt(3000, 10000) / 100.0);
    valueMap.put(Pet.FosterDate, fosterDate);
    valueMap.put(Pet.IntakeDate, intakeDate + intakeTime);
    valueMap.put(Pet.PetId, nextPetId);
    valueMap.put(Pet.PetName, nameAndSex[0]);
    valueMap.put(Pet.PetTypeId, GLUtil.getRandomInt(PetTypes.length) + 1);
    valueMap.put(Pet.Sex, nameAndSex[1]);
    valueMap.put(Pet.TrainedFlag, GLUtil.getRandomInt(2) == 0 ? "Y" : "N");
    listStore.add(valueMap);
    ++nextPetId;
  }
}
//--------------------------------------------------------------------------------------------------
private static final String[] PetTypes = new String[] {"Cat,Cat", "Dog,Dog"};
public static void loadPetTypeTestData(final ListStore<GLValueMap> listStore) {
  listStore.clear();
  int nextPetTypeId = 1;
  for (final String petType : PetTypes) {
    final String[] petTypeFields = petType.split(",");
    final GLValueMap valueMap = new GLValueMap(false);
    valueMap.put(PetType.PetTypeCode, petTypeFields[0]);
    valueMap.put(PetType.PetTypeDesc, petTypeFields[1]);
    valueMap.put(PetType.PetTypeId, nextPetTypeId);
    listStore.add(valueMap);
    ++nextPetTypeId;
  }
}
//--------------------------------------------------------------------------------------------------
}