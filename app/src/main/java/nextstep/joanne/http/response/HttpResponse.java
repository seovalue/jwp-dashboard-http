package nextstep.joanne.http.response;

import nextstep.joanne.http.HttpStatus;

public class HttpResponse {
    private HttpStatus httpStatus;
    private String body;

    public HttpResponse(HttpStatus httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public String body() {
        return body;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
