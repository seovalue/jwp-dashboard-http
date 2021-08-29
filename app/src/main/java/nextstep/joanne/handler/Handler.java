package nextstep.joanne.handler;

import nextstep.joanne.converter.HttpRequestResponseConverter;
import nextstep.joanne.db.InMemoryUserRepository;
import nextstep.joanne.http.HttpMethod;
import nextstep.joanne.http.HttpStatus;
import nextstep.joanne.http.request.HttpRequest;
import nextstep.joanne.http.response.HttpResponse;
import nextstep.joanne.model.User;

import java.io.IOException;

public class Handler {
    private final HttpRequest httpRequest;

    public Handler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse handle() throws IOException {

        if (httpRequest.uriEquals("/")) {
            return mainPage();
        }

        if (httpRequest.uriContains("/register")) {
            return doRegister();
        }

        if (httpRequest.uriContains("/login")) {
            return doLogin();
        }

        return null;
    }

    private HttpResponse doLogin() throws IOException {
        if (httpRequest.isEqualsMethod(HttpMethod.GET)) {
            return HttpRequestResponseConverter.convertToHttpResponse(
                    HttpStatus.OK,
                    httpRequest.resourceUri(),
                    httpRequest.contentType()
            );
        }

        if (httpRequest.isEqualsMethod(HttpMethod.POST)) {
            try {
                loginRequest(httpRequest.getFromRequestBody("account"),
                        httpRequest.getFromRequestBody("password"));
                return HttpRequestResponseConverter.convertToHttpResponse(
                        HttpStatus.FOUND,
                        "/index.html",
                        httpRequest.contentType()
                );

            } catch (IllegalArgumentException e) {
                return HttpRequestResponseConverter.convertToHttpResponse(
                        HttpStatus.UNAUTHORIZED,
                        "/401.html",
                        httpRequest.contentType()
                );
            }
        }
        return null;
    }

    private HttpResponse doRegister() throws IOException {
        if (httpRequest.isEqualsMethod(HttpMethod.GET)) {
            return HttpRequestResponseConverter.convertToHttpResponse(
                    HttpStatus.OK,
                    httpRequest.resourceUri(),
                    httpRequest.contentType()
            );
        }

        if (httpRequest.isEqualsMethod(HttpMethod.POST)) {
            registerRequest();
            return HttpRequestResponseConverter.convertToHttpResponse(
                    HttpStatus.FOUND,
                    "/index.html",
                    httpRequest.contentType()
            );
        }
        return null;
    }

    private HttpResponse mainPage() throws IOException {
        return HttpRequestResponseConverter.convertToHttpResponse(
                HttpStatus.OK,
                httpRequest.resourceUri(),
                httpRequest.contentType()
        );
    }

    private void loginRequest(String account, String password) {
        User user = InMemoryUserRepository.findByAccount(account)
                .orElseThrow(IllegalArgumentException::new);
        if (!user.checkPassword(password)) {
            throw new IllegalArgumentException();
        }
    }

    private void registerRequest() {
        final User user = new User(httpRequest.getFromRequestBody("account"),
                httpRequest.getFromRequestBody("password"),
                httpRequest.getFromRequestBody("email"));
        InMemoryUserRepository.save(user);
    }
}
