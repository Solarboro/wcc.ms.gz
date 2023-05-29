package org.solar.auth.service;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.ErrorCodes;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.solar.auth.exception.ErrorCode;
import org.solar.auth.exception.GenericException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JoseForJ {

    RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

    JwtConsumer jwtConsumer = new JwtConsumerBuilder()
//                .setRequireExpirationTime() // the JWT must have an expiration time
//                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
            .setRequireSubject() // the JWT must have a subject claim
            .setExpectedIssuer("Issuer") // whom the JWT needs to have been issued by
            .setExpectedAudience("Audience") // to whom the JWT is intended for
            .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
            .setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
                    AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256) // which is only RS256 here
            .build(); // create the JwtConsumer instance

    public JoseForJ() throws JoseException {
    }

    public String produce (String subject, List<String> stringListClaimValue){

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("Issuer");  // who creates the token and signs it
        claims.setAudience("Audience"); // to whom the token is intended to be sent
//        claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
//        claims.setGeneratedJwtId(); // a unique identifier for the token
//        claims.setIssuedAtToNow();  // when the token was issued/created (now)
//        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(subject); // the subject/principal is whom the token is about
//        claims.setClaim("email","mail@example.com"); // additional claims/attributes about the subject can be added
        claims.setStringListClaim("g1", stringListClaimValue); // multi-valued claims work too and will end up as a JSON array

        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // In this example it is a JWS so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
//        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        // If you wanted to encrypt it, you can simply set this jwt as the payload
        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
        String jwt = null;
        try {
            jwt = jws.getCompactSerialization();
        } catch (Exception e) {
            throw new GenericException(e.getMessage(), e, ErrorCode.JWTPRODUCE);

        }

        // Now you can do something with the JWT. Like send it to some other party
        // over the clouds and through the interwebs.
//        System.out.println("JWT: " + jwt);
        return jwt;
    }



    public JwtClaims consume(String jws){
        // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
        // be used to validate and process the JWT.
        // The specific validation requirements for a JWT are context dependent, however,
        // it is typically advisable to require a (reasonable) expiration time, a trusted issuer, and
        // an audience that identifies your system as the intended recipient.
        // If the JWT is encrypted too, you need only provide a decryption key or
        // decryption key resolver to the builder.


        try
        {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(jws);
            return jwtClaims;
        }
        catch (Exception e)
        {
            throw new GenericException(e.getMessage(), e, ErrorCode.JWTCONSUMER);
        }
    }
}
