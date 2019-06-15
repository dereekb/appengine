// Karma configuration file, see link for more information
// https://karma-runner.github.io/1.0/config/configuration-file.html

let configure = require('../../../karma');

module.exports = function (config) {
  config.set({
    basePath: '',
    coverageIstanbulReporter: {
      dir: require('path').join(__dirname, '../../../coverage/gae-web/appengine-token'),
      reports: ['html', 'lcovonly'],
      fixWebpackSourcePaths: true
    },
    junitReporter: {
      outputDir: '../../../tests',
      outputFile: 'appengine-token.xml'
    },
    ...configure(config)
  });
};
