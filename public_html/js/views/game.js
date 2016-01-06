define([
    'backbone',
    'tmpl/game',
    'collections/cells',
    'models/gameinfo',
    'models/player',
    'views/abstract/baseView',
    'webService/webSocket'
], function(
    Backbone,
    tmpl,
    gamefield,
    gameinfo,
    player,
    baseView,
    webSocket
){

    var GameView = Backbone.View.extend({
        __proto__: baseView,
        el: '.game',
        template: tmpl,
        model: gamefield,
        cell_index: null,
        animate: null,

        initialize: function () {
            console.log("GameView has been created");
            this.render();
            this.hide();
            this.blockGamefield();
            
            this.listenTo(player, "change:isMyTurn", this.changeTurn);
            this.listenTo(gameinfo, "change:status", this.changeStatus);
            this.listenTo(gamefield, "change", this.render);           
        },
        
        
        
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
        },
        
        getTurnMsg: function() {
            var msg_turn = "Turn: ";
            if(player.get("isMyTurn")) {
                msg_turn += player.get("name");
            } else {
                msg_turn += player.get("enemyName");
            }
            return msg_turn;
        },
        
        changeTurn: function () {
            if(gameinfo.get("status") === "ready") {
                return ;
            }
            if(player.get("isMyTurn")) {
                this.unblockGamefield();
            } else {
                this.blockGamefield();
            }
        
            $(".msg_turn").text(this.getTurnMsg());
            $(".gamemsg__turn").show(this.getTurnMsg());
            setTimeout(function(){$('.gamemsg__turn').fadeOut('fast')}, 2000);

            console.log("send-ready_msg");
            setTimeout(function(){webSocket.sendReadyMsg()}, 2100);
            
            this.printGameinfo();            
        },
        
        printGameinfo() {
            $(".msg__round").text(gameinfo.get("roundNum"));
            msg_turn = this.getTurnMsg();
            $(".msg__turn").text(msg_turn);
            $(".msg__username").text(player.get("name"));
            $(".msg__chip").text(player.get("chipColor"));
            $(".msg__time").text(gameinfo.get("turnTime"));
        },
        
        printGreeting: function() {
            $(".gamemsg__greeting").show();
        },
        
        printRoundOver: function() {
            msg = "";
            if(gameinfo.get("win")) {
                msg = "Won this round";
            } else {
                msg = "Lost this round";
            }
            $(".msg__roundover").text(msg);            
            $(".gamemsg__roundover").show();
            setTimeout(function(){$('.gamemsg__roundover').fadeOut('fast')}, 3000);
            setTimeout(this.changeTurn, 2100);   
        },
        
        printGameOver: function() {
            msg = "";
            if(gameinfo.get("win")) {
                msg = "VICTORY";
            } else {
                msg = "DEAFEAT";
            }
            $(".msg__gamewin").text(msg);
            $(".gamemsg__gameover").show();
        },
        
        changeStatus: function () {
            console.log("changeStatus");
            switch(gameinfo.get("status")) {
                case "wait":
                    this.blockGamefield();
                    break;
                    
                case "ready":
                    this.blockGamefield();
                    this.printGameinfo();
                    this.printGreeting();
                    break;

                case "run":
                    this.printGameinfo();
                    break;

                case "roundOver":
                    blockGamefield();
                    this.printRoundOver();                
                    break;

                case "gameOver":
                    this.printGameOver();
                    break;
            }
        },
        
        js_ready: function () {
            $(".gamemsg__greeting").hide();
            gameinfo.set("status", "startGame");
            this.changeTurn();
        },
        
        js_playAgain: function () {
            console.log("Play again");
            webSocket.sendPlayAgainMsg(true);
            $(".gamemsg__gameover").hide();
        },
        
        js_notPlayAgain: function () {
            console.log("Not play again");
            webSocket.sendPlayAgainMsg(false);        
            $(".gamemsg__gameover").hide();
        },
        
        dropAnimationStart: function (event) {
            this.chooseColumn(event);
        },
        
        chooseColumn : function(event) {
            column__id = event.currentTarget.attributes.getNamedItem("id").value;
            arrow_margin = 90 * (--column__id) + 40;
            $(".gamefield__arrow").css({ marginLeft: arrow_margin});
        },
        
        dropAnimationStop: function() {
        
        },
        
        dropChip: function (event) {
            column__id = event.currentTarget.attributes.getNamedItem("id").value;
            console.log("send column " + column__id + " to server");
            webSocket.sendCollumnChoosedMsg(column__id);
        },
        
//--------------------------------------------------------------------------
        
        blockGamefield: function () {
        
            this.undelegateEvents(this.events);
            events = {
                'click .js_change_status' : 'js_change_status',
                'click .js_add_cell' : 'js_add_cell',
                'click .js_change_turn': 'js_change_turn',
                'click .js_isReady_btn_yes': 'js_ready',
                'click .js_playAgain_btn_yes': 'js_playAgain',
                'click .js_playAgain_btn_nope': 'js_notPlayAgain'           
            }
            this.delegateEvents(events);
        },
        
        unblockGamefield: function () {
            events = {
                'click .gamefield__column': 'dropChip',
                'mouseenter .gamefield__column': 'dropAnimationStart',
                'mouseleave .gamefield__column': 'dropAnimationStop',
                'show': 'show',
                'click .js_change_status' : 'js_change_status',
                'click .js_change_turn': 'js_change_turn',
                'click .js_isReady_btn_yes': 'js_ready',
                'click .js_playAgain_btn_yes': 'js_playAgain',
                'click .js_playAgain_btn_nope': 'js_notPlayAgain'            
            }
            this.delegateEvents(events);
        }
//-------------------------------------------------------------------
        
    });

    return new GameView();
});
