package PropertyManager.PM.dao;

import PropertyManager.PM.Database.SqlConnection;
import PropertyManager.PM.model.Person;
//import org.flywaydb.core.internal.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

    @Autowired
    public PersonDataAccessService() {

    }


    @Override
    public int insertPerson(UUID id, Person person) {
        return 0;
    }

    @Override
    public List<Person> selectAllPeople()  {
        final String sql = "SELECT id, name FROM person";
        List<Person> people = new ArrayList<>();
        ResultSet rs = SqlConnection.executeQuery(sql);
        try {
            while(rs.next()){
                UUID id = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                people.add(new Person(id, name));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return Optional.empty();
    }

    @Override
    public int deletePersonById(UUID id) {
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return 0;
    }
}
