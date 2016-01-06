define([
    'backbone',
    'tmpl/signup',
    'views/abstract/baseView'
], function(
    Backbone,
    tmpl,
    baseView
){

    var signUpView = Backbone.View.extend({
        __proto__: baseView,
        el: '.signup',
        template: tmpl,
        events: {
            'click  .signup__js_log_in': 'login',
            'click  .signup__js_sign_up': 'signUp',
            'click  .signup__js_btn_close': 'signUpClose',
            'change .signup__password1': 'arePasswordsEqual',
            'keyup  .signup__password2': 'arePasswordsEqual',
            'change .signup__email': 'checkEmailInput',
            'change .signup__username': 'checkUsernameInput',
            'change .signup__password1': 'checkPasswordInput', 
            'show': 'show'
        },
        initialize: function () {
            console.log("SignupView has been created");
            this.render();
            this.hide();
        },
        render: function () {
            this.$el.html(this.template);
        },
        
        arePasswordsEqual : function() {
            if ($(".signup__password1").val() === $(".signup__password2").val()) {
                $(".form__passwords-different").hide();
                return true;
            } else {
                $(".form__passwords-different").show();
                return false;
            }
        },
        
        checkUsernameInput : function() {
            $(".form__username-exists").hide();   
            if ($(".signup__username").val() === "") {
                $(".form__username-invalid").show();
                return false;
            } else {
                $(".form__username-invalid").hide();
                return true;                
            }
        },
        
        checkEmailInput : function() {
            if ($(".signup__email").val() === "") {
                $(".form__email-invalid").show();
                return false;
            } else {
                $(".form__email-invalid").hide();
                return true;                
            }
        },
        
        checkPasswordInput : function() {
            if ($(".signup__password1").val() === "") {
                $(".form__password-invalid").show();
                return false;
            } else {
                $(".form__password-invalid").hide();
                return true;                
            }
        },
        login : function() {
            $(".signup_fixed").hide();
        }, 
        signUpClose : function() {
            $(".signup_fixed").hide();
        },
        
        signUp : function() {
            var username = $(".signup__username").val();
            if ( this.arePasswordsEqual() && this.checkUsernameInput() && this.checkEmailInput() && this.checkPasswordInput() ) {
                $.post($(".signup__form").attr("action"), $(".signup__form").serialize(), function(response) {
                console.log(response);
                  if(response.signup) {
                        $(".signup_fixed").show();
                        $(".welcome__name").text(username); 
                  } else {  
                        if(response.login === "exists") {
                            $(".form__username-exists").show();
                        } else if (!response.email) {
                            $(".form__email-invalid").show();
                        } else if (!response.password) {
                            $(".form__password-invalid").show();
                        }
                  }
                }, "json");               
            }

        }

    });

    return new signUpView();
});
