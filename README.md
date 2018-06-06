# Gestor-BDB
## ¿Cómo ejecutar la aplicación?
### Requisitos:


- Consultas ***MongoDB***:Tener funcionando MongoDB y tener introducidas la base de datos bdb_cica, bdb_cisa, bdb_sica y bdb_sisa
- Consultas ***PostgresQL***: Tener funcionando PostgresQL y tener introducidas la base de datos bdb_cica, bdb_cisa, bdb_sica y bdb_sisa
- Consultas ***MySQL***: Tener funcionando MySQL en el puerto **3307** y tener introducidas la base de datos bdb_cica, bdb_cisa, bdb_sica y bdb_sisa
- Consultas ***MairaDB***: Tener funcionando MairaDB y tener introducidas la base de datos bdb_cica, bdb_cisa, bdb_sica y bdb_sisa
- Consultas ***eXistDB***: Tener funcionando PostgresQL y tener introducidas la base de datos bdb_cica, bdb_cisa, bdb_sica y bdb_sisa

#### Ubuntu y MAC


Vamos a la carpeta donde tenemos la aplicación:


```cd APP```


Instalamos las dependencias de Maven


```sudo mvn clean install```


Corremos la aplicación


```sudo mvn spring-boot:run```


## Tareas que quedan pendientes:

  - Arreglar los problemas con los drivers de MongoDB.
  - Documentar todo el proceso (Latex) MYSQL to DOCKER, Conclusiones, Tiempos MongoDB, Codigo HTML y JavaScript
  - Pensar presentación
  - Añadir columna DB
  - Acabar de incluir resto de contenedores en Docker (If necessary).  
  - Incluir contenedores de Docker y que al inicial la app se inicien los contenedores.
 

