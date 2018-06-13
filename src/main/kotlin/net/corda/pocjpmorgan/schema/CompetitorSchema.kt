package net.corda.pocjpmorgan.schema

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.pocjpmorgan.common.Constants
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


/**
 * The family of schemas for CompetitorState.
 */
object CompetitorSchema

/**
 * An CompetitorState schema.
 */
object CompetitorSchemaV1 : MappedSchema(schemaFamily = CompetitorSchema.javaClass,
                                         version = 1,
                                         mappedTypes = listOf(PersistentCompetitor::class.java)) {
    @Entity
    @Table(name = "competitor_states")
    class PersistentCompetitor(
            @Column(name = "challengeName")
            var challengeName: String,

            @Column(name = "challengeYear")
            var challengeYear: String,

            @Column(name = "challengeRefCod")
            var challengeRefCod: String,

            @Column(name = "supervisor")
            var supervisor: String,

            @Column(name = "assistant")
            var assistant: String,

            @Column(name = "name")
            var name: String,

            @Column(name = "surname")
            var surname: String,

            @Column(name = "employee")
            var employee: String,

            @Column(name = "result")
            var result: Double,

            @Column(name = "linear_id")
            var linearId: UUID
    ) : PersistentState() {
        // Default constructor required by hibernate.
        constructor(): this(Constants.EMPTY,
                            Constants.EMPTY,
                            Constants.EMPTY,
                            Constants.EMPTY,
                            Constants.EMPTY,
                            Constants.EMPTY,
                            Constants.EMPTY,
                            Constants.EMPTY,
                           0.0,
                            UUID.randomUUID())
    }
}





