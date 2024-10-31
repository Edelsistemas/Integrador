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
public class ValDiaProduct extends Product {

  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemMarca; // U_SEI_Marca
  private String itemTipo; // U_SEI_Tipo
  private String itemModelo; // U_SEI_Modelo
  private String materialJuntas; // U_SEI_MatJun
  private String itemActuacion; // U_SEI_Actuacion
  private String itemDiametro; // U_SEI_Diametro

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder().request(new HashMap<>()).code(getProduct()).build();
  }

  @Override
  protected void populateUpdateRequest(Map<String, Object> request) {
    request.put("U_SEI_Modelo", itemModelo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Actuacion", itemActuacion);
    request.put("U_SEI_Diametro", itemDiametro);
  }

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_Modelo", itemModelo);
    request.put("U_SEI_Marca", itemMarca);
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Actuacion", itemActuacion);
    request.put("U_SEI_Diametro", itemDiametro);
  }

  public static ValDiaProduct create(ResultSet rs) throws SQLException {
    return ValDiaProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .itemModelo(rs.getString("Item_Modelo"))
        .itemMarca(rs.getString("Item_Marca"))
        .itemTipo(rs.getString("Item_Tipo"))
        .itemDiametro(rs.getString("Item_Diametro"))
        .itemActuacion(rs.getString("Item_Actuacion"))
        .materialJuntas(rs.getString("Material diafragma"))
        .status(rs.getString("Estado_TC"))
            .groupCode(rs.getInt("GroupCode"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  //return 122;

  @Override
  protected String getUoM() {
    return "UN";
  }
}
