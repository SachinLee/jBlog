require.config({
    baseUrl : basePath,
    waitSeconds : 200,
    paths : {
        "jquery" : "lib/jquery-3.1.1.min",

        "jqueryValidate" : "lib/jquery.validate.min",

        //"popper" : "https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min",
        //"popper.js" : "lib/js/popper",

        "bootstrap" : "lib/bootstrap/bootstrap.min",

        "sweet" : "lib/sweetAlert/sweetalert2.min",

        //editormd部分js
        "marked" : "lib/editormd/lib/marked.min",
        "prettify": "lib/editormd/lib/prettify.min",
        "raphael"         : "lib/editormd/lib/raphael.min",
        "underscore"      : "lib/editormd/lib/underscore.min",
        "flowchart"       : "lib/editormd/lib/flowchart.min",
        "jqueryflowchart" : "lib/editormd/lib/jquery.flowchart.min",
        "sequenceDiagram" : "lib/editormd/lib/sequence-diagram.min",
        "katex"           : "//cdnjs.cloudflare.com/ajax/libs/KaTeX/0.1.1/katex.min",
        "link-dialog" : "lib/editormd/plugins/link-dialog/link-dialog",
        "reference" : "lib/editormd/plugins/reference-link-dialog/reference-link-dialog",
        "image" : "lib/editormd/plugins/image-dialog/image-dialog",
        "code" : "lib/editormd/plugins/code-block-dialog/code-block-dialog",
        "table" : "lib/editormd/plugins/table-dialog/table-dialog",
        "emoji" : "lib/editormd/plugins/emoji-dialog/emoji-dialog",
        "goto" : "lib/editormd/plugins/goto-line-dialog/goto-line-dialog",
        "help" : "lib/editormd/plugins/help-dialog/help-dialog",
        "html" : "lib/editormd/plugins/html-entities-dialog/html-entities-dialog",
        "preformatted" : "lib/editormd/plugins/preformatted-text-dialog/preformatted-text-dialog",

        "editormd" : "lib/editormd/editormd.amd",

        "cropper" : "lib/cropper/cropper.min",

        "page" : "js/page"
    },
    shim : {
        'jqueryValidate' : {
            deps : ['jquery'],
            exports : 'jqueryValidate'
        },

        /*'popper' : {
            deps : ['jquery'],
            exports : 'popper'
        },*/

        'bootstrap' : {
            deps : ['jquery'],
            exports : 'bootstrap'
        },
        'sweet' :{
            deps:['jquery'],
            exports : 'sweet'
        },
        'editormd' : {
            deps:['jquery', 'marked','prettify','raphael','underscore','flowchart',
                'jqueryflowchart','sequenceDiagram','katex', 'editormd','link-dialog','reference','image','code',
                'table','emoji','goto','help','html','preformatted'],

            exports:'editormd'
        },
        'cropper' : {
            deps :['jquery'],
            exports : 'cropper'
        },
        'page': {
            deps : ['jquery'],
            exports : 'page'
        }
    }
});

require(["jquery", "jqueryValidate", 'bootstrap', 'sweet']);