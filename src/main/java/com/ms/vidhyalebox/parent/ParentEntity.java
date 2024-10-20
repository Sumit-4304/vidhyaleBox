package com.ms.vidhyalebox.parent;

import com.ms.shared.util.util.domain.GenericEntity;
import com.ms.vidhyalebox.role.RoleEntity;
import com.ms.vidhyalebox.user.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
@Table(name = "parent")
public class ParentEntity extends GenericEntity {

    @Column(name = "org_uniq_id")
    private String orgUniqId;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "status")
    private String status;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @EqualsAndHashCode.Include
    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "pwd")
    private String password;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserEntity> users;

//	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
//	private UserProfileEntity userProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity roleEntity;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    @Column(name = "is_account_non_expired")
    private boolean isAccountNonExpired;

    @Column(name = "is_credentials_non_expired")
    private boolean isCredentialsNonExpired;

//	@Column(name = "is_email_verified")
//	private boolean isEmailVerified;

    @Column(name = "is_phone_number_verified")
    private boolean isPhoneNumberVerified;

    @Column(name= "federated_user_id")
    private String federatedUserId;

    @Column(name = "identity_provider")
    private String identityProvider;

    @Column(name = "department")
    private String department;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

//	@ToString.Exclude
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	private List<UserPlanSubscriptionEntity> userPlanSubscriptionEntities;
//
//	@PrePersist
//	private void encryptPassword() {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		password = passwordEncoder.encode(password);
//	}
}
