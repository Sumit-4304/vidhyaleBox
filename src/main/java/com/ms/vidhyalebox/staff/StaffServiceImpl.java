package com.ms.vidhyalebox.staff;

import com.ms.shared.util.util.bl.GenericService;
import com.ms.shared.util.util.bl.IMapperNormal;
import com.ms.shared.util.util.domain.GenericEntity;
import com.ms.vidhyalebox.auth.JwtTokenProvider;
import com.ms.vidhyalebox.user.IUserRepo;
import com.ms.vidhyalebox.user.IUserService;
import com.ms.vidhyalebox.user.UserEntity;
import com.ms.vidhyalebox.user.UserMapperNormal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl extends GenericService<GenericEntity, Long> implements IStaffService {

	private final IStaffRepo _iStaffRepo;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	private final StaffMapperNormal _staffMapperNormal;

    public StaffServiceImpl(IStaffRepo iStaffRepo, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserMapperNormal userMapperNormal, StaffMapperNormal staffMapperNormal) {
        _iStaffRepo = iStaffRepo;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        _staffMapperNormal = staffMapperNormal;

    }

//    public UserEntity signup(UserEntity userEntity) {
//		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//		return _iStaffRepo.save(userEntity);
//	}

	public String login(String phoneNumber, String password) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(phoneNumber, password)
		);
		return jwtTokenProvider.generateToken(phoneNumber);
	}


	public boolean isEmailAlreadyExist(final String emailAddress) {
		return _iStaffRepo.existsByEmailAddress(emailAddress);
	}

	public boolean isMobileNumberExist(final String phoneNumber) {
		return _iStaffRepo.existsByMobileNumber(phoneNumber);
	}
	public void logout() {
		// Handle logout if needed (e.g., invalidate tokens on client-side).
	}

	public UserDetails loadUserByUsername(String username) {

        return null;
    }

	@Override
	public JpaRepository getRepo() {
		return _iStaffRepo;
	}

	@Override
	public IMapperNormal getMapper() {
		return _staffMapperNormal;
	}
}
