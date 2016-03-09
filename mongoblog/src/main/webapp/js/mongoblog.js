function cleanDom( id )
{
    var selectObj = document.getElementById(id);
    var selectParentNode = selectObj.parentNode;
    var newSelectObj = selectObj.cloneNode(false);
    selectParentNode.replaceChild(newSelectObj, selectObj);
    return newSelectObj;
}

/**
 * Function that make a post call to a service using paramArray and url.
 * When the call is completed it invokes the callback fuction whith two parameters
 * a boolean value indicating if it end up ok and the HttpServletRequest
 * @param {type} url
 * @param {type} paramArray
 * @param {type} callback
 * @returns {undefined}
 */
function post( url, paramArray, callback, async )
{
    var xhr = new XMLHttpRequest();
    var params = "";

    for ( var i = 0; paramArray && i < paramArray.length; i = i + 2 )
    {
        if ( i > 0 )
        {
            params+="&";
        }
        params += paramArray[i] + "=" + encodeURIComponent( paramArray[i+1] );
    }
    xhr.open('post', url, arguments.length == 3 ? async : true  );
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.setRequestHeader("Content-length", params.length);
    xhr.setRequestHeader("Connection", "close");

    // Track the state changes of the request.
    xhr.onreadystatechange = function () 
    {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) 
        {
            callback( xhr.status === OK, xhr );
        }
    };
    xhr.send( params );
}

/**
 * Makes a get ajax call.
 * @param {type} url
 * @param {type} paramArray
 * @param {type} callback
 * @param {type} async
 * @returns {undefined}
 */
function get( url, paramArray, callback, async )
{
    var xhr = new XMLHttpRequest();
    var params = "";

    for ( var i = 0; paramArray && i < paramArray.length; i = i + 2 )
    {
        if ( i > 0 )
        {
            params+="&";
        }
        params += paramArray[i] + "=" + encodeURIComponent( paramArray[i+1] );
    }
    xhr.open('GET', url + "?" + params, arguments.length == 3 ? async : true  );

    // Track the state changes of the request.
    xhr.onreadystatechange = function () 
    {
        var DONE = 4;
        var OK = 200;
        if (xhr.readyState === DONE) 
        {
            callback( xhr.status === OK, xhr );
        }
    };
    xhr.send( params );
}