define([
    'backbone',
    'models/cell'
], function(
    Backbone,
    Cell
){

    var Collection = Backbone.Collection.extend({
        model: Cell, 
        cellNum: null,
        initialize: function() {
            cellNum = 42;
            this.fill();  
        },
        
        fill : function() {
            for (var i = 0; i < cellNum; i++) {
                this.add({ cell:"undefined" });
            }
        },
        
        fillCell : function(collumnNum, chipColor) {
            this.models[collumnNum].set("cell", chipColor);
        }
    });
        
    return new Collection();
});
