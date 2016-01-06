define([
    'backbone',
    'views/ViewManager'
], function(
    Backbone,
    ViewManager
){

    var Router = Backbone.Router.extend({
        routes: {
            'rooms': 'roomsAction',
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'signup': 'signupAction',
            '*default': 'defaultActions'
        },
        
        navigateTo: function(url) {
            console.log("navigate to" + url);
            this.navigate(url, {trigger:true});
        },
        
        defaultActions: function () {
            ViewManager.show_current(ViewManager.MAIN);
        },
            
        scoreboardAction: function () {
            ViewManager.show_current(ViewManager.SCOREBOARD);
        },
        
        gameAction: function () {
            ViewManager.show_current(ViewManager.GAME);
        },
        
        loginAction: function () {
            ViewManager.show_current(ViewManager.LOGIN);
        },
        
        signupAction: function() {
            ViewManager.show_current(ViewManager.SIGNUP);
        },
        
        roomsAction: function() {
            ViewManager.show_current(ViewManager.ROOMS);
        }
        
        
    });

    return new Router();
});
