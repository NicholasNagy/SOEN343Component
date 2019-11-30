package PropertyManager.PM.Data.dao;

import PropertyManager.PM.Data.util.SqlConnection;
import PropertyManager.PM.Application.Apartment.ApartmentTO;
import PropertyManager.PM.Application.Property.PropertyTO;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository("apartment")
public class ApartmentDao {

    public static UUID insertApartment(UUID id, ApartmentTO apartment){

        String sql = "INSERT INTO appartment (id, laundryIncluded, heatingIncluded, electricityIncluded, internetIncluded," +
                "furnished, airConditioning, smokersAccepted) VALUES (\'" + id + "\', " +
                apartment.isLaundryIncluded() + ", " + apartment.isHeatingIncluded() + ", " +
                apartment.isElectricityIncluded() + ", " + apartment.isInternetIncluded() + ", " +
                apartment.isFurnished() + ", " + apartment.isAirConditioning() + ", " + apartment.isSmokersAccepted() + ");";
        SqlConnection.executeQuery(sql, false, true);

        //This sets the apartment id to the property object so that when it is inserted,
        //the property object gets the proper secondary key.
        //This is probably not the best practice, but I don't think there's much point in rewriting
        //a bunch of code to fix this minor problem.
        PropertyTO property = apartment.getProperty();
        property.setPropertyID(apartment.getId());

        PropertyDao.insertProperty(apartment.getProperty().getId(), property);
        return apartment.getProperty().getId();
    }

    public static ApartmentTO selectApartmentById(UUID id) {

        //selects property by id
        PropertyTO property = PropertyDao.selectPropertyById(id);

        try {
            String sql = "SELECT * FROM appartment WHERE id=\'" + property.getPropertyID() + "\';";
            ResultSet rs = SqlConnection.executeQuery(sql);
            if(rs.next()){
                boolean laundryIncluded = rs.getBoolean("laundryIncluded");
                boolean heatingIncluded = rs.getBoolean("heatingIncluded");
                boolean electricityIncluded = rs.getBoolean("electricityIncluded");
                boolean internetIncluded = rs.getBoolean("internetIncluded");
                boolean furnished = rs.getBoolean("furnished");
                boolean airConditioning = rs.getBoolean("airConditioning");
                boolean smokersAccepted = rs.getBoolean("smokersAccepted");

                return new ApartmentTO(property.getPropertyID(), property, laundryIncluded, heatingIncluded,
                        electricityIncluded, internetIncluded, furnished, airConditioning, smokersAccepted);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Apartment Not FOUND! THROWING ERROR!");
            e.printStackTrace();
        }
        return null;
    }

    public static int deleteApartmentById(UUID id){
        ApartmentTO apartment = selectApartmentById(id);
        try{
            String sql = "DELETE FROM appartment WHERE id=\'" + apartment.getId() + "\';";
            SqlConnection.executeQuery(sql, false, true);
            PropertyDao.deletePropertyById(apartment.getProperty().getId());
            return 0;
        } catch (NullPointerException e){
            e.printStackTrace();
            return 1;
        }

    }

    public static ApartmentTO updateApartmentById(UUID id, ApartmentTO update){
        ApartmentTO apartment = selectApartmentById(id);
        if(apartment == null || apartment.getProperty() == null || apartment.getProperty().getAddress() == null){
            return null;
        }
        deleteApartmentById(id);
        insertApartment(update.getId(), update);
        apartment = selectApartmentById(id);
        return apartment;
    }


}
