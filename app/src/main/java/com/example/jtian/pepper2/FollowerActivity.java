package com.example.jtian.pepper2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.GoToBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.builder.TransformBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Actuation;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.actuation.Frame;
import com.aldebaran.qi.sdk.object.actuation.FreeFrame;
import com.aldebaran.qi.sdk.object.actuation.GoTo;
import com.aldebaran.qi.sdk.object.actuation.Mapping;
import com.aldebaran.qi.sdk.object.conversation.Say;
import com.aldebaran.qi.sdk.object.geometry.Transform;
import com.aldebaran.qi.sdk.object.geometry.TransformTime;
import com.aldebaran.qi.sdk.object.geometry.Vector3;
import com.aldebaran.qi.sdk.object.human.Human;
import com.aldebaran.qi.sdk.object.humanawareness.HumanAwareness;

public class FollowerActivity extends RobotActivity implements RobotLifecycleCallbacks {

    private Say sayHello;
    private HumanAwareness humanAwareness;
    QiContext qiContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);
        QiSDK.register(this,this);
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        this.qiContext = qiContext;
        sayHello = SayBuilder.with(qiContext).withText("Hi there").build();
        humanAwareness = qiContext.getHumanAwareness();
        humanAwareness.addOnEngagedHumanChangedListener(this::onEngageHuman);
        onEngageHuman(humanAwareness.getEngagedHuman()); // ini
    }




    private void onEngageHuman(Human engagedHuman) {
        if (engagedHuman!=null) {
            //sayHello.async().run();

//            Mapping mapping = qiContext.getMapping();
//            FreeFrame targetFrame = mapping.makeFreeFrame();

//            Actuation actuation = qiContext.getActuation();
//            Frame robotFrame = actuation.robotFrame();
//
//            TransformTime transformTime = humanFrame.computeTransform(robotFrame);
//            Transform transform = transformTime.getTransform();
//            targetFrame.update(robotFrame, transform, System.currentTimeMillis());
            
            Frame humanFrame = engagedHuman.getHeadFrame();
            GoTo goTo = GoToBuilder.with(qiContext)
                    .withFrame(humanFrame) // Set the target frame.
                    .build();   // Add a consumer to the action execution
            goTo.run();

            onEngageHuman(engagedHuman);
        }

    }

    @Override
    public void onRobotFocusLost() {
        this.qiContext = null;
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
