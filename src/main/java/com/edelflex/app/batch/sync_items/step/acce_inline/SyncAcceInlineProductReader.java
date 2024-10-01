package com.edelflex.app.batch.sync_items.step.acce_inline;

import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.model.product.InterCalorProduct;
import com.edelflex.app.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
@Profile("sync-items-batch")
public class SyncAcceInlineProductReader extends SyncBaseProductReader<InterCalorProduct> {

  public SyncAcceInlineProductReader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.get-inter-calor-products}") String query) {
    super(
        jdbcTemplate,
        (rs, i) ->
            InterCalorProduct.builder()
                .name(rs.getString("Name"))
                .product(rs.getString("Product"))
                .revision(rs.getString("Revision"))
                .subtype(rs.getString("Subtype"))
                .cantidadDePlacas(rs.getString("CantidadDePlacas"))
                .cantidadDeSecciones(rs.getString("CantidadDeSecciones"))
                .codigoERPViejo(rs.getString("CodigoERPViejo"))
                .codigoFabricante(rs.getString("CodigoFabricante"))
                .corrugacion(rs.getString("Corrugacion"))
                .diametroDeConexionLadoProducto(rs.getString("DiametroDeConexionLadoProducto"))
                .diametroDeConexionLadoServicio(rs.getString("DiametroDeConexionLadoServicio"))
                .fabricable(rs.getString("Fabricable"))
                .importado(rs.getString("Importado"))
                .espesorPlacas(rs.getString("EspesorPlacas"))
                .longitudEquipo(rs.getString("LongitudEquipo"))
                .materialBastidor(rs.getString("MaterialBastidor"))
                .materialJuntas(rs.getString("MaterialJuntas"))
                .materialPlacas(rs.getString("MaterialPlacas"))
                .modeloBastidor(rs.getString("ModeloBastidor"))
                .normaConexionLadoProducto(rs.getString("NormaConexionLadoProducto"))
                .normaConexionLadoServicio(rs.getString("NormaConexionLadoServicio"))
                .tamanio(rs.getString("Tamaño"))
                .tipoJunta(rs.getString("TipoJunta"))
                .tipoConexionLadoProducto(rs.getString("TipoConexionLadoProducto"))
                .tipoConexionLadoServicio(rs.getString("TipoConexionLadoServicio"))
                .ubicacionConexiones(rs.getString("UbicacionConexiones"))
                .action(
                    rs.getString("Revision").equals("A")
                        ? Product.Action.CREATE
                        : Product.Action.UPDATE)
                .build(),
        query);
  }
}
