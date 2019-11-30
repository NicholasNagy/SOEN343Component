package PropertyManager.PM;


import PropertyManager.PM.Database.SqlConnection;
import PropertyManager.PM.model.Address;
import PropertyManager.PM.model.Apartment;
import PropertyManager.PM.model.Property;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApartmentTests {

    @LocalServerPort
    private int port;

    private String postID;

    //This class is used to retrieve two other missing IDs from the database when only
    //getting the property id from api call
    private static class Combo {
        UUID appartmentID;
        UUID addressID;

        public Combo(String propertyID) throws SQLException {
            String getProperty = "SELECT address, propertyid FROM property WHERE id=\'" + propertyID + "\';";
            ResultSet rs = SqlConnection.executeQuery(getProperty);
            if(rs.next()){
                this.appartmentID = UUID.fromString(rs.getString("propertyid"));
                this.addressID = UUID.fromString(rs.getString("address"));
            } else{
                Assert.fail("Could not find property in table with id: " + propertyID);
            }


        }
    }

    @BeforeEach
    public void prep(){

        String sqlArray[] = new String[3];

        sqlArray[0] = "INSERT INTO property (id, parkingspaces, petsallowed, bedrooms, bathrooms, address, propertytype, propertyid, price)" +
                "VALUES ('58897f3d-6aec-49a7-96b0-d292df820eb0', 0, false, 0, 0, 'f5ee2171-8ac8-487e-b60f-46afd9dbd431', 'APPARTMENT'," +
                "        '13d509c7-359b-4699-8522-0ed216429511', 1);";
        sqlArray[1] = "INSERT INTO address (id, address, street, city, province, country, postalcode)" +
                "VALUES ('f5ee2171-8ac8-487e-b60f-46afd9dbd431', 1234, 'Adrien', 'Montreal', 'Quebec', 'Canada', 'J6Y2k3');";
        sqlArray[2] = "INSERT INTO appartment (id, laundryincluded, heatingincluded, electricityincluded, " +
                "internetincluded, furnished, airconditioning, smokersaccepted) VALUES" +
                "('13d509c7-359b-4699-8522-0ed216429511', true, true, true, true, true, true, true);";

        SqlConnection.executeSqlArray(sqlArray);

    }



    @Test
    public void testGetApartment() throws IOException {
        HttpClient httpClient = new HttpClient(port);
        Response response = httpClient.sendGet("apartment/58897f3d-6aec-49a7-96b0-d292df820eb0");
        ObjectMapper objectMapper = new ObjectMapper();
        Apartment apartment = objectMapper.readValue(response.body().string(), Apartment.class);

        //Assert that apartment is returned.
        Assert.assertNotNull(apartment);

        //Assert that variables have been properly retrieved from DB through get
        Assert.assertEquals(UUID.fromString("13d509c7-359b-4699-8522-0ed216429511"), apartment.getId());
        Assert.assertTrue(apartment.isLaundryIncluded());
        Assert.assertTrue(apartment.isHeatingIncluded());
        Assert.assertTrue(apartment.isElectricityIncluded());
        Assert.assertTrue(apartment.isInternetIncluded());
        Assert.assertTrue(apartment.isFurnished());
        Assert.assertTrue(apartment.isAirConditioning());
        Assert.assertTrue(apartment.isSmokersAccepted());

    }

    @Test
    public void testUpdateApartment() throws IOException, SQLException{
        //Assert.fail();
        HttpClient httpClient = new HttpClient(port);

        Response response = httpClient.sendPut("apartment/58897f3d-6aec-49a7-96b0-d292df820eb0",
                "{\n" +
                        "  \"property\":{\n" +
                        "    \"petsAllowed\": false,\n" +
                        "    \"parkingSpace\": 0,\n" +
                        "    \"bedrooms\": 0,\n" +
                        "    \"bathrooms\": 0,\n" +
                        "    \"address\": {\"id\": \"f5ee2171-8ac8-487e-b60f-46afd9dbd431\",\"address\": 1234, \"street\": \"Adrien\", \"city\": \"Terrebonne\", \"province\": \"Quebecr\", \"country\": \"Canada\", \t\"postalcode\": \"J6Y1K1\"},\n" +
                        "    \"propertyType\": \"APPARTMENT\",\n" +
                        "    \"price\": 0,\n" +
                        "    \"id\": \"58897f3d-6aec-49a7-96b0-d292df820eb0\"\n" +
                        "  },\n" +
                        "  \"laundryIncluded\": false,\n" +
                        "  \"heatingIncluded\": false,\n" +
                        "  \"electricityIncluded\": false,\n" +
                        "  \"internetIncluded\": false,\n" +
                        "  \"furnished\": false,\n" +
                        "  \"airConditioning\": false,\n" +
                        "  \"smokersAccepted\": false,\n" +
                        "  \"id\": \"13d509c7-359b-4699-8522-0ed216429511\"\n" +
                        "}");

        ObjectMapper objectMapper = new ObjectMapper();
        Apartment apartment = objectMapper.readValue(response.body().string(), Apartment.class);

        //Testing returned attributes
        //Testing returned apartment attributes
        Assert.assertFalse(apartment.isAirConditioning());
        Assert.assertFalse(apartment.isInternetIncluded());
        Assert.assertFalse(apartment.isSmokersAccepted());

        //Testing returned property attributes
        Property property = apartment.getProperty();
        Assert.assertFalse(property.petsAllowed);
        Assert.assertEquals(0, property.getBathrooms());
        Assert.assertEquals(0, property.getPrice(), 0.005);

        //Testing returned address attributes
        Address address = property.getAddress();
        Assert.assertEquals(1234, address.getAddress());
        Assert.assertEquals("Quebecr", address.getProvince());
        Assert.assertEquals("Canada", address.getCountry());

        String sqlAppartment = "SELECT * FROM appartment where id=\'13d509c7-359b-4699-8522-0ed216429511\';";
        String sqlProperty = "SELECT * FROM property where id=\'58897f3d-6aec-49a7-96b0-d292df820eb0\';";
        String sqlAddress = "SELECT * FROM address where id=\'f5ee2171-8ac8-487e-b60f-46afd9dbd431\';";

        ResultSet appartmentRS = SqlConnection.executeQuery(sqlAppartment);
        if(appartmentRS.next()){
            Assert.assertFalse(appartmentRS.getBoolean("airconditioning"));
            Assert.assertFalse(appartmentRS.getBoolean("smokersaccepted"));
            Assert.assertFalse(appartmentRS.getBoolean("internetincluded"));
        } else{
            Assert.fail("NO RESULT SET FOUND FOR APARTMENT MATCHING ID: 13d509c7-359b-4699-8522-0ed216429511!");
        }

        ResultSet propertyRS = SqlConnection.executeQuery(sqlProperty);
        if(propertyRS.next()){
            Assert.assertFalse(propertyRS.getBoolean("petsallowed"));
            Assert.assertEquals(0, propertyRS.getInt("bathrooms"));
            Assert.assertEquals(0, propertyRS.getDouble("price"), 0.005);
        } else {
            Assert.fail("NO RESULT SET FOUND FOR PROPERTY MATCHING ID: 58897f3d-6aec-49a7-96b0-d292df820eb0!");
        }
        ResultSet addressRS = SqlConnection.executeQuery(sqlAddress);
        if(addressRS.next()){
            Assert.assertEquals(1234, addressRS.getInt("address"));
            Assert.assertEquals("Quebecr", addressRS.getString("province"));
            Assert.assertEquals("Canada", addressRS.getString("country"));
        } else {
            Assert.fail("NO RESULT SET FOUND FOR PROPERTY MATCHING ID: 58897f3d-6aec-49a7-96b0-d292df820eb0!");
        }
    }

    @Test
    public void testDeleteApartment() throws IOException, SQLException {
        HttpClient httpClient = new HttpClient(port);

        httpClient.sendDelete("apartment/58897f3d-6aec-49a7-96b0-d292df820eb0");

        String sql1 = "SELECT * FROM appartment where id=\'13d509c7-359b-4699-8522-0ed216429511\';";
        String sql2 = "SELECT * FROM property where id=\'58897f3d-6aec-49a7-96b0-d292df820eb0\';";
        String sql3 = "SELECT * FROM address where id=\'f5ee2171-8ac8-487e-b60f-46afd9dbd431\';";

        ResultSet appartmentRS = SqlConnection.executeQuery(sql1);
        if(appartmentRS.next()){
            Assert.fail("Failed to delete appartment in db");
        }
        ResultSet propertyRS = SqlConnection.executeQuery(sql2);
        if(propertyRS.next()){
            Assert.fail("Failed to delete property in db");
        }
        ResultSet addressRS = SqlConnection.executeQuery(sql3);
        if(addressRS.next()){
            Assert.fail("Failed to delete address in db");
        }
    }

    @AfterEach
    public void cleanup(){

        String[] sqlArray = new String[3];

        sqlArray[0] = "DELETE FROM property where id='58897f3d-6aec-49a7-96b0-d292df820eb0';";
        sqlArray[1] = "DELETE FROM address where id='f5ee2171-8ac8-487e-b60f-46afd9dbd431';";
        sqlArray[2] = "DELETE FROM appartment where id='13d509c7-359b-4699-8522-0ed216429511';";

        SqlConnection.executeSqlArray(sqlArray);

    }

    @Test
    public void testInsertApartment() throws IOException, SQLException {
        HttpClient httpClient = new HttpClient(port);

        Response response = httpClient.sendPost("apartment", "{\n" +
                "  \"property\":{\n" +
                "    \"petsAllowed\": false,\n" +
                "    \"parkingSpace\": 0,\n" +
                "    \"bedrooms\": 0,\n" +
                "    \"bathrooms\": 0,\n" +
                "    \"address\": {\"address\": 1234, \"street\": \"Adrien\", \"city\": \"Terrebonne\", \"province\": \"Quebecr\", \"country\": \"Canada\", \t\"postalcode\": \"J6Y1K1\"},\n" +
                "    \"propertyType\": \"APPARTMENT\",\n" +
                "    \"price\": 0\n" +
                "  },\n" +
                "  \"laundryIncluded\": false,\n" +
                "  \"heatingIncluded\": false,\n" +
                "  \"electricityIncluded\": false,\n" +
                "  \"internetIncluded\": false,\n" +
                "  \"furnished\": false,\n" +
                "  \"airConditioning\": false,\n" +
                "  \"smokersAccepted\": false\n" +
                "}");

        postID = response.body().string().replaceAll("\"", "");
        Combo combo = new Combo(postID);
        System.out.println("Post");
        System.out.println(postID);
        System.out.println(combo.addressID);
        System.out.println(combo.appartmentID);

        //Assert two random parameters got inserted properly in property table
        String getPropertyRS = "SELECT id, bedrooms, bathrooms FROM property where id=\'"+ postID +"\'";
        ResultSet propertyRs = SqlConnection.executeQuery(getPropertyRS);
        if(propertyRs.next()){
            Assert.assertEquals(postID, propertyRs.getString("id")); //sanity check
            Assert.assertEquals(0, propertyRs.getInt("bedrooms"));
            Assert.assertEquals(0, propertyRs.getInt("bathrooms"));
        }
        else{
            Assert.fail("Could not find the row in the property table that matched the following id: " + postID);
        }

        //Assert two random parameters got inserted properly in appartment table
        String getApartmentRS = "SELECT id, laundryincluded, airconditioning FROM appartment where id=\'"+combo.appartmentID+"\'";
        ResultSet apartmentRs = SqlConnection.executeQuery(getApartmentRS);
        if(apartmentRs.next()){
            Assert.assertEquals(combo.appartmentID, UUID.fromString(apartmentRs.getString("id"))); //sanity check
            Assert.assertFalse(apartmentRs.getBoolean("laundryincluded"));
            Assert.assertFalse(apartmentRs.getBoolean("airconditioning"));
        }
        else {
            Assert.fail("Could not find the row in the appartment table that matched the following id: " + combo.appartmentID);
        }

        //Assert two random parameters got inserted properly into address table
        String getAddressRS = "SELECT id, address, province FROM address WHERE id=\'"+combo.addressID+"\'";
        ResultSet addressRs = SqlConnection.executeQuery(getAddressRS);
        if(addressRs.next()){
            Assert.assertEquals(combo.addressID, UUID.fromString(addressRs.getString("id"))); //Sanity check
            Assert.assertEquals(1234, addressRs.getInt("address"));
            Assert.assertEquals("Quebecr", addressRs.getString("province"));
        }
        else {
            Assert.fail("Could not find the row in the address table that matched the following id: " + combo.addressID);
        }

    }

    @AfterEach
    public void cleanUpPost() throws SQLException {
        String sqlArray[] = new String[3];
        if(postID != null){
            Combo combo = new Combo(postID);
            System.out.println("CleanupPost");
            System.out.println(postID);
            System.out.println(combo.appartmentID);
            System.out.println(combo.addressID);
            sqlArray[0] = "DELETE FROM property where id=\'"+ postID +"\'";
            sqlArray[1] = "DELETE FROM appartment where id=\'" + combo.appartmentID + "\';";
            sqlArray[2] = "DELETE FROM address where id=\'" + combo.addressID + "\';";
            SqlConnection.executeSqlArray(sqlArray);
        }
    }

}
