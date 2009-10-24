//mxLog
var mxLog = {
    consoleResource: (mxClient.language != 'none') ? 'console' : '',
    TRACE: false,
    DEBUG: true,
    WARN: true,
    buffer: '',
    init: function(){
        if (mxLog.window == null && document.body != null) {
            var title = (mxResources.get(mxLog.consoleResource) || mxLog.consoleResource) + ' - mxGraph ' + mxClient.VERSION;
            var table = document.createElement('table');
            table.setAttribute('width', '100%');
            table.setAttribute('height', '100%');
            var tbody = document.createElement('tbody');
            var tr = document.createElement('tr');
            var td = document.createElement('td');
            td.style.verticalAlign = 'top';
            mxLog.textarea = document.createElement('textarea');
            mxLog.textarea.setAttribute('readOnly', 'true');
            mxLog.textarea.style.width = "100%";
            mxLog.textarea.style.height = "100%";
            mxLog.textarea.value = mxLog.buffer;
            td.appendChild(mxLog.textarea);
            tr.appendChild(td);
            tbody.appendChild(tr);
            tr = document.createElement('tr');
            mxLog.td = document.createElement('td');
            mxLog.td.style.verticalAlign = 'top';
            mxLog.td.setAttribute('height', '30px');
            tr.appendChild(mxLog.td);
            tbody.appendChild(tr);
            table.appendChild(tbody);
            mxLog.addButton('Info', function(evt){
                mxLog.writeln(mxUtils.toString(navigator));
            });
            mxLog.addButton('DOM', function(evt){
                var content = mxUtils.getInnerHtml(document.body);
                mxLog.debug(content);
            });
            mxLog.addButton('Trace', function(evt){
                mxLog.TRACE = !mxLog.TRACE;
                if (mxLog.TRACE) {
                    mxLog.debug('Tracing enabled');
                }
                else {
                    mxLog.debug('Tracing disabled');
                }
            });
            mxLog.addButton('Copy', function(evt){
                try {
                    mxUtils.copy(mxLog.textarea.value);
                } 
                catch (err) {
                    mxUtils.alert(err);
                }
            });
            mxLog.addButton('Show', function(evt){
                try {
                    mxUtils.popup(mxLog.textarea.value);
                } 
                catch (err) {
                    mxUtils.alert(err);
                }
            });
            mxLog.addButton('Clear', function(evt){
                mxLog.textarea.value = '';
            });
            var w = document.body.clientWidth;
            var h = (document.body.clientHeight || document.documentElement.clientHeight);
            mxLog.window = new mxWindow(title, table, w - 320, h - 210, 300, 160);
            mxLog.window.setMaximizable(true);
            mxLog.window.setScrollable(true);
            mxLog.window.setResizable(true);
            mxLog.window.setClosable(true);
            mxLog.window.destroyOnClose = false;
            if (true && document.compatMode != 'BackCompat') {
                var resizeHandler = function(sender, evt){
                    var elt = mxLog.window.getElement();
                    mxLog.textarea.style.height = (elt.offsetHeight - 78) + 'px';
                };
                mxLog.window.addListener(mxEvent.RESIZE_END, resizeHandler);
                mxLog.window.addListener(mxEvent.MAXIMIZE, resizeHandler);
                mxLog.window.addListener(mxEvent.NORMALIZE, resizeHandler);
                var elt = mxLog.window.getElement();
                mxLog.textarea.style.height = '96px';
            }
        }
    },
    addButton: function(lab, funct){
        var button = document.createElement('button');
        mxUtils.write(button, lab);
        mxEvent.addListener(button, 'click', funct);
        mxLog.td.appendChild(button);
    },
    isVisible: function(){
        if (mxLog.window != null) {
            return mxLog.window.isVisible();
        }
        return false;
    },
    show: function(){
        mxLog.setVisible(true);
    },
    setVisible: function(visible){
        if (mxLog.window == null) {
            mxLog.init();
        }
        if (mxLog.window != null) {
            mxLog.window.setVisible(visible);
        }
    },
    enter: function(string){
        if (mxLog.TRACE) {
            mxLog.writeln('Entering ' + string);
            return new Date().getTime();
        }
    },
    leave: function(string, t0){
        if (mxLog.TRACE) {
            var dt = (t0 != 0) ? ' (' + (new Date().getTime() - t0) + ' ms)' : '';
            mxLog.writeln('Leaving ' + string + dt);
        }
    },
    debug: function(string){
        if (mxLog.DEBUG) {
            mxLog.writeln(string);
        }
    },
    warn: function(string){
        if (mxLog.WARN) {
            mxLog.writeln(string);
        }
    },
    write: function(string){
        if (mxLog.textarea != null) {
            mxLog.textarea.value = mxLog.textarea.value + string;
            mxLog.textarea.scrollTop = mxLog.textarea.scrollHeight;
        }
        else {
            mxLog.buffer += string;
        }
    },
    writeln: function(string){
        mxLog.write(string + '\n');
    }
};