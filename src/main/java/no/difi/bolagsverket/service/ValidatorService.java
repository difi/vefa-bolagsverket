package no.difi.bolagsverket.service;

@FunctionalInterface
public interface ValidatorService {
    boolean validate(String identifier);
}
