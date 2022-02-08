package dev.gregorius.library.json.reflect.setup;

import dev.gregorius.library.json.reflect.model.JsonReflect;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    @LocalServerPort
    private int localServerPort;

    @BeforeAll
    void beforeAll() {
        JsonReflect.setBaseUrl(String.format("http://localhost:%d", localServerPort));
    }
}
