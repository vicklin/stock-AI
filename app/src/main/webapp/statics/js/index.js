/**
 * Created by vick on 15-6-16.
 */

Handlebars.registerHelper("inc", function (value) {
    return parseInt(value) + 1;
});

/**
 * loading util
 */
var spinner = new Spinner({
    lines: 9 // The number of lines to draw
    , length: 12 // The length of each line
    , width: 14 // The line thickness
    , radius: 26 // The radius of the inner circle
});

/**
 * 具体操作
 */
var opt = (function ($, handlebar, spin) {

    var url = contextPath + "/stockai";
    var container = "#result-area";

    function search(key) {
        var target = document.getElementById("result-area")
        spin.spin(target);
        $.getJSON(url, {key: key}, function (result) {
            spin.spin();
            var source = $("#stock-tpl").html();
            var template = handlebar.compile(source);
            $(container).html(template(result.data));
            $(".key-tag").text(key);
        });
    }

    function fuzzyTip(key) {
        $.getJSON(url + "/" + key, {}, function (result) {
            console.log(result);
            if (result.data.length > 0) {
                $(container).html('');
            }
            var source = $("#stock-item-tpl").html();
            var template = handlebar.compile(source);
            $("#tip-area").html(template(result));
        });
    }

    return {
        search: search,
        fuzzyTip: fuzzyTip
    };

})(jQuery, Handlebars, spinner);

/**
 * main
 */
$(function () {
    var $input = $(".search-input");
    var $btn = $("#search-btn");
    var tid = null;
    $input.focus().on("keyup", function (e) {
        var value = $input.val();
        if (!value) {
            return;
        }
        if (e.which == 13) {
            $("#search-btn").click();
        } else {
            if (tid) {
                clearTimeout(tid);
            }
            tid = setTimeout(function () {
                opt.fuzzyTip(value);
            }, 500);
        }
    });


    $btn.click(function () {
        if ("" == $input.val()) {
            $input.focus();
            return;
        }
        opt.search($input.val());
    });

    $("#result-area").on("click", ".tag-box li", function () {
        $input.val($(this).find("label").text());
        $btn.click();
    });

    $("#tip-area").on("click", "ul li", function () {
        $("#tip-area").html('');
        $input.val($(this).text());
        $btn.click();
    });
});

