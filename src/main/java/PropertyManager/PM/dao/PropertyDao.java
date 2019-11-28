package PropertyManager.PM.dao;


import PropertyManager.PM.Database.SqlConnection;
import PropertyManager.PM.model.Address;
import PropertyManager.PM.model.Property;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("property")
public class PropertyDao {

    public static int insertProperty(UUID id, Property property) {
        String sql = "INSERT INTO property (id, parkingspaces, petsallowed, bedrooms, " +
                "bathrooms, address, propertyType, propertyID, price) VALUES (\'" +
                id + "\', " + property.getParkingSpaces() + ", \'" +  property.getPetsAllowed() +
                "\', " + property.getBedrooms() + ", " + property.getBathrooms() + ", \'" +
                property.getAddress().getId() + "\', \'" + property.getPropertyType() + "\', \'"
                + property.getPropertyID() + "\', " + property.getPrice() +");";

        SqlConnection.executeQuery(sql,false, true);

        insertAddress(property.getAddress());
        return 0;
    }

    private static int insertAddress(Address address){
        String sql = "INSERT INTO address (id, address, street, city, province, country, postalcode) " +
                "Values ('"+ address.getId() +"', " + address.getAddress() +", '" + address.getStreet()+ "', '" + address.getCity() +
                "', '" + address.getProvince()+"', '" + address.getCountry() + "', '" + address.getPostalCode() +"');";
        SqlConnection.executeQuery(sql, false, true);
        return 0;
    }

    private static Property createPropertyWithRS(ResultSet rs) throws SQLException {
        UUID id = UUID.fromString(rs.getString("id"));
        int parkingSpaces = rs.getInt("parkingspaces");
        boolean petsAllowed = rs.getBoolean("petsallowed");
        int bedrooms = rs.getInt("bedrooms");
        int bathrooms = rs.getInt("bathrooms");
        Property.propertyType propertyType = Property.propertyType.valueOf(rs.getString("propertyType"));
        UUID propertyID = UUID.fromString(rs.getString("propertyID"));
        float price = rs.getFloat("price");

        Address address = createAddressWithId(UUID.fromString(rs.getString("address")));
        Property property = new Property(id, petsAllowed, parkingSpaces, bedrooms, bathrooms,
                address, propertyType, propertyID, price);
        return property;
    }

    private static Address createAddressWithId(UUID id) {
        String sql = "SELECT * from address where id='"+id+"';";
        ResultSet rs = SqlConnection.executeQuery(sql, false);
        try {
            if (rs.next()){
                int address = rs.getInt("address");
                String street = rs.getString("street");
                String city = rs.getString("city");
                String province = rs.getString("province");
                String country  = rs.getString("country");
                String postalCode = rs.getString("postalcode");
                return new Address(id, address, street, city, province, country, postalCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    public static Property selectPropertyById(UUID id) {
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

    public static int deletePropertyById(UUID id) {
        Property property = selectPropertyById(id);
        if(property == null){
            return 1;
        }
        String sql = "DELETE FROM property WHERE id='"+id+"';";
        String addressSql = "DELETE FROM address where id='"+property.getAddress().getId()+"'";
        SqlConnection.executeQuery(sql, false, true);
        SqlConnection.executeQuery(addressSql, false, true);
        return 0;
    }

    public Property updatePropertyById(UUID id, Property update) {
        if (selectPropertyById(id) == null){
            return null;
        }
        deletePropertyById(id);
        insertProperty(id, update);
        return selectPropertyById(id);
    }

}