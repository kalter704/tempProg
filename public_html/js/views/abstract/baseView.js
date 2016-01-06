define([
    'backbone'
], function(
    Backbone
){

    var BaseView = Backbone.View.extend({

        initialize: function () {
            this.render();
            this.hide();
        },
        render: function () {
            this.$el.html(this.template);
        },
        show: function () {
            $(this.el).show();
            console.log("BaseView show event");
        },
        hide: function () {
            $(this.el).hide();
            console.log("BaseView hide event");
        },
    });

    return new BaseView();
});
