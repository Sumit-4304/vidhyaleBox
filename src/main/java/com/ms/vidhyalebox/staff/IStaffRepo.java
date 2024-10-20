package com.ms.vidhyalebox.staff;


import com.ms.shared.util.util.repo.GenericRepo;
import com.ms.vidhyalebox.orgclient.OrgClientEntity;
import com.ms.vidhyalebox.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IStaffRepo extends GenericRepo<StaffEntity, Long> {

	StaffEntity findByMobileNumber(final String mobileNumber);
	Boolean existsByEmail(final String email);
	boolean existsByMobileNumber(final String MobileNumber);

	@Query("SELECT s FROM StaffEntity s WHERE (:email IS NULL OR s.email = :email) AND (:mobileNumber IS NULL OR s.mobileNumber = :mobileNumber)")
	Optional<StaffEntity> findByEmailOrMobileNumber(@Param("email") String email, @Param("mobileNumber") String mobileNumber);

//	Optional<UserEntity> findByReferralCodeOrPhoneNumberOrEmailAddress(final String referralCode, final String phoneNumber, final String emailAddress);
//	Optional<UserEntity> findByReferralCode(final String referralCode);
//	Optional<UserEntity> findByEmailAddressOrPhoneNumber(final String email, final String phoneNumber);
//	Optional<UserEntity> findByFederatedUserIdOrEmailAddress(String id, String emailAddress);
	
//	@Query(value = "SELECT * FROM users WHERE RIGHT(phone_number, 10) = :phoneNumber " +
//            "OR email_address = :email", nativeQuery = true)
//Optional<UserEntity> findByPhoneNumberOrEmailAddress(@Param("phoneNumber") String phoneNumber,
//                                                  @Param("email") String email);
}



