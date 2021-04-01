const path = require("path");

module.exports = {
  devServer: {
    port: process.env.VUE_APP_PORT_CLIENT || 3002,
  },
  outputDir: path.resolve(__dirname, "../src/main/resources/generated")
};