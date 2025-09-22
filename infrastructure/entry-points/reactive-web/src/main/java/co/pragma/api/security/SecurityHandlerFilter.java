package co.pragma.api.security;

import co.pragma.security.PermissionEnum;
import co.pragma.security.UserContextRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import security.PermissionValidator;

@Component
@RequiredArgsConstructor
public class SecurityHandlerFilter {

    private final PermissionValidator permissionValidator;
    private final UserContextExtractor userContextExtractor;

    public HandlerFilterFunction<ServerResponse, ServerResponse> requirePermission(PermissionEnum permissionEnum) {
        return (request, next) -> Mono.defer(() -> {
            UserContextRequest context = userContextExtractor.fromRequest(request);
            if (context == null) {
                return Mono.error(new SecurityException("User context could not be extracted"));
            }

            return permissionValidator.requirePermission(context, permissionEnum)
                    .then(next.handle(request)
                            .contextWrite(ctx -> {
                                if (context.userId() != null)
                                    return ctx.put("userId", context.userId());
                                return ctx;
                            }));
        });
    }
}

