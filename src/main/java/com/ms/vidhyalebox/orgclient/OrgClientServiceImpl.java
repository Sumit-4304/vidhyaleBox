package com.ms.vidhyalebox.orgclient;

import com.ms.shared.api.auth.OrgSignupRequestDTO;
import com.ms.shared.api.generic.GenericDTO;
import com.ms.shared.util.util.bl.GenericService;
import com.ms.shared.util.util.bl.IMapperNormal;
import com.ms.shared.util.util.domain.GenericEntity;
import com.ms.vidhyalebox.role.RoleEntity;
import com.ms.vidhyalebox.role.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrgClientServiceImpl extends GenericService<GenericEntity, Long> implements IOrgClientService {


    private final IOrgClientRepo orgClientRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrgClientMapper orgClientMapper;

    private final RoleRepo roleRepo;

    @Autowired
    public OrgClientServiceImpl(IOrgClientRepo orgClientRepository,
                                PasswordEncoder passwordEncoder, OrgClientMapper orgClientMapper, RoleRepo roleRepo) {
        this.orgClientRepository = orgClientRepository;
        this.passwordEncoder = passwordEncoder;
        this.orgClientMapper = orgClientMapper;
        this.roleRepo = roleRepo;
    }


    public GenericDTO signup(OrgSignupRequestDTO orgSignupRequestDTO) {
        // Encode the password
        orgSignupRequestDTO.setPassword(passwordEncoder.encode(orgSignupRequestDTO.getPassword()));
        // Fetch the RoleEntity by role name (e.g., "ROLE_SCHOOL_ADMIN")
        RoleEntity role = roleRepo.findByName(orgSignupRequestDTO.getRole())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role specified"));

        OrgClientEntity orgClientEntity = (OrgClientEntity) orgClientMapper.dtoToEntity(orgSignupRequestDTO);

        // Set the role in the OrgClientEntity
        orgClientEntity.setRoleEntity(role);

        // Save the OrgClientEntity with the assigned role
        OrgClientEntity saveEntity = orgClientRepository.save(orgClientEntity);
        GenericDTO genericDTO = orgClientMapper.entityToDto(saveEntity);
        return genericDTO;
    }

    public boolean isEmailAlreadyExist(final String emailAddress) {
        return orgClientRepository.existsByEmailAddress(emailAddress);
    }

    public boolean isMobileNumberExist(final String phoneNumber) {
        return orgClientRepository.existsByPhoneNumber(phoneNumber);
    }

    public void logout() {
        // Handle logout if needed (e.g., invalidate tokens on client-side).
    }


    @Override
    public JpaRepository getRepo() {
        return orgClientRepository;
    }

    @Override
    public IMapperNormal getMapper() {
        return orgClientMapper;
    }
}
