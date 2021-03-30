/**
* Community Quiz
* This is the server for my community quiz game
*
* OpenAPI spec version: 1.0.0
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package hu.bme.communityQuiz.server.network.apis

import com.google.gson.Gson
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*

// ktor 0.9.x is missing io.ktor.locations.DELETE, this adds it.
// see https://github.com/ktorio/ktor/issues/288

fun Route.QuestionApi() {
    val gson = Gson()
    val empty = mutableMapOf<String, Any?>()

    route("/question") {
        put {
            call.respond(HttpStatusCode.NotImplemented)
        }
    }
    
}
