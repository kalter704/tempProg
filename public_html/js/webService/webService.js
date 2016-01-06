define([
    'models/player',
    'models/gameinfo',
    'collections/cells'
], function(
    player,
    gameinfo,
    gamefield
){
    var msgHandler =  { 
    
        READY : {
            status: "ready"
        },
        
        COLLUMN_CHOOSED: {
            status: "collumnChoosed",
            collumn: null
        },
        
        PLAY_AGAIN: {
            status: "playAgain",
            answer: null             
        },
        
        PLAY_GAME: {
            status: "newGame",
            creator: ""
        },
        
        JOIN_GAME: {
            status: "joinGame",
            roomHolder: ""
        },
        
        onwait : function (data) {
            gameinfo.set("status", data.status);
        },
        
        onready : function (data) {
			console.log(data);
            player.set("enemyName", data.enemyName);
            player.set("name", data.myName);
            player.set("chipColor", data.chipColor);
            player.set("enemyChipColor", data.enemyChipColor);
            player.set("isMyTurn", data.isMyTurn);
            gameinfo.set("status", data.status);
        },

        onrun : function (data) {
            gameinfo.set("status", data.status);
            gameinfo.set("turnTime", data.turnTime);
        },

        onchangeTurn : function (data) {
            console.log(data)
            gameinfo.set("status", data.status);
            if (player.get("isMyTurn")) {
                gamefield.fillCell(data.cell, player.get("chipColor"));            
            } else {
                gamefield.fillCell(data.cell, player.get("enemyChipColor"));             
            }
            player.set("isMyTurn", data.isMyTurn); 
        },
        
        onroundOver : function (event) {
            gameinfo.set("status", event.status);
            gameinfo.set("win", event.win);
        },        

        ongameOver : function (event) {
            gameinfo.set("status", event.status);
            gameinfo.set("win", event.win);
        }
        
    }
    
    return msgHandler;

});
