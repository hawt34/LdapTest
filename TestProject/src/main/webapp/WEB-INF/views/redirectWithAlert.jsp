<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script>
        window.onload = function() {
            const alertMessage = "<%= request.getAttribute("alertMessage") %>";
            const redirectUrl = "<%= request.getAttribute("redirectUrl") %>";
            if (alertMessage) {
                alert(alertMessage);
            }
            if (redirectUrl) {
                window.location.href = redirectUrl;
            }
        }
    </script>
</head>
<body>
</body>
</html>
