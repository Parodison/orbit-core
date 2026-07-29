<p align="center">
  <h1 align="center">OrbitCore</h1>
  <p align="center">Una librería de mecánica orbital en Kotlin Multiplatform — propagación SGP4/SDP4, predicción de pasadas y GeoJSON.</p>
</p>

<p align="center"><a href="README.md">Read in English</a></p>

<p align="center"><sub>Licenciado bajo <a href="LICENSE">Apache License 2.0</a></sub></p>

---

## ¿Qué es OrbitCore?

OrbitCore es una librería de propagación orbital de satélites escrita enteramente en Kotlin.
Le das los elementos orbitales de un satélite (de un TLE / del formato JSON GP de Celestrak) y
un instante en el tiempo, y te dice dónde está el satélite y cuándo y cómo se lo puede
ver desde cualquier punto de la Tierra.

Está construida como una librería Kotlin Multiplatform, así que el mismo código se puede
compilar para todas las plataformas que soporta Kotlin e integrarse en el sistema que
necesites — un backend en la JVM, una app Android, una app iOS, un servicio nativo en Linux, y
más, todos compartiendo exactamente la misma lógica de propagación y el mismo conjunto de tests.

## Características

- **Propagador SGP4 + SDP4** — un port fiel de la revisión de Vallado/Crawford/Hujsak/Kelso
  (2006) del Spacetrack Report #3 (WGS-72), con soporte tanto para órbitas near-earth (SGP4)
  como deep-space (SDP4: geoestacionarias, semi-sincrónicas tipo GPS, y de alta excentricidad
  tipo Molniya — todas necesitan perturbaciones lunisolares y resonancia geopotencial de 12h/24h
  para propagarse correctamente).
- **API de alto nivel `Satellite`** — posición y velocidad (TEME), punto subsatelital
  (lat/lon/altitud), velocidad orbital, período orbital, ángulos de observación
  (azimuth/elevación/rango) desde cualquier observador en tierra, visibilidad geométrica y a
  simple vista (considerando la luz solar y el crepúsculo del observador), y predicción de
  próximas pasadas (AOS/TCA/LOS con elevación máxima).
- **Ground tracks y círculos de cobertura**, expuestos tanto como listas de coordenadas planas
  como en formato **GeoJSON** listo para usar (`Feature` / `FeatureCollection`, RFC 7946) —
  MapLibre, Mapbox, Leaflet, Google Maps y prácticamente cualquier otra librería de mapas habla
  GeoJSON nativamente, así que no hace falta código de integración entre esta librería y un mapa.
- **Testeada contra vectores de referencia** generados con una implementación SGP4/SDP4
  independiente y bien establecida, cubriendo los regímenes near-earth, geoestacionario,
  semi-sincrónico y tipo Molniya — no solo "compila", se verifican los vectores de estado
  propagados en sí.

## Plataformas soportadas

Por ahora OrbitCore es Kotlin puro para proyectos Kotlin: **JVM, Android, iOS (arm64 y
simulador arm64), y Linux (x64)** están listos para consumirla directamente como dependencia
Kotlin.

JavaScript/TypeScript y Wasm están declarados como targets de build, pero sus bindings
(adaptación de tipos para un consumo idiomático desde JS/TS, empaquetado para Wasm) todavía no
están terminados — están planeados, pero no listos para usar.

## Estado

Esta librería está en desarrollo activo y **todavía no está publicada en Maven Central**. Las
instrucciones de instalación se van a agregar acá una vez que se publique una primera versión.

## Licencia

Apache License 2.0 — ver [LICENSE](LICENSE).
