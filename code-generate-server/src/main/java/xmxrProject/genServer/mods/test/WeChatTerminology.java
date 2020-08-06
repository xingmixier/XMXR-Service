package xmxrProject.genServer.mods.test;

import xmxrProject.genServer.common.utils.C_DateUtil;

import java.util.*;

/**
 * @Aether 行米希尔
 * @Date 2020/7/8 11:14
 * @Version 1.0
 */
public class WeChatTerminology {

    private String syKey;
    private String wxsy;
    private String syValue;

    private  List<TagReplace> tags = new ArrayList<>();
    private  ValueHandle valueHandle ;
    private  ValueHandle defaultValueHandle ;
    private  Map<String ,?> map;


    private WeChatTerminology() {
    }

    public WeChatTerminology(String wxsy, Map<String ,?> map, ValueHandle valueHandle, TagReplace... tagReplaces) {
        init();
        initResource(valueHandle,tagReplaces);
        tags.sort((o1, o2) -> o2.getOrderNumber() - o1.getOrderNumber() );
        this.wxsy = wxsy;
        this.map =map;
    }

    public   String createWXSY() {
        List<WeChatTerminology> weChatTerminologies = getWeChatTerminologyList(wxsy,map);
        for(WeChatTerminology wct : weChatTerminologies){
            wxsy = wxsy.replace(wct.syKey,wct.syValue);
        }
        return wxsy;
    }

    private  void initResource(ValueHandle vh, TagReplace[] tagReplaces) {
        valueHandle = vh == null ? defaultValueHandle: vh;
        for(TagReplace tagReplace : tagReplaces){
            tags.add(tagReplace);
        }
    }


    private   List<WeChatTerminology> getWeChatTerminologyList(String wxsy, Map<String, ?> map) {
        List<WeChatTerminology> weChatTerminologies = new ArrayList<>();
        for(TagReplace or : tags){
            addKeyValue(or,wxsy,weChatTerminologies,map);
        }
        return weChatTerminologies;
    }

    private  void addKeyValue(TagReplace or, String wxsy, List<WeChatTerminology> weChatTerminologies, Map<String,?> map) {
        int start = -1;
        int end;
        WeChatTerminology wct;
        String beginTag = or.getBeginTag();
        String endTag = or.getEndTag();
        String key,relKey;
        Object value;
        while (  (start = wxsy.indexOf(beginTag,start+beginTag.length()) )  != -1){
            end = wxsy.indexOf(endTag,start);
            wct = new WeChatTerminology();
            key = wxsy.substring(start,end+endTag.length());
            relKey = wxsy.substring(start+beginTag.length(),end);
            value = map.get(relKey);
            value =  valueHandle.parseValue(key,value);
            wct.syKey = key;
            wct.syValue = (String) value;
            weChatTerminologies.add(wct);
        }
    }


    private  void init() {
        tags.add(new TagReplace("${","}",0){
            @Override
            String getTagValue(Object value) {
                return String.valueOf(value);
            }
        });
        defaultValueHandle = new ValueHandle() {
            @Override
            public String parseValue(String syKet, Object value) {
                return String.valueOf(value);
            }
        };
    }



    public static void main(String[] args) {
        Map<String ,Object > map = new HashMap<>();
        map.put("设备名","1");
        map.put("发生时间",new Date());
        map.put("告警历时",150);
        map.put("通知人","4");
        String wxsy =
                " ${通知人} 设备名：${设备名}，发生时间：${发生时间}，历时：${告警历时}    @${通知人}";
        TagReplace tr = new TagReplace("@${","}",3) {
            @Override
            String getTagValue(Object value) {
                return "@"+value;
            }
        };
        ValueHandle vh = new ValueHandle() {
            @Override
            public String parseValue(String syKet, Object value) {
                switch (syKet){
                    case "${告警历时}": int i = (int) value; return i/60 > 1?  i/60+"时 "+(i%60)+"分":(i%60)+"分";
                    case "${发生时间}": return C_DateUtil.formatDate((Date)value);
                    case "@${通知人}" : String retVal = ""; String str = (String) value ;
                    String strs [] = str.split("/");
                    for(String s :strs){
                        retVal += "@"+s;
                    }
                    return retVal;
                    default:return String.valueOf(value);
                }
            }
        };
        WeChatTerminology wct = new WeChatTerminology(wxsy,map,vh,tr);
        wxsy = wct.createWXSY();
        System.out.println(wxsy);
    }


}

abstract class TagReplace {

    private String beginTag;
    private String endTag;
    private int orderNumber;

    public TagReplace() {
    }

    public TagReplace(String beginTag, String endTag, int orderNumber) {
        this.beginTag = beginTag;
        this.endTag = endTag;
        this.orderNumber = orderNumber;
    }

    public String getBeginTag() {
        return beginTag;
    }

    public void setBeginTag(String beginTag) {
        this.beginTag = beginTag;
    }

    public String getEndTag() {
        return endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    abstract String getTagValue(Object value);
}

interface ValueHandle{
    String parseValue(String syKet ,Object value);
}

