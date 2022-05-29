# ProyectoCombinado
En este repositorio encontramos un proyecto combinado de las asignaturas de EEDD y BBDD del 1º año del grado superior de DAW con sus respectivas documentaciones y versiones.

# Aclaraciones
- En la rama master encontramos desde la versión 1.0.0 hasta la versión 1.0.4 (Hasta donde deja de funcionar la versión gráfica).
- En la rama VersiónNoGráfica tenemos el resto de versiones hasta la final sin interfaz gráfica.
- La documentación y los diferentes diagramas del proyectos se encuentran en el directorio "Documentación" dentro de la carpeta del proyecto.
- Los ficheros de insertar/modificar/eliminar desde fichero se ecuentran en el directorio "Ficheros" dentro de la carpera del proyecto.

# Memoria técnica (versiones)
- May 12, 2022 
  - **Programa base**
- May 14, 2022 
  - **Versión 1.0.1** ***(rama master)***
    - Modifico carácteres extraños y la columan "dirección" -> "ciudad".
   - **Versión 1.0.2** ***(rama master)***
      - Corrección y caracteres extraños (acentos).
      - Cambio nombre de variables. 
    - **Versión 1.0.3** ***(rama master)***
      - Sustitución de métodos obsoletos.
      - Redistribución tipo MVC.  
 - May 15, 2022
    - **Versión 1.0.4** ***(rama master)***
      - Modifico DBManager para que use un PreparedStatement
      - Mejora de la calidad del código con SonarLint
      - Termino de documentar.
  - May 25, 2022
    - **Rama sin versión gráfica**.
  - May 26, 2022
    - **Versión 1.1.0** ***(rama VersionNoGrafica)***
      - Separado en una rama aparte de la versión gráfica.
      - Mejorada la modificación para usar PreparedStatement.
      - Añadida opción para crear una tabla.
      - Añadida función para filtrar una linea.
      - Añadida función volcado de tabla en fichero.
      - Añadida función de carga de datos desde fichero.
      - Correción de errores no significativos.
    - **Versión 1.1.1** ***(rama VersionNoGrafica)***
      - Mejorada la función insertarDesdeFichero
      - Añadida función para modificar datos desde un fichero.
      - Añadida función para eliminar datos desde fichero.
  - May 27, 2022
    - **Versión 1.1.2** ***(rama VersionNoGrafica)***
      - Mejorada la calidad del código con SonarLint.
      - Terminados los comentarios.
      - Añadida la documentación con Javadoc

## Cosas que no he podido hacer.
- Modificar el programa para que pueda trabajar con cualquier tipo de BBDD.
- Usar CallableStatement, devido a que no se configurar procedimientos almacenados en sql.


