<html>
<head>
    <meta charset="utf-8">
    <title>Login | VS Labor Webshop mit Bootstrap 3 </title>
    <link rel="stylesheet" type="text/css" href="http://localhost:8787/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="http://localhost:8787/bootstrap/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="http://localhost:8787/bootstrap/css/custom.css">
    <script type="text/javascript" src="http://localhost:8787/jquery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="http://localhost:8787/bootstrap/js/bootstrap.min.js"></script>
    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }
        #signinform {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<div class="container">
    <form role="form" action="login" id="signinform" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username"/>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password"/>
        </div>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
