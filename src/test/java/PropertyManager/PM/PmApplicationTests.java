package PropertyManager.PM;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PmApplicationTests {

    @LocalServerPort
    private int port;


	@Test
	void contextLoads() throws IOException {
		// Tests that the context loads and that we are able to make the most basic get request.
		HttpClient httpClient = new HttpClient(port);
		System.out.println("Testing 1 - Send Http GET request");
		System.out.println(httpClient.sendGet("property").body().string());
	}

	@Before
	void hello(){

	}

	@After
	void bye(){

	}

}
