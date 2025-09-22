package co.pragma.api.adapters;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.test.StepVerifier;

class ResponseServiceTest {

    private final ResponseService responseService = new ResponseService();

    @Test
    void shouldReturnOkResponseWithJsonContentType() {
        String testBody = "Test data";

        StepVerifier.create(responseService.okJson(testBody))
                .expectNextMatches(serverResponse -> {
                    return serverResponse.statusCode().is2xxSuccessful() &&
                            serverResponse.headers().getContentType().equals(MediaType.APPLICATION_JSON);
                })
                .verifyComplete();
    }
}
