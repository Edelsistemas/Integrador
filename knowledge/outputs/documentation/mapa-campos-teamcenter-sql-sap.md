# Mapa de campos Teamcenter / SQL intermedio / SAP

## Mapeo SAP principal según código `Product.createRequest()`

| SAP | Origen lógico |
|---|---|
| `ItemCode` | `Product` / ID Teamcenter, solo CREATE |
| `ItemName` | `Name` / descripción |
| `ItemsGroupCode` | Grupo configurado por tabla o cláusula |
| `InventoryUOM` | UOM configurada por tabla |
| `U_SEIDORAR_REVISION` | Revisión |
| `U_SEIDORAR_ESTADO` | Estado Teamcenter |
| `U_SEIDORAR_ARTICULO_EDE_2` | Código Edelflex |
| `U_SEI_ITEMPROV` | Código proveedor |
| `U_SEI_Marca` | Marca |
| `U_SEI_Tipo` | Tipo |
| `U_SEI_Modelo` | Modelo |
| `U_SEI_Equipo` | Equipo |
| `U_SEI_Variable` | Variable |
| `U_SEI_Tamanho` | Tamaño |
| `U_SEI_ModBas` | Modelo Bastidor |
| `U_SEI_Corruga` | Corrugación |
| `U_SEI_MatPlac` | Material placas |
| `U_SEI_MatJun` | Material juntas |
| `U_SEI_CanSec` | Cantidad secciones |
| `U_SEI_Diametro` | Diámetro |
| `U_SEI_Actuacion` | Actuación |
| `U_SEI_Familia` | Familia |
| `U_SEI_DiamSup` | Diámetro superior |
| `U_SEI_DiamInf` | Diámetro inferior |
| `U_SEI_Cuerpo` | Cuerpo |
| `U_SEI_Conex` | Conexiones |
| `Properties1` | Es importado (`si` -> `tYES`, otro/vacío -> `tNO`) |
| `Properties2` | Es fabricado (`si` -> `tYES`, otro/vacío -> `tNO`) |

## Mapeo por tabla según `application.yml`

### AcceInline

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.acce-inline}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `EQUIPO <- Item_Equipo`
- `DIAMETRO <- Item_Diametro`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### BomDesPos

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.bom-des-pos}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### BombaCen

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.bomba-cen}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### DispLimpi

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.disp-limpi}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### ENA

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.ena}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Item_Codigo Proveedor`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Homogen

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- efx4_CodEdelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.homogen}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Item_Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Codigo Proveedor`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### InstrInd

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.instr-ind}`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Modelo`
- `VARIABLE <- Item_Variable`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Instrumen

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.instrumen}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Modelo`
- `VARIABLE <- Item_Variable`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Inter_Calor

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `CLAUSE_GROUP_ITEM <- (CASE WHEN Item_Marca like '%KELVION%' THEN ${team-center.querys.item-groups.inter-calor-kvn} ELSE ${team-center.querys.item-groups.inter-calor-arax} END)`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TAMANIO <- Item_Tamano`
- `MODELO_BASTIDOR <- Modelo Bastidor`
- `CORRUGACION <- Corrugacion`
- `MATERIAL_PLACAS <- Material Placas`
- `MATERIAL_JUNTAS <- Material juntas`
- `CANTIDAD_SECCIONES <- Cantidad Secciones`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### MatPri

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- efx4_CodEdelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.mat-pri}`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### OtrasBom

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.otras-bom}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo de bomba`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### OtrasVal

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.otras-val}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo de valvula`
- `MODELO <- Item_Modelo`
- `MATERIAL_JUNTAS <- Material sellos`
- `DIAMETRO <- Item_Diametro`
- `ACTUACION <- Item_Actuacion`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### OtrosComp

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.otros-comp}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `EQUIPO <- Item_Equipo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Pieza

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.pieza}`
- `PROVEEDOR <- Codigo Proveedor`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Repuestos

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Item_Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.repuestos}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Item_Codigo Proveedor`
- `MARCA <- Item_Marca`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### RestoInter

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.resto-inter}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Semielabo

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.semielabo}`
- `PROVEEDOR <- Codigo Proveedor`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### SisCalEH

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.sis-cal-eh}`
- `FIXED_UOM <- UN`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### SisLimEC

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.sis-lim-ec}`
- `FIXED_UOM <- UN`
- `MARCA <- Item_Marca`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### SisMezEM

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.sis-mez-em}`
- `FIXED_UOM <- UN`
- `MARCA <- Item_Marca`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### SisRecProd

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.sis-rec-prod}`
- `FIXED_UOM <- UN`
- `MARCA <- Item_Marca`
- `MODELO <- Item_Modelo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Sistemas

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- efx4_CodigoEdelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.sistemas}`
- `FIXED_UOM <- UN`
- `TIPO <- Item_Sistema`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Subcon

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.subcon}`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Tanques

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.tanques}`
- `FIXED_UOM <- UN`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### ValAliSeg

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.val-ali-seg}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Modelo`
- `MATERIAL_JUNTAS <- Material sellos`
- `ACTUACION <- Item_Actuacion`
- `DIAMETRO_SUPERIOR <- Item_Diametro cuerpo sup_medio`
- `DIAMETRO_INFERIOR <- Item_Diametro cuerpo inferior`
- `CUERPO <- Cuerpo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### ValAsiento

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.val-asiento}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `TIPO <- Item_Tipo`
- `MODELO <- Item_Modelo`
- `MATERIAL_JUNTAS <- Material sellos`
- `ACTUACION <- Item_Actuacion`
- `FAMILIA <- Item_Familia`
- `DIAMETRO_SUPERIOR <- Item_Diametro cuerpo sup_medio`
- `DIAMETRO_INFERIOR <- Item_Diametro cuerpo inferior`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### ValControl

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.val-control}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `MODELO <- Item_Modelo`
- `MATERIAL_JUNTAS <- Material sellos`
- `ACTUACION <- Item_Actuacion`
- `DIAMETRO_SUPERIOR <- Item_Diametro cuerpo sup_medio`
- `DIAMETRO_INFERIOR <- Item_Diametro cuerpo inferior`
- `CUERPO <- Cuerpo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### ValDia

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.val-dia}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `MODELO <- Modelo`
- `MATERIAL_JUNTAS <- Material diafragma`
- `DIAMETRO <- Item_Diametro`
- `ACTUACION <- Item_Actuacion`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### ValFuelle

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.val-fuelle}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `MATERIAL_JUNTAS <- Material fuelle`
- `ACTUACION <- Item_Actuacion`
- `FAMILIA <- Item_Familia`
- `DIAMETRO_SUPERIOR <- Item_Diametro cuerpo superior`
- `DIAMETRO_INFERIOR <- Item_Diametro cuerpo inferior`
- `CUERPO <- Cuerpo`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### ValReten

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.val-reten}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `MATERIAL_JUNTAS <- Material sellos`
- `DIAMETRO <- Item_Diametro`
- `CUERPO <- Cuerpo`
- `CONEXIONES <- Item_Conexiones`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
### Valvulas

- `ID <- id`
- `NAME <- Name`
- `PRODUCT <- Product`
- `REVISION <- Revision`
- `EDELFLEX <- Codigo Edelflex`
- `STATUS <- Estado_TC`
- `FIXED_GROUP_ITEM <- ${team-center.querys.item-groups.valvulas}`
- `FIXED_UOM <- UN`
- `PROVEEDOR <- Codigo Proveedor`
- `MARCA <- Item_Marca`
- `MODELO <- Item_Modelo`
- `MATERIAL_JUNTAS <- Material sellos`
- `DIAMETRO <- Item_Diametro`
- `ACTUACION <- Item_Actuacion`
- `IMPORTADO <- Es importado`
- `FABRICADO <- Es fabricado`
