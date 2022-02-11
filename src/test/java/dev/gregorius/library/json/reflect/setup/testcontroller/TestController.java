package dev.gregorius.library.json.reflect.setup.testcontroller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GetMapping(path = "/get-reflecting-headers")
    public ResponseEntity<String> getReflectingHeaders(@RequestHeader final Map<String, String> headers) {

        return ResponseEntity.status(HttpStatus.OK).body(GSON.toJson(headers));
    }

    @GetMapping(path = "/get-reflecting-parameters")
    public ResponseEntity<String> getReflectingParameters(@RequestParam final Map<String, String> parameters) {

        return ResponseEntity.status(HttpStatus.OK).body(GSON.toJson(parameters));
    }

    @PostMapping(path = "/post-reflecting-body")
    public ResponseEntity<String> postReflectingBody(@RequestBody(required = false) final String body) {

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @GetMapping(path = "/get-returning-200")
    public ResponseEntity<String> getReturning200() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/get-returning-invalid-json")
    public ResponseEntity<String> getReturningInvalidJson() {
        final String responseBody = """
            {
                "data": "val1"
            """;
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
