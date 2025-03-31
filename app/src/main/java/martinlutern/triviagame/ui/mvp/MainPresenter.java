package martinlutern.triviagame.ui.mvp;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public interface MainPresenter {
    void showState(MainView.ViewState viewState);
    void screenState(MainView.ScreenState screenState);
}
