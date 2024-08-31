package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
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
public class InterCalorProduct extends Product {

  private String name;
  private String product;
  private String revision;
  private String subtype;
  private String cantidadDePlacas;
  private String cantidadDeSecciones;
  private String codigoERPViejo;
  private String codigoFabricante;
  private String corrugacion;
  private String diametroDeConexionLadoProducto;
  private String diametroDeConexionLadoServicio;
  private String fabricable;
  private String importado;
  private String espesorPlacas;
  private String longitudEquipo;
  private String materialBastidor;
  private String materialJuntas;
  private String materialPlacas;
  private String modeloBastidor;
  private String normaConexionLadoProducto;
  private String normaConexionLadoServicio;
  private String tamanio;
  private String tipoJunta;
  private String tipoConexionLadoProducto;
  private String tipoConexionLadoServicio;
  private String ubicacionConexiones;

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder()
        .request(new HashMap<>()) // TODO:
        .code(product)
        .build();
  }

  @Override
  public Map<String, Object> createRequest() {
    Map<String, Object> request = new HashMap<>();
    // CREATE
    if (revision.equals("A")) {
      return getCreateRequest();
    } else { // UPDATE
      return getUpdateRequest();
    }
  }

  private Map<String, Object> getUpdateRequest() {
    Map<String, Object> request = getValues();
    return request;
  }

  private Map<String, Object> getCreateRequest() {
    Map<String, Object> request = getValues();
    return request;
  }

  private Map<String, Object> getValues() {
    Map<String, Object> request = new HashMap<>();
    request.put("name", "");
    request.put("product", "");
    request.put("revision", "");
    request.put("subtype", "");
    request.put("cantidadDePlacas", "");
    request.put("cantidadDeSecciones", "");
    request.put("codigoERPViejo", "");
    request.put("codigoFabricante", "");
    request.put("corrugacion", "");
    request.put("diametroDeConexionLadoProducto", "");
    request.put("diametroDeConexionLadoServicio", "");
    request.put("fabricable", "");
    request.put("importado", "");
    request.put("espesorPlacas", "");
    request.put("longitudEquipo", "");
    request.put("materialBastidor", "");
    request.put("materialJuntas", "");
    request.put("materialPlacas", "");
    request.put("modeloBastidor", "");
    request.put("normaConexionLadoProducto", "");
    request.put("normaConexionLadoServicio", "");
    request.put("tamanio", "");
    request.put("tipoJunta", "");
    request.put("tipoConexionLadoProducto", "");
    request.put("tipoConexionLadoServicio", "");
    request.put("ubicacionConexiones", "");
    return request;
  }
}
