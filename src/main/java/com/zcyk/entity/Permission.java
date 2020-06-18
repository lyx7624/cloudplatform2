package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "sys_permission")
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class Permission {

	private static final long serialVersionUID = 6180869216498363919L;

	private Long parentId;
	private String name;
	private String css;
	private String href;
	private Integer type;
	private String permission;
	private Integer sort;






}
