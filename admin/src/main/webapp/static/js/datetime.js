function ShowDate(id) {
    var temp;
    var datetime = new Date();
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var date = datetime.getDate();
    var day = datetime.getDay();                                                                          
    var hour = datetime.getHours();
    var minu = datetime.getMinutes();
    var seco = datetime.getSeconds();
    temp = year + "年" + month + "月" + date + "日 ";
    switch (day) {
        case 0:
            temp = temp + "星期日";
            break;
        case 1:
            temp = temp + "星期一";
            break;
        case 2:
            temp = temp + "星期二";
            break;
        case 3:
            temp = temp + "星期三";
            break;
        case 4:
            temp = temp + "星期四";
            break;
        case 5:
            temp = temp + "星期五";
            break;
        case 6:
            temp = temp + "星期六";
            break;
    }
    if (hour < 10)
        hour = "0" + hour;
    if (minu < 10)
        minu = "0" + minu;
    if (seco < 10)
        seco = "0" + seco;
    temp = temp + " " + hour + ":" + minu + ":" + seco + " ";
    if (hour >= 0 && hour < 5)
        temp = temp + "零晨好";
    if (hour >= 5 && hour < 8)
        temp = temp + "早上好";
    if (hour >= 8 && hour < 11)
        temp = temp + "上午好";
    if (hour >= 11 && hour < 13)
        temp = temp + "中午好";
    if (hour >= 13 && hour < 17)
        temp = temp + "下午好";
    if (hour >= 17 && hour < 24)
        temp = temp + "晚上好";
    $("#"+id).text(temp);
    window.setTimeout("ShowDate('"+id+" ')", 1000);   //这里Elements.name或Elements.id不是Elements
}

