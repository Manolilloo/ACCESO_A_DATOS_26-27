<div align="center">

  <img src="assets/header.svg" alt="Acceso a Datos · Proyectos 2026–2027" width="100%" />

  <br />

  <p>
    <strong>Repositorio académico · Curso 2026–2027</strong><br />
    Proyectos, prácticas y ejercicios del módulo de <strong>Acceso a Datos</strong>.
  </p>

  <p>
    <img src="https://img.shields.io/badge/JAVA-21-8B8CFF?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
    <img src="https://img.shields.io/badge/MAVEN-BUILD-65C7D8?style=for-the-badge&logo=apachemaven&logoColor=07111F" alt="Maven" />
    <img src="https://img.shields.io/badge/IDE-INTELLIJ-9A83F7?style=for-the-badge&logo=intellijidea&logoColor=white" alt="IntelliJ IDEA" />
    <img src="https://img.shields.io/badge/FORMATOS-XML%20%7C%20JSON-C18BFF?style=for-the-badge" alt="XML y JSON" />
  </p>

  <p>
    <a href="#sobre-el-repositorio">Sobre el repositorio</a> &nbsp;·&nbsp;
    <a href="#-actividades">Actividades</a> &nbsp;·&nbsp;
    <a href="#-puesta-en-marcha">Puesta en marcha</a> &nbsp;·&nbsp;
    <a href="#-estructura">Estructura</a>
  </p>

</div>

---

## Sobre el repositorio

Este repositorio reúne el trabajo práctico del módulo de **Acceso a Datos**. Cada actividad se organiza como un módulo Maven independiente, con su propio código fuente, configuración y recursos.

La idea es mantener un espacio limpio, ordenado y fácil de ampliar durante el curso: cada proyecto conserva su autonomía sin perder la visión global de la asignatura.

## Actividades

| Módulo | Proyecto | Tecnologías y contenido |
|:--:|---|---|
| **01** | **Manejo de ficheros**<br /><sub>`ACT1_1_ManejoFicheros`</sub> | Java · Lectura y escritura de ficheros · Acceso a datos |
| **02** | **Manejo complejo de ficheros con JAXB y Jackson JSON**<br /><sub>`ACT1_2_Manejo_complejo_de_ficheros_con_JAXB_y_Jackson_JSON`</sub> | Java · Maven · JAXB · XML · Jackson · JSON |

> Nuevas actividades se incorporarán como módulos independientes, manteniendo esta misma organización.

## Estructura

La raíz del repositorio contiene un **POM agregador** que permite cargar todos los módulos desde IntelliJ IDEA. Cada actividad mantiene, además, su propio `pom.xml`.

```text
ACCESO_A_DATOS_26-27/
│
├── pom.xml                                  # POM principal / agregador
├── README.md
├── .gitignore
├── assets/
│   └── header.svg
│
├── ACT1_1_ManejoFicheros/
│   ├── pom.xml                              # Proyecto Maven independiente
│   ├── src/
│   ├── BBDD Coches.csv
│   └── coches.dat
│
└── ACT1_2_Manejo_complejo_de_ficheros_con_JAXB_y_Jackson_JSON/
    ├── pom.xml                              # Proyecto Maven independiente
    ├── concesionario_plantilla.xml
    └── src/
        └── main/
            ├── java/
            │   ├── Main.java
            │   ├── model/
            │   └── Service/
            └── resources/
                └── config.properties
```

## Puesta en marcha

### Requisitos

- **JDK 21** o compatible con la configuración del proyecto.
- **Apache Maven**.
- **IntelliJ IDEA** (recomendado).

### Clonar el repositorio

```bash
git clone https://github.com/Manolilloo/ACCESO_A_DATOS_26-27.git
cd ACCESO_A_DATOS_26-27
```

### Abrir todos los módulos en IntelliJ IDEA

1. Selecciona **File → Open** y abre la carpeta raíz `ACCESO_A_DATOS_26-27`.
2. Espera a que IntelliJ detecte el POM principal.
3. Abre la ventana **Maven** y pulsa **Reload All Maven Projects**.
4. Comprueba que el SDK del proyecto está configurado con **JDK 21**.

### Trabajar con una actividad concreta

También puedes abrir directamente la carpeta de la actividad que quieras desarrollar. Su `pom.xml` contiene la configuración de ese módulo y permite trabajar en él sin abrir el resto.

### Compilar

Compilar todos los módulos:

```bash
mvn clean package
```

Compilar solo una actividad:

```bash
mvn -pl ACT1_1_ManejoFicheros clean package
```

```bash
mvn -pl ACT1_2_Manejo_complejo_de_ficheros_con_JAXB_y_Jackson_JSON clean package
```

## Criterios de organización

- **Independencia:** cada actividad tiene su propio POM y sus propias dependencias.
- **Orden:** el código y los recursos permanecen dentro de la carpeta de su actividad.
- **Mantenimiento:** el POM raíz agrupa los módulos sin mezclar sus implementaciones.
- **Limpieza:** los archivos generados y la configuración local del IDE no forman parte del control de versiones.

El `.gitignore` raíz excluye, entre otros, `.idea/`, `*.iml`, `target/`, `out/` y `build/`. Los archivos fuente, POM, datos de prueba y recursos necesarios sí se conservan.

---

<div align="center">

  <sub><strong>ACCESO A DATOS · 2026–2027</strong></sub><br />
  <sub>Construir · Probar · Comprender · Mejorar</sub><br /><br />
  <a href="https://github.com/Manolilloo">GitHub · @Manolilloo</a>

</div>
