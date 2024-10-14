package com.ms.vidhyalebox.user;

import com.ms.shared.util.util.bl.GenericService;
import com.ms.shared.util.util.bl.IMapperNormal;
import com.ms.shared.util.util.domain.GenericEntity;
import com.ms.vidhyalebox.auth.JwtTokenProvider;
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

    public UserServiceImpl(IUserRepo userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserMapperNormal userMapperNormal) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userMapperNormal = userMapperNormal;
    }

    public UserEntity signup(UserEntity userEntity) {
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		return userRepository.save(userEntity);
	}

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
