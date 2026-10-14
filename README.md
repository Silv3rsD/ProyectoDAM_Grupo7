# AutentIFPcar

## 1. Descripción del proyecto

Proyecto nacido de la idea de luchar contra el fraude comercial en la compra y venta de vehículos de segunda mano.

Dentro del contexto de este sector comercial, se plantea la creación de un sistema de registro seguro y verificado, en el cual los vehículos puedan estar inscritos, llevando cada uno un historial de su vida útil (revisiones, reparaciones, ITV, multas, accidentes, etc.).

Con ello se busca ofrecer una mayor transparencia en el mercado de vehículos de segunda mano, facilitando el acceso a información relevante y fomentando un comercio más justo y fiable.

## 2. Funcionalidades

### Búsqueda de vehículos

Permite consultar los vehículos inscritos en nuestra base de datos mediante un buscador de matrículas.

Esta es la función principal de la aplicación, siendo de acceso público y sin necesidad de registro o identificación previa.

El usuario podrá consultar los datos generales del vehículo; adicionalmente aquellos usuarios registrados podrán acceder a su historial de búsqueda.

### Registro de usuarios y empresas

Función que permite registrarse e identificarse tanto a personas físicas como a empresas, con el objetivo de acceder a diferentes funcionalidades de la aplicación.

Los administradores serán los encargados de asignar los roles correspondientes a los usuarios registrados, estableciendo así los permisos y funciones a los que podrán acceder.

### Publicación de vehículos

Una persona o entidad registrada podrá solicitar la inscripción de un vehículo en nuestra base de datos, proporcionando la información correspondiente.

Esta información será enviada a los administradores para su revisión y verificación, siendo estos los responsables de aprobar o rechazar su publicación.

### Actualización de datos

Las entidades o individuos autorizados, especialmente aquellos dedicados al mantenimiento de vehículos, podrán solicitar actualizaciones de la información registrada (revisiones, reparaciones, ITV, etc.).

Estas actualizaciones podrán requerir una revisión por parte de los administradores antes de incorporarse al historial del vehículo.

Dependiendo del rol asignado y del tipo de información, determinados registros podrán añadirse directamente, sin necesidad de aprobación previa.

### Modificación y verificación de datos

Funcionalidad reservada a los administradores, encargados de revisar la información proporcionada por los usuarios, verificar su coherencia y autorizar la publicación de nuevos registros.

Los administradores también podrán intervenir en la corrección de información cuando se detecten errores o irregularidades, buscando mantener la fiabilidad de los datos almacenados.

## 3. Tecnologías utilizadas

Para el desarrollo del proyecto se han seleccionado diferentes herramientas y tecnologías, buscando facilitar la comunicación entre los distintos componentes de la aplicación.

- **Java:** Lenguaje principal para el desarrollo del backend y de la aplicación Android.
- **JavaScript, HTML y CSS:** Lenguajes utilizados para el desarrollo de la interfaz web, en caso de incorporarse.
- **Spring Boot:** Framework utilizado para desarrollar la API REST que comunica las aplicaciones con la base de datos.
- **Spring Data JPA:** Herramienta para gestionar las operaciones entre el backend y la base de datos.
- **Spring Security:** Framework destinado a la autenticación de usuarios y gestión de permisos.
- **MySQL:** Sistema gestor de base de datos relacional, encargado de almacenar los vehículos, usuarios y sus correspondientes historiales.
- **MySQL Workbench:** Herramienta utilizada para administrar y realizar consultas sobre la base de datos.
- **Android Studio:** Entorno de desarrollo principal para la aplicación Android.
- **Visual Studio Code:** Entorno de desarrollo utilizado principalmente para el backend y la aplicación web.
- **Maven:** Herramienta destinada a gestionar las dependencias y compilación del backend.
- **Git y GitHub:** Herramientas utilizadas para el control de versiones, almacenamiento del código y trabajo colaborativo.

## 4. Arquitectura del sistema

El proyecto se plantea mediante una arquitectura cliente-servidor, separando las aplicaciones utilizadas por los usuarios, el procesamiento de la información y su almacenamiento.

**Aplicación Android/Web → API REST Spring Boot → Base de datos MySQL**

Las aplicaciones cliente realizarán peticiones a la API desarrollada mediante Spring Boot, que será la encargada de gestionar las consultas, comprobar los permisos y comunicarse con la base de datos MySQL.

De esta manera, los clientes no tendrán acceso directo a la base de datos, permitiendo un mayor control sobre la información.

## 5. Requisitos previos

Para ejecutar AutentIFPcar será necesario disponer de un entorno compatible con las tecnologías utilizadas y acceso a la red donde se encuentre alojado el servidor.

### Dispositivo Android

Requisitos recomendados:

- Sistema operativo Android 10 o superior.
- Memoria RAM de 4 GB o superior.
- Almacenamiento libre de 500 MB o más.
- Conexión estable a Internet o a la red del servidor.
- Pantalla recomendada de 6 pulgadas o superior.

### Ordenador cliente

Para acceder a la versión web, en caso de estar disponible:

- Sistema operativo Windows, Linux o macOS.
- Navegador web actualizado (Chrome, Firefox, Edge, etc.).
- Procesador de 1 GHz o superior.
- Memoria RAM mínima de 1 GB.
- Resolución de pantalla mínima HD (720p).
- Acceso a la red del servidor.

### Servidor

Para ejecutar el backend y la base de datos se establecen los siguientes requisitos de referencia:

- Sistema operativo Windows o Linux de 64 bits.
- Procesador de 2 núcleos o 2 vCPU.
- Memoria RAM mínima de 4 GB.
- Almacenamiento SSD de 80 GB.
- Java JDK 17 o superior.
- MySQL Server.
- Aplicación Spring Boot configurada.
- Conexión de red estable.

Estos requisitos son orientativos y podrán ajustarse según las pruebas realizadas durante el desarrollo.

## 6. Instalación y configuración

Apartado destinado a explicar los pasos necesarios para instalar, configurar y ejecutar AutentIFPcar en un entorno de pruebas.

### 6.1. Clonar el repositorio

Pendiente de documentar los comandos para descargar el proyecto desde GitHub.

### 6.2. Configurar MySQL

Pendiente de incorporar las instrucciones para crear la base de datos, ejecutar los scripts SQL y cargar los datos de prueba.

### 6.3. Configurar Spring Boot

Pendiente de documentar la configuración del backend y su conexión con MySQL.

### 6.4. Ejecutar el backend

Pendiente de incorporar los comandos necesarios para iniciar el servidor.

### 6.5. Ejecutar la aplicación Android

Pendiente de documentar la instalación, configuración de conexión y ejecución de la aplicación desde Android Studio o un dispositivo compatible.

## 7. Usuarios y datos de prueba

Pendiente de documentar los usuarios de demostración, los roles disponibles y los vehículos utilizados durante las pruebas.

## 8. Endpoints principales de la API

Pendiente de documentar las rutas de la API REST, indicando las operaciones disponibles y los permisos necesarios para utilizarlas.

## 9. Pruebas de funcionamiento

Pendiente de incorporar las pruebas realizadas para comprobar el correcto funcionamiento de la aplicación, la comunicación con el servidor y las operaciones sobre la base de datos.

## 10. Estado actual del proyecto

**Pre-alpha (fase temprana de desarrollo).**

El proyecto se encuentra en desarrollo, por lo que algunas funcionalidades y decisiones técnicas podrán modificarse conforme avance su implementación.

La prioridad es conseguir una versión funcional que permita realizar consultas de vehículos, visualizar su historial y gestionar la información mediante los permisos correspondientes.

## 11. Equipo de desarrollo

- Silv3rsD
- lauura10
- Franer-Narf