package contactSite.LoginUtils;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    public LoginMemberResolver(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        // 여기부터 중복코드
        String[] tokenFormat = authorization.split(" ");
        String tokenType = tokenFormat[0];
        String token = tokenFormat[1];

        if (!tokenType.equals("Bearer")) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        if (!jwtProvider.isValidToken(token)) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }

        String userId = jwtProvider.getSubject(token);
        // 여기까지 중복코드

        return userId;
    }
}