//mxResources
var mxResources = {
    resources: new Array(),
    add: function(basename, lan){
        lan = (lan != null) ? lan : mxClient.language;
        if (lan != 'none') {
            try {
                var req = mxUtils.load(basename + '.properties');
                if (req.isReady()) {
                    mxResources.parse(req.getText());
                }
            } 
            catch (e) {
            }
            try {
                var req = mxUtils.load(basename + '_' + lan + '.properties');
                if (req.isReady()) {
                    mxResources.parse(req.getText());
                }
            } 
            catch (e) {
            }
        }
    },
    parse: function(text){
        var lines = text.split('\n');
        for (var i = 0; i < lines.length; i++) {
            var index = lines[i].indexOf('=');
            if (index > 0) {
                var key = lines[i].substring(0, index);
                var idx = lines[i].length;
                if (lines[i].charCodeAt(idx - 1) == 13) {
                    idx--;
                }
                var value = lines[i].substring(index + 1, idx);
                mxResources.resources[key] = unescape(value);
            }
        }
    },
    get: function(key, params, defaultValue){
        var value = mxResources.resources[key];
        if (value == null) {
            value = defaultValue;
        }
        if (value != null && params != null) {
            var result = new Array();
            var index = null;
            for (var i = 0; i < value.length; i++) {
                var c = value.charAt(i);
                if (c == '{') {
                    index = '';
                }
                else 
                    if (index != null && c == '}') {
                        index = parseInt(index) - 1;
                        if (index >= 0 && index < params.length) {
                            result.push(params[index]);
                        }
                        index = null;
                    }
                    else 
                        if (index != null) {
                            index += c;
                        }
                        else {
                            result.push(c);
                        }
            }
            value = result.join('');
        }
        return value;
    }
};
//mxPoint
{
    function mxPoint(x, y){
        this.x = (x != null) ? x : 0;
        this.y = (y != null) ? y : 0;
    };
    mxPoint.prototype.x = null;
    mxPoint.prototype.y = null;
    mxPoint.prototype.equals = function(obj){
        return obj.x == this.x && obj.y == this.y;
    };
    mxPoint.prototype.clone = function(){
        return mxUtils.clone(this);
    };
}
//mxRectangle
{
    function mxRectangle(x, y, width, height){
        mxPoint.call(this, x, y);
        this.width = (width != null) ? width : 0;
        this.height = (height != null) ? height : 0;
    };
    mxRectangle.prototype = new mxPoint();
    mxRectangle.prototype.constructor = mxRectangle;
    mxRectangle.prototype.width = null;
    mxRectangle.prototype.height = null;
    mxRectangle.prototype.getCenterX = function(){
        return this.x + this.width / 2;
    };
    mxRectangle.prototype.getCenterY = function(){
        return this.y + this.height / 2;
    };
    mxRectangle.prototype.add = function(rect){
        if (rect != null) {
            var minX = Math.min(this.x, rect.x);
            var minY = Math.min(this.y, rect.y);
            var maxX = Math.max(this.x + this.width, rect.x + rect.width);
            var maxY = Math.max(this.y + this.height, rect.y + rect.height);
            this.x = minX;
            this.y = minY;
            this.width = maxX - minX;
            this.height = maxY - minY;
        }
    };
    mxRectangle.prototype.grow = function(amount){
        this.x -= amount;
        this.y -= amount;
        this.width += 2 * amount;
        this.height += 2 * amount;
    };
    mxRectangle.prototype.getPoint = function(){
        return new mxPoint(this.x, this.y);
    };
    mxRectangle.prototype.equals = function(obj){
        return obj.x == this.x && obj.y == this.y && obj.width == this.width && obj.height == this.height;
    };
}
//mxXmlRequest
{
    function mxXmlRequest(url, params, method, async, username, password){
        this.url = url;
        this.params = params;
        this.method = method || 'POST';
        this.async = (async != null) ? async : true;
        this.username = username;
        this.password = password;
    };
    mxXmlRequest.prototype.url = null;
    mxXmlRequest.prototype.params = null;
    mxXmlRequest.prototype.method = null;
    mxXmlRequest.prototype.async = null;
    mxXmlRequest.prototype.username = null;
    mxXmlRequest.prototype.password = null;
    mxXmlRequest.prototype.request = null;
    mxXmlRequest.prototype.isReady = function(){
        return this.request.readyState == 4;
    }
    mxXmlRequest.prototype.getDocumentElement = function(){
        var doc = this.getXml();
        if (doc != null) {
            return doc.documentElement;
        }
        return null;
    };
    mxXmlRequest.prototype.getXml = function(){
        var xml = this.request.responseXML;
        if (xml == null || xml.documentElement == null) {
            xml = mxUtils.parseXml(this.request.responseText);
        }
        return xml;
    };
    mxXmlRequest.prototype.getText = function(){
        return this.request.responseText;
    };
    mxXmlRequest.prototype.getStatus = function(){
        return this.request.status;
    };
    mxXmlRequest.prototype.create = function(){
        if (window.XMLHttpRequest) {
            return function(){
                return new XMLHttpRequest();
            };
        }
        else 
            if (typeof(ActiveXObject) != "undefined") {
                return function(){
                    return new ActiveXObject("Microsoft.XMLHTTP");
                };
            }
    }();
    mxXmlRequest.prototype.send = function(onload, onerror){
        this.request = this.create();
        if (this.request != null) {
            var self = this;
            this.request.onreadystatechange = function(){
                if (self.isReady()) {
                    if (onload != null) {
                        onload(self);
                    }
                }
            }
            this.request.open(this.method, this.url, this.async, this.username, this.password);
            this.setRequestHeaders(this.request, this.params);
            this.request.send(this.params);
        }
    };
    mxXmlRequest.prototype.setRequestHeaders = function(request, params){
        if (params != null) {
            request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        }
    };
    mxXmlRequest.prototype.simulate = function(doc, target){
        doc = doc || document;
        var old = null;
        if (doc == document) {
            old = window.onbeforeunload;
            window.onbeforeunload = null;
        }
        var form = doc.createElement('form');
        form.setAttribute('method', this.method);
        form.setAttribute('action', this.url);
        if (target != null) {
            form.setAttribute('target', target);
        }
        form.style.display = 'none';
        form.style.visibility = 'hidden';
        var pars = (this.params.indexOf('&') > 0) ? this.params.split('&') : this.params.split();
        for (var i = 0; i < pars.length; i++) {
            var pos = pars[i].indexOf('=');
            if (pos > 0) {
                var name = pars[i].substring(0, pos);
                var value = pars[i].substring(pos + 1);
                var textarea = doc.createElement('textarea');
                textarea.setAttribute('name', name);
                value = value.replace(/\n/g, '&#xa;');
                var content = doc.createTextNode(value);
                textarea.appendChild(content);
                form.appendChild(textarea);
            }
        }
        doc.body.appendChild(form);
        form.submit();
        doc.body.removeChild(form);
        if (old != null) {
            window.onbeforeunload = old;
        }
    };
}
//mxClipboard
var mxClipboard = {
    STEPSIZE: 10,
    insertCount: 1,
    cells: null,
    isEmpty: function(){
        return mxClipboard.cells == null;
    },
    cut: function(graph, cells){
        cells = mxClipboard.copy(graph, cells);
        mxClipboard.insertCount = 0;
        mxClipboard.removeCells(graph, cells);
        return cells;
    },
    removeCells: function(graph, cells){
        graph.removeCells(cells);
    },
    copy: function(graph, cells){
        cells = cells || graph.getSelectionCells();
        var result = graph.getExportableCells(cells);
        mxClipboard.insertCount = 1;
        mxClipboard.cells = graph.cloneCells(result);
        return result;
    },
    paste: function(graph){
        if (mxClipboard.cells != null) {
            var cells = graph.getImportableCells(mxClipboard.cells);
            var delta = mxClipboard.insertCount * mxClipboard.STEPSIZE;
            var parent = graph.getDefaultParent();
            cells = graph.importCells(cells, delta, delta, parent);
            mxClipboard.insertCount++;
            graph.setSelectionCells(cells);
        }
    }
};
