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

Brukarar av bolagsverket-client gjer oppslag mot endepunktet, utvida med ein numerisk identifikator på 10 (aksjeselskap/aktiebolag) eller 15 siffer (enkeltmannsføretak/enskild näringsidkare). Dei to første sifra i identifikatoren til eit enkeltmannsføretak er 18, 19 eller 20. Dette talet representerar hundreåret organisasjonen vart oppretta. Deretter kjem eit svensk personnummer på ti siffer, som identifiserar eigaren av føretaket. Til slutt kjem eit tre-sifra løpenummer (startar frå 001) for kvart føretak enkeltpersonen eig. For aksjeselskap (aktiebolag) treng ein ikkje hundreårsprefikset, då er det nok med eit ti-sifra organisasjonsnummer.

**Respons**

Type oppslag | HTTP-statuskode | Respons 
------------ | --------------- |--------
Registrert svensk aksjeselskaps-identifikator. | 200 | JSON: {"name":"Namnet på selskapet"} 
Gyldig svensk aksjeselskaps-identifikator utan match i registeret. | 404 | - 
Ugyldig svensk aksjeselskap-identifikator. | 400 | - 
Registrert svensk enkeltmannsføretak. | 200 | JSON: {"name":"Namnet på selskapet"}
