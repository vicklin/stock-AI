/**
 * Created by vick on 15-6-19.
 */
var Util = {}; // 定义工具类
/**
 * 扩展
 */
(function (ut) {
    /**
     * 高频执行方法的防抖
     * @method SC.util.debounce
     * @param  {function} func      需要高频执行的函数
     * @param  {string} wait      执行间隔时间
     * @param  {boolean} immediate 是否立刻执行
     */
    ut.debounce = function (func, wait, immediate) {
        var timeout;
        return function () {
            var context = this,
                args = arguments;
            var later = function () {
                timeout = null;
                if (!immediate) func.apply(context, args);
            };
            var callNow = immediate && !timeout;
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
            if (callNow) func.apply(context, args);
        };
    };


    ut.delay = function (func, wait) {
        var timeout;
        timeout = setTimeout(func, wait);

    };

    return ut;
})(Util);
