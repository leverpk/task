package com.example.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/repositories/{username}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUserRepositories(
            @PathVariable String username,
            @RequestHeader(value = "Accept") String acceptHeader
    ) {
        try {
            List<GithubRepo> nonForksRepos = getNonForkedRepositories(username);

            if (acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
                return ResponseEntity
                        .ok(nonForksRepos);
            } else if (acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_XML_VALUE)) {
                ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_ACCEPTABLE.value(), "406 Not acceptable.");
                String xmlResponse = new XmlMapper().writeValueAsString(apiResponse);
                return ResponseEntity
                        .status(HttpStatus.NOT_ACCEPTABLE)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                        .body(xmlResponse);
            } else {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Header value ACCEPT is invalid."));
            }
        } catch (HttpClientErrorException.NotFound | JsonProcessingException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found."));
        }
    }

    private List<GithubRepo> getNonForkedRepositories(String username) {
        String URL = "https://api.github.com";
        String apiUrl = URL + "/users/" + username + "/repos";
        GithubRepo[] repos = restTemplate.getForObject(apiUrl, GithubRepo[].class);
        return Arrays.stream(repos)
                .filter(repo -> !repo.isForked())
                .collect(Collectors.toList());
    }

}