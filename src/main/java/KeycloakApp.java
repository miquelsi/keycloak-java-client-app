import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;

public class KeycloakApp {

    public static void main(String[] args) {
        String serverUrl = "http://localhost:8180/auth";
        String realm = "master";
        // idm-client needs to allow "Direct Access Grants: Resource Owner Password Credentials Grant"
        String clientId = "keycloak-app";
        String clientSecret = "12830af4-0d95-49a7-b5d6-49ea7b305102";

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username("admin")
                .password("admin")
                .build();

        CredentialRepresentation cr = new CredentialRepresentation();
        cr.setValue("password");
        cr.setTemporary(false);
        cr.setType("type");

        UsersResource usersResource = keycloak.realm(realm).users();
        String id = usersResource.search("admin").get(0).getId();
        UserResource ur = usersResource.get(id);
        ur.resetPassword(cr);

        System.out.println("Users count: " + usersResource.count("admin"));
    }
}
