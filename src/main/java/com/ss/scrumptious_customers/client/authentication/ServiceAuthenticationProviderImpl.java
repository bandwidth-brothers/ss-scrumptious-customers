// package com.ss.scrumptious_customers.client.authentication;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ss.scrumptious_customers.client.AuthClient;
// import com.ss.scrumptious_customers.exception.AccountsClientException;
// import com.ss.scrumptious_customers.exception.AuthenticationFailureException;

// import java.io.IOException;
// import java.time.Instant;
// import java.time.ZoneId;
// import java.time.ZonedDateTime;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;

// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class ServiceAuthenticationProviderImpl implements ServiceAuthenticationProvider {

//     private final AuthClient accountsClient;
//     private final ObjectMapper objectMapper;
//     private final ServiceAuthenticationConfiguration serviceAuthenticationConfiguration;
//     private String authorizationHeader;
//     private ZonedDateTime expiration;

//     @Override
//     public String getAuthorizationHeader() {
//         if (shouldRefresh()) {
//         refreshAuthorization();
//         }
//         return authorizationHeader;
//     }

//     private boolean shouldRefresh() {
//         return authorizationHeader == null
//             || expiration == null
//             || expiration.isBefore(ZonedDateTime.now().plusMinutes(5));
//     }

//     @Override
//     public void refreshAuthorization() {
//         log.info("Refreshing authorization.");
//         if (expiration != null && expiration.isAfter(ZonedDateTime.now().plusHours(1))) {
//         log.warn("Authentication token is not expired.");
//         return;
//         }

//         checkCredentialsNotEmpty();

//         AuthenticationRequest request = AuthenticationRequest.builder()
//             .email(serviceAuthenticationConfiguration.getEmail())
//             .password(serviceAuthenticationConfiguration.getPassword())
//             .build();

//         ResponseEntity<String> res = accountsClient.login(request);
//         String body = res.getBody();
//         if (body == null) {
//         log.error("Response from authentication was null. "
//                         + "Unable to execute service calls requiring authentication.");
//         throw new AuthenticationFailureException(res);
//         }

//         try {
//         AuthenticationResponse auth = objectMapper.readValue(body, AuthenticationResponse.class);
//         synchronized (this) {
//             authorizationHeader = auth.getToken();
//             expiration = Instant.ofEpochMilli(auth.getExpiresAt()).atZone(ZoneId.of("UTC"));
//         }
//         } catch (IOException ex) {
//         log.error("Unable to parse Authorization Response.");
//         throw new AccountsClientException(ex);
//         }
//     }

//     private void checkCredentialsNotEmpty() {
//         String email = serviceAuthenticationConfiguration.getEmail();
//         String password = serviceAuthenticationConfiguration.getPassword();

//         if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
//         log.error("Empty credentials. Cannot authenticate.");
//         throw new AuthenticationFailureException("Empty credentials.");
//         }
//     }
// }
