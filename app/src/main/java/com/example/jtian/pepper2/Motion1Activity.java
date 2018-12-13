package com.example.jtian.pepper2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.GoToBuilder;
import com.aldebaran.qi.sdk.builder.TransformBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Actuation;
import com.aldebaran.qi.sdk.object.actuation.Frame;
import com.aldebaran.qi.sdk.object.actuation.FreeFrame;
import com.aldebaran.qi.sdk.object.actuation.GoTo;
import com.aldebaran.qi.sdk.object.actuation.Mapping;
import com.aldebaran.qi.sdk.object.geometry.Transform;

public class Motion1Activity extends RobotActivity implements RobotLifecycleCallbacks {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion1);
        QiSDK.register(this,this);
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        //Get the Robot Frame
        Actuation actuation = qiContext.getActuation();
        Frame robotFrame = actuation.robotFrame();

        //Move 1 Meter Forward (X Axis is forward/backward)
        Transform transform = TransformBuilder.create()
                .fromXTranslation(1);

        Mapping mapping = qiContext.getMapping();
        FreeFrame targetFrame = mapping.makeFreeFrame();

        //Update the frame; currentFrame, targetFrame
        targetFrame.update(robotFrame, transform, System.currentTimeMillis());

        GoTo goTo;
        // Create the action   
        goTo = GoToBuilder.with(qiContext)           
                .withFrame(targetFrame.frame()) // Set the target frame.          
                .build();  
        // Add a consumer to the action execution.   
        goTo.run();
        
    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }
}
