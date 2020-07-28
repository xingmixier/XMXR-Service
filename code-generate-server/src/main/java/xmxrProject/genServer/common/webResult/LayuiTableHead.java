package xmxrProject.genServer.common.webResult;

import xmxrProject.genServer.common.baseMod.BaseEntity;

/**
 * @FileName: LayuiTableHead.java
 * @Author: 行米希尔
 * @Date: 2020/7/14 18:10
 * @Description:
 */
public class LayuiTableHead extends BaseEntity<LayuiTableHead> {
    /***
     * 设定字段名。字段名的设定非常重要，且是表格数据列的唯一标识
     */
    private String field;
    /**
     * 设定标题名称
     * */
    private String title;
    /**
     * 设定列宽，若不填写，则自动分配；若填写，则支持值为：数字、百分比 请结合实际情况，对不同列做不同设定。
     */
    private String  width;//Number/String
    /**
     设定列类型。可选值有：
     normal（常规列，无需设定）
     checkbox（复选框列）
     radio（单选框列，layui 2.4.0 新增）
     numbers（序号列）
     space（空列）
     */
    private String type;
    /**
     * 是否全选状态（默认：false）。必须复选框列开启后才有效，如果设置 true，则表示复选框默认全部选中
     */
    private boolean LAY_CHECKED;
    /**
     * 固定列。可选值有：left（固定在左）、right（固定在右）。一旦设定，对应的列将会被固定在左或右，不随滚动条而滚动。
     * 注意：如果是固定在左，该列必须放在表头最前面；如果是固定在右，该列必须放在表头最后面。
     */
    private String fixed;
    /**
     * 是否初始隐藏列，默认：false
     */
    private boolean hide;
    /**
     * 是否开启该列的自动合计功能，默认：false。
     *
     * 当开启时，则默认由前端自动合计当前行数据。从 layui 2.5.6 开始： 若接口直接返回了合计行数据，则优先读取接口合计行数据，格式如下：
     *
     * codelayui.code
     * {
     *   "code": 0,
     *   "msg": "",
     *   "count": 1000,
     *   "data": [{}, {}]
     *   "totalRow": {
     *     "score": "666"
     *     ,"experience": "999"
     *   }
     * }
     *
     * 如上，在 totalRow 中返回所需统计的列字段名和值即可。
     * 另外，totalRow 字段同样可以通过 parseData 回调来解析成为 table 组件所规定的数据格式。
     */
    private Object totalRow; //boolean or object
    /**
     * 用于显示自定义的合计文本
     */
    private String totalRowText;
    /**
     *是否允许排序（默认：false）。如果设置 true，则在对应的表头显示排序icon，从而对列开启排序功能。
     * 注意：不推荐对值同时存在“数字和普通字符”的列开启排序，因为会进入字典序比对。比如：'贤心' > '2' > '100'，
     * 这可能并不是你想要的结果，但字典序排列算法（ASCII码比对）就是如此。
     */
    private boolean sort;
    /**
     * 是否禁用拖拽列宽（默认：false）。默认情况下会根据列类型（type）来决定是否禁用，
     * 如复选框列，会自动禁用。而其它普通列，默认允许拖拽列宽，当然你也可以设置 true 来禁用该功能。
     */
    private boolean unresize;
    /**
     * 单元格编辑类型（默认不开启）目前只支持：text（输入框）
     */
    private String edit;
    /**
     *自定义单元格点击事件名，以便在 tool 事件中完成对该单元格的业务处理
     */
    private String event;
    /**
     * 自定义单元格样式。即传入 CSS 样式
     */
    private String style;
    /**
     * 单元格排列方式。可选值有：left（默认）、center（居中）、right（居右）
     */
    private String align;
    /**
     * 单元格所占列数（默认：1）。一般用于多级表头
     */
    private int colspan;
    /**
     * 单元格所占行数（默认：1）。一般用于多级表头
     */
    private int rowspan;
    /**
     *自定义列模板，模板遵循 laytpl 语法。这是一个非常实用的功能，你可借助它实现逻辑处理，以及将原始数据转化成其它格式，如时间戳转化为日期字符等
     * @url https://www.layui.com/doc/modules/table.html#templet
     */
    private String templet;
    /**
     *绑定工具条模板。可在每行对应的列中出现一些自定义的操作性按钮
     * @url https://www.layui.com/doc/modules/table.html#templet
     */
    private String toolbar;


    public LayuiTableHead() {
    }

    /**
     * 构建简单的 layui 表头
     * @param field 属性名 对应 回调接口的 对象属性名
     * @param title 表头显示的标题
     * @param width 列宽
     * @param sort 是否排序
     */
    public LayuiTableHead(String field, String title, String width, boolean sort) {
        this.field = field;
        this.title = title;
        this.width = width;
        this.sort = sort;
    }

    /**
     * 构建 标准的 layui 表有
     * @param field 属性名 对应 回调接口的 对象属性名
     * @param title 表头显示的标题
     * @param width 列宽
     * @param type 列单元格类型
     * @param sort 是否排序
     * @param edit 单元格编辑类型
     * @param templet 自定义列表模板
     * @param toolbar 工具条 : 修改/查看/删除 等操作
     */
    public LayuiTableHead(String field, String title, String width, String type, boolean sort, String edit, String templet, String toolbar) {
        this.field = field;
        this.title = title;
        this.width = width;
        this.type = type;
        this.sort = sort;
        this.edit = edit;
        this.templet = templet;
        this.toolbar = toolbar;
    }



    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLAY_CHECKED() {
        return LAY_CHECKED;
    }

    public void setLAY_CHECKED(boolean LAY_CHECKED) {
        this.LAY_CHECKED = LAY_CHECKED;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public Object getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Object totalRow) {
        this.totalRow = totalRow;
    }

    public String getTotalRowText() {
        return totalRowText;
    }

    public void setTotalRowText(String totalRowText) {
        this.totalRowText = totalRowText;
    }

    public boolean isSort() {
        return sort;
    }

    public void setSort(boolean sort) {
        this.sort = sort;
    }

    public boolean isUnresize() {
        return unresize;
    }

    public void setUnresize(boolean unresize) {
        this.unresize = unresize;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public String getTemplet() {
        return templet;
    }

    public void setTemplet(String templet) {
        this.templet = templet;
    }

    public String getToolbar() {
        return toolbar;
    }

    public void setToolbar(String toolbar) {
        this.toolbar = toolbar;
    }
}
