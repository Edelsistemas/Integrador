package com.edelflex.app.model.sap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SapBusinessPartnerVersion {

  public static final String CIGRA_CAMPOS_COLLECTION = "business-partners-sap-cigra-campos-version";
  public static final String MEDANO_COLLECTION = "business-partners-sap-medano-version";

  public enum Action {
    CREATE,
    UPDATE
  }
  private String code;

  private String version;

  private Action action;

  private Map source;
}
