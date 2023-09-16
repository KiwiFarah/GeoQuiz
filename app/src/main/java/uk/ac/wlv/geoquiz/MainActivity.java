package uk.ac.wlv.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,true),
            new Question(R.string.question_asia,true),
    };

    private int mCurrentIndex = 0;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;


    private void updateQuestion(){
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer (boolean userPressedTrue){
        boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        mNextButton = (ImageButton) findViewById((R.id.next_button));
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();

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

            }
        });
    }


}