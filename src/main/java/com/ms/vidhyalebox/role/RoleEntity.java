package com.ms.vidhyalebox.role;

import com.ms.shared.util.util.domain.GenericEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
public class RoleEntity extends GenericEntity {
	
	@Column(name = "name")
	private String name;
	
	public RoleEntity() {}

	public RoleEntity(String name) {
		this.name = name;
	}
}