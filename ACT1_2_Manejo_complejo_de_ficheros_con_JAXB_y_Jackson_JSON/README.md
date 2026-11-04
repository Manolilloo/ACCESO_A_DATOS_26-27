# ACT1.2: Manejo complejo de ficheros con JAXB y Jackson JSON

Aplicación Java para gestionar los coches de un concesionario. Los datos se mantienen en memoria mientras se ejecuta el programa y se guardan en XML.

## Requisitos

- JDK 21 o superior.
- IntelliJ IDEA con soporte para proyectos Maven.

## Abrir y ejecutar

1. Abrir en IntelliJ IDEA la carpeta de este módulo, donde se encuentra el archivo `pom.xml`.
2. Esperar a que Maven descargue las dependencias.
3. Comprobar que el proyecto utiliza un JDK 21 o superior.
4. Ejecutar la clase `principal.Main`.

El proyecto es independiente del `pom.xml` de la carpeta principal del repositorio.

## Estructura

- `src/main/java/config`: carga las rutas de los archivos.
- `src/main/java/model`: contiene las clases `Coche` y `Concesionario`.
- `src/main/java/service`: contiene los servicios XML, CSV, JSON, gestión e informe.
- `src/main/java/principal`: contiene el menú de la aplicación.
- `src/main/resources/config.properties`: configura las rutas.
- `datos/concesionario.xml`: base de datos inicial.
- `datos/BBDDejemplo.csv`: archivo CSV de ejemplo.

## Archivos de datos

Las rutas se configuran en `src/main/resources/config.properties`. Son relativas a la carpeta de este módulo, por lo que la aplicación no depende del directorio de trabajo configurado en IntelliJ.

Los archivos de salida se guardan dentro de `datos`:

- `concesionario.xml`: base de datos persistente.
- `concesionario.json`: exportación del concesionario completo.
- `coche.json`: exportación de un coche.
- `informe_concesionario.txt`: informe resumen.

## Funcionamiento de la importación JSON

- Importar concesionario completo sustituye la lista actual de coches por la que contiene el JSON.
- Importar un coche lee un único objeto `Coche` y lo añade a la lista actual si su matrícula no está repetida.
- Las matrículas se comparan sin distinguir mayúsculas y minúsculas.

## Comprobaciones recomendadas

1. Importar el CSV dos veces. La primera vez debe añadir los coches nuevos y la segunda no debe duplicarlos.
2. Insertar un coche y volver a intentar insertarlo con la misma matrícula.
3. Modificar marca, modelo y extras, y comprobar que la matrícula no cambia.
4. Borrar un coche y volver a buscarlo.
5. Ordenar por matrícula y revisar el XML.
6. Exportar e importar el concesionario completo.
7. Exportar, borrar e importar un coche individual.
8. Generar el informe y comprobar sus tres apartados: total de coches, agrupación por marca y extra más repetido.
9. Probar las rutas abriendo el módulo por separado y ejecutándolo desde IntelliJ.
