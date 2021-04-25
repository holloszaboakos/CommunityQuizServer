package hu.bme.communityQuiz.server.network

import com.codahale.metrics.Slf4jReporter
import com.typesafe.config.ConfigFactory
import hu.bme.communityQuiz.server.models.HibernateManager
import hu.bme.communityQuiz.server.network.apis.QuizApi
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.config.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.metrics.dropwizard.*
import io.ktor.routing.*
import io.ktor.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime


@KtorExperimentalAPI
internal val settings = HoconApplicationConfig(ConfigFactory.defaultApplication(HTTP::class.java.classLoader))

object HTTP {
    val client = HttpClient(Apache)
}

@KtorExperimentalLocationsAPI
@KtorExperimentalAPI
@ExperimentalTime
fun Application.main() {
    install(DefaultHeaders)
    install(DropwizardMetrics) {
        val reporter = Slf4jReporter.forRegistry(registry)
                .outputTo(log)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build()
        reporter.start(10, TimeUnit.SECONDS)
    }
    install(ContentNegotiation) {
        register(ContentType.Application.Json, GsonConverter())
    }
    install(AutoHeadResponse) // see http://ktor.io/features/autoheadresponse.html
    install(HSTS, ApplicationHstsConfiguration()) // see http://ktor.io/features/hsts.html
    install(Compression, ApplicationCompressionConfiguration()) // see http://ktor.io/features/compression.html
    install(Locations) // see http://ktor.io/features/locations.html
    install(Routing) {
        QuizApi()
    }
    install(CORS){
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.ContentType)
        header(HttpHeaders.AccessControlAllowOrigin)
        anyHost()
        //maxAgeDuration = 1.0.toDuration(DurationUnit.DAYS)
        allowNonSimpleContentTypes = true
    }

    environment.monitor.subscribe(ApplicationStopping)
    {
        HTTP.client.close()
        HibernateManager.closeFactory()
    }
}