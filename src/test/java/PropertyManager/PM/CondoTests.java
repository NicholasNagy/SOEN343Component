package PropertyManager.PM;

import PropertyManager.PM.Database.SqlConnection;
import PropertyManager.PM.model.Address;
import PropertyManager.PM.model.Condo;
import PropertyManager.PM.model.Property;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
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
public class CondoTests {
    @LocalServerPort
    private int port;

    private String postID;

    //This class is used to retrieve two other missing IDs from the database when only
    //getting the property id from api call
    private static class Combo {
        UUID condoID;
        UUID addressID;

        public Combo(String propertyID) throws SQLException {
            String getProperty = "SELECT address, propertyid FROM property WHERE id=\'" + propertyID + "\';";
            ResultSet rs = SqlConnection.executeQuery(getProperty);
            if(rs.next()){
                this.condoID = UUID.fromString(rs.getString("propertyid"));
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
                "VALUES ('8ab4f916-0228-4812-8b27-b321e7c04deb', 0, false, 0, 0, '7ab012b6-8845-4bd4-abfa-50cafa68b8e2', 'CONDO'," +
                "        '641b4b97-279a-4bde-b477-2920cfc2921f', 1);";
        sqlArray[1] = "INSERT INTO address (id, address, street, city, province, country, postalcode)" +
                "VALUES ('7ab012b6-8845-4bd4-abfa-50cafa68b8e2', 9999, 'bishop', 'Montreal', 'Quebec', 'Canada', 'H3G1Y1');";
        sqlArray[2] = "INSERT INTO condo (id, elevatorIncluded, storageIncluded, outdoorAreasIncluded, " +
                "gymIncluded, conciergeIncluded, airConditioning, buildingInsurance) VALUES" +
                "('641b4b97-279a-4bde-b477-2920cfc2921f', true, true, true, true, true, true, true);";
        System.out.print("ARRRRAAYYYYYY");
        for(String a : sqlArray) {
            System.out.println(a);
        }
        SqlConnection.executeSqlArray(sqlArray);

    }



    @Test
    public void testGetCondo() throws IOException {
        HttpClient httpClient = new HttpClient(port);
        Response response = httpClient.sendGet("condo/8ab4f916-0228-4812-8b27-b321e7c04deb");
        ObjectMapper objectMapper = new ObjectMapper();
       Condo condo = objectMapper.readValue(response.body().string(), Condo.class);

        //Assert that condo is returned.
        Assert.assertNotNull(condo);

        //Assert that variables have been properly retrieved from DB through get
        Assert.assertEquals(UUID.fromString("641b4b97-279a-4bde-b477-2920cfc2921f"), condo.getId());
        Assert.assertTrue(condo.isAirConditioning());
        Assert.assertTrue(condo.isBuildingInsurance());
        Assert.assertTrue(condo.isConciergeIncluded());
        Assert.assertTrue(condo.isOutdoorAreasIncluded());
        Assert.assertTrue(condo.isElevatorIncluded());
        Assert.assertTrue(condo.isStorageIncluded());
        Assert.assertTrue(condo.isGymIncluded());

    }

    @Test
    public void testUpdateCondo() throws IOException, SQLException{
        //Assert.fail();
        HttpClient httpClient = new HttpClient(port);

        Response response = httpClient.sendPut("condo/8ab4f916-0228-4812-8b27-b321e7c04deb",
                "{\n" +
                        "  \"property\":{\n" +
                        "    \"petsAllowed\": false,\n" +
                        "    \"parkingSpace\": 0,\n" +
                        "    \"bedrooms\": 0,\n" +
                        "    \"bathrooms\": 0,\n" +
                        "    \"address\": {\"id\": \"7ab012b6-8845-4bd4-abfa-50cafa68b8e2\",\"address\": 9999, \"street\": \"Robert\", \"city\": \"Beyrouth\", \"province\": \"No\", \"country\": \"Lebanon\", \t\"postalcode\": \"0000\"},\n" +
                        "    \"propertyType\": \"CONDO\",\n" +
                        "    \"price\": 0,\n" +
                        "    \"id\": \"8ab4f916-0228-4812-8b27-b321e7c04deb\"\n" +
                        "  },\n" +
                        "  \"elevatorIncluded\": false,\n" +
                        "  \"storageIncluded\": false,\n" +
                        "  \"outdoorAreasIncluded\": false,\n" +
                        "  \"gymIncluded\": false,\n" +
                        "  \"conciergeIncluded\": false,\n" +
                        "  \"airConditioning\": false,\n" +
                        "  \"buildingInsurance\": true,\n" +
                        "  \"id\": \"641b4b97-279a-4bde-b477-2920cfc2921f\"\n" +
                        "}");
//System.out.print("#########11"+response.body().string());
        ObjectMapper objectMapper = new ObjectMapper();
        Condo condo = objectMapper.readValue(response.body().string(), Condo.class);

        //Testing returned attributes
        //Testing returned condo attributes
        Assert.assertFalse(condo.isAirConditioning());
        Assert.assertFalse(condo.isGymIncluded());
        Assert.assertFalse(condo.isStorageIncluded());

        //Testing returned property attributes
        Property property = condo.getProperty();
        Assert.assertFalse(property.petsAllowed);
        Assert.assertEquals(0, property.getBathrooms());
        Assert.assertEquals(0, property.getPrice(), 0.005);

        //Testing returned address attributes
        Address address = property.getAddress();
        Assert.assertEquals(9999, address.getAddress());
        Assert.assertEquals("No", address.getProvince());
        Assert.assertEquals("Lebanon", address.getCountry());

        String sqlCondo = "SELECT * FROM condo where id=\'13d509c7-359b-4699-8522-0ed216429511\';";
        String sqlProperty = "SELECT * FROM property where id=\'58897f3d-6aec-49a7-96b0-d292df820eb0\';";
        String sqlAddress = "SELECT * FROM address where id=\'f5ee2171-8ac8-487e-b60f-46afd9dbd431\';";

        ResultSet condoRS = SqlConnection.executeQuery(sqlCondo);
        if(condoRS.next()){
            Assert.assertFalse(condoRS.getBoolean("airConditioning"));
            Assert.assertFalse(condoRS.getBoolean("gymIncluded"));
            Assert.assertFalse(condoRS.getBoolean("storageIncluded"));
        } else{
            Assert.fail("NO RESULT SET FOUND FOR condo MATCHING ID: 13d509c7-359b-4699-8522-0ed216429511!");
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
            Assert.assertEquals(9999, addressRS.getInt("address"));
            Assert.assertEquals("Not", addressRS.getString("province"));
            Assert.assertEquals("Lebanon", addressRS.getString("country"));
        } else {
            Assert.fail("NO RESULT SET FOUND FOR PROPERTY MATCHING ID: 58897f3d-6aec-49a7-96b0-d292df820eb0!");
        }
    }

    @Test
    public void testDelete() throws IOException, SQLException {
        HttpClient httpClient = new HttpClient(port);

        httpClient.sendDelete("condo/8ab4f916-0228-4812-8b27-b321e7c04deb");

        String sql1 = "SELECT * FROM condo where id=\'641b4b97-279a-4bde-b477-2920cfc2921f\';";
        String sql2 = "SELECT * FROM property where id=\'8ab4f916-0228-4812-8b27-b321e7c04deb\';";
        String sql3 = "SELECT * FROM address where id=\'7ab012b6-8845-4bd4-abfa-50cafa68b8e2\';";

        ResultSet condoRS = SqlConnection.executeQuery(sql1);
        if(condoRS.next()){
            Assert.fail("Failed to delete condo in db");
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
         System.out.print("FROMM CLEANNNNNNN1");
        String[] sqlArray = new String[3];

        sqlArray[0] = "DELETE FROM property where id='8ab4f916-0228-4812-8b27-b321e7c04deb';";
        sqlArray[1] = "DELETE FROM address where id='7ab012b6-8845-4bd4-abfa-50cafa68b8e2';";
        sqlArray[2] = "DELETE FROM condo where id='641b4b97-279a-4bde-b477-2920cfc2921f';";

        SqlConnection.executeSqlArray(sqlArray);

    }


    @Test
    public void testInsertCondo() throws IOException, SQLException {
        HttpClient httpClient = new HttpClient(port);

        Response response = httpClient.sendPost("condo", "{\n" +
                "  \"property\":{\n" +
                "    \"petsAllowed\": false,\n" +
                "    \"parkingSpace\": 10,\n" +
                "    \"bedrooms\": 0,\n" +
                "    \"bathrooms\": 0,\n" +
                "    \"address\": {\"address\": 9999, \"street\": \"Robert\", \"city\": \"Beyrouth\", \"province\": \"Not\", \"country\": \"Lebanon\", \t\"postalcode\": \"0000\"},\n" +
                "    \"propertyType\": \"CONDO\",\n" +
                "    \"price\": 0\n" +
                "  },\n" +
                "  \"elevatorIncluded\": false,\n" +
                "  \"storageIncluded\": false,\n" +
                "  \"outdoorAreasIncluded\": false,\n" +
                "  \"gymIncluded\": false,\n" +
                "  \"conciergeIncluded\": false,\n" +
                "  \"airConditioning\": false,\n" +
                "  \"buildingInsurance\": true\n" +
                "}");

        postID = response.body().string().replaceAll("\"", "");
        if(postID == ""){
            System.out.println("NULLLLLLLLLLLL");
        }
        System.out.println(postID);
        CondoTests.Combo combo = new CondoTests.Combo(postID);

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

        //Assert two random parameters got inserted properly in condo table
        String getCondoRS = "SELECT id, conciergeIncluded, airConditioning FROM condo where id=\'"+combo.condoID +"\'";
        ResultSet condoRs = SqlConnection.executeQuery(getCondoRS);
        if(condoRs.next()){
            Assert.assertEquals(combo.condoID, UUID.fromString(condoRs.getString("id"))); //sanity check
            Assert.assertFalse(condoRs.getBoolean("conciergeIncluded"));
            Assert.assertFalse(condoRs.getBoolean("airConditioning"));
        }
        else {
            Assert.fail("Could not find the row in the condo table that matched the following id: " + combo.condoID);
        }

        //Assert two random parameters got inserted properly into address table
        String getAddressRS = "SELECT id, address, province FROM address WHERE id=\'"+combo.addressID+"\'";
        ResultSet addressRs = SqlConnection.executeQuery(getAddressRS);
        if(addressRs.next()){
            Assert.assertEquals(combo.addressID, UUID.fromString(addressRs.getString("id"))); //Sanity check
            Assert.assertEquals(9999, addressRs.getInt("address"));
            Assert.assertEquals("Not", addressRs.getString("province"));
        }
        else {
            Assert.fail("Could not find the row in the address table that matched the following id: " + combo.addressID);
        }

    }

    @AfterEach
    public void cleanUpPost() throws SQLException {
        String sqlArray[] = new String[3];
        if(postID!=null){
            CondoTests.Combo combo = new CondoTests.Combo(postID);
            sqlArray[0] = "DELETE FROM property where id=\'"+ postID +"\'";
            sqlArray[1] = "DELETE FROM condo where id=\'" + combo.condoID + "\';";
            sqlArray[2] = "DELETE FROM address where id=\'" + combo.addressID + "\';";
            SqlConnection.executeSqlArray(sqlArray);
        }
    }
}
