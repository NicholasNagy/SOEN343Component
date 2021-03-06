package PropertyManager.PM.Application;

import PropertyManager.PM.Data.util.SqlConnection;
import PropertyManager.PM.Application.Property.PropertyTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.UUID;

//import static PropertyManager.PM.model.Property.propertyType.APPARTMENT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyTests {

    @LocalServerPort
    private int port;

    @Test
    public void testGetProperties() throws IOException {
        HttpClient httpClient = new HttpClient(port);
        Response response = httpClient.sendGet("property");
        ObjectMapper objectMapper = new ObjectMapper();
        PropertyTO[] properties = objectMapper.readValue(response.body().string(), PropertyTO[].class);

        PropertyTO property1 = null, property2 = null;

        for (int i = 0; i< properties.length; i++){
            if(UUID.fromString("58897f3d-6aec-49a7-96b0-d292df820eb0").equals(properties[i].getId())){
                property1 = properties[i];
            }
            if(UUID.fromString("58897f3d-6aec-49a7-96b0-d292df820eb2").equals(properties[i].getId())){
                property2 = properties[i];
            }
        }
        //Assert that they were actually found
        Assertions.assertNotNull(property1);
        Assertions.assertNotNull(property2);

        //Sanity check
        Assertions.assertEquals(property1.getId(), UUID.fromString("58897f3d-6aec-49a7-96b0-d292df820eb0"));
        Assertions.assertEquals(property2.getId(), UUID.fromString("58897f3d-6aec-49a7-96b0-d292df820eb2"));

        //Assert addresses were well mapped to objects
        Assertions.assertNotNull(property1.getAddress());
        Assertions.assertNotNull(property2.getAddress());

        //Asserting random value for address objects
        Assertions.assertEquals(1234, property1.getAddress().getAddress());
        Assertions.assertEquals(1235, property2.getAddress().getAddress());

        //Asserting random value for property objects
        Assertions.assertEquals(1, property1.getPrice());
        Assertions.assertEquals(2, property2.getPrice());
    }

    @BeforeAll
    public static void prep(){

        String[] sqlArray = new String[4];

        sqlArray[0] = "INSERT INTO property (id, parkingspaces, petsallowed, bedrooms, bathrooms, address, propertytype, propertyid, price)" +
                "VALUES ('58897f3d-6aec-49a7-96b0-d292df820eb0', 0, false, 0, 0, 'f5ee2171-8ac8-487e-b60f-46afd9dbd431', 'APPARTMENT'," +
                "        '13d509c7-359b-4699-8522-0ed216429511', 1);";
        sqlArray[1] = "INSERT INTO address (id, address, street, city, province, country, postalcode)" +
                "VALUES ('f5ee2171-8ac8-487e-b60f-46afd9dbd431', 1234, 'Adrien', 'Montreal', 'Quebec', 'Canada', 'J6Y2k3');";
        sqlArray[2] = "INSERT INTO property (id, parkingspaces, petsallowed, bedrooms, bathrooms, address, propertytype, propertyid, price)" +
                "VALUES ('58897f3d-6aec-49a7-96b0-d292df820eb2', 1, true, 1, 1, 'f5ee2171-8ac8-487e-b60f-46afd9dbd432', 'APPARTMENT'," +
                "        '13d509c7-359b-4699-8522-0ed216429512', 2);";
        sqlArray[3] = "INSERT INTO address (id, address, street, city, province, country, postalcode)" +
                "VALUES ('f5ee2171-8ac8-487e-b60f-46afd9dbd432', 1235, 'Adrien2', 'Montreal2', 'Quebec2', 'Canada2', 'J6Y2k3')";

        SqlConnection.executeSqlArray(sqlArray);

    }

    @AfterAll
    public static void cleanup(){
        String[] sqlArray = new String[4];

        sqlArray[0] = "DELETE FROM property where id='58897f3d-6aec-49a7-96b0-d292df820eb0';";
        sqlArray[1] = "DELETE FROM address where id='f5ee2171-8ac8-487e-b60f-46afd9dbd431';";
        sqlArray[2] = "DELETE FROM property where id='58897f3d-6aec-49a7-96b0-d292df820eb2';";
        sqlArray[3] = "DELETE FROM address where id='f5ee2171-8ac8-487e-b60f-46afd9dbd432';";

        SqlConnection.executeSqlArray(sqlArray);
    }

}

