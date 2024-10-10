<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2024-10-10
  Time: 오후 2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<link
        rel="stylesheet"
        href="https://unpkg.com/jodit@4.0.1/es2021/jodit.min.css"
/>
<script src="https://unpkg.com/jodit@4.0.1/es2021/jodit.min.js"></script>
<form action="/posts" method="post" enctype="multipart/form-data">
    <textarea id="editor" name="content"></textarea>
    <button type="submit">Save</button>
</form>
<script>
    const editor = new Jodit("#editor", {
        uploader: {
            url: '/upload', // 파일 업로드 URL
            format: ["jpg", "jpeg", "png", "gif", "pdf", "doc", "docx"],
            // 응답 형식 설정
            responseType: 'json',
            isSuccess: function (resp) {
                return !resp.error;
            },
            getMessage: function (resp) {
                return resp.msg;
            },
            process: function (resp) {
                return {
                    files: resp.files || [],
                    path: resp.path,
                    baseurl: resp.baseurl,
                    error: resp.error,
                    msg: resp.msg
                };
            },
            defaultHandlerSuccess: function (data, resp) {
                var i,
                    field = 'files';
                if (data[field] && data[field].length) {
                    for (i = 0; i < data[field].length; i += 1) {
                        this.s.insertImage(data.baseurl + data[field][i]);
                    }
                }
            },
            error: function (e) {
                this.message.message(e.getMessage(), 'error', 4000);
            }
        }
    });
</script>

</body>
</html>
