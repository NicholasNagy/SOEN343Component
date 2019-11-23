package PropertyManager.PM.dao;

import PropertyManager.PM.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {
    //insert in DB
    int insertPerson(UUID id, Person person);

    //allows us to generate randomly a UUID for the person instead of passing one like above
    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person>  selectAllPeople();

    Optional<Person> selectPersonById(UUID id);

    int deletePersonById(UUID id);

    int updatePersonById(UUID id, Person person);
}
