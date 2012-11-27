/**
 * I don't remember exactly what this did but it was useful.
 * I think it would loop through a lists key/value pair, attempt to find the 
 * match with the instance provided, and set the variable to the one in the list.
 * A use case is a drop down where the instance points at value 3 and so it sets
 * the js drop down to value 3
 */
var setSourceProperty = function(instance, instanceList, instanceProperty) {
    if(instance) {
        instance = find(instance, instanceList, instanceProperty)
    }
    else {
        instance = instanceList[0];
    }
    return instance;
}

var find = function(instance, list, property) {
    var found = {};
    
    for (var i = 0; i < list.length; i++) {
        if (list[i][property] === instance[property] || list[i][property] === instance) {
            found = list[i];
            break;
        }
    }
    return found;
}