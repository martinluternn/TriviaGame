package martinlutern.triviagame.ui.mvp;

import martinlutern.triviagame.util.api.model.CategoryResponseModel;
import martinlutern.triviagame.util.api.model.ContentCategoryResponseModel;
import martinlutern.triviagame.util.api.model.RequestSessionResponseModel;
import martinlutern.triviagame.util.api.model.ResetSessionResponseModel;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public class MainModel {
    private String amount, category, difficulty, type, token;
    private int responseCode, countQuestionNo, countRightAnswers;
    private String snackbarMessage;
    private CharSequence difficultLevel[];
    private String selectedAnswer, correctAnswer;
    private boolean isCheckAnswer, isActivityRunning, clickBackTwice;
    private MainView.ScreenState selectedScreenState;

    private RequestSessionResponseModel requestSessionResponseModel;
    private ResetSessionResponseModel resetSessionResponseModel;
    private CategoryResponseModel categoryResponseModel;
    private CategoryResponseModel.TriviaCategory selectedCategoryResponseModel;
    private ContentCategoryResponseModel contentCategoryResponseModel;

    public ResetSessionResponseModel getResetSessionResponseModel() {
        return resetSessionResponseModel;
    }

    public void setResetSessionResponseModel(ResetSessionResponseModel resetSessionResponseModel) {
        this.resetSessionResponseModel = resetSessionResponseModel;
    }

    public boolean isClickBackTwice() {
        return clickBackTwice;
    }

    public void setClickBackTwice(boolean clickBackTwice) {
        this.clickBackTwice = clickBackTwice;
    }

    public boolean isActivityRunning() {
        return isActivityRunning;
    }

    public void setActivityRunning(boolean activityRunning) {
        isActivityRunning = activityRunning;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isCheckAnswer() {
        return isCheckAnswer;
    }

    public void setCheckAnswer(boolean checkAnswer) {
        isCheckAnswer = checkAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public MainView.ScreenState getSelectedScreenState() {
        return selectedScreenState;
    }

    public void setSelectedScreenState(MainView.ScreenState selectedScreenState) {
        this.selectedScreenState = selectedScreenState;
    }

    public CategoryResponseModel.TriviaCategory getSelectedCategoryResponseModel() {
        return selectedCategoryResponseModel;
    }

    public void setSelectedCategoryResponseModel(CategoryResponseModel.TriviaCategory selectedCategoryResponseModel) {
        this.selectedCategoryResponseModel = selectedCategoryResponseModel;
    }

    public int getCountRightAnswers() {
        return countRightAnswers;
    }

    public void setCountRightAnswers(int countRightAnswers) {
        this.countRightAnswers = countRightAnswers;
    }

    public int getCountQuestionNo() {
        return countQuestionNo;
    }

    public void setCountQuestionNo(int countQuestionNo) {
        this.countQuestionNo = countQuestionNo;
    }

    public CharSequence[] getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(CharSequence[] difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public String getSnackbarMessage() {
        return snackbarMessage;
    }

    public void setSnackbarMessage(String snackbarMessage) {
        this.snackbarMessage = snackbarMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public RequestSessionResponseModel getRequestSessionResponseModel() {
        return requestSessionResponseModel;
    }

    public void setRequestSessionResponseModel(RequestSessionResponseModel requestSessionResponseModel) {
        this.requestSessionResponseModel = requestSessionResponseModel;
    }

    public CategoryResponseModel getCategoryResponseModel() {
        return categoryResponseModel;
    }

    public void setCategoryResponseModel(CategoryResponseModel categoryResponseModel) {
        this.categoryResponseModel = categoryResponseModel;
    }

    public ContentCategoryResponseModel getContentCategoryResponseModel() {
        return contentCategoryResponseModel;
    }

    public void setContentCategoryResponseModel(ContentCategoryResponseModel contentCategoryResponseModel) {
        this.contentCategoryResponseModel = contentCategoryResponseModel;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
