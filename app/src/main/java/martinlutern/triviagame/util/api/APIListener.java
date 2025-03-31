package martinlutern.triviagame.util.api;

import retrofit2.Call;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public interface APIListener {
    void onAPICallSucceed(APIManager.APIRoute route, retrofit2.Response response);

    void onAPICallFailed(APIManager.APIRoute route, retrofit2.Response response, Call call, Throwable t);
}
