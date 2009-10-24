Ext.override(Ext.form.Checkbox, {
	initValue: function() {
		this.originalValue = this.checked;
	}
});

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
Ext.namespace('Ext.ux.WatermarkPositionType');

Ext.ux.WatermarkPositionType = [
                        ['lt', '左上', '左上'],
                        ['lb', '左下', '左下'],
                        ['rt', '右上', '右上'],
                        ['rb', '右下', '右下'],
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
Ext.namespace('Ext.ux.FunctionIsMenu');
Ext.ux.FunctionIsMenu = [
                        [true, '是', '是'],
                        [false, '否', '否']
                    ];
Ext.namespace('Ext.ux.FunctionIsLog');
Ext.ux.FunctionIsLog = [
                        [true, '是', '是'],
                        [false, '否', '否']
                    ];
Ext.namespace('Ext.ux.ModelRelationType');
Ext.ux.ModelRelationType = [
                  		['OneToMany', '一对多', '一对多'],
                  		['ManyToOne', '多对一', '多对一'],
                        ['OnetoOne', '一对一', '一对一'],
                        ['ManyToMany', '多对多', '多对多']
                    ];
Ext.namespace('Ext.ux.OrgType');
Ext.ux.OrgType = [
	['org', '组织','组织'],
	['dep', '部门','部门']
];
Ext.namespace('Ext.ux.PersonGender');
Ext.ux.PersonGender = [
                        ['m', '男', '男'],
                        ['f', '女', '女']
                    ];
Ext.namespace('Ext.ux.TemplateType');
Ext.ux.TemplateType = [
                       ['c', '普通模板', '普通模板'],
                       ['t', '标签模板', '标签模板']
                        ];
Ext.namespace('Ext.ux.CmsCategoryType');
Ext.ux.CmsCategoryType = [
                       ['op', '单页面栏目', '单页面'],
                       ['co', '内容栏目', '内容页面']
                        ];
function rendererEnabled(v){
	if(v){
		return '<img src="js/resources/images/default/menu/checked.gif">';
	}
	return '<img src="js/resources/images/default/menu/unchecked.gif">';
}