package com.edelflex.app.model.product;

import com.edelflex.app.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private static final String REMOVE_STATUS = "90";
    private long id;
    private String name;
    private String product;
    private String revision;
    private String status;
    private String edelflex;
    private String groupItem;
    private String productType;

    private String proveedor;
    private String marca;
    private String tipo;
    private String modelo;
    private String equipo;
    private String variable;
    private String tamanio;
    private String modeloBastidor;
    private String corrugacion;
    private String materialPlacas;
    private String materialJuntas;
    private String cantidadSecciones;
    private String diametro;
    private String actuacion;
    private String familia;
    private String diametroSuperior;
    private String diametroInferior;
    private String cuerpo;
    private String conexiones;
    private String uom;
    private Action action;
    private String importado;
    private String fabricado;
    private Boolean inactivo;
    private Map<String, Object> origin;

    public void evaluateInactivo(Map<String, Object> itemData) {
        Map<String, Object> value = (Map<String, Object>) ((List) itemData.get("value")).get(0);
        String name = (String) value.get("ItemName");
        Integer group = (Integer) value.get("ItemsGroupCode");
        String importado = (String) value.get("Properties1");
        String fabricado = (String) value.get("Properties2");
        value.remove("odata.etag");

        if (!this.name.equalsIgnoreCase(name)) {
            setInactivo(true);
        } else if (Integer.parseInt(this.groupItem) != group) {
            setInactivo(true);
        } else if (!getSapBoolean(this.importado).equalsIgnoreCase(importado)) {
            setInactivo(true);
        } else if (!getSapBoolean(this.fabricado).equalsIgnoreCase(fabricado)) {
            setInactivo(true);
        } else if (this.status.equalsIgnoreCase(REMOVE_STATUS)) {
            setInactivo(true);
        }
    }

    public enum Action {
        UPDATE("Actualizar"),
        CREATE("Crear");

        Action(String label) {
            this.label = label;
        }

        private final String label;

        public String getLabel() {
            return label;
        }
    }

    public Map<String, Object> createRequest() {
        Map<String, Object> request = new HashMap<>();
        request.put("ItemName", getName());
        request.put("ItemsGroupCode", Integer.parseInt(getGroupItem()));
        request.put("U_SEIDORAR_REVISION", getRevision());
        request.put("U_SEIDORAR_ESTADO", getStatus());
        request.put("U_SEIDORAR_ARTICULO_EDE_2", getEdelflex());
        request.put("InventoryUOM", getUom());
        // CREATE
        if (getAction().equals(Action.CREATE)) {
            request.put("ItemCode", getProduct());
        }

        if (inactivo != null && inactivo) {
            request.put("Valid", "tNO");
            request.put("Frozen", "tYES");
        }

        populateRequest(request);
        return clearEmptyValues(request);
    }

    protected void populateRequest(Map<String, Object> request) {
        request.put("U_SEI_ITEMPROV", trimValue(proveedor, 100));
        request.put("U_SEI_Marca", trimValue(marca, 30));
        request.put("U_SEI_Tipo", trimValue(tipo, 50));
        request.put("U_SEI_Modelo", trimValue(modelo, 30));
        request.put("U_SEI_Equipo", trimValue(equipo, 50));
        request.put("U_SEI_Variable", trimValue(variable, 30));
        request.put("U_SEI_Tamanho", trimValue(tamanio, 30));
        request.put("U_SEI_ModBas", trimValue(modeloBastidor, 30));
        request.put("U_SEI_Corruga", trimValue(corrugacion, 30));
        request.put("U_SEI_MatPlac", trimValue(materialPlacas, 30));
        request.put("U_SEI_MatJun", trimValue(materialJuntas, 30));
        request.put("U_SEI_CanSec", trimValue(cantidadSecciones, 30));
        request.put("U_SEI_Diametro", trimValue(diametro, 30));
        request.put("U_SEI_Actuacion", trimValue(actuacion, 50));
        request.put("U_SEI_Familia", trimValue(familia, 30));
        request.put("U_SEI_DiamSup", trimValue(diametroSuperior, 30));
        request.put("U_SEI_DiamInf", trimValue(diametroInferior, 30));
        request.put("U_SEI_Cuerpo", trimValue(cuerpo, 50));
        request.put("U_SEI_Conex", trimValue(conexiones, 50));
        request.put("Properties1", trimValue(getSapBoolean(importado), 30));
        request.put("Properties2", trimValue(getSapBoolean(fabricado), 30));
    }

    private String getSapBoolean(String value) {
        if (Utils.isEmpty(value)) {
            return "tNO";
        }
        if (value.trim().equalsIgnoreCase("si")) {
            return "tYES";
        }
        return "tNO";
    }

    private Map<String, Object> clearEmptyValues(Map<String, Object> request) {
        Map<String, Object> results = new HashMap<>();
        request.forEach(
                (key, value) -> {
                    if (value != null) {
                        if (value instanceof String) {
                            if (Utils.isNotEmpty(value.toString())) {
                                results.put(key, value.toString());
                            }
                        } else {
                            results.put(key, value.toString());
                        }
                    }
                });
        return results;
    }

    private String trimValue(String value, int length) {
        if (value != null) {
            return value.length() > length ? value.substring(0, length) : value;
        }
        return null;
    }
}
