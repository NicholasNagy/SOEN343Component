package PropertyManager.PM.Data.dao;

import PropertyManager.PM.Data.util.SqlConnection;
import PropertyManager.PM.Application.Condo.CondoTO;
import PropertyManager.PM.Application.Property.PropertyTO;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository("condo")
public class CondoDao {
    public static UUID insertCondo(UUID id, CondoTO condo){

        String sql = "INSERT INTO condo (id, elevatorIncluded, storageIncluded, outdoorAreasIncluded, gymIncluded," +
                "conciergeIncluded, airConditioning, buildingInsurance) VALUES (\'" + id + "\', " +
                condo.isElevatorIncluded() + ", " + condo.isStorageIncluded() + ", " +
                condo.isOutdoorAreasIncluded() + ", " + condo.isGymIncluded() + ", " +
                condo.isConciergeIncluded() + ", " + condo.isAirConditioning() + ", " + condo.isBuildingInsurance() + ");";
        SqlConnection.executeQuery(sql, false, true);

        PropertyTO property = condo.getProperty();
        property.setPropertyID(condo.getId());

        PropertyDao.insertProperty(condo.getProperty().getId(), property);
        return condo.getProperty().getId();
    }

    public static CondoTO selectCondoById(UUID id) {

        //selects property by id
        PropertyTO property = PropertyDao.selectPropertyById(id);

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

                return new CondoTO(property.getPropertyID(), property, elevatorIncluded, storageIncluded,
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
       CondoTO condo = selectCondoById(id);
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

    public static CondoTO updateCondoById(UUID id, CondoTO update){
        CondoTO condo = selectCondoById(id);
        if(condo == null || condo.getProperty() == null || condo.getProperty().getAddress() == null){
            return null;
        }
        deleteCondoById(id);
        insertCondo(update.getId(), update); //potential bug
        condo = selectCondoById(id);
//        System.out.println(condo.getId());
        return condo;
    }


}
