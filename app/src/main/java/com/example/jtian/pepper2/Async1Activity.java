package com.example.jtian.pepper2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aldebaran.qi.Future;
import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.SayBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Say;

public class Async1Activity extends RobotActivity implements RobotLifecycleCallbacks {
    QiContext qiContext = null;
    Say say = null;
    Future<Void> sayFuture = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_async1);
        setContentView(R.layout.activity_async1);
        QiSDK.register(this, this);
        
        final Button sayHelloButton = findViewById(R.id.sayhello);
        sayHelloButton.setOnClickListener((View v) -> {
            //say.async().run();

//            if (say != null) {
//                Future<Void> sayFuture = say.async().run();
//                sayFuture.thenConsume(this::onSayDone);
//            }


            if (say == null) {       }
            else if ((sayFuture == null) || (sayFuture.isDone())) {
                sayFuture = say.async().run();
                sayFuture.thenConsume(this::showFutureResult);
            }
            else {
                // Say future is not done: cancel it
                sayFuture.requestCancellation();
            }
            
        });
    }

    private void showFutureResult(Future<Void> sayFuture) {
        if (sayFuture.hasError()) {
            showToast("Error: " + sayFuture.getErrorMessage());
        } else {
            showToast("Say Done");
        }
    }

    private void onSayDone(Future<Void> sayFuture) {
        if (sayFuture.hasError()) {
            showToast("Error: " + sayFuture.getErrorMessage());   
        } else {       
            showToast("Say Done");   
        }
    }

    private void showToast(String msg) {
        runOnUiThread(()->{
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
//        Say say = SayBuilder.with(qiContext)
//                .withText("Hello everybody!")
//                .build();
//        Animation anim = AnimationBuilder.with(qiContext)
//                .withResources(R.raw.hello_a001)
//                .build();
//        Animate animate = AnimateBuilder.with(qiContext)
//                .withAnimation(anim)
//                .build();
//        say.async().run();
//        animate.run();
        this.qiContext = qiContext;
        say = SayBuilder.with(qiContext)
                .withText("Hello everybody, I  never said that.He came out with a load of crap about how he'd tried to call me yesterday.Greg's full of crap (=often says things that are completely wrong).cut the crap (=used to tell someone to stop saying things that are completely wrong)Just cut the crap and tell me what really happened.!")
                .build();
    }

    @Override
    public void onRobotFocusLost() {

    }

    @Override
    public void onRobotFocusRefused(String reason) {

    }
}
