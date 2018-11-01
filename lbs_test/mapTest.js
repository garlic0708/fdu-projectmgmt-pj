/**
 * Created by sunlu on 2018/10/27.
 */
window.onLoad  = function(){
    var map = new AMap.Map('container');
}
var url = 'https://webapi.amap.com/maps?v=1.4.10&key=19449cb659e47dda0acb7287cac81641&callback=onLoad';
var jsapi = document.createElement('script');
jsapi.charset = 'utf-8';
jsapi.src = url;
document.head.appendChild(jsapi);

AMap.plugin('AMap.Autocomplete', function(){
    // 实例化Autocomplete
    var autoOptions = {
        // input 为绑定输入提示功能的input的DOM ID
        input: 'input_id'
    }
    var autoComplete= new AMap.Autocomplete(autoOptions);
    // 无需再手动执行search方法，autoComplete会根据传入input对应的DOM动态触发search
})

var placeSearch = new AMap.PlaceSearch({
    // city 指定搜索所在城市，支持传入格式有：城市名、citycode和adcode
    city: '010'
})

placeSearch.search('北京大学', function (status, result) {
    // 查询成功时，result即对应匹配的POI信息
})
