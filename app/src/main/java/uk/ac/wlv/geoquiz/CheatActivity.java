package uk.ac.wlv.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import android.os.Bundle;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "uk.ac.wlv.geoquiz.answer is true";
    private static final String EXTRA_ANSWER_SHOWN = "uk.ac.wlv.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                if (mAnswerIsTrue) {

                    mAnswerTextView.setText(R.string.true_button);
                }else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }
    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}