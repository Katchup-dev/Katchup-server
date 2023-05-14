package site.katchup.katchupserver.api.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import site.katchup.katchupserver.api.auth.dto.GoogleInfoDto;
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.response.ErrorStatus;

@RequiredArgsConstructor
@Component
public class GoogleAuthService  {

    public static GoogleInfoDto getGoogleData(String googleToken) {

        try {
            HttpHeaders headers = new HttpHeaders();

            headers.add("Authorization", "Bearer " + googleToken);
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v1/userinfo",
                HttpMethod.GET,
                googleUserInfoRequest,
                String.class
            );

            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            String nickname = jsonNode.get("name").asText();
            String email = jsonNode.get("email").asText();
            String imageUrl = jsonNode.get("picture").asText();

            return new GoogleInfoDto(nickname, email, imageUrl);
        } catch (HttpClientErrorException e) {
            throw new CustomException(ErrorStatus.GOOGLE_UNAUTHORIZED_USER);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
