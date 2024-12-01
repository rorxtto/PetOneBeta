package app.petone.auth.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class TokenDTO {
    private long exp;
    private long iat;
    private String jti;
    private String iss;
    private String aud;
    private String sub;
    private String typ;
    private String azp;
    private String sid;
    private String acr;

    @JsonProperty("allowed-origins")
    private List<String> allowedOrigins;

    @JsonProperty("realm_access")
    private RealmAccess realmAccess;

    @JsonProperty("resource_access")
    private ResourceAccess resourceAccess;
    private String scope;
    @JsonProperty("email_verified")
    private boolean emailVerified;
    private String name;
    @JsonProperty("preferred_username")
    private String preferredUsername;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    private String email;

    @Data
    public static class RealmAccess {
        private List<String> roles;
    }

    @Data
    public static class ResourceAccess {
        @JsonProperty("account")
        private ResourceRoles account;

        @Data
        public static class ResourceRoles {
            @JsonDeserialize(using = FlexibleRolesDeserializer.class)
            private List<String> roles;
        }
    }

    public static class FlexibleRolesDeserializer extends JsonDeserializer<List<String>> {
        @Override
        public List<String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonToken currentToken = p.currentToken();

            if (currentToken == JsonToken.START_ARRAY) {
                return p.readValueAs(new TypeReference<List<String>>() {});
            } else if (currentToken == JsonToken.START_OBJECT) {
                Map<String, Object> map = p.readValueAs(Map.class);
                return map.containsKey("roles") ? (List<String>) map.get("roles") : new ArrayList<>();
            }

            return new ArrayList<>();
        }
    }
}