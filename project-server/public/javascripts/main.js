"use strict";

requirejs.config({
    baseUrl: "assets",
    paths: {
        appDeps: ['client-jsdeps.min', 'client-jsdeps'],
        appMain: ['client-opt', 'client-fastOpt'],
        appLauncher: 'client-launcher',
        jquery: 'lib/jquery/jquery',
        bootstrap: 'lib/bootstrap/js/bootstrap',
        angular: 'lib/angularjs/angular'
    },
    shim: {
        jquery: {
            exports: '$'
        },
        bootstrap: {
            deps: ['jquery']
        },
        angular: {
            exports: 'angular'
        }
    }
});

require(["jquery", "bootstrap", "angular"], function ($, bootstrap, angular) {
    require(["appDeps", "appMain"], function() {
        require(["appLauncher"], function() {
            console.log("I'm OK!")
        })
    })
});