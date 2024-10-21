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
public class ValControlProduct extends Product {

  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemMarca; // U_SEI_Marca
  private String itemModelo; // U_SEI_Modelo
  private String materialJuntas; // U_SEI_MatJun
  private String itemActuacion; // U_SEI_Actuacion
  private String itemDiametroSupMedio; // U_SEI_DiamSup
  private String itemDiametroInferior; // U_SEI_DiamInf
  private String cuerpo; // U_SEI_Cuerpo

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
   // request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Actuacion", itemActuacion);
    request.put("U_SEI_DiamSup", itemDiametroSupMedio);
    request.put("U_SEI_DiamInf", itemDiametroInferior);
    request.put("U_SEI_Cuerpo", cuerpo);
  }

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_Modelo", itemModelo);
    request.put("U_SEI_Marca", itemMarca);
  //  request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_Actuacion", itemActuacion);
    request.put("U_SEI_DiamSup", itemDiametroSupMedio);
    request.put("U_SEI_DiamInf", itemDiametroInferior);
    request.put("U_SEI_Cuerpo", cuerpo);
  }

  public static ValControlProduct create(ResultSet rs) throws SQLException {
    return ValControlProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .itemModelo(rs.getString("Item_Modelo"))
        .itemActuacion(rs.getString("Item_Actuacion"))
        .itemMarca(rs.getString("Item_Marca"))
        .itemDiametroSupMedio(rs.getString("Item_Diametro cuerpo sup_medio"))
        .itemDiametroInferior(rs.getString("Item_Diametro cuerpo inferior"))
        .itemActuacion(rs.getString("Item_Actuacion"))
        .cuerpo(rs.getString("Cuerpo"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  @Override
  protected int getGroupCode() {
    return 125;
  }

  @Override
  protected String getUoM() {
    return "UN";
  }
}
