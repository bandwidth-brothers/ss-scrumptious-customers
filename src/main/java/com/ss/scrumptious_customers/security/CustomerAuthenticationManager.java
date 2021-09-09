package com.ss.scrumptious_customers.security;

import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomerAuthenticationManager {

  // public boolean customerEmailMatches(Authentication authentication, String email) {
  //   try {
  //     JwtPrincipal principal = (JwtPrincipal) authentication.getPrincipal();
  //     return principal.getEmail().equals(email);
  //   } catch (ClassCastException ex) {
  //     return false;
  //   }
  // }

  public boolean customerIdMatches(Authentication authentication, UUID id) {
    try {
      JwtPrincipalModel principal = (JwtPrincipalModel) authentication.getPrincipal();
      return principal.getUserId().equals(id);
    } catch (ClassCastException ex) {
      return false;
    }
  }

//   public boolean customerIdMatches(Authentication authentication,
//                                    DeleteAccountDto deleteAccountDto) {
//     try {
//       JwtPrincipal principal = (JwtPrincipal) authentication.getPrincipal();
//       return principal.getUserId().equals(deleteAccountDto.getId());
//     } catch (ClassCastException ex) {
//       return false;
//     }
//   }
}