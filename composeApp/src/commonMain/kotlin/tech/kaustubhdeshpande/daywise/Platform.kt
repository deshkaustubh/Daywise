package tech.kaustubhdeshpande.daywise

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform