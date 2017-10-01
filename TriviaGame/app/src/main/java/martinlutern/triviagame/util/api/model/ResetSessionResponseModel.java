package martinlutern.triviagame.util.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public class ResetSessionResponseModel {
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("token")
    @Expose
    private String token;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
