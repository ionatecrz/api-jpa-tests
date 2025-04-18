package edu.comillas.icai.gitt.pat.spring.p5;

import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MockMvc;



import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
class P5ApplicationE2ETest {

    private static final String NAME = "Name";
    private static final String EMAIL = "name@email.com";
    private static final String PASS = "aaaaaaA1";

    @Autowired
    TestRestTemplate client;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void registerTest() throws Exception {
        String response = mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                            {
                                              "name": "Name",
                                              "email": "name@email.com",
                                              "password": "Password123",
                                              "role": "USER"
                                            }
                                        """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(response.contains("\"name\":\"Name\""));
        assertTrue(response.contains("\"email\":\"name@email.com\""));
        assertTrue(response.contains("\"role\":\"USER\""));
    }


    /**
     * TODO#11
     * Completa el siguiente test E2E para que verifique la
     * respuesta de login cuando se proporcionan credenciales correctas
     */
    @Test public void loginOkTest() {
        // Given ...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String registro = "{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.USER + "\"," +
                "\"password\":\"" + PASS + "\"}";

        client.exchange(
                "http://localhost:8080/api/users",
                HttpMethod.POST,
                new HttpEntity<>(registro, headers),
                String.class);

        String login = "{" +
                "\"email\":\"" + EMAIL + "\"," +
                "\"password\":\"" + PASS + "\"}";

        // When ...
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/users/me/session",
                HttpMethod.POST,
                new HttpEntity<>(login, headers),
                String.class);

        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Set-Cookie"));
    }
}
