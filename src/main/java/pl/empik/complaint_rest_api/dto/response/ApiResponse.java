package pl.empik.complaint_rest_api.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String status;
    private T response;

    public ApiResponse(int code, String status, T response) {
        this.code = code;
        this.status = status;
        this.response = response;
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(200, "OK", response);
    }

    public static <T> ApiResponse<T> error(int code, String status, T response) {
        return new ApiResponse<>(code, status, response);
    }
}
