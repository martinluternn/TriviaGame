package martinlutern.triviagame.ui.mvp;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public interface MainView {
    enum ViewState {
        SNACKBAR,
        REQUEST_SESSION, REQUEST_SESSION_SUCCESS, REQUEST_SESSION_FAILURE,
        RESET_SESSION, RESET_SESSION_SUCCESS, RESET_SESSION_FAILURE,
        GET_CATEGORY, GET_CATEGORY_SUCCESS, GET_CATEGORY_FAILURE,
        GET_CONTENT_CATEGORY, GET_CONTENT_CATEGORY_SUCCESS, GET_CONTENT_CATEGORY_FAILURE
    }

    enum ScreenState {
        SHOW_STARTUP_SCREEN, SHOW_CATEGORY_SCREEN, SHOW_CONTENT_SCREEN,
        SHOW_LOADING_SCREEN, SHOW_IDLE_SCREEN
    }

    MainModel doRetrieveModel();

    void showState(ViewState viewState);

    void showScreenState(ScreenState screenState);
}
