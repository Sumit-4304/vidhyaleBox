package com.ms.vidhyalebox.parent;

import com.ms.shared.api.auth.ParentSignupRequestDTO;
import com.ms.shared.api.auth.ParentSignupResponseDTO;
import com.ms.shared.api.generic.GenericDTO;
import com.ms.shared.util.util.bl.IMapperNormal;
import com.ms.shared.util.util.domain.GenericEntity;
import org.springframework.stereotype.Service;

@Service
public class ParentMapperNormal implements IMapperNormal {

@Override
    public GenericEntity dtoToEntity(GenericDTO genericDto, GenericEntity genericEntity) {
        ParentEntity entity = genericEntity == null ? new ParentEntity() : (ParentEntity) genericEntity;

        ParentSignupRequestDTO signupRequestDTO = (ParentSignupRequestDTO) genericDto;

        entity.setFirstName(signupRequestDTO.getFirstname());
        entity.setLastName(signupRequestDTO.getLastname());
        entity.setPassword(signupRequestDTO.getPassword() == null ? entity.getPassword() : signupRequestDTO.getPassword());
        entity.setEmail(signupRequestDTO.getEmailAddress());
        entity.setMobileNumber(signupRequestDTO.getMobileNumber().concat(signupRequestDTO.getIsdCode()));
        entity.setAddress(signupRequestDTO.getAddress());
        // Setting temporary values for account status
        entity.setAccountNonLocked(true);
        entity.setAccountNonExpired(true);
        entity.setCredentialsNonExpired(true);

        return entity;
    }

    @Override
    public GenericDTO entityToDto(GenericEntity genericEntity) {
        ParentEntity clientEntity = (ParentEntity) genericEntity;

        ParentSignupResponseDTO signupResponseDTO = new ParentSignupResponseDTO();
        signupResponseDTO.setName(clientEntity.getFirstName().concat(" ").concat(clientEntity.getLastName()));
        signupResponseDTO.setEmailAddress(clientEntity.getEmail());
        signupResponseDTO.setMobileNumber(clientEntity.getMobileNumber());
        signupResponseDTO.setIsActive(clientEntity.isActive());
        signupResponseDTO.setAccountNonExpired(clientEntity.isAccountNonExpired()); // If email verification logic is available, use it
        signupResponseDTO.setAccountNonLocked(clientEntity.isAccountNonLocked()); // If phone verification logic is available, use it
        signupResponseDTO.setOrgUniqId(clientEntity.getOrgUniqId()); // Set default or as per actual logic

        return signupResponseDTO;
    }
}
