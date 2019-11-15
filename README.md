# vefa-bolagsverket
**Introduksjon**

REST-tenesta bolagsverket-client gjer oppslag mot XML-tenestene til Bolagsverket, som er beskrivne [HER](http://bolagsverket.se/be/sok/xml), nærmare bestemt produktet "Grundpaket" (versjon 2.00).
Klienten er utvikla som følge av behovet for å validera svenske organisasjonsnummer i ELMA. 
Organisasjonsnummeret vert først validert i henhold til tilgjengeleg informasjon om svenske identifikatorar, inkludert verifisering mot [Luhn-algoritma](https://sv.wikipedia.org/wiki/Luhn-algoritmen). 
Dersom denne valideringa passerar, vert det sendt ein førespurnad til Bolagsverket sine tenester.

Endepunkt | Beskriving
--------- | ---------------
~/identifier | Oppslag på organisasjonsnummer.
~/manage/health | Viser helse-informasjon om tenesta.
~/manage/info | Viser annan informasjon om tenesta.

**Konfigurasjon**

Property | Beskriving | Default
-------- | ---------- | -------
bolagsverket.serviceEndpoint | Endepunkt, ref. ovanfor | -
bolagsverket.validation.callXmlService | true => XML-tenestene til Bolagsverket vert kontakta etter grunnleggande validering. false => Grunnleggande ID-validering som spesifisert ovanfor. | true
bolagsverket.keystore.type | Keystore-type | JKS
bolagsverket.keystore.path | Sti til keystore-fil | -
bolagsverket.keystore.password | passord | -
bolagsverket.keystore.keyAlias | Referanse til sertifikat i keystore. | -
bolagsverket.keystore.keyPassword | passord	 | -
bolagsverket.truststore.type | Keystore-type | JKS
bolagsverket.truststore.path | Sti til keystore-fil som støttar SSL-sertifikatet for XML-tenestene til Bolagsverket, som i skrivande stund ligg [HER](https://repository.trust.teliasonera.com/teliasonerarootcav1.cer). | -
bolagsverket.truststore.password | passord | -
bolagsverket.truststore.keyAlias | Referanse til sertifikat i keystore. | -
bolagsverket.truststore.keyPassword	| passord | -
spring.security.user.name | Basic auth-brukarnamn | -
spring.security.user.password | Basic auth-passord | -

**Oppslag**

Brukarar av bolagsverket-client gjer oppslag mot endepunktet, utvida med eit tolv-sifra organisasjonsnummer. Dei to første sifra i dette nummeret er 18, 19 eller 20 og representerar hundreåret organisasjonen vart oppretta. For aksjeselskap (aktiebolag) treng ein ikkje hundreårsprefikset, då er det nok med eit ti-sifra organisasjonsnummer.

**Respons**

Type oppslag | HTTP-statuskode | Respons | Døme
------------ | --------------- |-------- |-----
Registrert svensk organisasjon. | 200 | JSON | ~/identifier/5566618020
Gyldig svensk organisasjonsnummer. | 204 | - | ~/identifier/2021005489
Ugyldig svensk organisasjonsnummer. | 404 | - | ~/identifier/2021005490
