define([
    'backbone'
], function(
    Backbone
){

    var playerModel = Backbone.Model.extend({
        
        defaults: {
            name: "",
            chipColor: "",
            enemyChipColor: "",
            enemyName: "",
            isAuth: false,
            isMyTurn: false,
            roundWins: 0,
            gamesWon: 0,
            gamesPlayed: 0
        },
        
        initialize : function() {
            console.log("PlayerModel is created");
        }
        
    });
    
    return new playerModel();
});
