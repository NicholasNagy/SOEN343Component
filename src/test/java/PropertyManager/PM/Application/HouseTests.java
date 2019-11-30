package PropertyManager.PM.Application;

import PropertyManager.PM.Data.util.SqlConnection;
import PropertyManager.PM.Application.Property.AddressTO;
import PropertyManager.PM.Application.House.HouseTO;
import PropertyManager.PM.Application.Property.PropertyTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class HouseTests {
    @LocalServerPort
    private int port;

    private String postID;

    //This class is used to retrieve two other missing IDs from the database when only
    //getting the property id from api call
    private static class Combo {
        UUID houseID;
        UUID addressID;

        public Combo(String propertyID) throws SQLException {
            String getProperty = "SELECT address, propertyid FROM property WHERE id=\'" + propertyID + "\';";
            ResultSet rs = SqlConnection.executeQuery(getProperty);
            if(rs.next()){
                this.houseID = UUID.fromString(rs.getString("propertyid"));
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
        sqlArray[2] = "INSERT INTO house (id, transitFriendly, privateBackyardIncluded, poolIncluded, " +
                "basementIncluded, pedestrianFriendly, yearBuilt) VALUES" +
                "('13d509c7-359b-4699-8522-0ed216429511', true, true, true, true, true, 1999);";

        SqlConnection.executeSqlArray(sqlArray);

    }



    @Test
    public void testGetHouse() throws IOException {
        HttpClient httpClient = new HttpClient(port);
        Response response = httpClient.sendGet("house/58897f3d-6aec-49a7-96b0-d292df820eb0");
        ObjectMapper objectMapper = new ObjectMapper();
        HouseTO house = objectMapper.readValue(response.body().string(), HouseTO.class);

        //Assert that house is returned.
        Assert.assertNotNull(house);

        //Assert that variables have been properly retrieved from DB through get
        Assert.assertEquals(UUID.fromString("13d509c7-359b-4699-8522-0ed216429511"), house.getId());
        Assert.assertTrue(house.isBasementIncluded());
        Assert.assertTrue(house.isPedestrianFriendly());
        Assert.assertTrue(house.isPoolIncluded());
        Assert.assertTrue(house.isPrivateBackyardIncluded());
        Assert.assertTrue(house.isTransitFriendly());
        Assert.assertEquals(1999, house.getYearBuilt());
        //Assert.assertTrue(house.isSmokersAccepted());

    }

    @Test
    public void testUpdateHouse() throws IOException, SQLException{
        //Assert.fail();
        HttpClient httpClient = new HttpClient(port);

        Response response = httpClient.sendPut("house/58897f3d-6aec-49a7-96b0-d292df820eb0",
                "{\n" +
                        "  \"property\":{\n" +
                        "    \"petsAllowed\": false,\n" +
                        "    \"parkingSpace\": 0,\n" +
                        "    \"bedrooms\": 0,\n" +
                        "    \"bathrooms\": 0,\n" +
                        "    \"address\": {\"id\": \"f5ee2171-8ac8-487e-b60f-46afd9dbd431\",\"address\": 1234, \"street\": \"Adrien\", \"city\": \"Terrebonne\", \"province\": \"Quebecr\", \"country\": \"Canada\", \t\"postalcode\": \"J6Y1K1\"},\n" +
                        "    \"propertyType\": \"HOUSE\",\n" +
                        "    \"price\": 0,\n" +
                        "    \"id\": \"58897f3d-6aec-49a7-96b0-d292df820eb0\"\n" +
                        "  },\n" +
                        "  \"transitFriendly\": false,\n" +
                        "  \"privateBackyardIncluded\": false,\n" +
                        "  \"poolIncluded\": false,\n" +
                        "  \"basementIncluded\": false,\n" +
                        "  \"pedestrianFriendly\": false,\n" +
                        "  \"yearBuilt\": 1999,\n" +
                        "  \"id\": \"13d509c7-359b-4699-8522-0ed216429511\"\n" +
                        "}");

        ObjectMapper objectMapper = new ObjectMapper();
        HouseTO house = objectMapper.readValue(response.body().string(), HouseTO.class);

        //Testing returned attributes
        //Testing returned house attributes
        Assert.assertFalse(house.isTransitFriendly());
        Assert.assertFalse(house.isPrivateBackyardIncluded());
        Assert.assertFalse(house.isPoolIncluded());

        //Testing returned property attributes
        PropertyTO property = house.getProperty();
        Assert.assertFalse(property.petsAllowed);
        Assert.assertEquals(0, property.getBathrooms());
        Assert.assertEquals(0, property.getPrice(), 0.005);

        //Testing returned address attributes
        AddressTO address = property.getAddress();
        Assert.assertEquals(1234, address.getAddress());
        Assert.assertEquals("Quebecr", address.getProvince());
        Assert.assertEquals("Canada", address.getCountry());

        String sqlHouse = "SELECT * FROM house where id=\'13d509c7-359b-4699-8522-0ed216429511\';";
        String sqlProperty = "SELECT * FROM property where id=\'58897f3d-6aec-49a7-96b0-d292df820eb0\';";
        String sqlAddress = "SELECT * FROM address where id=\'f5ee2171-8ac8-487e-b60f-46afd9dbd431\';";

        ResultSet houseRS = SqlConnection.executeQuery(sqlHouse);
        if(houseRS.next()){
            Assert.assertFalse(houseRS.getBoolean("transitFriendly"));
            Assert.assertFalse(houseRS.getBoolean("privateBackyardIncluded"));
            Assert.assertFalse(houseRS.getBoolean("poolIncluded"));
        } else{
            Assert.fail("NO RESULT SET FOUND FOR HOUSE MATCHING ID: 13d509c7-359b-4699-8522-0ed216429511!");
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
    public void testDeleteHouse() throws IOException, SQLException {
        HttpClient httpClient = new HttpClient(port);

        httpClient.sendDelete("house/58897f3d-6aec-49a7-96b0-d292df820eb0");

        String sql1 = "SELECT * FROM house where id=\'13d509c7-359b-4699-8522-0ed216429511\';";
        String sql2 = "SELECT * FROM property where id=\'58897f3d-6aec-49a7-96b0-d292df820eb0\';";
        String sql3 = "SELECT * FROM address where id=\'f5ee2171-8ac8-487e-b60f-46afd9dbd431\';";

        ResultSet houseRS = SqlConnection.executeQuery(sql1);
        if(houseRS.next()){
            Assert.fail("Failed to delete house in db");
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
        sqlArray[2] = "DELETE FROM house where id='13d509c7-359b-4699-8522-0ed216429511';";

        SqlConnection.executeSqlArray(sqlArray);

    }

    @Test
    public void testInsertHouse() throws IOException, SQLException {
        HttpClient httpClient = new HttpClient(port);

        Response response = httpClient.sendPost("house", "{\n" +
                "  \"property\":{\n" +
                "    \"petsAllowed\": false,\n" +
                "    \"parkingSpace\": 0,\n" +
                "    \"bedrooms\": 0,\n" +
                "    \"bathrooms\": 0,\n" +
                "    \"address\": {\"address\": 1234, \"street\": \"Adrien\", \"city\": \"Terrebonne\", \"province\": \"Quebecr\", \"country\": \"Canada\", \t\"postalcode\": \"J6Y1K1\"},\n" +
                "    \"propertyType\": \"HOUSE\",\n" +
                "    \"price\": 0\n" +
                "  },\n" +
                        "  \"transitFriendly\": false,\n" +
                        "  \"privateBackyardIncluded\": false,\n" +
                        "  \"poolIncluded\": false,\n" +
                        "  \"basementIncluded\": false,\n" +
                        "  \"pedestrianFriendly\": false,\n" +
                        "  \"yearBuilt\": 1999\n"+
                "}");

        postID = response.body().string().replaceAll("\"", "");
        HouseTests.Combo combo = new HouseTests.Combo(postID);
        System.out.println("Post");
        System.out.println(postID);
        System.out.println(combo.addressID);
        System.out.println(combo.houseID);

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


        String getHouseRS = "SELECT id, transitFriendly, privateBackyardIncluded FROM house where id=\'"+combo.houseID+"\'";
        ResultSet houseRs = SqlConnection.executeQuery(getHouseRS);
        if(houseRs.next()){
            Assert.assertEquals(combo.houseID, UUID.fromString(houseRs.getString("id"))); //sanity check
            Assert.assertFalse(houseRs.getBoolean("transitFriendly"));
            Assert.assertFalse(houseRs.getBoolean("privateBackyardIncluded"));
        }
        else {
            Assert.fail("Could not find the row in the house table that matched the following id: " + combo.houseID);
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
            HouseTests.Combo combo = new HouseTests.Combo(postID);
            System.out.println("CleanupPost");
            System.out.println(postID);
            System.out.println(combo.houseID);
            System.out.println(combo.addressID);
            sqlArray[0] = "DELETE FROM property where id=\'"+ postID +"\'";
            sqlArray[1] = "DELETE FROM house where id=\'" + combo.houseID + "\';";
            sqlArray[2] = "DELETE FROM address where id=\'" + combo.addressID + "\';";
            SqlConnection.executeSqlArray(sqlArray);
        }
    }

}
