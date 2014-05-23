package com.unity.recruitment.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Address implements Serializable{

	private String city;
	private String street;
	private String numberOfLocal;
	private String postcode;

}
