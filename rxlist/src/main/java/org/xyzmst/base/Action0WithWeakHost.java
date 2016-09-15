package org.xyzmst.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.Modifier;

import rx.functions.Action0;

/**
 * $CLASS_NAME
 *
 * @author Green
 * @since: 15/2/27 下午4:21
 */
public class Action0WithWeakHost<H> implements Action0 {
    WeakReference<H> mWeakRefHost;
    public Action0WithWeakHost(H host){
        if(getClass().isAnonymousClass()){
            throw new IllegalStateException("SubscriberWithWeakHost must not be declared as an anonymousClass");
        }
        if(getClass().isLocalClass()){
            throw new IllegalStateException("SubscriberWithWeakHost must not be declared as an LocalClass");
        }
        if(getClass().isMemberClass()&&!Modifier.isStatic(getClass().getModifiers())){
            throw new IllegalStateException("SubscriberWithWeakHost must be declared as an static member class or single class");
        }
        if(host==null){
            throw new IllegalArgumentException("host must not be null");
        }
        mWeakRefHost = new WeakReference<H>(host);
    }

    public boolean isHostExist(){
        return mWeakRefHost!=null&&mWeakRefHost.get()!=null;
    }

    public H getHost(){
        if(isHostExist()){
            return mWeakRefHost.get();
        }
        return null;
    }

    @Override
    public void call() {

    }
}
