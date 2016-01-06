define([
    'backbone'
], function(
    Backbone
){

    var cellModel = Backbone.Model.extend({
        
        defaults: {
            //cell may be blue, orange or undefined
            cell: "undefined", 
              
        },
        
        initialize : function() {
            console.log("CellModel is created");
        }
        
    });
    
    return cellModel;
});
