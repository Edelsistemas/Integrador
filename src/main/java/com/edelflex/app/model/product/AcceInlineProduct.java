package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AcceInlineProduct extends Product {

  private String id;
  private String name;
  private String product;
  private String revision;

  private String codigoEdelflex; // U_SEIDORAR_ARTICULO_EDE_2
  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemMarca; // U_SEI_Marca
  private String itemTipo; // U_SEI_Tipo
  private String itemEquipo; // U_SEI_Equipo

  /*
    U_SEIDORAR_ARTICULO_EDE_2	Código Edelflex
  U_SEI_ITEMPROV	Código proveedor
  U_SEI_Marca	Marca
  U_SEI_Tipo	Tipo
  U_SEI_Modelo	Modelo
  U_SEI_Equipo	Equipo
  U_SEI_Variable	Variable
  U_SEI_Tamanho	Tamaño
  U_SEI_ModBas	Modelo Bastidor
  U_SEI_Corruga	Corrugación
  U_SEI_MatPlac	Material placas
  U_SEI_MatJun	Material juntas
  U_SEI_CanSec	Cant. Secciones
  U_SEI_Diametro	Diámetro
  U_SEI_Actuacion	Actuación
  U_SEI_Familia	Familia
  U_SEI_DiamSup	Diámetro superior
  U_SEI_DiamInf	Diámetro inferior
  U_SEI_Cuerpo	Cuerpo
  U_SEI_Conex	Conexiones
   */

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
