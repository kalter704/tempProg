define([
    'backbone'
], function(
    Backbone
){

    var scoreModel = Backbone.Model.extend({
        
        defaults: {
            //Gamer name
            name: "",            
            //Number of person's wins
            score: 0
        },
        
        initialize : function() {
            console.log("ScoreModel is created");
        }
        
    });
    
    return scoreModel;
});
