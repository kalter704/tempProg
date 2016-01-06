define([
    'backbone',
    'tmpl/login',
    'views/abstract/baseView',
    'models/player'
], function(
    Backbone,
    tmpl,
    baseView,
    player
){

    var LoginView = Backbone.View.extend({
        __proto__: baseView,
        el: '.signin',
        template: tmpl,
        events: {
            'click .login__js_log_in': 'login',
            'click .login__js_btn_close': 'loginErrClose',    
        },
        initialize: function () {
            console.log("LoginView has been created");
            this.render();
            this.hide();
        },
        render: function () {
            this.$el.html(this.template)
        },
        loginErrClose: function() {
            $(".login_fixed").hide();  
        },
        login: function() {
            $.post($(".login__form").attr("action"), $(".login__form").serialize(), function(response) {
                if(response.auth) {;
                    player.set("isAuth", true);
                    player.set("name", response.name);
                    $(".js_btn_back").trigger("click");
                } else {
                    $(".login_fixed").show();
                    setTimeout(function(){$('.login_fixed').fadeOut('fast')}, 5000);  
                }
            }, "json");
        }

    });
    return new LoginView();
});
