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

  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemTipo; // U_SEI_Tipo
  private String itemMarca; // U_SEI_Marca
  private String itemModelo; // U_SEI_Modelo

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder().request(new HashMap<>()).code(getProduct()).build();
  }

  @Override
  protected void populateUpdateRequest(Map<String, Object> request) {
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Modelo", itemModelo);
    request.put("U_SEI_ITEMPROV", codigoProveedor);
  }

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Modelo", itemModelo);
    request.put("U_SEI_ITEMPROV", codigoProveedor);
  }

  public static RestoInterProduct create(ResultSet rs) throws SQLException {
    return RestoInterProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .status(rs.getString("Estado_TC"))
        .itemTipo(rs.getString("Item_Tipo"))
        .itemMarca(rs.getString("Item_Marca"))
        .itemModelo(rs.getString("Item_Modelo"))
            .groupCode(rs.getInt("GroupCode"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  //return 100;

  @Override
  protected String getUoM() {
    return "UN";
  }
}
