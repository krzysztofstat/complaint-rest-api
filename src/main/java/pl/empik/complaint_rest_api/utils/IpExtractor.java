package pl.empik.complaint_rest_api.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

import static java.util.function.Predicate.not;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpExtractor {

    private static final String LOCATION_HEADER = "X-FORWARDED-FOR";
    private static final String SEPARATOR = ",";

    public static String extractClientIp(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(LOCATION_HEADER))
                .flatMap(header -> Arrays.stream(header.split(SEPARATOR))
                        .map(String::trim)
                        .filter(not(String::isEmpty))
                        .findFirst())
                .orElse(request.getRemoteAddr());
    }
}
