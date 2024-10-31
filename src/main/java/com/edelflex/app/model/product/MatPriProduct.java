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
@NoArgsConstructor
@SuperBuilder
public class MatPriProduct extends Product {

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder().request(new HashMap<>()).code(getProduct()).build();
  }

  @Override
  protected void populateUpdateRequest(Map<String, Object> request) {}

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {}

  public static MatPriProduct create(ResultSet rs) throws SQLException {
    return MatPriProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("efx4_CodEdelflex"))
        .status(rs.getString("Estado_TC"))
            .groupCode(rs.getInt("GroupCode"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  //@Override
  //protected int getGroupCode() {
  //return 105;
  //}

  @Override
  protected String getUoM() {
    return null;
  }
}
