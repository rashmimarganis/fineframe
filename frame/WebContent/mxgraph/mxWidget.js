//mxWindow
{
    function mxWindow(title, content, x, y, width, height, minimizable, movable, replaceNode, style){
        if (content != null) {
            minimizable = (minimizable != null) ? minimizable : true;
            this.content = content;
            this.init(x, y, width, height, style);
            this.installMaximizeHandler();
            this.installMinimizeHandler();
            this.installCloseHandler();
            this.setMinimizable(minimizable);
            mxUtils.write(this.title, title || '');
            if (movable == null || movable) {
                this.installMoveHandler();
            }
            if (replaceNode != null && replaceNode.parentNode != null) {
                replaceNode.parentNode.replaceChild(this.div, replaceNode);
            }
            else {
                document.body.appendChild(this.div);
            }
        }
    };
    mxWindow.prototype = new mxEventSource();
    mxWindow.prototype.constructor = mxWindow;
    mxWindow.prototype.closeImage = mxClient.imageBasePath + 'close.gif';
    mxWindow.prototype.minimizeImage = mxClient.imageBasePath + 'minimize.gif';
    mxWindow.prototype.normalizeImage = mxClient.imageBasePath + 'normalize.gif';
    mxWindow.prototype.maximizeImage = mxClient.imageBasePath + 'maximize.gif';
    mxWindow.prototype.resizeImage = mxClient.imageBasePath + 'resize.gif';
    mxWindow.prototype.visible = false;
    mxWindow.prototype.content = false;
    mxWindow.prototype.minimumSize = new mxRectangle(0, 0, 50, 40);
    mxWindow.prototype.content = false;
    mxWindow.prototype.destroyOnClose = true;
    mxWindow.prototype.init = function(x, y, width, height, style){
        style = (style != null) ? style : 'mxWindow';
        this.div = document.createElement('div');
        this.div.className = style;
        this.div.style.left = x + 'px';
        this.div.style.top = y + 'px';
        if (!false && mxClient.WINDOW_SHADOWS) {
            this.shadow = document.createElement('div');
            this.shadow.style.background = mxConstants.SVG_SHADOWCOLOR;
            mxUtils.setOpacity(this.shadow, 70);
            this.shadow.style.position = 'absolute';
            this.shadow.style.display = 'inline';
        }
        else 
            if (false && !mxClient.WINDOW_SHADOWS) {
                this.div.style.filter = '';
            }
        this.table = document.createElement('table');
        this.table.className = style;
        if (width != null) {
            if (!false) {
                this.div.style.width = width + 'px';
            }
            this.table.style.width = width + 'px';
        }
        if (height != null) {
            if (!false) {
                this.div.style.height = height + 'px';
            }
            this.table.style.height = height + 'px';
        }
        var tbody = document.createElement('tbody');
        var tr = document.createElement('tr');
        this.title = document.createElement('td');
        this.title.className = style + 'Title';
        tr.appendChild(this.title);
        tbody.appendChild(tr);
        tr = document.createElement('tr');
        this.td = document.createElement('td');
        this.td.className = style + 'Pane';
        this.contentWrapper = document.createElement('div');
        this.contentWrapper.className = style + 'Pane';
        this.contentWrapper.style.width = '100%';
        this.contentWrapper.appendChild(this.content);
        
        if (false || this.content.nodeName.toUpperCase() != 'DIV') {
            this.contentWrapper.style.height = '100%';
        }
        this.td.appendChild(this.contentWrapper);
        tr.appendChild(this.td);
        tbody.appendChild(tr);
        this.table.appendChild(tbody);
        this.div.appendChild(this.table);
        var self = this;
        var activator = function(evt){
            self.activate();
        };
        mxEvent.addListener(this.title, 'mousedown', activator);
        mxEvent.addListener(this.table, 'mousedown', activator);
        if (this.shadow != null) {
            mxEvent.addListener(this.div, 'DOMNodeInserted', function(evt){
                var node = mxEvent.getSource(evt);
                var loadHandler = function(evt){
                    mxEvent.removeListener(node, 'load', loadHandler);
                    self.updateShadow();
                };
                mxEvent.addListener(node, 'load', loadHandler);
                self.updateShadow();
            });
        }
        this.hide();
    };
    mxWindow.prototype.setScrollable = function(scrollable){
        if (scrollable) {
            this.contentWrapper.style.overflow = 'auto'
        }
        else {
            this.contentWrapper.style.overflow = 'hidden'
        }
    };
    mxWindow.prototype.updateShadow = function(){
        if (this.shadow != null) {
            this.shadow.style.display = this.div.style.display;
            this.shadow.style.left = (parseInt(this.div.style.left) + 3) + 'px';
            this.shadow.style.top = (parseInt(this.div.style.top) + 3) + 'px';
            this.shadow.style.width = this.div.offsetWidth + 'px';
            this.shadow.style.height = this.div.offsetHeight + 'px';
            if (this.shadow.parentNode != this.div.parentNode) {
                this.div.parentNode.appendChild(this.shadow);
            }
        }
    };
    mxWindow.prototype.activate = function(){
        if (mxWindow.activeWindow != this) {
            var style = mxUtils.getCurrentStyle(this.getElement());
            var index = (style != null) ? style.zIndex : 3;
            if (mxWindow.activeWindow) {
                var elt = mxWindow.activeWindow.getElement();
                if (elt != null && elt.style != null) {
                    elt.style.zIndex = index;
                }
            }
            var oldWindow = mxWindow.activeWindow;
            this.getElement().style.zIndex = index + 1;
            mxWindow.activeWindow = this;
            this.fireEvent(mxEvent.ACTIVATE, new mxEventObject([oldWindow]));
        }
    };
    mxWindow.prototype.getElement = function(){
        return this.div;
    };
    mxWindow.prototype.fit = function(){
        mxUtils.fit(this.div);
    };
    mxWindow.prototype.isResizable = function(){
        if (this.resize != null) {
            return this.resize.style.display != 'none';
        }
        return false;
    };
    mxWindow.prototype.setResizable = function(resizable){
        if (resizable) {
            if (this.resize == null) {
                this.resize = document.createElement('img');
                this.resize.style.position = 'absolute';
                this.resize.style.bottom = '2px';
                this.resize.style.right = '2px';
                this.resize.setAttribute('src', mxClient.imageBasePath + 'resize.gif');
                this.resize.style.cursor = 'nw-resize';
                var self = this;
                mxEvent.addListener(this.resize, 'mousedown', function(evt){
                    self.activate();
                    var startX = evt.clientX;
                    var startY = evt.clientY;
                    var width = self.div.offsetWidth;
                    var height = self.div.offsetHeight;
                    
                    var dragHandler = function(evt){
                        var dx = evt.clientX - startX;
                        var dy = evt.clientY - startY;
                        self.setSize(width + dx, height + dy);
                        self.updateShadow();
                        self.fireEvent(mxEvent.RESIZE, evt);
                        mxEvent.consume(evt);
                    };
                    var dropHandler = function(evt){
                        mxEvent.removeListener(document, 'mousemove', dragHandler);
                        mxEvent.removeListener(document, 'mouseup', dropHandler);
                        self.fireEvent(mxEvent.RESIZE_END, evt);
                        mxEvent.consume(evt);
                    };
                    mxEvent.addListener(document, 'mousemove', dragHandler);
                    mxEvent.addListener(document, 'mouseup', dropHandler);
                    self.fireEvent(mxEvent.RESIZE_START, evt);
                    mxEvent.consume(evt);
                });
                this.div.appendChild(this.resize);
            }
            else {
                this.resize.style.display = 'inline';
            }
        }
        else 
            if (this.resize != null) {
                this.resize.style.display = 'none';
            }
    };
    mxWindow.prototype.setSize = function(width, height){
        width = Math.max(this.minimumSize.width, width);
        height = Math.max(this.minimumSize.height, height);
        if (!false) {
            this.div.style.width = width + 'px';
            this.div.style.height = height + 'px';
        }
        this.table.style.width = width + 'px';
        this.table.style.height = height + 'px';
        if (!false) {
            this.contentWrapper.style.height = (this.div.offsetHeight - this.title.offsetHeight - 2) + 'px';
        }
    };
    mxWindow.prototype.setMinimizable = function(minimizable){
        this.minimize.style.display = (minimizable) ? '' : 'none';
    };
    mxWindow.prototype.installMinimizeHandler = function(){
        this.minimize = document.createElement('img');
        this.minimize.setAttribute('src', this.minimizeImage);
        this.minimize.setAttribute('align', 'right');
        this.minimize.setAttribute('title', 'Minimize');
        this.minimize.style.cursor = 'pointer';
        this.minimize.style.marginRight = '1px';
        this.minimize.style.display = 'none';
        this.title.appendChild(this.minimize);
        var minimized = false;
        var maxDisplay = null;
        var height = null;
        var self = this;
        var funct = function(evt){
            self.activate();
            if (!minimized) {
                minimized = true;
                self.minimize.setAttribute('src', self.normalizeImage);
                self.minimize.setAttribute('title', 'Normalize');
                self.contentWrapper.style.display = 'none';
                maxDisplay = self.maximize.style.display;
                self.maximize.style.display = 'none';
                height = self.table.style.height;
                if (!false) {
                    self.div.style.height = self.title.offsetHeight + 'px';
                }
                self.table.style.height = self.title.offsetHeight + 'px';
                if (self.resize != null) {
                    self.resize.style.visibility = 'hidden';
                }
                self.updateShadow();
                self.fireEvent(mxEvent.MINIMIZE, evt);
            }
            else {
                minimized = false;
                self.minimize.setAttribute('src', self.minimizeImage);
                self.minimize.setAttribute('title', 'Minimize');
                self.contentWrapper.style.display = '';
                self.maximize.style.display = maxDisplay;
                if (!false) {
                    self.div.style.height = height;
                }
                self.table.style.height = height;
                if (self.resize != null) {
                    self.resize.style.visibility = 'visible';
                }
                self.updateShadow();
                self.fireEvent(mxEvent.NORMALIZE, evt);
            }
            mxEvent.consume(evt);
        };
        mxEvent.addListener(self.minimize, 'mousedown', funct);
    };
    mxWindow.prototype.setMaximizable = function(maximizable){
        this.maximize.style.display = (maximizable) ? '' : 'none';
    };
    mxWindow.prototype.installMaximizeHandler = function(){
        this.maximize = document.createElement('img');
        this.maximize.setAttribute('src', this.maximizeImage);
        this.maximize.setAttribute('align', 'right');
        this.maximize.setAttribute('title', 'Maximize');
        this.maximize.style.cursor = 'default';
        this.maximize.style.marginLeft = '1px';
        this.maximize.style.cursor = 'pointer';
        this.maximize.style.display = 'none';
        this.title.appendChild(this.maximize);
        var maximized = false;
        var x = null;
        var y = null;
        var height = null;
        var width = null;
        var self = this;
        var funct = function(evt){
            self.activate();
            if (self.maximize.style.display != 'none') {
                if (!maximized) {
                    maximized = true;
                    self.maximize.setAttribute('src', self.normalizeImage);
                    self.maximize.setAttribute('title', 'Normalize');
                    self.contentWrapper.style.display = '';
                    self.minimize.style.visibility = 'hidden';
                    x = parseInt(self.div.style.left);
                    y = parseInt(self.div.style.top);
                    height = self.table.style.height;
                    width = self.table.style.width;
                    self.div.style.left = '0px';
                    self.div.style.top = '0px';
                    if (!false) {
                        self.div.style.height = (document.body.clientHeight - 2) + 'px';
                        self.div.style.width = (document.body.clientWidth - 2) + 'px';
                    }
                    self.table.style.width = (document.body.clientWidth - 2) + 'px';
                    self.table.style.height = (document.body.clientHeight - 2) + 'px';
                    if (self.resize != null) {
                        self.resize.style.visibility = 'hidden';
                    }
                    if (self.shadow != null) {
                        self.shadow.style.display = 'none';
                    }
                    if (!false) {
                        var style = mxUtils.getCurrentStyle(self.contentWrapper);
                        if (style.overflow == 'auto' || self.resize != null) {
                            self.contentWrapper.style.height = (self.div.offsetHeight - self.title.offsetHeight - 2) + 'px';
                        }
                    }
                    self.fireEvent(mxEvent.MAXIMIZE, evt);
                }
                else {
                    maximized = false;
                    self.maximize.setAttribute('src', self.maximizeImage);
                    self.maximize.setAttribute('title', 'Maximize');
                    self.contentWrapper.style.display = '';
                    self.minimize.style.visibility = '';
                    self.div.style.left = x + 'px';
                    self.div.style.top = y + 'px';
                    if (!false) {
                        self.div.style.height = height;
                        self.div.style.width = width;
                        var style = mxUtils.getCurrentStyle(self.contentWrapper);
                        if (style.overflow == 'auto' || self.resize != null) {
                            self.contentWrapper.style.height = (self.div.offsetHeight - self.title.offsetHeight - 2) + 'px';
                        }
                    }
                    self.table.style.height = height;
                    self.table.style.width = width;
                    if (self.resize != null) {
                        self.resize.style.visibility = 'visible';
                    }
                    self.updateShadow();
                    self.fireEvent(mxEvent.NORMALIZE, evt);
                }
                mxEvent.consume(evt);
            }
        };
        mxEvent.addListener(this.maximize, 'mousedown', funct);
        mxEvent.addListener(this.title, 'dblclick', funct);
    };
    mxWindow.prototype.installMoveHandler = function(){
        this.title.style.cursor = 'move';
        var self = this;
        mxEvent.addListener(this.title, 'mousedown', function(evt){
            var startX = evt.clientX;
            var startY = evt.clientY;
            var x = self.getX();
            var y = self.getY();
            
            var dragHandler = function(evt){
                var dx = evt.clientX - startX;
                var dy = evt.clientY - startY;
                self.setLocation(x + dx, y + dy);
                self.fireEvent(mxEvent.MOVE, evt);
                mxEvent.consume(evt);
            };
            var dropHandler = function(evt){
                mxEvent.removeListener(document, 'mousemove', dragHandler);
                mxEvent.removeListener(document, 'mouseup', dropHandler);
                self.fireEvent(mxEvent.MOVE_END, evt);
                mxEvent.consume(evt);
            };
            mxEvent.addListener(document, 'mousemove', dragHandler);
            mxEvent.addListener(document, 'mouseup', dropHandler);
            self.fireEvent(mxEvent.MOVE_START, evt);
            mxEvent.consume(evt);
        });
    };
    mxWindow.prototype.setLocation = function(x, y){
        this.div.style.left = x + 'px';
        this.div.style.top = y + 'px';
        this.updateShadow();
    };
    mxWindow.prototype.getX = function(){
        return parseInt(this.div.style.left);
    };
    mxWindow.prototype.getY = function(){
        return parseInt(this.div.style.top);
    };
    mxWindow.prototype.installCloseHandler = function(){
        this.closeImg = document.createElement('img');
        this.closeImg.setAttribute('src', this.closeImage);
        this.closeImg.setAttribute('align', 'right');
        this.closeImg.setAttribute('title', 'Close');
        this.closeImg.style.marginLeft = '2px';
        this.closeImg.style.cursor = 'pointer';
        this.closeImg.style.display = 'none';
        this.title.insertBefore(this.closeImg, this.title.firstChild);
        var self = this;
        mxEvent.addListener(this.closeImg, 'mousedown', function(evt){
            self.fireEvent(mxEvent.CLOSE, evt);
            if (self.destroyOnClose) {
                self.destroy();
            }
            else {
                self.setVisible(false);
            }
            mxEvent.consume(evt);
        });
    };
    mxWindow.prototype.setImage = function(image){
        this.image = document.createElement('img');
        this.image.setAttribute('src', image);
        this.image.setAttribute('align', 'left');
        this.image.style.marginRight = '4px';
        this.image.style.marginLeft = '0px';
        this.image.style.marginTop = '-2px';
        this.title.insertBefore(this.image, this.title.firstChild);
    };
    mxWindow.prototype.setClosable = function(closable){
        this.closeImg.style.display = (closable) ? '' : 'none';
    };
    mxWindow.prototype.isVisible = function(){
        if (this.div != null) {
            return this.div.style.display != 'none';
        }
        return false;
    };
    mxWindow.prototype.setVisible = function(visible){
        if (this.div != null && this.isVisible() != visible) {
            if (visible) {
                this.show();
            }
            else {
                this.hide();
            }
        }
        this.updateShadow();
    };
    mxWindow.prototype.show = function(){
        this.div.style.display = '';
        this.activate();
        var style = mxUtils.getCurrentStyle(this.contentWrapper);
        if (!false && (style.overflow == 'auto' || this.resize != null)) {
            this.contentWrapper.style.height = (this.div.offsetHeight - this.title.offsetHeight - 2) + 'px';
        }
        this.fireEvent(mxEvent.SHOW);
    };
    mxWindow.prototype.hide = function(){
        this.div.style.display = 'none';
        this.fireEvent(mxEvent.HIDE);
    };
    mxWindow.prototype.destroy = function(){
        this.fireEvent(mxEvent.DESTROY);
        if (this.div != null) {
            mxEvent.release(this.div);
            this.div.parentNode.removeChild(this.div);
            this.div = null;
        }
        if (this.shadow != null) {
            this.shadow.parentNode.removeChild(this.shadow);
            this.shadow = null;
        }
        this.title = null;
        this.content = null;
        this.contentWrapper = null;
    };
}
//mxForm
{
    function mxForm(className){
        this.table = document.createElement('table');
        this.table.className = className;
        this.body = document.createElement('tbody');
        this.table.appendChild(this.body);
    };
    mxForm.prototype.table = null;
    mxForm.prototype.body = false;
    mxForm.prototype.getTable = function(){
        return this.table;
    };
    mxForm.prototype.addButtons = function(okFunct, cancelFunct){
        var tr = document.createElement('tr');
        var td = document.createElement('td');
        tr.appendChild(td);
        td = document.createElement('td');
        var button = document.createElement('button');
        mxUtils.write(button, mxResources.get('ok') || 'OK');
        td.appendChild(button);
        var self = this;
        mxEvent.addListener(button, 'click', function(){
            okFunct();
        });
        button = document.createElement('button');
        mxUtils.write(button, mxResources.get('cancel') || 'Cancel');
        td.appendChild(button);
        mxEvent.addListener(button, 'click', function(){
            cancelFunct();
        });
        tr.appendChild(td);
        this.body.appendChild(tr);
    };
    mxForm.prototype.addText = function(name, value){
        var input = document.createElement('input');
        input.setAttribute('type', 'text');
        input.value = value;
        return this.addField(name, input);
    };
    mxForm.prototype.addCheckbox = function(name, value){
        var input = document.createElement('input');
        input.setAttribute('type', 'checkbox');
        this.addField(name, input);
        if (value) {
            input.checked = true;
        }
        return input;
    };
    mxForm.prototype.addTextarea = function(name, value, rows){
        var input = document.createElement('textarea');
        if (true) {
            rows--;
        }
        input.setAttribute('rows', rows || 2);
        input.value = value;
        return this.addField(name, input);
    };
    mxForm.prototype.addCombo = function(name, isMultiSelect, size){
        var select = document.createElement('select');
        if (size != null) {
            select.setAttribute('size', size);
        }
        if (isMultiSelect) {
            select.setAttribute('multiple', 'true');
        }
        return this.addField(name, select);
    };
    mxForm.prototype.addOption = function(combo, label, value, isSelected){
        var option = document.createElement('option');
        mxUtils.writeln(option, label);
        option.setAttribute('value', value);
        if (isSelected) {
            option.setAttribute('selected', isSelected);
        }
        combo.appendChild(option);
    };
    mxForm.prototype.addField = function(name, input){
        var tr = document.createElement('tr');
        var td = document.createElement('td');
        mxUtils.write(td, name);
        tr.appendChild(td);
        td = document.createElement('td');
        td.appendChild(input);
        tr.appendChild(td);
        this.body.appendChild(tr);
        return input;
    };
}
//mxImage
{
    function mxImage(src, width, height){
        this.src = src;
        this.width = width;
        this.height = height;
    };
    mxImage.prototype.src = null;
    mxImage.prototype.width = null;
    mxImage.prototype.height = null;
}
//mxDivResizer
{
    function mxDivResizer(div, container){
        if (div.nodeName.toLowerCase() == 'div') {
            if (container == null) {
                container = window;
            }
            this.div = div;
            var style = mxUtils.getCurrentStyle(div);
            if (style != null) {
                this.resizeWidth = style.width == 'auto';
                this.resizeHeight = style.height == 'auto';
            }
            var self = this;
            mxEvent.addListener(container, 'resize', function(evt){
                if (!self.handlingResize) {
                    self.handlingResize = true;
                    self.resize();
                    self.handlingResize = false;
                }
            });
            this.resize();
        }
    };
    mxDivResizer.prototype.resizeWidth = true;
    mxDivResizer.prototype.resizeHeight = true;
    mxDivResizer.prototype.handlingResize = false;
    mxDivResizer.prototype.resize = function(){
        var w = this.getDocumentWidth();
        var h = this.getDocumentHeight();
        var l = parseInt(this.div.style.left);
        var r = parseInt(this.div.style.right);
        var t = parseInt(this.div.style.top);
        var b = parseInt(this.div.style.bottom);
        if (this.resizeWidth && !isNaN(l) && !isNaN(r) && l >= 0 && r >= 0 && w - r - l > 0) {
            this.div.style.width = (w - r - l) + 'px';
        }
        if (this.resizeHeight && !isNaN(t) && !isNaN(b) && t >= 0 && b >= 0 && h - t - b > 0) {
            this.div.style.height = (h - t - b) + 'px';
        }
    };
    mxDivResizer.prototype.getDocumentWidth = function(){
        return document.body.clientWidth;
    };
    mxDivResizer.prototype.getDocumentHeight = function(){
        return document.body.clientHeight;
    };
}
//mxToolbar
{
    function mxToolbar(container){
        this.container = container;
    };
    mxToolbar.prototype = new mxEventSource();
    mxToolbar.prototype.constructor = mxToolbar;
    mxToolbar.prototype.container = null;
    mxToolbar.prototype.enabled = true;
    mxToolbar.prototype.noReset = false;
    mxToolbar.prototype.updateDefaultMode = true;
    mxToolbar.prototype.addItem = function(title, icon, funct, pressedIcon, style, factoryMethod){
        var img = document.createElement((icon != null) ? 'img' : 'button');
        var initialClassName = style || ((factoryMethod != null) ? 'mxToolbarMode' : 'mxToolbarItem');
        img.className = initialClassName;
        img.setAttribute('src', icon);
        if (title != null) {
            if (icon != null) {
                img.setAttribute('title', title);
            }
            else {
                mxUtils.write(img, title);
            }
        }
        this.container.appendChild(img);
        if (funct != null) {
            mxEvent.addListener(img, 'click', funct);
        }
        var self = this;
        
        mxEvent.addListener(img, 'mousedown', function(evt){
            if (pressedIcon != null) {
                img.setAttribute('src', pressedIcon);
            }
            else {
                img.style.backgroundColor = 'gray';
            }
            if (factoryMethod != null) {
                if (self.menu == null) {
                    self.menu = new mxPopupMenu();
                    self.menu.init();
                }
                var last = self.currentImg;
                if (self.menu.isMenuShowing()) {
                    self.menu.hideMenu();
                }
                if (last != img) {
                    self.currentImg = img;
                    self.menu.factoryMethod = factoryMethod;
                    var point = new mxPoint(img.offsetLeft, img.offsetTop + img.offsetHeight);
                    self.menu.popup(point.x, point.y, null, evt);
                    if (self.menu.isMenuShowing()) {
                        img.className = initialClassName + 'Selected'
                        self.menu.hideMenu = function(){
                            mxPopupMenu.prototype.hideMenu.apply(this);
                            img.className = initialClassName;
                            self.currentImg = null;
                        };
                    }
                }
            }
        });
        var mouseHandler = function(evt){
            if (pressedIcon != null) {
                img.setAttribute('src', icon);
            }
            else {
                img.style.backgroundColor = '';
            }
        }
        mxEvent.addListener(img, 'mouseup', mouseHandler);
        mxEvent.addListener(img, 'mouseout', mouseHandler);
        return img;
    };
    mxToolbar.prototype.addCombo = function(style){
        var div = document.createElement('div');
        div.style.display = 'inline';
        div.className = 'mxToolbarComboContainer';
        var select = document.createElement('select');
        select.className = style || 'mxToolbarCombo';
        div.appendChild(select);
        this.container.appendChild(div);
        return select;
    };
    mxToolbar.prototype.addActionCombo = function(title, style){
        var select = document.createElement('select');
        select.className = style || 'mxToolbarCombo';
        this.addOption(select, title, null);
        mxEvent.addListener(select, 'change', function(evt){
            var value = select.options[select.selectedIndex];
            select.selectedIndex = 0;
            if (value.funct != null) {
                value.funct(evt);
            }
        });
        this.container.appendChild(select);
        return select;
    };
    mxToolbar.prototype.addOption = function(combo, title, value){
        var option = document.createElement('option');
        mxUtils.writeln(option, title);
        if (typeof(value) == 'function') {
            option.funct = value;
        }
        else {
            option.setAttribute('value', value);
        }
        combo.appendChild(option);
        return option;
    };
    mxToolbar.prototype.addSwitchMode = function(title, icon, funct, pressedIcon, style){
        var img = document.createElement('img');
        img.initialClassName = style || 'mxToolbarMode';
        img.className = img.initialClassName;
        img.setAttribute('src', icon);
        img.altIcon = pressedIcon;
        if (title != null) {
            img.setAttribute('title', title);
        }
        var self = this;
        mxEvent.addListener(img, 'click', function(evt){
            var tmp = self.selectedMode.altIcon;
            if (tmp != null) {
                self.selectedMode.altIcon = self.selectedMode.getAttribute('src');
                self.selectedMode.setAttribute('src', tmp);
            }
            else {
                self.selectedMode.className = self.selectedMode.initialClassName;
            }
            if (self.updateDefaultMode) {
                self.defaultMode = img;
            }
            self.selectedMode = img;
            var tmp = img.altIcon;
            if (tmp != null) {
                img.altIcon = img.getAttribute('src');
                img.setAttribute('src', tmp);
            }
            else {
                img.className = img.initialClassName + 'Selected';
            }
            self.fireEvent(mxEvent.SELECT, new mxEventObject([null]));
            funct();
        });
        this.container.appendChild(img);
        if (this.defaultMode == null) {
            this.defaultMode = img;
            this.selectedMode = img;
            var tmp = img.altIcon;
            if (tmp != null) {
                img.altIcon = img.getAttribute('src');
                img.setAttribute('src', tmp);
            }
            else {
                img.className = img.initialClassName + 'Selected';
            }
            funct();
        }
        return img;
    };
    mxToolbar.prototype.addMode = function(title, icon, funct, pressedIcon, style){
        var img = document.createElement('img');
        img.initialClassName = style || 'mxToolbarMode';
        img.className = img.initialClassName;
        img.setAttribute('src', icon);
        img.altIcon = pressedIcon;
        if (title != null) {
            img.setAttribute('title', title);
        }
        if (this.enabled) {
            var self = this;
            mxEvent.addListener(img, 'click', function(evt){
                self.selectMode(img, funct);
                self.noReset = false;
            });
            mxEvent.addListener(img, 'dblclick', function(evt){
                self.selectMode(img, funct);
                self.noReset = true;
            });
            if (this.defaultMode == null) {
                this.defaultMode = img;
                this.selectedMode = img;
                var tmp = img.altIcon;
                if (tmp != null) {
                    img.altIcon = img.getAttribute('src');
                    img.setAttribute('src', tmp);
                }
                else {
                    img.className = img.initialClassName + 'Selected';
                }
            }
        }
        this.container.appendChild(img);
        return img;
    };
    mxToolbar.prototype.selectMode = function(domNode, funct){
        if (this.selectedMode != domNode) {
            var tmp = this.selectedMode.altIcon;
            if (tmp != null) {
                this.selectedMode.altIcon = this.selectedMode.getAttribute('src');
                this.selectedMode.setAttribute('src', tmp);
            }
            else {
                this.selectedMode.className = this.selectedMode.initialClassName;
            }
            this.selectedMode = domNode;
            var tmp = this.selectedMode.altIcon;
            if (tmp != null) {
                this.selectedMode.altIcon = this.selectedMode.getAttribute('src');
                this.selectedMode.setAttribute('src', tmp);
            }
            else {
                this.selectedMode.className = this.selectedMode.initialClassName + 'Selected';
            }
            this.fireEvent(mxEvent.SELECT, new mxEventObject([funct]));
        }
    };
    mxToolbar.prototype.resetMode = function(forced){
        if ((forced || !this.noReset) && this.selectedMode != this.defaultMode) {
        
        
            this.selectMode(this.defaultMode, null);
        }
    };
    mxToolbar.prototype.addSeparator = function(icon){
        return this.addItem(null, icon, null);
    };
    mxToolbar.prototype.addBreak = function(){
        mxUtils.br(this.container);
    };
    mxToolbar.prototype.addLine = function(){
        var hr = document.createElement('hr');
        hr.style.marginRight = '6px';
        hr.setAttribute('size', '1');
        this.container.appendChild(hr);
    };
    mxToolbar.prototype.destroy = function(){
        mxEvent.release(this.container);
        this.container = null;
        this.defaultMode = null;
        this.selectedMode = null;
        if (this.menu != null) {
            this.menu.destroy();
        }
    };
}
//mxSession
{
    function mxSession(model, urlInit, urlPoll, urlNotify){
        this.model = model;
        this.urlInit = urlInit;
        this.urlPoll = urlPoll;
        this.urlNotify = urlNotify;
        if (model != null) {
            this.codec = new mxCodec();
            this.codec.lookup = function(id){
                return model.getCell(id);
            };
        }
        
        var self = this;
        model.addListener(mxEvent.NOTIFY, function(sender, evt){
            var changes = evt.getArgAt(0);
            if (changes != null && self.debug || (self.connected && !self.suspended)) {
                self.notify(self.encodeChanges(changes));
            }
        });
    };
    mxSession.prototype = new mxEventSource();
    mxSession.prototype.constructor = mxSession;
    mxSession.prototype.model = null;
    mxSession.prototype.urlInit = null;
    mxSession.prototype.urlPoll = null;
    mxSession.prototype.urlNotify = null;
    mxSession.prototype.codec = null;
    mxSession.prototype.linefeed = '\n';
    mxSession.prototype.escapePostData = true;
    mxSession.prototype.significantRemoteChanges = true;
    mxSession.prototype.sent = 0;
    mxSession.prototype.received = 0;
    mxSession.prototype.debug = false;
    mxSession.prototype.connected = false;
    mxSession.prototype.suspended = false;
    mxSession.prototype.polling = false;
    mxSession.prototype.start = function(){
        if (this.debug) {
            this.connected = true;
            this.fireEvent(mxEvent.CONNECT);
        }
        else 
            if (!this.connected) {
                var self = this;
                this.get(this.urlInit, function(req){
                    self.connected = true;
                    self.fireEvent(mxEvent.CONNECT);
                    self.poll();
                });
            }
    };
    mxSession.prototype.suspend = function(){
        if (this.connected && !this.suspended) {
            this.suspended = true;
            this.fireEvent(mxEvent.SUSPEND);
        }
    };
    mxSession.prototype.resume = function(type, attr, value){
        if (this.connected && this.suspended) {
            this.suspended = false;
            this.fireEvent(mxEvent.RESUME);
            if (!this.polling) {
                this.poll();
            }
        }
    };
    mxSession.prototype.stop = function(reason){
        if (this.connected) {
            this.connected = false;
        }
        this.fireEvent(mxEvent.DISCONNECT, new mxEventObject([reason]));
    };
    mxSession.prototype.poll = function(){
        if (this.connected && !this.suspended && this.urlPoll != null) {
            this.polling = true;
            var self = this;
            this.get(this.urlPoll, function(){
                self.poll()
            });
        }
        else {
            this.polling = false;
        }
    };
    mxSession.prototype.notify = function(xml, onLoad, onError){
        if (xml != null && xml.length > 0) {
            if (this.urlNotify != null) {
                if (this.debug) {
                    mxLog.show();
                    mxLog.debug('mxSession.notify: ' + this.urlNotify + ' xml=' + xml);
                }
                else {
                    if (this.escapePostData) {
                        xml = encodeURIComponent(xml);
                    }
                    mxUtils.post(this.urlNotify, 'xml=' + xml, onLoad, onError);
                }
            }
            this.sent += xml.length;
            this.fireEvent(mxEvent.NOTIFY, new mxEventObject([this.urlNotify, xml]));
        }
    };
    mxSession.prototype.get = function(url, onLoad, onError){
    
    
        if (typeof(mxUtils) != 'undefined') {
            var self = this;
            var onErrorWrapper = function(ex){
                if (onError != null) {
                    onError(ex);
                }
                else {
                    self.stop(ex);
                }
            };
            
            var req = mxUtils.get(url, function(req){
                if (typeof(mxUtils) != 'undefined') {
                    try {
                        if (req.isReady() && req.getStatus() != 404) {
                            self.received += req.getText().length;
                            self.fireEvent(mxEvent.GET, new mxEventObject([url, req]));
                            if (self.isValidResponse(req)) {
                                if (req.getText().length > 0) {
                                    var node = req.getDocumentElement();
                                    if (node == null) {
                                        onErrorWrapper('Invalid response: ' + req.getText());
                                    }
                                    else {
                                        self.receive(node);
                                    }
                                }
                                if (onLoad != null) {
                                    onLoad(req);
                                }
                            }
                        }
                        else {
                            onErrorWrapper('Response not ready');
                        }
                    } 
                    catch (ex) {
                        onErrorWrapper(ex);
                        throw ex;
                    }
                }
            }, function(req){
                onErrorWrapper('Transmission error');
            });
        }
    };
    mxSession.prototype.isValidResponse = function(req){
    
        return req.getText().indexOf('<?php') < 0;
    };
    mxSession.prototype.encodeChanges = function(changes){
        var xml = '';
        for (var i = 0; i < changes.length; i++) {
        
        
            var node = this.codec.encode(changes[i]);
            xml += mxUtils.getXml(node, this.linefeed);
        }
        return xml;
    };
    mxSession.prototype.receive = function(node){
        if (node != null && node.nodeType == mxConstants.NODETYPE_ELEMENT) {
            var name = node.nodeName.toLowerCase();
            if (name == 'state') {
                var tmp = node.firstChild;
                while (tmp != null) {
                    this.receive(tmp);
                    tmp = tmp.nextSibling;
                }
                
                var sid = node.getAttribute('namespace');
                this.model.prefix = sid + '-';
            }
            else 
                if (name == 'delta') {
                    var changes = this.decodeChanges(node);
                    if (changes.length > 0) {
                        var edit = this.createUndoableEdit(changes);
                        this.model.fireEvent(mxEvent.UNDO, new mxEventObject([edit]));
                        this.model.fireEvent(mxEvent.CHANGE, new mxEventObject([changes]));
                        this.fireEvent(mxEvent.FIRED, new mxEventObject([changes]));
                    }
                }
            this.fireEvent(mxEvent.RECEIVE, new mxEventObject([node]));
        }
    };
    mxSession.prototype.createUndoableEdit = function(changes){
        var edit = new mxUndoableEdit(this.model, this.significantRemoteChanges);
        edit.changes = changes;
        edit.notify = function(){
            edit.source.fireEvent(mxEvent.CHANGE, new mxEventObject([edit.changes]));
            edit.source.fireEvent(mxEvent.NOTIFY, new mxEventObject([edit.changes]));
        }
        return edit;
    };
    mxSession.prototype.decodeChanges = function(node){
        this.codec.document = node.ownerDocument;
        var changes = new Array();
        node = node.firstChild;
        while (node != null) {
            if (node.nodeType == mxConstants.NODETYPE_ELEMENT) {
            
            
            
                var change = null;
                if (node.nodeName == 'mxRootChange') {
                    var codec = new mxCodec(node.ownerDocument);
                    change = codec.decode(node);
                }
                else {
                    change = this.codec.decode(node);
                }
                if (change != null) {
                    change.model = this.model;
                    change.execute();
                    changes.push(change);
                }
            }
            node = node.nextSibling;
        }
        return changes;
    };
}
//mxUndoableEdit
{
    function mxUndoableEdit(source, significant){
        this.source = source;
        this.changes = new Array();
        this.significant = (significant != null) ? significant : true;
    };
    mxUndoableEdit.prototype.source = null;
    mxUndoableEdit.prototype.changes = null;
    mxUndoableEdit.prototype.significant = null;
    mxUndoableEdit.prototype.undone = false;
    mxUndoableEdit.prototype.redone = false;
    mxUndoableEdit.prototype.isEmpty = function(){
        return this.changes.length == 0;
    }
    mxUndoableEdit.prototype.isSignificant = function(){
        return this.significant;
    };
    mxUndoableEdit.prototype.add = function(change){
        this.changes.push(change);
    };
    mxUndoableEdit.prototype.notify = function(){
    };
    mxUndoableEdit.prototype.die = function(){
    };
    mxUndoableEdit.prototype.undo = function(){
        if (!this.undone) {
            var count = this.changes.length;
            for (var i = count - 1; i >= 0; i--) {
                var change = this.changes[i];
                if (change.execute != null) {
                    change.execute();
                }
                else 
                    if (change.undo != null) {
                        change.undo();
                    }
            }
            this.undone = true;
            this.redone = false;
        }
        this.notify();
    };
    mxUndoableEdit.prototype.redo = function(){
        if (!this.redone) {
            var count = this.changes.length;
            for (var i = 0; i < count; i++) {
                var change = this.changes[i];
                if (change.execute != null) {
                    change.execute();
                }
                else 
                    if (change.redo != null) {
                        change.redo();
                    }
            }
            this.undone = false;
            this.redone = true;
        }
        this.notify();
    };
}
//mxUndoManager
{
    function mxUndoManager(size){
        this.size = size || 100;
        this.reset();
    };
    mxUndoManager.prototype = new mxEventSource();
    mxUndoManager.prototype.constructor = mxUndoManager;
    mxUndoManager.prototype.size = null;
    mxUndoManager.prototype.history = null;
    mxUndoManager.prototype.indexOfNextAdd = 0;
    mxUndoManager.prototype.isEmpty = function(){
        return this.history.length == 0;
    };
    mxUndoManager.prototype.reset = function(){
        this.history = new Array();
        this.indexOfNextAdd = 0;
        this.fireEvent(mxEvent.RESET, new mxEventObject());
    };
    mxUndoManager.prototype.canUndo = function(){
        return this.indexOfNextAdd > 0;
    };
    mxUndoManager.prototype.undo = function(){
        while (this.indexOfNextAdd > 0) {
            var edit = this.history[--this.indexOfNextAdd];
            edit.undo();
            if (edit.isSignificant()) {
                this.fireEvent(mxEvent.UNDO, new mxEventObject([edit]));
                break;
            }
        }
    };
    mxUndoManager.prototype.canRedo = function(){
        return this.indexOfNextAdd < this.history.length;
    };
    mxUndoManager.prototype.redo = function(){
        var n = this.history.length;
        while (this.indexOfNextAdd < n) {
            var edit = this.history[this.indexOfNextAdd++];
            edit.redo();
            if (edit.isSignificant()) {
                this.fireEvent(mxEvent.REDO, new mxEventObject([edit]));
                break;
            }
        }
    };
    mxUndoManager.prototype.undoableEditHappened = function(undoableEdit){
        this.trim();
        if (this.size > 0 && this.size == this.history.length) {
            this.history.shift();
        }
        this.history.push(undoableEdit);
        this.indexOfNextAdd = this.history.length;
        this.fireEvent(mxEvent.ADD, new mxEventObject([undoableEdit]));
    };
    mxUndoManager.prototype.trim = function(){
        if (this.history.length > this.indexOfNextAdd) {
            var edits = this.history.splice(this.indexOfNextAdd, this.history.length - this.indexOfNextAdd);
            for (var i = 0; i < edits.length; i++) {
                edits[i].die();
            }
        }
    };
}
//mxPath
{
    function mxPath(format){
        this.format = format;
        this.path = new Array();
        this.translate = new mxPoint(0, 0);
    };
    mxPath.prototype.format = null;
    mxPath.prototype.translate = null;
    mxPath.prototype.path = null;
    mxPath.prototype.isVml = function(){
        return this.format == 'vml';
    };
    mxPath.prototype.getPath = function(){
        return this.path.join('');
    };
    mxPath.prototype.setTranslate = function(x, y){
        this.translate = new mxPoint(x, y);
    };
    mxPath.prototype.moveTo = function(x, y){
        if (this.isVml()) {
            this.path.push('m ', Math.floor(this.translate.x + x), ' ', Math.floor(this.translate.y + y), ' ');
        }
        else {
            this.path.push('M ', Math.floor(this.translate.x + x), ' ', Math.floor(this.translate.y + y), ' ');
        }
    };
    mxPath.prototype.lineTo = function(x, y){
        if (this.isVml()) {
            this.path.push('l ', Math.floor(this.translate.x + x), ' ', Math.floor(this.translate.y + y), ' ');
        }
        else {
            this.path.push('L ', Math.floor(this.translate.x + x), ' ', Math.floor(this.translate.y + y), ' ');
        }
    };
    mxPath.prototype.curveTo = function(x1, y1, x2, y2, x, y){
        if (this.isVml()) {
            this.path.push('c ', Math.floor(this.translate.x + x1), ' ', Math.floor(this.translate.y + y1), ' ', Math.floor(this.translate.x + x2), ' ', Math.floor(this.translate.y + y2), ' ', Math.floor(this.translate.x + x), ' ', Math.floor(this.translate.y + y), ' ');
        }
        else {
            this.path.push('C ', (this.translate.x + x1), ' ', (this.translate.y + y1), ' ', (this.translate.x + x2), ' ', (this.translate.y + y2), ' ', (this.translate.x + x), ' ', (this.translate.y + y), ' ');
        }
    };
    mxPath.prototype.write = function(string){
        this.path.push(string, ' ');
    };
    mxPath.prototype.end = function(){
        if (this.format == 'vml') {
            this.path.push('e');
        }
    };
    mxPath.prototype.close = function(){
        if (this.format == 'vml') {
            this.path.push('x e');
        }
        else {
            this.path.push('Z');
        }
    };
}
//mxPopupMenu
{
    function mxPopupMenu(factoryMethod){
        this.factoryMethod = factoryMethod;
    };
    mxPopupMenu.prototype.submenuImage = mxClient.imageBasePath + 'submenu.gif';
    mxPopupMenu.prototype.zIndex = 10006;
    mxPopupMenu.prototype.factoryMethod = true;
    mxPopupMenu.prototype.useLeftButtonForPopup = false;
    mxPopupMenu.prototype.enabled = true;
    mxPopupMenu.prototype.itemCount = 0;
    mxPopupMenu.prototype.init = function(){
        this.table = document.createElement('table');
        this.table.className = 'mxPopupMenu';
        this.tbody = document.createElement('tbody');
        this.table.appendChild(this.tbody);
        this.div = document.createElement('div');
        this.div.className = 'mxPopupMenu';
        this.div.style.display = 'inline';
        this.div.style.zIndex = this.zIndex;
        this.div.appendChild(this.table);
        if (!false && mxClient.MENU_SHADOWS) {
            this.shadow = document.createElement('div');
            this.shadow.className = 'mxPopupMenuShadow';
            this.shadow.style.zIndex = this.zIndex - 1;
            mxUtils.setOpacity(this.shadow, 70);
        }
        else 
            if (false && !mxClient.MENU_SHADOWS) {
                this.div.style.filter = '';
            }
        mxEvent.disableContextMenu(this.div);
    };
    mxPopupMenu.prototype.isEnabled = function(){
        return this.enabled;
    };
    mxPopupMenu.prototype.setEnabled = function(enabled){
        this.enabled = enabled;
    };
    mxPopupMenu.prototype.isPopupTrigger = function(me){
        return me.isPopupTrigger() || (this.useLeftButtonForPopup && mxEvent.isLeftMouseButton(me.getEvent()));
    };
    mxPopupMenu.prototype.addItem = function(title, image, funct, parent){
        parent = parent || this;
        this.itemCount++;
        var tr = document.createElement('tr');
        tr.className = 'mxPopupMenuItem';
        var col1 = document.createElement('td');
        col1.className = 'mxPopupMenuIcon';
        if (image != null) {
            var img = document.createElement('img');
            if (!false) {
                if (this.loading == null) {
                    this.loading = 0;
                }
                this.loading++;
                var self = this;
                var loader = function(){
                    mxEvent.removeListener(img, 'load', loader);
                    self.loading--;
                    if (self.loading == 0) {
                        self.showShadow();
                    }
                };
                mxEvent.addListener(img, 'load', loader);
            }
            img.src = image;
            col1.appendChild(img);
        }
        tr.appendChild(col1);
        var col2 = document.createElement('td');
        col2.className = 'mxPopupMenuItem';
        mxUtils.write(col2, title);
        col2.align = 'left';
        tr.appendChild(col2);
        var col3 = document.createElement('td');
        col3.style.width = '10px';
        col3.style.paddingRight = '6px';
        tr.appendChild(col3);
        if (parent.div == null) {
            this.createSubmenu(parent);
        }
        parent.tbody.appendChild(tr);
        var self = this;
        mxEvent.addListener(tr, 'mousedown', function(evt){
            self.eventReceiver = tr;
            if (parent.activeRow != tr && parent.activeRow != parent) {
                if (parent.activeRow != null && parent.activeRow.div.parentNode != null) {
                    self.hideSubmenu(parent);
                }
                if (tr.div != null) {
                    self.showSubmenu(parent, tr);
                    parent.activeRow = tr;
                }
            }
            mxEvent.consume(evt);
        });
        mxEvent.addListener(tr, 'mouseup', function(evt){
        
            if (self.eventReceiver == tr) {
                if (parent.activeRow != tr) {
                    self.hideMenu();
                }
                if (funct != null) {
                    funct(evt);
                }
            }
            self.eventReceiver = null;
            mxEvent.consume(evt);
        });
        mxEvent.addListener(tr, 'mousemove', function(evt){
            if (parent.activeRow != tr && parent.activeRow != parent) {
                if (parent.activeRow != null && parent.activeRow.div.parentNode != null) {
                    self.hideSubmenu(parent);
                }
            }
            if (false) {
                tr.style.backgroundColor = '#000066';
                tr.style.color = 'white';
            }
        });
        if (false) {
            mxEvent.addListener(tr, 'mouseout', function(evt){
                tr.style.backgroundColor = '';
                tr.style.color = '';
            });
        }
        return tr;
    };
    mxPopupMenu.prototype.createSubmenu = function(parent){
        parent.table = document.createElement('table');
        parent.table.className = 'mxPopupMenu';
        parent.tbody = document.createElement('tbody');
        parent.table.appendChild(parent.tbody);
        parent.div = document.createElement('div');
        parent.div.className = 'mxPopupMenu';
        parent.div.style.position = 'absolute';
        parent.div.style.display = 'inline';
        parent.div.appendChild(parent.table);
        var img = document.createElement('img');
        img.setAttribute('src', this.submenuImage);
        td = parent.firstChild.nextSibling.nextSibling;
        td.appendChild(img);
    };
    mxPopupMenu.prototype.showSubmenu = function(parent, row){
        if (row.div != null) {
            row.div.style.left = (parent.div.offsetLeft + row.offsetLeft + row.offsetWidth - 1) + 'px';
            row.div.style.top = (parent.div.offsetTop + row.offsetTop) + 'px';
            document.body.appendChild(row.div);
            var left = parseInt(row.div.offsetLeft);
            var width = parseInt(row.div.offsetWidth);
            var b = document.body;
            var d = document.documentElement;
            var right = (b.scrollLeft || d.scrollLeft) + (b.clientWidth || d.clientWidth);
            if (left + width > right) {
                row.div.style.left = (parent.div.offsetLeft - width + ((false) ? 6 : -6)) + 'px';
            }
            mxUtils.fit(row.div);
        }
    };
    mxPopupMenu.prototype.addSeparator = function(parent){
        parent = parent || this;
        var tr = document.createElement('tr');
        var col1 = document.createElement('td');
        col1.className = 'mxPopupMenuIcon';
        col1.style.padding = '0 0 0 0px';
        tr.appendChild(col1);
        var col2 = document.createElement('td');
        col2.style.padding = '0 0 0 0px';
        col2.setAttribute('colSpan', '2');
        var hr = document.createElement('hr');
        hr.setAttribute('size', '1');
        col2.appendChild(hr);
        tr.appendChild(col2);
        parent.tbody.appendChild(tr);
    };
    mxPopupMenu.prototype.popup = function(x, y, cell, evt){
        if (this.div != null && this.tbody != null && this.factoryMethod != null) {
            this.div.style.left = x + 'px';
            this.div.style.top = y + 'px';
            while (this.tbody.firstChild != null) {
                mxEvent.release(this.tbody.firstChild);
                this.tbody.removeChild(this.tbody.firstChild);
            }
            this.itemCount = 0;
            this.factoryMethod(this, cell, evt);
            if (this.itemCount > 0) {
                this.showMenu();
            }
        }
    };
    mxPopupMenu.prototype.isMenuShowing = function(){
        return this.div.parentNode == document.body;
    };
    mxPopupMenu.prototype.showMenu = function(){
        document.body.appendChild(this.div);
        mxUtils.fit(this.div);
        if (this.shadow != null) {
            if (!this.loading) {
                this.showShadow();
            }
        }
    };
    mxPopupMenu.prototype.showShadow = function(){
        if (this.shadow != null && this.div.parentNode == document.body) {
            this.shadow.style.left = (parseInt(this.div.style.left) + 3) + 'px';
            this.shadow.style.top = (parseInt(this.div.style.top) + 3) + 'px';
            this.shadow.style.width = this.div.offsetWidth + 'px';
            this.shadow.style.height = this.div.offsetHeight + 'px';
            document.body.appendChild(this.shadow);
        }
    };
    mxPopupMenu.prototype.hideMenu = function(){
        if (this.div != null) {
            if (this.div.parentNode != null) {
                this.div.parentNode.removeChild(this.div);
            }
            if (this.shadow != null) {
                if (this.shadow.parentNode != null) {
                    this.shadow.parentNode.removeChild(this.shadow);
                }
            }
            this.hideSubmenu(this);
        }
    };
    mxPopupMenu.prototype.hideSubmenu = function(parent){
        if (parent.activeRow != null) {
            this.hideSubmenu(parent.activeRow);
            if (parent.activeRow.div.parentNode != null) {
                parent.activeRow.div.parentNode.removeChild(parent.activeRow.div);
            }
            parent.activeRow = null;
        }
    };
    mxPopupMenu.prototype.destroy = function(){
        if (this.div != null) {
            mxEvent.release(this.div);
            if (this.div.parentNode != null) {
                this.div.parentNode.removeChild(this.div);
            }
            this.div = null;
        }
        if (this.shadow != null) {
            mxEvent.release(this.shadow);
            if (this.shadow.parentNode != null) {
                this.shadow.parentNode.removeChild(this.shadow);
            }
            this.shadow = null;
        }
    };
}
//mxAutoSaveManager
{
    function mxAutoSaveManager(graph){
        var self = this;
        
        this.changeHandler = function(sender, evt){
            if (self.isEnabled()) {
                self.graphModelChanged(evt.getArgAt(0));
            }
        };
        this.setGraph(graph);
    };
    mxAutoSaveManager.prototype = new mxEventSource();
    mxAutoSaveManager.prototype.constructor = mxAutoSaveManager;
    mxAutoSaveManager.prototype.graph = null;
    mxAutoSaveManager.prototype.autoSaveDelay = 10;
    mxAutoSaveManager.prototype.autoSaveThrottle = 2;
    mxAutoSaveManager.prototype.autoSaveThreshold = 5;
    mxAutoSaveManager.prototype.ignoredChanges = 0;
    mxAutoSaveManager.prototype.lastSnapshot = 0;
    mxAutoSaveManager.prototype.enabled = true;
    mxAutoSaveManager.prototype.changeHandler = null;
    mxAutoSaveManager.prototype.isEnabled = function(){
        return this.enabled;
    };
    mxAutoSaveManager.prototype.setEnabled = function(value){
        this.enabled = value;
    };
    mxAutoSaveManager.prototype.setGraph = function(graph){
        if (this.graph != null) {
            this.graph.getModel().removeListener(this.changeHandler);
        }
        this.graph = graph;
        if (this.graph != null) {
            this.graph.getModel().addListener(mxEvent.CHANGE, this.changeHandler);
        }
    };
    mxAutoSaveManager.prototype.save = function(){
    };
    mxAutoSaveManager.prototype.graphModelChanged = function(changes){
        var now = new Date().getTime();
        var dt = (now - this.lastSnapshot) / 1000;
        if (dt > this.autoSaveDelay || (this.ignoredChanges >= this.autoSaveThreshold && dt > this.autoSaveThrottle)) {
            this.save();
            this.reset();
        }
        else {
            this.ignoredChanges++;
        }
    };
    mxAutoSaveManager.prototype.reset = function(){
        this.lastSnapshot = new Date().getTime();
        this.ignoredChanges = 0;
    };
    mxAutoSaveManager.prototype.destroy = function(){
        this.setGraph(null);
    };
}
//mxShape
{
    function mxShape(){
    };
    mxShape.prototype.SVG_STROKE_TOLERANCE = 8;
    mxShape.prototype.scale = 1;
    mxShape.prototype.dialect = null;
    mxShape.prototype.mixedModeHtml = true;
    mxShape.prototype.preferModeHtml = true;
    mxShape.prototype.bounds = null;
    mxShape.prototype.points = null;
    mxShape.prototype.node = null;
    mxShape.prototype.label = null;
    mxShape.prototype.innerNode = null;
    mxShape.prototype.style = null;
    mxShape.prototype.startOffset = null;
    mxShape.prototype.endOffset = null;
    mxShape.prototype.init = function(container){
        if (this.node == null) {
            this.node = this.create(container);
            if (container != null) {
                container.appendChild(this.node);
            }
        }
        this.redraw();
        if (this.insertGradientNode != null) {
            var count = 0;
            var id = this.insertGradientNode.getAttribute('id');
            var gradient = document.getElementById(id);
            while (gradient != null && gradient.ownerSVGElement != this.node.ownerSVGElement) {
                count++;
                id = this.insertGradientNode.getAttribute('id') + '-' + count;
                gradient = document.getElementById(id);
            }
            if (gradient == null) {
                this.insertGradientNode.setAttribute('id', id);
                this.node.ownerSVGElement.appendChild(this.insertGradientNode);
                gradient = this.insertGradientNode;
            }
            if (gradient != null) {
                var tmp = (this.innerNode != null) ? this.innerNode : this.node;
                if (tmp != null) {
                    tmp.setAttribute('fill', 'url(#' + id + ')');
                }
            }
            if (this.insertGradientNode != null) {
                this.insertGradient(this.insertGradientNode);
                this.insertGradientNode = null;
            }
        }
    };
    mxShape.prototype.insertGradient = function(node){
        if (node != null) {
            var count = 0;
            var id = node.getAttribute('id');
            var gradient = document.getElementById(id);
            while (gradient != null && gradient.ownerSVGElement != this.node.ownerSVGElement) {
                count++;
                id = node.getAttribute('id') + '-' + count;
                gradient = document.getElementById(id);
            }
            if (gradient == null) {
                node.setAttribute('id', id);
                this.node.ownerSVGElement.appendChild(node);
                gradient = node;
            }
            if (gradient != null) {
                var tmp = (this.innerNode != null) ? this.innerNode : this.node;
                if (tmp != null) {
                    tmp.setAttribute('fill', 'url(#' + id + ')');
                }
            }
        }
    };
    mxShape.prototype.isMixedModeHtml = function(){
        return this.mixedModeHtml && !this.isRounded && !this.isShadow && this.gradient == null;
    };
    mxShape.prototype.create = function(container){
        var node = null;
        if (this.dialect == mxConstants.DIALECT_SVG) {
            node = this.createSvg();
        }
        else 
            if (this.dialect == mxConstants.DIALECT_STRICTHTML || (this.preferModeHtml && this.dialect == mxConstants.DIALECT_PREFERHTML) || (this.isMixedModeHtml() && this.dialect == mxConstants.DIALECT_MIXEDHTML)) {
                node = this.createHtml();
            }
            else {
                node = this.createVml();
            }
        return node;
    };
    mxShape.prototype.createHtml = function(){
        var node = document.createElement('DIV');
        this.configureHtmlShape(node);
        return node;
    };
    mxShape.prototype.destroy = function(){
        if (this.node != null) {
            mxEvent.release(this.node);
            if (this.node.parentNode != null) {
                this.node.parentNode.removeChild(this.node);
            }
            this.node = null;
        }
    };
    mxShape.prototype.apply = function(state){
        var style = state.style;
        this.style = style;
        if (style != null) {
            this.fill = mxUtils.getValue(style, mxConstants.STYLE_FILLCOLOR, this.fill);
            this.gradient = mxUtils.getValue(style, mxConstants.STYLE_GRADIENTCOLOR, this.gradient);
            this.gradientDirection = mxUtils.getValue(style, mxConstants.STYLE_GRADIENT_DIRECTION, this.gradientDirection);
            this.opacity = mxUtils.getValue(style, mxConstants.STYLE_OPACITY, this.opacity);
            this.stroke = mxUtils.getValue(style, mxConstants.STYLE_STROKECOLOR, this.stroke);
            this.strokewidth = mxUtils.getValue(style, mxConstants.STYLE_STROKEWIDTH, this.strokewidth);
            this.isShadow = mxUtils.getValue(style, mxConstants.STYLE_SHADOW, this.isShadow);
            this.isDashed = mxUtils.getValue(style, mxConstants.STYLE_DASHED, this.isDashed);
            this.spacing = mxUtils.getValue(style, mxConstants.STYLE_SPACING, this.spacing);
            this.startSize = mxUtils.getValue(style, mxConstants.STYLE_STARTSIZE, this.startSize);
            this.endSize = mxUtils.getValue(style, mxConstants.STYLE_ENDSIZE, this.endSize);
            this.isRounded = mxUtils.getValue(style, mxConstants.STYLE_ROUNDED, this.isRounded);
            this.startArrow = mxUtils.getValue(style, mxConstants.STYLE_STARTARROW, this.startArrow);
            this.endArrow = mxUtils.getValue(style, mxConstants.STYLE_ENDARROW, this.endArrow);
            this.rotation = mxUtils.getValue(style, mxConstants.STYLE_ROTATION, this.rotation);
        }
    };
    mxShape.prototype.createSvgGroup = function(shape){
        var g = document.createElementNS(mxConstants.NS_SVG, 'g');
        this.innerNode = document.createElementNS(mxConstants.NS_SVG, shape);
        this.configureSvgShape(this.innerNode);
        this.shadowNode = this.createSvgShadow(this.innerNode);
        if (this.shadowNode != null) {
            g.appendChild(this.shadowNode);
        }
        g.appendChild(this.innerNode);
        return g;
    };
    mxShape.prototype.createSvgShadow = function(node){
        if (this.isShadow && this.fill != null) {
            var shadow = node.cloneNode(true);
            shadow.setAttribute('stroke', mxConstants.SVG_SHADOWCOLOR);
            shadow.setAttribute('fill', mxConstants.SVG_SHADOWCOLOR);
            shadow.setAttribute('transform', mxConstants.SVG_SHADOWTRANSFORM);
            return shadow;
        }
        return null;
    };
    mxShape.prototype.configureHtmlShape = function(node){
        if (mxUtils.isVml(node)) {
            this.configureVmlShape(node);
        }
        else {
            node.style.position = 'absolute';
            node.style.overflow = 'hidden';
            var color = this.stroke;
            if (color != null) {
                node.style.borderColor = color;
            }
            if (this.isDashed) {
                node.style.borderStyle = 'dashed';
            }
            else 
                if (this.strokewidth > 0) {
                    node.style.borderStyle = 'solid';
                }
            node.style.borderWidth = this.strokewidth + 'px';
            color = this.fill;
            if (color != null) {
                node.style.backgroundColor = color;
            }
            else {
                node.style.background = 'url(\'' + mxClient.imageBasePath + 'transparent.gif\')';
            }
            if (this.opacity != null) {
                mxUtils.setOpacity(node, this.opacity);
            }
        }
    };
    mxShape.prototype.configureVmlShape = function(node){
        node.style.position = 'absolute';
        var color = this.stroke;
        if (color != null) {
            node.setAttribute('strokecolor', color);
        }
        else {
            node.setAttribute('stroked', 'false');
        }
        color = this.fill;
        if (color != null) {
            node.setAttribute('fillcolor', color);
            if (node.fillNode == null) {
                node.fillNode = document.createElement('v:fill');
                node.appendChild(node.fillNode);
            }
            node.fillNode.setAttribute('color', color);
            if (this.gradient != null) {
                node.fillNode.setAttribute('type', 'gradient');
                node.fillNode.setAttribute('color2', this.gradient);
                var angle = '180';
                if (this.gradientDirection == mxConstants.DIRECTION_EAST) {
                    angle = '270';
                }
                else 
                    if (this.gradientDirection == mxConstants.DIRECTION_WEST) {
                        angle = '90';
                    }
                    else 
                        if (this.gradientDirection == mxConstants.DIRECTION_NORTH) {
                            angle = '0';
                        }
                node.fillNode.setAttribute('angle', angle);
            }
            if (this.opacity != null) {
                node.fillNode.setAttribute('opacity', this.opacity + '%');
                if (this.gradient != null) {
                    node.fillNode.setAttribute('o:opacity2', this.opacity + '%');
                }
            }
        }
        else {
            node.setAttribute('filled', 'false');
            if (node.fillNode != null) {
                mxEvent.release(node.fillNode);
                node.removeChild(node.fillNode);
                node.fillNode = null;
            }
        }
        if ((this.isDashed || this.opacity != null) && this.strokeNode == null) {
            this.strokeNode = document.createElement('v:stroke');
            node.appendChild(this.strokeNode);
        }
        if (this.strokeNode != null) {
            if (this.isDashed) {
                this.strokeNode.setAttribute('dashstyle', '2 2');
            }
            else {
                this.strokeNode.setAttribute('dashstyle', 'solid');
            }
            if (this.opacity != null) {
                this.strokeNode.setAttribute('opacity', this.opacity + '%');
            }
        }
        if (this.isShadow && this.fill != null) {
            if (this.shadowNode == null) {
                this.shadowNode = document.createElement('v:shadow');
                this.shadowNode.setAttribute('on', 'true');
                node.appendChild(this.shadowNode);
            }
        }
        if (node.nodeName == 'roundrect') {
            node.setAttribute('arcsize', String(mxConstants.RECTANGLE_ROUNDING_FACTOR * 100) + '%');
        }
    }
    mxShape.prototype.configureSvgShape = function(node){
        var color = this.stroke;
        if (color != null) {
            node.setAttribute('stroke', color);
        }
        else {
            node.setAttribute('stroke', 'none');
        }
        color = this.fill;
        if (color != null) {
            if (this.gradient != null) {
                var id = this.getGradientId(color, this.gradient, this.opacity);
                if (this.gradientNode != null && this.gradientNode.getAttribute('id') != id) {
                    this.gradientNode = null;
                    node.setAttribute('fill', '');
                }
                if (this.gradientNode == null) {
                    this.gradientNode = this.createSvgGradient(id, color, this.gradient, this.opacity, node);
                    node.setAttribute('fill', 'url(#' + id + ')');
                }
            }
            else {
            
                this.gradientNode = null;
                node.setAttribute('fill', color);
            }
        }
        else {
            node.setAttribute('fill', 'none');
        }
        if (this.isDashed) {
            node.setAttribute('stroke-dasharray', '3, 3');
        }
        if (this.opacity != null) {
            node.setAttribute('fill-opacity', this.opacity / 100);
            node.setAttribute('stroke-opacity', this.opacity / 100);
        }
    };
    mxShape.prototype.getGradientId = function(start, end, opacity){
        var op = (opacity != null) ? opacity : 100;
        var dir = null;
        if (this.gradientDirection == null || this.gradientDirection == mxConstants.DIRECTION_SOUTH) {
            dir = 'south';
        }
        else 
            if (this.gradientDirection == mxConstants.DIRECTION_EAST) {
                dir = 'east';
            }
            else 
                if (this.gradientDirection == mxConstants.DIRECTION_NORTH) {
                    dir = 'north';
                }
                else 
                    if (this.gradientDirection == mxConstants.DIRECTION_WEST) {
                        dir = 'west';
                    }
        return 'mxGradient-' + start + '-' + end + '-' + op + '-' + dir;
    };
    mxShape.prototype.createSvgGradient = function(id, start, end, opacity, node){
        var op = (opacity != null) ? opacity : 100;
        var gradient = this.insertGradientNode;
        if (gradient == null) {
            var gradient = document.createElementNS(mxConstants.NS_SVG, 'linearGradient');
            gradient.setAttribute('id', id);
            gradient.setAttribute('x1', '0%');
            gradient.setAttribute('y1', '0%');
            gradient.setAttribute('x2', '0%');
            gradient.setAttribute('y2', '0%');
            if (this.gradientDirection == null || this.gradientDirection == mxConstants.DIRECTION_SOUTH) {
                gradient.setAttribute('y2', '100%');
            }
            else 
                if (this.gradientDirection == mxConstants.DIRECTION_EAST) {
                    gradient.setAttribute('x2', '100%');
                }
                else 
                    if (this.gradientDirection == mxConstants.DIRECTION_NORTH) {
                        gradient.setAttribute('y1', '100%');
                    }
                    else 
                        if (this.gradientDirection == mxConstants.DIRECTION_WEST) {
                            gradient.setAttribute('x1', '100%');
                        }
            var stop = document.createElementNS(mxConstants.NS_SVG, 'stop');
            stop.setAttribute('offset', '0%');
            stop.setAttribute('style', 'stop-color:' + start + ';stop-opacity:' + (op / 100));
            gradient.appendChild(stop);
            stop = document.createElementNS(mxConstants.NS_SVG, 'stop');
            stop.setAttribute('offset', '100%');
            stop.setAttribute('style', 'stop-color:' + end + ';stop-opacity:' + (op / 100));
            gradient.appendChild(stop);
        }
        
        this.insertGradientNode = gradient;
        return gradient;
    };
    mxShape.prototype.createPoints = function(moveCmd, lineCmd, curveCmd, isRelative){
        var offsetX = (isRelative) ? this.bounds.x : 0;
        var offsetY = (isRelative) ? this.bounds.y : 0;
        var size = mxConstants.LINE_ARCSIZE * this.scale;
        var points = moveCmd + ' ' + Math.floor(this.points[0].x - offsetX) + ' ' + Math.floor(this.points[0].y - offsetY) + ' ';
        for (var i = 1; i < this.points.length; i++) {
            var pt = this.points[i];
            var p0 = this.points[i - 1];
            if (isNaN(pt.x) || isNaN(pt.y)) {
                return null;
            }
            if (i == 1 && this.startOffset != null) {
                p0 = p0.clone();
                p0.x += this.startOffset.x;
                p0.y += this.startOffset.y;
            }
            else 
                if (i == this.points.length - 1 && this.endOffset != null) {
                    pt = pt.clone();
                    pt.x += this.endOffset.x;
                    pt.y += this.endOffset.y;
                }
            var dx = p0.x - pt.x;
            var dy = p0.y - pt.y;
            if ((this.isRounded && i < this.points.length - 1) && (dx != 0 || dy != 0) && this.scale > 0.3) {
            
            
            
                var dist = Math.sqrt(dx * dx + dy * dy);
                var nx1 = dx * Math.min(size, dist / 2) / dist;
                var ny1 = dy * Math.min(size, dist / 2) / dist;
                points += lineCmd + ' ' + Math.floor(pt.x + nx1 - offsetX) + ' ' + Math.floor(pt.y + ny1 - offsetY) + ' ';
                
                
                
                var pe = this.points[i + 1];
                dx = pe.x - pt.x;
                dy = pe.y - pt.y;
                dist = Math.max(1, Math.sqrt(dx * dx + dy * dy));
                var nx2 = dx * Math.min(size, dist / 2) / dist;
                var ny2 = dy * Math.min(size, dist / 2) / dist;
                points += curveCmd + ' ' + Math.floor(pt.x - offsetX) + ' ' + Math.floor(pt.y - offsetY) + ' ' + Math.floor(pt.x - offsetX) + ',' + Math.floor(pt.y - offsetY) + ' ' + Math.floor(pt.x + nx2 - offsetX) + ' ' + Math.floor(pt.y + ny2 - offsetY) + ' ';
            }
            else {
                points += lineCmd + ' ' + Math.floor(pt.x - offsetX) + ' ' + Math.floor(pt.y - offsetY) + ' ';
            }
        }
        return points;
    };
    mxShape.prototype.updateHtmlShape = function(node){
        if (node != null) {
            if (mxUtils.isVml(node)) {
                this.updateVmlShape(node);
            }
            else {
                node.style.borderWidth = Math.max(1, Math.floor(this.strokewidth * this.scale)) + 'px';
                if (this.bounds != null) {
                    node.style.left = Math.floor(this.bounds.x) + 'px';
                    node.style.top = Math.floor(this.bounds.y) + 'px';
                    node.style.width = Math.floor(this.bounds.width) + 'px';
                    node.style.height = Math.floor(this.bounds.height) + 'px';
                }
            }
            if (this.points != null && this.bounds != null && !mxUtils.isVml(node)) {
                if (this.divContainer == null) {
                    this.divContainer = document.createElement('div');
                    node.appendChild(this.divContainer);
                }
                node.style.borderStyle = 'none';
                while (this.divContainer.firstChild != null) {
                    mxEvent.release(this.divContainer.firstChild);
                    this.divContainer.removeChild(this.divContainer.firstChild);
                }
                if (this.points.length == 2) {
                    var p0 = this.points[0];
                    var pe = this.points[1];
                    var dx = pe.x - p0.x;
                    var dy = pe.y - p0.y;
                    if (dx == 0 || dy == 0) {
                        node.style.borderStyle = 'solid';
                    }
                    else {
                        node.style.width = Math.floor(this.bounds.width + 1) + 'px';
                        node.style.height = Math.floor(this.bounds.height + 1) + 'px';
                        var length = Math.sqrt(dx * dx + dy * dy);
                        var dotCount = 1 + (length / (20 * this.scale));
                        var nx = dx / dotCount;
                        var ny = dy / dotCount;
                        var x = p0.x - this.bounds.x;
                        var y = p0.y - this.bounds.y;
                        for (var i = 0; i < dotCount; i++) {
                            var tmp = document.createElement('DIV');
                            tmp.style.position = 'absolute';
                            tmp.style.overflow = 'hidden';
                            tmp.style.left = Math.floor(x) + 'px';
                            tmp.style.top = Math.floor(y) + 'px';
                            tmp.style.width = Math.max(1, 2 * this.scale) + 'px';
                            tmp.style.height = Math.max(1, 2 * this.scale) + 'px';
                            tmp.style.backgroundColor = this.stroke;
                            this.divContainer.appendChild(tmp);
                            x += nx;
                            y += ny;
                        }
                    }
                }
                else 
                    if (this.points.length == 3) {
                        var mid = this.points[1];
                        var n = '0';
                        var s = '1';
                        var w = '0';
                        var e = '1';
                        if (mid.x == this.bounds.x) {
                            e = '0';
                            w = '1';
                        }
                        if (mid.y == this.bounds.y) {
                            n = '1';
                            s = '0';
                        }
                        node.style.borderStyle = 'solid';
                        node.style.borderWidth = n + ' ' + e + ' ' + s + ' ' + w + 'px';
                    }
                    else {
                        node.style.width = Math.floor(this.bounds.width + 1) + 'px';
                        node.style.height = Math.floor(this.bounds.height + 1) + 'px';
                        var last = this.points[0];
                        for (var i = 1; i < this.points.length; i++) {
                            var next = this.points[i];
                            var tmp = document.createElement('DIV');
                            tmp.style.position = 'absolute';
                            tmp.style.overflow = 'hidden';
                            tmp.style.borderColor = this.stroke;
                            tmp.style.borderStyle = 'solid';
                            tmp.style.borderWidth = '1 0 0 1px';
                            var x = Math.min(next.x, last.x) - this.bounds.x;
                            var y = Math.min(next.y, last.y) - this.bounds.y;
                            var w = Math.max(1, Math.abs(next.x - last.x));
                            var h = Math.max(1, Math.abs(next.y - last.y));
                            tmp.style.left = x + 'px';
                            tmp.style.top = y + 'px';
                            tmp.style.width = w + 'px';
                            tmp.style.height = h + 'px';
                            this.divContainer.appendChild(tmp);
                            last = next;
                        }
                    }
            }
        }
    };
    mxShape.prototype.updateVmlShape = function(node){
        node.setAttribute('strokeweight', this.strokewidth * this.scale);
        if (this.bounds != null) {
            node.style.left = Math.floor(this.bounds.x) + 'px';
            node.style.top = Math.floor(this.bounds.y) + 'px';
            node.style.width = Math.floor(this.bounds.width) + 'px';
            node.style.height = Math.floor(this.bounds.height) + 'px';
            if (this.points == null) {
                if (this.rotation != null && this.rotation != 0) {
                    node.style.rotation = this.rotation;
                }
                else 
                    if (node.style.rotation != null) {
                        node.style.rotation = '';
                    }
            }
        }
        if (this.points != null) {
            if (node.nodeName == 'polyline' && node.points != null) {
                var points = '';
                for (var i = 0; i < this.points.length; i++) {
                    points += this.points[i].x + ',' + this.points[i].y + ' ';
                }
                node.points.value = points;
                node.style.left = null;
                node.style.top = null;
                node.style.width = null;
                node.style.height = null;
            }
            else 
                if (this.bounds != null) {
                    this.node.setAttribute('coordsize', Math.floor(this.bounds.width) + ',' + Math.floor(this.bounds.height));
                    var points = this.createPoints('m', 'l', 'c', true);
                    
                    
                    
                    {
                    
                    
                    
                        {
                        
                        }
                        
                    }
                    node.setAttribute('path', points + ' e');
                }
        }
    };
    mxShape.prototype.updateSvgShape = function(node){
        var strokeWidth = Math.max(1, this.strokewidth * this.scale);
        node.setAttribute('stroke-width', strokeWidth);
        if (this.points != null && this.points[0] != null) {
            var d = this.createPoints('M', 'L', 'C', false);
            if (d != null) {
                node.setAttribute('d', d);
                
                
                
                {
                
                
                
                    {
                    }
                }
                node.removeAttribute('x');
                node.removeAttribute('y');
                node.removeAttribute('width');
                node.removeAttribute('height');
            }
        }
        else 
            if (this.bounds != null) {
                node.setAttribute('x', this.bounds.x);
                node.setAttribute('y', this.bounds.y);
                var w = this.bounds.width;
                var h = this.bounds.height;
                node.setAttribute('width', w);
                node.setAttribute('height', h);
                if (this.isRounded) {
                    var r = Math.min(w * mxConstants.RECTANGLE_ROUNDING_FACTOR, h * mxConstants.RECTANGLE_ROUNDING_FACTOR);
                    node.setAttribute('rx', r);
                    node.setAttribute('ry', r);
                }
                this.updateSvgTransform(node, node == this.shadowNode);
            }
    };
    mxShape.prototype.updateSvgTransform = function(node, shadow){
        if (this.rotation != null && this.rotation != 0) {
            var cx = this.bounds.x + this.bounds.width / 2;
            var cy = this.bounds.y + this.bounds.height / 2;
            if (shadow) {
                node.setAttribute('transform', 'rotate(' + this.rotation + ',' + cx + ',' + cy + ') ' + mxConstants.SVG_SHADOWTRANSFORM);
            }
            else {
                node.setAttribute('transform', 'rotate(' + this.rotation + ',' + cx + ',' + cy + ')');
            }
        }
        else {
            if (shadow) {
                node.setAttribute('transform', mxConstants.SVG_SHADOWTRANSFORM);
            }
            else {
                node.removeAttribute('transform');
            }
        }
    };
    mxShape.prototype.reconfigure = function(){
        if (this.dialect == mxConstants.DIALECT_SVG) {
            if (this.innerNode != null) {
                this.configureSvgShape(this.innerNode);
            }
            else {
                this.configureSvgShape(this.node);
            }
            if (this.insertGradientNode != null) {
                this.insertGradient(this.insertGradientNode);
                this.insertGradientNode = null;
            }
        }
        else 
            if (mxUtils.isVml(this.node)) {
                this.configureVmlShape(this.node);
            }
            else {
                this.configureHtmlShape(this.node);
            }
    };
    mxShape.prototype.redraw = function(){
        if (this.dialect == mxConstants.DIALECT_SVG) {
            this.redrawSvg();
        }
        else 
            if (mxUtils.isVml(this.node)) {
                this.redrawVml();
            }
            else {
                this.redrawHtml();
            }
    };
    mxShape.prototype.redrawSvg = function(){
        if (this.innerNode != null) {
            this.updateSvgShape(this.innerNode);
            if (this.shadowNode != null) {
                this.updateSvgShape(this.shadowNode);
            }
        }
        else {
            this.updateSvgShape(this.node);
        }
    };
    mxShape.prototype.redrawVml = function(){
        this.updateVmlShape(this.node);
    };
    mxShape.prototype.redrawHtml = function(){
        this.updateHtmlShape(this.node);
    };
    mxShape.prototype.createPath = function(arg){
        var x = this.bounds.x;
        var y = this.bounds.y;
        var w = this.bounds.width;
        var h = this.bounds.height;
        var path = null;
        if (this.dialect == mxConstants.DIALECT_SVG) {
            path = new mxPath('svg');
            path.setTranslate(x, y);
        }
        else {
            path = new mxPath('vml');
        }
        this.redrawPath(path, x, y, w, h, arg);
        return path.getPath();
    };
    mxShape.prototype.redrawPath = function(path, x, y, w, h){
    };
}
//mxActor
{
    function mxActor(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxActor.prototype = new mxShape();
    mxActor.prototype.constructor = mxActor;
    mxActor.prototype.mixedModeHtml = false;
    mxActor.prototype.preferModeHtml = false;
    mxActor.prototype.createVml = function(){
        var node = document.createElement('v:shape');
        this.configureVmlShape(node);
        return node;
    };
    mxActor.prototype.redrawVml = function(){
        this.updateVmlShape(this.node);
        var w = Math.floor(this.bounds.width);
        var h = Math.floor(this.bounds.height);
        var s = this.strokewidth * this.scale;
        this.node.setAttribute('coordsize', w + ',' + h);
        this.node.setAttribute('strokeweight', s);
        var d = this.createPath();
        this.node.setAttribute('path', d);
    };
    mxActor.prototype.createSvg = function(){
        return this.createSvgGroup('path');
    };
    mxActor.prototype.redrawSvg = function(){
        var strokeWidth = Math.max(1, this.strokewidth * this.scale);
        this.innerNode.setAttribute('stroke-width', strokeWidth);
        var d = this.createPath();
        this.innerNode.setAttribute('d', d);
        this.updateSvgTransform(this.innerNode, false);
        if (this.shadowNode != null) {
            this.shadowNode.setAttribute('stroke-width', strokeWidth);
            this.shadowNode.setAttribute('d', d);
            this.updateSvgTransform(this.shadowNode, true);
        }
    };
    mxActor.prototype.redrawPath = function(path, x, y, w, h){
        var width = w / 3;
        path.moveTo(0, h);
        path.curveTo(0, 3 * h / 5, 0, 2 * h / 5, w / 2, 2 * h / 5);
        path.curveTo(w / 2 - width, 2 * h / 5, w / 2 - width, 0, w / 2, 0);
        path.curveTo(w / 2 + width, 0, w / 2 + width, 2 * h / 5, w / 2, 2 * h / 5);
        path.curveTo(w, 2 * h / 5, w, 3 * h / 5, w, h);
        path.close();
    };
}
//mxCloud
{
    function mxCloud(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxCloud.prototype = new mxActor();
    mxCloud.prototype.constructor = mxActor;
    mxCloud.prototype.redrawPath = function(path, x, y, w, h){
        path.moveTo(0.25 * w, 0.25 * h);
        path.curveTo(0.05 * w, 0.25 * h, 0, 0.5 * h, 0.16 * w, 0.55 * h);
        path.curveTo(0, 0.66 * h, 0.18 * w, 0.9 * h, 0.31 * w, 0.8 * h);
        path.curveTo(0.4 * w, h, 0.7 * w, h, 0.8 * w, 0.8 * h);
        path.curveTo(w, 0.8 * h, w, 0.6 * h, 0.875 * w, 0.5 * h);
        path.curveTo(w, 0.3 * h, 0.8 * w, 0.1 * h, 0.625 * w, 0.2 * h);
        path.curveTo(0.5 * w, 0.05 * h, 0.3 * w, 0.05 * h, 0.25 * w, 0.25 * h);
        path.close();
    };
}
//mxRectangleShape
{
    function mxRectangleShape(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxRectangleShape.prototype = new mxShape();
    mxRectangleShape.prototype.constructor = mxRectangleShape;
    mxRectangleShape.prototype.createHtml = function(){
        var node = document.createElement('DIV');
        this.configureHtmlShape(node);
        return node;
    };
    mxRectangleShape.prototype.createVml = function(){
        var name = (this.isRounded) ? 'v:roundrect' : 'v:rect';
        var node = document.createElement(name);
        this.configureVmlShape(node);
        return node;
    };
    mxRectangleShape.prototype.createSvg = function(){
        var node = this.createSvgGroup('rect');
        
        if (this.strokewidth * this.scale >= 1 && !this.isRounded) {
            this.innerNode.setAttribute('shape-rendering', 'optimizeSpeed');
        }
        return node;
    };
}
//mxEllipse
{
    function mxEllipse(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxEllipse.prototype = new mxShape();
    mxEllipse.prototype.constructor = mxEllipse;
    mxEllipse.prototype.mixedModeHtml = false;
    mxEllipse.prototype.preferModeHtml = false;
    mxEllipse.prototype.createVml = function(){
    
    
        var node = document.createElement('v:arc');
        node.setAttribute('startangle', '0');
        node.setAttribute('endangle', '360');
        this.configureVmlShape(node);
        return node;
    };
    mxEllipse.prototype.createSvg = function(){
        return this.createSvgGroup('ellipse');
    };
    mxEllipse.prototype.redrawSvg = function(){
        this.updateSvgNode(this.innerNode);
        this.updateSvgNode(this.shadowNode);
    };
    mxEllipse.prototype.updateSvgNode = function(node){
        if (node != null) {
            var strokeWidth = Math.max(1, this.strokewidth * this.scale);
            node.setAttribute('stroke-width', strokeWidth);
            node.setAttribute('cx', this.bounds.x + this.bounds.width / 2);
            node.setAttribute('cy', this.bounds.y + this.bounds.height / 2);
            node.setAttribute('rx', this.bounds.width / 2);
            node.setAttribute('ry', this.bounds.height / 2);
        }
    };
}
//mxDoubleEllipse
{
    function mxDoubleEllipse(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxDoubleEllipse.prototype = new mxShape();
    mxDoubleEllipse.prototype.constructor = mxDoubleEllipse;
    mxDoubleEllipse.prototype.mixedModeHtml = false;
    mxDoubleEllipse.prototype.preferModeHtml = false;
    mxDoubleEllipse.prototype.createVml = function(){
        var node = document.createElement('v:group');
        this.background = document.createElement('v:arc');
        this.background.setAttribute('startangle', '0');
        this.background.setAttribute('endangle', '360');
        this.configureVmlShape(this.background);
        node.appendChild(this.background);
        this.label = this.background;
        this.isShadow = false;
        this.fill = null;
        this.foreground = document.createElement('v:oval');
        this.configureVmlShape(this.foreground);
        node.appendChild(this.foreground);
        this.stroke = null;
        this.configureVmlShape(node);
        return node;
    };
    mxDoubleEllipse.prototype.redrawVml = function(){
        var x = Math.floor(this.bounds.x);
        var y = Math.floor(this.bounds.y);
        var w = Math.floor(this.bounds.width);
        var h = Math.floor(this.bounds.height);
        var s = this.strokewidth * this.scale;
        this.updateVmlShape(this.node);
        this.node.setAttribute('coordsize', w + ',' + h);
        this.updateVmlShape(this.background);
        this.background.setAttribute('strokeweight', s);
        this.background.style.top = '0px';
        this.background.style.left = '0px';
        this.updateVmlShape(this.foreground);
        this.foreground.setAttribute('strokeweight', s);
        var inset = 3 + s;
        this.foreground.style.top = inset + 'px';
        this.foreground.style.left = inset + 'px';
        this.foreground.style.width = Math.max(0, w - 2 * inset) + 'px';
        this.foreground.style.height = Math.max(0, h - 2 * inset) + 'px';
    };
    mxDoubleEllipse.prototype.createSvg = function(){
        var g = this.createSvgGroup('ellipse');
        this.foreground = document.createElementNS(mxConstants.NS_SVG, 'ellipse');
        if (this.stroke != null) {
            this.foreground.setAttribute('stroke', this.stroke);
        }
        else {
            this.foreground.setAttribute('stroke', 'none');
        }
        this.foreground.setAttribute('fill', 'none');
        g.appendChild(this.foreground);
        return g;
    };
    mxDoubleEllipse.prototype.redrawSvg = function(){
        var s = this.strokewidth * this.scale;
        this.updateSvgNode(this.innerNode);
        this.updateSvgNode(this.shadowNode);
        this.updateSvgNode(this.foreground, 3 * this.scale + s);
    };
    mxDoubleEllipse.prototype.updateSvgNode = function(node, inset){
        inset = (inset != null) ? inset : 0;
        if (node != null) {
            var strokeWidth = Math.max(1, this.strokewidth * this.scale);
            node.setAttribute('stroke-width', strokeWidth);
            node.setAttribute('cx', this.bounds.x + this.bounds.width / 2);
            node.setAttribute('cy', this.bounds.y + this.bounds.height / 2);
            node.setAttribute('rx', this.bounds.width / 2 - inset);
            node.setAttribute('ry', this.bounds.height / 2 - inset);
        }
    };
}
//mxRhombus
{
    function mxRhombus(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxRhombus.prototype = new mxShape();
    mxRhombus.prototype.constructor = mxRhombus;
    mxRhombus.prototype.mixedModeHtml = false;
    mxRhombus.prototype.preferModeHtml = false;
    mxRhombus.prototype.createHtml = function(){
        var node = null;
        if (mxClient.IS_CANVAS) {
            node = document.createElement('CANVAS');
            this.configureHtmlShape(node);
            node.style.borderStyle = 'none';
        }
        else {
            node = document.createElement('DIV');
            this.configureHtmlShape(node);
        }
        return node;
    };
    mxRhombus.prototype.createVml = function(){
        var node = document.createElement('v:shape');
        this.configureVmlShape(node);
        return node;
    };
    mxRhombus.prototype.createSvg = function(){
        return this.createSvgGroup('path');
    }
    
    
    
    mxRhombus.prototype.redrawVml = function(){
        this.node.setAttribute('strokeweight', this.strokewidth * this.scale);
        this.updateVmlShape(this.node);
        var x = 0;
        var y = 0;
        var w = Math.floor(this.bounds.width);
        var h = Math.floor(this.bounds.height);
        this.node.setAttribute('coordsize', w + ',' + h);
        var points = 'm ' + Math.floor(x + w / 2) + ' ' + y + ' l ' + (x + w) + ' ' + Math.floor(y + h / 2) + ' l ' + Math.floor(x + w / 2) + ' ' + (y + h) + ' l ' + x + ' ' + Math.floor(y + h / 2);
        this.node.setAttribute('path', points + ' x e');
    };
    mxRhombus.prototype.redrawHtml = function(){
        if (this.node.nodeName == 'CANVAS') {
            this.redrawCanvas();
        }
        else {
            this.updateHtmlShape(this.node);
        }
    };
    mxRhombus.prototype.redrawCanvas = function(){
        this.updateHtmlShape(this.node);
        var x = 0;
        var y = 0;
        var w = this.bounds.width;
        var h = this.bounds.height;
        this.node.setAttribute('width', w);
        this.node.setAttribute('height', h);
        if (!this.isRepaintNeeded) {
            var ctx = this.node.getContext('2d');
            ctx.clearRect(0, 0, w, h);
            ctx.beginPath();
            ctx.moveTo(x + w / 2, y);
            ctx.lineTo(x + w, y + h / 2);
            ctx.lineTo(x + w / 2, y + h);
            ctx.lineTo(x, y + h / 2);
            ctx.lineTo(x + w / 2, y);
            if (this.node.style.backgroundColor != 'transparent') {
                ctx.fillStyle = this.node.style.backgroundColor;
                ctx.fill();
            }
            if (this.node.style.borderColor != null) {
                ctx.strokeStyle = this.node.style.borderColor;
                ctx.stroke();
            }
            this.isRepaintNeeded = false;
        }
    };
    mxRhombus.prototype.redrawSvg = function(){
        this.updateSvgNode(this.innerNode);
        if (this.shadowNode != null) {
            this.updateSvgNode(this.shadowNode);
        }
    };
    mxRhombus.prototype.updateSvgNode = function(node){
        var strokeWidth = Math.max(1, this.strokewidth * this.scale);
        node.setAttribute('stroke-width', strokeWidth);
        var x = this.bounds.x;
        var y = this.bounds.y;
        var w = this.bounds.width;
        var h = this.bounds.height;
        var d = 'M ' + (x + w / 2) + ' ' + y + ' L ' + (x + w) + ' ' + (y + h / 2) + ' L ' + (x + w / 2) + ' ' + (y + h) + ' L ' + x + ' ' + (y + h / 2) + ' Z ';
        node.setAttribute('d', d);
        this.updateSvgTransform(node, node == this.shadowNode);
    };
}
//mxPolyline
{
    function mxPolyline(points, stroke, strokewidth){
        this.points = points;
        this.stroke = stroke || 'black';
        this.strokewidth = strokewidth || 1;
    };
    mxPolyline.prototype = new mxShape();
    mxPolyline.prototype.constructor = mxPolyline;
    mxPolyline.prototype.create = function(){
        var node = null;
        if (this.dialect == mxConstants.DIALECT_SVG) {
            node = this.createSvg();
        }
        else 
            if (this.dialect == mxConstants.DIALECT_STRICTHTML || (this.dialect == mxConstants.DIALECT_PREFERHTML && this.points != null && this.points.length > 0)) {
                node = document.createElement('DIV');
                this.configureHtmlShape(node);
                node.style.borderStyle = 'none';
                node.style.background = '';
            }
            else {
                node = document.createElement('v:polyline');
                this.configureVmlShape(node);
                var strokeNode = document.createElement('v:stroke');
                if (this.opacity != null) {
                    strokeNode.setAttribute('opacity', this.opacity + '%');
                }
                node.appendChild(strokeNode);
            }
        return node;
    };
    mxPolyline.prototype.createSvg = function(){
        var g = this.createSvgGroup('path');
        
        
        
        var color = this.innerNode.getAttribute('stroke');
        this.pipe = document.createElementNS(mxConstants.NS_SVG, 'path');
        this.pipe.setAttribute('stroke', color);
        this.pipe.setAttribute('visibility', 'hidden');
        this.pipe.setAttribute('pointer-events', 'stroke');
        g.appendChild(this.pipe);
        return g;
    };
    mxPolyline.prototype.redrawSvg = function(){
        this.updateSvgShape(this.innerNode);
        var d = this.innerNode.getAttribute('d')
        if (d != null) {
            this.pipe.setAttribute('d', d);
            var strokeWidth = this.strokewidth * this.scale;
            if (mxConstants.SVG_CRISP_EDGES && strokeWidth == Math.floor(strokeWidth) && !this.isRounded) {
                this.innerNode.setAttribute('shape-rendering', 'optimizeSpeed');
            }
            else {
                this.innerNode.setAttribute('shape-rendering', 'auto');
            }
            this.pipe.setAttribute('stroke-width', strokeWidth + mxShape.prototype.SVG_STROKE_TOLERANCE);
        }
    };
}
//mxArrow
{
    function mxArrow(points, fill, stroke, strokewidth, arrowWidth, spacing, endSize){
        this.points = points;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
        this.arrowWidth = arrowWidth || mxConstants.ARROW_WIDTH;
        this.spacing = spacing || mxConstants.ARROW_SPACING;
        this.endSize = endSize || mxConstants.ARROW_SIZE;
    };
    mxArrow.prototype = new mxShape();
    mxArrow.prototype.constructor = mxArrow;
    mxArrow.prototype.mixedModeHtml = false;
    mxArrow.prototype.preferModeHtml = false;
    mxArrow.prototype.DEG_PER_RAD = 57.2957795;
    mxArrow.prototype.createVml = function(){
        var node = document.createElement('v:polyline');
        this.configureVmlShape(node);
        return node;
    };
    mxArrow.prototype.redrawVml = function(){
        this.node.setAttribute('strokeweight', this.strokewidth * this.scale);
        if (this.points != null) {
            var spacing = this.spacing * this.scale;
            var width = this.arrowWidth * this.scale;
            var arrow = this.endSize * this.scale;
            var p0 = this.points[0];
            var pe = this.points[this.points.length - 1];
            var dx = pe.x - p0.x;
            var dy = pe.y - p0.y;
            var dist = Math.sqrt(dx * dx + dy * dy);
            var length = dist - 2 * spacing - arrow;
            var nx = dx / dist;
            var ny = dy / dist;
            var basex = length * nx;
            var basey = length * ny;
            var floorx = width * ny / 3;
            var floory = -width * nx / 3;
            var p0x = p0.x - floorx / 2 + spacing * nx;
            var p0y = p0.y - floory / 2 + spacing * ny;
            var p1x = p0x + floorx;
            var p1y = p0y + floory;
            var p2x = p1x + basex;
            var p2y = p1y + basey;
            var p3x = p2x + floorx;
            var p3y = p2y + floory;
            var p5x = p3x - 3 * floorx;
            var p5y = p3y - 3 * floory;
            this.node.points.value = p0x + ',' + p0y + ',' + p1x + ',' + p1y + ',' + p2x + ',' + p2y + ',' + p3x + ',' + p3y + ',' + (pe.x - spacing * nx) + ',' + (pe.y - spacing * ny) + ',' + p5x + ',' + p5y + ',' + (p5x + floorx) + ',' + (p5y + floory) + ',' + p0x + ',' + p0y;
        }
    };
    mxArrow.prototype.createSvg = function(){
        var node = document.createElementNS(mxConstants.NS_SVG, 'polygon');
        this.configureSvgShape(node);
        return node;
    };
    mxArrow.prototype.redrawSvg = function(){
        if (this.points != null) {
            var strokeWidth = Math.max(1, this.strokewidth * this.scale);
            this.node.setAttribute('stroke-width', strokeWidth);
            var p0 = this.points[0];
            var pe = this.points[this.points.length - 1];
            var tdx = pe.x - p0.x;
            var tdy = pe.y - p0.y;
            var dist = Math.sqrt(tdx * tdx + tdy * tdy);
            var offset = this.spacing * this.scale;
            var h = Math.min(25, Math.max(20, dist / 5)) * this.scale;
            var w = dist - 2 * offset;
            var x = p0.x + offset;
            var y = p0.y - h / 2;
            var dx = h;
            var dy = h * 0.3;
            var right = x + w;
            var bottom = y + h;
            var points = x + ',' + (y + dy) + ' ' + (right - dx) + ',' + (y + dy) + ' ' + (right - dx) + ',' + y + ' ' + right + ',' + (y + h / 2) + ' ' + (right - dx) + ',' + bottom + ' ' + (right - dx) + ',' + (bottom - dy) + ' ' + x + ',' + (bottom - dy);
            this.node.setAttribute('points', points);
            var dx = pe.x - p0.x;
            var dy = pe.y - p0.y;
            var theta = Math.atan(dy / dx) * this.DEG_PER_RAD;
            if (dx < 0) {
                theta -= 180;
            }
            this.node.setAttribute('transform', 'rotate(' + theta + ',' + p0.x + ',' + p0.y + ')');
        }
    };
}
//mxText
{
    function mxText(value, bounds, align, valign, color, family, size, fontStyle, spacing, spacingTop, spacingRight, spacingBottom, spacingLeft, isRotate, background, border, useTableBounds, isAbsolute, isWrapping, isClipping){
        this.value = value;
        this.bounds = bounds;
        this.color = color || 'black';
        this.align = align || 0;
        this.valign = valign || 0;
        this.family = family || mxConstants.DEFAULT_FONTFAMILY;
        this.size = size || mxConstants.DEFAULT_FONTSIZE;
        this.fontStyle = fontStyle || 0;
        this.spacing = parseInt(spacing || 2);
        this.spacingTop = this.spacing + parseInt(spacingTop || 0);
        this.spacingRight = this.spacing + parseInt(spacingRight || 0);
        this.spacingBottom = this.spacing + parseInt(spacingBottom || 0);
        this.spacingLeft = this.spacing + parseInt(spacingLeft || 0);
        this.isRotate = isRotate || false;
        this.background = background;
        this.border = border;
        this.useTableBounds = (useTableBounds != null) ? useTableBounds : true;
        this.isAbsolute = (isAbsolute != null) ? isAbsolute : false;
        this.isWrapping = (isWrapping != null) ? isWrapping : false;
        this.isClipping = (isClipping != null) ? isClipping : false;
    };
    mxText.prototype = new mxShape();
    mxText.prototype.constructor = mxText;
    mxText.prototype.ENABLE_FOREIGNOBJECT = false;
    mxText.prototype.isStyleSet = function(style){
        return (this.fontStyle & style) == style;
    }
    mxText.prototype.create = function(container){
        var node = null;
        if (this.dialect == mxConstants.DIALECT_SVG && (!true || !this.ENABLE_FOREIGNOBJECT)) {
            node = this.createSvg();
        }
        else 
            if (this.dialect == mxConstants.DIALECT_STRICTHTML || this.dialect == mxConstants.DIALECT_PREFERHTML || !mxUtils.isVml(container)) {
                if (this.dialect == mxConstants.DIALECT_SVG) {
                    node = this.createForeignObject();
                }
                else {
                    container.style.overflow = 'visible';
                    node = this.createHtml();
                }
            }
            else {
                node = this.createVml();
            }
        return node;
    };
    mxText.prototype.createForeignObject = function(){
        var node = document.createElementNS(mxConstants.NS_SVG, 'foreignObject');
        node.style.cursor = 'default';
        return node;
    };
    mxText.prototype.createHtml = function(){
        var node = document.createElement('DIV');
        node.style.cursor = 'default';
        return node;
    };
    mxText.prototype.createVml = function(){
        var node = document.createElement('v:textbox');
        node.inset = '0px,0px,0px,0px';
        return node;
    };
    mxText.prototype.redrawHtml = function(){
        this.redrawVml();
    };
    mxText.prototype.redrawVml = function(){
        if (this.value != null) {
            var scale = (false) ? 1 : this.scale;
            var table = document.createElement('table');
            table.style.borderCollapse = 'collapse';
            var tbody = document.createElement('tbody');
            var row = document.createElement('tr');
            var td = document.createElement('td');
            this.node.style.overflow = (this.isClipping) ? 'hidden' : 'visible';
            if (!mxUtils.isVml(this.node)) {
                this.node.style.marginLeft = '0px';
                this.node.style.marginTop = '0px';
            }
            else {
                this.node.inset = '0px,0px,0px,0px';
            }
            if (this.isAbsolute || (this.bounds.width == 0 && this.bounds.height == 0)) {
                if (mxUtils.isVml(this.node)) {
                    var x0 = parseInt(this.node.parentNode.style.left);
                    var y0 = parseInt(this.node.parentNode.style.top);
                    this.node.inset = (this.bounds.x - x0) + 'px,' + (this.bounds.y - y0) + 'px,0px,0px';
                }
                else {
                    this.node.style.position = 'absolute';
                    this.node.style.left = this.bounds.x + 'px';
                    this.node.style.top = this.bounds.y + 'px';
                    if (mxUtils.isVml(this.node.parentNode) || false) {
                        this.node.style.left = (this.bounds.x - parseInt(this.node.parentNode.style.left) || 0) + 'px';
                        this.node.style.top = (this.bounds.y - parseInt(this.node.parentNode.style.top) || 0) + 'px';
                    }
                    if (this.bounds.width > 0 || this.bounds.height > 0) {
                        this.node.style.width = this.bounds.width + 'px';
                        this.node.style.height = this.bounds.height + 'px';
                        table.setAttribute('height', '100%');
                        table.setAttribute('width', '100%');
                    }
                }
            }
            else {
                if (!mxUtils.isVml(this.node)) {
                    this.node.style.width = this.bounds.width + 'px';
                    this.node.style.height = this.bounds.height + 'px';
                }
                table.setAttribute('height', '100%');
                table.setAttribute('width', '100%');
            }
            td.style.textAlign = (this.align == mxConstants.ALIGN_RIGHT) ? 'right' : ((this.align == mxConstants.ALIGN_CENTER) ? 'center' : 'left');
            td.style.verticalAlign = (this.valign == mxConstants.ALIGN_BOTTOM) ? 'bottom' : ((this.valign == mxConstants.ALIGN_MIDDLE) ? 'middle' : 'top');
            var container = td;
            
            
            if (!this.useTableBounds && (this.background != null || this.border != null)) {
                var tbl = document.createElement('table');
                tbl.style.borderCollapse = 'collapse';
                var tb = document.createElement('tbody');
                var tr = document.createElement('tr');
                container = document.createElement('td');
                container.style.textAlign = td.style.textAlign;
                container.style.verticalAlign = td.style.verticalAlign;
                tr.appendChild(container);
                tb.appendChild(tr);
                tbl.appendChild(tb);
                td.appendChild(tbl);
                if (mxClient.IS_MAC) {
                    tbl.setAttribute('align', td.style.textAlign);
                }
            }
            container.style.zoom = this.scale;
            container.style.color = this.color;
            container.style.fontSize = (this.size * scale) + 'px';
            container.style.fontFamily = this.family;
            if (this.isRotate) {
                if (container != td) {
                    td.style.verticalAlign = (this.align == mxConstants.ALIGN_RIGHT) ? 'top' : ((this.align == mxConstants.ALIGN_CENTER) ? 'middle' : 'bottom');
                    td.style.textAlign = (this.valign == mxConstants.ALIGN_BOTTOM) ? 'right' : ((this.valign == mxConstants.ALIGN_MIDDLE) ? 'center' : 'left');
                }
                if (false) {
                    container.style.writingMode = 'tb-rl';
                    container.style.filter = 'flipv fliph';
                }
                else 
                    if (false || false) {
                        container.style.WebkitTransform = 'rotate(-90deg)';
                    }
                    else 
                        if (!false) {
                            container.style.MozTransform = 'rotate(-90deg)';
                        }
                var f = (false) ? 1 : this.scale;
                td.style.paddingTop = (this.spacingRight * f) + 'px';
                td.style.paddingRight = (this.spacingBottom * f) + 'px';
                td.style.paddingBottom = (this.spacingLeft * f) + 'px';
                td.style.paddingLeft = (this.spacingTop * f) + 'px';
            }
            else {
                var f = (false) ? 1 : this.scale;
                td.style.paddingTop = (this.spacingTop * f) + 'px';
                td.style.paddingRight = (this.spacingRight * f) + 'px';
                td.style.paddingBottom = (this.spacingBottom * f) + 'px';
                td.style.paddingLeft = (this.spacingLeft * f) + 'px';
            }
            if (this.isStyleSet(mxConstants.FONT_BOLD)) {
                container.style.fontWeight = 'bold';
            }
            else {
                container.style.fontWeight = 'normal';
            }
            if (this.isStyleSet(mxConstants.FONT_ITALIC)) {
                container.style.fontStyle = 'italic';
            }
            if (this.isStyleSet(mxConstants.FONT_UNDERLINE)) {
                container.style.textDecoration = 'underline';
            }
            if (!this.isWrapping) {
                container.style.whiteSpace = 'nowrap';
            }
            if (this.background != null) {
                container.style.background = this.background;
            }
            if (this.border != null) {
                container.style.borderColor = this.border;
                container.style.borderWidth = '1px';
                container.style.borderStyle = 'solid';
            }
            if (!mxUtils.isNode(this.value)) {
                var value = this.value.replace(/\n/g, '<br/>');
                if (false && this.isStyleSet(mxConstants.FONT_SHADOW)) {
                    value = '<p style=\"height:1em;filter:Shadow(Color=#666666,' + 'Direction=135,Strength=%);\">' + value + '</p>';
                }
                container.innerHTML = value;
            }
            else {
                container.appendChild(this.value);
            }
            row.appendChild(td);
            tbody.appendChild(row);
            table.appendChild(tbody);
            if (this.node.nodeName == 'foreignObject') {
            
            
                if (this.node.firstChild != null) {
                    table = this.node.firstChild.firstChild;
                    var oldTd = table.firstChild.firstChild.firstChild;
                    oldTd.style.cssText = td.getAttribute('style');
                }
                else {
                    var body = document.createElementNS(mxConstants.NS_XHTML, 'body');
                    body.style.overflow = this.node.style.overflow;
                    table.setAttribute('width', '100%');
                    table.setAttribute('height', '100%');
                    body.appendChild(table);
                    this.node.appendChild(body);
                }
            }
            else {
                this.node.innerHTML = '';
                this.node.appendChild(table);
            }
            var xdiff = 0;
            var ydiff = 0;
            var tmpalign = (this.isRotate) ? this.valign : this.align;
            var tmpvalign = (this.isRotate) ? this.align : this.valign;
            if (this.node.style.overflow != 'hidden') {
                if (this.bounds.width > 0 || this.useTableBounds) {
                    xdiff = Math.floor(Math.max(0, table.offsetWidth - this.bounds.width));
                    if (tmpalign == mxConstants.ALIGN_CENTER || tmpalign == mxConstants.ALIGN_MIDDLE) {
                        xdiff = Math.floor(xdiff / 2);
                    }
                    else 
                        if (tmpalign != mxConstants.ALIGN_RIGHT && tmpalign != mxConstants.ALIGN_BOTTOM) {
                            xdiff = 0;
                        }
                }
                if (this.bounds.height > 0 || this.useTableBounds) {
                    ydiff = Math.floor(Math.max(0, table.offsetHeight - this.bounds.height));
                    if (tmpvalign == mxConstants.ALIGN_MIDDLE || tmpvalign == mxConstants.ALIGN_CENTER) {
                        ydiff = Math.floor(ydiff / 2);
                    }
                    else 
                        if ((!this.isRotate && tmpvalign != mxConstants.ALIGN_BOTTOM) || (this.isRotate && tmpvalign != mxConstants.ALIGN_LEFT)) {
                            ydiff = 0;
                        }
                }
                if (xdiff > 0 || ydiff > 0) {
                    if (!mxUtils.isVml(this.node)) {
                        this.node.style.marginLeft = -xdiff + 'px';
                        this.node.style.marginTop = -ydiff + 'px';
                    }
                    else {
                        var x0 = parseInt(this.node.parentNode.style.left) || 0;
                        var y0 = parseInt(this.node.parentNode.style.top) || 0;
                        xdiff -= this.bounds.x - x0;
                        ydiff -= this.bounds.y - y0;
                        this.node.inset = (-xdiff) + 'px,' + (-ydiff) + 'px,0px,0px';
                    }
                }
                else 
                    if (mxUtils.isVml(this.node)) {
                        var x0 = parseInt(this.node.parentNode.style.left);
                        var y0 = parseInt(this.node.parentNode.style.top);
                        this.node.inset = (this.bounds.x - x0) + 'px,' + (this.bounds.y - y0) + 'px,' + (y0 - this.bounds.y) + 'px,' + (x0 - this.bounds.x) + 'px';
                    }
            }
            if (this.opacity != null) {
                mxUtils.setOpacity(this.node, this.opacity);
            }
            var x = this.bounds.x - xdiff;
            var y = this.bounds.y - ydiff;
            var width = Math.max(this.bounds.width, table.offsetWidth || 0);
            var height = Math.max(this.bounds.height, table.offsetHeight || 0);
            this.boundingBox = new mxRectangle(x, y, width, height);
        }
        else {
            this.node.innerHTML = '<div style=\'width:100%;height:100%;\'></div>';
            this.boundingBox = this.bounds.clone();
            if (!mxUtils.isVml(this.node)) {
                this.node.style.position = 'absolute';
                this.node.style.left = this.bounds.x + 'px';
                this.node.style.top = this.bounds.y + 'px';
                this.node.style.width = this.bounds.width + 'px';
                this.node.style.height = this.bounds.height + 'px';
            }
        }
        
        
        if (this.node.nodeName == 'foreignObject') {
            this.node.setAttribute('x', parseInt(this.node.style.left) + parseInt(this.node.style.marginLeft));
            this.node.setAttribute('y', parseInt(this.node.style.top) + parseInt(this.node.style.marginTop));
            var w = parseInt(this.node.style.width);
            if (!isNaN(w)) {
                this.node.setAttribute('width', w);
            }
            var h = parseInt(this.node.style.height);
            if (!isNaN(h)) {
                this.node.setAttribute('height', h);
            }
        }
    };
    mxText.prototype.createSvg = function(){
    
        var node = document.createElementNS(mxConstants.NS_SVG, 'g');
        var uline = this.isStyleSet(mxConstants.FONT_UNDERLINE) ? 'underline' : 'none';
        var weight = this.isStyleSet(mxConstants.FONT_BOLD) ? 'bold' : 'normal';
        var s = this.isStyleSet(mxConstants.FONT_ITALIC) ? 'italic' : null;
        var align = (this.align == mxConstants.ALIGN_RIGHT) ? 'end' : (this.align == mxConstants.ALIGN_CENTER) ? 'middle' : 'start';
        
        node.setAttribute('text-decoration', uline);
        node.setAttribute('text-anchor', align);
        node.setAttribute('font-family', this.family);
        node.setAttribute('font-weight', weight);
        node.setAttribute('font-size', Math.floor(this.size * this.scale) + 'px');
        node.setAttribute('fill', this.color);
        if (s != null) {
            node.setAttribute('font-style', s);
        }
        if (this.background != null || this.border != null) {
            this.backgroundNode = document.createElementNS(mxConstants.NS_SVG, 'rect');
            this.backgroundNode.setAttribute('shape-rendering', 'optimizeSpeed');
            if (this.background != null) {
                this.backgroundNode.setAttribute('fill', this.background);
            }
            else {
                this.backgroundNode.setAttribute('fill', 'none');
            }
            if (this.border != null) {
                this.backgroundNode.setAttribute('stroke', this.border);
            }
            else {
                this.backgroundNode.setAttribute('stroke', 'none');
            }
        }
        this.updateSvgValue(node);
        return node;
    };
    mxText.prototype.updateSvgValue = function(node){
        if (this.currentValue != this.value) {
            while (node.firstChild != null) {
                node.removeChild(node.firstChild);
            }
            if (this.value != null) {
                var lines = this.value.split('\n');
                
                
                
                this.textNodes = new Array(lines.length);
                for (var i = 0; i < lines.length; i++) {
                    if (!this.isEmptyString(lines[i])) {
                        var tspan = this.createSvgSpan(lines[i]);
                        node.appendChild(tspan);
                        this.textNodes[i] = tspan;
                    }
                    else {
                        this.textNodes[i] = null;
                    }
                }
            }
            this.currentValue = this.value;
        }
    };
    mxText.prototype.redrawSvg = function(){
        if (this.node.nodeName == 'foreignObject') {
            this.redrawHtml()
            return;
        }
        this.updateSvgValue(this.node);
        this.node.setAttribute('font-size', Math.floor(this.size * this.scale) + 'px');
        if (this.opacity != null) {
            this.node.setAttribute('fill-opacity', this.opacity / 100);
            this.node.setAttribute('stroke-opacity', this.opacity / 100);
        }
        var dy = this.size * 1.3 * this.scale;
        var childCount = this.node.childNodes.length;
        var lineCount = (this.textNodes != null) ? this.textNodes.length : 0;
        if (this.backgroundNode != null) {
            childCount--;
        }
        var x = this.bounds.x;
        var y = this.bounds.y;
        x += (this.align == mxConstants.ALIGN_RIGHT) ? ((this.isRotate) ? this.bounds.height : this.bounds.width) - this.spacingRight * this.scale : (this.align == mxConstants.ALIGN_CENTER) ? this.spacingLeft + (((this.isRotate) ? this.bounds.height : this.bounds.width) - this.spacingLeft - this.spacingRight) / 2 : this.spacingLeft * this.scale;
        var y0 = (this.valign == mxConstants.ALIGN_BOTTOM) ? ((this.isRotate) ? this.bounds.width : this.bounds.height) - (lineCount - 1) * dy - this.spacingBottom * this.scale - 3 : (this.valign == mxConstants.ALIGN_MIDDLE) ? (this.spacingTop * this.scale + ((this.isRotate) ? this.bounds.width : this.bounds.height) - this.spacingBottom * this.scale - (lineCount - 1.5) * dy) / 2 + 1 : this.spacingTop * this.scale + dy - 2;
        y += y0;
        this.node.setAttribute('x', x);
        this.node.setAttribute('y', y);
        if (this.isRotate) {
            var cx = this.bounds.x + this.bounds.width / 2;
            var cy = this.bounds.y + this.bounds.height / 2;
            var offsetX = (this.bounds.width - this.bounds.height) / 2;
            var offsetY = (this.bounds.height - this.bounds.width) / 2;
            this.node.setAttribute('transform', 'rotate(-90 ' + cx + ' ' + cy + ') ' + 'translate(' + -offsetY + ' ' + (-offsetX) + ')');
        }
        if (this.backgroundNode != null && this.backgroundNode.parentNode == this.node) {
            this.node.removeChild(this.backgroundNode);
        }
        if (this.textNodes != null) {
            for (var i = 0; i < lineCount; i++) {
                var node = this.textNodes[i];
                if (node != null) {
                    node.setAttribute('x', x);
                    node.setAttribute('y', y);
                    node.setAttribute('style', 'pointer-events: all');
                }
                y += dy;
            }
        }
        this.boundingBox = this.bounds.clone();
        
        if (!false && this.value != null && this.value.length > 0) {
            try {
                var box = this.node.getBBox();
                this.boundingBox = new mxRectangle(Math.min(this.bounds.x, box.x - 4 * this.scale || 0), Math.min(this.bounds.y, box.y - 4 * this.scale || 0), Math.max(this.bounds.width, box.width + 8 * this.scale || 0), Math.max(this.bounds.height, box.height + 10 * this.scale || 0));
                if (this.backgroundNode != null && this.node.firstChild != null) {
                    this.node.insertBefore(this.backgroundNode, this.node.firstChild);
                    this.backgroundNode.setAttribute('x', box.x - 4 * this.scale || 0);
                    this.backgroundNode.setAttribute('y', box.y - 4 * this.scale || 0);
                    this.backgroundNode.setAttribute('width', box.width + 8 * this.scale || 0);
                    this.backgroundNode.setAttribute('height', box.height + 8 * this.scale || 0);
                    var strokeWidth = Math.floor(Math.max(1, this.scale));
                    this.backgroundNode.setAttribute('stroke-width', strokeWidth);
                }
            } 
            catch (ex) {
            }
        }
    };
    mxText.prototype.isEmptyString = function(text){
        return text.replace(/ /g, '').length == 0;
    };
    mxText.prototype.createSvgSpan = function(text){
    
    
    
        var node = document.createElementNS(mxConstants.NS_SVG, 'text');
        mxUtils.write(node, text);
        return node;
    };
}
//mxTriangle
{
    function mxTriangle(){
    };
    mxTriangle.prototype = new mxActor();
    mxTriangle.prototype.constructor = mxTriangle;
    mxTriangle.prototype.redrawPath = function(path, x, y, w, h){
        var dir = this.style[mxConstants.STYLE_DIRECTION];
        if (dir == mxConstants.DIRECTION_NORTH) {
            path.moveTo(0, h);
            path.lineTo(0.5 * w, 0);
            path.lineTo(w, h);
        }
        else 
            if (dir == mxConstants.DIRECTION_SOUTH) {
                path.moveTo(0, 0);
                path.lineTo(0.5 * w, h);
                path.lineTo(w, 0);
            }
            else 
                if (dir == mxConstants.DIRECTION_WEST) {
                    path.moveTo(w, 0);
                    path.lineTo(0, 0.5 * h);
                    path.lineTo(w, h);
                }
                else {
                    path.moveTo(0, 0);
                    path.lineTo(w, 0.5 * h);
                    path.lineTo(0, h);
                }
        path.close();
    };
}
//mxHexagon
{
    function mxHexagon(){
    };
    mxHexagon.prototype = new mxActor();
    mxHexagon.prototype.constructor = mxHexagon;
    mxHexagon.prototype.redrawPath = function(path, x, y, w, h){
        var dir = this.style[mxConstants.STYLE_DIRECTION];
        if (dir == mxConstants.DIRECTION_NORTH || dir == mxConstants.DIRECTION_SOUTH) {
            path.moveTo(0.5 * w, 0);
            path.lineTo(w, 0.25 * h);
            path.lineTo(w, 0.75 * h);
            path.lineTo(0.5 * w, h);
            path.lineTo(0, 0.75 * h);
            path.lineTo(0, 0.25 * h);
        }
        else {
            path.moveTo(0.25 * w, 0);
            path.lineTo(0.75 * w, 0);
            path.lineTo(w, 0.5 * h);
            path.lineTo(0.75 * w, h);
            path.lineTo(0.25 * w, h);
            path.lineTo(0, 0.5 * h);
        }
        path.close();
    };
}
//mxLine
{
    function mxLine(bounds, stroke, strokewidth){
        this.bounds = bounds;
        this.stroke = stroke || 'black';
        this.strokewidth = strokewidth || '1';
    };
    mxLine.prototype = new mxShape();
    mxLine.prototype.constructor = mxLine;
    mxLine.prototype.mixedModeHtml = false;
    mxLine.prototype.preferModeHtml = false;
    mxLine.prototype.clone = function(){
        var clone = new mxLine(this.bounds, this.stroke, this.strokewidth);
        clone.isDashed = this.isDashed;
        return clone;
    };
    mxLine.prototype.createVml = function(){
        var node = document.createElement('v:group');
        node.setAttribute('coordorigin', '0,0');
        node.style.position = 'absolute';
        node.style.overflow = 'visible';
        this.label = document.createElement('v:rect');
        this.configureVmlShape(this.label);
        this.label.setAttribute('stroked', 'false');
        this.label.setAttribute('filled', 'false');
        node.appendChild(this.label);
        this.innerNode = document.createElement('v:polyline');
        this.configureVmlShape(this.innerNode);
        node.appendChild(this.innerNode);
        return node;
    };
    mxLine.prototype.redrawVml = function(){
        var x = Math.floor(this.bounds.x);
        var y = Math.floor(this.bounds.y);
        var w = Math.floor(this.bounds.width);
        var h = Math.floor(this.bounds.height);
        this.updateVmlShape(this.node);
        this.node.setAttribute('coordsize', w + ',' + h);
        this.updateVmlShape(this.label);
        this.label.style.left = '0px';
        this.label.style.top = '0px';
        this.innerNode.setAttribute('strokeweight', this.strokewidth * this.scale);
        var direction = this.style[mxConstants.STYLE_DIRECTION];
        if (direction == mxConstants.DIRECTION_NORTH || direction == mxConstants.DIRECTION_SOUTH) {
            this.innerNode.points.value = (w / 2) + ',0 ' + (w / 2) + ',' + (h);
        }
        else {
            this.innerNode.points.value = '0,' + (h / 2) + ' ' + (w) + ',' + (h / 2);
        }
    };
    mxLine.prototype.createSvg = function(){
        var g = this.createSvgGroup('path');
        
        
        
        var color = this.innerNode.getAttribute('stroke');
        this.pipe = document.createElementNS(mxConstants.NS_SVG, 'path');
        this.pipe.setAttribute('stroke', color);
        this.pipe.setAttribute('visibility', 'hidden');
        this.pipe.setAttribute('pointer-events', 'stroke');
        g.appendChild(this.pipe);
        return g;
    };
    mxLine.prototype.redrawSvg = function(){
        var strokeWidth = Math.max(1, this.strokewidth * this.scale);
        this.innerNode.setAttribute('stroke-width', strokeWidth);
        if (this.bounds != null) {
            var x = this.bounds.x;
            var y = this.bounds.y;
            var w = this.bounds.width;
            var h = this.bounds.height;
            var d = null;
            var direction = this.style[mxConstants.STYLE_DIRECTION];
            if (direction == mxConstants.DIRECTION_NORTH || direction == mxConstants.DIRECTION_SOUTH) {
                d = 'M ' + (x + w / 2) + ' ' + y + ' L ' + (x + w / 2) + ' ' + (y + h);
            }
            else {
                d = 'M ' + x + ' ' + (y + h / 2) + ' L ' + (x + w) + ' ' + (y + h / 2);
            }
            this.innerNode.setAttribute('d', d);
            this.pipe.setAttribute('d', d);
            this.pipe.setAttribute('stroke-width', this.strokewidth * this.scale + mxShape.prototype.SVG_STROKE_TOLERANCE);
            this.updateSvgTransform(this.innerNode, false);
            this.updateSvgTransform(this.pipe, false);
        }
    };
}
//mxImageShape
{
    function mxImageShape(bounds, image, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.image = image;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 0;
        this.isShadow = false;
    };
    mxImageShape.prototype = new mxShape();
    mxImageShape.prototype.constructor = mxImageShape;
    mxImageShape.prototype.create = function(){
        var node = null;
        if (this.dialect == mxConstants.DIALECT_SVG) {
        
        
        
        
            node = this.createSvgGroup('rect');
            this.innerNode.setAttribute('fill', this.fill);
            this.innerNode.setAttribute('visibility', 'hidden');
            this.innerNode.setAttribute('pointer-events', 'fill');
            this.imageNode = document.createElementNS(mxConstants.NS_SVG, 'image');
            this.imageNode.setAttributeNS(mxConstants.NS_XLINK, 'xlink:href', this.image);
            this.imageNode.setAttribute('style', 'pointer-events:none');
            this.configureSvgShape(this.imageNode);
            this.imageNode.removeAttribute("stroke");
            this.imageNode.removeAttribute("fill");
            node.insertBefore(this.imageNode, this.innerNode);
        }
        else {
            if (this.dialect == mxConstants.DIALECT_STRICTHTML || this.dialect == mxConstants.DIALECT_PREFERHTML) {
                node = document.createElement('DIV');
                this.configureHtmlShape(node);
                var imgName = this.image.toUpperCase()
                if (imgName.substring(imgName.length - 3, imgName.length) == "PNG" && false && !false) {
                    node.style.filter = 'progid:DXImageTransform.Microsoft.AlphaImageLoader (src=\'' + this.image + '\', sizingMethod=\'scale\')';
                }
                else {
                    var img = document.createElement('img');
                    img.setAttribute('src', this.image);
                    img.style.width = '100%';
                    img.style.height = '100%';
                    img.setAttribute('border', '0');
                    node.appendChild(img);
                }
            }
            else {
                node = document.createElement('v:image');
                node.setAttribute('src', this.image);
                this.configureVmlShape(node);
            }
        }
        return node;
    };
    mxImageShape.prototype.redrawSvg = function(){
        this.updateSvgShape(this.innerNode);
        this.updateSvgShape(this.imageNode);
    };
}
//mxLabel
{
    function mxLabel(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxLabel.prototype = new mxShape();
    mxLabel.prototype.constructor = mxLabel;
    mxLabel.prototype.imageSize = mxConstants.DEFAULT_IMAGESIZE;
    mxLabel.prototype.spacing = 2;
    mxLabel.prototype.indicatorSize = 10;
    mxLabel.prototype.indicatorSpacing = 2;
    mxLabel.prototype.createHtml = function(){
        var name = 'DIV';
        var node = document.createElement(name);
        this.configureHtmlShape(node);
        if (this.indicatorColor != null && this.indicatorShape != null) {
            this.indicator = new this.indicatorShape(this.bounds);
            this.indicator.dialect = this.dialect;
            this.indicator.fill = this.indicatorColor;
            this.indicator.gradient = this.indicatorGradientColor;
            this.indicator.init(node);
        }
        else 
            if (this.indicatorImage != null) {
                this.indicatorImageNode = mxUtils.createImage(this.indicatorImage);
                this.indicatorImageNode.style.position = 'absolute';
                node.appendChild(this.indicatorImageNode);
            }
        if (this.image != null) {
            this.imageNode = mxUtils.createImage(this.image);
            this.stroke = null;
            this.configureHtmlShape(this.imageNode);
            node.appendChild(this.imageNode);
        }
        return node;
    };
    mxLabel.prototype.createVml = function(){
        var node = document.createElement('v:group');
        var name = (this.isRounded) ? 'v:roundrect' : 'v:rect';
        this.rectNode = document.createElement(name);
        this.configureVmlShape(this.rectNode);
        this.isShadow = false;
        this.configureVmlShape(node);
        node.setAttribute('coordorigin', '0,0');
        node.appendChild(this.rectNode);
        if (this.indicatorColor != null && this.indicatorShape != null) {
            this.indicator = new this.indicatorShape(this.bounds);
            this.indicator.dialect = this.dialect;
            this.indicator.fill = this.indicatorColor;
            this.indicator.gradient = this.indicatorGradientColor;
            this.indicator.init(node);
        }
        else 
            if (this.indicatorImage != null) {
                this.indicatorImageNode = document.createElement('v:image');
                this.indicatorImageNode.setAttribute('src', this.indicatorImage);
                node.appendChild(this.indicatorImageNode);
            }
        if (this.image != null) {
            this.imageNode = document.createElement('v:image');
            this.imageNode.setAttribute('src', this.image);
            this.configureVmlShape(this.imageNode);
            node.appendChild(this.imageNode);
        }
        this.label = document.createElement('v:rect');
        this.label.style.top = '0px';
        this.label.style.left = '0px';
        this.label.setAttribute('filled', 'false');
        this.label.setAttribute('stroked', 'false');
        node.appendChild(this.label);
        return node;
    };
    mxLabel.prototype.createSvg = function(){
        var g = this.createSvgGroup('rect');
        if (this.strokewidth * this.scale >= 1 && !this.isRounded) {
            this.innerNode.setAttribute('shape-rendering', 'optimizeSpeed');
        }
        if (this.indicatorColor != null && this.indicatorShape != null) {
            this.indicator = new this.indicatorShape(this.bounds);
            this.indicator.dialect = this.dialect;
            this.indicator.fill = this.indicatorColor;
            this.indicator.gradient = this.indicatorGradientColor;
            this.indicator.init(g);
        }
        else 
            if (this.indicatorImage != null) {
                this.indicatorImageNode = document.createElementNS(mxConstants.NS_SVG, 'image');
                this.indicatorImageNode.setAttributeNS(mxConstants.NS_XLINK, 'href', this.indicatorImage);
                g.appendChild(this.indicatorImageNode);
            }
        if (this.image != null) {
            this.imageNode = document.createElementNS(mxConstants.NS_SVG, 'image');
            this.imageNode.setAttributeNS(mxConstants.NS_XLINK, 'href', this.image);
            this.imageNode.setAttribute('style', 'pointer-events:none');
            this.configureSvgShape(this.imageNode);
            g.appendChild(this.imageNode);
        }
        return g;
    };
    mxLabel.prototype.redraw = function(){
        var isSvg = (this.dialect == mxConstants.DIALECT_SVG);
        var isVml = mxUtils.isVml(this.node);
        if (isSvg) {
            this.updateSvgShape(this.innerNode);
            if (this.shadowNode != null) {
                this.updateSvgShape(this.shadowNode);
            }
        }
        else 
            if (isVml) {
                this.updateVmlShape(this.node);
                this.node.setAttribute('coordsize', this.bounds.width + ',' + this.bounds.height);
                this.updateVmlShape(this.rectNode);
                this.rectNode.style.top = '0px';
                this.rectNode.style.left = '0px';
                this.label.style.width = this.bounds.width + 'px';
                this.label.style.height = this.bounds.height + 'px';
            }
            else {
                this.updateHtmlShape(this.node);
            }
        var imageWidth = 0;
        var imageHeight = 0;
        if (this.imageNode != null) {
            imageWidth = (this.style[mxConstants.STYLE_IMAGE_WIDTH] || this.imageSize) * this.scale;
            imageHeight = (this.style[mxConstants.STYLE_IMAGE_HEIGHT] || this.imageSize) * this.scale;
        }
        var indicatorSpacing = 0;
        var indicatorWidth = 0;
        var indicatorHeight = 0;
        if (this.indicator != null || this.indicatorImageNode != null) {
            indicatorSpacing = (this.style[mxConstants.STYLE_INDICATOR_SPACING] || this.indicatorSpacing) * this.scale;
            indicatorWidth = (this.style[mxConstants.STYLE_INDICATOR_WIDTH] || this.indicatorSize) * this.scale;
            indicatorHeight = (this.style[mxConstants.STYLE_INDICATOR_HEIGHT] || this.indicatorSize) * this.scale;
        }
        var align = this.style[mxConstants.STYLE_IMAGE_ALIGN];
        var valign = this.style[mxConstants.STYLE_IMAGE_VERTICAL_ALIGN];
        var inset = this.spacing * this.scale;
        var width = Math.max(imageWidth, indicatorWidth);
        var height = imageHeight + indicatorSpacing + indicatorHeight;
        var x = (isSvg) ? this.bounds.x : 0;
        if (align == mxConstants.ALIGN_RIGHT) {
            x += this.bounds.width - width - inset;
        }
        else 
            if (align == mxConstants.ALIGN_CENTER) {
                x += (this.bounds.width - width) / 2;
            }
            else {
                x += inset;
            }
        var y = (isSvg) ? this.bounds.y : 0;
        if (valign == mxConstants.ALIGN_BOTTOM) {
            y += this.bounds.height - height - inset;
        }
        else 
            if (valign == mxConstants.ALIGN_TOP) {
                y += inset;
            }
            else {
                y += (this.bounds.height - height) / 2;
            }
        if (this.imageNode != null) {
            if (isSvg) {
                this.imageNode.setAttribute('x', (x + (width - imageWidth) / 2) + 'px');
                this.imageNode.setAttribute('y', y + 'px');
                this.imageNode.setAttribute('width', imageWidth + 'px');
                this.imageNode.setAttribute('height', imageHeight + 'px');
            }
            else {
                this.imageNode.style.left = (x + width - imageWidth) + 'px';
                this.imageNode.style.top = y + 'px';
                this.imageNode.style.width = imageWidth + 'px';
                this.imageNode.style.height = imageHeight + 'px';
            }
        }
        if (this.indicator != null) {
            this.indicator.bounds = new mxRectangle(x + (width - indicatorWidth) / 2, y + imageHeight + indicatorSpacing, indicatorWidth, indicatorHeight);
            this.indicator.redraw();
        }
        else 
            if (this.indicatorImageNode != null) {
                if (isSvg) {
                    this.indicatorImageNode.setAttribute('x', (x + (width - indicatorWidth) / 2) + 'px');
                    this.indicatorImageNode.setAttribute('y', (y + imageHeight + indicatorSpacing) + 'px');
                    this.indicatorImageNode.setAttribute('width', indicatorWidth + 'px');
                    this.indicatorImageNode.setAttribute('height', indicatorHeight + 'px');
                }
                else {
                    this.indicatorImageNode.style.left = (x + (width - indicatorWidth) / 2) + 'px';
                    this.indicatorImageNode.style.top = (y + imageHeight + indicatorSpacing) + 'px';
                    this.indicatorImageNode.style.width = indicatorWidth + 'px';
                    this.indicatorImageNode.style.height = indicatorHeight + 'px';
                }
            }
    };
}
//mxCylinder
{
    function mxCylinder(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxCylinder.prototype = new mxShape();
    mxCylinder.prototype.constructor = mxCylinder;
    mxCylinder.prototype.mixedModeHtml = false;
    mxCylinder.prototype.preferModeHtml = false;
    mxCylinder.prototype.maxHeight = 40;
    mxCylinder.prototype.create = function(container){
        if (this.stroke == null) {
            this.stroke = this.fill;
        }
        return mxShape.prototype.create.apply(this, arguments);
    };
    mxCylinder.prototype.createVml = function(){
        var node = document.createElement('v:group');
        this.background = document.createElement('v:shape');
        this.label = this.background;
        this.configureVmlShape(this.background);
        node.appendChild(this.background);
        this.fill = null;
        this.isShadow = false;
        this.configureVmlShape(node);
        this.foreground = document.createElement('v:shape');
        this.configureVmlShape(this.foreground);
        node.appendChild(this.foreground);
        return node;
    };
    mxCylinder.prototype.redrawVml = function(){
        var x = Math.floor(this.bounds.x);
        var y = Math.floor(this.bounds.y);
        var w = Math.floor(this.bounds.width);
        var h = Math.floor(this.bounds.height);
        var s = this.strokewidth * this.scale;
        this.node.setAttribute('coordsize', w + ',' + h);
        this.background.setAttribute('coordsize', w + ',' + h);
        this.foreground.setAttribute('coordsize', w + ',' + h);
        this.updateVmlShape(this.node);
        this.updateVmlShape(this.background);
        this.background.style.top = '0px';
        this.background.style.left = '0px';
        this.background.style.rotation = null;
        this.updateVmlShape(this.foreground);
        this.foreground.style.top = '0px';
        this.foreground.style.left = '0px';
        this.foreground.style.rotation = null;
        this.background.setAttribute('strokeweight', s);
        this.foreground.setAttribute('strokeweight', s);
        var d = this.createPath(false);
        this.background.setAttribute('path', d);
        var d = this.createPath(true);
        this.foreground.setAttribute('path', d);
    };
    mxCylinder.prototype.createSvg = function(){
        var g = this.createSvgGroup('path');
        this.foreground = document.createElementNS(mxConstants.NS_SVG, 'path');
        if (this.stroke != null) {
            this.foreground.setAttribute('stroke', this.stroke);
        }
        else {
            this.foreground.setAttribute('stroke', 'none');
        }
        this.foreground.setAttribute('fill', 'none');
        g.appendChild(this.foreground);
        return g;
    };
    mxCylinder.prototype.redrawSvg = function(){
        var strokeWidth = Math.max(1, this.strokewidth * this.scale);
        this.innerNode.setAttribute('stroke-width', strokeWidth);
        var d = this.createPath(false);
        this.innerNode.setAttribute('d', d);
        this.updateSvgTransform(this.innerNode, false);
        if (this.shadowNode != null) {
            this.shadowNode.setAttribute('stroke-width', strokeWidth);
            this.shadowNode.setAttribute('d', d);
            this.updateSvgTransform(this.shadowNode, true);
        }
        d = this.createPath(true);
        this.foreground.setAttribute('stroke-width', strokeWidth);
        this.foreground.setAttribute('d', d);
        this.updateSvgTransform(this.foreground, false);
    };
    mxCylinder.prototype.redrawPath = function(path, x, y, w, h, isForeground){
        var dy = Math.min(this.maxHeight, Math.floor(h / 5));
        if (isForeground) {
            path.moveTo(0, dy);
            path.curveTo(0, 2 * dy, w, 2 * dy, w, dy);
        }
        else {
            path.moveTo(0, dy);
            path.curveTo(0, -dy / 3, w, -dy / 3, w, dy);
            path.lineTo(w, h - dy);
            path.curveTo(w, h + dy / 3, 0, h + dy / 3, 0, (h - dy));
            path.close();
        }
    };
}
//mxConnector
{
    function mxConnector(points, stroke, strokewidth){
        this.points = points;
        this.stroke = stroke || 'black';
        this.strokewidth = strokewidth || 1;
    };
    mxConnector.prototype = new mxShape();
    mxConnector.prototype.constructor = mxConnector;
    mxConnector.prototype.mixedModeHtml = false;
    mxConnector.prototype.preferModeHtml = false;
    mxConnector.prototype.createHtml = function(){
        var node = document.createElement('DIV');
        this.configureHtmlShape(node);
        node.style.borderStyle = 'none';
        node.style.background = '';
        return node;
    };
    mxConnector.prototype.createVml = function(){
        var node = document.createElement('v:shape');
        this.strokeNode = document.createElement('v:stroke');
        this.configureVmlShape(node);
        this.strokeNode.setAttribute('endarrow', this.endArrow);
        this.strokeNode.setAttribute('startarrow', this.startArrow);
        if (this.opacity != null) {
            this.strokeNode.setAttribute('opacity', this.opacity + '%');
        }
        node.appendChild(this.strokeNode);
        return node;
    };
    mxConnector.prototype.redrawVml = function(){
        if (this.node != null && this.strokeNode != null) {
            var startSize = mxUtils.getValue(this.style, mxConstants.STYLE_STARTSIZE, mxConstants.DEFAULT_MARKERSIZE) * this.scale;
            var endSize = mxUtils.getValue(this.style, mxConstants.STYLE_ENDSIZE, mxConstants.DEFAULT_MARKERSIZE) * this.scale;
            var startWidth = 'medium';
            var startLength = 'medium';
            var endWidth = 'medium';
            var endLength = 'medium';
            if (startSize < 6) {
                startWidth = 'narrow';
                startLength = 'short';
            }
            else 
                if (startSize > 10) {
                    startWidth = 'wide';
                    startLength = 'long';
                }
            if (endSize < 6) {
                endWidth = 'narrow';
                endLength = 'short';
            }
            else 
                if (endSize > 10) {
                    endWidth = 'wide';
                    endLength = 'long';
                }
            this.strokeNode.setAttribute('startarrowwidth', startWidth);
            this.strokeNode.setAttribute('startarrowlength', startLength);
            this.strokeNode.setAttribute('endarrowwidth', endWidth);
            this.strokeNode.setAttribute('endarrowlength', endLength);
            this.updateVmlShape(this.node);
        }
    };
    mxConnector.prototype.createSvg = function(){
        var g = this.createSvgGroup('path');
        var color = this.innerNode.getAttribute('stroke');
        if (this.startArrow != null) {
            this.start = document.createElementNS(mxConstants.NS_SVG, 'path');
            g.appendChild(this.start);
        }
        if (this.endArrow != null) {
            this.end = document.createElementNS(mxConstants.NS_SVG, 'path');
            g.appendChild(this.end);
        }
        
        
        
        this.pipe = document.createElementNS(mxConstants.NS_SVG, 'path');
        this.pipe.setAttribute('stroke', color);
        this.pipe.setAttribute('visibility', 'hidden');
        this.pipe.setAttribute('pointer-events', 'stroke');
        g.appendChild(this.pipe);
        return g;
    };
    mxConnector.prototype.redrawSvg = function(){
        mxShape.prototype.redrawSvg.apply(this, arguments);
        var strokeWidth = this.strokewidth * this.scale;
        var color = this.innerNode.getAttribute('stroke');
        if (mxConstants.SVG_CRISP_EDGES && strokeWidth == Math.floor(strokeWidth) && !this.isRounded) {
            this.node.setAttribute('shape-rendering', 'optimizeSpeed');
        }
        else {
            this.node.setAttribute('shape-rendering', 'auto');
        }
        
        
        
        if (this.points != null && this.points[0] != null) {
            if (this.start != null) {
                var p0 = this.points[1];
                var pe = this.points[0];
                var size = mxUtils.getValue(this.style, mxConstants.STYLE_STARTSIZE, mxConstants.DEFAULT_MARKERSIZE);
                this.startOffset = this.redrawSvgMarker(this.start, this.startArrow, p0, pe, color, size);
            }
            if (this.end != null) {
                var n = this.points.length;
                var p0 = this.points[n - 2];
                var pe = this.points[n - 1];
                var size = mxUtils.getValue(this.style, mxConstants.STYLE_ENDSIZE, mxConstants.DEFAULT_MARKERSIZE);
                this.endOffset = this.redrawSvgMarker(this.end, this.endArrow, p0, pe, color, size);
            }
        }
        this.updateSvgShape(this.innerNode);
        var d = this.innerNode.getAttribute('d');
        if (d != null) {
            this.pipe.setAttribute('d', this.innerNode.getAttribute('d'));
            this.pipe.setAttribute('stroke-width', strokeWidth + mxShape.prototype.SVG_STROKE_TOLERANCE);
        }
        this.innerNode.setAttribute('fill', 'none');
    };
    mxConnector.prototype.redrawSvgMarker = function(node, type, p0, pe, color, size){
        var offset = null;
        var dx = pe.x - p0.x;
        var dy = pe.y - p0.y;
        var dist = Math.max(1, Math.sqrt(dx * dx + dy * dy));
        var absSize = size * this.scale;
        var nx = dx * absSize / dist;
        var ny = dy * absSize / dist;
        pe = pe.clone();
        pe.x -= nx * this.strokewidth / (2 * size);
        pe.y -= ny * this.strokewidth / (2 * size);
        nx *= 0.5 + this.strokewidth / 2;
        ny *= 0.5 + this.strokewidth / 2;
        if (type == 'classic' || type == 'block') {
            var d = 'M ' + pe.x + ' ' + pe.y + ' L ' + (pe.x - nx - ny / 2) + ' ' + (pe.y - ny + nx / 2) +
            ((type != 'classic') ? '' : ' L ' + (pe.x - nx * 3 / 4) + ' ' + (pe.y - ny * 3 / 4)) +
            ' L ' +
            (pe.x + ny / 2 - nx) +
            ' ' +
            (pe.y - ny - nx / 2) +
            ' z';
            node.setAttribute('d', d);
            offset = new mxPoint(-nx * 3 / 4, -ny * 3 / 4);
        }
        else 
            if (type == 'open') {
                nx *= 1.2;
                ny *= 1.2;
                var d = 'M ' + (pe.x - nx - ny / 2) + ' ' + (pe.y - ny + nx / 2) + ' L ' + (pe.x - nx / 6) + ' ' + (pe.y - ny / 6) + ' L ' + (pe.x + ny / 2 - nx) + ' ' + (pe.y - ny - nx / 2) + ' M ' + pe.x + ' ' + pe.y;
                node.setAttribute('d', d);
                node.setAttribute('fill', 'none');
                node.setAttribute('stroke-width', this.scale * this.strokewidth);
                offset = new mxPoint(-nx / 4, -ny / 4);
            }
            else 
                if (type == 'oval') {
                    nx *= 1.2;
                    ny *= 1.2;
                    absSize *= 1.2;
                    var d = 'M ' + (pe.x - ny / 2) + ' ' + (pe.y + nx / 2) + ' a ' + (absSize / 2) + ' ' + (absSize / 2) + ' 0  1,1 ' + (nx / 8) + ' ' + (ny / 8) + ' z';
                    node.setAttribute('d', d);
                }
                else 
                    if (type == 'diamond') {
                        var d = 'M ' + (pe.x + nx / 2) + ' ' + (pe.y + ny / 2) + ' L ' + (pe.x - ny / 2) + ' ' + (pe.y + nx / 2) + ' L ' + (pe.x - nx / 2) + ' ' + (pe.y - ny / 2) + ' L ' + (pe.x + ny / 2) + ' ' + (pe.y - nx / 2) + ' z';
                        node.setAttribute('d', d);
                    }
        node.setAttribute('stroke', color);
        if (type != 'open') {
            node.setAttribute('fill', color);
        }
        else {
            node.setAttribute('stroke-linecap', 'round');
        }
        if (this.opacity != null) {
            node.setAttribute('fill-opacity', this.opacity / 100);
            node.setAttribute('stroke-opacity', this.opacity / 100);
        }
        return offset;
    };
}
//mxSwimlane
{
    function mxSwimlane(bounds, fill, stroke, strokewidth){
        this.bounds = bounds;
        this.fill = fill;
        this.stroke = stroke;
        this.strokewidth = strokewidth || 1;
    };
    mxSwimlane.prototype = new mxShape();
    mxSwimlane.prototype.constructor = mxSwimlane;
    mxSwimlane.prototype.imageSize = 16;
    mxSwimlane.prototype.defaultStartSize = 40;
    mxSwimlane.prototype.mixedModeHtml = false;
    mxRhombus.prototype.preferModeHtml = false;
    mxSwimlane.prototype.createHtml = function(){
        var node = document.createElement('DIV');
        this.configureHtmlShape(node);
        node.style.background = '';
        node.style.backgroundColor = '';
        node.style.borderStyle = 'none';
        this.label = document.createElement('DIV');
        this.configureHtmlShape(this.label);
        node.appendChild(this.label);
        this.content = document.createElement('DIV');
        var tmp = this.fill;
        this.configureHtmlShape(this.content);
        this.content.style.background = '';
        this.content.style.backgroundColor = '';
        if (mxUtils.getValue(this.style, mxConstants.STYLE_HORIZONTAL, true)) {
            this.content.style.borderTopStyle = 'none';
        }
        else {
            this.content.style.borderLeftStyle = 'none';
        }
        this.content.style.cursor = 'default';
        node.appendChild(this.content);
        var color = this.style[mxConstants.STYLE_SEPARATORCOLOR];
        if (color != null) {
            this.separator = document.createElement('DIV');
            this.separator.style.borderColor = color;
            this.separator.style.borderLeftStyle = 'dashed';
            node.appendChild(this.separator);
        }
        if (this.image != null) {
            this.imageNode = mxUtils.createImage(this.image);
            this.configureHtmlShape(this.imageNode);
            this.imageNode.style.borderStyle = 'none';
            node.appendChild(this.imageNode);
        }
        return node;
    };
    mxSwimlane.prototype.redrawHtml = function(){
        this.updateHtmlShape(this.node);
        this.startSize = parseInt(this.style[mxConstants.STYLE_STARTSIZE]) || this.defaultStartSize;
        this.updateHtmlShape(this.label);
        this.label.style.top = '0px';
        this.label.style.left = '0px';
        if (mxUtils.getValue(this.style, mxConstants.STYLE_HORIZONTAL, true)) {
            this.startSize = Math.min(this.startSize, this.bounds.height);
            this.label.style.height = (this.startSize * this.scale) + 'px';
            this.updateHtmlShape(this.content);
            var h = this.startSize * this.scale;
            this.content.style.top = h + 'px';
            this.content.style.left = '0px';
            this.content.style.height = Math.max(1, this.bounds.height - h) + 'px';
            if (this.separator != null) {
                this.separator.style.left = Math.floor(this.bounds.width) + 'px';
                this.separator.style.top = Math.floor(this.startSize * this.scale) + 'px';
                this.separator.style.width = '1px';
                this.separator.style.height = Math.floor(this.bounds.height) + 'px';
                this.separator.style.borderWidth = Math.floor(this.scale) + 'px';
            }
            if (this.imageNode != null) {
                this.imageNode.style.left = (this.bounds.width - this.imageSize - 4) + 'px';
                this.imageNode.style.top = '0px';
                this.imageNode.style.width = Math.floor(this.imageSize * this.scale) + 'px';
                this.imageNode.style.height = Math.floor(this.imageSize * this.scale) + 'px';
            }
        }
        else {
            this.startSize = Math.min(this.startSize, this.bounds.width);
            this.label.style.width = (this.startSize * this.scale) + 'px';
            this.updateHtmlShape(this.content);
            var w = this.startSize * this.scale;
            this.content.style.top = '0px';
            this.content.style.left = w + 'px';
            this.content.style.width = Math.max(0, this.bounds.width - w) + 'px';
            if (this.separator != null) {
                this.separator.style.left = Math.floor(this.startSize * this.scale) + 'px';
                this.separator.style.top = Math.floor(this.bounds.height) + 'px';
                this.separator.style.width = Math.floor(this.bounds.width) + 'px';
                this.separator.style.height = '1px';
            }
            if (this.imageNode != null) {
                this.imageNode.style.left = (this.bounds.width - this.imageSize - 4) + 'px';
                this.imageNode.style.top = '0px';
                this.imageNode.style.width = this.imageSize * this.scale + 'px';
                this.imageNode.style.height = this.imageSize * this.scale + 'px';
            }
        }
    };
    mxSwimlane.prototype.createVml = function(){
        var node = document.createElement('v:group');
        var name = (this.isRounded) ? 'v:roundrect' : 'v:rect';
        this.label = document.createElement(name);
        this.configureVmlShape(this.label);
        if (this.isRounded) {
            this.label.setAttribute('arcsize', '20%');
        }
        this.isShadow = false;
        this.configureVmlShape(node);
        node.setAttribute('coordorigin', '0,0');
        node.appendChild(this.label);
        this.content = document.createElement(name);
        var tmp = this.fill;
        this.fill = null;
        this.configureVmlShape(this.content);
        if (this.isRounded) {
            this.content.setAttribute('arcsize', '4%');
        }
        this.fill = tmp;
        this.content.style.borderBottom = '0px';
        node.appendChild(this.content);
        var color = this.style[mxConstants.STYLE_SEPARATORCOLOR];
        if (color != null) {
            this.separator = document.createElement('v:polyline');
            this.separator.setAttribute('strokecolor', color);
            var strokeNode = document.createElement('v:stroke');
            strokeNode.setAttribute('dashstyle', '2 2');
            this.separator.appendChild(strokeNode);
            node.appendChild(this.separator);
        }
        if (this.image != null) {
            this.imageNode = document.createElement('v:image');
            this.imageNode.setAttribute('src', this.image);
            this.configureVmlShape(this.imageNode);
            node.appendChild(this.imageNode);
        }
        return node;
    };
    mxSwimlane.prototype.redrawVml = function(){
        var x = Math.floor(this.bounds.x);
        var y = Math.floor(this.bounds.y);
        var w = Math.floor(this.bounds.width);
        var h = Math.floor(this.bounds.height);
        this.updateVmlShape(this.node);
        this.node.setAttribute('coordsize', w + ',' + h);
        this.updateVmlShape(this.label);
        this.label.style.top = '0px';
        this.label.style.left = '0px';
        this.label.style.rotation = null;
        this.startSize = parseInt(this.style[mxConstants.STYLE_STARTSIZE]) || this.defaultStartSize;
        var start = Math.floor(this.startSize * this.scale);
        if (mxUtils.getValue(this.style, mxConstants.STYLE_HORIZONTAL, true)) {
            start = Math.min(start, this.bounds.height);
            this.label.style.height = start + 'px';
            this.updateVmlShape(this.content);
            this.content.style.top = start + 'px';
            this.content.style.left = '0px';
            this.content.style.height = Math.max(0, h - start) + 'px';
            if (this.separator != null) {
                this.separator.points.value = w + ',' + start + ' ' + w + ',' + h;
            }
            if (this.imageNode != null) {
                var img = Math.floor(this.imageSize * this.scale);
                this.imageNode.style.left = (w - img - 4) + 'px';
                this.imageNode.style.top = '0px';
                this.imageNode.style.width = img + 'px';
                this.imageNode.style.height = img + 'px';
            }
        }
        else {
            start = Math.min(start, this.bounds.width);
            this.label.style.width = start + 'px';
            this.updateVmlShape(this.content);
            this.content.style.top = '0px';
            this.content.style.left = start + 'px';
            this.content.style.width = Math.max(0, w - start) + 'px';
            if (this.separator != null) {
                this.separator.points.value = '0,' + h + ' ' + (w + start) + ',' + h;
            }
            if (this.imageNode != null) {
                var img = Math.floor(this.imageSize * this.scale);
                this.imageNode.style.left = (w - img - 4) + 'px';
                this.imageNode.style.top = '0px';
                this.imageNode.style.width = img + 'px';
                this.imageNode.style.height = img + 'px';
            }
        }
        this.content.style.rotation = null;
    };
    mxSwimlane.prototype.createSvg = function(){
        var node = this.createSvgGroup('rect');
        if (this.strokewidth * this.scale >= 1 && !this.isRounded) {
            this.innerNode.setAttribute('shape-rendering', 'optimizeSpeed');
        }
        if (this.isRounded) {
            this.innerNode.setAttribute('rx', 10);
            this.innerNode.setAttribute('ry', 10);
        }
        this.content = document.createElementNS(mxConstants.NS_SVG, 'path');
        this.configureSvgShape(this.content);
        this.content.setAttribute('fill', 'none');
        if (this.strokewidth * this.scale >= 1 && !this.isRounded) {
            this.content.setAttribute('shape-rendering', 'optimizeSpeed');
        }
        if (this.isRounded) {
            this.content.setAttribute('rx', 10);
            this.content.setAttribute('ry', 10);
        }
        node.appendChild(this.content);
        var color = this.style[mxConstants.STYLE_SEPARATORCOLOR];
        if (color != null) {
            this.separator = document.createElementNS(mxConstants.NS_SVG, 'line');
            this.separator.setAttribute('stroke', color);
            this.separator.setAttribute('fill', 'none');
            this.separator.setAttribute('stroke-dasharray', '2, 2');
            this.separator.setAttribute('shape-rendering', 'optimizeSpeed');
            node.appendChild(this.separator);
        }
        if (this.image != null) {
            this.imageNode = document.createElementNS(mxConstants.NS_SVG, 'image');
            this.imageNode.setAttributeNS(mxConstants.NS_XLINK, 'href', this.image);
            this.configureSvgShape(this.imageNode);
            node.appendChild(this.imageNode);
        }
        return node;
    };
    mxSwimlane.prototype.redrawSvg = function(){
        var tmp = this.isRounded;
        this.isRounded = false;
        this.updateSvgShape(this.innerNode);
        this.updateSvgShape(this.content);
        if (this.shadowNode != null) {
            this.updateSvgShape(this.shadowNode);
            if (this.style[mxConstants.STYLE_HORIZONTAL]) {
                this.shadowNode.setAttribute('width', this.startSize * this.scale);
            }
            else {
                this.shadowNode.setAttribute('height', this.startSize * this.scale);
            }
        }
        this.isRounded = tmp;
        this.startSize = parseInt(this.style[mxConstants.STYLE_STARTSIZE]) || this.defaultStartSize;
        if (mxUtils.getValue(this.style, mxConstants.STYLE_HORIZONTAL, true)) {
            this.startSize = Math.min(this.startSize, this.bounds.height);
            this.innerNode.setAttribute('height', this.startSize * this.scale);
            var h = this.startSize * this.scale;
            var points = 'M ' + this.bounds.x + ' ' + (this.bounds.y + h) + ' l 0 ' + (this.bounds.height - h) + ' l ' + this.bounds.width + ' 0' + ' l 0 ' + (-this.bounds.height + h);
            this.content.setAttribute('d', points);
            this.content.removeAttribute('x');
            this.content.removeAttribute('y');
            this.content.removeAttribute('width');
            this.content.removeAttribute('height');
            if (this.separator != null) {
                this.separator.setAttribute('x1', this.bounds.x + this.bounds.width);
                this.separator.setAttribute('y1', this.bounds.y + this.startSize * this.scale);
                this.separator.setAttribute('x2', this.bounds.x + this.bounds.width);
                this.separator.setAttribute('y2', this.bounds.y + this.bounds.height);
            }
            if (this.imageNode != null) {
                this.imageNode.setAttribute('x', this.bounds.x + this.bounds.width - this.imageSize - 4);
                this.imageNode.setAttribute('y', this.bounds.y);
                this.imageNode.setAttribute('width', this.imageSize * this.scale + 'px');
                this.imageNode.setAttribute('height', this.imageSize * this.scale + 'px');
            }
        }
        else {
            this.startSize = Math.min(this.startSize, this.bounds.width);
            this.innerNode.setAttribute('width', this.startSize * this.scale);
            var w = this.startSize * this.scale;
            var points = 'M ' + (this.bounds.x + w) + ' ' + this.bounds.y + ' l ' + (this.bounds.width - w) + ' 0' + ' l 0 ' + this.bounds.height + ' l ' + (-this.bounds.width + w) + ' 0';
            this.content.setAttribute('d', points);
            this.content.removeAttribute('x');
            this.content.removeAttribute('y');
            this.content.removeAttribute('width');
            this.content.removeAttribute('height');
            if (this.separator != null) {
                this.separator.setAttribute('x1', this.bounds.x + this.startSize * this.scale);
                this.separator.setAttribute('y1', this.bounds.y + this.bounds.height);
                this.separator.setAttribute('x2', this.bounds.x + this.bounds.width);
                this.separator.setAttribute('y2', this.bounds.y + this.bounds.height);
            }
            if (this.imageNode != null) {
                this.imageNode.setAttribute('x', this.bounds.x + this.bounds.width - this.imageSize - 4);
                this.imageNode.setAttribute('y', this.bounds.y);
                this.imageNode.setAttribute('width', this.imageSize * this.scale + 'px');
                this.imageNode.setAttribute('height', this.imageSize * this.scale + 'px');
            }
        }
    };
}
//mxGraphLayout
{
    function mxGraphLayout(graph){
        this.graph = graph;
    };
    mxGraphLayout.prototype.graph = null;
    mxGraphLayout.prototype.useBoundingBox = true;
    mxGraphLayout.prototype.moveCell = function(cell, x, y){
    };
    mxGraphLayout.prototype.execute = function(parent){
    };
    mxGraphLayout.prototype.getGraph = function(){
        return this.graph;
    };
    mxGraphLayout.prototype.isVertexMovable = function(cell){
        return this.graph.isCellMovable(cell);
    };
    mxGraphLayout.prototype.isVertexIgnored = function(vertex){
        return !this.graph.getModel().isVertex(vertex) || !this.graph.isCellVisible(vertex);
    };
    mxGraphLayout.prototype.isEdgeIgnored = function(edge){
        var model = this.graph.getModel();
        return !model.isEdge(edge) || !this.graph.isCellVisible(edge) || model.getTerminal(edge, true) == null || model.getTerminal(edge, false) == null;
    };
    mxGraphLayout.prototype.setVertexLocation = function(cell, x, y){
        var model = this.graph.getModel();
        var geometry = model.getGeometry(cell);
        var result = null;
        if (geometry != null) {
            result = new mxRectangle(x, y, geometry.width, geometry.height);
            
            if (this.useBoundingBox) {
                var state = this.graph.getView().getState(cell);
                if (state != null && state.text != null && state.text.boundingBox != null && state.text.boundingBox.x < state.x) {
                    var scale = this.graph.getView().scale;
                    var box = state.text.boundingBox;
                    x += (state.x - box.x) / scale;
                    result.width = box.width;
                }
            }
            if (geometry.x != x || geometry.y != y) {
                geometry = geometry.clone();
                geometry.x = x;
                geometry.y = y;
                model.setGeometry(cell, geometry);
            }
        }
        return result;
    };
    mxGraphLayout.prototype.setEdgeStyleEnabled = function(edge, value){
        this.graph.setCellStyles(mxConstants.STYLE_NOEDGESTYLE, (value) ? '0' : '1', [edge]);
    };
    mxGraphLayout.prototype.setEdgePoints = function(edge, points){
        if (edge != null) {
            var model = this.graph.model;
            var geometry = model.getGeometry(edge);
            if (geometry == null) {
                geometry = new mxGeometry();
                geometry.setRelative(true);
            }
            else {
                geometry = geometry.clone();
            }
            geometry.points = points;
            model.setGeometry(edge, geometry);
        }
    };
    mxGraphLayout.prototype.getVertexBounds = function(cell){
        var geo = this.graph.getModel().getGeometry(cell);
        
        if (this.useBoundingBox) {
            var state = this.graph.getView().getState(cell);
            if (state != null && state.text != null && state.text.boundingBox != null) {
                var scale = this.graph.getView().scale;
                var tmp = state.text.boundingBox;
                var dx0 = (tmp.x - state.x) / scale;
                var dy0 = (tmp.y - state.y) / scale;
                var dx1 = (tmp.x + tmp.width - state.x - state.width) / scale;
                var dy1 = (tmp.y + tmp.height - state.y - state.height) / scale;
                geo = new mxRectangle(geo.x + dx0, geo.y + dy0, geo.width - dx0 + dx1, geo.height - dy0 + dy1);
            }
        }
        return new mxRectangle(geo.x, geo.y, geo.width, geo.height);
    };
}
//mxStackLayout
{
    function mxStackLayout(graph, horizontal, spacing, x0, y0){
        mxGraphLayout.call(this, graph);
        this.horizontal = (horizontal != null) ? horizontal : true;
        this.spacing = (spacing != null) ? spacing : graph.gridSize;
        this.x0 = (x0 != null) ? x0 : this.spacing;
        this.y0 = (y0 != null) ? y0 : this.spacing;
    };
    mxStackLayout.prototype = new mxGraphLayout();
    mxStackLayout.prototype.constructor = mxStackLayout;
    mxStackLayout.prototype.horizontal = null;
    mxStackLayout.prototype.spacing = null;
    mxStackLayout.prototype.x0 = null;
    mxStackLayout.prototype.y0 = null;
    mxStackLayout.prototype.keepFirstLocation = false;
    mxStackLayout.prototype.fill = false;
    mxStackLayout.prototype.resizeParent = false;
    mxStackLayout.prototype.wrap = null;
    mxStackLayout.prototype.isHorizontal = function(){
        return this.horizontal;
    };
    mxStackLayout.prototype.moveCell = function(cell, x, y){
        var model = this.graph.getModel();
        var parent = model.getParent(cell);
        var horizontal = this.isHorizontal();
        if (cell != null && parent != null) {
            var i = 0;
            var last = 0;
            var childCount = model.getChildCount(parent);
            var value = (horizontal) ? x : y;
            var pstate = this.graph.getView().getState(parent);
            if (pstate != null) {
                value -= (horizontal) ? pstate.x : pstate.y;
            }
            for (i = 0; i < childCount; i++) {
                var child = model.getChildAt(parent, i);
                if (child != cell) {
                    var bounds = model.getGeometry(child);
                    if (bounds != null) {
                        var tmp = (horizontal) ? bounds.x + bounds.width / 2 : bounds.y + bounds.height / 2;
                        if (last < value && tmp > value) {
                            break;
                        }
                        last = tmp;
                    }
                }
            }
            var idx = parent.getIndex(cell);
            idx = Math.max(0, i - ((i > idx) ? 1 : 0));
            model.add(parent, cell, idx);
        }
    };
    mxStackLayout.prototype.getParentSize = function(parent){
        var model = this.graph.getModel();
        var pgeo = model.getGeometry(parent);
        
        
        if (this.graph.container != null && ((pgeo == null && model.isLayer(parent)) || parent == this.graph.getView().currentRoot)) {
            var width = this.graph.container.offsetWidth;
            var height = this.graph.container.offsetHeight;
            pgeo = new mxRectangle(0, 0, width, height);
        }
        return pgeo;
    };
    mxStackLayout.prototype.execute = function(parent){
        var horizontal = this.isHorizontal();
        if (parent != null) {
            var model = this.graph.getModel();
            var x0 = this.x0 + 1;
            var y0 = this.y0;
            var pgeo = this.getParentSize(parent);
            var fillValue = 0;
            if (pgeo != null) {
                fillValue = (horizontal) ? pgeo.height : pgeo.width;
            }
            fillValue -= 2 * this.spacing;
            var size = this.graph.getStartSize(parent);
            fillValue -= (horizontal) ? size.height : size.width;
            x0 = this.x0 + size.width;
            y0 = this.y0 + size.height;
            model.beginUpdate();
            try {
                var tmp = 0;
                var last = null;
                var childCount = model.getChildCount(parent);
                for (var i = 0; i < childCount; i++) {
                    var child = model.getChildAt(parent, i);
                    if (!this.isVertexIgnored(child) && this.isVertexMovable(child)) {
                        var geo = model.getGeometry(child);
                        if (geo != null) {
                            geo = geo.clone();
                            if (this.wrap != null && last != null) {
                                if ((horizontal && last.x + last.width + geo.width + 2 * this.spacing > this.wrap) || (!horizontal && last.y + last.height + geo.height + 2 * this.spacing > this.wrap)) {
                                    last = null;
                                    if (horizontal) {
                                        y0 += tmp + this.spacing;
                                    }
                                    else {
                                        x0 += tmp + this.spacing;
                                    }
                                    tmp = 0;
                                }
                            }
                            tmp = Math.max(tmp, (horizontal) ? geo.height : geo.width);
                            if (last != null) {
                                if (horizontal) {
                                    geo.x = last.x + last.width + this.spacing;
                                }
                                else {
                                    geo.y = last.y + last.height + this.spacing;
                                }
                            }
                            else 
                                if (!this.keepFirstLocation) {
                                    if (horizontal) {
                                        geo.x = x0;
                                    }
                                    else {
                                        geo.y = y0;
                                    }
                                }
                            if (horizontal) {
                                geo.y = y0;
                            }
                            else {
                                geo.x = x0;
                            }
                            if (this.fill && fillValue > 0) {
                                if (horizontal) {
                                    geo.height = fillValue;
                                }
                                else {
                                    geo.width = fillValue;
                                }
                            }
                            model.setGeometry(child, geo);
                            last = geo;
                        }
                    }
                }
                if (this.resizeParent && pgeo != null && last != null && !this.graph.isCellCollapsed(parent)) {
                    pgeo = pgeo.clone();
                    if (horizontal) {
                        pgeo.width = last.x + last.width + this.spacing;
                    }
                    else {
                        pgeo.height = last.y + last.height + this.spacing;
                    }
                    model.setGeometry(parent, pgeo);
                }
            }
            finally {
                model.endUpdate();
            }
        }
    };
}
//mxPartitionLayout
{
    function mxPartitionLayout(graph, horizontal, spacing, border){
        mxGraphLayout.call(this, graph);
        this.horizontal = (horizontal != null) ? horizontal : true;
        this.spacing = spacing || 0;
        this.border = border || 0;
    };
    mxPartitionLayout.prototype = new mxGraphLayout();
    mxPartitionLayout.prototype.constructor = mxPartitionLayout;
    mxPartitionLayout.prototype.horizontal = null;
    mxPartitionLayout.prototype.spacing = null;
    mxPartitionLayout.prototype.border = null;
    mxPartitionLayout.prototype.resizeVertices = true;
    mxPartitionLayout.prototype.isHorizontal = function(){
        return this.horizontal;
    };
    mxPartitionLayout.prototype.moveCell = function(cell, x, y){
        var model = this.graph.getModel();
        var parent = model.getParent(cell);
        if (cell != null && parent != null) {
            var i = 0;
            var last = 0;
            var childCount = model.getChildCount(parent);
            
            for (i = 0; i < childCount; i++) {
                var child = model.getChildAt(parent, i);
                var bounds = this.getVertexBounds(child);
                if (bounds != null) {
                    var tmp = bounds.x + bounds.width / 2;
                    if (last < x && tmp > x) {
                        break;
                    }
                    last = tmp;
                }
            }
            var idx = parent.getIndex(cell);
            idx = Math.max(0, i - ((i > idx) ? 1 : 0));
            model.add(parent, cell, idx);
        }
    };
    mxPartitionLayout.prototype.execute = function(parent){
        var horizontal = this.isHorizontal();
        var model = this.graph.getModel();
        var pgeo = model.getGeometry(parent);
        
        
        if (this.graph.container != null && ((pgeo == null && model.isLayer(parent)) || parent == this.graph.getView().currentRoot)) {
            var width = this.graph.container.offsetWidth;
            var height = this.graph.container.offsetHeight;
            pgeo = new mxRectangle(0, 0, width, height);
        }
        if (pgeo != null) {
            var children = new Array();
            var childCount = model.getChildCount(parent);
            for (var i = 0; i < childCount; i++) {
                var child = model.getChildAt(parent, i);
                if (!this.isVertexIgnored(child) && this.isVertexMovable(child)) {
                    children.push(child);
                }
            }
            var n = children.length;
            if (n > 0) {
                var x0 = this.border;
                var y0 = this.border;
                var other = (horizontal) ? pgeo.height : pgeo.width;
                other -= 2 * this.border;
                var size = this.graph.getStartSize(parent);
                other -= (horizontal) ? size.height : size.width;
                x0 = x0 + size.width;
                y0 = y0 + size.height;
                var tmp = this.border + (n - 1) * this.spacing
                var value = (horizontal) ? ((pgeo.width - x0 - tmp) / n) : ((pgeo.height - y0 - tmp) / n);
                
                if (value > 0) {
                    model.beginUpdate();
                    try {
                        for (var i = 0; i < n; i++) {
                            var child = children[i];
                            var geo = model.getGeometry(child);
                            if (geo != null) {
                                geo = geo.clone();
                                geo.x = x0;
                                geo.y = y0;
                                if (horizontal) {
                                    if (this.resizeVertices) {
                                        geo.width = value;
                                        geo.height = other;
                                    }
                                    x0 += value + this.spacing;
                                }
                                else {
                                    if (this.resizeVertices) {
                                        geo.height = value;
                                        geo.width = other;
                                    }
                                    y0 += value + this.spacing;
                                }
                                model.setGeometry(child, geo);
                            }
                        }
                    }
                    finally {
                        model.endUpdate();
                    }
                }
            }
        }
    };
}
//mxCompactTreeLayout
{
    function mxCompactTreeLayout(graph, horizontal, invert){
        mxGraphLayout.call(this, graph);
        this.horizontal = (horizontal != null) ? horizontal : true;
        this.invert = (invert != null) ? invert : false;
    };
    mxCompactTreeLayout.prototype = new mxGraphLayout();
    mxCompactTreeLayout.prototype.constructor = mxCompactTreeLayout;
    mxCompactTreeLayout.prototype.horizontal = null;
    mxCompactTreeLayout.prototype.invert = null;
    mxCompactTreeLayout.prototype.resizeParent = true;
    mxCompactTreeLayout.prototype.moveTree = true;
    mxCompactTreeLayout.prototype.levelDistance = 10;
    mxCompactTreeLayout.prototype.nodeDistance = 20;
    mxCompactTreeLayout.prototype.resetEdges = true;
    mxCompactTreeLayout.prototype.isVertexIgnored = function(vertex){
        return mxGraphLayout.prototype.isVertexIgnored.apply(this, arguments) || this.graph.getConnections(vertex).length == 0;
    };
    mxCompactTreeLayout.prototype.isHorizontal = function(){
        return this.horizontal;
    };
    mxCompactTreeLayout.prototype.execute = function(parent, root){
        var model = this.graph.getModel();
        if (root == null) {
            if (this.graph.getEdges(parent, model.getParent(parent), this.invert, !this.invert, false).length > 0) {
                root = parent;
            }
            
            else {
                var roots = this.graph.findTreeRoots(parent, true, this.invert);
                if (roots.length > 0) {
                    for (var i = 0; i < roots.length; i++) {
                        if (!this.isVertexIgnored(roots[i]) && this.graph.getEdges(roots[i], null, this.invert, !this.invert, false).length > 0) {
                            root = roots[i];
                            break;
                        }
                    }
                }
            }
        }
        if (root != null) {
            parent = model.getParent(root);
            model.beginUpdate();
            try {
                var node = this.dfs(root, parent);
                if (node != null) {
                    this.layout(node);
                    var x0 = this.graph.gridSize;
                    var y0 = x0;
                    if (!this.moveTree || model.isLayer(parent)) {
                        var g = model.getGeometry(root);
                        if (g != null) {
                            x0 = g.x;
                            y0 = g.y;
                        }
                    }
                    var bounds = null;
                    if (this.isHorizontal()) {
                        bounds = this.horizontalLayout(node, x0, y0);
                    }
                    else {
                        bounds = this.verticalLayout(node, null, x0, y0);
                    }
                    if (bounds != null) {
                        var dx = 0;
                        var dy = 0;
                        if (bounds.x < 0) {
                            dx = Math.abs(x0 - bounds.x);
                        }
                        if (bounds.y < 0) {
                            dy = Math.abs(y0 - bounds.y);
                        }
                        if (parent != null) {
                            var size = this.graph.getStartSize(parent);
                            dx += size.width;
                            dy += size.height;
                            if (this.resizeParent && !this.graph.isCellCollapsed(parent)) {
                                var g = model.getGeometry(parent);
                                if (g != null) {
                                    var width = bounds.width + size.width - bounds.x + 2 * x0;
                                    var height = bounds.height + size.height - bounds.y + 2 * y0;
                                    g = g.clone();
                                    if (g.width > width) {
                                        dx += (g.width - width) / 2;
                                    }
                                    else {
                                        g.width = width;
                                    }
                                    if (g.height > height) {
                                        if (this.isHorizontal()) {
                                            dy += (g.height - height) / 2;
                                        }
                                    }
                                    else {
                                        g.height = height;
                                    }
                                    model.setGeometry(parent, g);
                                }
                            }
                        }
                        this.moveNode(node, dx, dy);
                    }
                }
            }
            finally {
                model.endUpdate();
            }
        }
    };
    mxCompactTreeLayout.prototype.moveNode = function(node, dx, dy){
        node.x += dx;
        node.y += dy;
        this.apply(node);
        var child = node.child;
        while (child != null) {
            this.moveNode(child, dx, dy);
            child = child.next;
        }
    };
    mxCompactTreeLayout.prototype.dfs = function(cell, parent, visited){
        visited = visited || new Array();
        var id = mxCellPath.create(cell);
        var node = null;
        if (cell != null && visited[id] == null && !this.isVertexIgnored(cell)) {
            visited[id] = cell;
            node = this.createNode(cell);
            var model = this.graph.getModel();
            var prev = null;
            var out = this.graph.getEdges(cell, parent, this.invert, !this.invert, false);
            for (var i = 0; i < out.length; i++) {
                var edge = out[i];
                if (!this.isEdgeIgnored(edge)) {
                    if (this.resetEdges) {
                        this.setEdgePoints(edge, null);
                    }
                    var target = this.graph.getView().getVisibleTerminal(edge, this.invert);
                    var tmp = this.dfs(target, parent, visited);
                    if (tmp != null && model.getGeometry(target) != null) {
                        if (prev == null) {
                            node.child = tmp;
                        }
                        else {
                            prev.next = tmp;
                        }
                        prev = tmp;
                    }
                }
            }
        }
        return node;
    };
    mxCompactTreeLayout.prototype.layout = function(node){
        if (node != null) {
            var child = node.child;
            while (child != null) {
                this.layout(child);
                child = child.next;
            }
            if (node.child != null) {
                this.attachParent(node, this.join(node));
            }
            else {
                this.layoutLeaf(node);
            }
        }
    };
    mxCompactTreeLayout.prototype.horizontalLayout = function(node, x0, y0, bounds){
        node.x += x0 + node.offsetX;
        node.y += y0 + node.offsetY;
        bounds = this.apply(node, bounds);
        var child = node.child;
        if (child != null) {
            bounds = this.horizontalLayout(child, node.x, node.y, bounds);
            var siblingOffset = node.y + child.offsetY;
            var s = child.next;
            while (s != null) {
                bounds = this.horizontalLayout(s, node.x + child.offsetX, siblingOffset, bounds);
                siblingOffset += s.offsetY;
                s = s.next;
            }
        }
        return bounds;
    };
    mxCompactTreeLayout.prototype.verticalLayout = function(node, parent, x0, y0, bounds){
        node.x += x0 + node.offsetY;
        node.y += y0 + node.offsetX;
        bounds = this.apply(node, bounds);
        var child = node.child;
        if (child != null) {
            bounds = this.verticalLayout(child, node, node.x, node.y, bounds);
            var siblingOffset = node.x + child.offsetY;
            var s = child.next;
            while (s != null) {
                bounds = this.verticalLayout(s, node, siblingOffset, node.y + child.offsetX, bounds);
                siblingOffset += s.offsetY;
                s = s.next;
            }
        }
        return bounds;
    };
    mxCompactTreeLayout.prototype.attachParent = function(node, height){
        var x = this.nodeDistance + this.levelDistance;
        var y2 = (height - node.width) / 2 - this.nodeDistance;
        var y1 = y2 + node.width + 2 * this.nodeDistance - height;
        node.child.offsetX = x + node.height;
        node.child.offsetY = y1;
        node.contour.upperHead = this.createLine(node.height, 0, this.createLine(x, y1, node.contour.upperHead));
        node.contour.lowerHead = this.createLine(node.height, 0, this.createLine(x, y2, node.contour.lowerHead));
    };
    mxCompactTreeLayout.prototype.layoutLeaf = function(node){
        var dist = 2 * this.nodeDistance;
        node.contour.upperTail = this.createLine(node.height + dist, 0);
        node.contour.upperHead = node.contour.upperTail;
        node.contour.lowerTail = this.createLine(0, -node.width - dist);
        node.contour.lowerHead = this.createLine(node.height + dist, 0, node.contour.lowerTail);
    };
    mxCompactTreeLayout.prototype.join = function(node){
        var dist = 2 * this.nodeDistance;
        var child = node.child;
        node.contour = child.contour;
        var h = child.width + dist;
        var sum = h;
        child = child.next;
        while (child != null) {
            var d = this.merge(node.contour, child.contour);
            child.offsetY = d + h;
            child.offsetX = 0;
            h = child.width + dist;
            sum += d + h;
            child = child.next;
        }
        return sum;
    };
    mxCompactTreeLayout.prototype.merge = function(p1, p2){
        var x = 0;
        var y = 0;
        var total = 0;
        var upper = p1.lowerHead;
        var lower = p2.upperHead;
        while (lower != null && upper != null) {
            var d = this.offset(x, y, lower.dx, lower.dy, upper.dx, upper.dy);
            y += d;
            total += d;
            if (x + lower.dx <= upper.dx) {
                x += lower.dx;
                y += lower.dy;
                lower = lower.next;
            }
            else {
                x -= upper.dx;
                y -= upper.dy;
                upper = upper.next;
            }
        }
        if (lower != null) {
            var b = this.bridge(p1.upperTail, 0, 0, lower, x, y);
            p1.upperTail = (b.next != null) ? p2.upperTail : b;
            p1.lowerTail = p2.lowerTail;
        }
        else {
            var b = this.bridge(p2.lowerTail, x, y, upper, 0, 0);
            if (b.next == null) {
                p1.lowerTail = b;
            }
        }
        p1.lowerHead = p2.lowerHead;
        return total;
    };
    mxCompactTreeLayout.prototype.offset = function(p1, p2, a1, a2, b1, b2){
        var d = 0;
        if (b1 <= p1 || p1 + a1 <= 0) {
            return 0;
        }
        var t = b1 * a2 - a1 * b2;
        if (t > 0) {
            if (p1 < 0) {
                var s = p1 * a2;
                d = s / a1 - p2;
            }
            else 
                if (p1 > 0) {
                    var s = p1 * b2;
                    d = s / b1 - p2;
                }
                else {
                    d = -p2;
                }
        }
        else 
            if (b1 < p1 + a1) {
                var s = (b1 - p1) * a2;
                d = b2 - (p2 + s / a1);
            }
            else 
                if (b1 > p1 + a1) {
                    var s = (a1 + p1) * b2;
                    d = s / b1 - (p2 + a2);
                }
                else {
                    d = b2 - (p2 + a2);
                }
        if (d > 0) {
            return d;
        }
        else {
            return 0;
        }
    };
    mxCompactTreeLayout.prototype.bridge = function(line1, x1, y1, line2, x2, y2){
        var dx = x2 + line2.dx - x1;
        var dy = 0;
        var s = 0;
        if (line2.dx == 0) {
            dy = line2.dy;
        }
        else {
            var s = dx * line2.dy;
            dy = s / line2.dx;
        }
        var r = this.createLine(dx, dy, line2.next);
        line1.next = this.createLine(0, y2 + line2.dy - dy - y1, r);
        return r;
    };
    mxCompactTreeLayout.prototype.createNode = function(cell){
        var node = new Object();
        node.cell = cell;
        node.x = 0;
        node.y = 0;
        node.width = 0;
        node.height = 0;
        var geo = this.getVertexBounds(cell);
        if (geo != null) {
            if (this.isHorizontal()) {
                node.width = geo.height;
                node.height = geo.width;
            }
            else {
                node.width = geo.width;
                node.height = geo.height;
            }
        }
        node.offsetX = 0;
        node.offsetY = 0;
        node.contour = new Object();
        return node;
    };
    mxCompactTreeLayout.prototype.apply = function(node, bounds){
        var g = this.graph.getModel().getGeometry(node.cell);
        if (node.cell != null && g != null) {
            if (this.isVertexMovable(node.cell)) {
                g = this.setVertexLocation(node.cell, node.x, node.y);
            }
            if (bounds == null) {
                bounds = new mxRectangle(g.x, g.y, g.width, g.height);
            }
            else {
                bounds = new mxRectangle(Math.min(bounds.x, g.x), Math.min(bounds.y, g.y), Math.max(bounds.x + bounds.width, g.x + g.width), Math.max(bounds.y + bounds.height, g.y + g.height));
            }
        }
        return bounds;
    };
    mxCompactTreeLayout.prototype.createLine = function(dx, dy, next){
        var line = new Object();
        line.dx = dx;
        line.dy = dy;
        line.next = next;
        return line;
    };
}
//mxFastOrganicLayout
{
    function mxFastOrganicLayout(graph){
        mxGraphLayout.call(this, graph);
    };
    mxFastOrganicLayout.prototype = new mxGraphLayout();
    mxFastOrganicLayout.prototype.constructor = mxFastOrganicLayout;
    mxFastOrganicLayout.prototype.useInputOrigin = true;
    mxFastOrganicLayout.prototype.resetEdges = true;
    mxFastOrganicLayout.prototype.disableEdgeStyle = true;
    mxFastOrganicLayout.prototype.forceConstant = 50;
    mxFastOrganicLayout.prototype.forceConstantSquared = 0;
    mxFastOrganicLayout.prototype.minDistanceLimit = 2;
    mxFastOrganicLayout.prototype.minDistanceLimitSquared = 4;
    mxFastOrganicLayout.prototype.initialTemp = 200;
    mxFastOrganicLayout.prototype.temperature = 0;
    mxFastOrganicLayout.prototype.maxIterations = 0;
    mxFastOrganicLayout.prototype.iteration = 0;
    mxFastOrganicLayout.prototype.vertexArray;
    mxFastOrganicLayout.prototype.dispX;
    mxFastOrganicLayout.prototype.dispY;
    mxFastOrganicLayout.prototype.cellLocation;
    mxFastOrganicLayout.prototype.radius;
    mxFastOrganicLayout.prototype.radiusSquared;
    mxFastOrganicLayout.prototype.isMoveable;
    mxFastOrganicLayout.prototype.neighbours;
    mxFastOrganicLayout.prototype.indices;
    mxFastOrganicLayout.prototype.allowedToRun = true;
    mxFastOrganicLayout.prototype.isVertexIgnored = function(vertex){
        return mxGraphLayout.prototype.isVertexIgnored.apply(this, arguments) || this.graph.getConnections(vertex).length == 0;
    };
    mxFastOrganicLayout.prototype.execute = function(parent){
        var model = this.graph.getModel();
        this.vertexArray = new Array();
        var cells = this.graph.getChildVertices(parent);
        for (var i = 0; i < cells.length; i++) {
            if (!this.isVertexIgnored(cells[i])) {
                this.vertexArray.push(cells[i]);
            }
        }
        var initialBounds = (this.useInputOrigin) ? this.graph.view.getBounds(this.vertexArray) : null;
        var n = this.vertexArray.length;
        this.indices = new Array();
        this.dispX = new Array();
        this.dispY = new Array();
        this.cellLocation = new Array();
        this.isMoveable = new Array();
        this.neighbours = new Array();
        this.radius = new Array();
        this.radiusSquared = new Array();
        if (this.forceConstant < 0.001) {
            this.forceConstant = 0.001;
        }
        this.forceConstantSquared = this.forceConstant * this.forceConstant;
        
        
        
        for (var i = 0; i < this.vertexArray.length; i++) {
            var vertex = this.vertexArray[i];
            this.cellLocation[i] = new Array();
            var id = mxCellPath.create(vertex);
            this.indices[id] = i;
            var bounds = this.getVertexBounds(vertex);
            
            var width = bounds.width;
            var height = bounds.height;
            var x = bounds.x;
            var y = bounds.y;
            this.cellLocation[i][0] = x + width / 2.0;
            this.cellLocation[i][1] = y + height / 2.0;
            this.radius[i] = Math.min(width, height);
            this.radiusSquared[i] = this.radius[i] * this.radius[i];
        }
        
        model.beginUpdate();
        try {
            for (var i = 0; i < n; i++) {
                this.dispX[i] = 0;
                this.dispY[i] = 0;
                this.isMoveable[i] = this.isVertexMovable(this.vertexArray[i]);
                
                
                var edges = this.graph.getConnections(this.vertexArray[i], parent);
                var cells = this.graph.getOpposites(edges, this.vertexArray[i]);
                this.neighbours[i] = new Array();
                for (var j = 0; j < cells.length; j++) {
                    if (this.resetEdges) {
                        this.graph.resetEdge(edges[j]);
                    }
                    if (this.disableEdgeStyle) {
                        this.setEdgeStyleEnabled(edges[j], false);
                    }
                    var id = mxCellPath.create(cells[j]);
                    var index = this.indices[id];
                    
                    if (index != null) {
                        this.neighbours[i][j] = index;
                    }
                    
                    
                    
                    else {
                        this.neighbours[i][j] = i;
                    }
                }
            }
            this.temperature = this.initialTemp;
            if (this.maxIterations == 0) {
                this.maxIterations = 20 * Math.sqrt(n);
            }
            for (this.iteration = 0; this.iteration < this.maxIterations; this.iteration++) {
                if (!this.allowedToRun) {
                    return;
                }
                this.calcRepulsion();
                this.calcAttraction();
                this.calcPositions();
                this.reduceTemperature();
            }
            var minx = null;
            var miny = null;
            for (var i = 0; i < this.vertexArray.length; i++) {
                var vertex = this.vertexArray[i];
                if (this.isVertexMovable(vertex)) {
                    var bounds = this.getVertexBounds(vertex);
                    if (bounds != null) {
                        this.cellLocation[i][0] -= bounds.width / 2.0;
                        this.cellLocation[i][1] -= bounds.height / 2.0;
                        var x = this.graph.snap(this.cellLocation[i][0]);
                        var y = this.graph.snap(this.cellLocation[i][1]);
                        this.setVertexLocation(vertex, x, y);
                        if (minx == null) {
                            minx = x;
                        }
                        else {
                            minx = Math.min(minx, x);
                        }
                        if (miny == null) {
                            miny = y;
                        }
                        else {
                            miny = Math.min(miny, y);
                        }
                    }
                }
            }
            
            
            var dx = -(minx || 0) + 1;
            var dy = -(miny || 0) + 1;
            if (initialBounds != null) {
                dx += initialBounds.x;
                dy += initialBounds.y;
            }
            this.graph.moveCells(this.vertexArray, dx, dy);
        }
        finally {
            model.endUpdate();
        }
    };
    mxFastOrganicLayout.prototype.calcPositions = function(){
        for (var index = 0; index < this.vertexArray.length; index++) {
            if (this.isMoveable[index]) {
            
                var deltaLength = Math.sqrt(this.dispX[index] * this.dispX[index] + this.dispY[index] * this.dispY[index]);
                if (deltaLength < 0.001) {
                    deltaLength = 0.001;
                }
                
                var newXDisp = this.dispX[index] / deltaLength * Math.min(deltaLength, this.temperature);
                var newYDisp = this.dispY[index] / deltaLength * Math.min(deltaLength, this.temperature);
                this.dispX[index] = 0;
                this.dispY[index] = 0;
                this.cellLocation[index][0] += newXDisp;
                this.cellLocation[index][1] += newYDisp;
            }
        }
    };
    mxFastOrganicLayout.prototype.calcAttraction = function(){
    
        for (var i = 0; i < this.vertexArray.length; i++) {
            for (var k = 0; k < this.neighbours[i].length; k++) {
                var j = this.neighbours[i][k];
                if (i != j && this.isMoveable[i] && this.isMoveable[j]) {
                    var xDelta = this.cellLocation[i][0] - this.cellLocation[j][0];
                    var yDelta = this.cellLocation[i][1] - this.cellLocation[j][1];
                    var deltaLengthSquared = xDelta * xDelta + yDelta * yDelta - this.radiusSquared[i] - this.radiusSquared[j];
                    if (deltaLengthSquared < this.minDistanceLimitSquared) {
                        deltaLengthSquared = this.minDistanceLimitSquared;
                    }
                    var deltaLength = Math.sqrt(deltaLengthSquared);
                    var force = (deltaLengthSquared) / this.forceConstant;
                    var displacementX = (xDelta / deltaLength) * force;
                    var displacementY = (yDelta / deltaLength) * force;
                    this.dispX[i] -= displacementX;
                    this.dispY[i] -= displacementY;
                    this.dispX[j] += displacementX;
                    this.dispY[j] += displacementY;
                }
            }
        }
    };
    mxFastOrganicLayout.prototype.calcRepulsion = function(){
        var vertexCount = this.vertexArray.length;
        for (var i = 0; i < vertexCount; i++) {
            for (var j = i; j < vertexCount; j++) {
                if (!this.allowedToRun) {
                    return;
                }
                if (j != i && this.isMoveable[i] && this.isMoveable[j]) {
                    var xDelta = this.cellLocation[i][0] - this.cellLocation[j][0];
                    var yDelta = this.cellLocation[i][1] - this.cellLocation[j][1];
                    if (xDelta == 0) {
                        xDelta = 0.01 + Math.random();
                    }
                    if (yDelta == 0) {
                        yDelta = 0.01 + Math.random();
                    }
                    var deltaLength = Math.sqrt((xDelta * xDelta) + (yDelta * yDelta));
                    var deltaLengthWithRadius = deltaLength - this.radius[i] - this.radius[j];
                    if (deltaLengthWithRadius < this.minDistanceLimit) {
                        deltaLengthWithRadius = this.minDistanceLimit;
                    }
                    var force = this.forceConstantSquared / deltaLengthWithRadius;
                    var displacementX = (xDelta / deltaLength) * force;
                    var displacementY = (yDelta / deltaLength) * force;
                    this.dispX[i] += displacementX;
                    this.dispY[i] += displacementY;
                    this.dispX[j] -= displacementX;
                    this.dispY[j] -= displacementY;
                }
            }
        }
    };
    mxFastOrganicLayout.prototype.reduceTemperature = function(){
        this.temperature = this.initialTemp * (1.0 - this.iteration / this.maxIterations);
    };
}
//mxCircleLayout
{
    function mxCircleLayout(graph, radius){
        mxGraphLayout.call(this, graph);
        this.radius = (radius != null) ? radius : 100;
    };
    mxCircleLayout.prototype = new mxGraphLayout();
    mxCircleLayout.prototype.constructor = mxCircleLayout;
    mxCircleLayout.prototype.radius = null;
    mxCircleLayout.prototype.moveCircle = false;
    mxCircleLayout.prototype.x0 = 0;
    mxCircleLayout.prototype.y0 = 0;
    mxCircleLayout.prototype.resetEdges = true;
    mxCircleLayout.prototype.disableEdgeStyle = true;
    mxCircleLayout.prototype.execute = function(parent){
        var model = this.graph.getModel();
        
        
        model.beginUpdate();
        try {
        
            var max = 0;
            var top = null;
            var left = null;
            var vertices = new Array();
            var childCount = model.getChildCount(parent);
            for (var i = 0; i < childCount; i++) {
                var cell = model.getChildAt(parent, i);
                if (!this.isVertexIgnored(cell)) {
                    vertices.push(cell);
                    var bounds = this.getVertexBounds(cell);
                    if (top == null) {
                        top = bounds.y;
                    }
                    else {
                        top = Math.min(top, bounds.y);
                    }
                    if (left == null) {
                        left = bounds.x;
                    }
                    else {
                        left = Math.min(left, bounds.x);
                    }
                    max = Math.max(max, Math.max(bounds.width, bounds.height));
                }
                else 
                    if (!this.isEdgeIgnored(cell)) {
                        if (this.resetEdges) {
                            this.graph.resetEdge(cell);
                        }
                        if (this.disableEdgeStyle) {
                            this.setEdgeStyleEnabled(cell, false);
                        }
                    }
            }
            var vertexCount = vertices.length;
            var r = Math.max(vertexCount * max / Math.PI, this.radius);
            if (this.moveCircle) {
                top = this.x0;
                left = this.y0;
            }
            this.circle(vertices, r, left, top);
        }
        finally {
            model.endUpdate();
        }
    };
    mxCircleLayout.prototype.circle = function(vertices, r, left, top){
        var vertexCount = vertices.length;
        var phi = 2 * Math.PI / vertexCount;
        for (var i = 0; i < vertexCount; i++) {
            if (this.isVertexMovable(vertices[i])) {
                this.setVertexLocation(vertices[i], left + r + r * Math.sin(i * phi), top + r + r * Math.cos(i * phi));
            }
        }
    };
}
//mxParallelEdgeLayout
{
    function mxParallelEdgeLayout(graph){
        mxGraphLayout.call(this, graph);
    };
    mxParallelEdgeLayout.prototype = new mxGraphLayout();
    mxParallelEdgeLayout.prototype.constructor = mxParallelEdgeLayout;
    mxParallelEdgeLayout.prototype.spacing = 20;
    mxParallelEdgeLayout.prototype.execute = function(parent){
        var lookup = this.findParallels(parent);
        this.graph.model.beginUpdate();
        try {
            for (var i in lookup) {
                var parallels = lookup[i];
                if (parallels.length > 1) {
                    this.layout(parallels);
                }
            }
        }
        finally {
            this.graph.model.endUpdate();
        }
    };
    mxParallelEdgeLayout.prototype.findParallels = function(parent){
        var view = this.graph.getView();
        var model = this.graph.getModel();
        var lookup = new Array();
        var childCount = model.getChildCount(parent);
        for (var i = 0; i < childCount; i++) {
            var child = model.getChildAt(parent, i);
            if (!this.isEdgeIgnored(child)) {
                var id = this.getEdgeId(child);
                if (id != null) {
                    if (lookup[id] == null) {
                        lookup[id] = new Array();
                    }
                    lookup[id].push(child);
                }
            }
        }
        return lookup;
    };
    mxParallelEdgeLayout.prototype.getEdgeId = function(edge){
        var view = this.graph.getView();
        var src = view.getVisibleTerminal(edge, true);
        var trg = view.getVisibleTerminal(edge, false);
        if (src != null && trg != null) {
            src = mxCellPath.create(src);
            trg = mxCellPath.create(trg);
            return (src > trg) ? trg + '-' + src : src + '-' + trg;
        }
        return null;
    };
    mxParallelEdgeLayout.prototype.layout = function(parallels){
        var edge = parallels[0];
        var view = this.graph.getView();
        var model = this.graph.getModel();
        var src = model.getGeometry(model.getTerminal(edge, true));
        var trg = model.getGeometry(model.getTerminal(edge, false));
        if (src == trg) {
            var x0 = src.x + src.width + this.spacing;
            var y0 = src.y + src.height / 2;
            for (var i = 0; i < parallels.length; i++) {
                this.route(parallels[i], x0, y0);
                x0 += this.spacing;
            }
        }
        else 
            if (src != null && trg != null) {
                var scx = src.x + src.width / 2;
                var scy = src.y + src.height / 2;
                var tcx = trg.x + trg.width / 2;
                var tcy = trg.y + trg.height / 2;
                var dx = tcx - scx;
                var dy = tcy - scy;
                var len = Math.sqrt(dx * dx + dy * dy);
                var x0 = scx + dx / 2;
                var y0 = scy + dy / 2;
                var nx = dy * this.spacing / len;
                var ny = dx * this.spacing / len;
                x0 += nx * (parallels.length - 1) / 2;
                y0 -= ny * (parallels.length - 1) / 2;
                for (var i = 0; i < parallels.length; i++) {
                    this.route(parallels[i], x0, y0);
                    x0 -= nx;
                    y0 += ny;
                }
            }
    };
    mxParallelEdgeLayout.prototype.route = function(edge, x, y){
        if (this.graph.isCellMovable(edge)) {
            this.setEdgePoints(edge, [new mxPoint(x, y)]);
        }
    };
}
//mxCompositeLayout
{
    function mxCompositeLayout(graph, layouts, master){
        mxGraphLayout.call(this, graph);
        this.layouts = layouts;
        this.master = master;
    };
    mxCompositeLayout.prototype = new mxGraphLayout();
    mxCompositeLayout.prototype.constructor = mxCompositeLayout;
    mxCompositeLayout.prototype.layouts = null;
    mxCompositeLayout.prototype.master = null;
    mxCompositeLayout.prototype.moveCell = function(cell, x, y){
        if (this.master != null) {
            this.master.move.apply(this.master, arguments);
        }
        else {
            this.layouts[0].move.apply(this.layouts[0], arguments);
        }
    };
    mxCompositeLayout.prototype.execute = function(parent){
        var model = this.graph.getModel();
        model.beginUpdate();
        try {
            for (var i = 0; i < this.layouts.length; i++) {
                this.layouts[i].execute.apply(this.layouts[i], arguments);
            }
        }
        finally {
            model.endUpdate();
        }
    };
}
//mxEdgeLabelLayout
{
    function mxEdgeLabelLayout(graph, radius){
        mxGraphLayout.call(this, graph);
    };
    mxEdgeLabelLayout.prototype = new mxGraphLayout();
    mxEdgeLabelLayout.prototype.constructor = mxEdgeLabelLayout;
    mxEdgeLabelLayout.prototype.execute = function(parent){
        var view = this.graph.view;
        var model = this.graph.getModel();
        var edges = new Array();
        var vertices = new Array();
        var childCount = model.getChildCount(parent);
        for (var i = 0; i < childCount; i++) {
            var cell = model.getChildAt(parent, i);
            var state = view.getState(cell);
            if (state != null) {
                if (!this.isVertexIgnored(cell)) {
                    vertices.push(state);
                }
                else 
                    if (!this.isEdgeIgnored(cell)) {
                        edges.push(state);
                    }
            }
        }
        this.placeLabels(vertices, edges);
    };
    mxEdgeLabelLayout.prototype.placeLabels = function(v, e){
        var model = this.graph.getModel();
        
        
        model.beginUpdate();
        try {
            for (var i = 0; i < e.length; i++) {
                var edge = e[i];
                if (edge != null && edge.text != null && edge.text.boundingBox != null) {
                    for (var j = 0; j < v.length; j++) {
                        var vertex = v[j];
                        if (vertex != null) {
                            this.avoid(edge, vertex);
                        }
                    }
                }
            }
        }
        finally {
            model.endUpdate();
        }
    };
    mxEdgeLabelLayout.prototype.avoid = function(edge, vertex){
        var model = this.graph.getModel();
        var labRect = edge.text.boundingBox;
        if (mxUtils.intersects(labRect, vertex)) {
            var dy1 = -labRect.y - labRect.height + vertex.y;
            var dy2 = -labRect.y + vertex.y + vertex.height;
            var dy = (Math.abs(dy1) < Math.abs(dy2)) ? dy1 : dy2;
            var dx1 = -labRect.x - labRect.width + vertex.x;
            var dx2 = -labRect.x + vertex.x + vertex.width;
            var dx = (Math.abs(dx1) < Math.abs(dx2)) ? dx1 : dx2;
            if (Math.abs(dx) < Math.abs(dy)) {
                dy = 0;
            }
            else {
                dx = 0;
            }
            var g = model.getGeometry(edge.cell);
            if (g != null) {
                g = g.clone();
                if (g.offset != null) {
                    g.offset.x += dx;
                    g.offset.y += dy;
                }
                else {
                    g.offset = new mxPoint(dx, dy);
                }
                model.setGeometry(edge.cell, g);
            }
        }
    };
}
//mxGraphAbstractHierarchyCell
{
    function mxGraphAbstractHierarchyCell(){
        this.x = new Array();
        this.y = new Array();
        this.temp = new Array();
    };
    mxGraphAbstractHierarchyCell.prototype.maxRank = -1;
    mxGraphAbstractHierarchyCell.prototype.minRank = -1;
    mxGraphAbstractHierarchyCell.prototype.x = null;
    mxGraphAbstractHierarchyCell.prototype.y = null;
    mxGraphAbstractHierarchyCell.prototype.width = 0;
    mxGraphAbstractHierarchyCell.prototype.height = 0;
    mxGraphAbstractHierarchyCell.prototype.nextLayerConnectedCells = null;
    mxGraphAbstractHierarchyCell.prototype.previousLayerConnectedCells = null;
    mxGraphAbstractHierarchyCell.prototype.temp = null;
    mxGraphAbstractHierarchyCell.prototype.getNextLayerConnectedCells = function(layer){
        return null;
    };
    mxGraphAbstractHierarchyCell.prototype.getPreviousLayerConnectedCells = function(layer){
        return null;
    };
    mxGraphAbstractHierarchyCell.prototype.isEdge = function(){
        return false;
    };
    mxGraphAbstractHierarchyCell.prototype.isVertex = function(){
        return false;
    };
    mxGraphAbstractHierarchyCell.prototype.getGeneralPurposeVariable = function(layer){
        return null;
    };
    mxGraphAbstractHierarchyCell.prototype.setGeneralPurposeVariable = function(layer, value){
        return null;
    };
    mxGraphAbstractHierarchyCell.prototype.setX = function(layer, value){
        if (this.isVertex()) {
            this.x[0] = value;
        }
        else 
            if (this.isEdge()) {
                this.x[layer - this.minRank - 1] = value;
            }
    };
    mxGraphAbstractHierarchyCell.prototype.getX = function(layer){
        if (this.isVertex()) {
            return this.x[0];
        }
        else 
            if (this.isEdge()) {
                return this.x[layer - this.minRank - 1];
            }
        return 0.0;
    };
    mxGraphAbstractHierarchyCell.prototype.setY = function(layer, value){
        if (this.isVertex()) {
            this.y[0] = value;
        }
        else 
            if (this.isEdge()) {
                this.y[layer - this.minRank - 1] = value;
            }
    };
}
//mxGraphHierarchyNode
{
    function mxGraphHierarchyNode(cell){
        mxGraphAbstractHierarchyCell.apply(this, arguments);
        this.cell = cell;
    };
    mxGraphHierarchyNode.prototype = new mxGraphAbstractHierarchyCell();
    mxGraphHierarchyNode.prototype.constructor = mxGraphHierarchyNode;
    mxGraphHierarchyNode.prototype.cell = null;
    mxGraphHierarchyNode.prototype.connectsAsTarget = new Array();
    mxGraphHierarchyNode.prototype.connectsAsSource = new Array();
    mxGraphHierarchyNode.prototype.hashCode = false;
    mxGraphHierarchyNode.prototype.getRankValue = function(layer){
        return this.maxRank;
    };
    mxGraphHierarchyNode.prototype.getNextLayerConnectedCells = function(layer){
        if (this.nextLayerConnectedCells == null) {
            this.nextLayerConnectedCells = new Array();
            this.nextLayerConnectedCells[0] = new Array();
            for (var i = 0; i < this.connectsAsTarget.length; i++) {
                var edge = this.connectsAsTarget[i];
                if (edge.maxRank == -1 || edge.maxRank == layer + 1) {
                
                    this.nextLayerConnectedCells[0].push(edge.source);
                }
                else {
                    this.nextLayerConnectedCells[0].push(edge);
                }
            }
        }
        return this.nextLayerConnectedCells[0];
    };
    mxGraphHierarchyNode.prototype.getPreviousLayerConnectedCells = function(layer){
        if (this.previousLayerConnectedCells == null) {
            this.previousLayerConnectedCells = new Array();
            this.previousLayerConnectedCells[0] = new Array();
            for (var i = 0; i < this.connectsAsSource.length; i++) {
                var edge = this.connectsAsSource[i];
                if (edge.minRank == -1 || edge.minRank == layer - 1) {
                    this.previousLayerConnectedCells[0].push(edge.target);
                }
                else {
                    this.previousLayerConnectedCells[0].push(edge);
                }
            }
        }
        return this.previousLayerConnectedCells[0];
    };
    mxGraphHierarchyNode.prototype.isVertex = function(){
        return true;
    };
    mxGraphHierarchyNode.prototype.getGeneralPurposeVariable = function(layer){
        return this.temp[0];
    };
    mxGraphHierarchyNode.prototype.setGeneralPurposeVariable = function(layer, value){
        this.temp[0] = value;
    };
    mxGraphHierarchyNode.prototype.isAncestor = function(otherNode){
    
        if (otherNode != null && this.hashCode != null && otherNode.hashCode != null && this.hashCode.length < otherNode.hashCode.length) {
            if (this.hashCode == otherNode.hashCode) {
                return true;
            }
            if (this.hashCode == null || this.hashCode == null) {
                return false;
            }
            
            
            
            for (var i = 0; i < this.hashCode.length; i++) {
                if (this.hashCode[i] != otherNode.hashCode[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    };
}
//mxGraphHierarchyEdge
{
    function mxGraphHierarchyEdge(edges){
        mxGraphAbstractHierarchyCell.apply(this, arguments);
        this.edges = edges;
    };
    mxGraphHierarchyEdge.prototype = new mxGraphAbstractHierarchyCell();
    mxGraphHierarchyEdge.prototype.constructor = mxGraphHierarchyEdge;
    mxGraphHierarchyEdge.prototype.edges = null;
    mxGraphHierarchyEdge.prototype.source = null;
    mxGraphHierarchyEdge.prototype.target = null;
    mxGraphHierarchyEdge.prototype.isReversed = false;
    mxGraphHierarchyEdge.prototype.invert = function(layer){
        var temp = this.source;
        this.source = this.target;
        this.target = temp;
        this.isReversed = !this.isReversed;
    };
    mxGraphHierarchyEdge.prototype.getNextLayerConnectedCells = function(layer){
        if (this.nextLayerConnectedCells == null) {
            this.nextLayerConnectedCells = new Array();
            for (var i = 0; i < this.temp.length; i++) {
                this.nextLayerConnectedCells[i] = new Array();
                if (i == this.nextLayerConnectedCells.length - 1) {
                    this.nextLayerConnectedCells[i].push(this.source);
                }
                else {
                    this.nextLayerConnectedCells[i].push(this);
                }
            }
        }
        return this.nextLayerConnectedCells[layer - this.minRank - 1];
    };
    mxGraphHierarchyEdge.prototype.getPreviousLayerConnectedCells = function(layer){
        if (this.previousLayerConnectedCells == null) {
            this.previousLayerConnectedCells = new Array();
            for (var i = 0; i < this.temp.length; i++) {
                this.previousLayerConnectedCells[i] = new Array();
                if (i == 0) {
                    this.previousLayerConnectedCells[i].push(this.target);
                }
                else {
                    this.previousLayerConnectedCells[i].push(this);
                }
            }
        }
        return this.previousLayerConnectedCells[layer - this.minRank - 1];
    };
    mxGraphHierarchyEdge.prototype.isEdge = function(){
        return true;
    };
    mxGraphHierarchyEdge.prototype.getGeneralPurposeVariable = function(layer){
        return this.temp[layer - this.minRank - 1];
    };
    mxGraphHierarchyEdge.prototype.setGeneralPurposeVariable = function(layer, value){
        this.temp[layer - this.minRank - 1] = value;
    };
}
//mxGraphHierarchyModel
{
    function mxGraphHierarchyModel(layout, vertices, roots, parent, ordered, deterministic, tightenToSource){
        var graph = layout.getGraph();
        this.deterministic = deterministic;
        this.tightenToSource = tightenToSource;
        this.roots = roots;
        this.parent = parent;
        
        this.vertexMapper = new Object();
        this.edgeMapper = new Object();
        this.maxRank = 0;
        var internalVertices = new Array();
        if (vertices == null) {
            vertices = this.graph.getChildVertices(parent);
        }
        if (ordered) {
            this.formOrderedHierarchy(layout, vertices, parent);
        }
        else {
        
        
            this.createInternalCells(layout, vertices, internalVertices);
            
            for (var i = 0; i < vertices.length; i++) {
                var edges = internalVertices[i].connectsAsSource;
                for (var j = 0; j < edges.length; j++) {
                    var internalEdge = edges[j];
                    var realEdges = internalEdge.edges;
                    for (var k = 0; k < realEdges.length; k++) {
                        var realEdge = realEdges[k];
                        var targetCell = graph.getView().getVisibleTerminal(realEdge, false);
                        var targetCellId = mxCellPath.create(targetCell);
                        var internalTargetCell = this.vertexMapper[targetCellId];
                        if (internalTargetCell != null && internalVertices[i] != internalTargetCell) {
                            internalEdge.target = internalTargetCell;
                            if (internalTargetCell.connectsAsTarget.length == 0) {
                                internalTargetCell.connectsAsTarget = new Array();
                            }
                            if (mxUtils.indexOf(internalTargetCell.connectsAsTarget, internalEdge) < 0) {
                                internalTargetCell.connectsAsTarget.push(internalEdge);
                            }
                        }
                    }
                }
                
                internalVertices[i].temp[0] = 1;
            }
        }
    };
    mxGraphHierarchyModel.prototype.sinksAtLayerZero = true;
    mxGraphHierarchyModel.prototype.maxRank = null;
    mxGraphHierarchyModel.prototype.vertexMapper = null;
    mxGraphHierarchyModel.prototype.edgeMapper = null;
    mxGraphHierarchyModel.prototype.ranks = null;
    mxGraphHierarchyModel.prototype.roots = null;
    mxGraphHierarchyModel.prototype.parent = null;
    mxGraphHierarchyModel.prototype.dfsCount = 0;
    mxGraphHierarchyModel.prototype.deterministic;
    mxGraphHierarchyModel.prototype.tightenToSource = false;
    mxGraphHierarchyModel.prototype.formOrderedHierarchy = function(layout, vertices, parent){
        var graph = layout.getGraph();
        this.createInternalCells(layout, vertices, internalVertices);
        
        
        
        
        
        var tempList = new Array();
        for (var i = 0; i < vertices.length; i++) {
            var edges = internalVertices[i].connectsAsSource;
            for (var j = 0; j < edges.length; j++) {
                var internalEdge = edges[j];
                var realEdges = internalEdge.edges;
                for (var k = 0; k < realEdges.length; k++) {
                    var realEdge = realEdges[k];
                    var targetCell = this.graph.getView().getVisibleTerminal(realEdge, false);
                    var targetCellId = mxCellPath.create(targetCell);
                    var internalTargetCell = vertexMapper[targetCellId];
                    if (internalTargetCell != null && internalVertices[i] != internalTargetCell) {
                        internalEdge.target = internalTargetCell;
                        if (internalTargetCell.connectsAsTarget.length == 0) {
                            internalTargetCell.connectsAsTarget = new Array();
                        }
                        
                        if (internalTargetCell.temp[0] == 1) {
                            internalEdge.invert();
                            internalTargetCell.connectsAsSource.push(internalEdge);
                            tempList.push(internalEdge);
                            if (mxUtils.indexOf(internalVertices[i].connectsAsTarget, internalEdge) < 0) {
                                internalVertices[i].connectsAsTarget.push(internalEdge);
                            }
                        }
                        else {
                            if (mxUtils.indexOf(internalTargetCell.connectsAsTarget, internalEdge) < 0) {
                                internalTargetCell.connectsAsTarget.push(internalEdge);
                            }
                        }
                    }
                }
            }
            for (var j = 0; j < tempList.length; j++) {
                var tmp = tempList[j];
                mxUtils.remove(tmp, internalVertices[i].connectsAsSource);
            }
            tempList = new Array();
            
            internalVertices[i].temp[0] = 1;
        }
    };
    mxGraphHierarchyModel.prototype.createInternalCells = function(layout, vertices, internalVertices){
        var graph = layout.getGraph();
        for (var i = 0; i < vertices.length; i++) {
            internalVertices[i] = new mxGraphHierarchyNode(vertices[i]);
            var vertexId = mxCellPath.create(vertices[i]);
            this.vertexMapper[vertexId] = internalVertices[i];
            
            var conns = graph.getConnections(vertices[i], this.parent);
            var outgoingCells = graph.getOpposites(conns, vertices[i]);
            internalVertices[i].connectsAsSource = new Array();
            
            
            for (var j = 0; j < outgoingCells.length; j++) {
                var cell = outgoingCells[j];
                if (cell != vertices[i] && !layout.isVertexIgnored(cell)) {
                
                
                    var edges = graph.getEdgesBetween(vertices[i], cell, true);
                    if (edges != null && edges.length > 0) {
                        var internalEdge = new mxGraphHierarchyEdge(edges);
                        for (var k = 0; k < edges.length; k++) {
                            var edge = edges[k];
                            var edgeId = mxCellPath.create(edge);
                            this.edgeMapper[edgeId] = internalEdge;
                            
                            graph.resetEdge(edge);
                            if (layout.disableEdgeStyle) {
                                layout.setEdgeStyleEnabled(edge, false);
                            }
                        }
                        internalEdge.source = internalVertices[i];
                        if (mxUtils.indexOf(internalVertices[i].connectsAsSource, internalEdge) < 0) {
                            internalVertices[i].connectsAsSource.push(internalEdge);
                        }
                    }
                }
            }
            internalVertices[i].temp[0] = 0;
        }
    };
    mxGraphHierarchyModel.prototype.initialRank = function(startAtSinks){
        sinksAtLayerZero = startAtSinks;
        var startNodes = null;
        if (!startAtSinks && this.roots != null) {
            startNodes = this.roots.slice();
        }
        else {
            startNodes = new Array();
        }
        if (startAtSinks) {
            for (var key in this.vertexMapper) {
                var internalNode = this.vertexMapper[key];
                if (internalNode.connectsAsSource == null || internalNode.connectsAsSource.length == 0) {
                    startNodes.push(internalNode);
                }
                internalNode.temp[0] = -1;
            }
            if (startNodes.length == 0) {
                startAtSinks = false;
            }
        }
        var startNodesCopy = startNodes.slice();
        while (startNodes.length > 0) {
            var internalNode = startNodes[0];
            var layerDeterminingEdges;
            var edgesToBeMarked;
            if (startAtSinks) {
                layerDeterminingEdges = internalNode.connectsAsSource;
                edgesToBeMarked = internalNode.connectsAsTarget;
            }
            else {
                layerDeterminingEdges = internalNode.connectsAsTarget;
                edgesToBeMarked = internalNode.connectsAsSource;
            }
            
            var allEdgesScanned = true;
            
            
            var minimumLayer = 0;
            for (var i = 0; i < layerDeterminingEdges.length; i++) {
                var internalEdge = layerDeterminingEdges[i];
                if (internalEdge.temp[0] == 5270620) {
                
                    var otherNode;
                    if (startAtSinks) {
                        otherNode = internalEdge.target;
                    }
                    else {
                        otherNode = internalEdge.source;
                    }
                    minimumLayer = Math.max(minimumLayer, otherNode.temp[0] + 1);
                }
                else {
                    allEdgesScanned = false;
                    break;
                }
            }
            
            if (allEdgesScanned) {
                internalNode.temp[0] = minimumLayer;
                this.maxRank = Math.max(this.maxRank, minimumLayer);
                if (edgesToBeMarked != null) {
                    for (var i = 0; i < edgesToBeMarked.length; i++) {
                        var internalEdge = edgesToBeMarked[i];
                        internalEdge.temp[0] = 5270620;
                        
                        var otherNode;
                        if (startAtSinks) {
                            otherNode = internalEdge.source;
                        }
                        else {
                            otherNode = internalEdge.target;
                        }
                        if (otherNode.temp[0] == -1) {
                            startNodes.push(otherNode);
                            
                            
                            
                            otherNode.temp[0] = -2;
                        }
                    }
                }
                startNodes.shift();
            }
            else {
            
                var removedCell = startNodes.shift();
                startNodes.push(internalNode);
                if (removedCell == internalNode && startNodes.length == 1) {
                
                
                
                    break;
                }
            }
        }
        sinksAtLayerZero = startAtSinks;
        if (startAtSinks) {
            if (this.tightenToSource) {
                for (var i = 0; i < startNodesCopy.length; i++) {
                    var internalNode = startNodesCopy[i];
                    var currentMinLayer = 1000000;
                    var layerDeterminingEdges = internalNode.connectsAsTarget;
                    for (var j = 0; j < internalNode.connectsAsTarget.length; j++) {
                        var internalEdge = internalNode.connectsAsTarget[j];
                        var otherNode = internalEdge.source;
                        internalNode.temp[0] = Math.min(currentMinLayer, otherNode.temp[0] - 1);
                        currentMinLayer = internalNode.temp[0];
                    }
                }
            }
        }
    };
    mxGraphHierarchyModel.prototype.fixRanks = function(){
        var rankList = new Array();
        this.ranks = new Array();
        for (var i = 0; i < this.maxRank + 1; i++) {
            rankList[i] = new Array();
            this.ranks[i] = rankList[i];
        }
        
        
        var rootsArray = null;
        if (this.roots != null) {
            var oldRootsArray = this.roots;
            rootsArray = new Array();
            for (var i = 0; i < oldRootsArray.length; i++) {
                var cell = oldRootsArray[i];
                var cellId = mxCellPath.create(cell);
                var internalNode = this.vertexMapper[cellId];
                rootsArray[i] = internalNode;
            }
        }
        this.visit(function(parent, node, edge, layer, seen){
            if (seen == 0 && node.maxRank < 0 && node.minRank < 0) {
                rankList[node.temp[0]].push(node);
                node.maxRank = node.temp[0];
                node.minRank = node.temp[0];
                node.temp[0] = rankList[node.maxRank].length - 1;
            }
            if (parent != null && edge != null) {
                var parentToCellRankDifference = parent.maxRank - node.maxRank;
                if (parentToCellRankDifference > 1) {
                    edge.maxRank = parent.maxRank;
                    edge.minRank = node.maxRank;
                    edge.temp = new Array();
                    edge.x = new Array();
                    edge.y = new Array();
                    for (var i = edge.minRank + 1; i < edge.maxRank; i++) {
                    
                        rankList[i].push(edge);
                        edge.setGeneralPurposeVariable(i, rankList[i].length - 1);
                    }
                }
            }
        }, rootsArray, false, null);
    };
    mxGraphHierarchyModel.prototype.visit = function(visitor, dfsRoots, trackAncestors, seenNodes){
        if (dfsRoots != null) {
            for (var i = 0; i < dfsRoots.length; i++) {
                var internalNode = dfsRoots[i];
                if (internalNode != null) {
                    if (seenNodes == null) {
                        seenNodes = new Object();
                    }
                    if (trackAncestors) {
                        internalNode.hashCode = new Array();
                        internalNode.hashCode[0] = this.dfsCount;
                        internalNode.hashCode[1] = i;
                        this.extendedDfs(null, internalNode, null, visitor, seenNodes, internalNode.hashCode, i, 0);
                    }
                    else {
                        this.dfs(null, internalNode, null, visitor, seenNodes, 0);
                    }
                }
            }
            this.dfsCount++;
        }
    };
    mxGraphHierarchyModel.prototype.dfs = function(parent, root, connectingEdge, visitor, seen, layer){
        if (root != null) {
            var rootId = mxCellPath.create(root.cell);
            if (seen[rootId] == null) {
                seen[rootId] = root;
                visitor(parent, root, connectingEdge, layer, 0);
                
                for (var i = 0; i < root.connectsAsSource.length; i++) {
                    var internalEdge = root.connectsAsSource[i];
                    var targetNode = internalEdge.target;
                    this.dfs(root, targetNode, internalEdge, visitor, seen, layer + 1);
                }
            }
            else {
                visitor(parent, root, connectingEdge, layer, 1);
            }
        }
    };
    mxGraphHierarchyModel.prototype.extendedDfs = function(parent, root, connectingEdge, visitor, seen, ancestors, childHash, layer){
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        if (root != null) {
            if (parent != null) {
            
            
            
            
                if (root.hashCode == null || root.hashCode[0] != parent.hashCode[0]) {
                    var hashCodeLength = parent.hashCode.length + 1;
                    root.hashCode = parent.hashCode.slice();
                    root.hashCode[hashCodeLength - 1] = childHash;
                }
            }
            var rootId = mxCellPath.create(root.cell);
            if (seen[rootId] == null) {
                seen[rootId] = root;
                visitor(parent, root, connectingEdge, layer, 0);
                
                var outgoingEdges = root.connectsAsSource.slice();
                for (var i = 0; i < root.connectsAsSource.length; i++) {
                    var internalEdge = root.connectsAsSource[i];
                    var targetNode = internalEdge.target;
                    this.extendedDfs(root, targetNode, internalEdge, visitor, seen, root.hashCode, i, layer + 1);
                }
            }
            else {
                visitor(parent, root, connectingEdge, layer, 1);
            }
        }
    };
}
//mxHierarchicalLayoutStage
{
    function mxHierarchicalLayoutStage(){
    };
    mxHierarchicalLayoutStage.prototype.execute = function(parent){
    };
}
//mxMedianHybridCrossingReduction
{
    function mxMedianHybridCrossingReduction(layout){
        this.layout = layout;
    };
    mxMedianHybridCrossingReduction.prototype = new mxHierarchicalLayoutStage();
    mxMedianHybridCrossingReduction.prototype.constructor = mxMedianHybridCrossingReduction;
    mxMedianHybridCrossingReduction.prototype.layout = null;
    mxMedianHybridCrossingReduction.prototype.maxIterations = 24;
    mxMedianHybridCrossingReduction.prototype.nestedBestRanks = null;
    mxMedianHybridCrossingReduction.prototype.currentBestCrossings = 0;
    mxMedianHybridCrossingReduction.prototype.iterationsWithoutImprovement = 0;
    mxMedianHybridCrossingReduction.prototype.maxNoImprovementIterations = 2;
    mxMedianHybridCrossingReduction.prototype.execute = function(parent){
        var model = this.layout.getModel();
        this.nestedBestRanks = new Array();
        for (var i = 0; i < model.ranks.length; i++) {
            this.nestedBestRanks[i] = model.ranks[i].slice();
        }
        var iterationsWithoutImprovement = 0;
        var currentBestCrossings = this.calculateCrossings(model);
        for (var i = 0; i < this.maxIterations && iterationsWithoutImprovement < this.maxNoImprovementIterations; i++) {
            this.weightedMedian(i, model);
            this.transpose(i, model);
            var candidateCrossings = this.calculateCrossings(model);
            if (candidateCrossings < currentBestCrossings) {
                currentBestCrossings = candidateCrossings;
                iterationsWithoutImprovement = 0;
                for (var j = 0; j < this.nestedBestRanks.length; j++) {
                    var rank = model.ranks[j];
                    for (var k = 0; k < rank.length; k++) {
                        var cell = rank[k];
                        this.nestedBestRanks[j][cell.getGeneralPurposeVariable(j)] = cell;
                    }
                }
            }
            else {
            
                iterationsWithoutImprovement++;
                for (var j = 0; j < this.nestedBestRanks.length; j++) {
                    var rank = model.ranks[j];
                    for (var k = 0; k < rank.length; k++) {
                        var cell = rank[k];
                        cell.setGeneralPurposeVariable(j, k);
                    }
                }
            }
            if (currentBestCrossings == 0) {
                break;
            }
        }
        var ranks = new Array();
        var rankList = new Array();
        for (var i = 0; i < model.maxRank + 1; i++) {
            rankList[i] = new Array();
            ranks[i] = rankList[i];
        }
        for (var i = 0; i < this.nestedBestRanks.length; i++) {
            for (var j = 0; j < this.nestedBestRanks[i].length; j++) {
                rankList[i].push(this.nestedBestRanks[i][j]);
            }
        }
        model.ranks = ranks;
    };
    mxMedianHybridCrossingReduction.prototype.calculateCrossings = function(model){
        var numRanks = model.ranks.length;
        var totalCrossings = 0;
        for (var i = 1; i < numRanks; i++) {
            totalCrossings += this.calculateRankCrossing(i, model);
        }
        return totalCrossings;
    };
    mxMedianHybridCrossingReduction.prototype.calculateRankCrossing = function(i, model){
        var totalCrossings = 0;
        var rank = model.ranks[i];
        var previousRank = model.ranks[i - 1];
        var currentRankSize = rank.length;
        var previousRankSize = previousRank.length;
        var connections = new Array();
        for (var j = 0; j < currentRankSize; j++) {
            connections[j] = new Array();
        }
        for (var j = 0; j < rank.length; j++) {
            var node = rank[j];
            var rankPosition = node.getGeneralPurposeVariable(i);
            var connectedCells = node.getPreviousLayerConnectedCells(i);
            for (var k = 0; k < connectedCells.length; k++) {
                var connectedNode = connectedCells[k];
                var otherCellRankPosition = connectedNode.getGeneralPurposeVariable(i - 1);
                connections[rankPosition][otherCellRankPosition] = 201207;
            }
        }
        
        
        for (var j = 0; j < currentRankSize; j++) {
            for (var k = 0; k < previousRankSize; k++) {
                if (connections[j][k] == 201207) {
                
                    for (var j2 = j + 1; j2 < currentRankSize; j2++) {
                        for (var k2 = 0; k2 < k; k2++) {
                            if (connections[j2][k2] == 201207) {
                                totalCrossings++;
                            }
                        }
                    }
                    for (var j2 = 0; j2 < j; j2++) {
                        for (var k2 = k + 1; k2 < previousRankSize; k2++) {
                            if (connections[j2][k2] == 201207) {
                                totalCrossings++;
                            }
                        }
                    }
                }
            }
        }
        return totalCrossings / 2;
    };
    mxMedianHybridCrossingReduction.prototype.transpose = function(mainLoopIteration, model){
        var improved = true;
        var count = 0;
        var maxCount = 10;
        while (improved && count++ < maxCount) {
        
        
            var nudge = mainLoopIteration % 2 == 1 && count % 2 == 1;
            improved = false;
            for (var i = 0; i < model.ranks.length; i++) {
                var rank = model.ranks[i];
                var orderedCells = new Array();
                for (var j = 0; j < rank.length; j++) {
                    var cell = rank[j];
                    var tempRank = cell.getGeneralPurposeVariable(i);
                    if (tempRank < 0) {
                        tempRank = j;
                    }
                    orderedCells[tempRank] = cell;
                }
                var leftCellAboveConnections = null;
                var leftCellBelowConnections = null;
                var rightCellAboveConnections = null;
                var rightCellBelowConnections = null;
                var leftAbovePositions = null;
                var leftBelowPositions = null;
                var rightAbovePositions = null;
                var rightBelowPositions = null;
                var leftCell = null;
                var rightCell = null;
                for (var j = 0; j < (rank.length - 1); j++) {
                
                
                
                
                
                    if (j == 0) {
                        leftCell = orderedCells[j];
                        leftCellAboveConnections = leftCell.getNextLayerConnectedCells(i);
                        leftCellBelowConnections = leftCell.getPreviousLayerConnectedCells(i);
                        leftAbovePositions = new Array();
                        leftBelowPositions = new Array();
                        for (var k = 0; k < leftAbovePositions.length; k++) {
                            leftAbovePositions[k] = leftCellAboveConnections[k].getGeneralPurposeVariable(i + 1);
                        }
                        for (var k = 0; k < leftBelowPositions.length; k++) {
                            leftBelowPositions[k] = leftCellBelowConnections[k].getGeneralPurposeVariable(i - 1);
                        }
                    }
                    else {
                        leftCellAboveConnections = rightCellAboveConnections;
                        leftCellBelowConnections = rightCellBelowConnections;
                        leftAbovePositions = rightAbovePositions;
                        leftBelowPositions = rightBelowPositions;
                        leftCell = rightCell;
                    }
                    rightCell = orderedCells[j + 1];
                    rightCellAboveConnections = rightCell.getNextLayerConnectedCells(i);
                    rightCellBelowConnections = rightCell.getPreviousLayerConnectedCells(i);
                    rightAbovePositions = new Array();
                    rightBelowPositions = new Array();
                    for (var k = 0; k < rightAbovePositions.length; k++) {
                        rightAbovePositions[k] = rightCellAboveConnections[k].getGeneralPurposeVariable(i + 1);
                    }
                    for (var k = 0; k < rightBelowPositions.length; k++) {
                        rightBelowPositions[k] = rightCellBelowConnections[k].getGeneralPurposeVariable(i - 1);
                    }
                    var totalCurrentCrossings = 0;
                    var totalSwitchedCrossings = 0;
                    for (var k = 0; k < leftAbovePositions.length; k++) {
                        for (var ik = 0; ik < rightAbovePositions.length; ik++) {
                            if (leftAbovePositions[k] > rightAbovePositions[ik]) {
                                totalCurrentCrossings++;
                            }
                            if (leftAbovePositions[k] < rightAbovePositions[ik]) {
                                totalSwitchedCrossings++;
                            }
                        }
                    }
                    for (var k = 0; k < leftBelowPositions.length; k++) {
                        for (var ik = 0; ik < rightBelowPositions.length; ik++) {
                            if (leftBelowPositions[k] > rightBelowPositions[ik]) {
                                totalCurrentCrossings++;
                            }
                            if (leftBelowPositions[k] < rightBelowPositions[ik]) {
                                totalSwitchedCrossings++;
                            }
                        }
                    }
                    if ((totalSwitchedCrossings < totalCurrentCrossings) || (totalSwitchedCrossings == totalCurrentCrossings && nudge)) {
                        var temp = leftCell.getGeneralPurposeVariable(i);
                        leftCell.setGeneralPurposeVariable(i, rightCell.getGeneralPurposeVariable(i));
                        rightCell.setGeneralPurposeVariable(i, temp);
                        
                        
                        
                        rightCellAboveConnections = leftCellAboveConnections;
                        rightCellBelowConnections = leftCellBelowConnections;
                        rightAbovePositions = leftAbovePositions;
                        rightBelowPositions = leftBelowPositions;
                        rightCell = leftCell;
                        if (!nudge) {
                        
                        
                            improved = true;
                        }
                    }
                }
            }
        }
    };
    mxMedianHybridCrossingReduction.prototype.weightedMedian = function(iteration, model){
        var downwardSweep = (iteration % 2 == 0);
        if (downwardSweep) {
            for (var j = model.maxRank - 1; j >= 0; j--) {
                this.medianRank(j, downwardSweep);
            }
        }
        else {
            for (var j = 1; j < model.maxRank; j++) {
                this.medianRank(j, downwardSweep);
            }
        }
    };
    mxMedianHybridCrossingReduction.prototype.medianRank = function(rankValue, downwardSweep){
        var numCellsForRank = this.nestedBestRanks[rankValue].length;
        var medianValues = new Array();
        for (var i = 0; i < numCellsForRank; i++) {
            var cell = this.nestedBestRanks[rankValue][i];
            medianValues[i] = new MedianCellSorter();
            medianValues[i].cell = cell;
            
            medianValues[i].nudge = !downwardSweep;
            var nextLevelConnectedCells;
            if (downwardSweep) {
                nextLevelConnectedCells = cell.getNextLayerConnectedCells(rankValue);
            }
            else {
                nextLevelConnectedCells = cell.getPreviousLayerConnectedCells(rankValue);
            }
            var nextRankValue;
            if (downwardSweep) {
                nextRankValue = rankValue + 1;
            }
            else {
                nextRankValue = rankValue - 1;
            }
            if (nextLevelConnectedCells != null && nextLevelConnectedCells.length != 0) {
                medianValues[i].medianValue = this.medianValue(nextLevelConnectedCells, nextRankValue);
            }
            else {
            
            
                medianValues[i].medianValue = -1.0;
                
            }
        }
        medianValues.sort(MedianCellSorter.prototype.compare);
        
        for (var i = 0; i < numCellsForRank; i++) {
            medianValues[i].cell.setGeneralPurposeVariable(rankValue, i);
        }
    };
    mxMedianHybridCrossingReduction.prototype.medianValue = function(connectedCells, rankValue){
        var medianValues = new Array();
        var arrayCount = 0;
        for (var i = 0; i < connectedCells.length; i++) {
            var cell = connectedCells[i];
            medianValues[arrayCount++] = cell.getGeneralPurposeVariable(rankValue);
        }
        medianValues.sort(MedianCellSorter.prototype.compare);
        if (arrayCount % 2 == 1) {
            return medianValues[arrayCount / 2];
        }
        else 
            if (arrayCount == 2) {
                return ((medianValues[0] + medianValues[1]) / 2.0);
            }
            else {
                var medianPoint = arrayCount / 2;
                var leftMedian = medianValues[medianPoint - 1] - medianValues[0];
                var rightMedian = medianValues[arrayCount - 1] - medianValues[medianPoint];
                return (medianValues[medianPoint - 1] * rightMedian + medianValues[medianPoint] * leftMedian) / (leftMedian + rightMedian);
            }
    };
    {
        function MedianCellSorter(){
        };
        MedianCellSorter.prototype.medianValue = 0;
        MedianCellSorter.prototype.nudge = false;
        MedianCellSorter.prototype.cell = false;
        MedianCellSorter.prototype.compare = function(a, b){
            if (a != null && b != null) {
                if (b.medianValue > a.medianValue) {
                    return -1;
                }
                else 
                    if (b.medianValue < a.medianValue) {
                        return 1;
                    }
                    else {
                        if (b.nudge) {
                            return -1;
                        }
                        else {
                            return 1;
                        }
                    }
            }
            else {
                return 0;
            }
        };
    }
}
//mxMinimumCycleRemover
{
    function mxMinimumCycleRemover(layout){
        this.layout = layout;
    };
    mxMinimumCycleRemover.prototype = new mxHierarchicalLayoutStage();
    mxMinimumCycleRemover.prototype.constructor = mxMinimumCycleRemover;
    mxMinimumCycleRemover.prototype.layout = null;
    mxMinimumCycleRemover.prototype.execute = function(parent){
        var model = this.layout.getModel();
        var seenNodes = new Object();
        var unseenNodes = mxUtils.clone(model.vertexMapper, null, true);
        
        var rootsArray = null;
        if (model.roots != null) {
            var modelRoots = model.roots;
            rootsArray = new Array();
            for (var i = 0; i < modelRoots.length; i++) {
                var nodeId = mxCellPath.create(modelRoots[i]);
                rootsArray[i] = model.vertexMapper[nodeId];
            }
        }
        model.visit(function(parent, node, connectingEdge, layer, seen){
        
        
            if (node.isAncestor(parent)) {
                connectingEdge.invert();
                mxUtils.remove(connectingEdge, parent.connectsAsSource);
                parent.connectsAsTarget.push(connectingEdge);
                mxUtils.remove(connectingEdge, node.connectsAsTarget);
                node.connectsAsSource.push(connectingEdge);
            }
            var cellId = mxCellPath.create(node.cell);
            seenNodes[cellId] = node;
            delete unseenNodes[cellId];
        }, rootsArray, true, null);
        var possibleNewRoots = null;
        if (unseenNodes.lenth > 0) {
            possibleNewRoots = mxUtils.clone(unseenNodes, null, true);
        }
        
        
        var seenNodesCopy = mxUtils.clone(seenNodes, null, true);
        model.visit(function(parent, node, connectingEdge, layer, seen){
        
        
            if (node.isAncestor(parent)) {
                connectingEdge.invert();
                mxUtils.remove(connectingEdge, parent.connectsAsSource);
                node.connectsAsSource.push(connectingEdge);
                parent.connectsAsTarget.push(connectingEdge);
                mxUtils.remove(connectingEdge, node.connectsAsTarget);
            }
            var cellId = mxCellPath.create(node.cell);
            seenNodes[cellId] = node;
            delete unseenNodes[cellId];
        }, unseenNodes, true, seenNodesCopy);
        var graph = this.layout.getGraph();
        if (possibleNewRoots != null && possibleNewRoots.length > 0) {
            var roots = model.roots;
            for (var i = 0; i < possibleNewRoots.length; i++) {
                var node = possibleNewRoots[i];
                var realNode = node.cell;
                var numIncomingEdges = graph.getIncomingEdges(realNode).length;
                if (numIncomingEdges == 0) {
                    roots.push(realNode);
                }
            }
        }
    };
}
//mxCoordinateAssignment
{
    function mxCoordinateAssignment(layout, intraCellSpacing, interRankCellSpacing, orientation, initialX, parallelEdgeSpacing){
        this.layout = layout;
        this.intraCellSpacing = intraCellSpacing;
        this.interRankCellSpacing = interRankCellSpacing;
        this.orientation = orientation;
        this.initialX = initialX;
        this.parallelEdgeSpacing = parallelEdgeSpacing;
    };
    mxCoordinateAssignment.prototype = new mxHierarchicalLayoutStage();
    mxCoordinateAssignment.prototype.constructor = mxCoordinateAssignment;
    mxCoordinateAssignment.prototype.layout = null;
    mxCoordinateAssignment.prototype.intraCellSpacing = 30;
    mxCoordinateAssignment.prototype.interRankCellSpacing = 10;
    mxCoordinateAssignment.prototype.parallelEdgeSpacing = 10;
    mxCoordinateAssignment.prototype.maxIterations = 8;
    mxCoordinateAssignment.prototype.orientation = mxConstants.DIRECTION_NORTH;
    mxCoordinateAssignment.prototype.initialX = null;
    mxCoordinateAssignment.prototype.limitX = null;
    mxCoordinateAssignment.prototype.currentXDelta = null;
    mxCoordinateAssignment.prototype.widestRank = null;
    mxCoordinateAssignment.prototype.widestRankValue = null;
    mxCoordinateAssignment.prototype.rankWidths = null;
    mxCoordinateAssignment.prototype.rankY = null;
    mxCoordinateAssignment.prototype.fineTuning = true;
    mxCoordinateAssignment.prototype.nextLayerConnectedCache = null;
    mxCoordinateAssignment.prototype.previousLayerConnectedCache = null;
    mxCoordinateAssignment.prototype.execute = function(parent){
        var model = this.layout.getModel();
        this.currentXDelta = 0.0;
        this.initialCoords(this.layout.getGraph(), model);
        if (this.fineTuning) {
            this.minNode(model);
        }
        var bestXDelta = 100000000.0;
        if (this.fineTuning) {
            for (var i = 0; i < this.maxIterations; i++) {
                if (i != 0) {
                    this.medianPos(i, model);
                    this.minNode(model);
                }
                
                
                if (this.currentXDelta < bestXDelta) {
                    for (var j = 0; j < model.ranks.length; j++) {
                        var rank = model.ranks[j];
                        for (var k = 0; k < rank.length; k++) {
                            var cell = rank[k];
                            cell.setX(j, cell.getGeneralPurposeVariable(j));
                        }
                    }
                    bestXDelta = this.currentXDelta;
                }
                else {
                    for (var j = 0; j < model.ranks.length; j++) {
                        var rank = model.ranks[j];
                        for (var k = 0; k < rank.length; k++) {
                            var cell = rank[k];
                            cell.setGeneralPurposeVariable(j, cell.getX(j));
                        }
                    }
                }
                this.currentXDelta = 0;
            }
        }
        this.setCellLocations(this.layout.getGraph(), model);
    };
    mxCoordinateAssignment.prototype.minNode = function(model){
        var nodeList = new Array();
        var map = new Array();
        var rank = new Array();
        for (var i = 0; i <= model.maxRank; i++) {
            rank[i] = model.ranks[i];
            for (var j = 0; j < rank[i].length; j++) {
            
                var node = rank[i][j];
                var nodeWrapper = new WeightedCellSorter(node, i);
                nodeWrapper.rankIndex = j;
                nodeWrapper.visited = true;
                nodeList.push(nodeWrapper);
                var cellId = mxCellPath.create(node.cell);
                map[cellId] = nodeWrapper;
            }
        }
        
        var maxTries = nodeList.length * 10;
        var count = 0;
        var tolerance = 1;
        while (nodeList.length > 0 && count <= maxTries) {
            var cellWrapper = nodeList.shift();
            var cell = cellWrapper.cell;
            var rankValue = cellWrapper.weightedValue;
            var rankIndex = parseInt(cellWrapper.rankIndex);
            var nextLayerConnectedCells = cell.getNextLayerConnectedCells(rankValue);
            var previousLayerConnectedCells = cell.getPreviousLayerConnectedCells(rankValue);
            var numNextLayerConnected = nextLayerConnectedCells.length;
            var numPreviousLayerConnected = previousLayerConnectedCells.length;
            var medianNextLevel = this.medianXValue(nextLayerConnectedCells, rankValue + 1);
            var medianPreviousLevel = this.medianXValue(previousLayerConnectedCells, rankValue - 1);
            var numConnectedNeighbours = numNextLayerConnected + numPreviousLayerConnected;
            var currentPosition = cell.getGeneralPurposeVariable(rankValue);
            var cellMedian = currentPosition;
            if (numConnectedNeighbours > 0) {
                cellMedian = (medianNextLevel * numNextLayerConnected + medianPreviousLevel * numPreviousLayerConnected) / numConnectedNeighbours;
            }
            var positionChanged = false;
            if (cellMedian < currentPosition - tolerance) {
                if (rankIndex == 0) {
                    cell.setGeneralPurposeVariable(rankValue, cellMedian);
                    positionChanged = true;
                }
                else {
                    var leftCell = rank[rankValue][rankIndex - 1];
                    var leftLimit = leftCell.getGeneralPurposeVariable(rankValue);
                    leftLimit = leftLimit + leftCell.width / 2 + this.intraCellSpacing + cell.width / 2;
                    if (leftLimit < cellMedian) {
                        cell.setGeneralPurposeVariable(rankValue, cellMedian);
                        positionChanged = true;
                    }
                    else 
                        if (leftLimit < cell.getGeneralPurposeVariable(rankValue) - tolerance) {
                            cell.setGeneralPurposeVariable(rankValue, leftLimit);
                            positionChanged = true;
                        }
                }
            }
            else 
                if (cellMedian > currentPosition + tolerance) {
                    var rankSize = rank[rankValue].length;
                    if (rankIndex == rankSize - 1) {
                        cell.setGeneralPurposeVariable(rankValue, cellMedian);
                        positionChanged = true;
                    }
                    else {
                        var rightCell = rank[rankValue][rankIndex + 1];
                        var rightLimit = rightCell.getGeneralPurposeVariable(rankValue);
                        rightLimit = rightLimit - rightCell.width / 2 - this.intraCellSpacing - cell.width / 2;
                        if (rightLimit > cellMedian) {
                            cell.setGeneralPurposeVariable(rankValue, cellMedian);
                            positionChanged = true;
                        }
                        else 
                            if (rightLimit > cell.getGeneralPurposeVariable(rankValue) + tolerance) {
                                cell.setGeneralPurposeVariable(rankValue, rightLimit);
                                positionChanged = true;
                            }
                    }
                }
            if (positionChanged) {
                for (var i = 0; i < nextLayerConnectedCells.length; i++) {
                    var connectedCell = nextLayerConnectedCells[i];
                    var connectedCellId = mxCellPath.create(connectedCell.cell);
                    var connectedCellWrapper = map[connectedCellId];
                    if (connectedCellWrapper != null) {
                        if (connectedCellWrapper.visited == false) {
                            connectedCellWrapper.visited = true;
                            nodeList.push(connectedCellWrapper);
                        }
                    }
                }
                for (var i = 0; i < previousLayerConnectedCells.length; i++) {
                    var connectedCell = previousLayerConnectedCells[i];
                    var connectedCellId = mxCellPath.create(connectedCell.cell);
                    var connectedCellWrapper = map[connectedCellId];
                    if (connectedCellWrapper != null) {
                        if (connectedCellWrapper.visited == false) {
                            connectedCellWrapper.visited = true;
                            nodeList.push(connectedCellWrapper);
                        }
                    }
                }
            }
            cellWrapper.visited = false;
            count++;
        }
    };
    mxCoordinateAssignment.prototype.medianPos = function(i, model){
        var downwardSweep = (i % 2 == 0);
        if (downwardSweep) {
            for (var j = model.maxRank; j > 0; j--) {
                this.rankMedianPosition(j - 1, model, j);
            }
        }
        else {
            for (var j = 0; j < model.maxRank - 1; j++) {
                this.rankMedianPosition(j + 1, model, j);
            }
        }
    };
    mxCoordinateAssignment.prototype.rankMedianPosition = function(rankValue, model, nextRankValue){
        var rank = model.ranks[rankValue];
        
        
        var weightedValues = new Array();
        var cellMap = new Array();
        for (var i = 0; i < rank.length; i++) {
            var currentCell = rank[i];
            weightedValues[i] = new WeightedCellSorter();
            weightedValues[i].cell = currentCell;
            weightedValues[i].rankIndex = i;
            var currentCellId = mxCellPath.create(currentCell.cell);
            cellMap[currentCellId] = weightedValues[i];
            var nextLayerConnectedCells = null;
            if (nextRankValue < rankValue) {
                nextLayerConnectedCells = currentCell.getPreviousLayerConnectedCells(rankValue);
            }
            else {
                nextLayerConnectedCells = currentCell.getNextLayerConnectedCells(rankValue);
            }
            
            weightedValues[i].weightedValue = this.calculatedWeightedValue(currentCell, nextLayerConnectedCells);
        }
        weightedValues.sort(WeightedCellSorter.prototype.compare);
        
        for (var i = 0; i < weightedValues.length; i++) {
            var numConnectionsNextLevel = 0;
            var cell = weightedValues[i].cell;
            var nextLayerConnectedCells = null;
            var medianNextLevel = 0;
            if (nextRankValue < rankValue) {
                nextLayerConnectedCells = cell.getPreviousLayerConnectedCells(rankValue).slice();
            }
            else {
                nextLayerConnectedCells = cell.getNextLayerConnectedCells(rankValue).slice();
            }
            if (nextLayerConnectedCells != null) {
                numConnectionsNextLevel = nextLayerConnectedCells.length;
                if (numConnectionsNextLevel > 0) {
                    medianNextLevel = this.medianXValue(nextLayerConnectedCells, nextRankValue);
                }
                else {
                
                
                    medianNextLevel = cell.getGeneralPurposeVariable(rankValue);
                }
            }
            var leftBuffer = 0.0;
            var leftLimit = -100000000.0;
            for (var j = weightedValues[i].rankIndex - 1; j >= 0;) {
                var rankId = mxCellPath.create(rank[j].cell);
                var weightedValue = cellMap[rankId];
                if (weightedValue != null) {
                    var leftCell = weightedValue.cell;
                    if (weightedValue.visited) {
                    
                    
                        leftLimit = leftCell.getGeneralPurposeVariable(rankValue) + leftCell.width / 2.0 + this.intraCellSpacing + leftBuffer + cell.width / 2.0;
                        j = -1;
                    }
                    else {
                        leftBuffer += leftCell.width + this.intraCellSpacing;
                        j--;
                    }
                }
            }
            var rightBuffer = 0.0;
            var rightLimit = 100000000.0;
            for (var j = weightedValues[i].rankIndex + 1; j < weightedValues.length;) {
                var rankId = mxCellPath.create(rank[j].cell);
                var weightedValue = cellMap[rankId];
                if (weightedValue != null) {
                    var rightCell = weightedValue.cell;
                    if (weightedValue.visited) {
                    
                    
                        rightLimit = rightCell.getGeneralPurposeVariable(rankValue) - rightCell.width / 2.0 - this.intraCellSpacing - rightBuffer - cell.width / 2.0;
                        j = weightedValues.length;
                    }
                    else {
                        rightBuffer += rightCell.width + this.intraCellSpacing;
                        j++;
                    }
                }
            }
            if (medianNextLevel >= leftLimit && medianNextLevel <= rightLimit) {
                cell.setGeneralPurposeVariable(rankValue, medianNextLevel);
            }
            else 
                if (medianNextLevel < leftLimit) {
                
                    cell.setGeneralPurposeVariable(rankValue, leftLimit);
                    this.currentXDelta += leftLimit - medianNextLevel;
                }
                else 
                    if (medianNextLevel > rightLimit) {
                    
                        cell.setGeneralPurposeVariable(rankValue, rightLimit);
                        this.currentXDelta += medianNextLevel - rightLimit;
                    }
            weightedValues[i].visited = true;
        }
    };
    mxCoordinateAssignment.prototype.calculatedWeightedValue = function(currentCell, collection){
        var totalWeight = 0;
        for (var i = 0; i < collection.length; i++) {
            var cell = collection[i];
            if (currentCell.isVertex() && cell.isVertex()) {
                totalWeight++;
            }
            else 
                if (currentCell.isEdge() && cell.isEdge()) {
                    totalWeight += 8;
                }
                else {
                    totalWeight += 2;
                }
        }
        return totalWeight;
    };
    mxCoordinateAssignment.prototype.medianXValue = function(connectedCells, rankValue){
        if (connectedCells.length == 0) {
            return 0;
        }
        var medianValues = new Array();
        for (var i = 0; i < connectedCells.length; i++) {
            medianValues[i] = connectedCells[i].getGeneralPurposeVariable(rankValue);
        }
        medianValues.sort(MedianCellSorter.prototype.compare);
        if (connectedCells.length % 2 == 1) {
            return medianValues[connectedCells.length / 2];
        }
        else {
            var medianPoint = connectedCells.length / 2;
            var leftMedian = medianValues[medianPoint - 1];
            var rightMedian = medianValues[medianPoint];
            return ((leftMedian + rightMedian) / 2);
        }
    };
    mxCoordinateAssignment.prototype.initialCoords = function(facade, model){
        this.calculateWidestRank(facade, model);
        for (var i = this.widestRank; i >= 0; i--) {
            if (i < model.maxRank) {
                this.rankCoordinates(i, facade, model);
            }
        }
        for (var i = this.widestRank + 1; i <= model.maxRank; i++) {
            if (i > 0) {
                this.rankCoordinates(i, facade, model);
            }
        }
    };
    mxCoordinateAssignment.prototype.rankCoordinates = function(rankValue, graph, model){
        var rank = model.ranks[rankValue];
        var maxY = 0.0;
        var localX = this.initialX + (this.widestRankValue - this.rankWidths[rankValue]) / 2;
        
        var boundsWarning = false;
        for (var i = 0; i < rank.length; i++) {
            var node = rank[i];
            if (node.isVertex()) {
                var bounds = this.layout.getVertexBounds(node.cell);
                if (bounds != null) {
                    if (this.orientation == mxConstants.DIRECTION_NORTH || this.orientation == mxConstants.DIRECTION_SOUTH) {
                        node.width = bounds.width;
                        node.height = bounds.height;
                    }
                    else {
                        node.width = bounds.height;
                        node.height = bounds.width;
                    }
                }
                else {
                    boundsWarning = true;
                }
                maxY = Math.max(maxY, node.height);
            }
            else 
                if (node.isEdge()) {
                
                    var numEdges = 1;
                    if (node.edges != null) {
                        numEdges = node.edges.length;
                    }
                    else {
                        mxLog.warn('edge.edges is null');
                    }
                    node.width = (numEdges - 1) * this.parallelEdgeSpacing;
                }
            localX += node.width / 2.0;
            node.setX(rankValue, localX);
            node.setGeneralPurposeVariable(rankValue, localX);
            localX += node.width / 2.0;
            localX += this.intraCellSpacing;
        }
        if (boundsWarning == true) {
            mxLog.warn('At least one cell has no bounds');
        }
    };
    mxCoordinateAssignment.prototype.calculateWidestRank = function(graph, model){
        var y = -this.interRankCellSpacing;
        
        var lastRankMaxCellHeight = 0.0;
        this.rankWidths = new Array();
        this.rankY = new Array();
        for (var rankValue = model.maxRank; rankValue >= 0; rankValue--) {
            var maxCellHeight = 0.0;
            var rank = model.ranks[rankValue];
            var localX = this.initialX;
            
            var boundsWarning = false;
            for (var i = 0; i < rank.length; i++) {
                var node = rank[i];
                if (node.isVertex()) {
                    var bounds = this.layout.getVertexBounds(node.cell);
                    if (bounds != null) {
                        if (this.orientation == mxConstants.DIRECTION_NORTH || this.orientation == mxConstants.DIRECTION_SOUTH) {
                            node.width = bounds.width;
                            node.height = bounds.height;
                        }
                        else {
                            node.width = bounds.height;
                            node.height = bounds.width;
                        }
                    }
                    else {
                        boundsWarning = true;
                    }
                    maxCellHeight = Math.max(maxCellHeight, node.height);
                }
                else 
                    if (node.isEdge()) {
                    
                        var numEdges = 1;
                        if (node.edges != null) {
                            numEdges = node.edges.length;
                        }
                        else {
                            mxLog.warn('edge.edges is null');
                        }
                        node.width = (numEdges - 1) * this.parallelEdgeSpacing;
                    }
                localX += node.width / 2.0;
                node.setX(rankValue, localX);
                node.setGeneralPurposeVariable(rankValue, localX);
                localX += node.width / 2.0;
                localX += this.intraCellSpacing;
                if (localX > this.widestRankValue) {
                    this.widestRankValue = localX;
                    this.widestRank = rankValue;
                }
                this.rankWidths[rankValue] = localX;
            }
            if (boundsWarning == true) {
                mxLog.warn('At least one cell has no bounds');
            }
            this.rankY[rankValue] = y;
            var distanceToNextRank = maxCellHeight / 2.0 + lastRankMaxCellHeight / 2.0 + this.interRankCellSpacing;
            lastRankMaxCellHeight = maxCellHeight;
            if (this.orientation == mxConstants.DIRECTION_NORTH || this.orientation == mxConstants.DIRECTION_WEST) {
                y += distanceToNextRank;
            }
            else {
                y -= distanceToNextRank;
            }
            for (var i = 0; i < rank.length; i++) {
                var cell = rank[i];
                cell.setY(rankValue, y);
            }
        }
    };
    mxCoordinateAssignment.prototype.setCellLocations = function(graph, model){
        for (var i = 0; i < model.ranks.length; i++) {
            var rank = model.ranks[i];
            for (var h = 0; h < rank.length; h++) {
                var node = rank[h];
                if (node.isVertex()) {
                    var realCell = node.cell;
                    var positionX = node.x[0] - node.width / 2;
                    var positionY = node.y[0] - node.height / 2;
                    if (this.orientation == mxConstants.DIRECTION_NORTH || this.orientation == mxConstants.DIRECTION_SOUTH) {
                        this.layout.setVertexLocation(realCell, positionX, positionY);
                    }
                    else {
                        this.layout.setVertexLocation(realCell, positionY, positionX);
                    }
                    limitX = Math.max(this.limitX, positionX + node.width);
                }
                else 
                    if (node.isEdge()) {
                    
                        var offsetX = 0.0;
                        if (node.temp[0] != 101207) {
                            for (var j = 0; j < node.edges.length; j++) {
                                var realEdge = node.edges[j];
                                var newPoints = new Array();
                                if (node.isReversed) {
                                
                                    for (var k = 0; k < node.x.length; k++) {
                                        var positionX = node.x[k] + offsetX;
                                        if (this.orientation == mxConstants.DIRECTION_NORTH || this.orientation == mxConstants.DIRECTION_SOUTH) {
                                            newPoints.push(new mxPoint(positionX, node.y[k]));
                                        }
                                        else {
                                            newPoints.push(new mxPoint(node.y[k], positionX));
                                        }
                                        limitX = Math.max(limitX, positionX);
                                    }
                                    this.processReversedEdge(node, realEdge);
                                }
                                else {
                                    for (var k = node.x.length - 1; k >= 0; k--) {
                                        var positionX = node.x[k] + offsetX;
                                        if (this.orientation == mxConstants.DIRECTION_NORTH || this.orientation == mxConstants.DIRECTION_SOUTH) {
                                            newPoints.push(new mxPoint(positionX, node.y[k]));
                                        }
                                        else {
                                            newPoints.push(new mxPoint(node.y[k], positionX));
                                        }
                                        limitX = Math.max(limitX, positionX);
                                    }
                                }
                                this.layout.setEdgePoints(realEdge, newPoints);
                                
                                if (offsetX == 0.0) {
                                    offsetX = this.parallelEdgeSpacing;
                                }
                                else 
                                    if (offsetX > 0) {
                                        offsetX = -offsetX;
                                    }
                                    else {
                                        offsetX = -offsetX + this.parallelEdgeSpacing;
                                    }
                            }
                            node.temp[0] = 101207;
                        }
                    }
            }
        }
    };
    mxCoordinateAssignment.prototype.processReversedEdge = function(graph, model){
    };
    {
        function WeightedCellSorter(cell, weightedValue){
            this.cell = cell;
            this.weightedValue = weightedValue;
        };
        WeightedCellSorter.prototype.weightedValue = 0;
        WeightedCellSorter.prototype.nudge = false;
        WeightedCellSorter.prototype.visited = false;
        WeightedCellSorter.prototype.rankIndex = null;
        WeightedCellSorter.prototype.cell = null;
        WeightedCellSorter.prototype.compare = function(a, b){
            if (a != null && b != null) {
                if (b.weightedValue > a.weightedValue) {
                    return -1;
                }
                else 
                    if (b.weightedValue < a.weightedValue) {
                        return 1;
                    }
                    else {
                        if (b.nudge) {
                            return -1;
                        }
                        else {
                            return 1;
                        }
                    }
            }
            else {
                return 0;
            }
        };
    }
}
//mxHierarchicalLayout
{
    function mxHierarchicalLayout(graph, orientation, deterministic){
        mxGraphLayout.call(this, graph);
        this.orientation = (orientation != null) ? orientation : mxConstants.DIRECTION_NORTH;
        this.deterministic = (deterministic != null) ? deterministic : true;
    };
    mxHierarchicalLayout.prototype = new mxGraphLayout();
    mxHierarchicalLayout.prototype.constructor = mxHierarchicalLayout;
    mxHierarchicalLayout.prototype.INITIAL_X_POSITION = 100;
    mxHierarchicalLayout.prototype.roots = null;
    mxHierarchicalLayout.prototype.intraCellSpacing = 30;
    mxHierarchicalLayout.prototype.interRankCellSpacing = 50;
    mxHierarchicalLayout.prototype.interHierarchySpacing = 60;
    mxHierarchicalLayout.prototype.parallelEdgeSpacing = 10;
    mxHierarchicalLayout.prototype.orientation = mxConstants.DIRECTION_NORTH;
    mxHierarchicalLayout.prototype.fineTuning = true;
    mxHierarchicalLayout.prototype.deterministic;
    mxHierarchicalLayout.prototype.fixRoots = false;
    mxHierarchicalLayout.prototype.layoutFromSinks = true;
    mxHierarchicalLayout.prototype.tightenToSource = true;
    mxHierarchicalLayout.prototype.disableEdgeStyle = true;
    mxHierarchicalLayout.prototype.model = null;
    mxHierarchicalLayout.prototype.getModel = function(){
        return this.model;
    };
    mxHierarchicalLayout.prototype.execute = function(parent, roots){
        if (roots == null) {
            roots = this.graph.findTreeRoots(parent);
        }
        this.roots = roots;
        if (this.roots != null) {
            var model = this.graph.getModel();
            model.beginUpdate();
            try {
                this.run(parent);
            }
            finally {
                model.endUpdate();
            }
        }
    };
    mxHierarchicalLayout.prototype.run = function(parent){
        var hierarchyVertices = new Array();
        var fixedRoots = null;
        var rootLocations = null;
        var affectedEdges = null;
        if (this.fixRoots) {
            fixedRoots = new Array();
            rootLocations = new Array();
            affectedEdges = new Array();
        }
        for (var i = 0; i < this.roots.length; i++) {
        
            var newHierarchy = true;
            for (var j = 0; newHierarchy && j < hierarchyVertices.length; j++) {
                var rootId = mxCellPath.create(this.roots[i]);
                if (hierarchyVertices[j][rootId] != null) {
                    newHierarchy = false;
                }
            }
            if (newHierarchy) {
                var cellsStack = new Array();
                cellsStack.push(this.roots[i]);
                var edgeSet = null;
                if (this.fixRoots) {
                    fixedRoots.push(this.roots[i]);
                    var location = this.getVertexBounds(this.roots[i]).getPoint();
                    rootLocations.push(location);
                    edgeSet = new Array();
                }
                var vertexSet = new Object();
                while (cellsStack.length > 0) {
                    var cell = cellsStack.shift();
                    var cellId = mxCellPath.create(cell);
                    if (vertexSet[cellId] == null) {
                        vertexSet[cellId] = cell;
                        if (this.fixRoots) {
                            var tmp = this.graph.getIncomingEdges(cell, parent);
                            for (var k = 0; k < tmp.length; k++) {
                                edgeSet.push(tmp[k]);
                            }
                        }
                        var conns = this.graph.getConnections(cell, parent);
                        var cells = this.graph.getOpposites(conns, cell);
                        for (var k = 0; k < cells.length; k++) {
                            var tmpId = mxCellPath.create(cells[k]);
                            if (vertexSet[tmpId] == null) {
                                cellsStack.push(cells[k]);
                            }
                        }
                    }
                }
                hierarchyVertices.push(vertexSet);
                if (this.fixRoots) {
                    affectedEdges.push(edgeSet);
                }
            }
        }
        
        var initialX = this.INITIAL_X_POSITION;
        for (var i = 0; i < hierarchyVertices.length; i++) {
            var vertexSet = hierarchyVertices[i];
            var tmp = new Array();
            for (var key in vertexSet) {
                tmp.push(vertexSet[key]);
            }
            this.model = new mxGraphHierarchyModel(this, tmp, this.roots, parent, false, this.deterministic, this.tightenToSource);
            this.cycleStage(parent);
            this.layeringStage();
            this.crossingStage(parent);
            initialX = this.placementStage(initialX, parent);
            if (this.fixRoots) {
            
                var root = fixedRoots[i];
                var oldLocation = rootLocations[i];
                var newLocation = this.getVertexBounds(root).getPoint();
                var diffX = oldLocation.x - newLocation.x;
                var diffY = oldLocation.y - newLocation.y;
                this.graph.moveCells(vertexSet, diffX, diffY);
                var connectedEdges = affectedEdges[i + 1];
                this.graph.moveCells(connectedEdges, diffX, diffY);
            }
        }
    };
    mxHierarchicalLayout.prototype.cycleStage = function(parent){
        var cycleStage = new mxMinimumCycleRemover(this);
        cycleStage.execute(parent);
    };
    mxHierarchicalLayout.prototype.layeringStage = function(){
        this.model.initialRank(true);
        this.model.fixRanks();
    };
    mxHierarchicalLayout.prototype.crossingStage = function(parent){
        var crossingStage = new mxMedianHybridCrossingReduction(this);
        crossingStage.execute(parent);
    };
    mxHierarchicalLayout.prototype.placementStage = function(initialX, parent){
        var placementStage = new mxCoordinateAssignment(this, this.intraCellSpacing, this.interRankCellSpacing, this.orientation, initialX, this.parallelEdgeSpacing);
        placementStage.fineTuning = this.fineTuning;
        placementStage.execute(parent);
        return placementStage.limitX + this.interHierarchySpacing;
    };
}
//mxGraphModel
{
    function mxGraphModel(root){
        this.currentEdit = this.createUndoableEdit();
        if (root != null) {
            this.setRoot(root);
        }
        else {
            this.clear();
        }
    };
    mxGraphModel.prototype = new mxEventSource();
    mxGraphModel.prototype.constructor = mxGraphModel;
    mxGraphModel.prototype.root = null;
    mxGraphModel.prototype.cells = null;
    mxGraphModel.prototype.maintainEdgeParent = true;
    mxGraphModel.prototype.createIds = true;
    mxGraphModel.prototype.prefix = '';
    mxGraphModel.prototype.postfix = '';
    mxGraphModel.prototype.nextId = 0;
    mxGraphModel.prototype.currentEdit = null;
    mxGraphModel.prototype.updateLevel = 0;
    mxGraphModel.prototype.endingUpdate = false;
    mxGraphModel.prototype.clear = function(){
        this.setRoot(this.createRoot());
    };
    mxGraphModel.prototype.isCreateIds = function(){
        return this.createIds;
    };
    mxGraphModel.prototype.setCreateIds = function(value){
        this.createIds = value;
    };
    mxGraphModel.prototype.createRoot = function(){
        var cell = new mxCell();
        cell.insert(new mxCell());
        return cell;
    };
    mxGraphModel.prototype.getCell = function(id){
        return (this.cells != null) ? this.cells[id] : null;
    };
    mxGraphModel.prototype.filterCells = function(cells, filter){
        var result = null;
        if (cells != null) {
            result = new Array();
            for (var i = 0; i < cells.length; i++) {
                if (filter(cells[i])) {
                    result.push(cells[i]);
                }
            }
        }
        return result;
    }
    mxGraphModel.prototype.getDescendants = function(parent){
        return this.filterDescendants(null, parent);
    };
    mxGraphModel.prototype.filterDescendants = function(filter, parent){
        var result = new Array();
        parent = parent || this.getRoot();
        
        if (filter == null || filter(parent)) {
            result.push(parent);
        }
        var childCount = this.getChildCount(parent);
        for (var i = 0; i < childCount; i++) {
            var child = this.getChildAt(parent, i);
            result = result.concat(this.filterDescendants(filter, child));
        }
        return result;
    };
    mxGraphModel.prototype.getRoot = function(cell){
        var root = cell || this.root;
        if (cell != null) {
            while (cell != null) {
                root = cell;
                cell = this.getParent(cell);
            }
        }
        return root;
    };
    mxGraphModel.prototype.setRoot = function(root){
        this.execute(new mxRootChange(this, root));
        return root;
    };
    mxGraphModel.prototype.rootChanged = function(root){
        var oldRoot = this.root;
        this.root = root;
        this.nextId = 0;
        this.cells = null;
        this.cellAdded(root);
        return oldRoot;
    };
    mxGraphModel.prototype.isRoot = function(cell){
        return cell != null && this.root == cell;
    };
    mxGraphModel.prototype.isLayer = function(cell){
        return this.isRoot(this.getParent(cell));
    };
    mxGraphModel.prototype.isAncestor = function(parent, child){
        while (child != null && child != parent) {
            child = this.getParent(child);
        }
        return child == parent;
    };
    mxGraphModel.prototype.contains = function(cell){
        return this.isAncestor(this.root, cell);
    };
    mxGraphModel.prototype.getParent = function(cell){
        return (cell != null) ? cell.getParent() : null;
    };
    mxGraphModel.prototype.add = function(parent, child, index){
        if (child != parent && parent != null && child != null) {
            if (index == null) {
                index = this.getChildCount(parent);
            }
            var parentChanged = parent != this.getParent(child);
            this.execute(new mxChildChange(this, parent, child, index));
            
            
            if (this.maintainEdgeParent && parentChanged) {
                this.updateEdgeParents(child);
            }
        }
        return child;
    };
    mxGraphModel.prototype.cellAdded = function(cell){
        if (cell != null) {
            if (cell.getId() == null && this.createIds) {
                cell.setId(this.createId(cell));
            }
            if (cell.getId() != null) {
                var collision = this.getCell(cell.getId());
                if (collision != cell) {
                
                    while (collision != null) {
                        cell.setId(this.createId(cell));
                        collision = this.getCell(cell.getId());
                    }
                    if (this.cells == null) {
                        this.cells = new Object();
                    }
                    this.cells[cell.getId()] = cell;
                }
            }
            if (mxUtils.isNumeric(cell.getId())) {
                this.nextId = Math.max(this.nextId, cell.getId());
            }
            var childCount = this.getChildCount(cell);
            for (var i = 0; i < childCount; i++) {
                this.cellAdded(this.getChildAt(cell, i));
            }
        }
    };
    mxGraphModel.prototype.createId = function(cell){
        var id = this.nextId;
        this.nextId++;
        return this.prefix + id + this.postfix;
    };
    mxGraphModel.prototype.updateEdgeParents = function(cell, root){
        root = root || this.getRoot(cell);
        var childCount = this.getChildCount(cell);
        for (var i = 0; i < childCount; i++) {
            var child = this.getChildAt(cell, i);
            this.updateEdgeParents(child, root);
        }
        var edgeCount = this.getEdgeCount(cell);
        var edges = new Array();
        for (var i = 0; i < edgeCount; i++) {
            edges.push(this.getEdgeAt(cell, i));
        }
        for (var i = 0; i < edges.length; i++) {
            var edge = edges[i];
            
            
            if (this.isAncestor(root, edge)) {
                this.updateEdgeParent(edge, root);
            }
        }
    };
    mxGraphModel.prototype.updateEdgeParent = function(edge, root){
        var source = this.getTerminal(edge, true);
        var target = this.getTerminal(edge, false);
        var cell = null;
        if (this.isAncestor(root, source) && this.isAncestor(root, target)) {
            if (source == target) {
                cell = this.getParent(source);
            }
            else {
                cell = this.getNearestCommonAncestor(source, target);
            }
            if (cell != null && (this.getParent(cell) != this.root || this.isAncestor(cell, edge)) && this.getParent(edge) != cell) {
                var geo = this.getGeometry(edge);
                if (geo != null) {
                    var origin1 = this.getOrigin(this.getParent(edge));
                    var origin2 = this.getOrigin(cell);
                    var dx = origin2.x - origin1.x;
                    var dy = origin2.y - origin1.y;
                    geo = geo.translate(-dx, -dy);
                    this.setGeometry(edge, geo);
                }
                this.add(cell, edge, this.getChildCount(cell));
            }
        }
    };
    mxGraphModel.prototype.getOrigin = function(cell){
        var result = null;
        if (cell != null) {
            result = this.getOrigin(this.getParent(cell));
            if (!this.isEdge(cell)) {
                var geo = this.getGeometry(cell);
                if (geo != null) {
                    result.x += geo.x;
                    result.y += geo.y;
                }
            }
        }
        else {
            result = new mxPoint();
        }
        return result;
    };
    mxGraphModel.prototype.getNearestCommonAncestor = function(cell1, cell2){
        var result = null;
        if (cell1 != null && cell2 != null) {
            var path = mxCellPath.create(cell2);
            if (path != null && path.length > 0) {
            
                var cell = cell1;
                var current = mxCellPath.create(cell);
                while (cell != null && result == null) {
                    var parent = this.getParent(cell);
                    
                    if (path.indexOf(current + mxCellPath.PATH_SEPARATOR) == 0 && parent != null) {
                        result = cell;
                    }
                    current = mxCellPath.getParentPath(current);
                    cell = parent;
                }
            }
        }
        return result;
    };
    mxGraphModel.prototype.remove = function(cell){
        if (cell == this.root) {
            this.setRoot(null);
        }
        else 
            if (this.getParent(cell) != null) {
                this.execute(new mxChildChange(this, null, cell));
            }
        return cell;
    };
    mxGraphModel.prototype.cellRemoved = function(cell){
        if (cell != null && this.cells != null) {
            var childCount = this.getChildCount(cell);
            for (var i = childCount - 1; i >= 0; i--) {
                this.cellRemoved(this.getChildAt(cell, i));
            }
            if (this.cells != null && cell.getId() != null) {
                delete this.cells[cell.getId()];
            }
        }
    };
    mxGraphModel.prototype.parentForCellChanged = function(cell, parent, index){
        var previous = this.getParent(cell);
        if (parent != null) {
            if (parent != previous || previous.getIndex(cell) != index) {
                parent.insert(cell, index);
            }
        }
        else 
            if (previous != null) {
                var oldIndex = previous.getIndex(cell);
                previous.remove(oldIndex);
            }
        
        if (!this.contains(previous) && parent != null) {
            this.cellAdded(cell);
        }
        else 
            if (parent == null) {
                this.cellRemoved(cell);
            }
        return previous;
    };
    mxGraphModel.prototype.getChildCount = function(cell){
        return (cell != null) ? cell.getChildCount() : 0;
    };
    mxGraphModel.prototype.getChildAt = function(cell, index){
        return (cell != null) ? cell.getChildAt(index) : null;
    };
    mxGraphModel.prototype.getChildren = function(cell){
        return (cell != null) ? cell.children : null;
    };
    mxGraphModel.prototype.getChildVertices = function(parent){
        return this.getChildCells(parent, true, false);
    };
    mxGraphModel.prototype.getChildEdges = function(parent){
        return this.getChildCells(parent, false, true);
    };
    mxGraphModel.prototype.getChildCells = function(parent, vertices, edges){
        vertices = (vertices != null) ? vertices : false;
        edges = (edges != null) ? edges : false;
        var childCount = this.getChildCount(parent);
        var result = new Array();
        for (var i = 0; i < childCount; i++) {
            var child = this.getChildAt(parent, i);
            if ((!edges && !vertices) || (edges && this.isEdge(child)) || (vertices && this.isVertex(child))) {
                result.push(child);
            }
        }
        return result;
    };
    mxGraphModel.prototype.getTerminal = function(edge, isSource){
        return (edge != null) ? edge.getTerminal(isSource) : null;
    };
    mxGraphModel.prototype.setTerminal = function(edge, terminal, isSource){
        var terminalChanged = terminal != this.getTerminal(edge, isSource);
        this.execute(new mxTerminalChange(this, edge, terminal, isSource));
        if (this.maintainEdgeParent && terminalChanged) {
            this.updateEdgeParent(edge, this.getRoot());
        }
        return terminal;
    };
    mxGraphModel.prototype.setTerminals = function(edge, source, target){
        this.beginUpdate();
        try {
            this.setTerminal(edge, source, true);
            this.setTerminal(edge, target, false);
        }
        finally {
            this.endUpdate();
        }
    };
    mxGraphModel.prototype.terminalForCellChanged = function(edge, terminal, isSource){
        var previous = this.getTerminal(edge, isSource);
        if (terminal != null) {
            terminal.insertEdge(edge, isSource);
        }
        else 
            if (previous != null) {
                previous.removeEdge(edge, isSource);
            }
        return previous;
    };
    mxGraphModel.prototype.getEdgeCount = function(cell){
        return (cell != null) ? cell.getEdgeCount() : 0;
    };
    mxGraphModel.prototype.getEdgeAt = function(cell, index){
        return (cell != null) ? cell.getEdgeAt(index) : null;
    };
    mxGraphModel.prototype.getDirectedEdgeCount = function(cell, outgoing, ignoredEdge){
        var count = 0;
        var edgeCount = this.getEdgeCount(cell);
        for (var i = 0; i < edgeCount; i++) {
            var edge = this.getEdgeAt(cell, i);
            if (edge != ignoredEdge && this.getTerminal(edge, outgoing) == cell) {
                count++;
            }
        }
        return count;
    };
    mxGraphModel.prototype.getConnections = function(cell){
        return this.getEdges(cell, true, true, false);
    };
    mxGraphModel.prototype.getIncomingEdges = function(cell){
        return this.getEdges(cell, true, false, false);
    };
    mxGraphModel.prototype.getOutgoingEdges = function(cell){
        return this.getEdges(cell, false, true, false);
    };
    mxGraphModel.prototype.getEdges = function(cell, incoming, outgoing, includeLoops){
        incoming = (incoming != null) ? incoming : true;
        outgoing = (outgoing != null) ? outgoing : true;
        includeLoops = (includeLoops != null) ? includeLoops : true;
        var edgeCount = this.getEdgeCount(cell);
        var result = new Array();
        for (var i = 0; i < edgeCount; i++) {
            var edge = this.getEdgeAt(cell, i);
            var source = this.getTerminal(edge, true);
            var target = this.getTerminal(edge, false);
            if (includeLoops || ((source != target) && ((incoming && target == cell) || (outgoing && source == cell)))) {
                result.push(edge);
            }
        }
        return result;
    };
    mxGraphModel.prototype.getEdgesBetween = function(source, target, directed){
        directed = (directed != null) ? directed : false;
        var tmp1 = this.getEdgeCount(source);
        var tmp2 = this.getEdgeCount(target);
        var terminal = source;
        var edgeCount = tmp1;
        
        if (tmp2 < tmp1) {
            edgeCount = tmp2;
            terminal = target;
        }
        var result = new Array();
        
        for (var i = 0; i < edgeCount; i++) {
            var edge = this.getEdgeAt(terminal, i);
            var src = this.getTerminal(edge, true);
            var trg = this.getTerminal(edge, false);
            var isSource = src == source;
            if (isSource && trg == target || (!directed && this.getTerminal(edge, !isSource) == target)) {
                result.push(edge);
            }
        }
        return result;
    };
    mxGraphModel.prototype.getOpposites = function(edges, terminal, sources, targets){
        sources = (sources != null) ? sources : true;
        targets = (targets != null) ? targets : true;
        var terminals = new Array();
        if (edges != null) {
            for (var i = 0; i < edges.length; i++) {
                var source = this.getTerminal(edges[i], true);
                var target = this.getTerminal(edges[i], false);
                
                
                if (source == terminal && target != null && target != terminal && targets) {
                    terminals.push(target);
                }
                
                
                else 
                    if (target == terminal && source != null && source != terminal && sources) {
                        terminals.push(source);
                    }
            }
        }
        return terminals;
    };
    mxGraphModel.prototype.getTopmostCells = function(cells){
        var tmp = new Array();
        for (var i = 0; i < cells.length; i++) {
            var cell = cells[i];
            var topmost = true;
            var parent = this.getParent(cell);
            while (parent != null) {
                if (mxUtils.indexOf(cells, parent) >= 0) {
                    topmost = false;
                    break;
                }
                parent = this.getParent(parent);
            }
            if (topmost) {
                tmp.push(cell);
            }
        }
        return tmp;
    };
    mxGraphModel.prototype.isVertex = function(cell){
        return (cell != null) ? cell.isVertex() : false;
    };
    mxGraphModel.prototype.isEdge = function(cell){
        return (cell != null) ? cell.isEdge() : false;
    };
    mxGraphModel.prototype.isConnectable = function(cell){
        return (cell != null) ? cell.isConnectable() : false;
    };
    mxGraphModel.prototype.getValue = function(cell){
        return (cell != null) ? cell.getValue() : null;
    };
    mxGraphModel.prototype.setValue = function(cell, value){
        this.execute(new mxValueChange(this, cell, value));
        return value;
    };
    mxGraphModel.prototype.valueForCellChanged = function(cell, value){
        return cell.valueChanged(value);
    };
    mxGraphModel.prototype.getGeometry = function(cell, geometry){
        return (cell != null) ? cell.getGeometry() : null;
    };
    mxGraphModel.prototype.setGeometry = function(cell, geometry){
        if (geometry != this.getGeometry(cell)) {
            this.execute(new mxGeometryChange(this, cell, geometry));
        }
        return geometry;
    };
    mxGraphModel.prototype.geometryForCellChanged = function(cell, geometry){
        var previous = this.getGeometry(cell);
        cell.setGeometry(geometry);
        return previous;
    };
    mxGraphModel.prototype.getStyle = function(cell){
        return (cell != null) ? cell.getStyle() : null;
    };
    mxGraphModel.prototype.setStyle = function(cell, style){
        if (style != this.getStyle(cell)) {
            this.execute(new mxStyleChange(this, cell, style));
        }
        return style;
    };
    mxGraphModel.prototype.styleForCellChanged = function(cell, style){
        var previous = this.getStyle(cell);
        cell.setStyle(style);
        return previous;
    };
    mxGraphModel.prototype.isCollapsed = function(cell){
        return (cell != null) ? cell.isCollapsed() : false;
    };
    mxGraphModel.prototype.setCollapsed = function(cell, collapsed){
        if (collapsed != this.isCollapsed(cell)) {
            this.execute(new mxCollapseChange(this, cell, collapsed));
        }
        return collapsed;
    };
    mxGraphModel.prototype.collapsedStateForCellChanged = function(cell, collapsed){
        var previous = this.isCollapsed(cell);
        cell.setCollapsed(collapsed);
        return previous;
    };
    mxGraphModel.prototype.isVisible = function(cell){
        return (cell != null) ? cell.isVisible() : false;
    };
    mxGraphModel.prototype.setVisible = function(cell, visible){
        if (visible != this.isVisible(cell)) {
            this.execute(new mxVisibleChange(this, cell, visible));
        }
        return visible;
    };
    mxGraphModel.prototype.visibleStateForCellChanged = function(cell, visible){
        var previous = this.isVisible(cell);
        cell.setVisible(visible);
        return previous;
    };
    mxGraphModel.prototype.execute = function(change){
        change.execute();
        this.beginUpdate();
        this.currentEdit.add(change);
        this.fireEvent(mxEvent.EXECUTE, this, new mxEventObject([change]));
        this.endUpdate();
    };
    mxGraphModel.prototype.beginUpdate = function(){
        this.updateLevel++;
        this.fireEvent(mxEvent.BEGIN_UPDATE);
    };
    mxGraphModel.prototype.endUpdate = function(){
        this.updateLevel--;
        if (!this.endingUpdate) {
            this.endingUpdate = this.updateLevel == 0;
            this.fireEvent(mxEvent.END_UPDATE, new mxEventObject([this.currentEdit]));
            try {
                if (this.endingUpdate && !this.currentEdit.isEmpty()) {
                    this.fireEvent(mxEvent.BEFORE_UNDO, new mxEventObject([this.currentEdit]));
                    var tmp = this.currentEdit;
                    this.currentEdit = this.createUndoableEdit();
                    tmp.notify();
                    this.fireEvent(mxEvent.UNDO, new mxEventObject([tmp]));
                }
            }
            finally {
                this.endingUpdate = false;
            }
        }
    };
    mxGraphModel.prototype.createUndoableEdit = function(){
        var edit = new mxUndoableEdit(this, true);
        edit.notify = function(){
            edit.source.fireEvent(mxEvent.CHANGE, new mxEventObject([edit.changes]));
            edit.source.fireEvent(mxEvent.NOTIFY, new mxEventObject([edit.changes]));
        }
        return edit;
    };
    mxGraphModel.prototype.mergeChildren = function(from, to, cloneAllEdges){
        cloneAllEdges = (cloneAllEdges != null) ? cloneAllEdges : true;
        this.beginUpdate();
        try {
            var mapping = new Object();
            this.mergeChildrenImpl(from, to, cloneAllEdges, mapping);
            
            
            for (var key in mapping) {
                var cell = mapping[key];
                var terminal = this.getTerminal(cell, true);
                if (terminal != null) {
                    terminal = mapping[mxCellPath.create(terminal)];
                    this.setTerminal(cell, terminal, true);
                }
                terminal = this.getTerminal(cell, false);
                if (terminal != null) {
                    terminal = mapping[mxCellPath.create(terminal)];
                    this.setTerminal(cell, terminal, false);
                }
            }
        }
        finally {
            this.endUpdate();
        }
    };
    mxGraphModel.prototype.mergeChildrenImpl = function(from, to, cloneAllEdges, mapping){
        this.beginUpdate();
        try {
            var childCount = from.getChildCount();
            for (var i = 0; i < childCount; i++) {
                var cell = from.getChildAt(i);
                if (typeof(cell.getId) == 'function') {
                    var id = cell.getId();
                    var target = (id != null && (!this.isEdge(cell) || !cloneAllEdges)) ? this.getCell(id) : null;
                    if (target == null) {
                        var clone = cell.clone();
                        clone.setId(id);
                        
                        clone.setTerminal(cell.getTerminal(true), true);
                        clone.setTerminal(cell.getTerminal(false), false);
                        
                        
                        target = to.insert(clone);
                        this.cellAdded(target);
                    }
                    mapping[mxCellPath.create(cell)] = target;
                    this.mergeChildrenImpl(cell, target, cloneAllEdges, mapping);
                }
            }
        }
        finally {
            this.endUpdate();
        }
    };
    mxGraphModel.prototype.getParents = function(cells){
        var parents = new Array();
        if (cells != null) {
            var hash = new Object();
            for (var i = 0; i < cells.length; i++) {
                var parent = this.getParent(cells[i]);
                if (parent != null) {
                    var id = mxCellPath.create(parent);
                    if (hash[id] == null) {
                        hash[id] = parent;
                        parents.push(parent);
                    }
                }
            }
        }
        return parents;
    };
    
    
    
    mxGraphModel.prototype.cloneCell = function(cell){
        if (cell != null) {
            return this.cloneCells([cell], true)[0];
        }
        return null;
    };
    mxGraphModel.prototype.cloneCells = function(cells, includeChildren){
        var mapping = new Object();
        var clones = new Array();
        for (var i = 0; i < cells.length; i++) {
            if (cells[i] != null) {
                clones.push(this.cloneCellImpl(cells[i], mapping, includeChildren));
            }
            else {
                clones.push(null);
            }
        }
        for (var i = 0; i < clones.length; i++) {
            if (clones[i] != null) {
                this.restoreClone(clones[i], cells[i], mapping);
            }
        }
        return clones;
    };
    mxGraphModel.prototype.cloneCellImpl = function(cell, mapping, includeChildren){
        var clone = this.cellCloned(cell);
        
        mapping[mxObjectIdentity.get(cell)] = clone;
        if (includeChildren) {
            var childCount = this.getChildCount(cell);
            for (var i = 0; i < childCount; i++) {
                var cloneChild = this.cloneCellImpl(this.getChildAt(cell, i), mapping, true);
                clone.insert(cloneChild);
            }
        }
        return clone;
    };
    mxGraphModel.prototype.cellCloned = function(cell){
        return cell.clone();
    };
    mxGraphModel.prototype.restoreClone = function(clone, cell, mapping){
        var source = this.getTerminal(cell, true);
        if (source != null) {
            var tmp = mapping[mxObjectIdentity.get(source)];
            if (tmp != null) {
                tmp.insertEdge(clone, true);
            }
        }
        var target = this.getTerminal(cell, false);
        if (target != null) {
            var tmp = mapping[mxObjectIdentity.get(target)];
            if (tmp != null) {
                tmp.insertEdge(clone, false);
            }
        }
        var childCount = this.getChildCount(clone);
        for (var i = 0; i < childCount; i++) {
            this.restoreClone(this.getChildAt(clone, i), this.getChildAt(cell, i), mapping);
        }
    };
    
    
    
    function mxRootChange(model, root){
        this.model = model;
        this.root = root;
        this.previous = root;
    };
    mxRootChange.prototype.execute = function(){
        this.root = this.previous;
        this.previous = this.model.rootChanged(this.previous);
    };
    function mxChildChange(model, parent, child, index){
        this.model = model;
        this.parent = parent;
        this.previous = parent;
        this.child = child;
        this.index = index;
        this.previousIndex = index;
        this.isAdded = (parent == null);
    };
    mxChildChange.prototype.execute = function(){
        var tmp = this.model.getParent(this.child);
        var tmp2 = (tmp != null) ? tmp.getIndex(this.child) : 0;
        if (this.previous == null) {
            this.connect(this.child, false);
        }
        tmp = this.model.parentForCellChanged(this.child, this.previous, this.previousIndex);
        if (this.previous != null) {
            this.connect(this.child, true);
        }
        this.parent = this.previous;
        this.previous = tmp;
        this.index = this.previousIndex;
        this.previousIndex = tmp2;
        this.isAdded = !this.isAdded;
    };
    mxChildChange.prototype.connect = function(cell, isConnect){
        isConnect = (isConnect != null) ? isConnect : true;
        var source = cell.getTerminal(true);
        var target = cell.getTerminal(false);
        if (source != null) {
            if (isConnect) {
                this.model.terminalForCellChanged(cell, source, true);
            }
            else {
                this.model.terminalForCellChanged(cell, null, true);
            }
        }
        if (target != null) {
            if (isConnect) {
                this.model.terminalForCellChanged(cell, target, false);
            }
            else {
                this.model.terminalForCellChanged(cell, null, false);
            }
        }
        cell.setTerminal(source, true);
        cell.setTerminal(target, false);
        var childCount = this.model.getChildCount(cell);
        for (var i = 0; i < childCount; i++) {
            this.connect(this.model.getChildAt(cell, i), isConnect);
        }
    };
    function mxTerminalChange(model, cell, terminal, isSource){
        this.model = model;
        this.cell = cell;
        this.terminal = terminal;
        this.previous = terminal;
        this.isSource = isSource;
    };
    mxTerminalChange.prototype.execute = function(){
        this.terminal = this.previous;
        this.previous = this.model.terminalForCellChanged(this.cell, this.previous, this.isSource);
    };
    function mxValueChange(model, cell, value){
        this.model = model;
        this.cell = cell;
        this.value = value;
        this.previous = value;
    };
    mxValueChange.prototype.execute = function(){
        this.value = this.previous;
        this.previous = this.model.valueForCellChanged(this.cell, this.previous);
    };
    function mxStyleChange(model, cell, style){
        this.model = model;
        this.cell = cell;
        this.style = style;
        this.previous = style;
    };
    mxStyleChange.prototype.execute = function(){
        this.style = this.previous;
        this.previous = this.model.styleForCellChanged(this.cell, this.previous);
    };
    function mxGeometryChange(model, cell, geometry){
        this.model = model;
        this.cell = cell;
        this.geometry = geometry;
        this.previous = geometry;
    };
    mxGeometryChange.prototype.execute = function(){
        this.geometry = this.previous;
        this.previous = this.model.geometryForCellChanged(this.cell, this.previous);
    };
    function mxCollapseChange(model, cell, collapsed){
        this.model = model;
        this.cell = cell;
        this.collapsed = collapsed;
        this.previous = collapsed;
    };
    mxCollapseChange.prototype.execute = function(){
        this.collapsed = this.previous;
        this.previous = this.model.collapsedStateForCellChanged(this.cell, this.previous);
    };
    function mxVisibleChange(model, cell, visible){
        this.model = model;
        this.cell = cell;
        this.visible = visible;
        this.previous = visible;
    };
    mxVisibleChange.prototype.execute = function(){
        this.visible = this.previous;
        this.previous = this.model.visibleStateForCellChanged(this.cell, this.previous);
    };
    function mxCellAttributeChange(cell, attribute, value){
        this.cell = cell;
        this.attribute = attribute;
        this.value = value;
        this.previous = value;
    };
    mxCellAttributeChange.prototype.execute = function(){
        var tmp = this.cell.getAttribute(this.attribute);
        if (this.previous == null) {
            this.cell.value.removeAttribute(this.attribute);
        }
        else {
            this.cell.setAttribute(this.attribute, this.previous);
        }
        this.previous = tmp;
    };
}
//mxCell
{
    function mxCell(value, geometry, style){
        this.value = value;
        this.setGeometry(geometry);
        this.setStyle(style);
        if (this.onInit != null) {
            this.onInit();
        }
    };
    mxCell.prototype.id = null;
    mxCell.prototype.value = null;
    mxCell.prototype.geometry = null;
    mxCell.prototype.style = null;
    mxCell.prototype.vertex = false;
    mxCell.prototype.edge = false;
    mxCell.prototype.connectable = true;
    mxCell.prototype.visible = true;
    mxCell.prototype.collapsed = false;
    mxCell.prototype.parent = null;
    mxCell.prototype.source = null;
    mxCell.prototype.target = null;
    mxCell.prototype.children = null;
    mxCell.prototype.edges = null;
    mxCell.prototype.mxTransient = ['id', 'value', 'parent', 'source', 'target', 'children', 'edges'];
    mxCell.prototype.getId = function(){
        return this.id;
    };
    mxCell.prototype.setId = function(id){
        this.id = id;
    };
    mxCell.prototype.getValue = function(){
        return this.value;
    };
    mxCell.prototype.setValue = function(value){
        this.value = value;
    };
    mxCell.prototype.valueChanged = function(newValue){
        var previous = this.getValue();
        this.setValue(newValue);
        return previous;
    };
    mxCell.prototype.getGeometry = function(){
        return this.geometry;
    };
    mxCell.prototype.setGeometry = function(geometry){
        this.geometry = geometry;
    };
    mxCell.prototype.getStyle = function(){
        return this.style;
    };
    mxCell.prototype.setStyle = function(style){
        this.style = style;
    };
    mxCell.prototype.isVertex = function(){
        return this.vertex;
    };
    mxCell.prototype.setVertex = function(vertex){
        this.vertex = vertex;
    };
    mxCell.prototype.isEdge = function(){
        return this.edge;
    };
    mxCell.prototype.setEdge = function(edge){
        this.edge = edge;
    };
    mxCell.prototype.isConnectable = function(){
        return this.connectable;
    };
    mxCell.prototype.setConnectable = function(connectable){
        this.connectable = connectable;
    };
    mxCell.prototype.isVisible = function(){
        return this.visible;
    };
    mxCell.prototype.setVisible = function(visible){
        this.visible = visible;
    };
    mxCell.prototype.isCollapsed = function(){
        return this.collapsed;
    };
    mxCell.prototype.setCollapsed = function(collapsed){
        this.collapsed = collapsed;
    };
    mxCell.prototype.getParent = function(parent){
        return this.parent;
    };
    mxCell.prototype.setParent = function(parent){
        this.parent = parent;
    };
    mxCell.prototype.getTerminal = function(source){
        return (source) ? this.source : this.target;
    };
    mxCell.prototype.setTerminal = function(terminal, isSource){
        if (isSource) {
            this.source = terminal;
        }
        else {
            this.target = terminal;
        }
        return terminal;
    };
    mxCell.prototype.getChildCount = function(){
        return (this.children == null) ? 0 : this.children.length;
    };
    mxCell.prototype.getIndex = function(child){
        return mxUtils.indexOf(this.children, child);
    };
    mxCell.prototype.getChildAt = function(index){
        return (this.children == null) ? null : this.children[index];
    };
    mxCell.prototype.insert = function(child, index){
        if (child != null) {
            index = (index != null) ? index : this.getChildCount();
            child.removeFromParent();
            child.setParent(this);
            if (this.children == null) {
                this.children = new Array();
                this.children.push(child);
            }
            else {
                this.children.splice(index, 0, child);
            }
        }
        return child;
    };
    mxCell.prototype.remove = function(index){
        var child = null;
        if (this.children != null && index >= 0) {
            child = this.getChildAt(index);
            if (child != null) {
                this.children.splice(index, 1);
                child.setParent(null);
            }
        }
        return child;
    };
    mxCell.prototype.removeFromParent = function(){
        if (this.parent != null) {
            var index = this.parent.getIndex(this);
            this.parent.remove(index);
        }
    };
    mxCell.prototype.getEdgeCount = function(){
        return (this.edges == null) ? 0 : this.edges.length;
    };
    mxCell.prototype.getEdgeIndex = function(edge){
        return mxUtils.indexOf(this.edges, edge);
    };
    mxCell.prototype.getEdgeAt = function(index){
        return (this.edges == null) ? null : this.edges[index];
    };
    mxCell.prototype.insertEdge = function(edge, isOutgoing){
        if (edge != null) {
            edge.removeFromTerminal(isOutgoing);
            edge.setTerminal(this, isOutgoing);
            if (this.edges == null || edge.getTerminal(!isOutgoing) != this || mxUtils.indexOf(this.edges, edge) < 0) {
                if (this.edges == null) {
                    this.edges = new Array();
                }
                this.edges.push(edge);
            }
        }
        return edge;
    };
    mxCell.prototype.removeEdge = function(edge, isOutgoing){
        if (edge != null) {
            if (edge.getTerminal(!isOutgoing) != this && this.edges != null) {
                var index = this.getEdgeIndex(edge);
                if (index >= 0) {
                    this.edges.splice(index, 1);
                }
            }
            edge.setTerminal(null, isOutgoing);
        }
        return edge;
    };
    mxCell.prototype.removeFromTerminal = function(isSource){
        var terminal = this.getTerminal(isSource);
        if (terminal != null) {
            terminal.removeEdge(this, isSource);
        }
    };
    mxCell.prototype.getAttribute = function(name, defaultValue){
        var userObject = this.getValue();
        var val = (userObject != null && userObject.nodeType == mxConstants.NODETYPE_ELEMENT) ? userObject.getAttribute(name) : null;
        return val || defaultValue;
    };
    mxCell.prototype.setAttribute = function(name, value){
        var userObject = this.getValue();
        if (userObject != null && userObject.nodeType == mxConstants.NODETYPE_ELEMENT) {
            userObject.setAttribute(name, value);
        }
    };
    mxCell.prototype.clone = function(){
        var clone = mxUtils.clone(this, this.mxTransient);
        clone.setValue(this.cloneValue());
        return clone;
    };
    mxCell.prototype.cloneValue = function(){
        var value = this.getValue();
        if (value != null) {
            if (typeof(value.clone) == 'function') {
                value = value.clone();
            }
            else 
                if (!isNaN(value.nodeType)) {
                    value = value.cloneNode(true);
                }
        }
        return value;
    };
}
//mxGeometry
{
    function mxGeometry(x, y, width, height){
        mxRectangle.call(this, x, y, width, height);
    };
    mxGeometry.prototype = new mxRectangle();
    mxGeometry.prototype.constructor = mxGeometry;
    mxGeometry.prototype.TRANSLATE_CONTROL_POINTS = true;
    mxGeometry.prototype.alternateBounds = null;
    mxGeometry.prototype.sourcePoint = null;
    mxGeometry.prototype.targetPoint = null;
    mxGeometry.prototype.points = null;
    mxGeometry.prototype.offset = null;
    mxGeometry.prototype.relative = false;
    mxGeometry.prototype.swap = function(){
        if (this.alternateBounds != null) {
            var old = new mxRectangle(this.x, this.y, this.width, this.height);
            this.x = this.alternateBounds.x;
            this.y = this.alternateBounds.y;
            this.width = this.alternateBounds.width;
            this.height = this.alternateBounds.height;
            this.alternateBounds = old;
        }
    };
    mxGeometry.prototype.getTerminalPoint = function(isSource){
        return (isSource) ? this.sourcePoint : this.targetPoint;
    };
    mxGeometry.prototype.setTerminalPoint = function(point, isSource){
        if (isSource) {
            this.sourcePoint = point;
        }
        else {
            this.targetPoint = point;
        }
        return point;
    };
    mxGeometry.prototype.translate = function(dx, dy){
        var clone = this.clone();
        if (!clone.relative) {
            clone.x += dx;
            clone.y += dy;
        }
        if (clone.sourcePoint != null) {
            clone.sourcePoint.x += dx;
            clone.sourcePoint.y += dy;
        }
        if (clone.targetPoint != null) {
            clone.targetPoint.x += dx;
            clone.targetPoint.y += dy;
        }
        if (this.TRANSLATE_CONTROL_POINTS && clone.points != null) {
            var count = clone.points.length;
            for (var i = 0; i < count; i++) {
                var pt = clone.points[i];
                if (pt != null) {
                    pt.x += dx;
                    pt.y += dy;
                }
            }
        }
        return clone;
    };
}

//mxCellPath
var mxCellPath = {
    PATH_SEPARATOR: '.',
    create: function(cell){
        var result = '';
        if (cell != null) {
            var parent = cell.getParent();
            while (parent != null) {
                var index = parent.getIndex(cell);
                result = index + mxCellPath.PATH_SEPARATOR + result;
                cell = parent;
                parent = cell.getParent();
            }
        }
        var n = result.length;
        if (n > 1) {
            result = result.substring(0, n - 1);
        }
        return result;
    },
    getParentPath: function(path){
        if (path != null) {
            var index = path.lastIndexOf(mxCellPath.PATH_SEPARATOR);
            if (index >= 0) {
                return path.substring(0, index);
            }
            else 
                if (path.length > 0) {
                    return '';
                }
        }
        return null;
    },
    resolve: function(root, path){
        var parent = root;
        if (path != null) {
            var tokens = path.split(mxCellPath.PATH_SEPARATOR);
            for (var i = 0; i < tokens.length; i++) {
                parent = parent.getChildAt(parseInt(tokens[i]));
            }
        }
        return parent;
    }
};
//mxPerimeter
var mxPerimeter = {
    RectanglePerimeter: function(bounds, edgeState, terminalState, isSource, next){
        var cx = bounds.getCenterX();
        var cy = bounds.getCenterY();
        var dx = next.x - cx;
        var dy = next.y - cy;
        var alpha = Math.atan2(dy, dx);
        var p = new mxPoint(0, 0);
        var pi = Math.PI;
        var pi2 = Math.PI / 2;
        var beta = pi2 - alpha;
        var t = Math.atan2(bounds.height, bounds.width);
        if (alpha < -pi + t || alpha > pi - t) {
            p.x = bounds.x;
            p.y = cy - bounds.width * Math.tan(alpha) / 2;
        }
        else 
            if (alpha < -t) {
                p.y = bounds.y;
                p.x = cx - bounds.height * Math.tan(beta) / 2;
            }
            else 
                if (alpha < t) {
                    p.x = bounds.x + bounds.width;
                    p.y = cy + bounds.width * Math.tan(alpha) / 2;
                }
                else {
                    p.y = bounds.y + bounds.height;
                    p.x = cx + bounds.height * Math.tan(beta) / 2;
                }
        if (edgeState != null && edgeState.view.graph.isOrthogonal(edgeState, terminalState)) {
            if (next.x >= bounds.x && next.x <= bounds.x + bounds.width) {
                p.x = next.x;
            }
            else 
                if (next.y >= bounds.y && next.y <= bounds.y + bounds.height) {
                    p.y = next.y;
                }
            if (next.x < bounds.x) {
                p.x = bounds.x;
            }
            else 
                if (next.x > bounds.x + bounds.width) {
                    p.x = bounds.x + bounds.width;
                }
            if (next.y < bounds.y) {
                p.y = bounds.y;
            }
            else 
                if (next.y > bounds.y + bounds.height) {
                    p.y = bounds.y + bounds.height;
                }
        }
        return p;
    },
    EllipsePerimeter: function(bounds, edgeState, terminalState, isSource, next){
        var x = bounds.x;
        var y = bounds.y;
        var a = bounds.width / 2;
        var b = bounds.height / 2;
        var cx = x + a;
        var cy = y + b;
        var px = next.x;
        var py = next.y;
        
        var dx = px - cx;
        var dy = py - cy;
        if (dx == 0 && dy != 0) {
            return new mxPoint(cx, cy + b * dy / Math.abs(dy));
        }
        var orthogonal = edgeState != null && edgeState.view.graph.isOrthogonal(edgeState, terminalState);
        if (orthogonal) {
            if (py >= y && py <= y + bounds.height) {
                var ty = py - cy;
                var tx = Math.sqrt(a * a * (1 - (ty * ty) / (b * b))) || 0;
                if (px <= x) {
                    tx = -tx;
                }
                return new mxPoint(cx + tx, py);
            }
            if (px >= x && px <= x + bounds.width) {
                var tx = px - cx;
                var ty = Math.sqrt(b * b * (1 - (tx * tx) / (a * a))) || 0;
                if (py <= y) {
                    ty = -ty;
                }
                return new mxPoint(px, cy + ty);
            }
        }
        var d = dy / dx;
        var h = cy - d * cx;
        var e = a * a * d * d + b * b;
        var f = -2 * cx * e;
        var g = a * a * d * d * cx * cx + b * b * cx * cx - a * a * b * b;
        var det = Math.sqrt(f * f - 4 * e * g);
        var xout1 = (-f + det) / (2 * e);
        var xout2 = (-f - det) / (2 * e);
        var yout1 = d * xout1 + h;
        var yout2 = d * xout2 + h;
        var dist1 = Math.sqrt(Math.pow((xout1 - px), 2) + Math.pow((yout1 - py), 2));
        var dist2 = Math.sqrt(Math.pow((xout2 - px), 2) + Math.pow((yout2 - py), 2));
        var xout = 0;
        var yout = 0;
        if (dist1 < dist2) {
            xout = xout1;
            yout = yout1;
        }
        else {
            xout = xout2;
            yout = yout2;
        }
        return new mxPoint(xout, yout);
    },
    RhombusPerimeter: function(bounds, edgeState, terminalState, isSource, next){
        var x = bounds.x;
        var y = bounds.y;
        var w = bounds.width;
        var h = bounds.height;
        var cx = x + w / 2;
        var cy = y + h / 2;
        var px = next.x;
        var py = next.y;
        if (cx == px) {
            if (cy > py) {
                return new mxPoint(cx, y);
            }
            else {
                return new mxPoint(cx, y + h);
            }
        }
        else 
            if (cy == py) {
                if (cx > px) {
                    return new mxPoint(x, cy);
                }
                else {
                    return new mxPoint(x + w, cy);
                }
            }
        var tx = cx;
        var ty = cy;
        if (edgeState != null && edgeState.view.graph.isOrthogonal(edgeState, terminalState)) {
            if (px >= x && px <= x + w) {
                tx = px;
            }
            else 
                if (py >= y && py <= y + h) {
                    ty = py;
                }
        }
        
        if (px < cx) {
            if (py < cy) {
                return mxUtils.intersection(px, py, tx, ty, cx, y, x, cy);
            }
            else {
                return mxUtils.intersection(px, py, tx, ty, cx, y + h, x, cy);
            }
        }
        else 
            if (py < cy) {
                return mxUtils.intersection(px, py, tx, ty, cx, y, x + w, cy);
            }
            else {
                return mxUtils.intersection(px, py, tx, ty, cx, y + h, x + w, cy);
            }
    },
    TrianglePerimeter: function(bounds, edgeState, terminalState, isSource, next){
        var orthogonal = edgeState != null && edgeState.view.graph.isOrthogonal(edgeState, terminalState);
        var direction = (terminalState != null) ? terminalState.style[mxConstants.STYLE_DIRECTION] : null;
        var vertical = direction == mxConstants.DIRECTION_NORTH || direction == mxConstants.DIRECTION_SOUTH;
        var x = bounds.x;
        var y = bounds.y;
        var w = bounds.width;
        var h = bounds.height;
        var cx = x + w / 2;
        var cy = y + h / 2;
        var start = new mxPoint(x, y);
        var corner = new mxPoint(x + w, cy);
        var end = new mxPoint(x, y + h);
        if (direction == mxConstants.DIRECTION_NORTH) {
            start = end;
            corner = new mxPoint(cx, y);
            end = new mxPoint(x + w, y + h);
        }
        else 
            if (direction == mxConstants.DIRECTION_SOUTH) {
                corner = new mxPoint(cx, y + h);
                end = new mxPoint(x + w, y);
            }
            else 
                if (direction == mxConstants.DIRECTION_WEST) {
                    start = new mxPoint(x + w, y);
                    corner = new mxPoint(x, cy);
                    end = new mxPoint(x + w, y + h);
                }
        var dx = next.x - cx;
        var dy = next.y - cy;
        var alpha = (vertical) ? Math.atan2(dx, dy) : Math.atan2(dy, dx);
        var t = (vertical) ? Math.atan2(w, h) : Math.atan2(h, w);
        var base = false;
        if (direction == mxConstants.DIRECTION_NORTH || direction == mxConstants.DIRECTION_WEST) {
            base = alpha > -t && alpha < t;
        }
        else {
            base = alpha < -Math.PI + t || alpha > Math.PI - t;
        }
        var result = null;
        if (base) {
            if (orthogonal && ((vertical && next.x >= start.x && next.x <= end.x) || (!vertical && next.y >= start.y && next.y <= end.y))) {
                if (vertical) {
                    result = new mxPoint(next.x, start.y);
                }
                else {
                    result = new mxPoint(start.x, next.y);
                }
            }
            else {
                if (direction == mxConstants.DIRECTION_NORTH) {
                    result = new mxPoint(x + w / 2 + h * Math.tan(alpha) / 2, y + h);
                }
                else 
                    if (direction == mxConstants.DIRECTION_SOUTH) {
                        result = new mxPoint(x + w / 2 - h * Math.tan(alpha) / 2, y);
                    }
                    else 
                        if (direction == mxConstants.DIRECTION_WEST) {
                            result = new mxPoint(x + w, y + h / 2 + w * Math.tan(alpha) / 2);
                        }
                        else {
                            result = new mxPoint(x, y + h / 2 - w * Math.tan(alpha) / 2);
                        }
            }
        }
        else {
            if (orthogonal) {
                var pt = new mxPoint(cx, cy);
                if (next.y >= y && next.y <= y + h) {
                    pt.x = (vertical) ? cx : ((direction == mxConstants.DIRECTION_WEST) ? x + w : x);
                    pt.y = next.y;
                }
                else 
                    if (next.x >= x && next.x <= x + w) {
                        pt.x = next.x;
                        pt.y = (!vertical) ? cy : ((direction == mxConstants.DIRECTION_NORTH) ? y + h : y);
                    }
                dx = next.x - pt.x;
                dy = next.y - pt.y;
                cx = pt.x;
                cy = pt.y;
            }
            if ((vertical && next.x <= x + w / 2) || (!vertical && next.y <= y + h / 2)) {
                result = mxUtils.intersection(next.x, next.y, cx, cy, start.x, start.y, corner.x, corner.y);
            }
            else {
                result = mxUtils.intersection(next.x, next.y, cx, cy, corner.x, corner.y, end.x, end.y);
            }
        }
        if (result == null) {
            result = new mxPoint(cx, cy);
        }
        return result;
    }
};
//mxPrintPreview
{
    function mxPrintPreview(graph, scale, pageFormat, x0, y0, border, title, pageSelector){
        x0 = (x0 != null) ? x0 : 0;
        y0 = (y0 != null) ? y0 : 0;
        scale = (scale != null) ? scale : 1;
        pageSelector = (pageSelector != null) ? pageSelector : true;
        if (pageFormat == null) {
            pageFormat = mxConstants.PAGE_FORMAT_A4_PORTRAIT;
        }
        this.graph = graph;
        this.pageFormat = pageFormat;
        this.scale = scale;
        this.x0 = x0;
        this.y0 = y0;
        this.border = border;
        this.title = title;
        this.pageSelector = pageSelector;
    };
    mxPrintPreview.prototype.graph = null;
    mxPrintPreview.prototype.pageFormat = null;
    mxPrintPreview.prototype.scale = null;
    mxPrintPreview.prototype.x0 = null;
    mxPrintPreview.prototype.y0 = null;
    mxPrintPreview.prototype.border = null;
    mxPrintPreview.prototype.title = null;
    mxPrintPreview.prototype.pageSelector = null;
    mxPrintPreview.prototype.wnd = null;
    mxPrintPreview.prototype.getWindow = function(){
        return this.wnd;
    };
    mxPrintPreview.prototype.open = function(){
        if (this.wnd == null) {
            this.wnd = window.open();
            var doc = this.wnd.document;
            doc.writeln('<html>');
            doc.writeln('<head>');
            if (this.title != null) {
                doc.writeln('<title>' + this.title + '</title>');
            }
            doc.writeln('<style type="text/css">');
            doc.writeln('@media print {');
            doc.writeln('  table.mxPageSelector { display: none; }');
            doc.writeln('  hr { display: none; }');
            doc.writeln('}');
            doc.writeln('@media screen {');
            
            doc.writeln('  table.mxPageSelector { position: fixed; right: 10px; top: 10px;' + 'font-family: Arial; font-size:10pt; color: gray; border-color: gray;' + 'background: white;}');
            doc.writeln('  body { background: gray; }');
            doc.writeln('}');
            doc.writeln('</style>');
            doc.writeln('</head>');
            doc.writeln('<body>');
            mxClient.link('stylesheet', mxClient.basePath + 'css/common.css', doc);
		    var isIe=mxClient.IS_IE||mxClient.IS_IE7;
            if (isIe) {
                doc.namespaces.add("v", "urn:schemas-microsoft-com:vml");
                doc.namespaces.add("o", "urn:schemas-microsoft-com:office:office");
                mxClient.link('stylesheet', mxClient.basePath + 'css/explorer.css', doc);
            }
            var bounds = this.graph.getGraphBounds().clone();
            var sc = this.graph.getView().getScale() / this.scale;
            var tr = this.graph.getView().getTranslate();
            
            bounds.x /= sc;
            bounds.x -= tr.x - this.x0;
            bounds.y /= sc;
            bounds.y -= tr.y - this.y0;
            bounds.width /= sc;
            bounds.height /= sc;
            var hpages = Math.max(1, Math.ceil((bounds.x + bounds.width) / this.pageFormat.width));
            var vpages = Math.max(1, Math.ceil((bounds.y + bounds.height) / this.pageFormat.height));
            
            for (var i = 0; i < vpages; i++) {
                dy = i * this.pageFormat.height / this.scale;
                for (var j = 0; j < hpages; j++) {
                    dx = j * this.pageFormat.width / this.scale;
                    var div = this.renderPage(this.pageFormat.width, this.pageFormat.height, -dx, -dy, this.scale);
                    var pageNum = i * hpages + j + 1;
                    div.setAttribute('id', 'mxPage-' + pageNum);
                    if (this.border != null) {
                        div.style.borderColor = this.border;
                        div.style.borderStyle = 'solid';
                        div.style.borderWidth = '1px';
                    }
                    
                    div.style.background = 'white';
                    if (i < vpages - 1 || j < hpages - 1) {
                        div.style.pageBreakAfter = 'always';
                    }
                    
                    var isIe=mxClient.IS_IE||mxClient.IS_IE7;
                    if (isIe) {
                        doc.writeln(div.outerHTML);
                        div.parentNode.removeChild(div);
                    }
                    else {
                        div.parentNode.removeChild(div);
                        doc.body.appendChild(div);
                    }
                    if (i < vpages - 1 || j < hpages - 1) {
                        doc.body.appendChild(doc.createElement('hr'));
                    }
                }
            }
            doc.writeln('</body>');
            doc.writeln('</html>');
            doc.close();
            mxEvent.release(doc.body);
            if (this.pageSelector && (vpages > 1 || hpages > 1)) {
                var table = this.createPageSelector(vpages, hpages);
                doc.body.appendChild(table);
                if (false) {
                    table.style.position = 'absolute';
                    var update = function(){
                        table.style.top = (doc.body.scrollTop + 10) + 'px';
                    };
                    mxEvent.addListener(this.wnd, 'scroll', function(evt){
                        update();
                    });
                    mxEvent.addListener(this.wnd, 'resize', function(evt){
                        update();
                    });
                }
            }
        }
        this.wnd.focus();
        return this.wnd;
    };
    mxPrintPreview.prototype.createPageSelector = function(vpages, hpages){
        var doc = this.wnd.document;
        var table = doc.createElement('table');
        table.className = 'mxPageSelector';
        table.setAttribute('border', '1');
        var tbody = doc.createElement('tbody');
        for (var i = 0; i < vpages; i++) {
            var row = doc.createElement('tr');
            for (var j = 0; j < hpages; j++) {
                var cell = doc.createElement('td');
                cell.style.cursor = 'pointer';
                var pageNum = i * hpages + j + 1;
                mxUtils.write(cell, pageNum, doc);
                this.addPageClickListener(cell, pageNum);
                row.appendChild(cell);
            }
            tbody.appendChild(row);
        }
        table.appendChild(tbody);
        return table;
    };
    mxPrintPreview.prototype.addPageClickListener = function(cell, pageNumber){
        var self = this;
        mxEvent.addListener(cell, 'click', function(evt){
            var page = self.wnd.document.getElementById('mxPage-' + pageNumber);
            if (page != null) {
                self.wnd.scrollTo(0, Math.max(0, page.offsetTop - 8));
            }
        });
    };
    mxPrintPreview.prototype.renderPage = function(w, h, dx, dy, scale){
        var div = document.createElement('div');
        div.style.width = w + 'px';
        div.style.height = h + 'px';
        div.style.overflow = 'hidden';
        div.style.pageBreakInside = 'avoid';
        document.body.appendChild(div);
        var view = this.graph.getView();
        var previousContainer = this.graph.container;
        this.graph.container = div;
        var canvas = view.getCanvas();
        var backgroundPane = view.getBackgroundPane();
        var drawPane = view.getDrawPane();
        var overlayPane = view.getOverlayPane();
        if (this.graph.dialect == mxConstants.DIALECT_SVG) {
            view.createSvg();
        }
        else 
            if (this.graph.dialect == mxConstants.DIALECT_VML) {
                view.createVml();
            }
            else {
                view.createHtml();
            }
        var eventsEnabled = view.isEventsEnabled();
        view.setEventsEnabled(false);
        var graphEnabled = this.graph.isEnabled();
        this.graph.setEnabled(false);
        var translate = view.getTranslate();
        view.translate = new mxPoint(dx, dy);
        var temp = null;
        try {
        
            var model = this.graph.getModel();
            var cells = [model.getRoot()];
            temp = new mxTemporaryCellStates(view, scale, cells);
        }
        finally {
			var isIe=mxClient.IS_IE||mxClient.IS_IE7;
            if (isIe) {
                view.overlayPane.innerHTML = '';
            }
            else {
                var tmp = div.firstChild;
                while (tmp != null) {
                    var next = tmp.nextSibling;
                    
                    
                    if (tmp.nodeName.toLowerCase() != 'svg' && tmp.style.cursor != 'default') {
                        tmp.parentNode.removeChild(tmp);
                    }
                    tmp = next;
                }
            }
            view.overlayPane.parentNode.removeChild(view.overlayPane);
            this.graph.setEnabled(graphEnabled);
            this.graph.container = previousContainer;
            view.canvas = canvas;
            view.backgroundPane = backgroundPane;
            view.drawPane = drawPane;
            view.overlayPane = overlayPane;
            view.translate = translate;
            temp.destroy();
            view.setEventsEnabled(eventsEnabled);
        }
        return div;
    };
    mxPrintPreview.prototype.print = function(){
        this.open();
        this.wnd.print();
    };
    mxPrintPreview.prototype.close = function(){
        if (this.wnd != null) {
            this.wnd.close();
            this.wnd = null;
        }
    };
}
//mxStylesheet
{
    function mxStylesheet(){
        this.styles = new Object();
        this.putDefaultVertexStyle(this.createDefaultVertexStyle());
        this.putDefaultEdgeStyle(this.createDefaultEdgeStyle());
    };
    mxStylesheet.prototype.styles;
    mxStylesheet.prototype.createDefaultVertexStyle = function(){
        var style = new Object();
        style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_RECTANGLE;
        style[mxConstants.STYLE_PERIMETER] = mxPerimeter.RectanglePerimeter;
        style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
        style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
        style[mxConstants.STYLE_FILLCOLOR] = '#C3D9FF';
        style[mxConstants.STYLE_STROKECOLOR] = '#6482B9';
        style[mxConstants.STYLE_FONTCOLOR] = '#774400';
        return style;
    };
    mxStylesheet.prototype.createDefaultEdgeStyle = function(){
        var style = new Object();
        style[mxConstants.STYLE_SHAPE] = mxConstants.SHAPE_CONNECTOR;
        style[mxConstants.STYLE_ENDARROW] = mxConstants.ARROW_CLASSIC;
        style[mxConstants.STYLE_VERTICAL_ALIGN] = mxConstants.ALIGN_MIDDLE;
        style[mxConstants.STYLE_ALIGN] = mxConstants.ALIGN_CENTER;
        style[mxConstants.STYLE_STROKECOLOR] = '#6482B9';
        style[mxConstants.STYLE_FONTCOLOR] = '#446299';
        return style;
    };
    mxStylesheet.prototype.putDefaultVertexStyle = function(style){
        this.putCellStyle('defaultVertex', style);
    };
    mxStylesheet.prototype.putDefaultEdgeStyle = function(style){
        this.putCellStyle('defaultEdge', style);
    };
    mxStylesheet.prototype.getDefaultVertexStyle = function(){
        return this.styles['defaultVertex'];
    };
    mxStylesheet.prototype.getDefaultEdgeStyle = function(){
        return this.styles['defaultEdge'];
    };
    mxStylesheet.prototype.putCellStyle = function(name, style){
        this.styles[name] = style;
    };
    mxStylesheet.prototype.getCellStyle = function(name, defaultStyle){
        var style = defaultStyle;
        if (name != null && name.length > 0) {
            var pairs = name.split(';');
            if (pairs != null && pairs.length > 0) {
                if (style != null && pairs[0].indexOf('=') >= 0) {
                    style = mxUtils.clone(style);
                }
                else {
                    style = new Object();
                }
                for (var i = 0; i < pairs.length; i++) {
                    var tmp = pairs[i];
                    var pos = tmp.indexOf('=');
                    if (pos >= 0) {
                        var key = tmp.substring(0, pos);
                        var value = tmp.substring(pos + 1);
                        if (value == mxConstants.NONE) {
                            delete style[key];
                        }
                        else 
                            if (mxUtils.isNumeric(value)) {
                                style[key] = parseFloat(value);
                            }
                            else {
                                style[key] = value;
                            }
                    }
                    else {
                        var tmpStyle = this.styles[tmp];
                        if (tmpStyle != null) {
                            for (var key in tmpStyle) {
                                style[key] = tmpStyle[key];
                            }
                        }
                    }
                }
            }
        }
        return style;
    };
}
//mxCellState
{
    function mxCellState(view, cell, style){
        this.view = view;
        this.cell = cell;
        this.style = style;
        this.origin = new mxPoint();
        this.absoluteOffset = new mxPoint();
    };
    mxCellState.prototype = new mxRectangle();
    mxCellState.prototype.constructor = mxCellState;
    mxCellState.prototype.view = null;
    mxCellState.prototype.cell = null;
    mxCellState.prototype.style = null;
    mxCellState.prototype.invalid = true;
    mxCellState.prototype.orderChanged = null;
    mxCellState.prototype.origin = null;
    mxCellState.prototype.absolutePoints = null;
    mxCellState.prototype.absoluteOffset = null;
    mxCellState.prototype.terminalDistance = 0;
    mxCellState.prototype.length = 0;
    mxCellState.prototype.segments = null;
    mxCellState.prototype.shape = null;
    mxCellState.prototype.text = null;
    mxCellState.prototype.getPerimeterBounds = function(border){
        border = border || 0;
        var bounds = new mxRectangle(this.x, this.y, this.width, this.height);
        if (border != 0) {
            bounds.grow(border);
        }
        return bounds;
    };
    mxCellState.prototype.setAbsoluteTerminalPoint = function(point, isSource){
        if (isSource) {
            if (this.absolutePoints == null) {
                this.absolutePoints = new Array();
            }
            if (this.absolutePoints.length == 0) {
                this.absolutePoints.push(point);
            }
            else {
                this.absolutePoints[0] = point;
            }
        }
        else {
            if (this.absolutePoints == null) {
                this.absolutePoints = new Array();
                this.absolutePoints.push(null);
                this.absolutePoints.push(point);
            }
            else 
                if (this.absolutePoints.length == 1) {
                    this.absolutePoints.push(point);
                }
                else {
                    this.absolutePoints[this.absolutePoints.length - 1] = point;
                }
        }
    };
    mxCellState.prototype.destroy = function(){
        this.view.graph.cellRenderer.destroy(this);
        this.view.graph.destroyHandler(this);
    };
    mxCellState.prototype.clone = function(){
        var clone = new mxCellState(this.view, this.cell, this.style);
        if (this.absolutePoints != null) {
            clone.absolutePoints = new Array();
            for (i = 0; i < this.absolutePoints.length; i++) {
                clone.absolutePoints.push(this.absolutePoints[i].clone());
            }
        }
        if (this.origin != null) {
            clone.origin = this.origin.clone();
        }
        if (this.absoluteOffset != null) {
            clone.absoluteOffset = this.absoluteOffset.clone();
        }
        if (this.sourcePoint != null) {
            clone.sourcePoint = this.sourcePoint.clone();
        }
        if (this.boundingBox != null) {
            clone.boundingBox = this.boundingBox.clone();
        }
        clone.terminalDistance = this.terminalDistance;
        clone.segments = this.segments;
        clone.length = this.length;
        clone.x = this.x;
        clone.y = this.y;
        clone.width = this.width;
        clone.height = this.height;
        return clone;
    };
}
//mxGraphSelectionModel
{
    function mxGraphSelectionModel(graph){
        this.graph = graph;
        this.cells = new Array();
    };
    mxGraphSelectionModel.prototype = new mxEventSource();
    mxGraphSelectionModel.prototype.constructor = mxGraphSelectionModel;
    mxGraphSelectionModel.prototype.doneResource = (mxClient.language != 'none') ? 'done' : '';
    mxGraphSelectionModel.prototype.updatingSelectionResource = (mxClient.language != 'none') ? 'updatingSelection' : '';
    mxGraphSelectionModel.prototype.graph = null;
    mxGraphSelectionModel.prototype.singleSelection = false;
    mxGraphSelectionModel.prototype.isSingleSelection = function(){
        return this.singleSelection;
    };
    mxGraphSelectionModel.prototype.setSingleSelection = function(singleSelection){
        this.singleSelection = singleSelection;
    };
    mxGraphSelectionModel.prototype.isSelected = function(cell){
        if (cell != null) {
            var state = this.graph.getView().getState(cell);
            return this.graph.hasHandler(state);
        }
        return false;
    };
    mxGraphSelectionModel.prototype.isEmpty = function(){
        return this.cells.length == 0;
    };
    mxGraphSelectionModel.prototype.clear = function(){
        this.changeSelection(null, this.cells);
    };
    mxGraphSelectionModel.prototype.setCell = function(cell){
        if (cell != null) {
            this.setCells([cell]);
        }
    };
    mxGraphSelectionModel.prototype.setCells = function(cells){
        if (cells != null) {
            if (this.singleSelection) {
                cells = [this.getFirstSelectableCell(cells)];
            }
            var tmp = new Array();
            for (var i = 0; i < cells.length; i++) {
                if (this.graph.isCellSelectable(cells[i])) {
                    tmp.push(cells[i]);
                }
            }
            this.changeSelection(tmp, this.cells);
        }
    };
    mxGraphSelectionModel.prototype.getFirstSelectableCell = function(cells){
        if (cells != null) {
            for (var i = 0; i < cells.length; i++) {
                if (this.graph.isCellSelectable(cells[i])) {
                    return cells[i];
                }
            }
        }
        return null;
    };
    mxGraphSelectionModel.prototype.addCell = function(cell){
        if (cell != null) {
            this.addCells([cell]);
        }
    };
    mxGraphSelectionModel.prototype.addCells = function(cells){
        if (cells != null) {
            var remove = null;
            if (this.singleSelection) {
                remove = this.cells;
                cells = [this.getFirstSelectableCell(cells)];
            }
            var tmp = new Array();
            for (var i = 0; i < cells.length; i++) {
                if (!this.isSelected(cells[i]) && this.graph.isCellSelectable(cells[i])) {
                    tmp.push(cells[i]);
                }
            }
            this.changeSelection(tmp, remove);
        }
    };
    mxGraphSelectionModel.prototype.removeCell = function(cell){
        if (cell != null) {
            this.removeCells([cell]);
        }
    };
    mxGraphSelectionModel.prototype.removeCells = function(cells){
        if (cells != null) {
            var tmp = new Array();
            for (var i = 0; i < cells.length; i++) {
                if (this.isSelected(cells[i])) {
                    tmp.push(cells[i]);
                }
            }
            this.changeSelection(null, tmp);
        }
    };
    mxGraphSelectionModel.prototype.changeSelection = function(added, removed){
        if ((added != null && added.length > 0 && added[0] != null) || (removed != null && removed.length > 0 && removed[0] != null)) {
            var change = new mxSelectionChange(this, added, removed);
            change.execute();
            var edit = new mxUndoableEdit(this, false);
            edit.add(change);
            this.fireEvent(mxEvent.UNDO, new mxEventObject([edit]));
        }
    };
    mxGraphSelectionModel.prototype.cellAdded = function(cell){
        if (cell != null) {
            var state = this.graph.getView().getState(cell);
            if (!this.graph.hasHandler(state)) {
                this.graph.createHandler(state);
                this.cells.push(cell);
            }
        }
    };
    mxGraphSelectionModel.prototype.cellRemoved = function(cell){
        if (cell != null) {
            var index = mxUtils.indexOf(this.cells, cell);
            if (index >= 0) {
                var state = this.graph.getView().getState(cell);
                this.graph.destroyHandler(state);
                this.cells.splice(index, 1);
            }
        }
    };
    function mxSelectionChange(selectionModel, added, removed){
        this.selectionModel = selectionModel;
        this.added = (added != null) ? added.slice() : null;
        this.removed = (removed != null) ? removed.slice() : null;
    };
    mxSelectionChange.prototype.execute = function(){
        var t0 = mxLog.enter('mxSelectionChange.execute');
        window.status = mxResources.get(this.selectionModel.updatingSelectionResource) || this.selectionModel.updatingSelectionResource;
        if (this.removed != null) {
            for (var i = 0; i < this.removed.length; i++) {
                this.selectionModel.cellRemoved(this.removed[i]);
            }
        }
        if (this.added != null) {
            for (var i = 0; i < this.added.length; i++) {
                this.selectionModel.cellAdded(this.added[i]);
            }
        }
        var tmp = this.added;
        this.added = this.removed;
        this.removed = tmp;
        window.status = mxResources.get(this.selectionModel.doneResource) || this.selectionModel.doneResource;
        mxLog.leave('mxSelectionChange.execute', t0);
        this.selectionModel.fireEvent(mxEvent.CHANGE, new mxEventObject([this.removed, this.added]));
    };
}
//mxCellEditor
{
    function mxCellEditor(graph){
        this.graph = graph;
        this.textarea = document.createElement('textarea');
        this.textarea.className = 'mxCellEditor';
        this.textarea.style.position = 'absolute';
        this.textarea.style.overflow = 'visible';
        this.textarea.setAttribute('cols', '20');
        this.textarea.setAttribute('rows', '4');
        
        this.init();
    };
    mxCellEditor.prototype.graph = null;
    mxCellEditor.prototype.textarea = null;
    mxCellEditor.prototype.editingCell = null;
    mxCellEditor.prototype.trigger = null;
    mxCellEditor.prototype.modified = false;
    mxCellEditor.prototype.emptyLabelText = '';
    mxCellEditor.prototype.textNode = '';
    mxCellEditor.prototype.init = function(){
        var self = this;
        mxEvent.addListener(this.textarea, 'blur', function(evt){
            self.stopEditing(!self.graph.isInvokesStopCellEditing());
        });
        mxEvent.addListener(this.textarea, 'keydown', function(evt){
            if (self.clearOnChange) {
                self.clearOnChange = false;
                self.textarea.value = '';
            }
            self.setModified(true);
        });
    };
    mxCellEditor.prototype.isModified = function(){
        return this.modified;
    };
    mxCellEditor.prototype.setModified = function(value){
        this.modified = value;
    };
    mxCellEditor.prototype.startEditing = function(cell, trigger){
        this.stopEditing(true);
        var state = this.graph.getView().getState(cell);
        if (state != null) {
            this.editingCell = cell;
            this.trigger = trigger;
            this.textNode = null;
            if (state.text != null) {
                if (this.isHideLabel(state)) {
                    this.textNode = state.text.node;
                    this.textNode.style.visibility = 'hidden';
                }
                var scale = this.graph.getView().scale;
                this.textarea.style.fontSize = state.text.size * scale;
                this.textarea.style.fontFamily = state.text.family;
                this.textarea.style.color = state.text.color;
                if (this.textarea.style.color == 'white') {
                    this.textarea.style.color = 'black';
                }
                this.textarea.style.textAlign = (this.graph.model.isEdge(state.cell)) ? 'left' : (state.text.align || 'left');
                this.textarea.style.fontWeight = state.text.isStyleSet(mxConstants.FONT_BOLD) ? 'bold' : 'normal';
            }
            var bounds = this.getEditorBounds(state);
            this.textarea.style.left = bounds.x + 'px';
            this.textarea.style.top = bounds.y + 'px';
            this.textarea.style.width = bounds.width + 'px';
            this.textarea.style.height = bounds.height + 'px';
            this.textarea.style.zIndex = 5;
            var value = this.getInitialValue(state, trigger);
            
            
            if (value == null || value.length == 0) {
                value = this.getEmptyLabelText();
                this.clearOnChange = true;
            }
            else {
                this.clearOnChange = false;
            }
            this.setModified(false);
            this.textarea.value = value;
            this.graph.container.appendChild(this.textarea);
            this.textarea.focus();
            this.textarea.select();
        }
    };
    mxCellEditor.prototype.stopEditing = function(cancel){
        cancel = cancel || false;
        if (this.editingCell != null) {
            if (this.textNode != null) {
                this.textNode.style.visibility = 'visible';
                this.textNode = null;
            }
            if (!cancel && this.isModified()) {
                this.graph.labelChanged(this.editingCell, this.getCurrentValue(), this.trigger);
            }
            this.editingCell = null;
            this.trigger = null;
            this.textarea.blur();
            this.textarea.parentNode.removeChild(this.textarea);
        }
    };
    mxCellEditor.prototype.getInitialValue = function(state, trigger){
        return this.graph.getEditingValue(state.cell, trigger);
    };
    mxCellEditor.prototype.getCurrentValue = function(){
        return this.textarea.value.replace(/\r/g, '');
    };
    mxCellEditor.prototype.isHideLabel = function(state){
        return true;
    };
    mxCellEditor.prototype.getEditorBounds = function(state){
        var isEdge = this.graph.getModel().isEdge(state.cell);
        var scale = this.graph.getView().scale;
        var minHeight = (state.text == null) ? 30 : state.text.size * scale + 20;
        var minWidth = (this.textarea.style.textAlign == 'left') ? 120 : 40;
        var spacing = parseInt(state.style[mxConstants.STYLE_SPACING] || 2) * scale;
        var spacingTop = (parseInt(state.style[mxConstants.STYLE_SPACING_TOP] || 0)) * scale + spacing;
        var spacingRight = (parseInt(state.style[mxConstants.STYLE_SPACING_RIGHT] || 0)) * scale + spacing;
        var spacingBottom = (parseInt(state.style[mxConstants.STYLE_SPACING_BOTTOM] || 0)) * scale + spacing;
        var spacingLeft = (parseInt(state.style[mxConstants.STYLE_SPACING_LEFT] || 0)) * scale + spacing;
        var result = new mxRectangle(state.x, state.y, Math.max(minWidth, state.width - spacingLeft - spacingRight), Math.max(minHeight, state.height - spacingTop - spacingBottom));
        if (isEdge) {
            result.x = state.absoluteOffset.x;
            result.y = state.absoluteOffset.y;
            if (state.text != null && state.text.boundingBox != null) {
                result.x = state.text.boundingBox.x;
                result.y = state.text.boundingBox.y;
            }
        }
        else 
            if (state.text != null && state.text.boundingBox != null) {
                result.x = Math.min(result.x, state.text.boundingBox.x);
                result.y = Math.min(result.y, state.text.boundingBox.y);
            }
        result.x += spacingLeft;
        result.y += spacingTop;
        if (state.text != null && state.text.boundingBox != null) {
            if (!isEdge) {
                result.width = Math.max(result.width, state.text.boundingBox.width);
                result.height = Math.max(result.height, state.text.boundingBox.height);
            }
            else {
                result.width = Math.max(minWidth, state.text.boundingBox.width);
                result.height = Math.max(minHeight, state.text.boundingBox.height);
            }
        }
        if (this.graph.getModel().isVertex(state.cell)) {
            var horizontal = mxUtils.getValue(state.style, mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
            if (horizontal == mxConstants.ALIGN_LEFT) {
                result.x -= state.width;
            }
            else 
                if (horizontal == mxConstants.ALIGN_RIGHT) {
                    result.x += state.width;
                }
            var vertical = mxUtils.getValue(state.style, mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE);
            if (vertical == mxConstants.ALIGN_TOP) {
                result.y -= state.height;
            }
            else 
                if (vertical == mxConstants.ALIGN_BOTTOM) {
                    result.y += state.height;
                }
        }
        return result;
    };
    mxCellEditor.prototype.getEmptyLabelText = function(cell){
        return this.emptyLabelText;
    };
    mxCellEditor.prototype.getEditingCell = function(){
        return this.editingCell;
    };
    mxCellEditor.prototype.destroy = function(){
        mxEvent.release(this.textarea);
        if (this.textarea.parentNode != null) {
            this.textarea.parentNode.removeChild(this.textarea);
        }
        this.textarea = null;
    };
}
//mxCellRenderer
{
    function mxCellRenderer(){
        this.shapes = mxUtils.clone(this.defaultShapes);
    };
    mxCellRenderer.prototype.collapseExpandResource = (mxClient.language != 'none') ? 'collapse-expand' : '';
    mxCellRenderer.prototype.shapes = null;
    mxCellRenderer.prototype.defaultEdgeShape = mxPolyline;
    mxCellRenderer.prototype.defaultVertexShape = mxRectangleShape;
    mxCellRenderer.prototype.defaultShapes = new Object();
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_ARROW] = mxArrow;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_RECTANGLE] = mxRectangleShape;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_ELLIPSE] = mxEllipse;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_DOUBLE_ELLIPSE] = mxDoubleEllipse;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_RHOMBUS] = mxRhombus;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_IMAGE] = mxImageShape;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_LINE] = mxLine;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_LABEL] = mxLabel;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_CYLINDER] = mxCylinder;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_SWIMLANE] = mxSwimlane;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_CONNECTOR] = mxConnector;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_ACTOR] = mxActor;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_CLOUD] = mxCloud;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_TRIANGLE] = mxTriangle;
    mxCellRenderer.prototype.defaultShapes[mxConstants.SHAPE_HEXAGON] = mxHexagon;
    mxCellRenderer.prototype.registerShape = function(key, shape){
        this.shapes[key] = shape;
    };
    mxCellRenderer.prototype.initialize = function(state){
        var model = state.view.graph.getModel();
        if (state.view.graph.container != null && state.shape == null && state.cell != state.view.currentRoot && (model.isVertex(state.cell) || model.isEdge(state.cell))) {
            this.createShape(state);
            if (state.shape != null) {
                this.initializeShape(state);
                if (state.view.graph.ordered) {
                    state.orderChanged = true;
                }
                else 
                    if (model.isEdge(state.cell)) {
                        this.orderEdge(state);
                    }
                    else 
                        if (state.view.graph.keepEdgesInForeground && this.firstEdge != null) {
                            if (this.firstEdge.parentNode ==
                            state.shape.node.parentNode) {
                                this.inserState(state, this.firstEdge);
                            }
                            else {
                                this.firstEdge = null;
                            }
                        }
                state.shape.scale = state.view.scale;
                this.createCellOverlays(state);
                this.installListeners(state);
            }
            
            var cells = state.view.graph.getSelectionCells();
            if (mxUtils.indexOf(cells, state.cell) >= 0) {
                state.doCreateHandler = true;
            }
        }
    };
    mxCellRenderer.prototype.initializeShape = function(state){
        state.shape.init(state.view.getDrawPane());
    };
    mxCellRenderer.prototype.getPreviousStateInContainer = function(state, container){
        var result = null;
        var graph = state.view.graph;
        var model = graph.getModel();
        var child = state.cell;
        var p = model.getParent(child);
        while (p != null && result == null) {
            result = this.findPreviousStateInContainer(graph, p, child, container);
            child = p;
            p = model.getParent(child);
        }
        return result;
    };
    mxCellRenderer.prototype.findPreviousStateInContainer = function(graph, cell, stop, container){
        var result = null;
        var model = graph.getModel();
        if (stop != null) {
            var start = cell.getIndex(stop);
            for (var i = start - 1; i >= 0 && result == null; i--) {
                result = this.findPreviousStateInContainer(graph, model.getChildAt(cell, i), null, container);
            }
        }
        else {
            var childCount = model.getChildCount(cell);
            for (var i = childCount - 1; i >= 0 && result == null; i--) {
                result = this.findPreviousStateInContainer(graph, model.getChildAt(cell, i), null, container);
            }
        }
        if (result == null) {
            result = graph.view.getState(cell);
            if (result != null && (result.shape == null || result.shape.node == null || result.shape.node.parentNode != container)) {
                result = null;
            }
        }
        return result;
    };
    mxCellRenderer.prototype.order = function(state){
        var container = state.shape.node.parentNode;
        var previous = this.getPreviousStateInContainer(state, container);
        var nextNode = container.firstChild;
        if (previous != null) {
            nextNode = previous.shape.node;
            if (previous.text != null && previous.text.node != null && previous.text.node.parentNode == container) {
                nextNode = previous.text.node;
            }
            nextNode = nextNode.nextSibling;
        }
        this.insertState(state, nextNode);
    };
    mxCellRenderer.prototype.orderEdge = function(state){
        var view = state.view;
        var model = view.graph.getModel();
        if (view.graph.keepEdgesInForeground) {
            var node = state.shape.node;
            if (this.firstEdge == null || this.firstEdge.parentNode == null || this.firstEdge.parentNode != state.shape.node.parentNode) {
                this.firstEdge = state.shape.node;
            }
        }
        else 
            if (view.graph.keepEdgesInBackground) {
                var node = state.shape.node;
                var parent = node.parentNode;
                var pcell = model.getParent(state.cell);
                var pstate = view.getState(pcell);
                if (pstate != null && pstate.shape != null && pstate.shape.node != null) {
                    var child = pstate.shape.node.nextSibling;
                    if (child != null && child != node) {
                        this.insertState(state, child);
                    }
                }
                else {
                    var child = parent.firstChild;
                    if (child != null && child != node) {
                        this.insertState(state, child);
                    }
                }
            }
    };
    mxCellRenderer.prototype.insertState = function(state, nextNode){
        state.shape.node.parentNode.insertBefore(state.shape.node, nextNode);
        if (state.text != null && state.text.node != null && state.text.node.parentNode == state.shape.node.parentNode) {
            state.shape.node.parentNode.insertBefore(state.text.node, state.shape.node.nextSibling);
        }
    };
    mxCellRenderer.prototype.createShape = function(state){
        if (state.style != null) {
            var ctor = this.getShapeConstructor(state);
            state.shape = new ctor();
            state.shape.points = state.absolutePoints;
            state.shape.bounds = new mxRectangle(state.x, state.y, state.width, state.height);
            state.shape.dialect = state.view.graph.dialect;
            this.configureShape(state);
        }
    };
    mxCellRenderer.prototype.getShapeConstructor = function(state){
        var key = state.style[mxConstants.STYLE_SHAPE];
        var ctor = (key != null) ? this.shapes[key] : null;
        if (ctor == null) {
            ctor = (state.view.graph.getModel().isEdge(state.cell)) ? this.defaultEdgeShape : this.defaultVertexShape;
        }
        return ctor;
    };
    mxCellRenderer.prototype.configureShape = function(state){
        state.shape.apply(state);
        var image = state.view.graph.getImage(state);
        if (image != null) {
            state.shape.image = image;
        }
        var indicator = state.view.graph.getIndicatorColor(state);
        var key = state.view.graph.getIndicatorShape(state);
        var ctor = (key != null) ? this.shapes[key] : null;
        if (indicator != null) {
            state.shape.indicatorShape = ctor;
            state.shape.indicatorColor = indicator;
            state.shape.indicatorGradientColor = state.view.graph.getIndicatorGradientColor(state);
        }
        else {
            var indicator = state.view.graph.getIndicatorImage(state);
            if (indicator != null) {
                state.shape.indicatorImage = indicator;
            }
        }
        this.postConfigureShape(state);
    };
    mxCellRenderer.prototype.postConfigureShape = function(state){
        if (state.shape != null) {
            this.resolveColor(state, 'indicatorColor', mxConstants.STYLE_FILLCOLOR);
            this.resolveColor(state, 'indicatorGradientColor', mxConstants.STYLE_GRADIENTCOLOR);
            this.resolveColor(state, 'fill', mxConstants.STYLE_FILLCOLOR);
            this.resolveColor(state, 'stroke', mxConstants.STYLE_STROKECOLOR);
            this.resolveColor(state, 'gradient', mxConstants.STYLE_GRADIENTCOLOR);
        }
    };
    mxCellRenderer.prototype.resolveColor = function(state, field, key){
        var value = state.shape[field];
        var graph = state.view.graph;
        var referenced = null;
        if (value == 'inherit') {
            referenced = graph.model.getParent(state.cell);
        }
        else 
            if (value == 'swimlane') {
                if (graph.model.getTerminal(state.cell, false) != null) {
                    referenced = graph.model.getTerminal(state.cell, false);
                }
                else {
                    referenced = state.cell;
                }
                referenced = graph.getSwimlane(referenced);
                key = graph.swimlaneIndicatorColorAttribute;
            }
            else 
                if (value == 'indicated') {
                    state.shape[field] = state.shape.indicatorColor;
                }
        if (referenced != null) {
            var rstate = graph.getView().getState(referenced);
            state.shape[field] = null;
            if (rstate != null) {
                if (rstate.shape != null && field != 'indicatorColor') {
                    state.shape[field] = rstate.shape[field];
                }
                else {
                    state.shape[field] = rstate.style[key];
                }
            }
        }
    };
    mxCellRenderer.prototype.getLabelValue = function(state){
        var graph = state.view.graph;
        var value = graph.getLabel(state.cell);
        if (!graph.isHtmlLabel(state.cell) && (value != null && !mxUtils.isNode(value)) && false) {
            value = mxUtils.htmlEntities(value, false);
        }
        return value;
    };
    mxCellRenderer.prototype.isLabelEvent = function(state, evt){
        return true;
    };
    mxCellRenderer.prototype.createLabel = function(state){
        var self = this;
        var graph = state.view.graph;
        var isEdge = graph.getModel().isEdge(state.cell);
        if (state.style[mxConstants.STYLE_FONTSIZE] > 0 || state.style[mxConstants.STYLE_FONTSIZE] == null) {
            var value = this.getLabelValue(state);
            if (value == null || value.length == 0) {
                return;
            }
            var isForceHtml = (graph.isHtmlLabel(state.cell) || (value != null && mxUtils.isNode(value))) && graph.dialect == mxConstants.DIALECT_SVG;
            var isRotate = state.style[mxConstants.STYLE_HORIZONTAL] == false;
            state.text = new mxText(value, new mxRectangle(), state.style[mxConstants.STYLE_ALIGN], graph.getVerticalAlign(state), state.style[mxConstants.STYLE_FONTCOLOR], state.style[mxConstants.STYLE_FONTFAMILY], state.style[mxConstants.STYLE_FONTSIZE], state.style[mxConstants.STYLE_FONTSTYLE], state.style[mxConstants.STYLE_SPACING], state.style[mxConstants.STYLE_SPACING_TOP], state.style[mxConstants.STYLE_SPACING_RIGHT], state.style[mxConstants.STYLE_SPACING_BOTTOM], state.style[mxConstants.STYLE_SPACING_LEFT], isRotate, state.style[mxConstants.STYLE_LABEL_BACKGROUNDCOLOR], state.style[mxConstants.STYLE_LABEL_BORDERCOLOR], isEdge, isEdge || isForceHtml, graph.isWrapping(state.cell), graph.isLabelClipped(state.cell));
            state.text.opacity = state.style[mxConstants.STYLE_TEXT_OPACITY];
            state.text.dialect = (isForceHtml) ? mxConstants.DIALECT_STRICTHTML : state.view.graph.dialect;
            this.initializeLabel(state);
            var cursor = graph.getCursorForCell(state.cell);
            if (cursor != null || (graph.isEnabled() && graph.isCellMovable(state.cell))) {
                state.text.node.style.cursor = cursor || 'move';
            }
            mxEvent.addListener(state.text.node, 'mousedown', function(evt){
                if (self.isLabelEvent(state, evt)) {
                    var handle = null;
                    
                    if (graph.getModel().isEdge(state.cell) && graph.isCellSelected(state.cell)) {
                        handle = mxEvent.LABEL_HANDLE;
                    }
                    graph.fireMouseEvent(mxEvent.MOUSE_DOWN, new mxMouseEvent(evt, state, handle));
                }
            });
            mxEvent.addListener(state.text.node, 'mousemove', function(evt){
                if (self.isLabelEvent(state, evt)) {
                    graph.fireMouseEvent(mxEvent.MOUSE_MOVE, new mxMouseEvent(evt, state));
                }
            });
            mxEvent.addListener(state.text.node, 'mouseup', function(evt){
                if (self.isLabelEvent(state, evt)) {
                    graph.fireMouseEvent(mxEvent.MOUSE_UP, new mxMouseEvent(evt, state));
                }
            });
            mxEvent.addListener(state.text.node, 'dblclick', function(evt){
                if (self.isLabelEvent(state, evt)) {
                    graph.dblClick(evt, state.cell);
                    mxEvent.consume(evt);
                }
            });
        }
    };
    mxCellRenderer.prototype.initializeLabel = function(state){
        var graph = state.view.graph;
        if (state.text.dialect != mxConstants.DIALECT_SVG) {
            if (graph.dialect == mxConstants.DIALECT_SVG) {
            
            
            
                var node = graph.container;
                var overflow = node.style.overflow;
                state.text.isAbsolute = true;
                state.text.init(node);
                node.style.overflow = overflow;
                return;
            }
            else 
                if (mxUtils.isVml(state.view.getDrawPane())) {
                    if (state.shape.label != null) {
                        state.text.init(state.shape.label);
                    }
                    else {
                        state.text.init(state.shape.node);
                    }
                    return;
                }
        }
        state.text.init(state.view.getDrawPane());
        state.text.isAbsolute = true;
        if (state.shape != null && state.text != null) {
            state.shape.node.parentNode.insertBefore(state.text.node, state.shape.node.nextSibling);
        }
    };
    mxCellRenderer.prototype.createCellOverlays = function(state){
        var graph = state.view.graph;
        var overlays = graph.getCellOverlays(state.cell);
        if (overlays != null) {
            state.overlays = new Array();
            for (var i = 0; i < overlays.length; i++) {
                var tmp = new mxImageShape(new mxRectangle(), overlays[i].image.src);
                tmp.dialect = state.view.graph.dialect;
                tmp.init(state.view.getOverlayPane());
                tmp.node.style.cursor = 'help';
                this.installCellOverlayListeners(state, overlays[i], tmp);
                state.overlays.push(tmp);
            }
        }
    };
    mxCellRenderer.prototype.installCellOverlayListeners = function(state, overlay, shape){
        var graph = state.view.graph;
        mxEvent.addListener(shape.node, 'click', function(evt){
            overlay.fireEvent(mxEvent.CLICK, new mxEventObject([evt, state.cell]));
        });
        mxEvent.addListener(shape.node, 'mousedown', function(evt){
            mxEvent.consume(evt);
        });
        mxEvent.addListener(shape.node, 'mousemove', function(evt){
            graph.fireMouseEvent(mxEvent.MOUSE_MOVE, new mxMouseEvent(evt, state, overlay, overlay.tooltip));
        });
    };
	//创建 组件
    mxCellRenderer.prototype.createControl = function(state){
        var graph = state.view.graph;
        var image = graph.getFoldingImage(state);
        if (graph.foldingEnabled && image != null) {
            if (state.control == null) {
                var b = new mxRectangle(0, 0, image.width, image.height);
                state.control = new mxImageShape(b, image.src);
                state.control.dialect = state.view.graph.dialect;
                var isForceHtml = (graph.isHtmlLabel(state.cell) && state.view.graph.dialect == mxConstants.DIALECT_SVG) || false || false;
                if (isForceHtml) {
                    state.control.dialect = mxConstants.DIALECT_PREFERHTML;
                    state.control.init(graph.container);
                    state.control.node.style.zIndex = 1;
                }
                else {
                    state.control.init(state.view.getOverlayPane());
                }
                var node = state.control.innerNode || state.control.node;
                var tip = this.collapseExpandResource;
                tip = mxResources.get(tip) || tip;
                if (graph.isEnabled()) {
                    node.style.cursor = 'pointer';
                }
                mxEvent.addListener(node, 'click', function(evt){
                    if (graph.isEnabled()) {
                        var collapse = !graph.isCellCollapsed(state.cell);
                        graph.foldCells(collapse, false, [state.cell]);
                        mxEvent.consume(evt);
                    }
                });
                mxEvent.addListener(node, 'mousedown', function(evt){
                    graph.fireMouseEvent(mxEvent.MOUSE_DOWN, new mxMouseEvent(evt, state));
                    mxEvent.consume(evt);
                });
                mxEvent.addListener(node, 'mousemove', function(evt){
                    graph.fireMouseEvent(mxEvent.MOUSE_MOVE, new mxMouseEvent(evt, state, null, tip));
                });
            }
        }
        else 
            if (state.control != null) {
                state.control.destroy();
                state.control = null;
            }
    };
    mxCellRenderer.prototype.isShapeEvent = function(state, evt){
        return true;
    };
    mxCellRenderer.prototype.installListeners = function(state){
        var self = this;
        var graph = state.view.graph;
        if (graph.dialect == mxConstants.DIALECT_SVG) {
            var events = 'all';
            if (graph.getModel().isEdge(state.cell) && state.shape.stroke != null && state.shape.fill == null) {
                events = 'visibleStroke';
            }
            if (state.shape.innerNode != null) {
                state.shape.innerNode.setAttribute('pointer-events', events);
            }
            else {
                state.shape.node.setAttribute('pointer-events', events);
            }
        }
        var cursor = graph.getCursorForCell(state.cell);
        if (cursor != null || graph.isEnabled()) {
            if (cursor == null) {
                if (graph.getModel().isEdge(state.cell)) {
                    cursor = 'pointer';
                }
                else 
                    if (graph.isCellMovable(state.cell)) {
                        cursor = 'move';
                    }
            }
            if (state.shape.innerNode != null && !graph.getModel().isEdge(state.cell)) {
                state.shape.innerNode.style.cursor = cursor;
            }
            else {
                state.shape.node.style.cursor = cursor;
            }
        }
        mxEvent.addListener(state.shape.node, 'mousedown', function(evt){
            if (self.isShapeEvent(state, evt)) {
                graph.fireMouseEvent(mxEvent.MOUSE_DOWN, new mxMouseEvent(evt, (state.shape != null && mxEvent.getSource(evt) == state.shape.content) ? null : state));
            }
        });
        mxEvent.addListener(state.shape.node, 'mousemove', function(evt){
            if (self.isShapeEvent(state, evt)) {
                graph.fireMouseEvent(mxEvent.MOUSE_MOVE, new mxMouseEvent(evt, (state.shape != null && mxEvent.getSource(evt) == state.shape.content) ? null : state));
            }
        });
        mxEvent.addListener(state.shape.node, 'mouseup', function(evt){
            if (self.isShapeEvent(state, evt)) {
                graph.fireMouseEvent(mxEvent.MOUSE_UP, new mxMouseEvent(evt, (state.shape != null && mxEvent.getSource(evt) == state.shape.content) ? null : state));
            }
        });
        mxEvent.addListener(state.shape.node, 'dblclick', function(evt){
            if (self.isShapeEvent(state, evt)) {
                graph.dblClick(evt, (state.shape != null && mxEvent.getSource(evt) == state.shape.content) ? null : state.cell);
                mxEvent.consume(evt);
            }
        });
    };
    mxCellRenderer.prototype.redrawLabel = function(state){
        var value = this.getLabelValue(state);
        if (state.text == null && value != null && value.length > 0) {
            this.createLabel(state);
        }
        else 
            if (state.text != null && (value == null || value.length == 0)) {
                state.text.destroy();
                state.text = null;
            }
        if (state.text != null) {
            var graph = state.view.graph;
            var wrapping = graph.isWrapping(state.cell);
            var clipping = graph.isLabelClipped(state.cell);
            var bounds = this.getLabelBounds(state);
            if (state.text.value != value || state.text.isWrapping != wrapping || state.text.isClipping != clipping || state.text.scale != state.view.scale || !state.text.bounds.equals(bounds)) {
                state.text.value = value;
                state.text.bounds = bounds;
                state.text.scale = state.view.scale;
                state.text.isWrapping = wrapping;
                state.text.isClipping = clipping;
                state.text.redraw();
            }
        }
    };
    mxCellRenderer.prototype.getLabelBounds = function(state){
        var graph = state.view.graph;
        var isEdge = graph.getModel().isEdge(state.cell);
        var bounds = new mxRectangle(state.absoluteOffset.x, state.absoluteOffset.y);
        if (!isEdge) {
            bounds.x += state.shape.bounds.x;
            bounds.y += state.shape.bounds.y;
            bounds.width = Math.max(1, state.shape.bounds.width);
            bounds.height = Math.max(1, state.shape.bounds.height);
            var isRotate = state.style[mxConstants.STYLE_HORIZONTAL] == false;
            if (graph.isSwimlane(state.cell)) {
                var scale = graph.view.scale;
                var height = (parseInt(state.style[mxConstants.STYLE_STARTSIZE]) || 0) * scale;
                if (isRotate) {
                    bounds.width = height;
                }
                else {
                    bounds.height = height;
                }
            }
        }
        return bounds;
    };
    mxCellRenderer.prototype.redrawCellOverlays = function(state){
        var overlays = state.view.graph.getCellOverlays(state.cell);
        var oldCount = (state.overlays != null) ? state.overlays.length : 0;
        var newCount = (overlays != null) ? overlays.length : 0;
        if (oldCount != newCount) {
            if (oldCount > 0) {
                for (var i = 0; i < state.overlays.length; i++) {
                    state.overlays[i].destroy();
                }
                state.overlays = null;
            }
            if (newCount > 0) {
                this.createCellOverlays(state);
            }
        }
        if (state.overlays != null) {
            for (var i = 0; i < overlays.length; i++) {
                var bounds = overlays[i].getBounds(state);
                if (state.overlays[i].bounds == null || state.overlays[i].scale != state.view.scale || !state.overlays[i].bounds.equals(bounds)) {
                    state.overlays[i].bounds = bounds;
                    state.overlays[i].scale = state.view.scale;
                    state.overlays[i].redraw();
                }
            }
        }
    };
    mxCellRenderer.prototype.redrawControl = function(state){
        if (state.control != null) {
            var bounds = this.getControlBounds(state);
            var s = state.view.scale;
            if (state.control.scale != s || !state.control.bounds.equals(bounds)) {
                state.control.bounds = bounds;
                state.control.scale = s;
                state.control.redraw();
            }
        }
    };
    mxCellRenderer.prototype.getControlBounds = function(state){
        if (state.control != null) {
            var oldScale = state.control.scale;
            var w = state.control.bounds.width / oldScale;
            var h = state.control.bounds.height / oldScale;
            var s = state.view.scale;
            return (state.view.graph.getModel().isEdge(state.cell)) ? new mxRectangle(state.x + state.width / 2 - w / 2 * s, state.y + state.height / 2 - h / 2 * s, w * s, h * s) : new mxRectangle(state.x + w / 2 * s, state.y + h / 2 * s, w * s, h * s);
        }
        return null;
    };
    mxCellRenderer.prototype.redraw = function(state){
        if (state.shape != null) {
            var model = state.view.graph.getModel();
            var isEdge = model.isEdge(state.cell);
            var reconfigure = false;
            this.createControl(state);
            if (state.shape.bounds == null || state.shape.scale != state.view.scale || !state.shape.bounds.equals(state) || !mxUtils.equalPoints(state.shape.points, state.absolutePoints)) {
            
            
            
            
            
                if (state.absolutePoints != null) {
                    state.shape.points = state.absolutePoints.slice();
                }
                else {
                    state.shape.points = null;
                }
                state.shape.bounds = new mxRectangle(state.x, state.y, state.width, state.height);
                state.shape.scale = state.view.scale;
                state.shape.redraw();
            }
            this.redrawLabel(state);
            this.redrawCellOverlays(state);
            this.redrawControl(state);
            if (state.orderChanged && state.view.graph.ordered) {
                this.order(state);
                reconfigure = true;
            }
            delete state.orderChanged;
            
            if (!mxUtils.equalEntries(state.shape.style, state.style)) {
                state.shape.apply(state);
                reconfigure = true;
            }
            if (reconfigure) {
                this.configureShape(state);
                state.shape.reconfigure();
            }
        }
        if (state.doCreateHandler) {
            delete state.doCreateHandler;
            state.view.graph.createHandler(state);
        }
        if (state.view.graph.hasHandler(state)) {
            state.view.graph.redrawHandler(state);
        }
    };
    mxCellRenderer.prototype.destroy = function(state){
        if (state.shape != null) {
            if (state.text != null) {
                state.text.destroy();
                state.text = null;
            }
            if (state.overlays != null) {
                for (var i = 0; i < state.overlays.length; i++) {
                    state.overlays[i].destroy();
                }
                state.overlays = null;
            }
            if (state.control != null) {
                state.control.destroy();
                state.control = null;
            }
            state.shape.destroy();
            state.shape = null;
        }
    };
}
//mxEdgeStyle
var mxEdgeStyle = {
    EntityRelation: function(state, source, target, points, result){
        var view = state.view;
        var graph = view.graph;
        var segment = mxUtils.getValue(state.style, mxConstants.STYLE_STARTSIZE, mxConstants.ENTITY_SEGMENT) * state.view.scale;
        var isSourceLeft = false;
        if (source != null) {
            var sourceGeometry = graph.getCellGeometry(source.cell);
            if (sourceGeometry.relative) {
                isSourceLeft = sourceGeometry.x <= 0.5;
            }
            else 
                if (target != null) {
                    isSourceLeft = target.x + target.width < source.x;
                }
        }
        else {
            var tmp = state.absolutePoints[0];
            if (tmp == null) {
                return;
            }
            source = new mxCellState();
            source.x = tmp.x;
            source.y = tmp.y;
        }
        var isTargetLeft = true;
        if (target != null) {
            var targetGeometry = graph.getCellGeometry(target.cell);
            if (targetGeometry.relative) {
                isTargetLeft = targetGeometry.x <= 0.5;
            }
            else 
                if (source != null) {
                    isTargetLeft = source.x + source.width < target.x;
                }
        }
        else {
            var pts = state.absolutePoints;
            var tmp = pts[pts.length - 1];
            if (tmp == null) {
                return;
            }
            target = new mxCellState();
            target.x = tmp.x;
            target.y = tmp.y;
        }
        var x0 = (isSourceLeft) ? source.x : source.x + source.width;
        var y0 = view.getRoutingCenterY(source);
        var xe = (isTargetLeft) ? target.x : target.x + target.width;
        var ye = view.getRoutingCenterY(target);
        var seg = segment;
        var dx = (isSourceLeft) ? -seg : seg;
        var dep = new mxPoint(x0 + dx, y0);
        dx = (isTargetLeft) ? -seg : seg;
        var arr = new mxPoint(xe + dx, ye);
        if (isSourceLeft == isTargetLeft) {
            var x = (isSourceLeft) ? Math.min(x0, xe) - segment : Math.max(x0, xe) + segment;
            result.push(new mxPoint(x, y0));
            result.push(new mxPoint(x, ye));
        }
        else 
            if ((dep.x < arr.x) == isSourceLeft) {
                var midY = y0 + (ye - y0) / 2;
                result.push(dep);
                result.push(new mxPoint(dep.x, midY));
                result.push(new mxPoint(arr.x, midY));
                result.push(arr);
            }
            else {
                result.push(dep);
                result.push(arr);
            }
    },
    Loop: function(state, source, target, points, result){
        var view = state.view;
        var graph = view.graph;
        var pt = (points != null && points.length > 0) ? points[0] : null;
        var s = view.scale;
        if (pt != null) {
            pt = view.transformControlPoint(state, pt);
            if (mxUtils.contains(source, pt.x, pt.y)) {
                pt = null;
            }
        }
        var x = 0;
        var dx = 0;
        var y = view.getRoutingCenterY(source);
        var dy = s * graph.gridSize;
        if (pt == null || pt.x < source.x || pt.x > source.x + source.width) {
            if (pt != null) {
                x = pt.x;
                dy = Math.max(Math.abs(y - pt.y), dy);
            }
            else {
                x = source.x + source.width + 2 * dy;
            }
        }
        else 
            if (pt != null) {
                x = view.getRoutingCenterX(source);
                dx = Math.max(Math.abs(x - pt.x), dy);
                y = pt.y;
                dy = 0;
            }
        result.push(new mxPoint(x - dx, y - dy));
        result.push(new mxPoint(x + dx, y + dy));
    },
    ElbowConnector: function(state, source, target, points, result){
        var pt = (points != null && points.length > 0) ? points[0] : null;
        var vertical = false;
        var horizontal = false;
        if (source != null && target != null) {
            if (pt != null) {
                var left = Math.min(source.x, target.x);
                var right = Math.max(source.x + source.width, target.x + target.width);
                var top = Math.min(source.y, target.y);
                var bottom = Math.max(source.y + source.height, target.y + target.height);
                var view = state.view;
                pt = view.transformControlPoint(state, pt);
                vertical = pt.y < top || pt.y > bottom;
                horizontal = pt.x < left || pt.x > right;
            }
            else {
                var left = Math.max(source.x, target.x);
                var right = Math.min(source.x + source.width, target.x + target.width);
                vertical = left == right;
                if (!vertical) {
                    var top = Math.max(source.y, target.y);
                    var bottom = Math.min(source.y + source.height, target.y + target.height);
                    horizontal = top == bottom;
                }
            }
        }
        if (!horizontal && (vertical || state.style[mxConstants.STYLE_ELBOW] == mxConstants.ELBOW_VERTICAL)) {
            mxEdgeStyle.TopToBottom(state, source, target, points, result);
        }
        else {
            mxEdgeStyle.SideToSide(state, source, target, points, result);
        }
    },
    SideToSide: function(state, source, target, points, result){
        var view = state.view;
        var pt = (points != null && points.length > 0) ? points[0] : null;
        if (pt != null) {
            pt = view.transformControlPoint(state, pt);
        }
        if (source == null) {
            var tmp = state.absolutePoints[0];
            if (tmp == null) {
                return;
            }
            source = new mxCellState();
            source.x = tmp.x;
            source.y = tmp.y;
        }
        if (target == null) {
            var pts = state.absolutePoints;
            var tmp = pts[pts.length - 1];
            if (tmp == null) {
                return;
            }
            target = new mxCellState();
            target.x = tmp.x;
            target.y = tmp.y;
        }
        var l = Math.max(source.x, target.x);
        var r = Math.min(source.x + source.width, target.x + target.width);
        var x = (pt != null) ? pt.x : r + (l - r) / 2;
        var y1 = view.getRoutingCenterY(source);
        var y2 = view.getRoutingCenterY(target);
        if (pt != null) {
            if (pt.y >= source.y && pt.y <= source.y + source.height) {
                y1 = pt.y;
            }
            if (pt.y >= target.y && pt.y <= target.y + target.height) {
                y2 = pt.y;
            }
        }
        if (!mxUtils.contains(target, x, y1) && !mxUtils.contains(source, x, y1)) {
            result.push(new mxPoint(x, y1));
        }
        if (!mxUtils.contains(target, x, y2) && !mxUtils.contains(source, x, y2)) {
            result.push(new mxPoint(x, y2));
        }
        if (result.length == 1) {
            if (pt != null) {
                if (!mxUtils.contains(target, x, pt.y) && !mxUtils.contains(source, x, pt.y)) {
                    result.push(new mxPoint(x, pt.y));
                }
            }
            else {
                var t = Math.max(source.y, target.y);
                var b = Math.min(source.y + source.height, target.y + target.height);
                result.push(new mxPoint(x, t + (b - t) / 2));
            }
        }
    },
    TopToBottom: function(state, source, target, points, result){
        var view = state.view;
        var pt = (points != null && points.length > 0) ? points[0] : null;
        if (pt != null) {
            pt = view.transformControlPoint(state, pt);
        }
        if (source == null) {
            var tmp = state.absolutePoints[0];
            if (tmp == null) {
                return;
            }
            source = new mxCellState();
            source.x = tmp.x;
            source.y = tmp.y;
        }
        if (target == null) {
            var pts = state.absolutePoints;
            var tmp = pts[pts.length - 1];
            if (tmp == null) {
                return;
            }
            target = new mxCellState();
            target.x = tmp.x;
            target.y = tmp.y;
        }
        var t = Math.max(source.y, target.y);
        var b = Math.min(source.y + source.height, target.y + target.height);
        var x = view.getRoutingCenterX(source);
        if (pt != null && pt.x >= source.x && pt.x <= source.x + source.width) {
            x = pt.x;
        }
        var y = (pt != null) ? pt.y : b + (t - b) / 2;
        if (!mxUtils.contains(target, x, y) && !mxUtils.contains(source, x, y)) {
            result.push(new mxPoint(x, y));
        }
        if (pt != null && pt.x >= target.x && pt.x <= target.x + target.width) {
            x = pt.x;
        }
        else {
            x = view.getRoutingCenterX(target);
        }
        if (!mxUtils.contains(target, x, y) && !mxUtils.contains(source, x, y)) {
            result.push(new mxPoint(x, y));
        }
        if (result.length == 1) {
            if (pt != null && result.length == 1) {
                if (!mxUtils.contains(target, pt.x, y) && !mxUtils.contains(source, pt.x, y)) {
                    result.push(new mxPoint(pt.x, y));
                }
            }
            else {
                var l = Math.max(source.x, target.x);
                var r = Math.min(source.x + source.width, target.x + target.width);
                result.push(new mxPoint(l + (r - l) / 2, y));
            }
        }
    }
};
//mxStyleRegistry
var mxStyleRegistry = {
    values: new Array(),
    putValue: function(name, obj){
        mxStyleRegistry.values[name] = obj;
    },
    getValue: function(name){
        return mxStyleRegistry.values[name];
    },
    getName: function(value){
        for (var key in mxStyleRegistry.values) {
            if (mxStyleRegistry.values[key] == value) {
                return key;
            }
        }
        return null;
    }
};
mxStyleRegistry.putValue(mxConstants.EDGESTYLE_ELBOW, mxEdgeStyle.ElbowConnector);
mxStyleRegistry.putValue(mxConstants.EDGESTYLE_ENTITY_RELATION, mxEdgeStyle.EntityRelation);
mxStyleRegistry.putValue(mxConstants.EDGESTYLE_LOOP, mxEdgeStyle.Loop);
mxStyleRegistry.putValue(mxConstants.EDGESTYLE_SIDETOSIDE, mxEdgeStyle.SideToSide);
mxStyleRegistry.putValue(mxConstants.EDGESTYLE_TOPTOBOTTOM, mxEdgeStyle.TopToBottom);
mxStyleRegistry.putValue(mxConstants.PERIMETER_ELLIPSE, mxPerimeter.EllipsePerimeter);
mxStyleRegistry.putValue(mxConstants.PERIMETER_RECTANGLE, mxPerimeter.RectanglePerimeter);
mxStyleRegistry.putValue(mxConstants.PERIMETER_RHOMBUS, mxPerimeter.RhombusPerimeter);
mxStyleRegistry.putValue(mxConstants.PERIMETER_TRIANGLE, mxPerimeter.TrianglePerimeter);
//mxDefaultPopupMenu
{
    function mxDefaultPopupMenu(config){
        this.config = config;
    };
    mxDefaultPopupMenu.prototype.imageBasePath = null;
    mxDefaultPopupMenu.prototype.config = null;
    mxDefaultPopupMenu.prototype.createMenu = function(editor, menu, cell, evt){
        if (this.config != null) {
            var conditions = this.createConditions(editor, cell, evt);
            var item = this.config.firstChild;
            this.addItems(editor, menu, cell, evt, conditions, item, null);
        }
    };
    mxDefaultPopupMenu.prototype.addItems = function(editor, menu, cell, evt, conditions, item, parent){
        var addSeparator = false;
        while (item != null) {
            if (item.nodeName == 'add') {
                var condition = item.getAttribute('if');
                if (condition == null || conditions[condition]) {
                    var as = item.getAttribute('as');
                    as = mxResources.get(as) || as;
                    var funct = mxUtils.eval(mxUtils.getTextContent(item));
                    var action = item.getAttribute('action');
                    var icon = item.getAttribute('icon');
                    if (addSeparator) {
                        menu.addSeparator(parent);
                        addSeparator = false;
                    }
                    if (icon != null && this.imageBasePath) {
                        icon = this.imageBasePath + icon;
                    }
                    var row = this.addAction(menu, editor, as, icon, funct, action, cell, parent);
                    this.addItems(editor, menu, cell, evt, conditions, item.firstChild, row);
                }
            }
            else 
                if (item.nodeName == 'separator') {
                    addSeparator = true;
                }
            item = item.nextSibling;
        }
    };
    mxDefaultPopupMenu.prototype.addAction = function(menu, editor, lab, icon, funct, action, cell, parent){
        var clickHandler = function(){
            if (typeof(funct) == 'function') {
                funct.call(editor, editor, cell);
            }
            if (action != null) {
                editor.execute(action, cell);
            }
        };
        return menu.addItem(lab, icon, clickHandler, parent);
    };
    mxDefaultPopupMenu.prototype.createConditions = function(editor, cell, evt){
        var model = editor.graph.getModel();
        var childCount = model.getChildCount(cell);
        var conditions = new Array();
        conditions['nocell'] = cell == null;
        conditions['ncells'] = editor.graph.getSelectionCount() > 1;
        conditions['notRoot'] = model.getRoot() !=
        model.getParent(editor.graph.getDefaultParent());
        conditions['cell'] = cell != null;
        var isCell = cell != null && editor.graph.getSelectionCount() == 1;
        conditions['nonEmpty'] = isCell && childCount > 0;
        conditions['expandable'] = isCell && editor.graph.isCellFoldable(cell, false);
        conditions['collapsable'] = isCell && editor.graph.isCellFoldable(cell, true);
        conditions['validRoot'] = isCell && editor.graph.isValidRoot(cell);
        conditions['emptyValidRoot'] = conditions['validRoot'] && childCount == 0;
        conditions['swimlane'] = isCell && editor.graph.isSwimlane(cell);
        var condNodes = this.config.getElementsByTagName('condition');
        for (var i = 0; i < condNodes.length; i++) {
            var funct = mxUtils.eval(mxUtils.getTextContent(condNodes[i]));
            var name = condNodes[i].getAttribute('name');
            if (name != null && typeof(funct) == 'function') {
                conditions[name] = funct(editor, cell, evt);
            }
        }
        return conditions;
    };
}
//mxDefaultToolbar
{
    function mxDefaultToolbar(container, editor){
        this.editor = editor;
        if (container != null && editor != null) {
            this.init(container);
        }
    };
    mxDefaultToolbar.prototype.editor = null;
    mxDefaultToolbar.prototype.toolbar = null;
    mxDefaultToolbar.prototype.resetHandler = null;
    mxDefaultToolbar.prototype.spacing = 4;
    mxDefaultToolbar.prototype.connectOnDrop = false;
    mxDefaultToolbar.prototype.init = function(container){
        if (container != null) {
            this.toolbar = new mxToolbar(container);
            
            var self = this;
            this.toolbar.addListener(mxEvent.SELECT, function(sender, evt){
                var funct = evt.getArgAt(0);
                if (funct != null) {
                    self.editor.insertFunction = function(){
                        funct.apply(self, arguments);
                        self.toolbar.resetMode();
                    };
                }
                else {
                    self.editor.insertFunction = null;
                }
            });
            this.resetHandler = function(){
                if (self.toolbar != null) {
                    self.toolbar.resetMode(true);
                }
            };
            this.editor.graph.addListener(mxEvent.DOUBLE_CLICK, this.resetHandler);
            this.editor.addListener(mxEvent.ESCAPE, this.resetHandler);
        }
    };
    mxDefaultToolbar.prototype.addItem = function(title, icon, action, pressed){
        var self = this;
        var clickHandler = function(){
            self.editor.execute(action);
        };
        return this.toolbar.addItem(title, icon, clickHandler, pressed);
    };
    mxDefaultToolbar.prototype.addSeparator = function(icon){
        icon = icon || mxClient.imageBasePath + 'separator.gif';
        this.toolbar.addSeparator(icon);
    };
    mxDefaultToolbar.prototype.addCombo = function(){
        return this.toolbar.addCombo();
    };
    mxDefaultToolbar.prototype.addActionCombo = function(title){
        return this.toolbar.addActionCombo(title);
    };
    mxDefaultToolbar.prototype.addActionOption = function(combo, title, action){
        var self = this;
        var clickHandler = function(){
            self.editor.execute(action);
        };
        this.addOption(combo, title, clickHandler);
    };
    mxDefaultToolbar.prototype.addOption = function(combo, title, value){
        return this.toolbar.addOption(combo, title, value);
    };
    mxDefaultToolbar.prototype.addMode = function(title, icon, mode, pressed, funct){
        var self = this;
        var clickHandler = function(){
            self.editor.setMode(mode);
            if (funct != null) {
                funct(self.editor);
            }
        };
        return this.toolbar.addSwitchMode(title, icon, clickHandler, pressed);
    };
    mxDefaultToolbar.prototype.addPrototype = function(title, icon, ptype, pressed, insert){
        var img = null;
        if (ptype == null) {
            img = this.toolbar.addMode(title, icon, null, pressed);
        }
        else {
        
            var factory = function(){
                if (typeof(ptype) == 'function') {
                    return ptype();
                }
                else {
                    return ptype.clone();
                }
            };
            
            var self = this;
            var clickHandler = function(evt, cell){
                if (typeof(insert) == 'function') {
                    insert(self.editor, factory(), evt, cell);
                }
                else {
                    self.drop(factory(), evt, cell);
                }
                self.toolbar.resetMode();
                mxEvent.consume(evt);
            };
            img = this.toolbar.addMode(title, icon, clickHandler, pressed);
            
            var dropHandler = function(graph, evt, cell){
                clickHandler(evt, cell);
            };
            this.installDropHandler(img, dropHandler);
        }
        return img;
    };
    mxDefaultToolbar.prototype.drop = function(vertex, evt, target){
        var graph = this.editor.graph;
        var model = graph.getModel();
        if (target == null || model.isEdge(target) || !this.connectOnDrop || !graph.isCellConnectable(target)) {
            while (target != null && !graph.isValidDropTarget(target, [vertex], evt)) {
                target = model.getParent(target);
            }
            this.insert(vertex, evt, target);
        }
        else {
            this.connect(vertex, evt, target);
        }
    };
    mxDefaultToolbar.prototype.insert = function(vertex, evt, target){
        var graph = this.editor.graph;
        if (graph.canImportCell(vertex)) {
            var pt = mxUtils.convertPoint(graph.container, evt.clientX, evt.clientY);
            if (graph.isSplitEnabled() && graph.isSplitTarget(target, [vertex], evt)) {
                return graph.splitEdge(target, [vertex], null, pt.x, pt.y);
            }
            else {
                return this.editor.addVertex(target, vertex, pt.x, pt.y);
            }
        }
        return null;
    };
    mxDefaultToolbar.prototype.connect = function(vertex, evt, source){
        var graph = this.editor.graph;
        var model = graph.getModel();
        if (source != null && graph.isCellConnectable(vertex) && graph.isEdgeValid(null, source, vertex)) {
            var edge = null;
            model.beginUpdate();
            try {
                var geo = model.getGeometry(source);
                var g = model.getGeometry(vertex).clone();
                
                g.x = geo.x + (geo.width - g.width) / 2;
                g.y = geo.y + (geo.height - g.height) / 2;
                var step = this.spacing * graph.gridSize;
                var dist = model.getDirectedEdgeCount(source, true) * 20;
                if (this.editor.horizontalFlow) {
                    g.x += (g.width + geo.width) / 2 + step + dist;
                }
                else {
                    g.y += (g.height + geo.height) / 2 + step + dist;
                }
                vertex.setGeometry(g);
                
                var parent = model.getParent(source);
                graph.addCell(vertex, parent);
                graph.constrainChild(vertex);
                
                edge = this.editor.createEdge(source, vertex);
                if (model.getGeometry(edge) == null) {
                    var edgeGeometry = new mxGeometry();
                    edgeGeometry.relative = true;
                    model.setGeometry(edge, edgeGeometry);
                }
                graph.addEdge(edge, parent, source, vertex);
            }
            finally {
                model.endUpdate();
            }
            graph.setSelectionCells([vertex, edge]);
            graph.scrollCellToVisible(vertex);
        }
    };
    mxDefaultToolbar.prototype.installDropHandler = function(img, dropHandler){
        var sprite = document.createElement('img');
        sprite.setAttribute('src', img.getAttribute('src'));
        var self = this;
        var loader = function(evt){
            sprite.style.width = (2 * img.offsetWidth) + 'px';
            sprite.style.height = (2 * img.offsetHeight) + 'px';
            mxUtils.makeDraggable(img, self.editor.graph, dropHandler, sprite);
            mxEvent.removeListener(sprite, 'load', loader);
        };
        if (false) {
            loader();
        }
        else {
            mxEvent.addListener(sprite, 'load', loader);
        }
    };
    mxDefaultToolbar.prototype.destroy = function(){
        if (this.resetHandler != null) {
            this.editor.graph.removeListener('dblclick', this.resetHandler);
            this.editor.removeListener('escape', this.resetHandler);
            this.resetHandler = null;
        }
        if (this.toolbar != null) {
            this.toolbar.destroy();
            this.toolbar = null;
        }
    };
}
