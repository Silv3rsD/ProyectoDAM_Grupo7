# AutentIFPcar — Documento maestro de pruebas y desarrollo Android

**Proyecto:** AutentIFPcar — Proyecto Intermodular DAM  
**Ámbito:** Aplicación Android (`mobile-app/AutentIFPcar/`)  
**Fases documentadas:** 5 — Integración y pruebas; 6 — Historial y funcionalidades adicionales  
**Registro de pruebas inicial:** 05/11/2026  
**Última actualización del documento:** 10/11/2026  
**Rama de referencia:** `mobile-app`  
**Commit de referencia de la Fase 6:** `ff427a9a87b1baffce7d83ad100de921205f03a9`

## 1. Propósito y estado general

Este documento reúne el registro de pruebas de la Fase 5 y el cierre técnico de la Fase 6. Su finalidad es conservar los resultados obtenidos, los cambios implementados, las incidencias resueltas y las tareas pendientes, de forma que puedan consultarse y reutilizarse en los entregables del TFG.

| Fase | Estado | Alcance comprobado |
|---|---|---|
| **5 — Integración y pruebas** | **En desarrollo** | Pruebas unitarias, prueba instrumentada y cinco escenarios HTTP con servidor simulado superados. **Integración con Spring Boot y MySQL pendiente.** |
| **6 — Historial y funcionalidades adicionales** | **Cerrada en el alcance del prototipo Android** | Modelo `HistoryRecord`, interfaz XML y visualización dinámica de registros ficticios implementados y probados. **Historial real pendiente.** |

> **Criterio de documentación:** «Superada» significa que se ha confirmado la prueba indicada, no que exista una integración completa con el backend. Los registros ficticios son exclusivamente de demostración.

## 2. Entorno y metodología de pruebas

| Componente | Configuración utilizada |
|---|---|
| Lenguaje | Java |
| IDE | Android Studio |
| Emulador | Medium Phone, API 35 |
| Gradle | 8.13 |
| Android Gradle Plugin | 8.13.2 |
| JDK para ejecutar Gradle | 17 |
| Compatibilidad Java del proyecto | 11 |
| Pruebas unitarias | JUnit 4 |
| Pruebas instrumentadas | AndroidX Test |
| Pruebas de comunicación | Servidor HTTP local simulado con Python |
| Dirección del servidor accesible desde el emulador | `http://10.0.2.2:8080` |

Se han empleado tres tipos de comprobación: pruebas unitarias ejecutadas por Gradle, una prueba instrumentada en emulador y pruebas manuales del cliente HTTP mediante respuestas simuladas. La Fase 6 también incorpora comprobación visual manual de la tarjeta de historial.

---

## 3. Fase 5 — Integración y pruebas

### 3.1. Objetivo

Verificar el comportamiento del cliente Android ante matrículas válidas e inválidas, la creación y presentación de vehículos y las respuestas HTTP del servidor, sin dar por implementada una API de producción que todavía no se ha validado.

### 3.2. Registro de pruebas

| ID | Tipo | Descripción | Resultado |
|---|---|---|---|
| 5.1.1 | Unitaria | Creación de `Vehicle` y comprobación de getters | **Superada** |
| 5.1.2 | Unitaria | Normalización y validación de matrículas | **Superada** |
| 5.2.1 | Instrumentada | Matrícula incorrecta en `MainActivity`, sin realizar petición | **Superada** |
| 5.3.1 | Manual HTTP | Servidor desconectado | **Superada** |
| 5.3.2 | Manual HTTP | Respuesta HTTP 404 | **Superada** |
| 5.3.3 | Manual HTTP | Respuesta HTTP 400 | **Superada** |
| 5.3.4 | Manual HTTP | HTTP 200 con JSON completo | **Superada** |
| 5.3.5 | Manual HTTP | HTTP 200 con JSON incompleto | **Superada** |

**Alcance de la evidencia:** los resultados unitarios e instrumentados fueron confirmados mediante ejecuciones con `BUILD SUCCESSFUL`; los cinco escenarios HTTP se confirmaron manualmente con un servidor de pruebas. Las capturas definitivas todavía pueden incorporarse al entregable académico.

### 3.3. Descripción de las pruebas HTTP

**5.3.1 — Servidor desconectado.** Se buscó un vehículo sin un servidor accesible. La aplicación mostró un error de conexión y permaneció operativa.

**5.3.2 — HTTP 404.** Un servidor simulado devolvió 404. La aplicación informó de que el vehículo no estaba encontrado o publicado.

**5.3.3 — HTTP 400.** El servidor simulado respondió con 400 ante una matrícula que sí superaba la validación local. La aplicación mostró el error correspondiente al rechazo de la petición.

**5.3.4 — HTTP 200 con JSON válido.** El servidor simulado proporcionó `vin`, `licensePlate`, `brand`, `model`, `manufacturingYear` y `currentMileage`. `VehicleApi` interpretó el JSON, construyó un objeto `Vehicle` y `MainActivity` mostró su ficha.

**5.3.5 — HTTP 200 con JSON incompleto.** Se eliminó `currentMileage` de la respuesta ficticia. Aunque el código HTTP seguía siendo 200, la aplicación detectó la ausencia del campo obligatorio, informó de que la ficha era incompleta y no se cerró inesperadamente.

### 3.4. Incidencias técnicas resueltas

| Incidencia | Causa identificada | Solución aplicada |
|---|---|---|
| Gradle no creaba correctamente las tareas de prueba | Uso de JDK 25 con la configuración Gradle 8.13 del entorno | Configuración de JDK 17 |
| No se encontraba `AndroidJUnitRunner` | Dependencia del ejecutor ausente | Incorporación de `androidx.test:runner:1.7.0` |
| El test no podía iniciar `MainActivity` | El `Intent` se lanzaba desde un contexto no asociado a una actividad | Incorporación de `Intent.FLAG_ACTIVITY_NEW_TASK` |

Tras estas correcciones, la prueba instrumentada `showsValidationErrorWithoutARequest()` terminó con `BUILD SUCCESSFUL` y **1 prueba ejecutada**.

### 3.5. Estado de la Fase 5

Las pruebas locales y las de comunicación con servidor simulado están superadas. **No se considera cerrada la integración completa**, porque falta comprobar las operaciones contra el backend real de Spring Boot y la base de datos MySQL.

---

## 4. Fase 6 — Historial y funcionalidades adicionales

### 4.1. Objetivo y etapas

Incorporar una base sencilla para representar y visualizar acontecimientos asociados a un vehículo, conservando la búsqueda por matrícula y preparando el cliente para una futura API de historial.

| Etapa | Trabajo realizado | Estado |
|---|---|---|
| 6.1 | `HistoryRecord.java` y `HistoryRecordTest.java` | **Completada** |
| 6.2 | Tarjeta del historial en `activity_main.xml` y textos en `strings.xml` | **Completada** |
| 6.3 | Visualización dinámica desde `MainActivity.java` | **Completada** |
| 6.4 | Registro de pruebas, alcance y cierre técnico | **Completada mediante este documento** |

### 4.2. Modelo `HistoryRecord` — 6.1

Se implementó la clase `HistoryRecord.java`, con los atributos `private final String type`, `date` y `description`, un constructor y getters. Es un **modelo de presentación de Android**, no una entidad JPA ni una reproducción de las tablas MySQL.

`HistoryRecordTest.java` comprueba mediante `assertEquals()` que los tres getters devuelven los valores proporcionados al constructor.

### 4.3. Interfaz XML — 6.2

En `activity_main.xml` se incorporó una tarjeta de historial bajo la ficha del vehículo con los siguientes identificadores:

- `history_section`: contenedor de toda la tarjeta.
- `history_status`: texto informativo sobre el estado del historial.
- `history_list`: contenedor de los registros creados dinámicamente.

La sección se oculta inicialmente con `android:visibility="gone"` y se muestra durante la edición visual con `tools:visibility="visible"`. Se añadieron las cadenas `history_title`, `history_pending` y `history_demo_warning` a `strings.xml`. Se reutilizó el estilo visual de las tarjetas existentes.

### 4.4. Visualización dinámica con Java — 6.3

`MainActivity` enlaza los componentes mediante `findViewById()`. Al iniciar una nueva búsqueda oculta la ficha y el historial anteriores y vacía el contenedor `history_list`.

El método `showDemoHistory()` crea dos objetos `HistoryRecord`, los recorre con un `for` y genera un `TextView` para cada registro. La demostración se activa únicamente cuando la ficha recibida coincide con **matrícula `1234ABC` y VIN `WVWZZZ1JZXW000001`**. Para otros vehículos se indica que el historial todavía no está conectado al servidor.

| Tipo ficticio | Fecha | Descripción de demostración |
|---|---|---|
| ITV | 2026-03-18 | Inspección favorable |
| Reparación | 2026-02-03 | Sustitución de embrague |

**Advertencia:** estos registros se crean directamente en Android y están identificados en pantalla como **datos ficticios**. No son historiales obtenidos de la API ni datos reales del vehículo.

### 4.5. Pruebas de la Fase 6

| Comprobación | Procedimiento / evidencia | Resultado |
|---|---|---|
| Modelo `HistoryRecord` | Test unitario de los tres getters | **Superada** |
| Compilación de la interfaz (6.2) | `:app:assembleDebug` → `BUILD SUCCESSFUL` | **Superada** |
| Compilación de la visualización (6.3) | `:app:assembleDebug` → `BUILD SUCCESSFUL` | **Superada** |
| Regresión unitaria tras cambios (6.3) | `:app:testDebugUnitTest` → `BUILD SUCCESSFUL` | **Superada** |
| Visualización del historial | Comprobación manual en el emulador de la ficha y los dos registros ficticios | **Superada** |
| Nueva búsqueda | Comprobación manual de ocultación y limpieza del resultado anterior | **Superada** |

El informe JUnit verificado después de incorporar `HistoryRecordTest` reflejó **4 pruebas unitarias, 0 fallos y 0 omitidas**: una prueba de `Vehicle`, dos de normalización/validación de matrículas y una de `HistoryRecord`. La prueba instrumentada de la Fase 5 se contabiliza por separado.

**Límites de evidencia:** no se ha aportado una prueba instrumentada específica del historial ni capturas definitivas para este documento. Las verificaciones visuales proceden de pruebas manuales confirmadas durante el desarrollo.

### 4.6. Observaciones de compilación

- Se observó una advertencia sobre el uso o sobrescritura de una API obsoleta en `MainActivity.java`; la compilación terminó correctamente. Queda pendiente identificar la llamada concreta antes de sustituirla.
- Gradle notificó el uso de características obsoletas de cara a versiones futuras (como Gradle 9.0); no fue necesario actualizar Gradle para superar las tareas con 8.13.

### 4.7. Archivos de la Fase 6

Rutas relativas a `mobile-app/AutentIFPcar/`:

| Archivo | Responsabilidad |
|---|---|
| `app/src/main/java/com/autentifpcar/mobile/HistoryRecord.java` | Modelo de un registro |
| `app/src/test/java/com/autentifpcar/mobile/HistoryRecordTest.java` | Prueba unitaria del modelo |
| `app/src/main/java/com/autentifpcar/mobile/MainActivity.java` | Visualización y limpieza de la lista |
| `app/src/main/res/layout/activity_main.xml` | Tarjeta del historial |
| `app/src/main/res/values/strings.xml` | Textos de la interfaz |

**Referencia verificada de código:** commit `ff427a9a87b1baffce7d83ad100de921205f03a9` en la rama `mobile-app`.

### 4.8. Estado de la Fase 6

**Cerrada exclusivamente para el prototipo Android**: el modelo, la interfaz y su representación dinámica están implementados y probados con datos de demostración. El historial real todavía no está implementado ni validado.

---

## 5. Pendientes comunes y próximos pasos

| Prioridad | Tarea | Componentes afectados | Estado |
|---|---|---|---|
| Alta | Acordar con el equipo de backend el contrato REST del historial | Backend, API, Android | Pendiente |
| Alta | Consultar datos reales desde MySQL a través de Spring Boot | Base de datos, backend, API | Pendiente |
| Alta | Sustituir los registros de `showDemoHistory()` por datos de la API | `VehicleApi`, `MainActivity` y modelos según el contrato | Pendiente |
| Alta | Probar con el backend real los códigos HTTP, datos válidos/incompletos y listas vacías | API, Android, pruebas | Pendiente |
| Alta | Acordar permisos de consulta y validación de registros, especialmente multas y documentos | Backend, seguridad, API, clientes | Pendiente |
| Media | Incorporar capturas e informes definitivos como evidencias del TFG | Documentación | Pendiente |
| Media | Considerar una prueba automatizada específica del historial si aporta valor | Pruebas Android | Recomendado |
| Media | Revisar advertencias de APIs y Gradle sin actualizar dependencias innecesariamente | Android/Gradle | Recomendado |
| Cuando proceda | Revisar y fusionar `mobile-app` hacia `main` mediante Pull Request | Git/GitHub | Pendiente de confirmar |

La integración del historial deberá respetar la arquitectura prevista: **Android → API REST → Spring Boot → MySQL**. No se recomienda conectar Android directamente a MySQL.

## 6. Trazabilidad y conservación de evidencias

**Comandos empleados:**

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:testDebugUnitTest
.\gradlew.bat :app:connectedDebugAndroidTest
```

**Informes locales generados por Gradle:**

- `app/build/reports/tests/testDebugUnitTest/`
- `app/build/reports/androidTests/connected/debug/`

Las carpetas `build/` y `.gradle/` son generadas y están excluidas del control de versiones mediante `.gitignore`. Si el TFG requiere evidencias, conviene conservar capturas o exportaciones seleccionadas en una carpeta de documentación, en lugar de subir toda la salida temporal de Gradle.

## 7. Conclusión general

El cliente Android de AutentIFPcar ha superado las pruebas locales descritas de validación, creación de objetos, ejecución instrumentada y tratamiento de respuestas HTTP simuladas. Además, se ha desarrollado y probado un **prototipo de historial dinámico** con modelo Java, interfaz XML y registros ficticios claramente identificados.

**La Fase 6 queda documentada y cerrada en el alcance del prototipo Android. La Fase 5 permanece abierta respecto a la integración real.** La consulta de historiales reales desde Spring Boot y MySQL no debe presentarse como una funcionalidad implementada hasta disponer de la API y superar las correspondientes pruebas de integración.

**Repositorio:** https://github.com/Silv3rsD/ProyectoDAM_Grupo7
