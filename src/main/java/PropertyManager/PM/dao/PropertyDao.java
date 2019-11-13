package PropertyManager.PM.dao;


import PropertyManager.PM.Database.SqlConnection;
import PropertyManager.PM.model.Person;
import PropertyManager.PM.model.Property;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("property")
public class PropertyDao {

    public int insertProperty(UUID id, Property property) {
        String sql = "INSERT INTO property (id, parkingspaces, petsallowed, bedrooms, " +
                "bathrooms, address) VALUES (\'" + id + "\', " + property.getParkingSpaces() +
                ", \'" +  property.getPetsAllowed() + "\', " + property.getBedrooms() +
                ", " + property.getBathrooms() + ", \'" + property.getAddress() + "\');";
        SqlConnection.executeQuery(sql,false, true);
        return 0;
    }

    private Property createPropertyWithRS(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        int parkingSpaces = rs.getInt("parkingspaces");
        Property.pets petsAllowed = Property.pets.valueOf(rs.getString("petsallowed"));
        int bedrooms = rs.getInt("bedrooms");
        int bathrooms = rs.getInt("bathrooms");
        String address = rs.getString("address");
        Property property = new Property(id, petsAllowed, parkingSpaces, bedrooms, bathrooms, address);
        return property;
    }

    public List<Property> selectAllProperties() {
        String sql = "SELECT * FROM property";
        List<Property> properties = new ArrayList<>();
        ResultSet rs = SqlConnection.executeQuery(sql);
        try {
            while(rs.next()){
                Property property = createPropertyWithRS(rs);
                properties.add(property);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public Property selectPropertyById(UUID id) {
        String sql = "SELECT * FROM property WHERE id='"+ id +"'";
        ResultSet rs = SqlConnection.executeQuery(sql);
        try {
            if(rs.next()){
                return createPropertyWithRS(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int deletePersonById(UUID id) {
        String sql = "DELETE FROM property WHERE id='"+id+"';";
        SqlConnection.executeQuery(sql, false, true);
        return 0;
    }

    public Property updatePersonById(UUID id, Property update) {
        if (selectPropertyById(id) == null){
            return null;
        }
        deletePersonById(id);
        insertProperty(id, update);
        return selectPropertyById(id);
    }

}