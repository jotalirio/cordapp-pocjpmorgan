package net.corda.pocjpmorgan.state

import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.pocjpmorgan.common.UtilsTests
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CompetitorStateTests : UtilsTests() {

    @Test
    fun `CompetitorState is LinearState`() {
        assert(LinearState::class.java.isAssignableFrom(CompetitorState::class.java))
    }

    @Test
    fun `Has CompetitorState a linearId field of correct type`() {
        // Does the linearId field exist?
        CompetitorState::class.java.getDeclaredField("linearId")
        // Is the linearId field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("linearId").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("linearId").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("linearId").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("linearId").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("linearId").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("linearId").type, Byte::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("linearId").type, String::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("linearId").type, UniqueIdentifier::class.java)
    }

    @Test
    fun `Has CompetitorState a challengeName field of correct type`() {
        // Does the challengeName field exist?
        CompetitorState::class.java.getDeclaredField("challengeName")
        // Is the challengeName field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeName").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeName").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeName").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeName").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeName").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeName").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("challengeName").type, String::class.java)
    }

    @Test
    fun `Has CompetitorState a challengeYear field of correct type`() {
        // Does the challengeYear field exist?
        CompetitorState::class.java.getDeclaredField("challengeYear")
        // Is the challengeYear field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeYear").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeYear").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeYear").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeYear").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeYear").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeYear").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("challengeYear").type, String::class.java)
    }

    @Test
    fun `Has CompetitorState a challengeCodRef field of correct type`() {
        // Does the challengeRefCode field exist?
        CompetitorState::class.java.getDeclaredField("challengeRefCode")
        // Is the challengeRefCode field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeRefCode").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeRefCode").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeRefCode").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeRefCode").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeRefCode").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("challengeRefCode").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("challengeRefCode").type, String::class.java)
    }

    @Test
    fun `Has CompetitorState a supervisor field of correct type`() {
        // Does the supervisor field exist?
        CompetitorState::class.java.getDeclaredField("supervisor")
        // Is the supervisor field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("supervisor").type, String::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("supervisor").type, Party::class.java)
    }

    @Test
    fun `Has CompetitorState a assistant field of correct type`() {
        // Does the assistant field exist?
        CompetitorState::class.java.getDeclaredField("assistant")
        // Is the assistant field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("assistant").type, String::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("assistant").type, Party::class.java)
    }

    @Test
    fun `Has CompetitorState a name field of correct type`() {
        // Does the name field exist?
        CompetitorState::class.java.getDeclaredField("name")
        // Is the name field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("name").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("name").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("name").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("name").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("name").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("name").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("name").type, String::class.java)
    }

    @Test
    fun `Has CompetitorState a surname field of correct type`() {
        // Does the surname field exist?
        CompetitorState::class.java.getDeclaredField("surname")
        // Is the surname field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("surname").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("surname").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("surname").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("surname").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("surname").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("surname").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("surname").type, String::class.java)
    }

    @Test
    fun `Has CompetitorState a gender field of correct type`() {
        // Does the gender field exist?
        CompetitorState::class.java.getDeclaredField("gender")
        // Is the gender field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("gender").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("gender").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("gender").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("gender").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("gender").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("gender").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("gender").type, String::class.java)
    }

    @Test
    fun `Has CompetitorState a employee field of correct type`() {
        // Does the employee field exist?
        CompetitorState::class.java.getDeclaredField("employee")
        // Is the employee field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("employee").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("employee").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("employee").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("employee").type, String::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("employee").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("employee").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("employee").type, Int::class.java)
    }

    @Test
    fun `Has CompetitorState a place field of correct type`() {
        // Does the place field exist?
        CompetitorState::class.java.getDeclaredField("place")
        // Is the place field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("place").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("place").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("place").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("place").type, String::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("place").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("place").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("place").type, Int::class.java)
    }

    @Test
    fun `Has CompetitorState a genderPlace field of correct type`() {
        // Does the genderPlace field exist?
        CompetitorState::class.java.getDeclaredField("genderPlace")
        // Is the genderPlace field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("genderPlace").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("genderPlace").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("genderPlace").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("genderPlace").type, String::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("genderPlace").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("genderPlace").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("genderPlace").type, Int::class.java)
    }

    @Test
    fun `Has CompetitorState a bib field of correct type`() {
        // Does the bib field exist?
        CompetitorState::class.java.getDeclaredField("bib")
        // Is the bib field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("bib").type, Double::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("bib").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("bib").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("bib").type, String::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("bib").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("bib").type, Byte::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("bib").type, Int::class.java)
    }

    @Test
    fun `Has CompetitorState a result field of correct type`() {
        // Does the result field exist?
        CompetitorState::class.java.getDeclaredField("result")
        // Is the result field of the correct type?
        assertNotEquals(CompetitorState::class.java.getDeclaredField("result").type, Float::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("result").type, Long::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("result").type, Int::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("result").type, Short::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("result").type, Byte::class.java)
        assertNotEquals(CompetitorState::class.java.getDeclaredField("result").type, String::class.java)
        assertEquals(CompetitorState::class.java.getDeclaredField("result").type, Double::class.java)
    }

    @Test
    fun `Supervisor is participant`() {
        assertNotEquals(competitorState.participants.indexOf(josepAlvarez.party), -1)
    }

    @Test
    fun `Assistant is participant`() {
        assertNotEquals(competitorState.participants.indexOf(jorgeLesmes.party), -1)
    }

    @Test
    fun `Check CompetitorState parameters order`() {
        // Fields
        val fields = CompetitorState::class.java.declaredFields
        val challengeNamePosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("challengeName"))
        val challengeYearPosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("challengeYear"))
        val challengeRefCodePosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("challengeRefCode"))
        val supervisorPosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("supervisor"))
        val assistantPosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("assistant"))
        val namePosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("name"))
        val surnamePosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("surname"))
        val employeePosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("employee"))
        val resultPosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("result"))
        val participantsX = fields.indexOf(CompetitorState::class.java.getDeclaredField("participants"))
        val linearIdPosX = fields.indexOf(CompetitorState::class.java.getDeclaredField("linearId"))
        // Asserts
        assert(challengeNamePosX < challengeYearPosX)
        assert(challengeYearPosX < challengeRefCodePosX)
        assert(challengeRefCodePosX < supervisorPosX)
        assert(supervisorPosX < assistantPosX)
        assert(assistantPosX < namePosX)
        assert(namePosX < surnamePosX)
        assert(surnamePosX < employeePosX)
        assert(employeePosX < resultPosX)
        assert(resultPosX < participantsX)
        assert(participantsX < linearIdPosX)
    }
}