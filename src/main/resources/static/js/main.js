require.config({
    paths : {
        "jquery" : "lib/jquery-3.1.1.min",

        "jqueryValidate" : "lib/jquery.validate.min",

        "popper" : "lib/popper.min",

        "bootstrap" : "lib/bootstrap/bootstrap.min"
    },
    shim : {
        'jqueryValidate' : {
            deps : ['jquery'],
            exports : 'jqueryValidate'
        },

        'popper' : {
            deps : ['jquery'],
            exports : 'popper'
        },

        'bootstrap' : {
            deps : ['jquery', 'css!lib/bootstrap/bootstrap.min'],
            exports : 'bootstrap'
        }
    }
});

require(["jquery", "jqueryValidate", "popper", "bootstrap"]);