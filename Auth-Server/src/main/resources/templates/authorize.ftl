<html>
<head>
    <meta charset="utf-8">
    <title>Autorizierung | VS Labor Webshop mit Bootstrap 3 </title>
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
    </style>
</head>
<body>
<div class="container">
    <h2>Please Confirm</h2>

    <p>
        Do you authorize "${authorizationRequest.clientId}" at "${authorizationRequest.redirectUri}" to access your
        protected resources
        with scope ${authorizationRequest.scope?join(", ")}.
    </p>
    <form id="denyForm" name="confirmationForm"
          action="../oauth/authorize" method="post">
        <input name="user_oauth_approval" value="false" type="hidden"/>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-default" type="submit">Deny</button>
    </form>
    <form id="confirmationForm" name="confirmationForm"
          action="../oauth/authorize" method="post">
        <input name="user_oauth_approval" value="true" type="hidden"/>
        <input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Approve</button>
    </form>
</div>
</body>
</html>
