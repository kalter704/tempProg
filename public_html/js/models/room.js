define([
    'backbone'
], function(
    Backbone
){

    var roomModel = Backbone.Model.extend({
        
        defaults: {
            holderName: "username",
            createDate: ""
        },
        
        initialize : function() {
            console.log("RoomModel is created");
        }
        
    });
    
    return roomModel;
});
