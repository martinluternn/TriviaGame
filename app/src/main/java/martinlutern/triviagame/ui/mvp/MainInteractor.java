package martinlutern.triviagame.ui.mvp;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public interface MainInteractor {
    void requestSession();

    void resetSession(String token);

    void getCategory();

    void getContentCategory(String amount, String category, String difficulty, String type, String token);
}
