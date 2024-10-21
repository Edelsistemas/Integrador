package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
  private String status;
  private String codigoEdelflex;

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
    Map<String, Object> request = new HashMap<>();
    request.put("ItemName", getName());
    request.put("ItemsGroupCode", getGroupCode());
    request.put("U_SEIDORAR_REVISION", getRevision());
    request.put("U_SEIDORAR_ESTADO", getStatus()); // TODO:
    request.put("U_SEIDORAR_ARTICULO_EDE_2", getCodigoEdelflex());
    request.put("InventoryUOM", getUoM());
    // CREATE
    if (getRevision().equals("A")) {
      request.put("ItemCode", getProduct());
      populateCreateRequest(request);
    } else { // UPDATE
      populateUpdateRequest(request);
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

  protected abstract void populateUpdateRequest(Map<String, Object> request);

  protected abstract void populateCreateRequest(Map<String, Object> request);

  protected abstract int getGroupCode();

  protected abstract String getUoM();
}
