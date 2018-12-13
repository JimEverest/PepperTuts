package com.example.jtian.pepper2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionImportance;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionValidity;
import com.aldebaran.qi.sdk.object.conversation.BaseQiChatExecutor;
import com.aldebaran.qi.sdk.object.conversation.Bookmark;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.QiChatExecutor;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.human.Human;
import com.aldebaran.qi.sdk.object.humanawareness.HumanAwareness;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {
    private Say sayHello;
    private HumanAwareness humanAwareness;
    private int number =10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QiSDK.register(this,this);
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        Say say = SayBuilder.with(qiContext)
                .withText("Hello everybody!")
                .build();
        Animation anim = AnimationBuilder.with(qiContext)
                .withResources(R.raw.feline_a001)
                .build();
        Animate animate = AnimateBuilder.with(qiContext)
                .withAnimation(anim)
                .build();
        say.run();
        animate.run();

    }

    private void onEngageHuman(Human engagedHuman) {
        if (engagedHuman!=null) {
            sayHello.run();   }
    }

    @Override
    public void onRobotFocusLost() {
        if (humanAwareness != null) {
            humanAwareness.removeAllOnEngagedHumanChangedListeners();
        }
    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        QiSDK.unregister(this,this);
    }


}
