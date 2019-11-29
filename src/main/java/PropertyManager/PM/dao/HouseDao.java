package PropertyManager.PM.dao;

import PropertyManager.PM.Database.SqlConnection;

import PropertyManager.PM.model.House;
import PropertyManager.PM.model.Property;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository("house")
public class HouseDao {

    public static UUID insertHouse(UUID id, House house){

        String sql = "INSERT INTO house (id, transitFriendly, privateBackyardIncluded, poolIncluded, basementIncluded," +
                "pedestrianFriendly, yearBuilt) VALUES (\'" + id + "\', " +
                house.isTransitFriendly() + ", " + house.isPrivateBackyardIncluded() + ", " +
                house.isPoolIncluded() + ", " + house.isBasementIncluded() + ", " +
                house.isPedestrianFriendly() + ", " + house.getYearBuilt() +  ");";
        SqlConnection.executeQuery(sql, false, true);

        Property property = house.getProperty();
        property.setPropertyID(house.getId());

        PropertyDao.insertProperty(house.getProperty().getId(), property);
        return house.getProperty().getId();
    }

    public static House selectHouseById(UUID id) {

        //selects property by id
        Property property = PropertyDao.selectPropertyById(id);

        try {
            String sql = "SELECT * FROM house WHERE id=\'" + property.getPropertyID() + "\';";
            ResultSet rs = SqlConnection.executeQuery(sql);
            if(rs.next()){
                boolean transitFriendly = rs.getBoolean("transitFriendly");
                boolean privateBackyardIncluded = rs.getBoolean("privateBackyardIncluded");
                boolean poolIncluded = rs.getBoolean("poolIncluded");
                boolean basementIncluded = rs.getBoolean("basementIncluded");
                boolean pedestrianFriendly = rs.getBoolean("pedestrianFriendly");
                int yearBuilt = rs.getInt("yearBuilt");


                return new House(property.getPropertyID(), property, transitFriendly, privateBackyardIncluded,
                        poolIncluded, basementIncluded, pedestrianFriendly, yearBuilt);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("House Not FOUND! THROWING ERROR!");
            e.printStackTrace();
        }
        return null;
    }

    public static int deleteHouseById(UUID id){
        House house = selectHouseById(id);
        try{
            String sql = "DELETE FROM house WHERE id=\'" + house.getId() + "\';";
            SqlConnection.executeQuery(sql, false, true);
            PropertyDao.deletePropertyById(house.getProperty().getId());
            return 0;
        } catch (NullPointerException e){
            e.printStackTrace();
            return 1;
        }

    }

    public static House updateHouseById(UUID id, House update){
        House house = selectHouseById(id);
        if(house == null || house.getProperty() == null || house.getProperty().getAddress() == null){
            return null;
        }
        deleteHouseById(id);
        insertHouse(update.getProperty().getPropertyID(), update); // potential bug
        house = selectHouseById(id);
        System.out.println(house.getId());
        return house;
    }
}
