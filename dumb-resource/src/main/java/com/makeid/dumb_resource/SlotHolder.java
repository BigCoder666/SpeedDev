package com.makeid.dumb_resource;

import com.makeid.dumb_resource.databinding.ItemSlotBinding;

import me.tx.app.common.base.CommonHolder;

/**
 * @author tx
 * @date 2022/12/23 13:36
 */
public class SlotHolder extends CommonHolder<ItemSlotBinding> {

    public IDiscCreate disc;

    public void setDisc(IDiscCreate disc){
        this.disc = disc;
    }

    public boolean haveDisc(){
        return disc!=null;
    }

    public void removeDisc(){
        disc = null;
    }

    public SlotHolder(ItemSlotBinding itemSlotBinding) {
        super(itemSlotBinding);
    }
}
