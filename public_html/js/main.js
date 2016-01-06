require.config({
    urlArgs: "_=" + (new Date()).getTime(),
    baseUrl: "js",
    waitSeconds: 200,
    paths: {
        backbone: "lib/backbone",
        jquery: "lib/jquery",
        underscore: "lib/underscore"
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            exports: 'Backbone'
        },
    }
});

define([
            "underscore",
            'backbone',
            'router'
        ], function(
                underscore,
                Backbone,
                Router
        ){        
        
        Backbone.history.start();

});

