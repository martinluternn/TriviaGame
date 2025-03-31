package martinlutern.triviagame.ui.mvp;

import android.util.Log;

import org.json.JSONObject;

import martinlutern.triviagame.util.api.APIListener;
import martinlutern.triviagame.util.api.APIManager;
import martinlutern.triviagame.util.api.model.CategoryResponseModel;
import martinlutern.triviagame.util.api.model.ContentCategoryResponseModel;
import martinlutern.triviagame.util.api.model.RequestSessionResponseModel;
import martinlutern.triviagame.util.api.model.ResetSessionResponseModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public class MainPresenterImpl implements MainPresenter, APIListener {
    private final String TAG = getClass().getName();
    private MainView view;
    private MainInteractor interactor;

    public MainPresenterImpl(MainView view) {
        this.view = view;
        this.interactor = new MainInteractorImpl(this);
    }

    @Override
    public void showState(MainView.ViewState viewState) {
        switch (viewState) {
            case SNACKBAR:
                view.showState(MainView.ViewState.SNACKBAR);
                break;
            case REQUEST_SESSION:
                interactor.requestSession();
                break;
            case REQUEST_SESSION_SUCCESS:
                view.showState(MainView.ViewState.REQUEST_SESSION_SUCCESS);
                break;
            case REQUEST_SESSION_FAILURE:
                view.showState(MainView.ViewState.REQUEST_SESSION_FAILURE);
                break;
            case RESET_SESSION:
                interactor.resetSession(view.doRetrieveModel().getToken());
                break;
            case RESET_SESSION_SUCCESS:
                view.showState(MainView.ViewState.RESET_SESSION_SUCCESS);
                break;
            case RESET_SESSION_FAILURE:
                view.showState(MainView.ViewState.RESET_SESSION_FAILURE);
                break;
            case GET_CATEGORY:
                interactor.getCategory();
                break;
            case GET_CATEGORY_SUCCESS:
                view.showState(MainView.ViewState.GET_CATEGORY_SUCCESS);
                break;
            case GET_CATEGORY_FAILURE:
                view.showState(MainView.ViewState.GET_CATEGORY_FAILURE);
                break;
            case GET_CONTENT_CATEGORY:
                interactor.getContentCategory(view.doRetrieveModel().getAmount(), view.doRetrieveModel().getCategory(),
                        view.doRetrieveModel().getDifficulty(), view.doRetrieveModel().getType(), view.doRetrieveModel().getToken());
                break;
            case GET_CONTENT_CATEGORY_SUCCESS:
                view.showState(MainView.ViewState.GET_CONTENT_CATEGORY_SUCCESS);
                break;
            case GET_CONTENT_CATEGORY_FAILURE:
                view.showState(MainView.ViewState.GET_CONTENT_CATEGORY_FAILURE);
                break;
            default:
                break;
        }
    }

    @Override
    public void screenState(MainView.ScreenState screenState) {
        switch (screenState) {
            case SHOW_IDLE_SCREEN:
                view.showScreenState(MainView.ScreenState.SHOW_IDLE_SCREEN);
                break;
            case SHOW_LOADING_SCREEN:
                view.showScreenState(MainView.ScreenState.SHOW_LOADING_SCREEN);
                break;
            case SHOW_STARTUP_SCREEN:
                view.showScreenState(MainView.ScreenState.SHOW_STARTUP_SCREEN);
                break;
            case SHOW_CATEGORY_SCREEN:
                view.showScreenState(MainView.ScreenState.SHOW_CATEGORY_SCREEN);
                break;
            case SHOW_CONTENT_SCREEN:
                view.showScreenState(MainView.ScreenState.SHOW_CONTENT_SCREEN);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAPICallSucceed(APIManager.APIRoute route, Response response) {
        switch (route) {
            case REQUEST_SESSION:
                view.doRetrieveModel().setRequestSessionResponseModel((RequestSessionResponseModel) response.body());
                view.showState(MainView.ViewState.REQUEST_SESSION_SUCCESS);
                break;
            case RESET_SESSION:
                view.doRetrieveModel().setResetSessionResponseModel((ResetSessionResponseModel) response.body());
                view.showState(MainView.ViewState.RESET_SESSION_SUCCESS);
                break;
            case GET_CATEGORY:
                view.doRetrieveModel().setCategoryResponseModel((CategoryResponseModel) response.body());
                view.showState(MainView.ViewState.GET_CATEGORY_SUCCESS);
                break;
            case GET_CONTENT_CATEGORY:
                view.doRetrieveModel().setContentCategoryResponseModel((ContentCategoryResponseModel) response.body());
                view.showState(MainView.ViewState.GET_CONTENT_CATEGORY_SUCCESS);
                break;
            default:
                break;
        }
    }

    @Override
    public void onAPICallFailed(APIManager.APIRoute route, Response response, Call call, Throwable t) {
        String errorMessage;

        if (response != null) {
            errorMessage = response.message();

            try {
                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                if (jsonObject.has("response_code")) {
                    view.doRetrieveModel().setResponseCode(jsonObject.getInt("response_code"));
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        } else {
            errorMessage = t.getMessage();
        }
        call.cancel();

        switch (route) {
            case REQUEST_SESSION:
                Log.e(TAG, "Error: " + errorMessage);
                view.showState(MainView.ViewState.REQUEST_SESSION_FAILURE);
                break;
            case RESET_SESSION:
                Log.e(TAG, "Error: " + errorMessage);
                view.showState(MainView.ViewState.RESET_SESSION_FAILURE);
                break;
            case GET_CATEGORY:
                Log.e(TAG, "Error: " + errorMessage);
                view.showState(MainView.ViewState.GET_CATEGORY_FAILURE);
                break;
            case GET_CONTENT_CATEGORY:
                Log.e(TAG, "Error: " + errorMessage);
                view.showState(MainView.ViewState.GET_CONTENT_CATEGORY_FAILURE);
                break;
            default:
                break;
        }
    }
}
