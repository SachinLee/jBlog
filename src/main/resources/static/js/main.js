require.config({
    baseUrl : "/jblog/",
    paths : {
        "jquery" : "lib/jquery-3.1.1.min",

        "jqueryValidate" : "lib/jquery.validate.min",

        //"popper" : "https://cdn.bootcss.com/popper.js/1.12.5/umd/popper.min",
        //"popper.js" : "lib/js/popper",

        "bootstrap" : "lib/bootstrap/bootstrap.min",

        "sweet" : "lib/sweetAlert/sweetalert2.min",

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
        'page': {
            deps : ['jquery'],
            exports : 'page'
        }
    }
});

require(["jquery", "jqueryValidate", 'bootstrap', 'sweet']);