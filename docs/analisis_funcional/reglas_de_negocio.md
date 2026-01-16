\# Reglas de Negocio - Sistema Stock Freezer



\*\*Versión del Documento:\*\* 1.0

\*\*Autor:\*\* Gabriel Origüen

\*\*Estado:\*\* Aprobado



---



\## 1. Gestión de Inventario y Contaminación Cruzada



\### RN-01: Exclusividad de Tipo por Cajón

\*\*Descripción:\*\* Para prevenir la contaminación cruzada de alimentos, un cajón solo puede contener productos del mismo `TipoProducto`.

\* \*\*Comportamiento:\*\*

&nbsp;   \* Si el cajón está \*\*vacío\*\*, acepta cualquier tipo de producto.

&nbsp;   \* Una vez ingresado el primer producto (ej: CARNE), el cajón adquiere la propiedad "Exclusivo CARNE".

&nbsp;   \* Cualquier intento de ingresar un producto de tipo diferente (ej: PESCADO) debe ser rechazado por el sistema con un mensaje de error bloqueante.



---



\## 2. Arquitectura Física del Freezer



\### RN-02: Topología y Distribución

\*\*Descripción:\*\* El sistema debe reflejar la estructura física real del freezer, compuesta por 12 compartimentos distribuidos en 3 niveles (pisos).

\* \*\*Nivel Inferior (Piso 1):\*\* 2 Cajones Grandes (Izq) + 2 Cajones Chicos (Der).

\* \*\*Nivel Medio (Piso 2):\*\* 2 Cajones Grandes (Izq) + 2 Cajones Chicos (Der).

\* \*\*Nivel Superior (Piso 3):\*\* 2 Cajones Grandes (Izq) + 2 Cajones Chicos (Der).



\### RN-03: Capacidad de los Cajones

\*\*Descripción:\*\* Se definen dos tamaños estandarizados de almacenamiento.

\* \*\*Cajón Grande (Tipo B - Big):\*\* Matriz de 3 filas x 4 columnas (12 espacios).

\* \*\*Cajón Chico (Tipo S - Small):\*\* Matriz de 3 filas x 2 columnas (6 espacios).

\* \*\*Capacidad Total del Sistema:\*\* 108 unidades de almacenamiento.



\### RN-04: Nomenclatura de Identificación

\*\*Descripción:\*\* Los cajones deben ser identificados unívocamente para facilitar la operación logística.

\* \*\*Formato Grandes:\*\* `IDB-{n}` (Donde n es correlativo global).

\* \*\*Formato Chicos:\*\* `IDS-{n}` (Donde n es correlativo global).

\* \*\*Orden de Llenado:\*\* De abajo hacia arriba (Inferior -> Medio -> Superior).



---



\## 3. Visualización y Control



\### RN-05: Representación Visual del Estado

\*\*Descripción:\*\* El mapa del freezer debe informar visualmente el contenido de cada celda.

\* `\[ ]`: Espacio Libre.

\* `\[X]`: Ocupado (Deprecado).

\* `\[C]`: Carne, `\[P]`: Pescado, `\[H]`: Hielo, etc. (Inicial del Tipo).

