package net.corda.pocjpmorgan.state

import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import net.corda.pocjpmorgan.schema.CompetitorSchemaV1

/**
 * A state must implement ContractState or one of its descendants.
 *
 * The CompetitorState object represents people competing in a Challenge. This objetct has the following properties:
 *
 * @param [challengeName] The Challenge's name.
 * @param [challengeYear] The Challenge's year.
 * @param [challengeRefCode] The Challenge's codRef.
 * @param [supervisor] The Supervisor party.
 * @param [assistant] The Assistant party. Provide support to the Supervisor party.
 *        Both, the Supervisor and Assistant, create Competitors on the Ledger.
 *
 * @param [name] The Competitor's name.
 * @param [surname] The Competitor's surname.
 * @param [employee] The Competitor's employee number.
 * @param [result] The result that the Competitor obtains in the Challenge.
 * @param [participants] The public keys of the involved parties managing the Challenge, Supervisor and Assistant.
 *        This property holds a list of the nodes which can "use" this state in a valid transaction. In this case, the
 *        Supervisor or the Assistant.
 *
 * @param [linearId] A unique id shared by all LinearState states representing the same agreement throughout history within
 *        the vaults of all parties. Verify methods should check that one input and one output share the id in a transaction,
 *        except at issuance/termination.
 */
data class CompetitorState(val challengeName: String,
                           val challengeYear: String,
                           val challengeRefCode: String,
                           val supervisor: Party,
                           val assistant: Party,
                           val name: String,
                           val surname: String,
                           val employee: String,
                           val result: Double,
                           override val participants: List<AbstractParty> = listOf(supervisor, assistant),
                           override val linearId: UniqueIdentifier = UniqueIdentifier())
           : LinearState, QueryableState {
    /**
     * Enumerate the schemas this state can export representations of itself as.
     */
    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(CompetitorSchemaV1)

    /**
     * Export a representation for the given schema.
     *
     * @param [schema] The schema of type [MappedSchema] to persist objects in the database.
     */
    override fun generateMappedObject(schema: MappedSchema) : PersistentState {
        return when(schema) {
            is CompetitorSchemaV1 -> CompetitorSchemaV1.PersistentCompetitor(this.challengeName,
                                                                             this.challengeYear,
                                                                             this.challengeRefCode,
                                                                             this.supervisor.name.toString(),
                                                                             this.assistant.name.toString(),
                                                                             this.name,
                                                                             this.surname,
                                                                             this.employee,
                                                                             this.result,
                                                                             this.linearId.id)

            else -> throw IllegalArgumentException("Unrecognised schema $schema")
        }
    }
}