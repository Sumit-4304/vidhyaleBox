package com.ms.vidhyalebox.staff;

import com.ms.shared.api.auth.SignupRequestDTO;
import com.ms.shared.util.util.bl.IGenericService;
import com.ms.shared.util.util.domain.GenericEntity;
import com.ms.shared.util.util.rest.GenericController;
import com.ms.vidhyalebox.user.UserEntity;
import com.ms.vidhyalebox.user.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Validated
@RequestMapping("/staff")
public class StaffController extends GenericController<SignupRequestDTO, Long> {

	private final UserServiceImpl _userService;


	public StaffController(final UserServiceImpl userService
                          ) {
		_userService = userService;
	}

	@Override
	public IGenericService<GenericEntity, Long> getService() {
		_userService.setAuthToken(getAuthToken());
		return _userService;
	}


/*	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signup(@RequestBody UserEntity userEntity) {
		UserEntity savedUser = _userService.signup(userEntity);
		return ResponseEntity.ok(savedUser);
	}*/

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String phoneNumber, @RequestParam String password) {
		String token = _userService.login(phoneNumber, password);
		return ResponseEntity.ok(token);
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		_userService.logout();
		return ResponseEntity.ok().build();
	}


//	@PostMapping("/logout")
//	@RolesAllowed({"CLIENT", "ADMIN"})
//	public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token, @RequestBody LogoutRequestDTO logoutRequestDTO) throws FatalException {
//
//		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String deviceId = logoutRequestDTO.getDeviceInfoDTO().getDeviceId();
//
//		UserDeviceDTO userDeviceDTO = _userDeviceService.getUserDevice(userPrincipal.getId(), deviceId);
//		userDeviceDTO.setSessionLogout(true);
//		_userDeviceService.update(userDeviceDTO);
//		_refreshTokenService.deactivateRefreshToken(userDeviceDTO.getRefreshToken(), userPrincipal.getId(), deviceId);
//
//		SignupRequestDTO userRequestDTO = (SignupRequestDTO) _userService.findByEmailAddress(userPrincipal.getUsername());
//		_userService.invalidateSession(userRequestDTO.getEmail(), token);
//		return ResponseEntity.ok(new LogoutResponseDTO(true, "User has successfully logged out from the system!"));
//	}


	
//	@PutMapping("/password")
//	public ResponseEntity<?> changePassword(@CurrentUser UserPrincipal currentUser, @RequestBody ChangePasswordRequestDTO changePasswordRequest) {
//
//		//Reset the password
//		boolean isPasswordUpdated = _userService.changePassword(currentUser.getId(), changePasswordRequest);
//
//		if (isPasswordUpdated) {
//			_userService.invalidateUsersAllSession(currentUser.getId()); //invalidate session
//		}
//
//		return ResponseEntity.ok(new ChangePasswordResponseDTO(isPasswordUpdated));
//	}

/*	@GetMapping("/detail/{id}") //TODO
	public GenericResponse getUserById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
		GenericDTO genericDTO = _userService.getUserById(id);
        List<GenericDTO> genericDTOs = new ArrayList<>();
        genericDTOs.add(genericDTO);
        return getResponse(genericDTOs);
	}
	
	@PostMapping(value = "/send/otp")
	public  ResponseEntity<SendOTPResponseDTO> sendVerificationOTP(@RequestHeader("Authorization") String token, @Valid @RequestBody SendOTPRequestDTO userVerificationRequest) {

		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final Long verificationRequestId = _userService.processSendOtpRequest(currentUser.getId(), userVerificationRequest.getTarget(), userVerificationRequest.getType(), userVerificationRequest.getPurpose());
		boolean isVerificationRequestProcessed = Objects.nonNull(verificationRequestId);

		SendOTPResponseDTO userVerificationResponseDTO = new SendOTPResponseDTO(userVerificationRequest.getTarget(), false, isVerificationRequestProcessed);
		userVerificationResponseDTO.setId(verificationRequestId);
		
		return ResponseEntity.ok(userVerificationResponseDTO);
	}
	
	
	@PostMapping(value = "/verify/otp")
	public  ResponseEntity<SendOTPResponseDTO> verifyOTP(@RequestHeader("Authorization") String token, @Valid @RequestBody VerifyOTPRequestDTO verifyOTPRequestDTO) {
		final boolean isVerified = _userService.processOTPVerification(verifyOTPRequestDTO);
		SendOTPResponseDTO userVerificationResponseDTO = new SendOTPResponseDTO(verifyOTPRequestDTO.getTarget(), isVerified, true);
		userVerificationResponseDTO.setId(verifyOTPRequestDTO.getId());
		return ResponseEntity.ok(userVerificationResponseDTO);
	}*/

}
