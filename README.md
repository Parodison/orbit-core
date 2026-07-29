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

OrbitCore publishes artifacts for **JVM, Android, iOS (arm64 and simulator arm64), Linux
(x64), JS and Wasm/JS**, so any of them can be consumed directly as a Kotlin dependency.

JS and Wasm/JS bindings are published, but their JS/TS-idiomatic ergonomics (clean TypeScript
types instead of raw Kotlin types like `Instant` in exported signatures) aren't finished yet —
the artifacts exist, direct JS/TS interop is still a work in progress.

## Installation

OrbitCore is published on [Maven Central](https://repo1.maven.org/maven2/com/parodison/orbit-core/) as `com.parodison:orbit-core`.

Version catalog (`libs.versions.toml`):

```toml
[versions]
orbit-core = "0.1.0"

[libraries]
orbit-core = { module = "com.parodison:orbit-core", version.ref = "orbit-core" }
```

Then, in your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation(libs.orbit.core)
}
```

Or without a version catalog:

```kotlin
dependencies {
    implementation("com.parodison:orbit-core:0.1.0")
}
```

## License

Apache License 2.0 — see [LICENSE](LICENSE).
