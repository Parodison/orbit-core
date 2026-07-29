package com.parodison.orbit.core.sgp4

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.time.Instant

@OptIn(ExperimentalJsExport::class)
@JsExport
fun getSGP4Engine(
    epoch: Instant,
    meanMotionRadPerMin: Double,
    eccentricity: Double,
    inclinationRad: Double,
    raanRad: Double,
    argPerigeeRad: Double,
    meanAnomalyRad: Double,
    bstar: Double,
): SGP4Engine {
    return SGP4Engine(
        epoch,
        meanMotionRadPerMin,
        eccentricity,
        inclinationRad,
        raanRad,
        argPerigeeRad,
        meanAnomalyRad,
        bstar,
    )
}
