package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
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
  private int groupitem;

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
  private String cantSecciones;
  private String diametro;
  private String actuacion;
  private String familia;
  private String diametroSuperior;
  private String diametroInferior;
  private String cuerpo;
  private String conexion;
  private String uom;

  public ProductProcessInfo getProcessInfo() {
    return null;
  }

  public Map<String, Object> createRequest() {
    Map<String, Object> request = new HashMap<>();
    request.put("ItemName", getName());
    request.put("ItemsGroupCode", getGroupitem());
    request.put("U_SEIDORAR_REVISION", getRevision());
    request.put("U_SEIDORAR_ESTADO", getStatus());
    request.put("U_SEIDORAR_ARTICULO_EDE_2", getEdelflex());
    request.put("InventoryUOM", uom);
    // CREATE
    if (getRevision().equals("A")) {
      request.put("ItemCode", getProduct());
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
    request.put("U_SEI_CanSec", cantSecciones);
    request.put("U_SEI_Corruga", corrugacion);
    request.put("U_SEI_ModBas", modeloBastidor);
    request.put("U_SEI_Tamanho", tamanio);
  }

  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_Diametro", diametro);
    request.put("U_SEI_Equipo", equipo);
    request.put("U_SEI_Marca", marca);
    request.put("U_SEI_Tipo", tipo);
    request.put("U_SEI_ITEMPROV", proveedor);
    request.put("U_SEI_MatPlac", materialPlacas);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_CanSec", cantSecciones);
    request.put("U_SEI_Corruga", corrugacion);
    request.put("U_SEI_ModBas", modeloBastidor);
    request.put("U_SEI_Tamanho", tamanio);
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
