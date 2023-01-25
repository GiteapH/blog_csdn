import { createApp } from 'vue';
import Antd from 'ant-design-vue';

import 'ant-design-vue/dist/antd.css';
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import tinymce from 'tinymce'
import App from './App'
import { router } from './route/index.js'
import article from './api/requests/articles'
import user from './api/requests/user'
import UMR from './api/requests/userModules'
import ColReq from './api/requests/collections'
const app = createApp(App)

app.config.globalProperties.$articles = article
app.config.globalProperties.$users = user
app.config.globalProperties.$UMs = UMR
app.config.globalProperties.$ColReq = ColReq

console.log(app.config.globalProperties)
app.use(router).use(Antd).use(tinymce).use(ElementPlus)
app.mount('#app')