package com.edelflex.app.model.sap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SapGroupMapping {

	public static final String CIGRA_CAMPOS_COLLECTION = "business-partners-sap-cigra-campos-group-mapping";
	public static final String MEDANO_COLLECTION = "business-partners-sap-medano-group-mapping";

	private String source;
	private String target;
	private String bu;
}
