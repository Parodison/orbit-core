<p align="center">
  <h1 align="center">OrbitCore</h1>
  <p align="center">A Kotlin Multiplatform orbital mechanics library — SGP4/SDP4 propagation, pass prediction and GeoJSON.</p>
</p>

<p align="center"><a href="README.es.md">Leer en español</a></p>

<p align="center"><sub>Licensed under the <a href="LICENSE">Apache License 2.0</a></sub></p>

---

## What is OrbitCore?

OrbitCore is a satellite orbit propagation library written entirely in Kotlin. Give it a
satellite's orbital elements (from a TLE / Celestrak's JSON GP format) and an instant in time,
and it tells you where the satellite is — and whether, when, and how it can be seen from
anywhere on Earth.

It's built as a Kotlin Multiplatform library, so the same code can be compiled for every
platform Kotlin supports and integrated into whatever system you need — a JVM backend, an
Android app, an iOS app, a native Linux service, and more, all sharing the exact same
propagation logic and test suite.

## Features

- **SGP4 + SDP4 propagator** — a faithful port of the Vallado/Crawford/Hujsak/Kelso (2006)
  revision of Spacetrack Report #3 (WGS-72), supporting both near-earth orbits (SGP4) and
  deep-space orbits (SDP4: geostationary, GPS-like semi-synchronous, and highly-eccentric
  Molniya-type orbits, all of which need lunisolar perturbations and 12h/24h geopotential
  resonance to propagate correctly).
- **High-level `Satellite` API** — position and velocity (TEME), sub-satellite point
  (lat/lon/altitude), ground speed, orbital period, look angles (azimuth/elevation/range) from
  any observer on the ground, geometric and naked-eye visibility (accounting for sunlight and
  the observer's twilight), and next-pass prediction (AOS/TCA/LOS with maximum elevation).
- **Ground tracks and coverage footprints**, exposed both as plain coordinate lists and as
  ready-to-use **GeoJSON** (`Feature` / `FeatureCollection`, RFC 7946) — MapLibre, Mapbox,
  Leaflet, Google Maps and virtually every other map library speak GeoJSON natively, so
  there's no glue code needed between this library and a map.
- **Tested against reference vectors** generated from a well-established, independent SGP4/SDP4
  implementation, covering near-earth, geostationary, semi-synchronous and Molniya-type
  regimes — not just "it compiles," the actual propagated state vectors are checked.

## Supported platforms

Right now OrbitCore is pure Kotlin for Kotlin projects: **JVM, Android, iOS (arm64 and
simulator arm64), and Linux (x64)** are ready to consume it directly as a Kotlin dependency.

JavaScript/TypeScript and Wasm are declared as build targets, but their bindings (type
adaptation for idiomatic JS/TS consumption, Wasm packaging) aren't finished yet — they're
planned, not ready for use.

## Status

This library is under active development and **not yet published to Maven Central**.
Installation instructions will be added here once a first version is published.

## License

Apache License 2.0 — see [LICENSE](LICENSE).
