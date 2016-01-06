define([
    'backbone',
    'models/score'
], function(
    Backbone,
    Score
){

    var Collection = Backbone.Collection.extend({
        model: Score, 
        initialize: function() {
            this.add([
                {name:"Иван", score: 10},
                {name:"Павел", score: 13},
                {name:"Андрей", score: 2},
                {name:"Оля", score: 54},
                {name:"Николай", score: 11}
            ])
        },
        comparator: function(model) {
            return -model.get("score");
        }
        
    });
        
    return new Collection();
});
