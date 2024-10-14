package com.ms.vidhyalebox.orgclient;

import com.ms.shared.util.util.repo.GenericRepo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrgClientRepo extends GenericRepo<OrgClientEntity,Long> {
    Optional<OrgClientEntity> findByEmailAddress(String email);

    Boolean existsByEmailAddress(final String email);

    boolean existsByPhoneNumber(final String phoneNumber);
}
