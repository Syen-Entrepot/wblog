var emailRegex = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    var checknum = /^\d{6}$/;
    $(function() {
        $('#check_button').click(function () {
            var email = $('#email').val();
            if(email==''){
                alert('请输入邮箱!');
                var t =document.getElementById('check_button');
                t.removeAttribute("disabled");
                countdown = 0;
                t.value = "获取验证码";
            }else if(!emailRegex.test(email)){
                alert('请输入正确的邮箱格式!')
                var t =document.getElementById('check_button');
                t.removeAttribute("disabled");
                countdown = 0;
                t.value = "获取验证码";
            }else {
                // post请求
                $.ajax({
                    url: 'getCheckCode',
                    method:'post',
                    data:{"email":email},
                    success: function(data) {
                        console.log(data);
                    }
                });
            }

        });

        $('#c_submit').click(function () {
            var email = $('#email').val();
            var code = $('#check_code').val();
            if(email == ''){
                alert('请输入邮箱!');
            }else if(!emailRegex.test(email)){
                alert('请输入正确的邮箱格式!');
            }else if(code == ''){
                alert('请输入验证码!')
            }else if(!checknum.test(code)){
                alert('请输入6位数字的验证码!')
            } else {
                var b = document.getElementById('show_hide');
                b.className = " ";
                var b1 = document.getElementById('denglu');
                b1.className = " ";
                // post请求
                $.ajax({
                    url: 'CheckEmailCode',
                    method:'post',
                    data:{"code":code},
                    dataType:'html',
                    success: function(data) {
                        console.log(data);
                        $(document.body).html(data);
                    }
                });
            }

        });

        $('#show_hide').click(function () {
            var t = document.getElementById('m_quit');
            t.className = " ";
            document.getElementById('m_quit').classList.add('show');
        });

    })
    //    退出功能
    function show_confirm()
    {
        var r=confirm("Press a button!");
        if (r==true)
        {
            alert("You pressed OK!");
            $.ajax({
                url:'logout',
                method:'get',
                dataType:'html',
                success: function(data) {
                    console.log(data);
                    $(document.body).html(data);
                }
            });
        }
        else
        {
            alert("You pressed Cancel!");
            var t = document.getElementById('m_quit');
            t.className = " ";
            document.getElementById('m_quit').classList.add('hide');
        }
    }

    var countdown=60;
    function settime(obj) {
        if (countdown == 0) {
            obj.removeAttribute("disabled");
            obj.value="获取验证码";
            countdown = 60;
            return;
        } else {
            obj.setAttribute("disabled", true);
            obj.value="重新发送(" + countdown + ")";
            countdown--;
        }
        setTimeout(function() {
                settime(obj) }
            ,1000)
    }


    function Show() {
        document.getElementById('shade').classList.remove('hide');
        document.getElementById('modal').classList.remove('hide');
        document.getElementById('dowebok_login').classList.add('show');
        document.getElementById('dowebok_register').classList.add('hide');
    }

    function Hide() {
        document.getElementById('shade').classList.add('hide');
        document.getElementById('modal').classList.add('hide');
        var b = document.getElementById('dowebok_login');
        b.className = " ";
        var b1 = document.getElementById('dowebok_register');
        b1.className = " ";
        //重置时间
        var t =document.getElementById('check_button');
        t.removeAttribute("disabled");
        countdown = 0;
        t.value = "获取验证码";
    }

    function dowebok_login() {
        document.getElementById('dowebok_login').classList.add('show');
        document.getElementById('dowebok_register').classList.remove('hide');
        document.getElementById('dowebok_register').classList.add('hide');
        document.getElementById('dowebok_register').classList.remove('show');
    }

    function dowebok_register() {
        document.getElementById('dowebok_login').classList.remove('show');
        document.getElementById('dowebok_login').classList.add('hide');
        document.getElementById('dowebok_register').classList.remove('hide');
        document.getElementById('dowebok_register').classList.add('show');
    }