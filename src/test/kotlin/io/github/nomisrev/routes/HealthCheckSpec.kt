package io.github.nomisrev.routes

import io.github.nomisrev.PostgreSQLContainer
import io.github.nomisrev.config.Config
import io.github.nomisrev.config.dependencies
import io.github.nomisrev.resource
import io.github.nomisrev.withService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HealthCheckSpec :
  StringSpec({
    val config = Config().copy(dataSource = PostgreSQLContainer.config())
    val module by resource(dependencies(config))

    "healthy" {
      withService(module) {
        val response = client.get("/health")
        response.status shouldBe HttpStatusCode.OK
        response.bodyAsText() shouldBe Json.encodeToString(HealthCheck("14.1"))
      }
    }
  })