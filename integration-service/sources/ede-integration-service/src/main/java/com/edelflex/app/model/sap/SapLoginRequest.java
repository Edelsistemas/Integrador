package com.edelflex.app.model.sap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SapLoginRequest {

	@JsonProperty("UserName")
	String user;
	@JsonProperty("Password")
	String password;
	@JsonProperty("CompanyDB")
	String company;
}
