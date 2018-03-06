package no.difi.bolagsverket.service;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * @see <a href="https://sv.wikipedia.org/wiki/Organisationsnummer"/>
 * @see <a href="https://sv.wikipedia.org/wiki/Luhn-algoritmen"/>
 */
@Slf4j
class IdentifierValidatorImpl implements ValidatorService {

    private static final Pattern pattern = Pattern.compile("[0-9]{10}");
    private static final int[] weights = {2, 1, 2, 1, 2, 1, 2, 1, 2, 1};

    @Override
    public boolean validate(String identifier) {
        String idWithoutDashes = identifier.replaceAll("\\-", "");
        if (!pattern.matcher(idWithoutDashes).matches()) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            int t = Integer.valueOf(idWithoutDashes.substring(i, i + 1)) * weights[i];
            sum += t > 9 ? t - 9 : t;
        }

        return sum % 10 == 0;
    }
}
