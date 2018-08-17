$(function () {
    var dateArray = [] // 假设已经签到的
    var signFun = function () {
        $.ajax({
            type: "POST",
            url: "/wx/user/complated",
            success: function (data) {
                for (var i = 0; i < data.atd_complated.length; i++) {
                    var timestamp = data.atd_complated[i];
                    var nowDate = new Date();
                    var newDate = new Date();  //实例化一个Date对象
                    newDate.setTime(timestamp); //设置Date对象的时间为时间戳的时间
                    if (newDate.getMonth() === nowDate.getMonth()) {
                        dateArray.push(newDate.getDate());
                    }

                }
                for (var j = 0; j < dateArray.length; j++) {
                    if (dateArray[j] != null) {
                        $dateLi.eq(dateArray[j] - 1 + monthFirst).addClass("qiandao");
                    }
                }
            }
        })
        var $dateBox = $("#qiandao-list"),
            $currentDate = $(".current-date"),
            $qiandaoBnt = $("#just-qiandao"),
            _html = '',
            _handle = true,
            myDate = new Date();
        $currentDate.text(myDate.getFullYear() + '年' + parseInt(myDate.getMonth() + 1) + '月' + myDate.getDate() + '日');

        var monthFirst = new Date(myDate.getFullYear(), parseInt(myDate.getMonth()), 1).getDay();

        var d = new Date(myDate.getFullYear(), parseInt(myDate.getMonth() + 1), 0);
        var totalDay = d.getDate(); //获取当前月的天数

        for (var i = 0; i < 42; i++) {
            _html += ' <li><div class="qiandao-icon"></div></li>'
        }
        $dateBox.html(_html) //生成日历网格

        var $dateLi = $dateBox.find("li");
        for (var i = 0; i < totalDay; i++) {
            $dateLi.eq(i + monthFirst).addClass("date" + parseInt(i + 1));

        } //生成当月的日历且含已签到

        $(".date" + myDate.getDate()).addClass('able-qiandao');

        $dateBox.on("click", "li", function () {
            if ($(this).hasClass('able-qiandao') && _handle) {
                $(this).addClass('qiandao');
                qiandaoFun();
            }
        }) //签到

        $qiandaoBnt.on("click", function () {
            if (_handle) {
                qiandaoFun();
            }
        }); //签到

        function qiandaoFun() {
            $qiandaoBnt.addClass('actived');
            openLayer("qiandao-active", qianDao);
            _handle = false;
        }

        function qianDao() {
            $(".date" + myDate.getDate()).addClass('qiandao');
        }
    }();

    function openLayer(a, Fun) {
        $('.' + a).fadeIn(Fun)
    } //打开弹窗

    var closeLayer = function () {
        $("body").on("click", ".close-qiandao-layer", function () {
            $(this).parents(".qiandao-layer").fadeOut()
        })
    }() //关闭弹窗

    $("#qiandao-history").on("click", function () {
        openLayer("qiandao-history-layer", myFun);

        function myFun() {
            console.log(1)
        } //打开弹窗返回函数
    })

})
