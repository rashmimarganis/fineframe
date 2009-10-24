//mxClient
var mxClient = {
    VERSION: '1.0.2.4',
    IS_IE: navigator.userAgent.indexOf('MSIE') >= 0,
    IS_IE7: navigator.userAgent.indexOf('MSIE 7') >= 0,
    IS_NS: navigator.userAgent.indexOf('Mozilla/5') >= 0,
    IS_FF2: navigator.userAgent.indexOf('Firefox/2') >= 0 || navigator.userAgent.indexOf('Iceweasel/2') >= 0,
    IS_FF3: navigator.userAgent.indexOf('Firefox/3') >= 0 || navigator.userAgent.indexOf('Iceweasel/3') >= 0,
    IS_OP: navigator.userAgent.indexOf('Opera/9') >= 0,
    IS_SF: navigator.userAgent.indexOf('AppleWebKit/5') >= 0 && navigator.userAgent.indexOf('Chrome') < 0,
    IS_GC: navigator.userAgent.indexOf('Chrome') >= 0,
    IS_SVG: navigator.userAgent.indexOf('Firefox/1.5') >= 0 || navigator.userAgent.indexOf('Firefox/2') >= 0 || navigator.userAgent.indexOf('Firefox/3') >= 0 || navigator.userAgent.indexOf('Iceweasel/1.5') >= 0 ||
    navigator.userAgent.indexOf('Iceweasel/2') >= 0 ||
    navigator.userAgent.indexOf('Iceweasel/3') >= 0 ||
    navigator.userAgent.indexOf('Camino/1') >= 0 ||
    navigator.userAgent.indexOf('Epiphany/2') >= 0 ||
    navigator.userAgent.indexOf('Iceape/1') >= 0 ||
    navigator.userAgent.indexOf('Galeon/2') >= 0 ||
    navigator.userAgent.indexOf('Opera/9') >= 0 ||
    navigator.userAgent.indexOf('Gecko/') >= 0 ||
    navigator.userAgent.indexOf('AppleWebKit/5') >= 0,
    
    IS_VML: navigator.appName.toUpperCase() == 'MICROSOFT INTERNET EXPLORER',
    IS_CANVAS: navigator.appName == 'Netscape',
    IS_MAC: navigator.userAgent.toUpperCase().indexOf('MACINTOSH') > 0,
    IS_LOCAL: document.location.href.indexOf('http://') < 0 && document.location.href.indexOf('https://') < 0,
    FADE_RUBBERBAND: false,
    WINDOW_SHADOWS: true,
    TOOLTIP_SHADOWS: true,
    MENU_SHADOWS: true,
    isBrowserSupported: function(){
        return false || true;
    },
    link: function(rel, href, doc){
        doc = doc || document;
        if (false && !false) {
            doc.write('<link rel="' + rel + '" href="' + href + '" charset="ISO-8859-1" type="text/css"/>');
        }
        else {
            var link = doc.createElement('link');
            link.setAttribute('rel', rel);
            link.setAttribute('href', href);
            link.setAttribute('charset', 'ISO-8859-1');
            link.setAttribute('type', 'text/css');
            var head = doc.getElementsByTagName('head')[0];
            head.appendChild(link);
        }
    },
    addOnloadCallback: function(callback){
        if (mxClient.loading == null || mxClient.loading == 0) {
            callback();
        }
        else {
            if (mxClient.onloadCallbacks == null) {
                mxClient.onloadCallbacks = new Array();
            }
            mxClient.onloadCallbacks.push(callback);
        }
    },
    onload: function(){
        if (mxClient.onloadCallbacks != null) {
            var cb = mxClient.onloadCallbacks;
            mxClient.onloadCallbacks = null;
            for (var i = 0; i < cb.length; i++) {
                cb[i]();
            }
        }
        if (false) {
            window.attachEvent("onunload", function(){
                mxClient.unload();
            });
        }
    },
    include: function(src){
        if (false || false) {
            var req = new XMLHttpRequest();
            try {
                req.open('GET', src, false);
                req.send();
                window._mxDynamicCode = req.responseText;
                var script = document.createElement('script');
                script.type = 'text/javascript';
                script.innerHTML = 'eval(window._mxDynamicCode)';
                var head = document.getElementsByTagName('head')[0];
                head.appendChild(script)
                delete window._mxDynamicCode;
            } 
            catch (e) {
            }
        }
        else 
            if (false) {
                document.write('<script src="' + src + '"></script>');
            }
            else {
                var script = document.createElement('script');
                script.setAttribute('type', 'text/javascript');
                script.setAttribute('src', src);
                var onload = function(script){
                    if (script != null) {
                        script.onload = null;
                        script.onerror = null;
                        script.onreadystatechange = null;
                    }
                    mxClient.loading--;
                    if (mxClient.loading == 0) {
                        mxClient.onload();
                    }
                };
                script.onload = onload;
                script.onerror = onload;
                var head = document.getElementsByTagName('head')[0];
                head.appendChild(script);
                if (mxClient.loading == null) {
                    mxClient.loading = 1;
                }
                else {
                    mxClient.loading++;
                }
            }
    },
    unload: function(){
        mxEvent.release(document.documentElement);
        mxEvent.release(window);
    }
};
mxClient.basePath = (typeof(mxBasePath) != 'undefined') ? mxBasePath : '';
mxClient.imageBasePath = (typeof(mxImageBasePath) != 'undefined') ? mxImageBasePath : mxClient.basePath + 'images/';
if (typeof(mxLanguage) != 'undefined') {
    mxClient.language = mxLanguage;
} else {
	var isIe=mxClient.IS_IE||mxClient.IS_IE7;
    mxClient.language = (isIe) ? navigator.userLanguage : navigator.language;
    var dash = mxClient.language.indexOf('-');
    if (dash > 0) {
        mxClient.language = mxClient.language.substring(0, dash);
    }
}
mxClient.link('stylesheet', mxClient.basePath + 'css/common.css');

if (mxClient.IS_IE||mxClient.IS_IE7) {
    document.namespaces.add("v", "urn:schemas-microsoft-com:vml");
    document.namespaces.add("o", "urn:schemas-microsoft-com:office:office");
    mxClient.link('stylesheet', mxClient.basePath + 'css/explorer.css');
}