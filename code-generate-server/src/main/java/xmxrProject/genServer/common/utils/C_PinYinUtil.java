package xmxrProject.genServer.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.List;

/**
 * @FileName: C_PinYinUtil.java
 * @Author: 行米希尔
 * @Date: 2020/8/19 15:13
 * @Description:
 */
public class C_PinYinUtil {

    /**
     * 汉语拼音转换对象
     */
    public static HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();

    /**
     * 全部小写
     */
    public static final HanyuPinyinCaseType LOWERCASE = HanyuPinyinCaseType.LOWERCASE;
    /**
     * 全部大写
     */
    public static final HanyuPinyinCaseType UPPERCASE = HanyuPinyinCaseType.UPPERCASE;


    /**
     * 正常书写的声调
     */
    public static final HanyuPinyinToneType WITH_TONE_MARK = HanyuPinyinToneType.WITH_TONE_MARK;
    /**
     * 不显示声调
     */
    public static final HanyuPinyinToneType WITHOUT_TONE = HanyuPinyinToneType.WITHOUT_TONE;
    /**
     * 用数字 代表声调
     */
    public static final HanyuPinyinToneType WITH_TONE_NUMBER = HanyuPinyinToneType.WITH_TONE_NUMBER;


    /**
     * 用 ü 代表 ü
     */
    public static final HanyuPinyinVCharType WITH_U_UNICODE = HanyuPinyinVCharType.WITH_U_UNICODE;
    /**
     * 用 ü 代表 ü
     */
    public static final HanyuPinyinVCharType WITH_U_AND_COLON = HanyuPinyinVCharType.WITH_U_AND_COLON;
    /**
     * 用 v 代表 ü
     */
    public static final HanyuPinyinVCharType WITH_V = HanyuPinyinVCharType.WITH_V;


    /**
     * 获取字符串的拼音
     *
     * @param str       字符串
     * @param caseType  大小写格式
     * @param toneType  音调格式
     * @param vCharType ü 格式
     * @param separate  分隔符
     * @param retain    是否保留特殊字符
     * @return 字符串拼音
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getPinYin(String str, HanyuPinyinCaseType caseType, HanyuPinyinToneType toneType, HanyuPinyinVCharType vCharType, String separate, boolean retain) throws BadHanyuPinyinOutputFormatCombination {
        hanyuPinyinOutputFormat.setCaseType(caseType);
        hanyuPinyinOutputFormat.setToneType(toneType);
        hanyuPinyinOutputFormat.setVCharType(vCharType);
        return PinyinHelper.toHanYuPinyinString(str + " ", hanyuPinyinOutputFormat, separate, retain).trim();
    }

    /**
     * 获取 所有文字首字母
     *
     * @param str       字符串
     * @param caseType  大小写格式
     * @param toneType  音调格式
     * @param vCharType ü 格式
     * @param separate  分隔符
     * @param retain    是否保留特殊字符
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getShouZiMu(String str, HanyuPinyinCaseType caseType, HanyuPinyinToneType toneType, HanyuPinyinVCharType vCharType, String separate, boolean retain) throws BadHanyuPinyinOutputFormatCombination {
        String pinYin = getPinYin(str, caseType, toneType, vCharType, separate, retain);
        String shouZiMu = "";
        for (String s : pinYin.split(separate)) {
            shouZiMu += s.charAt(0);
        }
        return shouZiMu.trim();
    }


    public static String getShouZiMu(String str) throws BadHanyuPinyinOutputFormatCombination {
        return getShouZiMu(str, LOWERCASE, WITH_V, false);
    }


    public static String getShouZiMu(String str, HanyuPinyinCaseType caseType, HanyuPinyinVCharType vCharType, boolean retain) throws BadHanyuPinyinOutputFormatCombination {
        return getShouZiMu(str, caseType, WITHOUT_TONE, WITH_V, "&XMXR;", false);
    }


    public static String getPinYin(String str) throws BadHanyuPinyinOutputFormatCombination {
        return getPinYin(str, " ", true);
    }

    public static String getPinYin(String str,String separate) throws BadHanyuPinyinOutputFormatCombination {
        return getPinYin(str, separate, true);
    }

    public static String getPinYinLTV(String str,String separate) throws BadHanyuPinyinOutputFormatCombination {
        return getPinYin(str, LOWERCASE, WITHOUT_TONE, WITH_V, separate, true);
    }

    public static String getPinYinLTVS(String str,String separate ) throws BadHanyuPinyinOutputFormatCombination {
        String pinYin = getPinYin(str, LOWERCASE, WITHOUT_TONE, WITH_V, separate, false);
        pinYin = pinYin.length()>separate.length() ? pinYin.substring(0,pinYin.lastIndexOf(separate)) : pinYin;
        return pinYin;
    }

    public static String getPinYinShouZiMuDaXie(String str ) throws BadHanyuPinyinOutputFormatCombination {
        String separate = "&XMXR";
        String pinYin = getPinYin(str, LOWERCASE, WITHOUT_TONE, WITH_V, separate, false);
        StringBuilder sb = new StringBuilder();
        Arrays.asList(pinYin.split(separate)).forEach(s->
                sb.append(s.substring(0,1).toUpperCase()+s.substring(1))
        );
        return sb.toString();
    }


    public static String getPinYin(String str, String separate, boolean retain) throws BadHanyuPinyinOutputFormatCombination {
        return getPinYin(str, LOWERCASE, WITH_TONE_MARK, WITH_U_UNICODE, separate, retain);
    }


    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        Arrays.asList(
          "是否需要执行下载12321原始数据",
                "是否需要一次筛选数据发邮件",
                "是否需要二次筛选数据上传经分",
                "是否需要下载经分数据",
                "是否需要筛选关停机数据",
                "是否需要执行下载话单信令邮件数据",
                "下载话单信令是否成功",
                "是否需要再次下载话单信令数据",
                "是否需要执行筛选举报侧数据",
                "是否需要执行筛选特殊数据",
                "是否需要执行筛选普通数据"
        ).forEach(o-> {
                    try {
                        System.out.println(getPinYinShouZiMuDaXie(o));
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                }
        );


    }


}//end
