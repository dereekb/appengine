# AppengineServices

This library was generated with [Angular CLI](https://github.com/angular/angular-cli) version 7.2.0.

## Code scaffolding

Run `ng generate component component-name --project appengine-services` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module --project appengine-services`.
> Note: Don't forget to add `--project appengine-services` or else it will be added to the default project in your `angular.json` file. 

## Build

Run `ng build appengine-services` to build the project. The build artifacts will be stored in the `dist/` directory.

## Publishing

After building your library with `ng build appengine-services`, go to the dist folder `cd dist/appengine-services` and run `npm publish`.

## Running unit tests

Run `ng test appengine-services` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).


# Services Configuration

## Google index.html Configuration

```javascript
<!-- Google Sign In -->
<script>
    window.___gcfg = {
        parsetags: 'onload'
    };

    function googleOnLoadCallback() {}
</script>

<script src="https://apis.google.com/js/platform.js?onload=googleOnLoadCallback" async defer></script>
```


## Facebook index.html Configuration
```javascript
<!-- Facebook -->
<script>
    window.fbAsyncInit = function() {
        FB.init({
            appId: '1814919078756842',
            cookie: false,
            xfbml: false,
            version: 'v2.9'
        });
    };

    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {
            return;
        }
        js = d.createElement(s);
        js.id = id;
        js.src = "//connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>
```