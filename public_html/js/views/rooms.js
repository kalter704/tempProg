define([
    'backbone',
    'tmpl/rooms',
    'collections/rooms',
    'views/abstract/baseView',
    'models/player',
    'webService/webSocket'
], function(
    Backbone,
    tmpl,
    Rooms,
    baseView,
    player,
    webSocket
){

    var RoomsView = Backbone.View.extend({
        __proto__: baseView,
        events: {
            'show': 'show',
            'click .js_create_btn': 'createGame',
            'click .js_play_btn': 'playGame'
        },
        el: '.rooms',
        template: tmpl,
        model: Rooms,
        
        initialize: function () {
            console.log("RoomsView has been created");
            
            this.render();
            this.hide();
            
            this.listenTo(Rooms, "add", this.render);
            this.listenTo(Rooms, "remove", this.render);
        },
        
        show: function() {
            $.get("/game", function(response){
                Rooms.addRooms(response);   
            }, "json");
            
            if(webSocket.socket === null) {
                webSocket.connect();
                console.log("webSocket.connect()");
            }
            $(".rooms").show();
        },
        
        render: function () {
            this.$el.html(this.template(this.model.toJSON()));
        },

        createGame: function () {
            console.log("newGame");
            webSocket.sendPlayMsg(player.get("name"));            
        },
        
        playGame: function (event) {
            console.log("joinGame");
            var id = event.currentTarget.attributes.getNamedItem("id").value;
            var roomHolder = Rooms.models[id].get("holderName");
            webSocket.sendJoinMsg(roomHolder);
            $(location).attr('href', '#game');
        }
        

    });

    return new RoomsView();
});
