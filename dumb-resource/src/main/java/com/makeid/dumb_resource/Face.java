package com.makeid.dumb_resource;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeid.dumb_resource.databinding.FragmentFaceBinding;
import com.makeid.dumb_resource.databinding.ItemPortBinding;
import com.makeid.dumb_resource.databinding.ItemSlotBinding;
import com.makeid.dumb_resource.databinding.ItemSplitterBinding;
import com.makeid.dumb_resource.databinding.ItemSplitterPortBinding;

import java.util.List;

import me.tx.app.ui.fragment.BaseFragment;
import me.tx.app.utils.OneClicklistener;

/**
 * @author tx
 * @date 2022/12/23 10:54
 */
public class Face extends BaseFragment<FragmentFaceBinding> {

    boolean open = false;

    IFiberFaceCreate iFiberFaceCreate;
    List<IDiscCreate> iDiscCreateList;

    public static Face getInstance(IFiberFaceCreate iFiberFaceCreate, List<IDiscCreate> iDiscCreates){
        Face face = new Face();
        face.setiFiberFaceCreate(iFiberFaceCreate);
        face.setiDiscCreateList(iDiscCreates);
        if(iFiberFaceCreate.maxSlotCount()!=iDiscCreates.size()){
            //槽位和盘列表不符合
            return null;
        }
        return face;
    }

    public void setiFiberFaceCreate(IFiberFaceCreate iFiberFaceCreate){
        this.iFiberFaceCreate = iFiberFaceCreate;
    }

    public void setiDiscCreateList(List<IDiscCreate> iDiscCreates){
        this.iDiscCreateList = iDiscCreates;
    }

    @Override
    public FragmentFaceBinding getVb() {
        return FragmentFaceBinding.inflate(getLayoutInflater());
    }

    @Override
    public void setView(View view) {
        fvb.faceName.setText(iFiberFaceCreate.getFaceName());
        fvb.openCloseLayout.setOnClickListener(new OneClicklistener() {
            @Override
            public void click(View view) {
                open = !open;
                if(open){
                    fvb.door.setVisibility(View.GONE);
                }else {
                    fvb.door.setVisibility(View.VISIBLE);
                }
            }
        });
        if(iFiberFaceCreate.haveLock()){
            fvb.lock.setVisibility(View.VISIBLE);
            fvb.lock.setOnClickListener(v -> {
                iFiberFaceCreate.onLockClick();
            });
        }else {
            fvb.lock.setVisibility(View.GONE);
            fvb.lock.setOnClickListener(null);
        }
        if(iFiberFaceCreate.haveNfc()){
            fvb.nfc.setVisibility(View.VISIBLE);
            fvb.nfc.setOnClickListener(v -> {
                iFiberFaceCreate.onNfcClick();
            });
        }else {
            fvb.nfc.setVisibility(View.GONE);
            fvb.nfc.setOnClickListener(null);
        }
        if(iFiberFaceCreate.haveQRCode()){
            fvb.qr.setVisibility(View.VISIBLE);
            fvb.qr.setOnClickListener(v -> {
                iFiberFaceCreate.onQRClick();
            });
        }else {
            fvb.qr.setVisibility(View.GONE);
            fvb.qr.setOnClickListener(null);
        }
    }

    @Override
    public void load() {
        fvb.slotRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fvb.slotRecycler.setAdapter(new RecyclerView.Adapter<SlotHolder>() {
            @NonNull
            @Override
            public SlotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SlotHolder slotHolder = new SlotHolder(ItemSlotBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
                return slotHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull SlotHolder holder, int position) {
                holder.setIsRecyclable(false);
                holder.setDisc(iDiscCreateList.get(position));
                if(holder.haveDisc()) {
                    holder.hvb.slotName.setText(iDiscCreateList.get(position).getDiscName());
                    holder.hvb.portRecycler.setVisibility(View.VISIBLE);
                    holder.hvb.portRecycler.setLayoutManager(new GridLayoutManager(getContext(),iDiscCreateList.get(position).getNumberEveryLine()));
                    holder.hvb.portRecycler.setAdapter(new RecyclerView.Adapter<PortHolder>() {
                        @NonNull
                        @Override
                        public PortHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return new PortHolder(ItemPortBinding.inflate(LayoutInflater.from(parent.getContext())));
                        }

                        @Override
                        public void onBindViewHolder(@NonNull PortHolder holder, int p) {
                            holder.setPort(iDiscCreateList.get(position).getIPortCreate().get(p));
                            switch (iDiscCreateList.get(position).getPortType()){
                                case FC:{
                                    holder.hvb.circle.setImageResource(R.drawable.fc_port);
                                    switch (holder.port.getPortState()){
                                        case USED:{
                                            holder.hvb.state.setImageResource(R.drawable.fc_port_inside_used);
                                            break;
                                        }
                                        case EMPTY:{
                                            holder.hvb.state.setImageResource(R.drawable.fc_port_inside_empty);
                                            break;
                                        }
                                        case BROKEN:{
                                            holder.hvb.state.setImageResource(R.drawable.fc_port_inside_broken);
                                            break;
                                        }
                                    }
                                    break;
                                }
                                case SC:{
                                    holder.hvb.circle.setImageResource(R.drawable.sc_port);
                                    switch (holder.port.getPortState()){
                                        case USED:{
                                            holder.hvb.state.setImageResource(R.drawable.sc_port_inside_used);
                                            break;
                                        }
                                        case EMPTY:{
                                            holder.hvb.state.setImageResource(R.drawable.sc_port_inside_empty);
                                            break;
                                        }
                                        case BROKEN:{
                                            holder.hvb.state.setImageResource(R.drawable.sc_port_inside_broken);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            if(holder.port.showNumber()) {
                                holder.hvb.number.setText((p+1)+"");
                            }else {
                                holder.hvb.number.setText("");
                            }
                        }

                        @Override
                        public int getItemCount() {
                            return iDiscCreateList.get(position).getPortNumber();
                        }
                    });
                }else {
                    holder.hvb.slotName.setText("");
                    holder.hvb.portRecycler.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public int getItemCount() {
                return iFiberFaceCreate.maxSlotCount();
            }
        });

        if(iFiberFaceCreate.haveSplitter()){
            fvb.splitterRecycler.setVisibility(View.VISIBLE);
            setSplitterList();
        }else {
            fvb.splitterRecycler.setVisibility(View.GONE);
        }
    }

    private void setSplitterList(){
        fvb.splitterRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        fvb.splitterRecycler.setAdapter(new RecyclerView.Adapter<SplitterHolder>() {
            @NonNull
            @Override
            public SplitterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return  new SplitterHolder(ItemSplitterBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
            }

            @Override
            public void onBindViewHolder(@NonNull SplitterHolder holder, int position) {
                IPortCreate inportCreate = iFiberFaceCreate.getISplitterCreate().get(position).getInPort();
                switch (inportCreate.getPortState()){
                    case USED:{
                        holder.hvb.state.setImageResource(R.drawable.sc_port_inside_used);
                        break;
                    }
                    case EMPTY:{
                        holder.hvb.state.setImageResource(R.drawable.sc_port_inside_empty);
                        break;
                    }
                    case BROKEN:{
                        holder.hvb.state.setImageResource(R.drawable.sc_port_inside_broken);
                        break;
                    }
                }
                if(inportCreate.showNumber()){
                    holder.hvb.number.setText("0");
                }else {
                    holder.hvb.number.setText("");
                }
                int rate = iFiberFaceCreate.getISplitterCreate().get(position).getRate();
                if(rate==iFiberFaceCreate.getISplitterCreate().get(position).getIPortCreate().size()){
                    GridLayoutManager glm = new GridLayoutManager(getContext(),rate/8+(rate%8>0?1:0));
                    glm.setOrientation(RecyclerView.VERTICAL);
                    holder.hvb.splitterPortRecycler.setLayoutManager(glm);
                    holder.hvb.splitterPortRecycler.setAdapter(new RecyclerView.Adapter<SplitterPortHolder>() {
                        @NonNull
                        @Override
                        public SplitterPortHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return new SplitterPortHolder(ItemSplitterPortBinding.inflate(LayoutInflater.from(parent.getContext())));
                        }

                        @Override
                        public void onBindViewHolder(@NonNull SplitterPortHolder h, int p) {
                            switch (iFiberFaceCreate.getISplitterCreate().get(position).getIPortCreate().get(p).getPortState()){
                                case USED:{
                                    h.hvb.state.setImageResource(R.drawable.sc_port_inside_used);
                                    break;
                                }
                                case EMPTY:{
                                    h.hvb.state.setImageResource(R.drawable.sc_port_inside_empty);
                                    break;
                                }
                                case BROKEN:{
                                    h.hvb.state.setImageResource(R.drawable.sc_port_inside_broken);
                                    break;
                                }
                            }
                            if(iFiberFaceCreate.getISplitterCreate().get(position).getIPortCreate().get(p).showNumber()){
                                h.hvb.number.setText((p+1)+"");
                            }else {
                                h.hvb.number.setText("");
                            }
                        }

                        @Override
                        public int getItemCount() {
                            return rate;
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return iFiberFaceCreate.getISplitterCreate().size();
            }
        });
    }

}
