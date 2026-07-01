package com.edelflex.app.model.sap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SapResponse<T> {

	@JsonProperty("value")
	private List<T> value;

	@JsonProperty("odata.nextLink")
	private String nextLink;

	@JsonProperty("odata.metadata")
	private String metadata;

	@JsonProperty("odata.count")
	private String count;

	public T getUnique() {
		if (value == null)
			return null;
		return value.stream().findFirst().orElse(null);
	}

	public boolean isEmpty(){
		return value == null || value.isEmpty();
	}

	public boolean isNotEmpty(){
		return !isEmpty();
	}
}
