package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ValvulasProduct extends Product {

  private String codigoEdelflex; // U_SEIDORAR_ARTICULO_EDE_2
  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemMarca; // U_SEI_Marca
  private String itemModelo; // U_SEI_Modelo
  private String itemTipo; // U_SEI_Tipo
  private String materialJuntas; // U_SEI_MatJun
  private String diametro; // U_SEI_Diametro
  private String itemActuacion; // U_SEI_Actuacion

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder()
        .request(new HashMap<>()) // TODO:
        .code(getProduct())
        .build();
  }

  @Override
  protected Map<String, Object> getUpdateRequest() {
    Map<String, Object> request = new HashMap<>();
    // request.put("ItemCode", product);
    request.put("ItemName", getName());
    request.put("ItemsGroupCode", "TODO");
    request.put("U_SEIDORAR_REVISION", getRevision());
    request.put("U_SEIDORAR_ESTADO", "TODO");
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEIDORAR_ARTICULO_EDE_2", codigoEdelflex);
    request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Diametro", diametro);
    request.put("U_SEI_Actuacion", itemActuacion);
    return request;
  }

  @Override
  protected Map<String, Object> getCreateRequest() {
    Map<String, Object> request = new HashMap<>();
    request.put("ItemCode", getProduct());
    request.put("ItemName", getName());
    request.put("ItemsGroupCode", "TODO");
    request.put("U_SEIDORAR_REVISION", getRevision());
    request.put("U_SEIDORAR_ESTADO", "TODO");
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEIDORAR_ARTICULO_EDE_2", codigoEdelflex);
    request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Diametro", diametro);
    request.put("U_SEI_Actuacion", itemActuacion);
    return request;
  }

  public static ValvulasProduct create(ResultSet rs) throws SQLException {
    return ValvulasProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .itemMarca(rs.getString("Item_Marca"))
        .itemTipo(rs.getString("Item_Tipo"))
        .diametro(rs.getString("Item_Diametro"))
        .itemActuacion(rs.getString("Item_Actuacion"))
        // .materialJuntas(rs.getString("")) FIXME: DEFINIR
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }
}
