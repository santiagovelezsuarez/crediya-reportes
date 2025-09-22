package co.pragma.usecase.security;

import co.pragma.security.PermissionEnum;
import co.pragma.security.UserContextRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import security.PermissionValidator;

import java.util.Set;

class PermissionValidatorTest {

    private PermissionValidator permissionValidator;

    @BeforeEach
    void setUp() {
        permissionValidator = new PermissionValidator();
    }

    @Test
    void shouldGrantPermissionWhenUserHasRequiredPermission() {
        Set<PermissionEnum> permissions = Set.of(PermissionEnum.LEER_REPORTES);
        UserContextRequest context = new UserContextRequest("1", "test-user", "ASESOR", permissions);
        PermissionEnum requiredPermission = PermissionEnum.LEER_REPORTES;

        StepVerifier.create(permissionValidator.requirePermission(context, requiredPermission))
                .verifyComplete();
    }

    @Test
    void shouldDenyPermissionWhenUserLacksRequiredPermission() {
        Set<PermissionEnum> permissions = Set.of();
        UserContextRequest context = new UserContextRequest("1", "test-user", "ASESOR", permissions);
        PermissionEnum requiredPermission = PermissionEnum.LEER_REPORTES;

        StepVerifier.create(permissionValidator.requirePermission(context, requiredPermission))
                .expectErrorMatches(throwable -> throwable instanceof SecurityException &&
                        throwable.getMessage().contains("Permiso denegado"))
                .verify();
    }

}
