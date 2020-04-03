package no.difi.bolagsverket.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IdentifierIdentifierValidatorServiceImplTest {

    private IdentifierValidatorServiceImpl target;

    @Before
    public void setUp() {
        target = new IdentifierValidatorServiceImpl();
    }

    @Test(expected = NullPointerException.class)
    public void validate_NullArgument_ShouldThrow() {
        target.validate(null);
    }

    @Test
    public void validate_EmptyIdentifier_ShouldFail() {
        assertFalse(target.validate(""));
    }

    @Test
    public void validate_BolagsverketId_ShouldSucceed() {
        assertTrue(target.validate("2021005489"));
    }

    @Test
    public void validate_InvalidLuhnComputation_ShouldFail() {
        assertFalse(target.validate("2021005488"));
    }

    @Test
    public void validate_ValidIdOnDashFormat_ShouldPass() {
        assertTrue(target.validate("202100-5489"));
    }

    @Test
    public void validate_LuhnFailOnDashFormat_ShouldFail() {
        assertFalse(target.validate("202100-5488"));
    }

    @Test
    public void validate_ValidIdentifierContainingSekelPrefix_ShouldSucceed() {
        assertTrue(target.validate("194910017930"));
    }

    @Test
    public void validate_SoleProprietorIdentifier_ShouldSucceed() {
        assertTrue(target.validate("194712158031001"));
    }

    @Test
    public void validate_TooLongIdentifier_ShouldFail() {
        assertFalse(target.validate("1947121580310011"));
    }
}
