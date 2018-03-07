package no.difi.bolagsverket.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IdentifierValidatorServiceImplTest {

    private IdentifierValidatorServiceImpl target;

    @Before
    public void setUp() {
        target = new IdentifierValidatorServiceImpl();
    }

    @Test(expected = NullPointerException.class)
    public void testValidate_nullArgument_shouldThrow() {
        target.validate(null);
    }

    @Test
    public void testValidate_emptyIdentifier_shouldFail() {
        assertFalse(target.validate(""));
    }

    @Test
    public void testValidate_bolagsverketId_shouldSucceed() {
        assertTrue(target.validate("2021005489"));
    }

    @Test
    public void testValidate_invalidLuhnComputation_shouldFail() {
        assertFalse(target.validate("2021005488"));
    }

    @Test
    public void testValidate_validIdOnDashFormat_shouldPass() {
        assertTrue(target.validate("202100-5489"));
    }

    @Test
    public void testValidate_luhnFailOnDashFormat_shouldFail() {
        assertFalse(target.validate("202100-5488"));
    }
}
