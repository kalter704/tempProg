define([
    'webService/webService'
], function(
    msgHandler
){
        var Socket =  { 
    
            socket : null,
            
            connect: function () {
                URL = "ws://" + location.host + "/gameplay";
                
                this.socket = new WebSocket(URL);
                
                console.log("ws is created on URL " + URL);
                
                this.socket.onopen = this.onopen;
                this.socket.onclose = this.onclose;
                this.socket.onmessage = this.onmessage;
                this.socket.error = this.error;
                
            },

            onopen : function() {
              console.log("Соединение установлено.");
            },

            onclose : function(event) {
              if (event.wasClean) {
                console.log('Соединение закрыто чисто');
              } else {
                console.log('Обрыв соединения'); 
              }
              console.log('Код: ' + event.code + ' причина: ' + event.reason);
            },

            onmessage : function(event) {   
                var data = JSON.parse(event.data);
                //console.log(event.data);         
                switch(data.status) {
                    case "wait":
                        console.log("wait");
                        msgHandler.onwait(data);
                        break;
                        
                    case "ready":
                        console.log("ready");
                        msgHandler.onready(data);
                        break;

                    case "run":
                        console.log("run");
                        msgHandler.onrun(data);
                        break;
                    
                    case "changeTurn":
                        console.log(data);
                        console.log("changeTurn");
                        msgHandler.onchangeTurn(data);
                        break;
                    
                    case "roundOver":
                        console.log("roundOver");
                        msgHandler.onroundOver(data);
                        break;
                    
                    case "gameOver":
                        console.log("gameOver");
                        msgHandler.ongameOver(data);
                        break;
                }
            },

            onerror : function(error) {
              alert("Ошибка " + error.message);
            },
            
            sendReadyMsg : function () {
                this.socket.send(JSON.stringify(msgHandler.READY));
            },
            
            sendCollumnChoosedMsg: function(collumnNum) {
                msgHandler.COLLUMN_CHOOSED.collumn = collumnNum;
                this.socket.send(JSON.stringify(msgHandler.COLLUMN_CHOOSED));
            },
            
            sendPlayAgainMsg : function(answer) {
                msgHandler.PLAY_AGAIN.answer = answer;
                this.socket.send(JSON.stringify(msgHandler.PLAY_AGAIN));
            },
            
            sendPlayMsg: function(playerName) {
                console.log("WebSocket send message - newGame");
                msgHandler.PLAY_GAME.creator = playerName;
                this.socket.send(JSON.stringify(msgHandler.PLAY_GAME));
            },
             
            sendJoinMsg: function(playerName) {
                console.log("WebSocket send message - joinGame");
                msgHandler.JOIN_GAME.roomHolder = playerName;
                this.socket.send(JSON.stringify(msgHandler.JOIN_GAME));
            }, 
            
            close: function() {
                this.socket.close();
            }
    }
    
    return Socket;

});
