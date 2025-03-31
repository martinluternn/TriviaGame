package martinlutern.triviagame.ui.mvp;

import martinlutern.triviagame.util.api.APIListener;
import martinlutern.triviagame.util.api.APIManager;
import martinlutern.triviagame.util.api.model.CategoryResponseModel;
import martinlutern.triviagame.util.api.model.ContentCategoryResponseModel;
import martinlutern.triviagame.util.api.model.RequestSessionResponseModel;
import martinlutern.triviagame.util.api.model.ResetSessionResponseModel;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public class MainInteractorImpl implements MainInteractor {
    private APIListener listener;

    public MainInteractorImpl(APIListener listener) {
        this.listener = listener;
    }

    @Override
    public void requestSession() {
        final APIManager.APIRoute route = APIManager.APIRoute.REQUEST_SESSION;
        APIManager.getInstance().requestSession(new Callback<RequestSessionResponseModel>() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                if (response.isSuccessful()) {
                    listener.onAPICallSucceed(route, response);
                } else {
                    listener.onAPICallFailed(route, response, call, null);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onAPICallFailed(route, null, call, t);
            }
        });
    }

    @Override
    public void resetSession(String token) {
        final APIManager.APIRoute route = APIManager.APIRoute.RESET_SESSION;
        APIManager.getInstance().resetSession(token, new Callback<ResetSessionResponseModel>() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                if (response.isSuccessful()) {
                    listener.onAPICallSucceed(route, response);
                } else {
                    listener.onAPICallFailed(route, response, call, null);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onAPICallFailed(route, null, call, t);
            }
        });
    }

    @Override
    public void getCategory() {
        final APIManager.APIRoute route = APIManager.APIRoute.GET_CATEGORY;
        APIManager.getInstance().getCategory(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                if (response.isSuccessful()) {
                    listener.onAPICallSucceed(route, response);
                } else {
                    listener.onAPICallFailed(route, response, call, null);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onAPICallFailed(route, null, call, t);
            }
        });
    }

    @Override
    public void getContentCategory(String amount, String category, String difficulty, String type, String token) {
        final APIManager.APIRoute route = APIManager.APIRoute.GET_CONTENT_CATEGORY;
        APIManager.getInstance().getContentCategory(amount, category, difficulty, type, token, new Callback<ContentCategoryResponseModel>() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                if (response.isSuccessful()) {
                    listener.onAPICallSucceed(route, response);
                } else {
                    listener.onAPICallFailed(route, response, call, null);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                listener.onAPICallFailed(route, null, call, t);
            }
        });
    }
}
