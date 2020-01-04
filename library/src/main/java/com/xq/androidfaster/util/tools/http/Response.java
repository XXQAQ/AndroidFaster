package com.xq.androidfaster.util.tools.http;

import com.xq.androidfaster.util.JsonConverter;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Response {
    private Map<String, List<String>> mHeaders;
    private InputStream               mBody;

    public Response(Map<String, List<String>> headers, InputStream body) {
        mHeaders = headers;
        mBody = body;
    }

    public Map<String, List<String>> getHeaders() {
        return mHeaders;
    }

    public InputStream getBody() {
        return mBody;
    }

    public String getString() {
        return getString("utf-8");
    }

    public String getString(final String charset) {
        return HttpUtils.is2String(mBody, charset);
    }

    public <T> T getJson(final Type type) {
        return getJson(type, "utf-8");
    }

    public <T> T getJson(final Type type, final String charset) {
        return JsonConverter.jsonToObject(getString(charset), type);
    }

    public boolean downloadFile(final File file) {
        return HttpUtils.writeFileFromIS(file, mBody);
    }
}
