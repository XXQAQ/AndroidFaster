package com.xq.androidfaster.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.xq.androidfaster.base.core.Life;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FasterLifecycleRegistry extends LifecycleRegistry {

    private List<LifecycleObserver> observerList = new CopyOnWriteArrayList<>();

    /**
     * Creates a new LifecycleRegistry for the given provider.
     * <p>
     * You should usually create this inside your LifecycleOwner class's constructor and hold
     * onto the same instance.
     *
     * @param provider The owner LifecycleOwner
     */
    public FasterLifecycleRegistry(@NonNull LifecycleOwner provider) {
        super(provider);
    }

    @Override
    public void addObserver(@NonNull LifecycleObserver observer) {
        super.addObserver(observer);
        if (!observerList.contains(observer))
            observerList.add(observer);
    }

    @Override
    public void removeObserver(@NonNull LifecycleObserver observer) {
        super.removeObserver(observer);
        observerList.remove(observer);
    }

    public void handleCreate(Bundle savedInstanceState) {
        for (LifecycleObserver observer : observerList)
        {
            if (observer instanceof Life)
                ((Life) observer).create(savedInstanceState);
        }
    }

    public void handleVisible() {
        for (LifecycleObserver observer : observerList)
        {
            if (observer instanceof Life)
                ((Life) observer).visible();
        }
    }

    public void handleInvisible() {
        for (LifecycleObserver observer : observerList)
        {
            if (observer instanceof Life)
                ((Life) observer).invisible();
        }
    }

    public void handleDestroy() {
        for (LifecycleObserver observer : observerList)
        {
            if (observer instanceof Life)
                ((Life) observer).destroy();
        }
    }

    public void handleSaveInstanceState(Bundle savedInstanceState) {
        for (LifecycleObserver observer : observerList)
        {
            if (observer instanceof Life)
                ((Life) observer).onSaveInstanceState(savedInstanceState);
        }
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        for (LifecycleObserver observer : observerList)
        {
            if (observer instanceof Life)
                ((Life) observer).onActivityResult(requestCode,resultCode,data);
        }
    }

}
