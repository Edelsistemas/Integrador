package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
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
public class RestoInterProduct extends Product {

  private final int GROUP_CODE = 1;
  private String codigoEdelflex; // U_SEIDORAR_ARTICULO_EDE_2
  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemTipo; // U_SEI_Tipo
  private String itemMarca; // U_SEI_Marca
  private String itemModelo; // U_SEI_Modelo

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
    request.put("U_SEIDORAR_REVISION", getRevision());
    request.put("U_SEIDORAR_ESTADO", "60");
    request.put("U_SEIDORAR_ARTICULO_EDE_2", codigoEdelflex);
    //request.put("U_SEI_ITEMPROV", codigoProveedor); TODO: NO ESTA EN LA INSTANCIA
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Modelo", itemModelo);
    return request;
  }

  @Override
  protected Map<String, Object> getCreateRequest() {
    Map<String, Object> request = new HashMap<>();
    request.put("ItemCode", getProduct());
    request.put("ItemName", getName());
    request.put("ItemsGroupCode", GROUP_CODE);
    request.put("U_SEIDORAR_REVISION", getRevision());
    request.put("U_SEIDORAR_ESTADO", "60"); // TODO:
    request.put("U_SEIDORAR_ARTICULO_EDE_2", codigoEdelflex);
    //request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Modelo", itemModelo);
    return request;
  }

  public static RestoInterProduct create(ResultSet rs) throws SQLException {
    return RestoInterProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .itemTipo(rs.getString("Item_Tipo"))
        .itemMarca(rs.getString("Item_Marca"))
        .itemModelo(rs.getString("Item_Modelo"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }
}
