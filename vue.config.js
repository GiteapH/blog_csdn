const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,//关闭语法检查,
  configureWebpack: {
    resolve: {
      fallback: {
        "http": require.resolve("stream-http"),
        "crypto": require.resolve("crypto-browserify"),
        "fs": false
      }
    },
    externals :{
      express:"express"
    }
  }
})