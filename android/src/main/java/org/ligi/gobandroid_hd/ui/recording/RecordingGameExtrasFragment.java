package org.ligi.gobandroid_hd.ui.recording;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.ligi.axt.simplifications.SimpleTextWatcher;
import org.ligi.gobandroid_hd.App;
import org.ligi.gobandroid_hd.R;
import org.ligi.gobandroid_hd.logic.GoGame;
import org.ligi.gobandroid_hd.logic.GoGame.GoGameChangeListener;
import org.ligi.gobandroid_hd.ui.fragments.GobandroidFragment;

public class RecordingGameExtrasFragment extends GobandroidFragment implements GoGameChangeListener {

    private EditText et;
    private Handler handler = new Handler();
    private GoGame game;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        et = new EditText(getActivity());

        game = App.getGame();
        game.addGoGameChangeListener(this);

        et.setText(game.getActMove().getComment());
        et.setHint(R.string.enter_your_comments_here);
        et.setGravity(Gravity.TOP);
        et.setTextColor(this.getResources().getColor(R.color.text_color_on_board_bg));

        et.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                game.getActMove().setComment(s.toString());
            }
        });

        et.setLayoutParams(lp);
        return et;
    }

    @Override
    public void onDestroyView() {
        game.removeGoGameChangeListener(this);
        super.onDestroyView();
    }

    @Override
    public void onGoGameChange() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if ((et != null) && getActivity() != null) {
                    et.setText(game.getActMove().getComment());
                }
            }

        });
    }

}
