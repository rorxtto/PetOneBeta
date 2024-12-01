package app.petone.auth.service;
import app.petone.auth.model.AuxToken;
import app.petone.auth.model.Login;
import app.petone.auth.model.TokenDTO;
import app.petone.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    public AuxToken login(Login login) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String formattedBody = String.format(
                "grant_type=password&client_id=petone&client_secret=90URn6CkWsS2ctgvpssIjSPc2ell3A7q&username=%s&password=%s",
                login.getUsername(),
                login.getPassword()
        );
        RequestBody body = RequestBody.create(mediaType, formattedBody);
        Request request = new Request.Builder()
                .url("http://192.168.56.14:8080/realms/petone/protocol/openid-connect/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                AuxToken auxToken = mapper.readValue(responseBody, AuxToken.class);

                System.out.println("Response: " + responseBody);

                // Chamar CheckUser após o login bem-sucedido
                CheckUser(auxToken);
                return auxToken;
            } else {
                System.err.println("Request failed with status: " + response.code());
                throw new RuntimeException("Falha na autenticação: " + response.message());
            }
        } catch (IOException e) {
            e.getStackTrace();
            throw new RuntimeException("Erro na comunicação com o servidor: " + e.getMessage());
        }
    }


    private void CheckUser(AuxToken auxToken) {
        if (auxToken == null || auxToken.getAccess_token() == null) {
            throw new IllegalArgumentException("Token de acesso inválido.");
        }
        System.out.println("CheckUser chamado com AuxToken: " + auxToken.getAccess_token());
        String accessToken = auxToken.getAccess_token();

        // Decodificar o token JWT e convertê-lo para TokenDto
        try {
            String[] parts = accessToken.split("\\.");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Token JWT inválido.");
            }

            // Decodificar o payload do JWT
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

            // Mapear o payload para TokenDto
            ObjectMapper objectMapper = new ObjectMapper();
            ConfigUserEntry(objectMapper.readValue(payload, TokenDTO.class));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter token JWT para TokenDto: " + e.getMessage(), e);
        }
    }

    private void ConfigUserEntry(TokenDTO tokenDTO) {
        if (tokenDTO == null) {
            throw new IllegalArgumentException("TokenDTO não pode ser nulo.");
        }
        System.out.println("ConfigUserEntry chamado com TokenDTO: " + tokenDTO);
        userService.loadUser(tokenDTO); // Chama o método da UserService
    }


    private AuxToken mapJsonToAuxToken(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, AuxToken.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao mapear JSON para AuxToken");
        }
    }

    public String getEmailFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return jwt.getClaimAsString("email");
        }
        return null;
    }


}
