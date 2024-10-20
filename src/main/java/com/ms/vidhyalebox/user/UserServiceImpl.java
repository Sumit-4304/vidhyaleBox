package com.ms.vidhyalebox.user;

import com.ms.shared.api.auth.ParentSignupRequestDTO;
import com.ms.shared.api.auth.SignupRequestDTO;
import com.ms.shared.api.generic.GenericDTO;
import com.ms.shared.util.util.bl.GenericService;
import com.ms.shared.util.util.bl.IMapperNormal;
import com.ms.shared.util.util.domain.GenericEntity;
import com.ms.vidhyalebox.auth.JwtTokenProvider;
import com.ms.vidhyalebox.parent.ParentEntity;
import com.ms.vidhyalebox.role.RoleEntity;
import com.ms.vidhyalebox.role.RoleRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericService<GenericEntity, Long> implements IUserService  {

	private final IUserRepo userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	private final UserMapperNormal userMapperNormal;
	private final RoleRepo roleRepo;

    public UserServiceImpl(IUserRepo userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserMapperNormal userMapperNormal, RoleRepo roleRepo) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userMapperNormal = userMapperNormal;
		this.roleRepo = roleRepo;
    }

	public GenericDTO signup(SignupRequestDTO signupRequestDTO) {
		// Encode the password
		signupRequestDTO.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
		// Fetch the RoleEntity by role name (e.g., "ROLE_SCHOOL_ADMIN")
		RoleEntity role = roleRepo.findByName(signupRequestDTO.getRole())
				.orElseThrow(() -> new IllegalArgumentException("Invalid role specified"));

		UserEntity userEntity = (UserEntity) userMapperNormal.dtoToEntity(signupRequestDTO);

		// Set the role in the OrgClientEntity
		userEntity.setRoleEntity(role);

		// Save the OrgClientEntity with the assigned role
		UserEntity saveEntity = userRepository.save(userEntity);
		GenericDTO genericDTO = userMapperNormal.entityToDto(saveEntity);
		return genericDTO;
	}

	public boolean isMobileNumberExist(final String mobileNumber) {
		return userRepository.existsByMobileNumber(mobileNumber);
	}

   /* public UserEntity signup(UserEntity userEntity) {
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		return userRepository.save(userEntity);
	}*/

	public String login(String phoneNumber, String password) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(phoneNumber, password)
		);
		return jwtTokenProvider.generateToken(phoneNumber);
	}

	public void logout() {
		// Handle logout if needed (e.g., invalidate tokens on client-side).
	}

	public UserDetails loadUserByUsername(String username) {

        return null;
    }

	@Override
	public JpaRepository getRepo() {
		return userRepository;
	}

	@Override
	public IMapperNormal getMapper() {
		return userMapperNormal;
	}
}
