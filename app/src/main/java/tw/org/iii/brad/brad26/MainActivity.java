package tw.org.iii.brad.brad26;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyService myService;
    private boolean isBind;
    private TextView mesg;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyService.LocalBinder binder = (MyService.LocalBinder)iBinder; //此處iBinder就是onBind的mBinder物件實體
            myService = binder.getService();//是MyService裡的內部類別的方法return回來的
            Log.v("brad","onServiceConnected");
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v("brad","onServiceDisconneted");
            isBind = false;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.v("brad","onBindingDied");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.v("brad","onNullBinding");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mesg = findViewById(R.id.mesg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("brad","onStart");
        Intent intent = new Intent(this,MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE); //三參數flag通常是這個, 此行會去觸發MyService的onBind方法
    }

    @Override
    protected void onStop() {
        Log.v("brad","onStop");
        if (isBind){
            Log.v("brad","disconnecting...");
            unbindService(mConnection);
        }
        super.onStop();

    }

    public void atest1(View view) {
        if(isBind && myService != null){
            int lottery = myService.lottery();
            Log.v("brad","lottery = " + myService.i+ ":" + lottery);
            mesg.setText("lottery = " + myService.i+ ":" + lottery);
        }
    }

    public void atest2(View view) {
        Log.v("brad",""+isBind);
        if(isBind){
            unbindService(mConnection);
            isBind = false;
        }
    }

    public void atest3(View view) {
        if(mConnection == null){
            Log.v("brad","null Connection");
        }
        if(myService == null){
            Log.v("brad","null Service");
        }
        Log.v("brad","==> " + myService.isBind);
    }
}
