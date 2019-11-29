package PropertyManager.PM.dao;

import PropertyManager.PM.Database.SqlConnection;
import PropertyManager.PM.model.Condo;
import PropertyManager.PM.model.Property;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository("condo")
public class CondoDao {
    public static UUID insertCondo(UUID id, Condo condo){

        String sql = "INSERT INTO condo (id, elevatorIncluded, storageIncluded, outdoorAreasIncluded, gymIncluded," +
                "conciergeIncluded, airConditioning, buildingInsurance) VALUES (\'" + id + "\', " +
                condo.isElevatorIncluded() + ", " + condo.isStorageIncluded() + ", " +
                condo.isOutdoorAreasIncluded() + ", " + condo.isGymIncluded() + ", " +
                condo.isConciergeIncluded() + ", " + condo.isAirConditioning() + ", " + condo.isBuildingInsurance() + ");";
        SqlConnection.executeQuery(sql, false, true);

        Property property = condo.getProperty();
        property.setPropertyID(condo.getId());

        PropertyDao.insertProperty(condo.getProperty().getId(), property);
        return condo.getProperty().getId();
    }

    public static Condo selectCondoById(UUID id) {

        //selects property by id
        Property property = PropertyDao.selectPropertyById(id);

        try {
            String sql = "SELECT * FROM condo WHERE id=\'" + property.getPropertyID() + "\';";
            ResultSet rs = SqlConnection.executeQuery(sql);
            if(rs.next()){
                boolean elevatorIncluded = rs.getBoolean("elevatorIncluded");
                boolean storageIncluded = rs.getBoolean("storageIncluded");
                boolean outdoorAreasIncluded = rs.getBoolean("outdoorAreasIncluded");
                boolean gymIncluded = rs.getBoolean("gymIncluded");
                boolean conciergeIncluded = rs.getBoolean("conciergeIncluded");
                boolean airConditioning = rs.getBoolean("airConditioning");
                boolean buildingInsurance = rs.getBoolean("buildingInsurance");

                return new Condo(property.getPropertyID(), property, elevatorIncluded, storageIncluded,
                        outdoorAreasIncluded, gymIncluded, conciergeIncluded, airConditioning, buildingInsurance);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Condo Not FOUND! THROWING ERROR!");
            e.printStackTrace();
        }
        return null;
    }

    public static int deleteCondoById(UUID id){
       Condo condo = selectCondoById(id);
        try{
            String sql = "DELETE FROM condo WHERE id=\'" + condo.getId() + "\';";
            SqlConnection.executeQuery(sql, false, true);
            PropertyDao.deletePropertyById(condo.getProperty().getId());
            return 0;
        } catch (NullPointerException e){
            e.printStackTrace();
            return 1;
        }

    }

    public static Condo updateCondoById(UUID id, Condo update){
        Condo condo = selectCondoById(id);
        if(condo == null || condo.getProperty() == null || condo.getProperty().getAddress() == null){
            return null;
        }
        deleteCondoById(id);
        insertCondo(update.getProperty().getPropertyID(), update); //potential bug
        condo = selectCondoById(id);
//        System.out.println(condo.getId());
        return condo;
    }


}
