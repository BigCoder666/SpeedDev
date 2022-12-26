package com.makeid.dumb_resource;

import java.util.List;

/**
 * @author tx
 * @date 2022/12/23 12:50
 */
public interface IFiberFaceCreate {
    String getFaceName();

    String getNfcCode();

    String getQRCode();

    String getLockCode();

    boolean haveLock();

    boolean haveNfc();

    boolean haveQRCode();

    void onNfcClick();

    void onQRClick();

    void onLockClick();

    int maxSlotCount();

    List<IDiscCreate> getIDiscCreate();

    boolean haveSplitter();

    List<ISplitterCreate> getISplitterCreate();
}
