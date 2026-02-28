package com.seo2yeon.students.domain.school.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class NeisSchoolClient {
    private final WebClient webClient;

    @Value("${neis.api.key}")
    private String apiKey;

    public String searchSchool(String locationName, String schoolName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hub/schoolInfo")
                        .queryParam("KEY", apiKey)
                        .queryParam("Type", "json")
                        .queryParam("pIndex", 1)
                        .queryParam("pSize", 10)
                        .queryParam("LCTN_SC_NM", locationName)
                        .queryParam("SCHUL_NM", schoolName)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
