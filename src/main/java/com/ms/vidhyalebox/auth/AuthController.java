package com.ms.vidhyalebox.auth;

import com.ms.shared.api.auth.LoginRequestDTO;
import com.ms.shared.api.auth.LoginResponseDTO;
import com.ms.shared.api.auth.OrgSignupRequestDTO;
import com.ms.shared.api.generic.GenericDTO;
import com.ms.shared.api.generic.GenericResponse;
import com.ms.shared.api.generic.ModalDTO;
import com.ms.shared.api.generic.Notification;
import com.ms.shared.util.util.FatalException;
import com.ms.vidhyalebox.orgclient.IOrgClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthController {



    private final AuthenticationManager _authenticationManager;
    private final JwtTokenProvider _jwtTokenProvider;

    private final IOrgClientService _iOrgClientService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, IOrgClientService iOrgClientService) {
        _authenticationManager = authenticationManager;
        _jwtTokenProvider = jwtTokenProvider;
        _iOrgClientService = iOrgClientService;
    }


    @PostMapping("/signup")
    public GenericResponse registerUser(@Valid @RequestBody OrgSignupRequestDTO orgSignupRequestDTO) {

        List<Notification> notifications = new ArrayList<>();

        if (_iOrgClientService.isEmailAlreadyExist(orgSignupRequestDTO.getEmailAddress())) {
            Notification notification = new Notification();
            notification.setNoificationCode("401");
            notification.setNotificationDescription("User's email address already exists");
            notifications.add(notification);
        }

        if (_iOrgClientService.isMobileNumberExist(orgSignupRequestDTO.getIsdCode().concat(orgSignupRequestDTO.getMobileNumber()))) {
            Notification notification = new Notification();
            notification.setNoificationCode("401");
            notification.setNotificationDescription("User's Phone Number already exists");
            notifications.add(notification);
        }

        if (!notifications.isEmpty()) {

            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
            genericResponse.setNotifications(notifications);

            return genericResponse;
        }

        GenericDTO genericDTO = _iOrgClientService.signup(orgSignupRequestDTO);

        List<GenericDTO> genericDTOs = new ArrayList<>();
        genericDTOs.add(genericDTO);

        ModalDTO modalDTO = new ModalDTO();
        modalDTO.setData(genericDTOs);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setCode(HttpStatus.OK.getReasonPhrase());
        genericResponse.setModalDTO(modalDTO);

        return genericResponse;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) throws FatalException {

        Authentication authentication = _authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        if (userDetails.isEnabled()) { // Check whether User is active or not

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String jwtToken = _jwtTokenProvider.generateToken(userPrincipal.getUsername());

            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            return ResponseEntity.ok(new LoginResponseDTO(jwtToken, userDetails.getId(), roles, userDetails.isEnabled(),
                    userDetails.isAccountNonLocked(), userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(), _jwtTokenProvider.getExpiryDuration(), StringUtils.EMPTY));
        }

        LoginResponseDTO loginResponseDTO = LoginResponseDTO.builder().message("User has been deactivated/locked !!").build();
        return ResponseEntity.badRequest().body(loginResponseDTO);
    }
}
