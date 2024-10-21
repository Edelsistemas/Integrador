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
public class OtrosCompProduct extends Product {

  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemMarca; // U_SEI_Marca
  private String itemTipo; // U_SEI_Tipo
  private String itemEquipo; // U_SEI_Equipo

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder()
        .request(new HashMap<>())
        .code(getProduct())
        .build();
  }

  @Override
  protected void populateUpdateRequest(Map<String, Object> request) {
    request.put("U_SEI_Equipo", itemEquipo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
   // request.put("U_SEI_ITEMPROV", codigoProveedor);
  }

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_Equipo", itemEquipo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
    //request.put("U_SEI_ITEMPROV", codigoProveedor);
  }

  public static OtrosCompProduct create(ResultSet rs) throws SQLException {
    return OtrosCompProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .itemEquipo(rs.getString("Item_Equipo"))
        .itemMarca(rs.getString("Item_Marca"))
        .itemTipo(rs.getString("Item_Tipo"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  @Override
  protected int getGroupCode() {
    return 102;
  }

  @Override
  protected String getUoM() {
    return "UN";
  }
}
