package uk.ac.wlv.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;
import android.util.Log;
import android.content.Intent;
import android.app.Activity;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String KEY_IS_CHEATER = "user_activity_is_cheater";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,true),
            new Question(R.string.question_asia,true),
            new Question(R.string.question_europe,false),
            new Question(R.string.question_australia,true),
            new Question(R.string.question_mountains,false),
            new Question(R.string.question_antarctica,true),
            new Question(R.string.question_desert,false)
    };
    private boolean[] mIsCheaterArray = new boolean[mQuestions.length];
    private int mCurrentIndex = 0;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private boolean mIsCheater;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mIsCheaterArray[mCurrentIndex] = mIsCheater;
        }
    }



    private int mCorrectlyAnswered = 0;
    private TextView mSuccessRateTextView;

    private void updateSuccessRate() {
        int totalQuestionsAnswered = mCurrentIndex + 1;
        double successRate = ((double) mCorrectlyAnswered / totalQuestionsAnswered) * 100;

        successRate = Math.min(successRate, 100.0);

        mSuccessRateTextView.setText(String.format("Success Rate is: %.2f%%", successRate));
    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mIsCheater = mIsCheaterArray[mCurrentIndex];
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater ){

            messageResId = R.string.judgement_toast;
        }
        else{
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mCorrectlyAnswered++;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        updateSuccessRate();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            boolean[] savedCheaterArray = savedInstanceState.getBooleanArray(KEY_IS_CHEATER);
            if (savedCheaterArray != null) {
                mIsCheaterArray = savedCheaterArray;
            }
            mIsCheater = mIsCheaterArray[mCurrentIndex];
        }
       mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentIndex > 0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestions.length;
                } else {
                    mCurrentIndex = mQuestions.length - 1;
                }
                updateQuestion();
            }
        });

        mSuccessRateTextView = (TextView) findViewById(R.id.success_rate_text_view);

    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(KEY_IS_CHEATER, mIsCheaterArray);
    }


}