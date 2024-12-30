package com.edelflex.app.model.product;

import com.edelflex.app.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

  private long id;
  private String name;
  private String product;
  private String revision;
  private String status;
  private String edelflex;
  private String groupItem;
  private String productType;

  private String proveedor;
  private String marca;
  private String tipo;
  private String modelo;
  private String equipo;
  private String variable;
  private String tamanio;
  private String modeloBastidor;
  private String corrugacion;
  private String materialPlacas;
  private String materialJuntas;
  private String cantidadSecciones;
  private String diametro;
  private String actuacion;
  private String familia;
  private String diametroSuperior;
  private String diametroInferior;
  private String cuerpo;
  private String conexiones;
  private String uom;
  private Action action;
  private String importado;
  private String fabricado;

  public enum Action {
    UPDATE("Actualizar"), CREATE("Crear");

    Action(String label) {
      this.label = label;
    }

    private final String label;

    public String getLabel() {
      return label;
    }
  }

  public Map<String, Object> createRequest() {
    Map<String, Object> request = new HashMap<>();
    request.put("ItemName", getName());
    request.put("ItemsGroupCode", Integer.parseInt(getGroupItem()));
    request.put("U_SEIDORAR_REVISION", getRevision());
    request.put("U_SEIDORAR_ESTADO", getStatus());
    request.put("U_SEIDORAR_ARTICULO_EDE_2", getEdelflex());
    request.put("InventoryUOM", getUom());
    // CREATE
    if (getAction().equals(Action.CREATE)) {
      request.put("ItemCode", getProduct());
      request.put("Valid", "N");
      populateCreateRequest(request);
    } else { // UPDATE
      populateUpdateRequest(request);
    }
    return clearEmptyValues(request);
  }

  protected void populateUpdateRequest(Map<String, Object> request) {
    request.put("U_SEI_Diametro", diametro);
    request.put("U_SEI_Equipo", equipo);
    request.put("U_SEI_Marca", marca);
    request.put("U_SEI_Tipo", tipo);
    request.put("U_SEI_ITEMPROV", proveedor);
    request.put("U_SEI_MatPlac", materialPlacas);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_CanSec", cantidadSecciones);
    request.put("U_SEI_Corruga", corrugacion);
    request.put("U_SEI_ModBas", modeloBastidor);
    request.put("U_SEI_Tamanho", tamanio);
    request.put("Properties1", getSapBoolean(importado));
    request.put("Properties2", getSapBoolean(fabricado));
  }

  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_Diametro", diametro);
    request.put("U_SEI_Equipo", equipo);
    request.put("U_SEI_Marca", marca);
    request.put("U_SEI_Tipo", tipo);
    request.put("U_SEI_ITEMPROV", proveedor);
    request.put("U_SEI_MatPlac", materialPlacas);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_CanSec", cantidadSecciones);
    request.put("U_SEI_Corruga", corrugacion);
    request.put("U_SEI_ModBas", modeloBastidor);
    request.put("U_SEI_Tamanho", tamanio);
    request.put("Properties1", getSapBoolean(importado));
    request.put("Properties2", getSapBoolean(fabricado));
  }

  private String getSapBoolean(String value ){
    if (Utils.isEmpty(value)){
      return "tNO";
    }
    if (value.trim().equalsIgnoreCase("si")){
      return "tYES";
    }
    return "tNO";
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

}
