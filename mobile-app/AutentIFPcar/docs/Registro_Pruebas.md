# AutentIFPcar — Registro de pruebas de Android

**Proyecto:** AutentIFPcar — Proyecto Intermodular DAM  
**Fase:** 5 — Integración y pruebas  
**Fecha de elaboración:** 05/11/2026  
**Estado:** En desarrollo

## 1. Objetivo

Verificar el funcionamiento de la aplicación Android de AutentIFPcar, especialmente la validación de matrículas, la presentación de información de vehículos y la gestión de respuestas HTTP.

Las pruebas se han organizado en pruebas unitarias, instrumentadas y manuales de comunicación con un servidor simulado.

## 2. Entorno de pruebas

| Componente | Configuración |
|---|---|
| Lenguaje | Java |
| Entorno | Android Studio |
| Emulador | Medium Phone, API 35 |
| Gradle | 8.13 |
| Android Gradle Plugin | 8.13.2 |
| JDK de ejecución de Gradle | 17 |
| Compatibilidad Java del proyecto | 11 |
| Framework de pruebas | JUnit 4 y AndroidX Test |
| Servidor para pruebas HTTP | Servidor Python local simulado |
| Dirección desde el emulador | http://10.0.2.2:8080 |

## 3. Pruebas realizadas

| ID | Tipo | Descripción | Resultado |
|---|---|---|---|
| 5.1.1 | Unitaria | Creación de Vehicle y comprobación de getters | Superada |
| 5.1.2 | Unitaria | Normalización y validación de matrículas | Superada |
| 5.2.1 | Instrumentada | Validación de matrícula incorrecta en MainActivity | Superada |
| 5.3.1 | Manual | Servidor desconectado | Superada |
| 5.3.2 | Manual | Respuesta HTTP 404 | Superada |
| 5.3.3 | Manual | Respuesta HTTP 400 | Superada |
| 5.3.4 | Manual | Respuesta HTTP 200 con JSON válido | Superada |
| 5.3.5 | Manual | Respuesta HTTP 200 con JSON incompleto | Superada |

Los resultados manuales se registran conforme a las comprobaciones realizadas durante el desarrollo. Las capturas y los registros de ejecución pueden incorporarse posteriormente como evidencias adicionales.

## 4. Descripción de las pruebas HTTP

**5.3.1 — Servidor desconectado**

Se realizó una búsqueda con el servidor inaccesible. La aplicación mostró un mensaje de error de conexión y permitió continuar utilizándola.

**5.3.2 — HTTP 404**

Se utilizó un servidor simulado para devolver un código HTTP 404. La aplicación informó de que el vehículo no se encontraba disponible.

**5.3.3 — HTTP 400**

Se configuró un servidor simulado para devolver HTTP 400 ante una matrícula válida para la validación local. La aplicación mostró el mensaje correspondiente al rechazo de la petición.

**5.3.4 — HTTP 200**

El servidor simulado devolvió un JSON con seis campos: VIN, matrícula, marca, modelo, año de fabricación y kilometraje.

La aplicación interpretó la respuesta, construyó un objeto Vehicle y mostró correctamente la ficha en pantalla.

**5.3.5 — JSON incompleto**

Se eliminó el campo `currentMileage` de la respuesta simulada, manteniendo el código HTTP 200.

La aplicación detectó la ausencia del dato obligatorio y mostró el mensaje de ficha incompleta, sin cerrarse inesperadamente.

## 5. Incidencias técnicas resueltas

| Incidencia | Causa identificada | Solución aplicada |
|---|---|---|
| Gradle no creaba las tareas de pruebas | Ejecución de Gradle 8.13 mediante JDK 25, no compatible oficialmente | Configuración de JDK 17 |
| AndroidJUnitRunner no se encontraba | Faltaba la dependencia del ejecutor de pruebas | Incorporación de `androidx.test:runner:1.7.0` |
| No se podía iniciar MainActivity desde el test | El Intent se iniciaba desde un contexto que requería una nueva tarea | Incorporación de `Intent.FLAG_ACTIVITY_NEW_TASK` |

Tras las correcciones, la prueba instrumentada `showsValidationErrorWithoutARequest()` terminó con `BUILD SUCCESSFUL` y una prueba ejecutada.

## 6. Pruebas pendientes

- Verificar las respuestas HTTP 200, 400 y 404 con el backend real de Spring Boot.
- Comprobar la recuperación de datos desde MySQL mediante el backend.
- Incorporar las evidencias y capturas definitivas necesarias para la documentación académica.

## 7. Conclusión provisional

Las pruebas realizadas con el servidor HTTP simulado muestran que la aplicación Android gestiona correctamente los escenarios ensayados de conexión, error HTTP y procesamiento de datos JSON.

La prueba instrumentada de validación de matrículas también se ha ejecutado satisfactoriamente.

**La integración completa con Spring Boot y MySQL permanece pendiente de verificación**, por lo que no se considera todavía finalizada la fase de integración del sistema.