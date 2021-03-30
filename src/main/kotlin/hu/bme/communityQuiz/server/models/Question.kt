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
package hu.bme.communityQuiz.server.models

import java.util.*
import javax.persistence.*


@Entity
@NamedQueries(
    NamedQuery(
        name = "listQuestion",
        query = "FROM Question"
    )
)
@Table(name = "question")
/**
 * 
 * @param id 
 * @param category 
 * @param question 
 * @param rightAnswer 
 * @param wrongAnswer1 
 * @param wrongAnswer2 
 * @param wrongAnswer3 
 */
data class Question (
    @Id
    @Column(name="id", length = 255)
    val id: String = UUID.randomUUID().toString(),
    val category: String = "",
    val question: String = "",
    val rightAnswer: String = "",
    val wrongAnswer1: String = "",
    val wrongAnswer2: String = "",
    val wrongAnswer3: String = ""
) {

}

