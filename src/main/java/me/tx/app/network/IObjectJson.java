package me.tx.app.network;

import me.tx.app.ui.activity.BaseActivity;

public abstract class IObjectJson extends IObject {
    public IObjectJson(BaseActivity activity) {
        super(activity);
    }

    public IObjectJson(BaseActivity activity,boolean nl) {
        super(activity,nl);
    }

    @Override
    public boolean iamJson() {
        return true;
    }

    @Override
    public boolean iamForm() {
        return false;
    }
}
