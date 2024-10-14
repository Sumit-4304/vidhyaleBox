package com.ms.vidhyalebox.teacher;


import com.ms.shared.util.util.repo.GenericRepo;
import com.ms.vidhyalebox.user.UserEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface ITeacherRepo extends GenericRepo<UserEntity, Long> {
	
	UserEntity findByPhoneNumber(final String email);
	Boolean existsByEmailAddress(final String email);
	boolean existsByPhoneNumber(final String phoneNumber);
//	Optional<UserEntity> findByReferralCodeOrPhoneNumberOrEmailAddress(final String referralCode, final String phoneNumber, final String emailAddress);
//	Optional<UserEntity> findByReferralCode(final String referralCode);
//	Optional<UserEntity> findByEmailAddressOrPhoneNumber(final String email, final String phoneNumber);
//	Optional<UserEntity> findByFederatedUserIdOrEmailAddress(String id, String emailAddress);
	
//	@Query(value = "SELECT * FROM users WHERE RIGHT(phone_number, 10) = :phoneNumber " +
//            "OR email_address = :email", nativeQuery = true)
//Optional<UserEntity> findByPhoneNumberOrEmailAddress(@Param("phoneNumber") String phoneNumber,
//                                                  @Param("email") String email);
}



