define([
    'backbone',
    'tmpl/scoreboard',
    'collections/scores',
    'views/abstract/baseView'
], function(
    Backbone,
    tmpl,
    Scores,
    baseView
){

    var ScoreboardView = Backbone.View.extend({
        __proto__: baseView,
        events: {
            'show': 'show'
        },
        el: '.scoreboard',
        template: tmpl,
        model: Scores,
        
        initialize: function () {
            console.log("ScoreboardView has been created");
            this.render();
            this.hide();
        },
        
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
        }

    });

    return new ScoreboardView();
});
