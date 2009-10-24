//mxEventObject
{
    function mxEventObject(args){
        this.args = args || [];
    };
    mxEventObject.prototype.args = null;
    mxEventObject.prototype.consumed = false;
    mxEventObject.prototype.getArgs = function(){
        return this.args;
    };
    mxEventObject.prototype.getArgCount = function(){
        return this.args.length;
    };
    mxEventObject.prototype.getArgAt = function(index){
        return this.args[index];
    };
    mxEventObject.prototype.isConsumed = function(){
        return this.consumed;
    };
    mxEventObject.prototype.consume = function(){
        this.consumed = true;
    };
}
//mxMouseEvent
{
    function mxMouseEvent(evt, state, handle, tooltip){
        this.evt = evt;
        this.state = state;
        this.handle = handle;
        this.tooltip = tooltip;
    };
    mxMouseEvent.prototype.evt = null;
    mxMouseEvent.prototype.state = null;
    mxMouseEvent.prototype.handle = null;
    mxMouseEvent.prototype.tooltip = null;
    mxMouseEvent.prototype.getEvent = function(){
        return this.evt;
    };
    mxMouseEvent.prototype.getTarget = function(){
        return mxEvent.getSource(this.evt);
    };
    mxMouseEvent.prototype.getX = function(){
        return this.getEvent().clientX;
    };
    mxMouseEvent.prototype.getY = function(){
        return this.getEvent().clientY;
    };
    mxMouseEvent.prototype.getState = function(){
        return this.state;
    };
    mxMouseEvent.prototype.getCell = function(){
        var state = this.getState();
        if (state != null) {
            return state.cell;
        }
        return null;
    };
    mxMouseEvent.prototype.setState = function(value){
        this.state = value;
    };
    mxMouseEvent.prototype.getHandle = function(){
        return this.handle;
    };
    mxMouseEvent.prototype.setHandle = function(value){
        this.handle = value;
    };
    mxMouseEvent.prototype.getTooltip = function(){
        return this.tooltip;
    };
    mxMouseEvent.prototype.setTooltip = function(value){
        this.tooltip = value;
    };
    mxMouseEvent.prototype.isPopupTrigger = function(){
        return mxEvent.isPopupTrigger(this.getEvent());
    };
    mxMouseEvent.prototype.isConsumed = function(){
        return mxEvent.isConsumed(this.getEvent());
    };
    mxMouseEvent.prototype.consume = function(){
        mxEvent.consume(this.getEvent());
    };
}
//mxEventSource
{
    function mxEventSource(eventSource){
        this.setEventSource(eventSource);
    };
    mxEventSource.prototype.eventListeners = null;
    mxEventSource.prototype.eventsEnabled = true;
    mxEventSource.prototype.eventSource = null;
    mxEventSource.prototype.isEventsEnabled = function(){
        return this.eventsEnabled;
    };
    mxEventSource.prototype.setEventsEnabled = function(value){
        this.eventsEnabled = value;
    };
    mxEventSource.prototype.getEventSource = function(){
        return this.eventSource;
    };
    mxEventSource.prototype.setEventSource = function(value){
        this.eventSource = value;
    };
    mxEventSource.prototype.addListener = function(name, funct){
        if (this.eventListeners == null) {
            this.eventListeners = new Array();
        }
        this.eventListeners.push(name);
        this.eventListeners.push(funct);
    };
    mxEventSource.prototype.removeListener = function(funct){
        if (this.eventListeners != null) {
            var i = 0;
            while (i < this.eventListeners.length) {
                if (this.eventListeners[i + 1] == funct) {
                    this.eventListeners.splice(i, 2);
                }else {
                    i += 2;
                }
            }
        }
    };
    mxEventSource.prototype.fireEvent = function(name, evt, source){
        if (this.eventListeners != null && this.isEventsEnabled()) {
            if (evt == null) {
                evt = new mxEventObject();
            }
            if (source == null) {
                source = this.getEventSource();
            }
            if (source == null) {
                source = this;
            }
            var args = [source, evt];
            for (var i = 0; i < this.eventListeners.length; i += 2) {
                var listen = this.eventListeners[i];
                if (listen == null || listen == name) {
                    this.eventListeners[i + 1].apply(this, args);
                }
            }
        }
    };
}
//mxEvent
var mxEvent = {
    addListener: function(){
        var updateListenerList = function(element, eventName, funct){
            if (element.mxListenerList == null) {
                element.mxListenerList = new Array();
            }
            var entry = {
                name: eventName,
                f: funct
            };
            element.mxListenerList.push(entry);
        }
		var isIe=mxClient.IS_IE||mxClient.IS_IE7;
        if (isIe) {
            return function(element, eventName, funct){
                element.attachEvent("on" + eventName, funct);
                updateListenerList(element, eventName, funct);
            }
        }
        else {
            return function(element, eventName, funct){
                element.addEventListener(eventName, funct, false);
                updateListenerList(element, eventName, funct);
            }
        }
    }(),
    redirectMouseEvents: function(element, graph, cell, handle, transparent, doubleClick, forceTransparent, transparentSwimlaneContent){
        transparentSwimlaneContent = (transparentSwimlaneContent != null) ? transparentSwimlaneContent : true;
        var state = graph.getView().getState(cell);
        
        
        
        var getState = function(evt){
            var result = state;
            var pt = mxUtils.convertPoint(graph.container, evt.clientX, evt.clientY);
            if (forceTransparent || (transparent && false)) {
                var tmp = graph.getCellAt(pt.x, pt.y);
                if (cell != tmp) {
                    result = graph.getView().getState(tmp);
                }
            }
            if (result != null && transparentSwimlaneContent && graph.isSwimlane(result.cell) && graph.hitsSwimlaneContent(result.cell, pt.x, pt.y)) {
                result = null;
            }
            return result;
        };
        mxEvent.addListener(element, 'mousedown', function(evt){
            graph.fireMouseEvent(mxEvent.MOUSE_DOWN, new mxMouseEvent(evt, getState(evt), handle));
        });
        mxEvent.addListener(element, 'mousemove', function(evt){
            graph.fireMouseEvent(mxEvent.MOUSE_MOVE, new mxMouseEvent(evt, getState(evt), handle));
        });
        mxEvent.addListener(element, 'mouseup', function(evt){
            graph.fireMouseEvent(mxEvent.MOUSE_UP, new mxMouseEvent(evt, getState(evt), handle));
        });
        if (doubleClick) {
            mxEvent.addListener(element, 'dblclick', function(evt){
                var state = getState(evt);
                var cell = (state != null) ? state.cell : null;
                graph.dblClick(evt, cell);
                mxEvent.consume(evt);
            });
        }
    },
    removeListener: function(){
        var updateListener = function(element, eventName, funct){
            if (element.mxListenerList != null) {
                var listenerCount = element.mxListenerList.length;
                for (var i = 0; i < listenerCount; i++) {
                    var entry = element.mxListenerList[i];
                    if (entry.f == funct) {
                        element.mxListenerList.splice(i, 1);
                        break;
                    }
                }
                if (element.mxListenerList.length == 0) {
                    element.mxListenerList = null;
                }
            }
        }
        if (false) {
            return function(element, eventName, funct){
                element.detachEvent("on" + eventName, funct);
                updateListener(element, eventName, funct);
            }
        }
        else {
            return function(element, eventName, funct){
                element.removeEventListener(eventName, funct, false);
                updateListener(element, eventName, funct);
            }
        }
    }(),
    removeAllListeners: function(element){
        var list = element.mxListenerList;
        if (list != null) {
            while (list.length > 0) {
                var entry = list[0];
                mxEvent.removeListener(element, entry.name, entry.f);
            }
        }
    },
    release: function(element){
        if (element != null) {
            mxEvent.removeAllListeners(element);
            var children = element.childNodes;
            if (children != null) {
                var childCount = children.length;
                for (var i = 0; i < childCount; i += 1) {
                    mxEvent.release(children[i]);
                }
            }
        }
    },
    addMouseWheelListener: function(funct){
        if (funct != null) {
            var wheelHandler = function(evt){
            
            
                if (evt == null) {
                    evt = window.event;
                }
                var delta = 0;
                if (true && !false && !false) {
                    delta = -evt.detail / 2;
                }
                else {
                    delta = evt.wheelDelta / 120;
                }
                if (delta != 0) {
                    funct(evt, delta > 0);
                }
            };
			var isIe=mxClient.IS_IE||mxClient.IS_IE7;
            if (!isIe) {
                var eventName = (isIe) ? 'mousewheel' : 'DOMMouseScroll';
                mxEvent.addListener(window, eventName, wheelHandler);
            }
            else {
            
                mxEvent.addListener(document, 'mousewheel', wheelHandler);
            }
        }
    },
    disableContextMenu: function(){
        if (false) {
            return function(element){
                mxEvent.addListener(element, 'contextmenu', function(){
                    return false;
                });
            }
        }
        else {
            return function(element){
                element.setAttribute('oncontextmenu', 'return false;');
            }
        }
    }(),
    getSource: function(evt){
        return (evt.srcElement != null) ? evt.srcElement : evt.target;
    },
    isConsumed: function(evt){
        return evt.isConsumed != null && evt.isConsumed;
    },
    isLeftMouseButton: function(evt){
        return evt.button == ((false) ? 1 : 0);
    },
    isRightMouseButton: function(evt){
        return evt.button == 2;
    },
    isPopupTrigger: function(evt){
        return mxEvent.isRightMouseButton(evt) || (mxEvent.isShiftDown(evt) && !mxEvent.isControlDown(evt));
    },
    isShiftDown: function(evt){
        return (evt != null) ? evt.shiftKey : false;
    },
    isAltDown: function(evt){
        return (evt != null) ? evt.altKey : false;
    },
    isControlDown: function(evt){
        return (evt != null) ? evt.ctrlKey : false;
    },
    isMetaDown: function(evt){
        return (evt != null) ? evt.metaKey : false;
    },
    consume: function(evt, preventDefault){
        if (preventDefault == null || preventDefault) {
            if (evt.preventDefault) {
                evt.stopPropagation();
                evt.preventDefault();
            }
            else {
                evt.cancelBubble = true;
            }
        }
        evt.isConsumed = true;
        evt.returnValue = false;
    },
    
    
    
    LABEL_HANDLE: -1,
    
    
    
    MOUSE_DOWN: 'mouseDown',
    MOUSE_MOVE: 'mouseMove',
    MOUSE_UP: 'mouseUp',
    ACTIVATE: 'activate',
    RESIZE_START: 'resizeStart',
    RESIZE: 'resize',
    RESIZE_END: 'resizeEnd',
    MOVE_START: 'moveStart',
    MOVE: 'move',
    MOVE_END: 'moveEnd',
    MINIMIZE: 'minimize',
    NORMALIZE: 'normalize',
    MAXIMIZE: 'maximize',
    HIDE: 'hide',
    SHOW: 'show',
    CLOSE: 'close',
    DESTROY: 'destroy',
    REFRESH: 'refresh',
    SIZE: 'size',
    SELECT: 'select',
    FIRED: 'fired',
    GET: 'get',
    RECEIVE: 'receive',
    CONNECT: 'connect',
    DISCONNECT: 'disconnect',
    SUSPEND: 'suspend',
    RESUME: 'resume',
    MARK: 'mark',
    SESSION: 'session',
    ROOT: 'root',
    POST: 'post',
    OPEN: 'open',
    SAVE: 'save',
    BEFORE_ADD_VERTEX: 'beforeAddVertex',
    ADD_VERTEX: 'addVertex',
    AFTER_ADD_VERTEX: 'afterAddVertex',
    EXECUTE: 'execute',
    BEGIN_UPDATE: 'beginUpdate',
    END_UPDATE: 'endUpdate',
    BEFORE_UNDO: 'beforeUndo',
    UNDO: 'undo',
    REDO: 'redo',
    CHANGE: 'change',
    NOTIFY: 'notify',
    LAYOUT_CELLS: 'layoutCells',
    CLICK: 'click',
    SCALE: 'scale',
    TRANSLATE: 'translate',
    SCALE_AND_TRANSLATE: 'scaleAndTranslate',
    UP: 'up',
    DOWN: 'down',
    ADD: 'add',
    RESET: 'reset',
    ADD_CELLS: 'addCells',
    CELLS_ADDED: 'cellsAdded',
    MOVE_CELLS: 'moveCells',
    CELLS_MOVED: 'cellsMoved',
    RESIZE_CELLS: 'resizeCells',
    CELLS_RESIZED: 'cellsResized',
    TOGGLE_CELLS: 'toggleCells',
    CELLS_TOGGLED: 'cellsToggled',
    ORDER_CELLS: 'orderCells',
    CELLS_ORDERED: 'cellsOrdered',
    REMOVE_CELLS: 'removeCells',
    CELLS_REMOVED: 'cellsRemoved',
    GROUP_CELLS: 'groupCells',
    UNGROUP_CELLS: 'ungroupCells',
    REMOVE_CELLS_FROM_PARENT: 'removeCellsFromParent',
    FOLD_CELLS: 'foldCells',
    CELLS_FOLDED: 'cellsFolded',
    ALIGN_CELLS: 'alignCells',
    LABEL_CHANGED: 'labelChanged',
    CONNECT_CELL: 'connectCell',
    CELL_CONNECTED: 'cellConnected',
    SPLIT_EDGE: 'splitEdge',
    FLIP_EDGE: 'flipEdge',
    START_EDITING: 'startEditing',
    ADD_OVERLAY: 'addOverlay',
    REMOVE_OVERLAY: 'removeOverlay',
    UPDATE_CELL_SIZE: 'updateCellSize',
    ESCAPE: 'escape',
    CLICK: 'click',
    DOUBLE_CLICK: 'doubleClick'
};
