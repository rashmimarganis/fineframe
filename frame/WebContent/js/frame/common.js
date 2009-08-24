Ext.namespace('Ext.ux.encoding');

Ext.ux.encoding = [
        ['GBK', 'GBK', 'GBK'],
        ['ISO8859-1', 'ISO8859-1', 'ISO8859-1'],
        ['UTF-8', 'UTF-8', 'UTF-8']
    ];

Ext.namespace('Ext.ux.JavaClassType');

Ext.ux.JavaClassType = [
                        ['Integer', 'Integer', 'Integer'],
                        ['String', 'String', 'String'],
                        ['Float', 'Float', 'Float'],
                         ['Double', 'Double', 'Double'],
                         ['Boolean', 'Boolean', 'Boolean']
    ];
Ext.namespace('Ext.ux.templateType');

Ext.ux.templateType = [
        ['control', '控件模板', '控件模板'],
        ['component', '组件模板', '组件模板']
    ];
Ext.ux.ComponentType = [
                       ['java', 'Java文件(.java)', 'Java文件'],
                       ['jsp', 'Jsp文件(.jsp)', 'Jsp文件'],
                       ['ftl', 'Ftl文件(.ftl)', 'Ftl文件'],
                       ['properties', 'Property文件(.properties)', 'Property文件'],
                       ['xml', '配置文件(.xml)', '配置文件'],
                        ['js', '脚本文件(.js)', '脚本文件']
                   ];
Ext.namespace('Ext.ux.DatabaseType');
Ext.ux.DatabaseType = [
                        ['mysql', 'MySQL', 'MySQL'],
                        ['sqlserver', 'SQLServer', 'SQLServer'],
                        ['oracle', 'Oracle', 'Oracle']
                    ];
Ext.namespace('Ext.ux.ComponentLevel');
Ext.ux.ComponentLevel = [
                        ['project', '项目', '项目'],
                        ['model', '模型', '模型']
                    ];
Ext.namespace('Ext.ux.Enabled');
Ext.ux.Enabled = [
                        ['true', '可用', '可用'],
                        ['false', '不可用', '不可用']
                    ];

function rendererEnabled(v){
	if(v){
		return '<img src="js/resources/images/default/menu/checked.gif">';
	}
	return '<img src="js/resources/images/default/menu/unchecked.gif">';
}