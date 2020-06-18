package com.zcyk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_token")
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class TokenModel {

	private static final long serialVersionUID = 4566334160572911795L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String id;
	private Date create_time = new Date();
	private Date update_time = new Date();
	/**
	 * 过期时间
	 */
	private Date expire_time;
	/**
	 * LoginUser的json串
	 */
	private String val;



}



