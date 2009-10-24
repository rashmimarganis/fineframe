//mxUtils
var mxUtils = {
    errorResource: (mxClient.language != 'none') ? 'error' : '',
    closeResource: (mxClient.language != 'none') ? 'close' : '',
    errorImage: mxClient.imageBasePath + 'error.gif',
    removeCursors: function(element){
        if (element.style != null) {
            element.style.cursor = '';
        }
        var children = element.childNodes;
        if (children != null) {
            var childCount = children.length;
            for (var i = 0; i < childCount; i += 1) {
                mxUtils.removeCursors(children[i]);
            }
        }
    },
    repaintGraph: function(graph, pt){
        var c = graph.container;
        if (c != null && pt != null && (false || false || false) && (c.scrollLeft > 0 || c.scrollTop > 0)) {
            var dummy = document.createElement('div');
            dummy.style.position = 'absolute';
            dummy.style.left = pt.x + 'px';
            dummy.style.top = pt.y + 'px';
            dummy.style.width = '1px';
            dummy.style.height = '1px';
            c.appendChild(dummy);
            c.removeChild(dummy);
        }
    },
    getCurrentStyle: function(){
        if (false) {
            return function(element){
                return (element != null) ? element.currentStyle : null;
            }
        }
        else {
            return function(element){
                return (element != null) ? window.getComputedStyle(element, '') : null;
            }
        }
    }(),
    hasScrollbars: function(node){
        var style = mxUtils.getCurrentStyle(node);
        return style != null && (style.overflow == 'scroll' || style.overflow == 'auto');
    },
    eval: function(expr){
        var result = null;
        if (expr.indexOf('function') >= 0 && (false || false || true || false)) {
            try {
                eval('var _mxJavaScriptExpression=' + expr);
                result = _mxJavaScriptExpression;
                delete _mxJavaScriptExpression;
            } 
            catch (e) {
                mxLog.warn(e.message + ' while evaluating ' + expr);
            }
        }
        else {
            result = eval(expr);
        }
        return result;
    },
    selectSingleNode: function(){
        if (false) {
            return function(doc, expr){
                return doc.selectSingleNode(expr);
            }
        }
        else {
            return function(doc, expr){
                var result = doc.evaluate(expr, doc, null, XPathResult.ANY_TYPE, null);
                return result.iterateNext();
            }
        }
    }(),
    getFunctionName: function(f){
        var str = null;
        if (f != null) {
            if (!false && true) {
                str = f.name;
            }
            else {
                var tmp = f.toString();
                var idx1 = 9;
                while (tmp.charAt(idx1) == ' ') {
                    idx1++;
                }
                var idx2 = tmp.indexOf('(', idx1);
                str = tmp.substring(idx1, idx2);
            }
        }
        return str;
    },
    indexOf: function(array, obj){
        if (array != null && obj != null) {
            for (var i = 0; i < array.length; i++) {
                if (array[i] == obj) {
                    return i;
                }
            }
        }
        return -1;
    },
    remove: function(obj, array){
        var result = null;
        if (typeof(array) == 'object') {
            var index = mxUtils.indexOf(array, obj);
            while (index >= 0) {
                array.splice(index, 1);
                result = obj;
                index = mxUtils.indexOf(array, obj);
            }
        }
        for (var key in array) {
            if (array[key] == obj) {
                delete array[key];
                result = obj;
            }
        }
        return result;
    },
    isNode: function(value, nodeName, attributeName, attributeValue){
        if (value != null && !isNaN(value.nodeType) && (nodeName == null || value.nodeName.toLowerCase() == nodeName.toLowerCase())) {
            return attributeName == null || value.getAttribute(attributeName) == attributeValue;
        }
        return false;
    },
    getChildNodes: function(node, nodeType){
        nodeType = nodeType || mxConstants.NODETYPE_ELEMENT;
        var children = new Array();
        var tmp = node.firstChild;
        while (tmp != null) {
            if (tmp.nodeType == nodeType) {
                children.push(tmp);
            }
            tmp = tmp.nextSibling;
        }
        return children;
    },
    createXmlDocument: function(){
        var doc = null;
        if (document.implementation && document.implementation.createDocument) {
            doc = document.implementation.createDocument('', '', null);
        }
        else 
            if (window.ActiveXObject) {
                doc = new ActiveXObject('Microsoft.XMLDOM');
            }
        return doc;
    },
    parseXml: function(xml){
        if (false) {
            return function(xml){
                var result = mxUtils.createXmlDocument();
                result.async = 'false';
                result.loadXML(xml)
                return result;
            }
        }
        else {
            return function(xml){
                var parser = new DOMParser();
                return parser.parseFromString(xml, 'text/xml');
            }
        }
    }(),
    createXmlElement: function(nodeName){
        return mxUtils.parseXml('<' + nodeName + '/>').documentElement;
    },
    getPrettyXml: function(node, tab, indent){
        var result = new Array();
        if (node != null) {
            tab = tab || '  ';
            indent = indent || '';
            if (node.nodeType == mxConstants.NODETYPE_TEXT) {
                result.push(node.nodeValue);
            }
            else {
                result.push(indent + '<' + node.nodeName);
                
                var attrs = node.attributes;
                if (attrs != null) {
                    for (var i = 0; i < attrs.length; i++) {
                        var val = mxUtils.htmlEntities(attrs[i].nodeValue);
                        result.push(' ' + attrs[i].nodeName + '="' + val + '"');
                    }
                }
                
                
                var tmp = node.firstChild;
                if (tmp != null) {
                    result.push('>\n');
                    while (tmp != null) {
                        result.push(mxUtils.getPrettyXml(tmp, tab, indent + tab));
                        tmp = tmp.nextSibling;
                    }
                    result.push(indent + '</' + node.nodeName + '>\n');
                }
                else {
                    result.push('/>\n');
                }
            }
        }
        return result.join('');
    },
    removeWhitespace: function(node, before){
        var tmp = (before) ? node.previousSibling : node.nextSibling;
        while (tmp != null && tmp.nodeType == mxConstants.NODETYPE_TEXT) {
            var next = (before) ? tmp.previousSibling : tmp.nextSibling;
            var text = mxUtils.getTextContent(tmp).replace(/\t/g, '').replace(/\r\n/g, '').replace(/\n/g, '').replace(/^\s+/g, '').replace(/\s+$/g, '');
            if (text.length == 0) {
                tmp.parentNode.removeChild(tmp);
            }
            tmp = next;
        }
    },
    htmlEntities: function(s, newline){
        s = s || '';
        s = s.replace(/&/g, '&amp;');
        s = s.replace(/"/g, '&quot;');
        s = s.replace(/\'/g, '&#39;');
        s = s.replace(/</g, '&lt;');
        s = s.replace(/>/g, '&gt;');
        if (newline == null || newline) {
            s = s.replace(/\n/g, '&#xa;');
        }
        return s;
    },
    isVml: function(node){
        return node != null && node.tagUrn == 'urn:schemas-microsoft-com:vml';
    },
    getXml: function(node, linefeed){
        var xml = '';
        if (node != null) {
            xml = node.xml;
            if (xml == null) {
                if (false) {
                    xml = node.innerHTML;
                }
                else {
                    var xmlSerializer = new XMLSerializer();
                    xml = xmlSerializer.serializeToString(node);
                }
            }
            else {
                xml = xml.replace(/\r\n\t[\t]*/g, '').replace(/>\r\n/g, '>').replace(/\r\n/g, '\n');
            }
        }
        linefeed = linefeed || '&#xa;';
        xml = xml.replace(/\n/g, linefeed);
        return xml;
    },
    getTextContent: function(node){
        var result = '';
        if (node != null) {
            if (node.firstChild != null) {
                node = node.firstChild;
            }
            result = node.nodeValue || '';
        }
        return result;
    },
    getInnerHtml: function(){
        if (false) {
            return function(node){
                if (node != null) {
                    return node.innerHTML;
                }
                return '';
            }
        }
        else {
            return function(node){
                if (node != null) {
                    var serializer = new XMLSerializer();
                    return serializer.serializeToString(node);
                }
                return '';
            }
        }
    }(),
    getOuterHtml: function(){
        if (false) {
            return function(node){
                if (node != null) {
                    var tmp = new Array();
                    tmp.push('<' + node.nodeName);
                    var attrs = node.attributes;
                    for (var i = 0; i < attrs.length; i++) {
                        var value = attrs[i].nodeValue;
                        if (value != null && value.length > 0) {
                            tmp.push(' ');
                            tmp.push(attrs[i].nodeName);
                            tmp.push('="');
                            tmp.push(value);
                            tmp.push('"');
                        }
                    }
                    if (node.innerHTML.length == 0) {
                        tmp.push('/>');
                    }
                    else {
                        tmp.push('>');
                        tmp.push(node.innerHTML);
                        tmp.push('</' + node.nodeName + '>');
                    }
                    return tmp.join('');
                }
                return '';
            }
        }
        else {
            return function(node){
                if (node != null) {
                    var serializer = new XMLSerializer();
                    return serializer.serializeToString(node);
                }
                return '';
            }
        }
    }(),
    write: function(parent, text, doc){
        doc = (doc != null) ? doc : document;
        var node = doc.createTextNode(text);
        if (parent != null) {
            parent.appendChild(node);
        }
        return node;
    },
    writeln: function(parent, text, doc){
        doc = (doc != null) ? doc : document;
        var node = doc.createTextNode(text);
        if (parent != null) {
            parent.appendChild(node);
            parent.appendChild(document.createElement('br'));
        }
        return node;
    },
    br: function(parent, count){
        count = count || 1;
        var br;
        for (var i = 0; i < count; i++) {
            br = document.createElement('br');
            if (parent != null) {
                parent.appendChild(br);
            }
        }
        return br;
    },
    button: function(label, funct){
        var button = document.createElement('button');
        mxUtils.write(button, label);
        mxEvent.addListener(button, 'click', function(evt){
            funct(evt);
        });
        return button;
    },
    para: function(parent, text){
        var p = document.createElement('p');
        mxUtils.write(p, text);
        if (parent != null) {
            parent.appendChild(p);
        }
        return p;
    },
    linkAction: function(parent, text, editor, action, pad){
        return mxUtils.link(parent, text, function(){
            editor.execute(action)
        }, pad);
    },
    linkInvoke: function(parent, text, editor, functName, arg, pad){
        return mxUtils.link(parent, text, function(){
            editor[functName](arg)
        }, pad);
    },
    link: function(parent, text, funct, pad){
        var a = document.createElement('span');
        a.style.color = 'blue';
        a.style.textDecoration = 'underline';
        a.style.cursor = 'pointer';
        if (pad != null) {
            a.style.paddingLeft = pad + 'px';
        }
        mxEvent.addListener(a, 'click', funct);
        mxUtils.write(a, text);
        if (parent != null) {
            parent.appendChild(a);
        }
        return a;
    },
    fit: function(node){
        var left = parseInt(node.offsetLeft);
        var width = parseInt(node.offsetWidth);
        var b = document.body;
        var d = document.documentElement;
        var right = (b.scrollLeft || d.scrollLeft) + (b.clientWidth || d.clientWidth);
        if (left + width > right) {
            node.style.left = Math.max((b.scrollLeft || d.scrollLeft), right - width) + 'px';
        }
        var top = parseInt(node.offsetTop);
        var height = parseInt(node.offsetHeight);
        var bottom = (b.scrollTop || d.scrollTop) + Math.max(b.clientHeight || 0, d.clientHeight);
        if (top + height > bottom) {
            node.style.top = Math.max((b.scrollTop || d.scrollTop), bottom - height) + 'px';
        }
    },
    open: function(filename){
        if (true) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
            } 
            catch (e) {
                mxUtils.alert('Permission to read file denied.');
                return '';
            }
            var file = Components.classes['@mozilla.org/file/local;1'].createInstance(Components.interfaces.nsILocalFile);
            file.initWithPath(filename);
            if (!file.exists()) {
                mxUtils.alert('File not found.');
                return '';
            }
            var is = Components.classes['@mozilla.org/network/file-input-stream;1'].createInstance(Components.interfaces.nsIFileInputStream);
            is.init(file, 0x01, 00004, null);
            var sis = Components.classes['@mozilla.org/scriptableinputstream;1'].createInstance(Components.interfaces.nsIScriptableInputStream);
            sis.init(is);
            var output = sis.read(sis.available());
            return output;
        }
        else {
            var activeXObject = new ActiveXObject('Scripting.FileSystemObject');
            var newStream = activeXObject.OpenTextFile(filename, 1);
            var text = newStream.readAll();
            newStream.close();
            return text;
        }
        return null;
    },
    save: function(filename, content){
		var isIe=mxClient.IS_IE||mxClient.IS_IE7;
        if (!isIe) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
            } 
            catch (e) {
                mxUtils.alert('Permission to write file denied.');
                return;
            }
            var file = Components.classes['@mozilla.org/file/local;1'].createInstance(Components.interfaces.nsILocalFile);
            file.initWithPath(filename);
            if (!file.exists()) {
                file.create(0x00, 0644);
            }
            var outputStream = Components.classes['@mozilla.org/network/file-output-stream;1'].createInstance(Components.interfaces.nsIFileOutputStream);
            outputStream.init(file, 0x20 | 0x02, 00004, null);
            outputStream.write(content, content.length);
            outputStream.flush();
            outputStream.close();
        }
        else {
            var fso = new ActiveXObject('Scripting.FileSystemObject');
            var file = fso.CreateTextFile(filename, true);
            file.Write(content);
            file.Close();
        }
    },
    saveAs: function(content){
        var iframe = document.createElement('iframe');
        iframe.setAttribute('src', '');
        iframe.style.visibility = 'hidden';
        document.body.appendChild(iframe);
        try {
			var isIe=mxClient.IS_IE||mxClient.IS_IE7;
            if (!isIe) {
                var doc = iframe.contentDocument;
                doc.open();
                doc.write(content);
                doc.close();
                try {
                    netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
                    iframe.focus();
                    saveDocument(doc);
                } 
                catch (e) {
                    mxUtils.alert('Permission to save document denied.');
                }
            }
            else {
                var doc = iframe.contentWindow.document;
                doc.write(content);
                doc.execCommand('SaveAs', false, document.location);
            }
        }
        finally {
            document.body.removeChild(iframe);
        }
    },
    copy: function(content){
        if (window.clipboardData) {
            window.clipboardData.setData('Text', content);
        } else {
            netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
            var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
            if (!clip) {
                return;
            }
            var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
            if (!trans) {
                return;
            }
            trans.addDataFlavor('text/unicode');
            var str = new Object();
            var len = new Object();
            var str = Components.classes['@mozilla.org/supports-string;1'].createInstance(Components.interfaces.nsISupportsString);
            var copytext = content;
            str.data = copytext;
            trans.setTransferData('text/unicode', str, copytext.length * 2);
            var clipid = Components.interfaces.nsIClipboard;
            clip.setData(trans, null, clipid.kGlobalClipboard);
        }
    },
    load: function(url){
        var req = new mxXmlRequest(url, null, 'GET', false);
        req.send();
        return req;
    },
    get: function(url, onload, onerror){
        return new mxXmlRequest(url, null, 'GET').send(onload, onerror);
    },
    post: function(url, params, onload, onerror){
        return new mxXmlRequest(url, params).send(onload, onerror);
    },
    submit: function(url, params, doc){
        return new mxXmlRequest(url, params).simulate(doc);
    },
    loadInto: function(url, doc, onload){
        if (false) {
            doc.onreadystatechange = function(){
                if (doc.readyState == 4) {
                    onload()
                }
            };
        }
        else {
            doc.addEventListener('load', onload, false);
        }
        doc.load(url);
    },
    getValue: function(array, key, defaultValue){
        var value = array[key];
        if (value == null) {
            value = defaultValue;
        }
        return value;
    },
    clone: function(obj, transients, shallow){
        shallow = (shallow != null) ? shallow : false;
        var clone = null;
        if (obj != null && typeof(obj.constructor) == 'function') {
            clone = new obj.constructor();
            for (var i in obj) {
                if (i != mxObjectIdentity.FIELD_NAME && (transients == null || mxUtils.indexOf(transients, i) < 0)) {
                    if (!shallow && typeof(obj[i]) == 'object') {
                        clone[i] = mxUtils.clone(obj[i]);
                    }
                    else {
                        clone[i] = obj[i];
                    }
                }
            }
        }
        return clone;
    },
    equalPoints: function(a, b){
        if ((a == null && b != null) || (a != null && b == null) || (a != null && b != null && a.length != b.length)) {
            return false;
        }
        else 
            if (a != null && b != null) {
                for (var i = 0; i < a.length; i++) {
                    if (a[i] == b[i] || (a[i] != null && !a[i].equals(b[i]))) {
                        return false;
                    }
                }
            }
        return true;
    },
    equalEntries: function(a, b){
        if ((a == null && b != null) || (a != null && b == null) || (a != null && b != null && a.length != b.length)) {
            return false;
        }
        else 
            if (a != null && b != null) {
                for (var key in a) {
                    if (a[key] != b[key]) {
                        return false;
                    }
                }
            }
        return true;
    },
    toString: function(obj){
        var output = '';
        for (var i in obj) {
            try {
                if (obj[i] == null) {
                    output += i + ' = [null]\n';
                }
                else 
                    if (typeof(obj[i]) == 'function') {
                        output += i + ' => [Function]\n';
                    }
                    else 
                        if (typeof(obj[i]) == 'object') {
                            var ctor = mxUtils.getFunctionName(obj[i].constructor);
                            output += i + ' => [' + ctor + ']\n';
                        }
                        else {
                            output += i + ' = ' + obj[i] + '\n';
                        }
            } 
            catch (e) {
                output += i + '=' + e.message;
            }
        }
        return output;
    },
    toRadians: function(deg){
        return Math.PI * deg / 180;
    },
    getBoundingBox: function(rect, rotation){
        var result = null;
        if (rect != null && rotation != null && rotation != 0) {
            var rad = mxUtils.toRadians(rotation);
            var cos = Math.cos(rad);
            var sin = Math.sin(rad);
            var cx = new mxPoint(rect.x + rect.width / 2, rect.y + rect.height / 2);
            var p1 = new mxPoint(rect.x, rect.y);
            var p2 = new mxPoint(rect.x + rect.width, rect.y);
            var p3 = new mxPoint(p2.x, rect.y + rect.height);
            var p4 = new mxPoint(rect.x, p3.y);
            p1 = mxUtils.getRotatedPoint(p1, cos, sin, cx);
            p2 = mxUtils.getRotatedPoint(p2, cos, sin, cx);
            p3 = mxUtils.getRotatedPoint(p3, cos, sin, cx);
            p4 = mxUtils.getRotatedPoint(p4, cos, sin, cx);
            result = new mxRectangle(p1.x, p1.y, 0, 0);
            result.add(new mxRectangle(p2.x, p2.y, 0, 0));
            result.add(new mxRectangle(p3.x, p3.y, 0, 0));
            result.add(new mxRectangle(p4.x, p4.Y, 0, 0));
        }
        return result;
    },
    getRotatedPoint: function(pt, cos, sin, cx){
        cx = (cx != null) ? cx : new mxPoint();
        var x = pt.x - c.x;
        var y = pt.y - c.y;
        var x1 = x * cos - y * sin;
        var y1 = y * cos + x * sin;
        return new mxPoint(x1 + c.x, y1 + c.y);
    },
    findNearestSegment: function(state, x, y){
        var index = -1;
        if (state.absolutePoints.length > 0) {
            var last = state.absolutePoints[0];
            var min = null;
            for (var i = 1; i < state.absolutePoints.length; i++) {
                var current = state.absolutePoints[i];
                var dist = mxUtils.ptSegDistSq(last.x, last.y, current.x, current.y, x, y);
                if (min == null || dist < min) {
                    min = dist;
                    index = i - 1;
                }
                last = current;
            }
        }
        return index;
    },
    contains: function(bounds, x, y){
        return (bounds.x <= x && bounds.x + bounds.width >= x && bounds.y <= y && bounds.y + bounds.height >= y);
    },
    intersects: function(a, b){
        return mxUtils.contains(a, b.x, b.y) || mxUtils.contains(a, b.x + b.width, b.y + b.height) || mxUtils.contains(a, b.x + b.width, b.y) || mxUtils.contains(a, b.x, b.y + b.height);
    },
    intersectsHotspot: function(state, x, y, hotspot, min, max){
        hotspot = (hotspot != null) ? hotspot : 1;
        min = (min != null) ? min : 0;
        max = (max != null) ? max : 0;
        if (hotspot > 0) {
            var cx = state.getCenterX();
            var cy = state.getCenterY();
            var w = state.width;
            var h = state.height;
            var start = mxUtils.getValue(state.style, mxConstants.STYLE_STARTSIZE);
            if (start > 0) {
                if (mxUtils.getValue(state.style, mxConstants.STYLE_HORIZONTAL, true)) {
                    cy = state.y + start / 2;
                    h = start;
                }
                else {
                    cx = state.x + start / 2;
                    w = start;
                }
            }
            var w = Math.max(min, w * hotspot);
            var h = Math.max(min, h * hotspot);
            if (max > 0) {
                w = Math.min(w, max);
                h = Math.min(h, max);
            }
            var rect = new mxRectangle(cx - w / 2, cy - h / 2, w, h);
            return mxUtils.contains(rect, x, y);
        }
        return true;
    },
    getOffset: function(container){
        var offsetLeft = 0;
        var offsetTop = 0;
        while (container.offsetParent) {
            offsetLeft += container.offsetLeft;
            offsetTop += container.offsetTop;
            container = container.offsetParent;
        }
        return new mxPoint(offsetLeft, offsetTop);
    },
    getScrollOrigin: function(node){
        var b = document.body;
        var d = document.documentElement;
        var sl = (b.scrollLeft || d.scrollLeft);
        var st = (b.scrollTop || d.scrollTop);
        var result = new mxPoint(sl, st);
        while (node != null && node != b && node != d) {
            result.x += node.scrollLeft;
            result.y += node.scrollTop;
            node = node.parentNode;
        }
        return result;
    },
    convertPoint: function(container, x, y){
        var origin = mxUtils.getScrollOrigin(container);
        var offset = mxUtils.getOffset(container);
        offset.x -= origin.x;
        offset.y -= origin.y;
        return new mxPoint(x - offset.x, y - offset.y);
    },
    isNumeric: function(str){
        return str != null && (str.length == null || (str.length > 0 && str.indexOf('0x') < 0) && str.indexOf('0X') < 0) && !isNaN(str);
    },
    intersection: function(x0, y0, x1, y1, x2, y2, x3, y3){
        var denom = ((y3 - y2) * (x1 - x0)) - ((x3 - x2) * (y1 - y0));
        var nume_a = ((x3 - x2) * (y0 - y2)) - ((y3 - y2) * (x0 - x2));
        var nume_b = ((x1 - x0) * (y0 - y2)) - ((y1 - y0) * (x0 - x2));
        var ua = nume_a / denom;
        var ub = nume_b / denom;
        if (ua >= 0.0 && ua <= 1.0 && ub >= 0.0 && ub <= 1.0) {
            var intersectionX = x0 + ua * (x1 - x0);
            var intersectionY = y0 + ua * (y1 - y0);
            return new mxPoint(intersectionX, intersectionY);
        }
        return null;
    },
    ptSegDistSq: function(x1, y1, x2, y2, px, py){
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        var dotprod = px * x2 + py * y2;
        var projlenSq;
        if (dotprod <= 0.0) {
            projlenSq = 0.0;
        }
        else {
            px = x2 - px;
            py = y2 - py;
            dotprod = px * x2 + py * y2;
            if (dotprod <= 0.0) {
                projlenSq = 0.0;
            }
            else {
                projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
            }
        }
        var lenSq = px * px + py * py - projlenSq;
        if (lenSq < 0) {
            lenSq = 0;
        }
        return lenSq;
    },
    relativeCcw: function(x1, y1, x2, y2, px, py){
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        var ccw = px * y2 - py * x2;
        if (ccw == 0.0) {
            ccw = px * x2 + py * y2;
            if (ccw > 0.0) {
                px -= x2;
                py -= y2;
                ccw = px * x2 + py * y2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    },
    animateChanges: function(graph, changes){
        var self = graph;
        var maxStep = 10;
        var step = 0;
        var animate = function(){
            var isRequired = false;
            for (var i = 0; i < changes.length; i++) {
                var change = changes[i];
                if (change.constructor == mxGeometryChange || change.constructor == mxTerminalChange || change.constructor == mxValueChange || change.constructor == mxChildChange || change.constructor == mxStyleChange) {
                    var state = self.getView().getState(change.cell || change.child, false);
                    if (state != null) {
                        isRequired = true;
                        if (change.constructor != mxGeometryChange || self.model.isEdge(change.cell)) {
                            mxUtils.setOpacity(state.shape.node, 100 * step / maxStep);
                        }
                        else {
                            var scale = graph.getView().scale;
                            var dx = (change.geometry.x - change.previous.x) * scale;
                            var dy = (change.geometry.y - change.previous.y) * scale;
                            var sx = (change.geometry.width - change.previous.width) * scale;
                            var sy = (change.geometry.height - change.previous.height) * scale;
                            if (step == 0) {
                                state.x -= dx;
                                state.y -= dy;
                                state.width -= sx;
                                state.height -= sy;
                            }
                            else {
                                state.x += dx / maxStep;
                                state.y += dy / maxStep;
                                state.width += sx / maxStep;
                                state.height += sy / maxStep;
                            }
                            self.cellRenderer.redraw(state);
                            mxUtils.cascadeOpacity(graph, change.cell, 100 * step / maxStep);
                        }
                    }
                }
            }
            mxUtils.repaintGraph(graph, new mxPoint(1, 1));
            if (step < maxStep && isRequired) {
                step++;
                window.setTimeout(animate, delay);
            }
        }
        var delay = 30;
        animate();
    },
    cascadeOpacity: function(graph, cell, opacity){
        var childCount = graph.model.getChildCount(cell);
        for (var i = 0; i < childCount; i++) {
            var child = graph.model.getChildAt(cell, i);
            var childState = graph.getView().getState(child);
            if (childState != null) {
                mxUtils.setOpacity(childState.shape.node, opacity);
                mxUtils.cascadeOpacity(graph, child, opacity);
            }
        }
        var edges = graph.model.getEdges(cell);
        if (edges != null) {
            for (var i = 0; i < edges.length; i++) {
                var edgeState = graph.getView().getState(edges[i]);
                if (edgeState != null) {
                    mxUtils.setOpacity(edgeState.shape.node, opacity);
                }
            }
        }
    },
    morph: function(graph, cells, dx, dy, step, delay){
        step = step || 30;
        delay = delay || 30;
        var current = 0;
        var f = function(){
            var model = graph.getModel();
            current = Math.min(100, current + step);
            for (var i = 0; i < cells.length; i++) {
                if (!model.isEdge(!cells[i])) {
                    var state = graph.getCellBounds(cells[i]);
                    state.x += step * dx / 100;
                    state.y += step * dy / 100;
                    graph.cellRenderer.redraw(state);
                }
            }
            if (current < 100) {
                window.setTimeout(f, delay);
            }
            else {
                graph.moveCells(cells, dx, dy);
            }
        };
        window.setTimeout(f, delay);
    },
    fadeIn: function(node, to, step, delay, isEnabled){
        to = (to != null) ? to : 100;
        step = step || 40;
        delay = delay || 30;
        var opacity = 0;
        mxUtils.setOpacity(node, opacity);
        node.style.visibility = 'visible';
        if (isEnabled || isEnabled == null) {
            var f = function(){
                opacity = Math.min(opacity + step, to);
                mxUtils.setOpacity(node, opacity);
                if (opacity < to) {
                    window.setTimeout(f, delay);
                }
            };
            window.setTimeout(f, delay);
        }
        else {
            mxUtils.setOpacity(node, to);
        }
    },
    fadeOut: function(node, from, remove, step, delay, isEnabled){
        step = step || 40;
        delay = delay || 30;
        var opacity = from || 100;
        mxUtils.setOpacity(node, opacity);
        if (isEnabled || isEnabled == null) {
            var f = function(){
                opacity = Math.max(opacity - step, 0);
                mxUtils.setOpacity(node, opacity);
                if (opacity > 0) {
                    window.setTimeout(f, delay);
                }
                else {
                    node.style.visibility = 'hidden';
                    if (remove && node.parentNode) {
                        node.parentNode.removeChild(node);
                    }
                }
            };
            window.setTimeout(f, delay);
        }
        else {
            node.style.visibility = 'hidden';
            if (remove && node.parentNode) {
                node.parentNode.removeChild(node);
            }
        }
    },
    setOpacity: function(node, value){
        if (mxUtils.isVml(node)) {
            if (value >= 100) {
                node.style.filter = null;
            }
            else {
                node.style.filter = 'alpha(opacity=' + (value / 5) + ')';
            }
        }
        else 
            if (false) {
                if (value >= 100) {
                    node.style.filter = null;
                }
                else {
                    node.style.filter = 'alpha(opacity=' + value + ')';
                }
            }
            else {
                node.style.opacity = (value / 100);
            }
    },
    createImage: function(src){
        var imgName = src.toUpperCase()
        var imageNode = null;
        if (imgName.substring(imgName.length - 3, imgName.length).toUpperCase() == 'PNG' && false && !false) {
            imageNode = document.createElement('DIV');
            imageNode.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader (src=\'' + src + '\', sizingMethod=\'scale\')';
        }
        else {
            imageNode = document.createElement('image');
            imageNode.setAttribute('src', src);
        }
        return imageNode;
    },
    sortCells: function(cells, ascending){
        ascending = (ascending != null) ? ascending : true;
        cells.sort(function(o1, o2){
            var p1 = mxCellPath.create(o1).split(mxCellPath.PATH_SEPARATOR);
            var p2 = mxCellPath.create(o2).split(mxCellPath.PATH_SEPARATOR);
            var min = Math.min(p1.length, p2.length);
            var comp = 0;
            for (var i = 0; i < min; i++) {
                if (p1[i] != p2[i]) {
                    if (p1[i].length == 0 || p2[i].length == 0) {
                        comp = (p1[i] == p2[i]) ? 0 : ((p1[i] > p2[i]) ? 1 : -1);
                    }
                    else {
                        var t1 = parseInt(p1[i]);
                        var t2 = parseInt(p2[i]);
                        comp = (t1 == t2) ? 0 : ((t1 > t2) ? 1 : -1);
                    }
                    break;
                }
            }
            if (comp == 0) {
                var t1 = p1.length;
                var t2 = p2.length;
                if (t1 != t2) {
                    comp = (t1 > t2) ? 1 : -1;
                }
            }
            return (comp == 0) ? 0 : (((comp > 0) == ascending) ? 1 : -1);
        });
        return cells;
    },
    getStylename: function(style){
        if (style != null) {
            var pairs = style.split(';');
            var stylename = pairs[0];
            if (stylename.indexOf('=') < 0) {
                return stylename;
            }
        }
        return '';
    },
    getStylenames: function(style){
        var result = new Array();
        if (style != null) {
            var pairs = style.split(';');
            for (var i = 0; i < pairs.length; i++) {
                if (pairs[i].indexOf('=') < 0) {
                    result.push(pairs[i]);
                }
            }
        }
        return result;
    },
    indexOfStylename: function(style, stylename){
        if (style != null && stylename != null) {
            var tokens = style.split(';');
            var pos = 0;
            for (var i = 0; i < tokens.length; i++) {
                if (tokens[i] == stylename) {
                    return pos;
                }
                pos += tokens[i].length + 1;
            }
        }
        return -1;
    },
    addStylename: function(style, stylename){
        if (mxUtils.indexOfStylename(style, stylename) < 0) {
            if (style == null) {
                style = '';
            }
            else 
                if (style.length > 0 && style.charAt(style.length - 1) != ';') {
                    style += ';';
                }
            style += stylename;
        }
        return style;
    },
    removeStylename: function(style, stylename){
        var result = new Array();
        if (style != null) {
            var tokens = style.split(';');
            for (var i = 0; i < tokens.length; i++) {
                if (tokens[i] != stylename) {
                    result.push(tokens[i]);
                }
            }
        }
        return result.join(';');
    },
    removeAllStylenames: function(style){
        var result = new Array();
        if (style != null) {
            var tokens = style.split(';');
            for (var i = 0; i < tokens.length; i++) {
                if (tokens[i].indexOf('=') >= 0) {
                    result.push(tokens[i]);
                }
            }
        }
        return result.join(';');
    },
    setCellStyles: function(model, cells, key, value){
        if (cells != null && cells.length > 0) {
            model.beginUpdate();
            try {
                for (var i = 0; i < cells.length; i++) {
                    if (cells[i] != null) {
                        var style = mxUtils.setStyle(model.getStyle(cells[i]), key, value);
                        model.setStyle(cells[i], style);
                    }
                }
            }
            finally {
                model.endUpdate();
            }
        }
    },
    setStyle: function(style, key, value){
        var isValue = value != null && (typeof(value.length) == 'undefined' || value.length > 0);
        if (style == null || style.length == 0) {
            if (isValue) {
                style = key + '=' + value;
            }
        }
        else {
            var index = style.indexOf(key + '=');
            if (index < 0) {
                if (isValue) {
                    var sep = (style.charAt(style.length - 1) == ';') ? '' : ';';
                    style = style + sep + key + '=' + value;
                }
            }
            else {
                var tmp = (isValue) ? key + '=' + value : '';
                var cont = style.indexOf(';', index);
                style = style.substring(0, index) + tmp + ((cont >= 0) ? style.substring(cont) : '');
            }
        }
        return style;
    },
    setCellStyleFlags: function(model, cells, key, flag, value){
        if (cells != null && cells.length > 0) {
            model.beginUpdate();
            try {
                for (var i = 0; i < cells.length; i++) {
                    if (cells[i] != null) {
                        var style = mxUtils.setStyleFlag(model.getStyle(cells[i]), key, flag, value);
                        model.setStyle(cells[i], style);
                    }
                }
            }
            finally {
                model.endUpdate();
            }
        }
    },
    setStyleFlag: function(style, key, flag, value){
        if (style == null || style.length == 0) {
            if (value || value == null) {
                style = key + '=' + flag;
            }
            else {
                style = key + '=0';
            }
        }
        else {
            var index = style.indexOf(key + '=');
            if (index < 0) {
                var sep = (style.charAt(style.length - 1) == ';') ? '' : ';';
                if (value || value == null) {
                    style = style + sep + key + '=' + flag;
                }
                else {
                    style = style + sep + key + '=0';
                }
            }
            else {
                var cont = style.indexOf(';', index);
                var tmp = '';
                if (cont < 0) {
                    tmp = style.substring(index + key.length + 1);
                }
                else {
                    tmp = style.substring(index + key.length + 1, cont);
                }
                if (value == null) {
                    tmp = parseInt(tmp) ^ flag;
                }
                else 
                    if (value) {
                        tmp = parseInt(tmp) | flag;
                    }
                    else {
                        tmp = parseInt(tmp) & ~ flag;
                    }
                style = style.substring(0, index) + key + '=' + tmp + ((cont >= 0) ? style.substring(cont) : '');
            }
        }
        return style;
    },
    getSizeForString: function(text, fontSize, fontFamily){
        var div = document.createElement('div');
        div.style.fontSize = fontSize || mxConstants.DEFAULT_FONTSIZE;
        div.style.fontFamily = fontFamily || mxConstants.DEFAULT_FONTFAMILY
        div.style.position = 'absolute';
        div.style.display = 'inline';
        div.style.visibility = 'hidden';
        div.innerHTML = text;
        document.body.appendChild(div);
        var size = new mxRectangle(0, 0, div.offsetWidth, div.offsetHeight);
        document.body.removeChild(div);
        return size;
    },
    getViewXml: function(graph, scale, cells, x0, y0){
        x0 = (x0 != null) ? x0 : 0;
        y0 = (y0 != null) ? y0 : 0;
        scale = (scale != null) ? scale : 1;
        if (cells == null) {
            var model = graph.getModel();
            cells = [model.getRoot()];
        }
        var view = graph.getView();
        var result = null;
        var eventsEnabled = view.isEventsEnabled();
        view.setEventsEnabled(false);
        var rendering = view.isRendering();
        view.setRendering(false);
        var translate = view.getTranslate();
        view.translate = new mxPoint(x0, y0);
        var temp = new mxTemporaryCellStates(graph.getView(), scale, cells);
        try {
            var enc = new mxCodec();
            result = enc.encode(graph.getView());
        }
        finally {
            temp.destroy();
            view.translate = translate;
            view.setRendering(rendering);
            view.setEventsEnabled(eventsEnabled);
        }
        return result;
    },
    getScaleForPageCount: function(pageCount, graph, pageFormat, x0, y0){
        x0 = (x0 != null) ? x0 : 0;
        y0 = (y0 != null) ? y0 : 0;
        if (pageFormat == null) {
            pageFormat = mxConstants.PAGE_FORMAT_A4_PORTRAIT;
        }
        
        var graphBounds = graph.getGraphBounds().clone();
        var sc = graph.getView().getScale();
        var tr = graph.getView().getTranslate();
        graphBounds.x /= sc;
        graphBounds.x -= tr.x - x0;
        graphBounds.y /= sc;
        graphBounds.y -= tr.y - y0;
        graphBounds.width /= sc;
        graphBounds.height /= sc;
        var graphWidth = graphBounds.width + graphBounds.x;
        var graphHeight = graphBounds.height + graphBounds.y;
        var rowPages = Math.max(1, Math.ceil((graphWidth) / pageFormat.width));
        var columnPages = Math.max(1, Math.ceil((graphHeight) / pageFormat.height));
        
        var unscaledPageCount = rowPages * columnPages;
        var scale = 1;
        if (pageCount != unscaledPageCount) {
        
            var unscaledGraphArea = (graphWidth) * (graphHeight);
            var optimalPageArea = (pageFormat.width * pageFormat.height) * pageCount;
            var areaRatio = optimalPageArea / unscaledGraphArea;
            var scale = Math.sqrt(areaRatio);
            
            var scaledGraphX = (graphWidth) * scale;
            var scaledGraphY = (graphHeight) * scale;
            var numScaledRowPages = scaledGraphX / pageFormat.width;
            var numScaledColumnPages = scaledGraphY / pageFormat.height;
            var currentTotalPages = Math.ceil(numScaledRowPages) * Math.ceil(numScaledColumnPages);
            
            while (currentTotalPages > pageCount) {
            
            
            
                var roundRowDownProportion = Math.floor(numScaledRowPages) / numScaledRowPages;
                var roundColumnDownProportion = Math.floor(numScaledColumnPages) / numScaledColumnPages;
                
                var scaleChange;
                if (roundRowDownProportion > roundColumnDownProportion && roundRowDownProportion > 0 && roundRowDownProportion < 1) {
                    scaleChange = roundRowDownProportion;
                }
                else 
                    if (roundColumnDownProportion > 0 && roundColumnDownProportion < 1 != null) {
                        scaleChange = roundColumnDownProportion;
                    }
                    else {
                    
                    
                        if (Math.floor(numScaledRowPages) > 1) {
                            scaleChange = (Math.floor(numScaledRowPages - 1) / numScaledRowPages);
                        }
                        else 
                            if (Math.floor(numScaledColumnPages) > 1) {
                                scaleChange = (Math.floor(numScaledColumnPages - 1) / numScaledColumnPages);
                            }
                    }
                numScaledRowPages = numScaledRowPages * scaleChange;
                numScaledColumnPages = numScaledColumnPages * scaleChange;
                scale = scale * scaleChange;
                currentTotalPages = Math.ceil(numScaledRowPages) * Math.ceil(numScaledColumnPages);
            }
            while (currentTotalPages < pageCount) {
            
            
            
            
            
                var roundRowUpProportion = Math.ceil(numScaledRowPages) / numScaledRowPages;
                var roundColumnUpProportion = Math.ceil(numScaledColumnPages) / numScaledColumnPages;
                
                var tempScale;
                if (roundRowUpProportion < roundColumnUpProportion && roundRowUpProportion > 0.001) {
                    tempScale = scale * roundRowUpProportion;
                }
                else 
                    if (roundColumnUpProportion > 0.01) {
                        tempScale = scale * roundColumnUpProportion;
                    }
                    else {
                    
                    
                        tempScale = scale * (Math.floor(numScaledRowPage + 1) / numScaleRowPage);
                    }
                var tempNumScaledRowPages = numScaledRowPages * tempScale;
                var tempNumScaledColumnPages = numScaledColumnPages * tempScale;
                var tempCurrentTotalPages = Math.ceil(numScaledRowPages) * Math.ceil(numScaledColumnPages);
                if (tempCurrentTotalPages <= pageCount) {
                    scale = tempScale;
                    numScaledRowPages = numScaledRowPages * scale;
                    numScaledColumnPages = numScaledColumnPages * scale;
                    currentTotalPages = Math.ceil(numScaledRowPages) * Math.ceil(numScaledColumnPages);
                }
                if (tempCurrentTotalPages >= pageCount) {
                
                    break;
                }
            }
        }
        return scale;
    },
    show: function(graph, doc){
        if (doc == null) {
            var wnd = window.open();
            doc = wnd.document;
        }
        else {
            doc.open();
        }
        doc.writeln('<html xmlns:v="urn:schemas-microsoft-com:vml">');
        doc.writeln('<head>');
        var base = document.getElementsByTagName('base');
        for (var i = 0; i < base.length; i++) {
            doc.writeln(mxUtils.getOuterHtml(base[i]));
        }
        var links = document.getElementsByTagName('link');
        for (var i = 0; i < links.length; i++) {
            doc.writeln(mxUtils.getOuterHtml(links[i]));
        }
        var styles = document.getElementsByTagName('style');
        for (var i = 0; i < styles.length; i++) {
            doc.writeln(mxUtils.getOuterHtml(styles[i]));
        }
        doc.writeln('</head>');
        var bounds = graph.getGraphBounds();
        var dx = Math.min(bounds.x, 0);
        var dy = Math.min(bounds.y, 0);
        if (false) {
            doc.writeln('<body>');
            doc.writeln(mxUtils.getInnerHtml(graph.container));
            doc.writeln('</body>');
            doc.writeln('</html>');
            var node = doc.body.getElementsByTagName('DIV')[0];
            if (node != null) {
                node.style.position = 'absolute';
                node.style.left = -dx + 'px';
                node.style.top = -dy + 'px';
            }
            doc.close();
        }
        else {
            doc.writeln('</html>');
            doc.close();
            
            doc.documentElement.appendChild(doc.createElement('body'));
            var node = graph.container.firstChild;
            while (node != null) {
                var clone = node.cloneNode(true);
                doc.body.appendChild(clone);
                node = node.nextSibling;
            }
        }
        mxUtils.removeCursors(doc.documentElement);
        if (!false) {
            var node = doc.getElementsByTagName('g')[0];
            if (node != null) {
                node.setAttribute('transform', 'translate(' + (-dx) + ',' + (-dy) + ')');
                var root = node.ownerSVGElement;
                root.setAttribute('width', bounds.width + Math.max(bounds.x, 0) + 3);
                root.setAttribute('height', bounds.height + Math.max(bounds.y, 0) + 3);
                root.style.position = 'absolute';
                root.style.left = dx + 'px';
                root.style.top = dy + '0px';
            }
        }
        else {
        
        
            doc.execCommand('Refresh', 'false', 'false');
        }
        return doc;
    },
    printScreen: function(graph){
        var wnd = window.open();
        mxUtils.show(graph, wnd.document);
        var print = function(){
            wnd.focus();
            wnd.print();
            wnd.close();
        };
        
        if (false) {
            wnd.setTimeout(print, 500)
        }
        else {
            print();
        }
    },
    popup: function(content, isInternalWindow){
        if (isInternalWindow) {
            var div = document.createElement('div');
            div.style.overflow = 'scroll';
            div.style.width = '636px';
            div.style.height = '460px';
            var pre = document.createElement('pre');
            pre.innerHTML = mxUtils.htmlEntities(content, false).replace(/\n/g, '<br>').replace(/ /g, '&nbsp;');
            div.appendChild(pre);
            var w = document.body.clientWidth;
            var h = (document.body.clientHeight || document.documentElement.clientHeight);
            var wnd = new mxWindow('Popup Window', div, w / 2 - 320, h / 2 - 240, 640, 480, false, true);
            wnd.setClosable(true);
            wnd.setVisible(true);
        }
        else {
            if (true) {
                var wnd = window.open();
                wnd.document.writeln('<pre>' + mxUtils.htmlEntities(content) + '</pre');
                wnd.document.close();
            }
            else {
                var wnd = window.open();
                var pre = wnd.document.createElement('pre');
                pre.innerHTML = mxUtils.htmlEntities(content, false).replace(/\n/g, '<br>').replace(/ /g, '&nbsp;');
                wnd.document.body.appendChild(pre);
            }
        }
    },
    alert: function(message){
        alert(message);
    },
    prompt: function(message, defaultValue){
        return prompt(message, defaultValue);
    },
    confirm: function(message){
        return confirm(message);
    },
    error: function(message, width, close, icon){
        var div = document.createElement('div');
        div.style.padding = '20px';
        var img = document.createElement('img');
        img.setAttribute('src', icon || mxUtils.errorImage);
        img.setAttribute('valign', 'bottom');
        img.style.verticalAlign = 'middle';
        div.appendChild(img);
        div.appendChild(document.createTextNode('\u00a0'));
        div.appendChild(document.createTextNode('\u00a0'));
        div.appendChild(document.createTextNode('\u00a0'));
        mxUtils.write(div, message);
        var w = document.body.clientWidth;
        var h = (document.body.clientHeight || document.documentElement.clientHeight);
        var warn = new mxWindow(mxResources.get(mxUtils.errorResource) || mxUtils.errorResource, div, (w - width) / 2, h / 4, width, null, false, true);
        if (close) {
            mxUtils.br(div);
            var tmp = document.createElement('p');
            var button = document.createElement('button');
            if (false) {
                button.style.cssText = 'float:right';
            }
            else {
                button.setAttribute('style', 'float:right');
            }
            mxEvent.addListener(button, 'click', function(evt){
                warn.destroy();
            });
            mxUtils.write(button, mxResources.get(mxUtils.closeResource) || mxUtils.closeResource);
            tmp.appendChild(button);
            div.appendChild(tmp);
            mxUtils.br(div);
            warn.setClosable(true);
        }
        warn.setVisible(true);
        return warn;
    },
    makeDraggable: function(element, graph, funct, dragElement, dx, dy, autoscroll, scalePreview, highlightDropTargets, getDropTarget){
        dx = (dx != null) ? dx : 0;
        dy = (dy != null) ? dy : mxConstants.TOOLTIP_VERTICAL_OFFSET;
        highlightDropTargets = (highlightDropTargets != null) ? highlightDropTargets : true;
        getDropTarget = (getDropTarget != null) ? getDropTarget : function(x, y){
            return graph.getCellAt(x, y);
        };
        mxEvent.addListener(element, 'mousedown', function(evt){
            if (graph.isEnabled() && !mxEvent.isConsumed(evt)) {
            
                var sprite = (dragElement != null) ? dragElement.cloneNode(true) : element.cloneNode(true);
                if (scalePreview) {
                    var scale = graph.view.scale;
                    sprite.style.width = (parseInt(sprite.style.width) * scale) + 'px';
                    sprite.style.height = (parseInt(sprite.style.height) * scale) + 'px';
                    dx *= scale;
                    dy *= scale;
                }
                sprite.style.zIndex = 3;
                sprite.style.position = 'absolute';
                mxUtils.setOpacity(sprite, 70);
                
                
                
                var dropTarget = null;
                var initialized = false;
                var startX = evt.clientX;
                var startY = evt.clientY;
                var highlight = null;
                var highlightCell = function(){
                };
                if (highlightDropTargets) {
                    highlight = new mxCellHighlight(graph, mxConstants.DROP_TARGET_COLOR);
                    
                    highlightCell = function(cell){
                        var state = graph.getView().getState(cell);
                        highlight.highlight(state);
                    };
                }
                
                var dragHandler = function(evt){
                    var origin = mxUtils.getScrollOrigin();
                    var pt = mxUtils.convertPoint(graph.container, evt.clientX, evt.clientY);
                    sprite.style.left = (evt.clientX + origin.x + dx) + 'px';
                    sprite.style.top = (evt.clientY + origin.y + dy) + 'px';
                    if (!initialized) {
                        initialized = true;
                        document.body.appendChild(sprite);
                    }
                    else 
                        if (graph.autoScroll && (autoscroll == null || autoscroll)) {
                            graph.scrollPointToVisible(pt.x, pt.y, graph.autoExtend);
                        }
                    
                    dropTarget = getDropTarget(pt.x, pt.y);
                    highlightCell(dropTarget);
                    mxEvent.consume(evt);
                };
                var dropHandler = function(evt){
                    mxEvent.removeListener(document, 'mousemove', dragHandler);
                    mxEvent.removeListener(document, 'mouseup', dropHandler);
                    if (sprite.parentNode != null) {
                        sprite.parentNode.removeChild(sprite);
                    }
                    if (highlight != null) {
                        highlight.destroy();
                        highlight = null;
                    }
                    try {
                    
                        var pt = mxUtils.convertPoint(graph.container, evt.clientX, evt.clientY);
                        
                        var tol = 2 * graph.tolerance;
                        if (pt.x >= graph.container.scrollLeft && pt.y >= graph.container.scrollTop && pt.x <= graph.container.scrollLeft + graph.container.clientWidth && pt.y <= graph.container.scrollTop + graph.container.clientHeight && (Math.abs(evt.clientX - startX) > tol || Math.abs(evt.clientY - startY) > tol)) {
                            funct(graph, evt, dropTarget);
                        }
                    }
                    finally {
                        mxEvent.consume(evt);
                    }
                };
                mxEvent.addListener(document, 'mousemove', dragHandler);
                mxEvent.addListener(document, 'mouseup', dropHandler);
                mxEvent.consume(evt);
            }
        });
    }
};
