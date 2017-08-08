package erick.android.textexpandable;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int descriptionLineCount = 0;
    private TextView tvDescription;
    private boolean isExpansion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvDescription.setText(getString(R.string.description));
        tvDescription.post(new Runnable() {
            @Override
            public void run() {
                descriptionLineCount = tvDescription.getLineCount();
                tvDescription.setMaxLines(4);
                tvDescription.setEllipsize(TextUtils.TruncateAt.END);
            }
        });

        tvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvDescription.getLineCount() >= 4) {
                    if (isExpansion) {
                        animExpansion(descriptionLineCount, 4);
                    } else {
                        animExpansion(4, descriptionLineCount);
                    }
                    isExpansion = !isExpansion;
                }
            }
        });
    }

    private void animExpansion(int fromLineCount, final int toLineCount) {
        int lineHeight = tvDescription.getLineHeight();
        ValueAnimator anim = ValueAnimator.ofInt(fromLineCount * lineHeight, toLineCount * lineHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvDescription.setMaxLines(toLineCount);
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = tvDescription.getLayoutParams();
                params.height = value;
                tvDescription.setLayoutParams(params);
            }
        });
        anim.setDuration(600);
        anim.start();
    }
}
