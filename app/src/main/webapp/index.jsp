<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>搜索</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css"/>
</head>
<body>
<div>

    <div class="wrapper">
        <img src="${pageContext.request.contextPath}/img/logo.png" class="logo"/>

        <div class="search-area">
            <input placeholder="请在此处输入 股票 或 标签" class="search-input" type="text"/>
            <button id="search-btn">搜索</button>
        </div>
        <div id="tip-area">

        </div>
        <div id="result-area">

        </div>
    </div>
</div>

<script type="text/javascript">
    var contextPath = "${pageContext.request.contextPath}";
</script>

<script id="stock-item-tpl" type="text/x-handlebars-template">
    <ul>
        {{#each data}}
        <li>{{name}}</li>
        {{/each}}
    </ul>
</script>
<script id="stock-tpl" type="text/x-handlebars-template">
    <div class="tag-box stock-tag">
        <h1>"<strong class="key-tag"></strong>" 对应的 <i>标签</i></h1>
        <ol>
            {{#each stockTag}}
            <li><b>{{inc @index}}.</b><label>{{value}}</label><i>{{score}}</i></li>
            {{/each}}
        </ol>
    </div>
    <div class="tag-box tag-stock">
        <h1>"<strong class="key-tag"></strong>" 对应的 <i>个股</i></h1>
        <ol>
            {{#each tagStock}}
            <li><b>{{inc @index}}.</b><label>{{value}}</label><i>{{score}}</i></li>
            {{/each}}
        </ol>
    </div>
    <div class="tag-box tag-tag">
        <h1>"<strong class="key-tag"></strong>" 的相似 <i>标签</i></h1>
        <ol>
            {{#each tagTag}}
            <li><b>{{inc @index}}.</b><label>{{value}}</label><i>{{score}}</i></li>
            {{/each}}
        </ol>
    </div>
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars-v3.0.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/spin.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Util.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>
