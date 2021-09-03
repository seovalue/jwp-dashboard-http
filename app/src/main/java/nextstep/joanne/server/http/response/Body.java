package nextstep.joanne.server.http.response;

import nextstep.joanne.server.converter.FileConverter;

import java.util.Objects;

public class Body {
    private final String body;

    public Body(String uri) {
        this.body = FileConverter.getResource(uri);
    }

    public String getBody() {
        if (Objects.isNull(body)) {
            return "";
        }
        return body;
    }
}
