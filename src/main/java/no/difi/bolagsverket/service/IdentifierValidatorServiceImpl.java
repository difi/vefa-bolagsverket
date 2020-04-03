package no.difi.bolagsverket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * @see <a href="https://sv.wikipedia.org/wiki/Organisationsnummer"/>
 * @see <a href="https://sv.wikipedia.org/wiki/Luhn-algoritmen"/>
 */
@Slf4j
@Service
public class IdentifierValidatorServiceImpl implements IdentifierValidatorService {

    private static final Pattern pattern = Pattern.compile("^(18|19|20)([0-9]{10})([0-9]{3})$|^([0-9]{10})$");
    private static final int[] weights = {2, 1, 2, 1, 2, 1, 2, 1, 2, 1};

    @Override
    public boolean validate(String identifier) {
        log.info("Validating identifier '{}'.", identifier);
        String idWithoutDashes = identifier.replaceAll("\\-", "");
        if (!pattern.matcher(idWithoutDashes).matches()) {
            log.info("Regex validation failed.");
            return false;
        }
        log.info("Performing Luhn algorithm validation.");
        String idToCheckAgainstLuhnAlgorithm =
                15 == idWithoutDashes.length()
                        ? idWithoutDashes.substring(2, 12)
                        : idWithoutDashes;

        boolean luhnResult = performLuhnAlgorithmValidation(idToCheckAgainstLuhnAlgorithm);
        log.info("Validation result: {}", luhnResult);

        return luhnResult;
    }

    private boolean performLuhnAlgorithmValidation(String identifier) {
        log.info("Validating '{}' against the Luhn algorithm.", identifier);
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int t = Integer.valueOf(identifier.substring(i, i + 1)) * weights[i];
            sum += t > 9 ? t - 9 : t;
        }

        return sum % 10 == 0;
    }
}
