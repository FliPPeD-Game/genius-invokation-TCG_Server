//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.alibaba.nacos.core.utils;

import com.alibaba.nacos.common.http.HttpUtils;
import com.alibaba.nacos.common.model.RestResult;
import com.alibaba.nacos.common.model.RestResultUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.sys.utils.DiskUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

public class WebUtils {

    private static final String ENCODING_KEY = "encoding";
    private static final String COMMA = ",";
    private static final String SEMI = ";";
    private static final String TMP_SUFFIX = ".tmp";

    public WebUtils() {
    }

    public static String required(HttpServletRequest req, String key) {
        String value = req.getParameter(key);
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Param '" + key + "' is required.");
        } else {
            String encoding = req.getParameter("encoding");
            return resolveValue(value, encoding);
        }
    }

    public static String optional(HttpServletRequest req, String key, String defaultValue) {
        String value = req.getParameter(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        } else {
            String encoding = req.getParameter("encoding");
            return resolveValue(value, encoding);
        }
    }

    private static String resolveValue(String value, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = StandardCharsets.UTF_8.name();
        }

        try {
            value = new String(value.getBytes(StandardCharsets.UTF_8), encoding);
        } catch (UnsupportedEncodingException var3) {
        }

        return value.trim();
    }

    private static String resolveValueWithUrlDecode(String value, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = StandardCharsets.UTF_8.name();
        }

        try {
            value = HttpUtils.decode(new String(value.getBytes(StandardCharsets.UTF_8), encoding), encoding);
        } catch (UnsupportedEncodingException var4) {
        } catch (Exception var5) {
            String seq = "URLDecoder";
            if (!StringUtils.contains(var5.toString(), "URLDecoder")) {
                throw var5;
            }
        }

        return value.trim();
    }

    public static String getAcceptEncoding(HttpServletRequest req) {
        String encode = StringUtils.defaultIfEmpty(req.getHeader("Accept-Encoding"), StandardCharsets.UTF_8.name());
        encode = encode.contains(",") ? encode.substring(0, encode.indexOf(",")) : encode;
        return encode.contains(";") ? encode.substring(0, encode.indexOf(";")) : encode;
    }

    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isEmpty(userAgent)) {
            userAgent = StringUtils.defaultIfEmpty(request.getHeader("Client-Version"), "");
        }

        return userAgent;
    }

    public static void response(HttpServletResponse response, String body, int code) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(body);
        response.setStatus(code);
    }

    public static void onFileUpload(MultipartFile multipartFile, Consumer<File> consumer,
            DeferredResult<RestResult<String>> response) {
        if (!Objects.isNull(multipartFile) && !multipartFile.isEmpty()) {
            File tmpFile = null;

            try {
                tmpFile = DiskUtils.createTmpFile(multipartFile.getName(), ".tmp");
                multipartFile.transferTo(tmpFile);
                consumer.accept(tmpFile);
            } catch (Throwable var8) {
                if (!response.isSetOrExpired()) {
                    response.setResult(RestResultUtils.failed(var8.getMessage()));
                }
            } finally {
                DiskUtils.deleteQuietly(tmpFile);
            }

        } else {
            response.setResult(RestResultUtils.failed("File is empty"));
        }
    }

    public static <T> void process(DeferredResult<T> deferredResult, CompletableFuture<T> future,
            Function<Throwable, T> errorHandler) {
        Objects.requireNonNull(future);
        deferredResult.onTimeout(future::join);
        future.whenComplete((t, throwable) -> {
            if (Objects.nonNull(throwable)) {
                deferredResult.setResult(errorHandler.apply(throwable));
            } else {
                deferredResult.setResult(t);
            }
        });
    }

    public static <T> void process(DeferredResult<T> deferredResult, CompletableFuture<T> future, Runnable success,
            Function<Throwable, T> errorHandler) {
        Objects.requireNonNull(future);
        deferredResult.onTimeout(future::join);
        future.whenComplete((t, throwable) -> {
            if (Objects.nonNull(throwable)) {
                deferredResult.setResult(errorHandler.apply(throwable));
            } else {
                success.run();
                deferredResult.setResult(t);
            }
        });
    }
}
