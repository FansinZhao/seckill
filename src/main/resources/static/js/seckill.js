/**
 * Created by zhaofeng on 17-4-11.
 */
/*存放交互逻辑*/
var seckill={
    //秒杀相关的ajax操作
    URL:{

        now:function () {
            return "/seckill/time/now";
        },
        kill:function (seckillId) {
            return "/seckill/"+seckillId+"/exposer";
        },
        execution:function (seckillId,md5) {
            return "/seckill/"+seckillId+"/"+md5+"/execution";
        }
    },

    handleSeckill:function (seckillId,node) {
        //获取秒杀地址.执行秒杀
        //新建秒杀开始按钮(关闭状态)
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">秒杀开始</button>');
        //获取藐视地址
        $.post(seckill.URL.kill(seckillId),{},function (result) {
            if(result && result['success']){
                //判断是否开始
                var exposer = result['data'];
                var exposed = exposer['exposed'];
                if(exposed){
                    //执行秒杀
                    var md5 = exposer['md5'];

                    //秒杀按钮
                    $("#killBtn").one('click',function () {
                        //1 禁止按钮,预防重复
                        $(this).addClass('disabled');
                        //2 发送秒杀请求
                        $.post(seckill.URL.execution(seckillId,md5),{},function (result) {
                            if(result && result['success']){
                                //秒杀成功
                                var secKillResult = result['data'];
                                var state = secKillResult['state'];
                                var stateInfo = secKillResult['stateInfo'];
                                //显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>');
                            }else{
                                window.console.log("result=:"+result);
                            }
                        });
                    });
                    //显示按钮
                    node.show();
                }else {
                    //重新倒计时
                    var now = result['now'];
                    var start= result['start'];
                    var end= result['end'];
                    seckill.countdown(seckillId,now,start,end);
                }
            }else {
                window.console.log("result=:"+result);
            }
        })


    },
    countdown:function (seckillId,now,startTime,endTime) {

        var seckillBox = $("#seckill-box");
        if(now > endTime){
            //秒杀结束
            seckillBox.html("秒杀结束!");
        }else if(now < startTime){
            //秒杀计时
            var countTime = new Date(startTime+1000);//时间补偿
            seckillBox.countdown(countTime,function (event) {
                var formatTime  = event.strftime("秒杀倒计时:%D天 %H小时 %M分钟 %S秒");
                seckillBox.html(formatTime);//显示
            }).on('finish.countdown',function(){
                //时间完成回调函数
                //获取秒杀地址,执行秒杀
                seckill.handleSeckill(seckillId,seckillBox);
            });
        }else{
            //秒杀开始
            seckillBox.html("秒杀开始!");
            seckill.handleSeckill(seckillId,seckillBox);
        }

    },
    validatePhone:function (phone) {
        if(phone && phone.length == 11 && !isNaN(phone)){
            return true;
        }else{
            return false;
        }
    },
    //页面详情页
    detail:{
        //详情页初始化
        init:function (params) {
            //手机登录注册,计时
            //规划逻辑
            //cookie获取
            var killPhone = $.cookie('killPhone');
            
            if(!seckill.validatePhone(killPhone)){
                //绑定手机号
                var killPhoneModal = $("#killPhoneModal");
                killPhoneModal.modal({
                    show:true,//显示
                    backdrop:'static',//禁止位置关闭
                    keyboard:false//禁止键盘
                });
                //获取按钮事件
                $("#killPhoneBtn").click(function () {
                    var killPhoneKey = $("#killPhoneKey").val();
                    if(seckill.validatePhone(killPhoneKey)){
                        $.cookie("killPhone",killPhoneKey,{expires:7,path:'/seckill'});
                        window.location.reload();
                    }else {
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号不合法!</label>').show(300);
                    }
                });
            }
            
            //倒计时
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(),{},function (result) {
                if(result && result['success']){
                    //时间判断
                    var now = result['data'];
                    seckill.countdown(seckillId,now,startTime,endTime);
                }else{
                    //ie下需要添加window
                    window.console.log('result='+result);
                }
            });

        }
    }
}