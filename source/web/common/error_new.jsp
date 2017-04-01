<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" isErrorPage="true" %>
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
    <meta charset="utf-8"/>
    <title>Metronic | 404 Page Option 1</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <!-- <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet"
          type="text/css"/> -->
    <link href="../assets/global/plugins/googleapis.css" rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="../assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
          rel="stylesheet" type="text/css"/>
    <link href="../assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="../assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet"
          type="text/css"/>
    <link href="../assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"
          rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN THEME GLOBAL STYLES -->
    <link href="../assets/global/css/components.min.css" rel="stylesheet" id="style_components"
          type="text/css"/>
    <link href="../assets/global/css/plugins.min.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME GLOBAL STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link href="../assets/pages/css/error.min.css" rel="stylesheet" type="text/css"/>
    <!-- END PAGE LEVEL STYLES -->
    <!-- BEGIN THEME LAYOUT STYLES -->
    <link href="../assets/layouts/layout2/css/layout.min.css" rel="stylesheet" type="text/css"/>
    <%--<link href="../assets/layouts/layout2/css/themes/blue.min.css" rel="stylesheet" type="text/css" id="style_color" />--%>
    <link href="../assets/layouts/layout2/css/custom.min.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME LAYOUT STYLES -->
    <link rel="shortcut icon" href="favicon.ico"/>
</head>
<body class="page-header-fixed page-sidebar-closed-hide-logo page-container-bg-solid">
<div id="errorContentDivId" class="col-md-12 page-404">
    <div class="number font-green"> 404</div>
    <div class="details">
        <h3>Oops! You're lost.</h3>
        <p> We can not find the page you're looking for.
            <br>
            <a href="#/main"> Return home </a> or try the search bar below. </p>
        <%--<form action="#">--%>
            <div class="input-group input-medium">
                <input type="text" class="form-control" placeholder="keyword...">
                                        <span class="input-group-btn">
                                            <button type="submit" class="btn green" style="height: 34px">
                                                <i class="fa fa-search"></i>
                                            </button>
                                        </span>
            </div>
            <!-- /input-group -->
        <%--</form>--%>
    </div>
</div>
<script src="../assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
//    alert('ssss')
    $(window).resize(function(){
        $("#errorContentDivId").css({
            position: "absolute",
            left: ($(window).width() - $("#errorContentDivId").outerWidth())/2,
            top: ($(window).height() - $("#errorContentDivId").outerHeight())/4
        });
    });

    $(function(){
        $(window).resize();
    });
</script>
</body>

</html>