package tw.org.iii.brad.brad26;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private final Binder mBinder = new LocalBinder();
    public boolean isBind;


    public class LocalBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        isBind = true;
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isBind = false;
        Log.v("brad","onUnbind");
        return super.onUnbind(intent);
    }

    public int i = 0;

    public int lottery(){
        i++;
        try{
            Thread.sleep(3*1000);
        }catch (Exception e){}
        return (int)(Math.random()*49+1);
    }
}
