# Acceso a Datos 2026-2027

Repositorio de actividades del módulo de Acceso a Datos.

## Estructura

Cada actividad es un módulo Maven independiente, con su propio `pom.xml`, código fuente y recursos.

- `ACT1_1_ManejoFicheros`: actividad 1.1.
- `ACT1_2_Manejo_complejo_de_ficheros_con_JAXB_y_Jackson_JSON`: actividad 1.2.

El `pom.xml` de la raíz agrupa los módulos para que IntelliJ pueda detectarlos todos, pero cada actividad mantiene su propia configuración y se puede abrir y ejecutar por separado.

## Abrir en IntelliJ

**Para cargar todas las actividades en una misma ventana:**

1. Abre la carpeta raíz `ACCESO_A_DATOS_26-27` usando `File → Open`.
2. Cuando IntelliJ pregunte, selecciona abrir como proyecto.
3. En la ventana Maven, pulsa `Reload All Maven Projects`.
4. Configura un JDK 21 en `File → Project Structure → Project → SDK`.

**Para trabajar únicamente en una actividad:**

Abre directamente la carpeta de esa actividad, la que contiene su propio `pom.xml`. También puedes abrir su `pom.xml` como proyecto Maven.

## Ejecutar

- Actividad 1.2: ejecuta la clase `Main`, situada en `src/main/java/Main.java`.
- Actividad 1.1: su clase actual `GestorCoches` todavía no contiene un método `main()`. Maven puede reconocer y compilar el módulo, pero tendrás que añadir el punto de entrada de la actividad para ejecutarla como aplicación.

## Archivos ignorados

El `.gitignore` raíz excluye la configuración local de IntelliJ (`.idea/` y `*.iml`), los resultados de compilación (`target/`, `out/`, `build/`) y otros archivos generados. No excluye los archivos Java, los `pom.xml` ni los recursos de las actividades.
