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
public class OtrasValProduct extends Product {

  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemMarca; // U_SEI_Marca
  private String itemTipo; // U_SEI_Tipo
  private String itemModelo; // U_SEI_Modelo
  private String materialJuntas; // U_SEI_MatJun
  private String itemDiametro; // U_SEI_Diametro
  private String actuacion; // U_SEI_Actuacion

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder()
        .request(new HashMap<>())
        .code(getProduct())
        .build();
  }

  @Override
  protected void populateUpdateRequest(Map<String, Object> request) {
    request.put("U_SEI_Modelo", itemModelo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
   // request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Diametro", itemDiametro);
    request.put("U_SEI_Actuacion", actuacion);
  }

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_Modelo", itemModelo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
   // request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Diametro", itemDiametro);
    request.put("U_SEI_Actuacion", actuacion);
  }

  public static OtrasValProduct create(ResultSet rs) throws SQLException {
    return OtrasValProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .itemModelo(rs.getString("Item_Modelo"))
        .itemMarca(rs.getString("Item_Marca"))
        .itemTipo(rs.getString("Item_Tipo"))
        .materialJuntas(rs.getString("Material sellos"))
        .itemDiametro(rs.getString("Item_Diametro"))
        .actuacion(rs.getString("Item_Actuacion"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  @Override
  protected int getGroupCode() {
    return 127;
  }

  @Override
  protected String getUoM() {
    return "UN";
  }
}
