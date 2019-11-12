package PropertyManager.PM.dao;


import PropertyManager.PM.model.Person;
import PropertyManager.PM.model.Property;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("property")
public class PropertyDao {

    public int insertProperty(UUID id, Property property) {

        return 0;
    }

    public List<Person> selectAllProperties() {
        return null;
    }

    public Optional<Person> selectPropertyById(UUID id) {
        return null;
    }

    public int deletePersonById(UUID id) {
        return 1;
    }

    public int updatePersonById(UUID id, Person update) {
        return 0;
    }


}