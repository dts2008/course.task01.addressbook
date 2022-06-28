package org.example.addressbook.application;

import org.example.addressbook.application.DTO.UserInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RequiredArgsConstructor
public class UserService {
    private final HttpClient httpClient;
    private final String serverLink;
    private final ObjectMapper objectMapper;

    public List<UserInfo> get() throws IOException, InterruptedException {

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(serverLink+"/get"))
                .build();

        var body = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

        return objectMapper.readValue(body, new TypeReference<List<UserInfo>>(){});
    }
}
