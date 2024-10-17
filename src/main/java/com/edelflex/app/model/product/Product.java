package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Product {

  private long id;
  private String name; // NAME
  private String product; // CODE
  private String revision; // REVISION
  private Action action;

  public enum Action {
    CREATE("Crear"),
    UPDATE("Actualizar");

    private final String label;

    Action(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }

  public abstract ProductProcessInfo getProcessInfo();

  public Map<String, Object> createRequest() {
    Map<String, Object> request;
    // CREATE
    if (getRevision().equals("A")) {
      request = getCreateRequest();
    } else { // UPDATE
      request = getUpdateRequest();
    }
    return clearEmptyValues(request);
  }

  private Map<String, Object> clearEmptyValues(Map<String, Object> request) {
    Map<String, Object> results = new HashMap<>();
    request.forEach(
        (key, value) -> {
          if (value != null) {
            if (value instanceof String) {
              if (Utils.isNotEmpty(value.toString())) {
                results.put(key, value);
              }
            } else {
              results.put(key, value);
            }
          }
        });
    return results;
  }

  protected abstract Map<String, Object> getUpdateRequest();

  protected abstract Map<String, Object> getCreateRequest();
}
