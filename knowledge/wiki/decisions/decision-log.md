# Registro de decisiones

| ID | Fecha | DecisiÃ³n | Motivo | Estado | Fuente |
|---|---|---|---|---|---|
| DEC-001 | 2024-06 | Usar ambientes separados Desarrollo/Testing/QA y ProducciÃ³n | MetodologÃ­a de trabajo y soporte posterior | Confirmada | Correo `Primera devoluciÃ³n alcances interfaz` |
| DEC-002 | 2024-09 | Usar base SQL Server intermedia `DB_Edelflex_SAP` | Teamcenter exporta datos y el Integrador los consume | Confirmada | Correo acceso base + usuario Edelflex |
| DEC-003 | 2024/2025 | Estado `60` habilita migraciÃ³n a SAP | Workflow aprobado/liberado | Confirmada | Correos + Edelflex |
| DEC-004 | 2024/2025 | Estado `90` representa obsoleto/baja | Debe reflejarse en SAP como inactivo segÃºn cambio posterior | Confirmada | Correos + Edelflex |
| DEC-005 | 2025-02 | Inactivar si cambian campos crÃ­ticos | Evitar cambios crÃ­ticos activos sin revisiÃ³n manual | Confirmada en correo y cÃ³digo | `Definiciones de Cambio`, `Product.evaluateInactivo` |
| DEC-006 | 2025 | Remover BitOne de nueva revisiÃ³n | No aporta al objetivo funcional y pertenece a soporte/desarrollo externo anterior | Confirmada por Edelflex | ConversaciÃ³n |
| DEC-007 | 2026-06 | Crear rama `main-GS` para trabajo propio sin tocar `master` | Preservar versiÃ³n original recuperada y avanzar seguro | Confirmada | Git/chat |
| DEC-008 | 2026-06 | Crear tags `RECOVERY-ORIGINAL` y `DOCKER-STABLE` | Congelar estado original y estado Docker estable | Confirmada parcialmente; verificar punteros | Git/chat |
| DEC-009 | 2026-07-01 | Retirar del proyecto local el backup `mongodb/data-backup-2026-06-30-1629` y conservarlo solo en respaldo externo | Esta copia se usara como base limpia de continuidad de desarrollo; no se requiere historico Mongo dentro del workspace | Confirmada | Conversacion operativa + estado local |
| DEC-010 | 2026-07-01 | Adoptar Integrador como monorepo raiz y preservar la historia de ede-integration-service dentro del nuevo repo | Cubrir sistema completo con Git manteniendo trazabilidad historica | Confirmada | Git/GitHub + conversacion |
| DEC-011 | 2026-07-01 | Ejecutar el bootstrap Wiki-SDD reconciliando knowledge/ existente en lugar de recrearlo desde cero | Minimizar riesgo documental y preservar contexto acumulado | Confirmada | Conversacion + estado real del repo |
| DEC-012 | 2026-07-01 | Durante el bootstrap no tocar codigo productivo, no hacer commit ni push, y priorizar completar gaps sobre renombrar carpetas | Reducir riesgo operativo y evitar churn innecesario | Confirmada | Conversacion + workflow bootstrap |

