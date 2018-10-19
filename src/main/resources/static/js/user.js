
$('.userList .clickLi').click(function () {
    var flag = $(this).attr('class').substring(8);
    $('#personalDate,#basicSetting,#articleManagement,#articleCollection,#articleCategories,#leaveMessage,#privateWord').css("display","none");
    $("#" + flag).css("display","block");
});


// 失败消息盒
function dangerNotice(notice) {
    $('.dangerNotice').html(notice);
    $('.dangerNoticeAlert').css("display","block");
    var closeNoticeBox = setTimeout(function () {
        $('.dangerNoticeAlert').css("display","none");
    },3000);
}
// 成功消息盒
function successNotice(notice) {
    $('.successNotice').html(notice);
    $('.successNoticeAlert').css("display","block");
    var closeNoticeBox = setTimeout(function () {
        $('.successNoticeAlert').css("display","none");
    },3000);
}

//更改头像
function imgChange(e) {
    var dom =$("input[id^='imgTest']")[0];
    var reader = new FileReader();
    reader.onload = (function (file) {
        return function (e) {
            $.ajax({
                type:'POST',
                url:'/uploadHead',
                dataType:'json',
                data:{
                    img:this.result
                },
                success:function (data) {
                    if(data['status'] == 403){
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    } else {
                        if(data['status'] == 200){
                            $('#headPortrait').attr("src",data['avatarImgUrl']);
                            successNotice("更改头像成功");
                        } else {
                            $('#headPortrait').attr("src",data['avatarImgUrl']);
                            dangerNotice("更改头像失败")
                        }
                    }

                },
                error:function () {
                }
            });
        };
    })(e.target.files[0]);
    reader.readAsDataURL(e.target.files[0]);
}

//获得个人信息
function getUserPersonalInfo() {
    $.ajax({
        type:'get',
        url:'/getUserPersonalInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/toLogin",function(data,status,xhr){
                    window.location.replace("/login");
                });
            } else {
                $('#username').attr("value",data['result']['username']);
                var personalPhone = data['result']['phone'];
                $('#personalPhone').html(personalPhone.substring(0,3) + "****" + personalPhone.substring(7));
                $('#trueName').attr("value",data['result']['trueName']);
                $('#birthday').attr("value",data['result']['birthday']);
                var gender = data['result']['gender'];
                if(gender == "male"){
                    $('.genderTable input').eq(0).attr("checked","checked");
                } else {
                    $('.genderTable input').eq(1).attr("checked","checked");
                }
                $('#email').attr("value",data['result']['email']);
                $('#personalBrief').val(data['result']['personalBrief']);
                $('#headPortrait').attr("src",data['avatarImgUrl']);
            }
        },
        error:function () {
        }
    });
}

//获得头像url
function showHeadPortrait() {
    $.ajax({
        type:'get',
        url:'/getHeadPortraitUrl',
        dataType:'json',
        data:{
        },
        success:function (data) {
            $('#headPortrait').attr("src",data['avatarImgUrl']);
        },
        error:function () {
        }
    });
}

//保存个人资料
var savePersonalDateBtn = $('#savePersonalDateBtn');
var username = $('#username');
var trueName = $('#trueName');
var birthday = $('#birthday');
var gender = $('.genderTable input');
var email = $('#email');
var personalBrief = $('#personalBrief');
savePersonalDateBtn.click(function () {
    var usernameValue = username.val();
    var genderValue = "male";
    if(usernameValue.length === 0){
        dangerNotice("昵称不能为空");
    } else if(!gender[0].checked && !gender[1].checked){
        dangerNotice("性别不能为空");
    } else {
        if(gender[0].checked){
            genderValue = "male";
        } else {
            genderValue = "female";
        }
        $.ajax({
            type:'post',
            url:'/savePersonalDate',
            dataType:'json',
            data:{
                username:username.val(),
                trueName:trueName.val(),
                birthday:birthday.val(),
                gender:genderValue,
                email:email.val(),
                personalBrief:personalBrief.val()
            },
            success:function (data) {
                if(data['status'] == 403){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
                } else {
                    if(data['status'] == 200){
                        alert("更改个人信息成功,重新登录后生效");
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/logout");
                            // window.location.replace("/login");

                        });
                    } else if (data['status'] == 500){
                        dangerNotice("该昵称已被占用");
                    } else if (data['status'] == 201){
                        alert("更改个人信息成功");
                        successNotice("更改个人信息成功");
                    } else {
                        alert("更改个人信息失败");
                        dangerNotice("更改个人信息失败");
                    }
                }
            },
            error:function () {
            }
        });
    }
});

showHeadPortrait();
getUserPersonalInfo();

