package com.parodison.orbit.core.sgp4

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Instant

/**
 * Verification vectors generated with `sgp4` (Brandon Rhodes), the Python port of the
 * Vallado/Crawford/Hujsak/Kelso (2006) reference propagator — the same Spacetrack Report #3
 * revision this engine implements. They cover the four supported regimes: near-earth (pure
 * SGP4), and the three deep-space cases (SDP4): 24h synchronous resonance (GEO), 12h
 * semi-diurnal resonance with low eccentricity (MEO/GPS), and with high eccentricity
 * (Molniya-type HEO).
 *
 * Generated with `opsmode='i'`, which uses the same Greenwich sidereal time formula
 * ("gstime") as [com.parodison.orbit.core.sgp4.time.greenwichSiderealTime] in this module.
 */
private val EPOCH = Instant.parse("2026-01-01T00:00:00Z")
private const val DEG2RAD = PI / 180.0
private const val REV_DAY_TO_RAD_MIN = 2.0 * PI / 1440.0

private const val POSITION_TOLERANCE_KM = 1.0e-5
private const val VELOCITY_TOLERANCE_KM_S = 1.0e-8

private fun engine(
    bstar: Double,
    eccentricity: Double,
    argPerigeeDeg: Double,
    inclinationDeg: Double,
    meanAnomalyDeg: Double,
    meanMotionRevPerDay: Double,
    raanDeg: Double,
) = SGP4Engine(
    epoch = EPOCH,
    meanMotionRadPerMin = meanMotionRevPerDay * REV_DAY_TO_RAD_MIN,
    eccentricity = eccentricity,
    inclinationRad = inclinationDeg * DEG2RAD,
    raanRad = raanDeg * DEG2RAD,
    argPerigeeRad = argPerigeeDeg * DEG2RAD,
    meanAnomalyRad = meanAnomalyDeg * DEG2RAD,
    bstar = bstar,
)

private fun assertMatchesReference(
    engine: SGP4Engine,
    minutesSinceEpoch: Double,
    expectedPositionKm: Triple<Double, Double, Double>,
    expectedVelocityKmS: Triple<Double, Double, Double>,
) {
    val state = engine.propagate(EPOCH + (minutesSinceEpoch * 60.0).toDuration())
    val (ex, ey, ez) = expectedPositionKm
    val (evx, evy, evz) = expectedVelocityKmS

    assertTrue(
        kotlin.math.abs(state.position.x - ex) < POSITION_TOLERANCE_KM &&
            kotlin.math.abs(state.position.y - ey) < POSITION_TOLERANCE_KM &&
            kotlin.math.abs(state.position.z - ez) < POSITION_TOLERANCE_KM,
        "t=$minutesSinceEpoch min: posición esperada ($ex, $ey, $ez), obtenida " +
            "(${state.position.x}, ${state.position.y}, ${state.position.z})",
    )
    assertTrue(
        kotlin.math.abs(state.velocity.x - evx) < VELOCITY_TOLERANCE_KM_S &&
            kotlin.math.abs(state.velocity.y - evy) < VELOCITY_TOLERANCE_KM_S &&
            kotlin.math.abs(state.velocity.z - evz) < VELOCITY_TOLERANCE_KM_S,
        "t=$minutesSinceEpoch min: velocidad esperada ($evx, $evy, $evz), obtenida " +
            "(${state.velocity.x}, ${state.velocity.y}, ${state.velocity.z})",
    )
}

private fun Double.toDuration() = kotlin.time.Duration.parse("${this}s")

class SGP4EngineLeoTest {
    private val sat = engine(
        bstar = 0.0001, eccentricity = 0.0012, argPerigeeDeg = 45.0, inclinationDeg = 51.6,
        meanAnomalyDeg = 10.0, meanMotionRevPerDay = 15.5, raanDeg = 120.0,
    )

    @Test
    fun atEpoch() = assertMatchesReference(
        sat, 0.0,
        Triple(-4934.710737885851, 1645.5835112348868, 4350.704458469322),
        Triple(0.7766253980140571, -6.809991083345481, 3.4499247488153952),
    )

    @Test
    fun plus30Minutes() = assertMatchesReference(
        sat, 30.0,
        Triple(2812.947084241724, -6140.502309330859, 784.2546581910107),
        Triple(4.644596157341724, 1.3534493737430568, -5.935596935823018),
    )

    @Test
    fun plus120Minutes() = assertMatchesReference(
        sat, 120.0,
        Triple(1939.217179358858, -6267.737466940261, 1775.5121917359259),
        Triple(5.164499749235754, -0.02186433183389596, -5.656792232675061),
    )

    @Test
    fun plus720Minutes() = assertMatchesReference(
        sat, 720.0,
        Triple(-562.7472742950678, 6107.563606502022, -2928.7276160455503),
        Triple(-5.4670726949944735, 1.8892958003670486, 5.01945637357884),
    )

    @Test
    fun minus60Minutes() = assertMatchesReference(
        sat, -60.0,
        Triple(3580.5446259188166, -5778.365323484796, -235.95234467364446),
        Triple(3.939438987657595, 2.6710530774135814, -5.9952626287243715),
    )
}

class SGP4EngineGeoTest {
    private val sat = engine(
        bstar = 0.0, eccentricity = 0.0005, argPerigeeDeg = 60.0, inclinationDeg = 0.05,
        meanAnomalyDeg = 15.0, meanMotionRevPerDay = 1.00273, raanDeg = 200.0,
    )

    @Test
    fun atEpoch() = assertMatchesReference(
        sat, 0.0,
        Triple(3686.178533987312, -41981.896346741814, -7.890498175677122),
        Triple(3.0644661808130182, 0.2686744657960053, 0.0011809334423745588),
    )

    @Test
    fun plus60Minutes() = assertMatchesReference(
        sat, 60.0,
        Triple(14465.29212553357, -39585.359402056754, -4.063330555960595),
        Triple(2.889473443371279, 1.0550520111331336, 0.0012733765071792772),
    )

    @Test
    fun plus360Minutes() = assertMatchesReference(
        sat, 360.0,
        Triple(41988.589763080454, 3900.7285929170343, 13.670010616415567),
        Triple(-0.2828959477946659, 3.061283210148127, 0.000797073146771604),
    )

    @Test
    fun plus1440Minutes() = assertMatchesReference(
        sat, 1440.0,
        Triple(4415.806488640485, -41911.46898750357, -15.270433743585883),
        Triple(3.0593275953441434, 0.3219081206603006, 0.0003906649943559698),
    )

    @Test
    fun plus4320Minutes() = assertMatchesReference(
        sat, 4320.0,
        Triple(5871.773854362025, -41732.49907899202, -12.980909550651822),
        Triple(3.0462663785682507, 0.42813530028709923, -0.0005213775289915459),
    )

    @Test
    fun minus720Minutes() = assertMatchesReference(
        sat, -720.0,
        Triple(-3303.523370293516, 42054.87078740498, 0.6931758371122905),
        Triple(-3.0637643384452597, -0.241050981309549, -0.00139236534686464),
    )
}

class SGP4EngineMeoTest {
    private val sat = engine(
        bstar = 0.0, eccentricity = 0.01, argPerigeeDeg = 90.0, inclinationDeg = 55.0,
        meanAnomalyDeg = 30.0, meanMotionRevPerDay = 2.00561, raanDeg = 15.0,
    )

    @Test
    fun atEpoch() = assertMatchesReference(
        sat, 0.0,
        Triple(-16303.31845449055, 9093.464440790858, 18561.646190973042),
        Triple(-2.9671299979010772, -1.9661508695185772, -1.6141121233541356),
    )

    @Test
    fun plus60Minutes() = assertMatchesReference(
        sat, 60.0,
        Triple(-24243.804705302093, 1089.8369520090648, 10461.428973788092),
        Triple(-1.3433373976052532, -2.3747225816703432, -2.7776758787874734),
    )

    @Test
    fun plus360Minutes() = assertMatchesReference(
        sat, 360.0,
        Triple(16353.516089177912, -9417.508717620973, -19023.32794404677),
        Triple(2.9187513475857076, 1.9292029831979436, 1.5818880740763994),
    )

    @Test
    fun plus720Minutes() = assertMatchesReference(
        sat, 720.0,
        Triple(-16659.86478806704, 8857.966781788755, 18361.33879024529),
        Triple(-2.9238688640740844, -1.9890573665354914, -1.6633738447401256),
    )

    @Test
    fun plus2160Minutes() = assertMatchesReference(
        sat, 2160.0,
        Triple(-17357.18182282775, 8379.082112850072, 17942.78088066138),
        Triple(-2.834580424942574, -2.0329834364578683, -1.7602427883917384),
    )

    @Test
    fun minus360Minutes() = assertMatchesReference(
        sat, -360.0,
        Triple(15997.775669119821, -9645.842228430422, -19213.722643108893),
        Triple(2.959004118568448, 1.9061290996074014, 1.533514903441138),
    )
}

class SGP4EngineMolniyaHeoTest {
    private val sat = engine(
        bstar = 0.00002, eccentricity = 0.72, argPerigeeDeg = 270.0, inclinationDeg = 63.4,
        meanAnomalyDeg = 180.0, meanMotionRevPerDay = 2.00561, raanDeg = 100.0,
    )

    @Test
    fun atEpoch() = assertMatchesReference(
        sat, 0.0,
        Triple(-20123.216473524266, -3545.5519896523742, 40827.40442959492),
        Triple(0.27313744625796954, -1.542270226033979, -0.0006841185327342013),
    )

    @Test
    fun plus60Minutes() = assertMatchesReference(
        sat, 60.0,
        Triple(-18600.512113977347, -8948.939239058773, 39710.948709367614),
        Triple(0.5720487460465444, -1.4453665289136195, -0.6228857714160585),
    )

    @Test
    fun plus360Minutes() = assertMatchesReference(
        sat, 360.0,
        Triple(3167.665273427789, 1232.085469984992, -6660.73025178433),
        Triple(-1.8761085529699186, 9.374317352973263, 0.4398325668155677),
    )

    @Test
    fun plus720Minutes() = assertMatchesReference(
        sat, 720.0,
        Triple(-20093.833786609943, -3707.57292630535, 40827.17334970995),
        Triple(0.2816137256185504, -1.5406431043667668, -0.021385360843667385),
    )

    @Test
    fun minus180Minutes() = assertMatchesReference(
        sat, -180.0,
        Triple(-17626.763738026697, 12445.677050632285, 30341.588515054274),
        Triple(-0.8230013690119837, -1.2346224437643967, 2.0498187252295414),
    )
}
