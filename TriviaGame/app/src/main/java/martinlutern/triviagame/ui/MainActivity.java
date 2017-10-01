package martinlutern.triviagame.ui;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import martinlutern.triviagame.R;
import martinlutern.triviagame.ui.mvp.MainModel;
import martinlutern.triviagame.ui.mvp.MainPresenter;
import martinlutern.triviagame.ui.mvp.MainPresenterImpl;
import martinlutern.triviagame.ui.mvp.MainView;
import martinlutern.triviagame.util.api.model.ContentCategoryResponseModel;
import martinlutern.triviagame.util.common.Connectivity;
import martinlutern.triviagame.util.common.Constant;
import martinlutern.triviagame.util.common.PreferencesManager;

public class MainActivity extends AppCompatActivity implements MainView {
    private MainModel model;
    private MainPresenter presenter;
    private CategoryAdapter categoryAdapter;

    @BindView(R.id.main_content_layout)
    RelativeLayout mainContent;
    @BindView(R.id.layout_loading)
    LinearLayout loadingLayout;
    @BindView(R.id.layout_startup)
    LinearLayout layoutStartup;
    @BindView(R.id.layout_category)
    LinearLayout layoutCategory;
    @BindView(R.id.layout_content)
    RelativeLayout layoutContent;

    @BindView(R.id.gridview_category)
    GridView gridviewCategory;
    @BindView(R.id.txt_right_answers_count)
    TextView rightAnswersCount;
    @BindView(R.id.txt_question_no_count)
    TextView questionNoCount;
    @BindView(R.id.txt_question)
    TextView txtQuestion;

    @BindView(R.id.txt_title_a)
    TextView txtA;
    @BindView(R.id.txt_state_a)
    TextView txtStateA;
    @BindView(R.id.txt_title_b)
    TextView txtB;
    @BindView(R.id.txt_state_b)
    TextView txtStateB;
    @BindView(R.id.txt_title_c)
    TextView txtC;
    @BindView(R.id.txt_state_c)
    TextView txtStateC;
    @BindView(R.id.txt_title_d)
    TextView txtD;
    @BindView(R.id.txt_state_d)
    TextView txtStateD;

    @BindView(R.id.btn_a)
    Button btnA;
    @BindView(R.id.btn_b)
    Button btnB;
    @BindView(R.id.btn_c)
    Button btnC;
    @BindView(R.id.btn_d)
    Button btnD;
    @BindView(R.id.layout_choice_a)
    RelativeLayout layoutChoiceA;
    @BindView(R.id.layout_choice_b)
    RelativeLayout layoutChoiceB;
    @BindView(R.id.layout_choice_c)
    RelativeLayout layoutChoiceC;
    @BindView(R.id.layout_choice_d)
    RelativeLayout layoutChoiceD;
    @BindView(R.id.txt_btn_lanjut)
    TextView txtBtnLanjut;

    @OnClick(R.id.layout_choice_a)
    public void onClickBtnA() {
        if (!doRetrieveModel().isCheckAnswer()) {
            layoutChoiceA.setTag("Selected");
            layoutChoiceB.setTag("");
            layoutChoiceC.setTag("");
            layoutChoiceD.setTag("");
            doRetrieveModel().setSelectedAnswer(txtA.getText().toString());
            setSelectedAnswerColor(layoutChoiceA, btnA, txtA, txtStateA);
            setSelectedDefaultColor(layoutChoiceB, btnB, txtB, txtStateB);
            setSelectedDefaultColor(layoutChoiceC, btnC, txtC, txtStateC);
            setSelectedDefaultColor(layoutChoiceD, btnD, txtD, txtStateD);
        }
    }

    @OnClick(R.id.layout_choice_b)
    public void onClickBtnB() {
        if (!doRetrieveModel().isCheckAnswer()) {
            layoutChoiceB.setTag("Selected");
            layoutChoiceA.setTag("");
            layoutChoiceC.setTag("");
            layoutChoiceD.setTag("");
            doRetrieveModel().setSelectedAnswer(txtB.getText().toString());
            setSelectedAnswerColor(layoutChoiceB, btnB, txtB, txtStateB);
            setSelectedDefaultColor(layoutChoiceA, btnA, txtA, txtStateA);
            setSelectedDefaultColor(layoutChoiceC, btnC, txtC, txtStateC);
            setSelectedDefaultColor(layoutChoiceD, btnD, txtD, txtStateD);
        }
    }

    @OnClick(R.id.layout_choice_c)
    public void onClickBtnC() {
        if (!doRetrieveModel().isCheckAnswer()) {
            layoutChoiceC.setTag("Selected");
            layoutChoiceB.setTag("");
            layoutChoiceA.setTag("");
            layoutChoiceD.setTag("");
            doRetrieveModel().setSelectedAnswer(txtC.getText().toString());
            setSelectedAnswerColor(layoutChoiceC, btnC, txtC, txtStateC);
            setSelectedDefaultColor(layoutChoiceB, btnB, txtB, txtStateB);
            setSelectedDefaultColor(layoutChoiceA, btnA, txtA, txtStateA);
            setSelectedDefaultColor(layoutChoiceD, btnD, txtD, txtStateD);
        }
    }

    @OnClick(R.id.layout_choice_d)
    public void onClickBtnD() {
        if (!doRetrieveModel().isCheckAnswer()) {
            layoutChoiceD.setTag("Selected");
            layoutChoiceB.setTag("");
            layoutChoiceC.setTag("");
            layoutChoiceA.setTag("");
            doRetrieveModel().setSelectedAnswer(txtD.getText().toString());
            setSelectedAnswerColor(layoutChoiceD, btnD, txtD, txtStateD);
            setSelectedDefaultColor(layoutChoiceB, btnB, txtB, txtStateB);
            setSelectedDefaultColor(layoutChoiceC, btnC, txtC, txtStateC);
            setSelectedDefaultColor(layoutChoiceA, btnA, txtA, txtStateA);
        }
    }

    @OnClick(R.id.layout_btn_lanjut)
    public void onClickLanjut() {
        if (isAnswerSelected()) {
            doRetrieveModel().setCheckAnswer(true);
        } else {
            if (!doRetrieveModel().isCheckAnswer()) {
                doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.no_selected_answer));
                presenter.showState(ViewState.SNACKBAR);
                return;
            }

            doRetrieveModel().setCheckAnswer(false);
        }

        if (doRetrieveModel().isCheckAnswer()) {
            if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(txtA.getText().toString())) {
                if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
                    doRetrieveModel().setCountRightAnswers(doRetrieveModel().getCountRightAnswers() + 1);
                    setCorrectAnswerColor(layoutChoiceA, btnA, txtA, txtStateA);
                } else {
                    setWrongAnswerColor(layoutChoiceA, btnA, txtA, txtStateA);
                    findCorrectAnswer();
                }
            } else if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(txtB.getText().toString())) {
                if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
                    doRetrieveModel().setCountRightAnswers(doRetrieveModel().getCountRightAnswers() + 1);
                    setCorrectAnswerColor(layoutChoiceB, btnB, txtB, txtStateB);
                } else {
                    setWrongAnswerColor(layoutChoiceB, btnB, txtB, txtStateB);
                    findCorrectAnswer();
                }
            } else if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(txtC.getText().toString())) {
                if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
                    doRetrieveModel().setCountRightAnswers(doRetrieveModel().getCountRightAnswers() + 1);
                    setCorrectAnswerColor(layoutChoiceC, btnC, txtC, txtStateC);
                } else {
                    setWrongAnswerColor(layoutChoiceC, btnC, txtC, txtStateC);
                    findCorrectAnswer();
                }
            } else if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(txtD.getText().toString())) {
                if (doRetrieveModel().getSelectedAnswer().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
                    doRetrieveModel().setCountRightAnswers(doRetrieveModel().getCountRightAnswers() + 1);
                    setCorrectAnswerColor(layoutChoiceD, btnD, txtD, txtStateD);
                } else {
                    setWrongAnswerColor(layoutChoiceD, btnD, txtD, txtStateD);
                    findCorrectAnswer();
                }
            }

            if ((doRetrieveModel().getCountQuestionNo() + 1) >= doRetrieveModel().getContentCategoryResponseModel().getResults().size()) {
                txtBtnLanjut.setText(getResources().getString(R.string.txt_btn_finish));
            }

            rightAnswersCount.setText(String.format(getResources().getString(R.string.txt_right_answers), doRetrieveModel().getCountRightAnswers()));
            if (doRetrieveModel().getCountRightAnswers() > 1) {
                rightAnswersCount.setText(rightAnswersCount.getText().toString() + "s");
            }

            layoutChoiceA.setTag("");
            layoutChoiceB.setTag("");
            layoutChoiceC.setTag("");
            layoutChoiceD.setTag("");
        } else {
            doRetrieveModel().setCountQuestionNo(doRetrieveModel().getCountQuestionNo() + 1);
            initContent();
        }
    }

    public void findCorrectAnswer() {
        if (txtA.getText().toString().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
            setCorrectAnswerColor(layoutChoiceA, btnA, txtA, txtStateA);
        } else if (txtB.getText().toString().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
            setCorrectAnswerColor(layoutChoiceB, btnB, txtB, txtStateB);
        } else if (txtC.getText().toString().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
            setCorrectAnswerColor(layoutChoiceC, btnC, txtC, txtStateC);
        } else if (txtD.getText().toString().equalsIgnoreCase(doRetrieveModel().getCorrectAnswer())) {
            setCorrectAnswerColor(layoutChoiceD, btnD, txtD, txtStateD);
        }
    }

    public boolean isAnswerSelected() {
        if (!layoutChoiceA.getTag().equals("")) {
            return true;
        }

        if (!layoutChoiceB.getTag().equals("")) {
            return true;
        }

        if (!layoutChoiceC.getTag().equals("")) {
            return true;
        }

        if (!layoutChoiceD.getTag().equals("")) {
            return true;
        }

        return false;
    }

    @Override
    public MainModel doRetrieveModel() {
        if (model == null) {
            model = new MainModel();
        }
        return model;
    }

    @Override
    public void showScreenState(ScreenState screenState) {
        if (doRetrieveModel().isActivityRunning()) {
            switch (screenState) {
                case SHOW_IDLE_SCREEN:
                    showProgressBar(false);
                    break;
                case SHOW_LOADING_SCREEN:
                    showProgressBar(true);
                    break;
                case SHOW_STARTUP_SCREEN:
                    doRetrieveModel().setSelectedScreenState(ScreenState.SHOW_STARTUP_SCREEN);
                    showStartUpScreen();
                    break;
                case SHOW_CATEGORY_SCREEN:
                    doRetrieveModel().setSelectedScreenState(ScreenState.SHOW_CATEGORY_SCREEN);
                    showCategoryScreen();
                    break;
                case SHOW_CONTENT_SCREEN:
                    doRetrieveModel().setSelectedScreenState(ScreenState.SHOW_CONTENT_SCREEN);
                    showContentScreen();
                    break;
                default:
                    break;
            }
        }
    }

    private void showContentScreen() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setTitle(doRetrieveModel().getSelectedCategoryResponseModel().getName());
        rightAnswersCount.setText(String.format(getResources().getString(R.string.txt_right_answers), doRetrieveModel().getCountRightAnswers()));
        initContent();
        layoutStartup.setVisibility(View.GONE);
        layoutCategory.setVisibility(View.GONE);
        layoutContent.setVisibility(View.VISIBLE);
    }

    private void setDefaultAnswerColor() {
        layoutChoiceA.setBackgroundResource(R.drawable.rounded_white_black_border);
        layoutChoiceB.setBackgroundResource(R.drawable.rounded_white_black_border);
        layoutChoiceC.setBackgroundResource(R.drawable.rounded_white_black_border);
        layoutChoiceD.setBackgroundResource(R.drawable.rounded_white_black_border);

        txtA.setTextColor(getResources().getColor(R.color.black));
        txtB.setTextColor(getResources().getColor(R.color.black));
        txtC.setTextColor(getResources().getColor(R.color.black));
        txtD.setTextColor(getResources().getColor(R.color.black));

        btnA.setTextColor(getResources().getColor(R.color.black));
        btnB.setTextColor(getResources().getColor(R.color.black));
        btnC.setTextColor(getResources().getColor(R.color.black));
        btnD.setTextColor(getResources().getColor(R.color.black));

        txtA.setText("");
        txtB.setText("");
        txtC.setText("");
        txtD.setText("");

        txtStateA.setVisibility(View.GONE);
        txtStateB.setVisibility(View.GONE);
        txtStateC.setVisibility(View.GONE);
        txtStateD.setVisibility(View.GONE);
    }

    private void setSelectedAnswerColor(RelativeLayout layout, Button btn, TextView txt1, TextView txt2) {
        layout.setBackgroundResource(R.drawable.rounded_grey);
        btn.setTextColor(getResources().getColor(R.color.black));
        txt1.setTextColor(getResources().getColor(R.color.black));

        txt2.setVisibility(View.GONE);
    }

    private void setSelectedDefaultColor(RelativeLayout layout, Button btn, TextView txt1, TextView txt2) {
        layout.setBackgroundResource(R.drawable.rounded_white_black_border);
        btn.setTextColor(getResources().getColor(R.color.black));
        txt1.setTextColor(getResources().getColor(R.color.black));

        txt2.setVisibility(View.GONE);
    }

    private void setCorrectAnswerColor(RelativeLayout layout, Button btn, TextView txt1, TextView txt2) {
        layout.setBackgroundResource(R.drawable.rounded_green);
        btn.setTextColor(getResources().getColor(R.color.green));
        txt1.setTextColor(getResources().getColor(R.color.white));

        txt2.setText(getResources().getString(R.string.correct_answer));
        txt2.setVisibility(View.VISIBLE);
    }

    private void setWrongAnswerColor(RelativeLayout layout, Button btn, TextView txt1, TextView txt2) {
        layout.setBackgroundResource(R.drawable.rounded_red);
        btn.setTextColor(getResources().getColor(R.color.colorAccent));
        txt1.setTextColor(getResources().getColor(R.color.white));

        txt2.setText(getResources().getString(R.string.wrong_answer));
        txt2.setVisibility(View.VISIBLE);
    }

    private void showCategoryScreen() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setTitle(getResources().getString(R.string.select_category));
        layoutContent.setVisibility(View.GONE);
        layoutStartup.setVisibility(View.GONE);
        layoutCategory.setVisibility(View.VISIBLE);
    }

    private void showStartUpScreen() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        layoutCategory.setVisibility(View.GONE);
        layoutContent.setVisibility(View.GONE);
        layoutStartup.setVisibility(View.VISIBLE);
    }

    private void showProgressBar(boolean b) {
        if (b) {
            loadingLayout.setVisibility(View.VISIBLE);
        } else {
            loadingLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showState(ViewState viewState) {
        if (doRetrieveModel().isActivityRunning()) {
            switch (viewState) {
                case SNACKBAR:
                    showSnackbar();
                    break;
                case REQUEST_SESSION_SUCCESS:
                    requestSessionSuccess();
                    break;
                case REQUEST_SESSION_FAILURE:
                    requestSessionFailure();
                    break;
                case RESET_SESSION_SUCCESS:
                    resetSessionSuccess();
                    break;
                case RESET_SESSION_FAILURE:
                    resetSessionFailure();
                    break;
                case GET_CATEGORY_SUCCESS:
                    getCategorySuccess();
                    break;
                case GET_CATEGORY_FAILURE:
                    getCategoryFailure();
                    break;
                case GET_CONTENT_CATEGORY_SUCCESS:
                    getContentCategorySuccess();
                    break;
                case GET_CONTENT_CATEGORY_FAILURE:
                    getContentCategoryFailure();
                    break;
                default:
                    break;
            }
        }
    }

    private void resetSessionFailure() {
        presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
        getErrorMessage();
    }

    private void resetSessionSuccess() {
        if (doRetrieveModel().getResetSessionResponseModel().getResponseCode() == 0) {
            presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
            PreferencesManager.saveAsString(getApplicationContext(), Constant.SESSIONS, doRetrieveModel().getResetSessionResponseModel().getToken());
            doRetrieveModel().setToken(doRetrieveModel().getResetSessionResponseModel().getToken());
            doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.reset_session_success));
            presenter.showState(ViewState.SNACKBAR);
        } else {
            doRetrieveModel().setResponseCode(doRetrieveModel().getResetSessionResponseModel().getResponseCode());
            getErrorMessage();
        }
    }

    private void initContent() {
        if (doRetrieveModel().getCountQuestionNo() < doRetrieveModel().getContentCategoryResponseModel().getResults().size()) {
            doRetrieveModel().setCheckAnswer(false);
            setDefaultAnswerColor();
            questionNoCount.setText(String.format(getResources().getString(R.string.txt_question_no), (doRetrieveModel().getCountQuestionNo() + 1)));

            ContentCategoryResponseModel.Result result = doRetrieveModel().getContentCategoryResponseModel().getResults().get(doRetrieveModel().getCountQuestionNo());
            txtQuestion.setText(Html.fromHtml(result.getQuestion()));
            doRetrieveModel().setCorrectAnswer(String.valueOf(Html.fromHtml(result.getCorrectAnswer())));
            randomAnswers(result);
        } else {
            resultDialog();
        }
    }

    private void resultDialog() {
        double score = 0;
        int range = 0;
        if (doRetrieveModel().getDifficulty().equalsIgnoreCase("easy")) {
            range = 100;
            score = ((double) (doRetrieveModel().getCountRightAnswers() * 10) / doRetrieveModel().getContentCategoryResponseModel().getResults().size()) * 10;
        } else if (doRetrieveModel().getDifficulty().equalsIgnoreCase("medium")) {
            range = 200;
            score = ((double) (doRetrieveModel().getCountRightAnswers() * 20) / doRetrieveModel().getContentCategoryResponseModel().getResults().size()) * 10;
        } else if (doRetrieveModel().getDifficulty().equalsIgnoreCase("hard")) {
            range = 300;
            score = ((double) (doRetrieveModel().getCountRightAnswers() * 30) / doRetrieveModel().getContentCategoryResponseModel().getResults().size()) * 10;
        }

        MaterialDialog dialogAlert = new MaterialDialog.Builder(MainActivity.this)
                .content(String.format(getResources().getString(R.string.txt_result_game), doRetrieveModel().getDifficulty(), score, range))
                .positiveText(getResources().getString(R.string.txt_btn_ulangi))
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .negativeText(getResources().getString(R.string.txt_btn_keluar))
                .negativeColor(getResources().getColor(R.color.colorAccent))
                .cancelable(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        try {
                            dialog.hide();
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                        presenter.screenState(ScreenState.SHOW_STARTUP_SCREEN);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        try {
                            dialog.hide();
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                        finish();
                    }
                }).build();
        try {
            dialogAlert.show();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    private void randomAnswers(ContentCategoryResponseModel.Result result) {
        List<String> listAnswer = new ArrayList<>();
        listAnswer.addAll(result.getIncorrectAnswers());
        listAnswer.add(result.getCorrectAnswer());
        Collections.shuffle(listAnswer);

        txtA.setText(Html.fromHtml(listAnswer.get(0)));
        txtB.setText(Html.fromHtml(listAnswer.get(1)));
        txtC.setText(Html.fromHtml(listAnswer.get(2)));
        txtD.setText(Html.fromHtml(listAnswer.get(3)));
    }

    private void initCategoryRecyclerView() {
        categoryAdapter = new CategoryAdapter(getApplicationContext(), doRetrieveModel().getCategoryResponseModel());
        gridviewCategory.setAdapter(categoryAdapter);

        gridviewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                doRetrieveModel().setDifficultLevel(new CharSequence[]{"Easy", "Medium", "Hard"});

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(doRetrieveModel().getDifficultLevel(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doRetrieveModel().setSelectedCategoryResponseModel(doRetrieveModel().getCategoryResponseModel().getTriviaCategories().get(position));
                        doRetrieveModel().setCountQuestionNo(0);
                        doRetrieveModel().setCountRightAnswers(0);
                        doRetrieveModel().setDifficulty(doRetrieveModel().getDifficultLevel()[which].toString().toLowerCase());
                        doRetrieveModel().setAmount("20");
                        doRetrieveModel().setType("multiple");
                        doRetrieveModel().setCategory(doRetrieveModel().getSelectedCategoryResponseModel().getId().toString());
                        presenter.screenState(ScreenState.SHOW_LOADING_SCREEN);
                        presenter.showState(ViewState.GET_CONTENT_CATEGORY);
                    }
                });
                builder.show();

                // This tells the GridView to redraw itself
                // in turn calling your BooksAdapter's getView method again for each cell
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getErrorMessage() {
        if (Connectivity.isConnected(getApplicationContext())) {
            switch (doRetrieveModel().getResponseCode()) {
                case 1:
                    doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.error_no_result));
                    break;
                case 2:
                    doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.error_invalid_parameter));
                    break;
                case 3:
                    doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.error_token_session));
                    break;
                case 4:
                    doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.error_token_session_need_reset));
                    break;
                default:
                    doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.error_general));
                    break;
            }
        } else {
            doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.error_koneksi_internet));
        }
        presenter.showState(ViewState.SNACKBAR);
        presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
    }

    private void getContentCategoryFailure() {
        presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
        getErrorMessage();
    }

    private void getContentCategorySuccess() {
        if (doRetrieveModel().getContentCategoryResponseModel().getResponseCode() == 0) {
            presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
            presenter.screenState(ScreenState.SHOW_CONTENT_SCREEN);
        } else {
            doRetrieveModel().setResponseCode(doRetrieveModel().getContentCategoryResponseModel().getResponseCode());
            getErrorMessage();
        }
    }

    private void getCategoryFailure() {
        presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
        getErrorMessage();
    }

    private void getCategorySuccess() {
        presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
        initCategoryRecyclerView();
        presenter.screenState(ScreenState.SHOW_CATEGORY_SCREEN);
    }

    private void requestSessionFailure() {
        presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
        getErrorMessage();
    }

    private void requestSessionSuccess() {
        if (doRetrieveModel().getRequestSessionResponseModel().getResponseCode() == 0) {
            presenter.screenState(ScreenState.SHOW_IDLE_SCREEN);
            PreferencesManager.saveAsString(getApplicationContext(), Constant.SESSIONS, doRetrieveModel().getRequestSessionResponseModel().getToken());
            doRetrieveModel().setToken(doRetrieveModel().getRequestSessionResponseModel().getToken());
            doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.request_session_success));
            presenter.showState(ViewState.SNACKBAR);
        } else {
            doRetrieveModel().setResponseCode(doRetrieveModel().getRequestSessionResponseModel().getResponseCode());
            getErrorMessage();
        }
    }

    private void showSnackbar() {
        if (doRetrieveModel().getSnackbarMessage() != null && !doRetrieveModel().getSnackbarMessage().equalsIgnoreCase("")) {
            Snackbar.make(mainContent, doRetrieveModel().getSnackbarMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_reset_token)
    public void onClickResetToken() {
        presenter.screenState(ScreenState.SHOW_LOADING_SCREEN);
        presenter.showState(ViewState.RESET_SESSION);
    }

    @OnClick(R.id.btn_request_token)
    public void onClickRequestToken() {
        presenter.screenState(ScreenState.SHOW_LOADING_SCREEN);
        presenter.showState(ViewState.REQUEST_SESSION);
    }

    @OnClick(R.id.btn_mulai)
    public void onClickMulai() {
        doRetrieveModel().setToken(PreferencesManager.getAsString(getApplicationContext(), Constant.SESSIONS, false));
        if (doRetrieveModel().getToken() == null) {
            doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.error_session_token_not_found));
            presenter.showState(ViewState.SNACKBAR);
        } else {
            presenter.screenState(ScreenState.SHOW_LOADING_SCREEN);
            presenter.showState(ViewState.GET_CATEGORY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenterImpl(this);
        ButterKnife.bind(this);

        presenter.screenState(ScreenState.SHOW_STARTUP_SCREEN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        doRetrieveModel().setActivityRunning(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        doRetrieveModel().setActivityRunning(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doRetrieveModel().getSelectedScreenState() == ScreenState.SHOW_CATEGORY_SCREEN) {
            presenter.screenState(ScreenState.SHOW_STARTUP_SCREEN);
        } else if (doRetrieveModel().getSelectedScreenState() == ScreenState.SHOW_CONTENT_SCREEN) {
            confirmDialogExitGame();
        } else {
            clickBackTwiceToExit();
        }
    }

    public void confirmDialogExitGame() {
        MaterialDialog dialogAlert = new MaterialDialog.Builder(MainActivity.this)
                .content(getResources().getString(R.string.txt_exit_game))
                .negativeText(getResources().getString(R.string.txt_btn_no))
                .positiveText(getResources().getString(R.string.txt_btn_yes))
                .positiveColor(getResources().getColor(R.color.colorPrimary))
                .negativeColor(getResources().getColor(R.color.colorAccent))
                .cancelable(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        try {
                            dialog.hide();
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                    }

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        try {
                            dialog.hide();
                            dialog.dismiss();
                        } catch (Exception e) {
                            e.getLocalizedMessage();
                        }
                        presenter.screenState(ScreenState.SHOW_STARTUP_SCREEN);
                    }
                }).build();
        try {
            dialogAlert.show();
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    public void clickBackTwiceToExit() {
        if (doRetrieveModel().isClickBackTwice()) {
            super.onBackPressed();
            return;
        }
        doRetrieveModel().setClickBackTwice(true);
        doRetrieveModel().setSnackbarMessage(getResources().getString(R.string.title_backbtn_exit));
        presenter.showState(ViewState.SNACKBAR);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doRetrieveModel().setClickBackTwice(false);
            }
        }, 2000);
    }
}
