package com.wewin.speeddev.demo;

import com.makeid.dumb_resource.EPortState;
import com.makeid.dumb_resource.EPortType;
import com.makeid.dumb_resource.IDiscCreate;
import com.makeid.dumb_resource.IFiberCreate;
import com.makeid.dumb_resource.IFiberFaceCreate;
import com.makeid.dumb_resource.IPortCreate;
import com.makeid.dumb_resource.ISplitterCreate;
import com.wewin.speeddev.databinding.ActivityTestDemoBinding;

import java.util.ArrayList;
import java.util.List;

import me.tx.app.common.base.CommonActivity;

/**
 * @author tx
 * @date 2022/12/23 16:16
 */
public class TestDemoActivity extends CommonActivity<ActivityTestDemoBinding> {
    @Override
    public void setView() {
        List<IFiberFaceCreate> faceList = new ArrayList<>();
        faceList.add(new IFiberFaceCreate() {
            @Override
            public String getFaceName() {
                return "A面";
            }

            @Override
            public String getNfcCode() {
                return "ABCDEFG";
            }

            @Override
            public String getQRCode() {
                return "ABCDEFG";
            }

            @Override
            public String getLockCode() {
                return "ABCDEFG";
            }

            @Override
            public boolean haveLock() {
                return true;
            }

            @Override
            public boolean haveNfc() {
                return true;
            }

            @Override
            public boolean haveQRCode() {
                return true;
            }

            @Override
            public void onNfcClick() {

            }

            @Override
            public void onQRClick() {

            }

            @Override
            public void onLockClick() {

            }

            @Override
            public int maxSlotCount() {
                return 30;
            }

            @Override
            public List<IDiscCreate> getIDiscCreate() {
                List<IDiscCreate> discList = new ArrayList<>();
                for(int i=0;i<30;i++) {
                    if(i>5&&i<18) {
                        discList.add(null);
                    }else {
                        int finalI = i;
                        discList.add(new IDiscCreate() {
                            @Override
                            public String getDiscName() {
                                return "标"+(finalI +1);
                            }

                            @Override
                            public int getPortNumber() {
                                return 24;
                            }

                            @Override
                            public int getNumberEveryLine() {
                                return 12;
                            }

                            @Override
                            public EPortType getPortType() {
                                return EPortType.SC;
                            }

                            @Override
                            public List<IPortCreate> getIPortCreate() {
                                List<IPortCreate> portList = new ArrayList<>();
                                for(int i = 0;i<24;i++){
                                    if(i<18) {
                                        portList.add(new IPortCreate() {
                                            @Override
                                            public EPortState getPortState() {
                                                return EPortState.USED;
                                            }

                                            @Override
                                            public boolean showNumber() {
                                                return true;
                                            }
                                        });
                                    }else {
                                        portList.add(new IPortCreate() {
                                            @Override
                                            public EPortState getPortState() {
                                                return EPortState.EMPTY;
                                            }

                                            @Override
                                            public boolean showNumber() {
                                                return true;
                                            }
                                        });
                                    }
                                }
                                return portList;
                            }
                        });
                    }
                }
                return discList;
            }

            @Override
            public boolean haveSplitter() {
                return false;
            }

            @Override
            public List<ISplitterCreate> getISplitterCreate() {
                return null;
            }
        });

        faceList.add(new IFiberFaceCreate() {
            @Override
            public String getFaceName() {
                return "B面";
            }

            @Override
            public String getNfcCode() {
                return null;
            }

            @Override
            public String getQRCode() {
                return null;
            }

            @Override
            public String getLockCode() {
                return null;
            }

            @Override
            public boolean haveLock() {
                return false;
            }

            @Override
            public boolean haveNfc() {
                return false;
            }

            @Override
            public boolean haveQRCode() {
                return false;
            }

            @Override
            public void onNfcClick() {

            }

            @Override
            public void onQRClick() {

            }

            @Override
            public void onLockClick() {

            }

            @Override
            public int maxSlotCount() {
                return 30;
            }

            @Override
            public List<IDiscCreate> getIDiscCreate() {
                List<IDiscCreate> discList = new ArrayList<>();
                for(int i=0;i<30;i++) {
                    if(i>2&&i<9) {
                        discList.add(null);
                    }else {
                        int finalI = i;
                        discList.add(new IDiscCreate() {
                            @Override
                            public String getDiscName() {
                                return "标"+(finalI +1);
                            }

                            @Override
                            public int getPortNumber() {
                                return 12;
                            }

                            @Override
                            public int getNumberEveryLine() {
                                return 12;
                            }

                            @Override
                            public EPortType getPortType() {
                                return EPortType.FC;
                            }

                            @Override
                            public List<IPortCreate> getIPortCreate() {
                                List<IPortCreate> portList = new ArrayList<>();
                                for(int i = 0;i<12;i++){
                                    if(i<8) {
                                        portList.add(new IPortCreate() {
                                            @Override
                                            public EPortState getPortState() {
                                                return EPortState.EMPTY;
                                            }

                                            @Override
                                            public boolean showNumber() {
                                                return true;
                                            }
                                        });
                                    }else {
                                        portList.add(new IPortCreate() {
                                            @Override
                                            public EPortState getPortState() {
                                                return EPortState.USED;
                                            }

                                            @Override
                                            public boolean showNumber() {
                                                return true;
                                            }
                                        });
                                    }
                                }
                                return portList;
                            }
                        });
                    }
                }
                return discList;
            }

            @Override
            public boolean haveSplitter() {
                return true;
            }

            @Override
            public List<ISplitterCreate> getISplitterCreate() {
                List<ISplitterCreate> sList = new ArrayList<>();
                for(int i =0;i<12;i++) {
                    sList.add(new ISplitterCreate() {
                        @Override
                        public int getRate() {
                            return 8;
                        }

                        @Override
                        public IPortCreate getInPort() {
                            return new IPortCreate() {
                                @Override
                                public EPortState getPortState() {
                                    return EPortState.USED;
                                }

                                @Override
                                public boolean showNumber() {
                                    return true;
                                }
                            };
                        }

                        @Override
                        public List<IPortCreate> getIPortCreate() {
                            List<IPortCreate> portList = new ArrayList<>();
                            for(int i =0;i<8;i++){
                                if(i<4) {
                                    portList.add(new IPortCreate() {
                                        @Override
                                        public EPortState getPortState() {
                                            return EPortState.USED;
                                        }

                                        @Override
                                        public boolean showNumber() {
                                            return true;
                                        }
                                    });
                                }else {
                                    portList.add(new IPortCreate() {
                                        @Override
                                        public EPortState getPortState() {
                                            return EPortState.EMPTY;
                                        }

                                        @Override
                                        public boolean showNumber() {
                                            return true;
                                        }
                                    });
                                }
                            }
                            return portList;
                        }
                    });
                }
                return sList;
            }
        });

        IFiberCreate iFiberCreate = new IFiberCreate() {
            @Override
            public String getFiberName() {
                return "测试光交箱";
            }

            @Override
            public String getFiberAddress() {
                return null;
            }

            @Override
            public float getLon() {
                return 0;
            }

            @Override
            public float getLat() {
                return 0;
            }

            @Override
            public String getConnectVillage() {
                return null;
            }
        };

        vb.fiber.createFiberView(2, getSupportFragmentManager(),iFiberCreate,faceList);

        vb.actionbar.init(this,iFiberCreate.getFiberName());
    }

    @Override
    public ActivityTestDemoBinding getVb() {
        return ActivityTestDemoBinding.inflate(getLayoutInflater());
    }
}
