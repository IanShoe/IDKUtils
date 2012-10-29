/**
 * I don't remember exactly what this did but it was useful.
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