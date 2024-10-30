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
public class InterCalorProduct extends Product {

  private String codigoProveedor; // U_SEI_ITEMPROV
  private String itemTipo; // U_SEI_Tipo
  private String materialPlacas; // U_SEI_MatPlac
  private String materialJuntas; // U_SEI_MatJun
  private String cantSecciones; // U_SEI_CanSec
  private String corrugacion; // U_SEI_Corruga
  private String modeloBastidor; // U_SEI_ModBas
  private String itemTamano; // U_SEI_Tamanho
  private String marca;

  @Override
  public ProductProcessInfo getProcessInfo() {
    return ProductProcessInfo.builder().request(new HashMap<>()).code(getProduct()).build();
  }

  @Override
  protected void populateUpdateRequest(Map<String, Object> request) {
    request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_MatPlac", materialPlacas);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_CanSec", cantSecciones);
    request.put("U_SEI_Corruga", corrugacion);
    request.put("U_SEI_ModBas", modeloBastidor);
    request.put("U_SEI_Tamanho", itemTamano);
  }

  @Override
  protected void populateCreateRequest(Map<String, Object> request) {
    request.put("U_SEI_ITEMPROV", codigoProveedor);
    request.put("U_SEI_Tipo", itemTipo);
    request.put("U_SEI_MatPlac", materialPlacas);
    request.put("U_SEI_MatJun", materialJuntas);
    request.put("U_SEI_CanSec", cantSecciones);
    request.put("U_SEI_Corruga", corrugacion);
    request.put("U_SEI_ModBas", modeloBastidor);
    request.put("U_SEI_Tamanho", itemTamano);
  }

  public static InterCalorProduct create(ResultSet rs) throws SQLException {
    return InterCalorProduct.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("Name"))
        .product(rs.getString("Product"))
        .revision(rs.getString("Revision"))
        .codigoEdelflex(rs.getString("Codigo Edelflex"))
        .codigoProveedor(rs.getString("Codigo Proveedor"))
        .itemTipo(rs.getString("Item_Tipo"))
        .itemTamano(rs.getString("Item_Tamano"))
        .corrugacion(rs.getString("Corrugacion"))
        .cantSecciones(rs.getString("Cantidad Secciones"))
        .materialJuntas(rs.getString("Material juntas"))
        .materialPlacas(rs.getString("Material Placas"))
        .modeloBastidor(rs.getString("Modelo Bastidor"))
        .marca(rs.getString("Item_Marca"))
        .status(rs.getString("Estado_TC"))
        .groupCode(rs.getInt("GroupCode"))
        .action(rs.getString("Revision").equals("A") ? Action.CREATE : Action.UPDATE)
        .build();
  }

  @Override
  protected int getGroupCode() {
    if (marca.equalsIgnoreCase("ARAX")) {
      return 104;
    } else {
      return 130;
    }
  }

  @Override
  protected String getUoM() {
    return "UN";
  }
}
