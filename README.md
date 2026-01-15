# StockFreezer



Sistema de gestión de stock con JPA y lógica de matrices.



\### 🛠 Notas Técnicas - Refactorización v1.1

\- \*\*Cambio de Arquitectura:\*\* Se migró de una estrategia de Herencia (Clases Pescado, Carne, etc.) a una estrategia de \*\*Composición parametrizada\*\* mediante `Enums`.

&nbsp; - \*Motivo:\* Mayor flexibilidad y mantenibilidad. Permite agregar nuevos tipos de productos sin recompilar nuevas clases ni detener el servidor.

\- \*\*Validación de Inputs:\*\* Se implementó lógica de validación estricta (Strong Typing) en la capa de vista (`MenuPrincipal`). No se permite la persistencia de datos si no coinciden con los tipos enumerados permitidos.

