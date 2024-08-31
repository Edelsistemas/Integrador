package com.edelflex.app.model.product;

import com.edelflex.app.model.ProductProcessInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class Product {

  public enum Action {
    CREATE("Crear"),
    UPDATE("Actualizar");

    private final String label;

    Action(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }
  }

  public abstract ProductProcessInfo getProcessInfo();

  public abstract Map<String, Object> createRequest();

  private Action action;
}
