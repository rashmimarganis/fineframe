var image_; 
function displayCaptcha (){
	var catptcha=Ext.get("captcha");
	Ext.getDom("captcha").innerHTML='<img src="captcha.jpg?'+Math.random()+'">';
	//var j_captcha=document.getElementById("j_captcha_response");
	//document.getElementById("captcha").style.top=j_captcha.style.offsetTop+'px';
	//document.getElementById("captcha").style.left=j_captcha.style.offsetLeft+'px';
	catptcha.show();
}
function hideCaptcha(){
	var catptcha=Ext.get("captcha");
	catptcha.hide();
}
Ext.onReady(function() {
    var form_ = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelWidth: 55,
        formId:'form1',
        url:'j_security_check',
        defaultType: 'textfield',
        standardSubmit: true,
        items: [{
            fieldLabel: '帐&nbsp;&nbsp;户',
            name: 'j_username',
            maxLength:16,
            anchor:'90%'  // anchor width by percentage
        },{
            fieldLabel: '密&nbsp;&nbsp;码',
            name: 'j_password',
            maxLength:16,
            labelSeparator: ':',
            inputType:'password',
            anchor: '90%'  // anchor width by percentage
        }, {
        	fieldLabel: '验证码',
        	id:'j_captcha_response',
            name: 'j_captcha_response',
            maxLength:5,
            anchor: '45%'
        }]
    });
   

    var window = new Ext.Window({
        title: 'FineCMS系统登录窗口',
        width: 300,
        height:160,
        minWidth: 300,
        minHeight: 140,
        layout: 'fit',
        plain:true,
        closable:false,
        resizable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items:form_,
        buttons: [{
            text: '登录',
            handler:function(){
        		document.getElementById("form1").action="j_security_check";
        		form_.getForm().submit({url:'j_security_check'});
        	}
        },{
            text: '重置'
        }]
    });
    window.show();
    image_=Ext.get('j_captcha_response');
    
    image_.on('focus',function(o){
    	//displayCaptcha();
    	setTimeout("displayCaptcha();",100);
    });
    image_.on('blur',function(o){
    	setTimeout("hideCaptcha();",100);
    });
    
});