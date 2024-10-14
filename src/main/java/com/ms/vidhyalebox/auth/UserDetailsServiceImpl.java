package com.ms.vidhyalebox.auth;

import java.util.Objects;
import java.util.Optional;

import com.ms.vidhyalebox.orgclient.IOrgClientRepo;
import com.ms.vidhyalebox.orgclient.OrgClientEntity;
import com.ms.vidhyalebox.user.IUserRepo;
import com.ms.vidhyalebox.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final IUserRepo _userRepo; // Repository for UserEntity
	private final IOrgClientRepo _IOrgClientRepo; // Repository for OrgClientEntity

	@Autowired
	public UserDetailsServiceImpl(final IUserRepo userRepo, IOrgClientRepo iOrgClientRepo) {
		_userRepo = userRepo;
		_IOrgClientRepo = iOrgClientRepo;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username) {
		// First, check OrgClientEntity
		Optional<OrgClientEntity> orgClientEntityOptional = _IOrgClientRepo.findByEmailAddress(username);
		if (orgClientEntityOptional.isPresent()) {
			return UserPrincipal.build(orgClientEntityOptional.get());
		}

		String processedPhoneNumber = getLastTenDigits(username);
		UserEntity userEntity = _userRepo.findByPhoneNumber(processedPhoneNumber);
		// If found, return UserPrincipal
		if (Objects.nonNull(userEntity)) {
			return UserPrincipal.build(userEntity);
		}

		throw new UsernameNotFoundException("User not found with username: " + username);
	}


	// Utility method to extract the last ten digits from a phone number
	public String getLastTenDigits(String phoneNumber) {
		String digitsOnlyPhoneNumber = phoneNumber.replaceAll("\\D", "");
		return digitsOnlyPhoneNumber.length() > 10 ? digitsOnlyPhoneNumber.substring(digitsOnlyPhoneNumber.length() - 10) : digitsOnlyPhoneNumber;
	}
}
