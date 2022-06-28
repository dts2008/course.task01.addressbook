import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import java.io.IOException;
import java.net.http.HttpClient;

import org.example.addressbook.application.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@WireMockTest(httpPort = 8888)
public class UserWireMockTest {

    //private static WireMockServer wireMockServer;

    @BeforeEach
    public void setUp()
    {
        //wireMockServer = new WireMockServer();
    }

    @Test
    public void TestUserService() throws IOException, InterruptedException {
        stubFor(get(urlPathMatching("/get"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"Id\":1,\"fIO\":\"fio1\",\"cityId\":1,\"email\":\"fio1.google.com\", \"phone\":\"050-505-55-55\"},{\"Id\":2,\"fIO\":\"fio2\",\"cityId\":1,\"email\":\"fio2.google.com\",\"phone\":\"050-707-7-7\"}]")));

        var httpClient = HttpClient.newBuilder().build();

        var mapper = new ObjectMapper();
        var userService = new UserService(httpClient, "http://localhost:8888", mapper);

        var result= userService.get();

        assertEquals(result.size(), 2);
        assertNotNull(result.get(0));
        assertNotNull(result.get(1));

        assertEquals(result.get(0).getFIO(), "fio1");
    }
}
