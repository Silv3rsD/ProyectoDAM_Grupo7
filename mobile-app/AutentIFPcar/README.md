# AutentIFPcar Android

Proyecto Android mínimo en Java y XML. Una pantalla permite consultar la ficha pública de un vehículo por matrícula. No necesita iniciar sesión ni se conecta directamente a MySQL.

## Estructura

```text
AutentIFPcar/
├── app/
│   └── src/
│       ├── main/      Pantalla, recursos y manifiesto
│       └── debug/     Dirección local de la API y permiso HTTP local
├── gradle/wrapper/    Gradle Wrapper
├── app/build.gradle
└── settings.gradle
```

## Abrir y ejecutar

1. Abrir esta carpeta (`mobile-app/AutentIFPcar`) como proyecto en Android Studio.
2. Sincronizar Gradle y ejecutar la variante `debug` en un emulador Android.
3. Iniciar el backend en el ordenador, puerto 8080. La app debug llama a `http://10.0.2.2:8080`.
4. Buscar una matrícula publicada, por ejemplo `1234ABC`, una vez que exista el endpoint `GET /api/public/vehicles/plate/{plate}`.

Para un móvil físico, cambiar `app/src/debug/res/values/config.xml` por la IP local del servidor. Para publicar, configurar una URL HTTPS real en `app/src/main/res/values/config.xml`. El proyecto backend del repositorio todavía no implementa el endpoint, por lo que la búsqueda mostrará un error de conexión o HTTP hasta que se añada.

## Pruebas

- `app/src/test/`: pruebas unitarias de normalización y validación de matrículas. Ejecutar `./gradlew :app:testDebugUnitTest`.
- `app/src/androidTest/`: prueba de la pantalla y su mensaje de validación. Requiere emulador o dispositivo conectado; ejecutar `./gradlew :app:connectedDebugAndroidTest`.

Si la vista de diseño de Android Studio aparece vacía, sincronizar Gradle y usar **Refresh Layout** en el editor XML. La pantalla se puede comprobar también al ejecutar la variante `debug` en un emulador.
