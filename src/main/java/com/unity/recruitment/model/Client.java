package com.unity.recruitment.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "firstName", "surname", "address",
		"valueOfOrderedArticles" })
@ToString
public class Client implements Serializable{

	@Setter
	@Getter
	private long ID;
	@Setter
	@Getter
	private String firstName;
	@Setter
	@Getter
	private String surname;
	@Setter
	@Getter
	@NonNull
	private Address address;
	@Setter
	@Getter
	private double valueOfOrderedArticles;

}
