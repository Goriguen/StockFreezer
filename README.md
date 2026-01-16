# ❄️ StockFreezer

**Sistema de gestión de stock y prevención de riesgos para el rubro gastronómico.**
Desarrollado en Java utilizando JPA (Hibernate) y lógica de matrices para la representación espacial.

---

## 💡 Motivación y Contexto del Negocio

Este proyecto surge de una necesidad real detectada tras mi trayectoria en el rubro gastronómico.

En entornos de alta presión y **proyectos acelerados**, he sido testigo de cómo la falta de implementación tecnológica y organización impacta negativamente en recursos vitales, especialmente el **tiempo**. La ausencia de jerarquías espaciales definidas y la falta de trazabilidad (quién guardó, cuándo y dónde) son, habitualmente, el inicio del caos operativo.

**La Propuesta:**
Diseñé este sistema bajo la premisa de que **reglas estrictas y una lógica impenetrable son la única solución escalable**. El software actúa como un auditor en tiempo real, imponiendo orden y estructura allí donde el error humano suele fallar.

---

## 🛠 Enfoque del Proyecto

Este desarrollo pone el foco principal en el **Análisis Funcional, la Calidad (QA) y la Lógica de Negocio**.

* **Diseño y Reglas:** La arquitectura del sistema, la distribución física del freezer (12 compartimentos) y las reglas de validación (como la prevención de contaminación cruzada) fueron definidas íntegramente bajo mi criterio analítico.
* **Implementación Técnica:** Utilicé herramientas de IA como soporte para agilizar la escritura de código Java (sintaxis y boilerplate), revisando y validando personalmente cada bloque lógico para asegurar que cumpla con los requerimientos diseñados.
* **Objetivo:** Demostrar mis capacidades para relevar requerimientos, diseñar sistemas escalables y ejecutar pruebas de calidad (QA), utilizando la programación como un medio para el fin.

---

## 🚀 Arquitectura Funcional

El sistema modela un freezer industrial con las siguientes características de negocio:
* **Topología:** 12 Cajones distribuidos en 3 niveles (Inferior, Medio, Superior).
* **Regla de Exclusividad:** Validación lógica estricta que impide mezclar tipos de alimentos (ej: Carne con Pescado) en un mismo cajón.
* **Visualización:** Mapeo visual en consola del estado de ocupación de cada celda.

> 📂 **Ver Documentación Completa:**
> * [Reglas de Negocio y Diseño Funcional](docs/analisis_funcional/)
> * [Plan de Pruebas y Reportes de QA](docs/qa_testing/)

---

## 💻 Notas Técnicas (Release v1.1)

Detalles sobre la evolución del código y decisiones de arquitectura de software:

### Refactorización y Mejora Continua
* **Cambio de Paradigma:** Se migró de una estrategia de Herencia (Clases separadas para Pescado, Carne, etc.) a una estrategia de **Composición parametrizada** mediante `Enums`.
    * *Motivo:* Mayor flexibilidad y mantenibilidad. Permite agregar nuevos tipos de productos sin recompilar clases ni detener el servidor.
* **Strong Typing & Validación:** Se implementó lógica de validación estricta en la capa de vista (`MenuPrincipal`). El sistema asegura la integridad de los datos antes de llamar a la persistencia, rechazando inputs que no coincidan con los enumerados permitidos.