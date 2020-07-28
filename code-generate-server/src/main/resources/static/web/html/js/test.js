;!function(){
    console.log($)
    var table = layui.table;
    var BASEURL = 'http://127.0.0.1:3333/xmxr/'
    $.ajax({
        url:BASEURL+"getTableHead",
        type: "GET", //请求方式为get
        dataType: "json", //返回数据格式为json
        success:function (r) {
            console.log(r);
            //执行一个laypage实例

            table.render({
                elem: '#t'
                ,height: 312
                ,url: BASEURL+'user/getUsers' //数据接口
                ,parseData: function(res) { //res 即为原始返回的数据
                    console.log(res.data.length);
                    return {
                        "code": res.status, //解析接口状态
                        "msg": res.msg, //解析提示文本
                        "count": res.data.length, //解析数据长度
                        "data": res.data //解析数据列表
                    }
                }
                ,where:{pkId:null,id:null,name:null,pwd:null}
                ,cols: r.data
                ,id: '1'
                ,page:{
                    limit: 3
                    ,limits:[3,6,9]
                    ,groups:3
                }
            });
        }
    });
}();
