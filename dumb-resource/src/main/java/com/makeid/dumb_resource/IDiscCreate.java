package com.makeid.dumb_resource;

import java.util.List;

/**
 * @author tx
 * @date 2022/12/23 13:54
 */
public interface IDiscCreate {

    String getDiscName();

    int getPortNumber();

    int getNumberEveryLine();

    EPortType getPortType();

    List<IPortCreate> getIPortCreate();
}
