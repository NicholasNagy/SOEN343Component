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
    public static int insertCondo(UUID id, Condo condo){

        String sql = "INSERT INTO condo (id, laundryIncluded, heatingIncluded, electricityIncluded, internetIncluded," +
                "furnished, airConditioning, smokersAccepted) VALUES (\'" + id + "\', " +
                condo.isElevatorIncluded() + ", " + condo.isStorageIncluded() + ", " +
                condo.isOutdoorAreasIncluded() + ", " + condo.isGymIncluded() + ", " +
                condo.isConciergeIncluded() + ", " + condo.isAirConditioning() + ", " + condo.isBuildingInsurance() + ");";
        SqlConnection.executeQuery(sql, false, true);

        Property property = condo.getProperty();
        property.setPropertyID(condo.getId());

        PropertyDao.insertProperty(condo.getProperty().getId(), property);
        return 0;
    }

    public static Condo selectCondoById(UUID id) {

        //selects property by id
        Property property = PropertyDao.selectPropertyById(id);

        try {
            String sql = "SELECT * FROM condo WHERE id=\'" + property.getPropertyID() + "\';";
            ResultSet rs = SqlConnection.executeQuery(sql);
            if(rs.next()){
                boolean laundryIncluded = rs.getBoolean("laundryIncluded");
                boolean heatingIncluded = rs.getBoolean("heatingIncluded");
                boolean electricityIncluded = rs.getBoolean("electricityIncluded");
                boolean internetIncluded = rs.getBoolean("internetIncluded");
                boolean furnished = rs.getBoolean("furnished");
                boolean airConditioning = rs.getBoolean("airConditioning");
                boolean smokersAccepted = rs.getBoolean("smokersAccepted");

                return new Condo(property.getPropertyID(), property, laundryIncluded, heatingIncluded,
                        electricityIncluded, internetIncluded, furnished, airConditioning, smokersAccepted);
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
        insertCondo(update.getProperty().getPropertyID(), update);
        condo = selectCondoById(id);
        System.out.println(condo.getId());
        return condo;
    }


}
