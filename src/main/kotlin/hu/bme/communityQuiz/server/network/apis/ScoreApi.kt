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
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import hu.bme.communityQuiz.server.network.Paths

// ktor 0.9.x is missing io.ktor.locations.DELETE, this adds it.
// see https://github.com/ktorio/ktor/issues/288

fun Route.ScoreApi() {
    val gson = Gson()
    val empty = mutableMapOf<String, Any?>()

    get<Paths.listGlobalScores> {
        val exampleContentType = "application/xml"
        val exampleContentString = """<null>
          <id>aeiou</id>
          <category>aeiou</category>
          <point>1.3579</point>
        </null>"""
        
        when(exampleContentType) {
            "application/json" -> call.respond(gson.fromJson(exampleContentString, empty::class.java))
            "application/xml" -> call.respondText(exampleContentString, ContentType.Text.Xml)
            else -> call.respondText(exampleContentString)
        }
    }
    

    route("/score") {
        put {
            call.respond(HttpStatusCode.NotImplemented)
        }
    }
    
}
