package com.makeid.dumb_resource;

import com.makeid.dumb_resource.databinding.ItemPortBinding;
import com.makeid.dumb_resource.databinding.ItemSlotBinding;

import me.tx.app.common.base.CommonHolder;

/**
 * @author tx
 * @date 2022/12/23 15:39
 */
public class PortHolder extends CommonHolder<ItemPortBinding> {

    public IPortCreate port;

    public void setPort(IPortCreate port){
        this.port = port;
    }

    public PortHolder(ItemPortBinding itemPortBinding) {
        super(itemPortBinding);
    }
}