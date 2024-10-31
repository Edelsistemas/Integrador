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
public class SemielaboProduct extends Product {

  private String codigoProveedor; // U_SEI_ITEMPROV

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder().request(new HashMap<>()).code(getProduct()).build();
  }

  @Override
  protected void populateUpdateRequest(Map<String, Object> request) {
    request.put("U_SEI_ITEMPROV", codigoProveedor);
  }

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_ITEMPROV", codigoProveedor);
  }

  public static SemielaboProduct create(ResultSet rs) throws SQLException {
    return SemielaboProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .status(rs.getString("Estado_TC"))
            .groupCode(rs.getInt("GroupCode"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  //return 116;

  @Override
  protected String getUoM() {
    return null;
  }
}
