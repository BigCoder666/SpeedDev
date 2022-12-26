package com.makeid.dumb_resource;

import java.util.List;

/**
 * @author tx
 * @date 2022/12/26 11:20
 */
public interface ISplitterCreate {
    //1:8 = 8
    int getRate();

    IPortCreate getInPort();

    List<IPortCreate> getIPortCreate();
}
