package com.ms.vidhyalebox.auth;

import com.ms.shared.api.auth.*;
import com.ms.shared.api.generic.GenericDTO;
import com.ms.shared.api.generic.GenericResponse;
import com.ms.shared.api.generic.ModalDTO;
import com.ms.shared.api.generic.Notification;
import com.ms.shared.util.util.FatalException;
import com.ms.vidhyalebox.orgclient.IOrgClientService;
import com.ms.vidhyalebox.parent.IParentService;
import com.ms.vidhyalebox.staff.IStaffService;
import com.ms.vidhyalebox.teacher.ITeacherService;
import com.ms.vidhyalebox.user.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {



    private final AuthenticationManager _authenticationManager;
    private final JwtTokenProvider _jwtTokenProvider;

    private final IOrgClientService _iOrgClientService;
    private final IStaffService _iStaffService;
    private final IParentService _iParentService;
    private final ITeacherService _iTeacherService;
    private final IUserService _iUserService;
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, IOrgClientService iOrgClientService, IStaffService iStaffService, IParentService iParentService, ITeacherService iTeacherService, IUserService iUserService) {
        _authenticationManager = authenticationManager;
        _jwtTokenProvider = jwtTokenProvider;
        _iOrgClientService = iOrgClientService;
        _iStaffService = iStaffService;
        _iParentService = iParentService;
        _iTeacherService = iTeacherService;
        _iUserService = iUserService;
    }


    @PostMapping("/org-signup")
    public GenericResponse registerOrg(@Valid @RequestBody OrgSignupRequestDTO orgSignupRequestDTO) {

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



    @PostMapping("/staff-signup")
    public GenericResponse registerStaff(@Valid @RequestBody StaffSignupRequestDTO staffSignupRequestDTO) {

        List<Notification> notifications = new ArrayList<>();

        if (_iStaffService.isEmailAlreadyExist(staffSignupRequestDTO.getEmailAddress())) {
            Notification notification = new Notification();
            notification.setNoificationCode("401");
            notification.setNotificationDescription("User's email address already exists");
            notifications.add(notification);
        }

        if (_iStaffService.isMobileNumberExist(staffSignupRequestDTO.getIsdCode().concat(staffSignupRequestDTO.getMobileNumber()))) {
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

        GenericDTO genericDTO = _iStaffService.signup(staffSignupRequestDTO);

        List<GenericDTO> genericDTOs = new ArrayList<>();
        genericDTOs.add(genericDTO);

        ModalDTO modalDTO = new ModalDTO();
        modalDTO.setData(genericDTOs);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setCode(HttpStatus.OK.getReasonPhrase());
        genericResponse.setModalDTO(modalDTO);

        return genericResponse;
    }

    @PostMapping("/parent-signup")
    public GenericResponse registerParent(@Valid @RequestBody ParentSignupRequestDTO parentSignupRequestDTO) {

        List<Notification> notifications = new ArrayList<>();

        if (_iParentService.isEmailAlreadyExist(parentSignupRequestDTO.getEmailAddress())) {
            Notification notification = new Notification();
            notification.setNoificationCode("401");
            notification.setNotificationDescription("User's email address already exists");
            notifications.add(notification);
        }

        if (_iParentService.isMobileNumberExist(parentSignupRequestDTO.getIsdCode().concat(parentSignupRequestDTO.getMobileNumber()))) {
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

        GenericDTO genericDTO = _iParentService.signup(parentSignupRequestDTO);

        List<GenericDTO> genericDTOs = new ArrayList<>();
        genericDTOs.add(genericDTO);

        ModalDTO modalDTO = new ModalDTO();
        modalDTO.setData(genericDTOs);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setCode(HttpStatus.OK.getReasonPhrase());
        genericResponse.setModalDTO(modalDTO);

        return genericResponse;
    }

    @PostMapping("/teacher-signup")
    public GenericResponse registerTeacher(@Valid @RequestBody TeacherSignupRequestDTO teacherSignupRequestDTO) {

        List<Notification> notifications = new ArrayList<>();

        if (_iTeacherService.isEmailAlreadyExist(teacherSignupRequestDTO.getEmailAddress())) {
            Notification notification = new Notification();
            notification.setNoificationCode("401");
            notification.setNotificationDescription("User's email address already exists");
            notifications.add(notification);
        }

        if (_iTeacherService.isMobileNumberExist(teacherSignupRequestDTO.getIsdCode().concat(teacherSignupRequestDTO.getMobileNumber()))) {
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

        GenericDTO genericDTO = _iTeacherService.signup(teacherSignupRequestDTO);

        List<GenericDTO> genericDTOs = new ArrayList<>();
        genericDTOs.add(genericDTO);

        ModalDTO modalDTO = new ModalDTO();
        modalDTO.setData(genericDTOs);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setCode(HttpStatus.OK.getReasonPhrase());
        genericResponse.setModalDTO(modalDTO);

        return genericResponse;
    }

    @PostMapping("/user-signup")
    public GenericResponse registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {

        List<Notification> notifications = new ArrayList<>();

      /*  if (_iUserService.isEmailAlreadyExist(signupRequestDTO.getEmailAddress())) {
            Notification notification = new Notification();
            notification.setNoificationCode("401");
            notification.setNotificationDescription("User's email address already exists");
            notifications.add(notification);
        }*/

        if (_iUserService.isMobileNumberExist(signupRequestDTO.getIsdCode().concat(signupRequestDTO.getMobileNumber()))) {
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

        GenericDTO genericDTO = _iUserService.signup(signupRequestDTO);

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
