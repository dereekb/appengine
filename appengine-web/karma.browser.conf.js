// Karma configuration file used when running in the browser.
// https://karma-runner.github.io/1.0/config/configuration-file.html

let configure = require('./karma');

module.exports = function (config) {
  config.set({
    basePath: '',
    coverageIstanbulReporter: {
      dir: require('path').join(__dirname, '../coverage/appengine-web'),
      reports: ['html', 'lcovonly', 'text-summary'],
      fixWebpackSourcePaths: true
    },
    ...configure(config, {
      skipJUnit: true
    }),
    client: {
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
    autoWatch: true,
    singleRun: false
  });
};
