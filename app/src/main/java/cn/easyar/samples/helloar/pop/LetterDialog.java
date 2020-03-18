package cn.easyar.samples.helloar.pop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import cn.easyar.samples.helloar.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.xuexiang.xutil.XUtil.runOnUiThread;

public class LetterDialog extends AlertDialog {

    private LinearLayout envelope;

    //    缩放动画
    private ZoomFromThumbAnimation zoomFromThumbAnimation;
    private RelativeLayout container;
    private ImageView btnCancel;
    private ImageView btnSeal;
    private ImageView ivCover;
    private ImageView ivTriangle;
    private boolean isOpenLetter = false;
    private ImageView ivBag;
    private RelativeLayout layoutLetterPaper;
    private ConstraintLayout.LayoutParams layoutLetterPaperParams;
    private LinearLayout layoutLetterContent;


    ;

    public LetterDialog(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.activity_reverse_animation, null);

        setView(view);
    }

    @Override
    public void setView(View view) {
        super.setView(view);
//        initialView = view.findViewById(R.id.btn_bubble);
        envelope = view.findViewById(R.id.layout_letter_dialog);
        btnCancel = view.findViewById(R.id.iv_cancel);
        container = view.findViewById(R.id.container);
        btnSeal = view.findViewById(R.id.btn_seal);
        ivBag = view.findViewById(R.id.ivBagBG);
        ivCover = view.findViewById(R.id.ivCover);
        ivTriangle = view.findViewById(R.id.ivTriangle);
        layoutLetterContent = view.findViewById(R.id.layout_content);
        layoutLetterPaper = view.findViewById(R.id.layout_concessionary_card);
        layoutLetterPaperParams = (ConstraintLayout.LayoutParams) layoutLetterPaper.getLayoutParams();

        zoomFromThumbAnimation = ZoomFromThumbAnimation.getIntents();
        zoomFromThumbAnimation.setOnAnimationClick(new ZoomFromThumbAnimation.OnAnimationClick() {
            @Override
            public void onOneAnimationEnd() {
//                显示
                showView();
            }
        });
        btnSeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenLetter)
                    //                对于信封打开与否进行的 控制
                    return;
                isOpenLetter = true;
                //                调用打开信件的函数
                openLetterAnim();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetView();
                dismiss();
            }
        });


    }
    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        获得屏幕的 长宽
        getWindow().setAttributes(layoutParams);
//        背景透明化
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                zoomFromThumbAnimation.zoomImageFromThumb(initialView, container, envelope);
//            }
//        });
    }
    public void showView()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnCancel.setVisibility(VISIBLE);
            }
        });
    }
    private void openLetterAnim()
    {
        ivBag.post(new Runnable() {
            @Override
            public void run() {
                layoutLetterPaperParams.height=ivBag.getHeight();
                layoutLetterPaperParams.width=ivBag.getWidth()-20;
                rotate();
            }
        });
    }
    private long animatorTime=1000;
    public void rotate()
    {
//       信纸
        layoutLetterPaper.setLayoutParams(layoutLetterPaperParams);

        ivCover.setPivotY(0);
        final ObjectAnimator animator=ObjectAnimator.ofFloat(ivCover,"scaleY",1f,0f);
        animator.setDuration(animatorTime/2);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                showView();
              layoutLetterPaper.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ivCover.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ivTriangle.setPivotY(ivTriangle.getHeight());
        final ObjectAnimator animator2=ObjectAnimator.ofFloat(ivTriangle,"scaleY",0,1f);
        animator2.setDuration(animatorTime/2);
        animator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ivTriangle.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        ValueAnimator va = ValueAnimator.ofInt(layoutLetterPaperParams.height, (layoutLetterPaperParams.height ) + layoutLetterContent.getHeight() *2);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int h = (int) animation.getAnimatedValue();
                layoutLetterPaperParams.height = h;
                layoutLetterPaper.setLayoutParams(layoutLetterPaperParams);

            }
        });

        va.setDuration(animatorTime*2);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(animator2).after(animator);
        animatorSet.play(va).after(animator2);
        animatorSet.start();
    }
    private void resetView(){
        ivCover.setScaleY(1);
        ivCover.setVisibility(VISIBLE);
        ivTriangle.setVisibility(View.INVISIBLE);
        layoutLetterPaperParams.height=ivBag.getHeight();
        layoutLetterPaper.setLayoutParams(layoutLetterPaperParams);
    }

}
