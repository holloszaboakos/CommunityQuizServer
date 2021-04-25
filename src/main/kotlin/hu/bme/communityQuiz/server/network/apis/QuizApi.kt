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
import hu.bme.communityQuiz.server.models.HibernateManager
import hu.bme.communityQuiz.server.models.Question
import hu.bme.communityQuiz.server.models.Score
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import hu.bme.communityQuiz.server.network.Paths

@KtorExperimentalLocationsAPI
fun Route.QuizApi() {

    get<Paths.getCategories> {
        try {
            val scores = HibernateManager.list<Score>("Score")
            val categories = scores.map { it.category }
            call.respond(HttpStatusCode.OK, categories)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }


    get<Paths.getQuiz> {
        try {
            val category = call.parameters["category"] ?: ""
            val questions = HibernateManager.list<Question>("Question")
            call.respond(HttpStatusCode.OK, questions.filter { it.category.compareTo(category) == 0 })
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }


    get<Paths.listGlobalScores> {
        try {
            val scores = HibernateManager.list<Score>("Score")
            call.respond(HttpStatusCode.OK, scores)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }


    route("/question") {
        put {
            try {
                val question = call.receive<Question>()
                val scores = HibernateManager.list<Score>("Score")
                if (!scores.any { it.category.compareTo(question.category) == 0 })
                    HibernateManager.save(Score(category = question.category))
                HibernateManager.save(question)
                call.respond(HttpStatusCode.OK)
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }


    route("/score") {
        put {
            try {
                val score = call.receive<Score>()
                val scores = HibernateManager.list<Score>("Score")
                if (scores.any { it.category.compareTo(score.category) == 0 })
                    scores.find { it.category.compareTo(score.category) == 0 }
                        ?.let {
                            if (it.point < score.point) {
                                HibernateManager.update(
                                    Score(
                                        id = it.id,
                                        category = score.category,
                                        point = score.point
                                    )
                                )
                            }
                        }
                else
                    HibernateManager.save(score)
                call.respond(HttpStatusCode.OK)
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}
    

