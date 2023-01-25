import { createRouter, createWebHistory } from "vue-router"

import articlelist from '../components/mainVue/articleList'
import uploadarticle from '../components/mainVue/uploadArticle'
import userArticle from '../components/mainVue/userArticle'
import regedit from '../components/mainVue/regedit'
import articleView from '../components/mainVue/articleView'
import curDayArticles from '../components/mainVue/curDayArticles'
import user from '../components/mainVue/user'
const routes = [
  {
    path: "/",
    name: "articleList",
    component: articlelist
  },
  {
    path: "/uploadArticle",
    name: "uploadArticle",
    component: uploadarticle
  },
  {
    path: "/userArticle",
    name: "userArticle",
    component: userArticle
  },
  {
    path: "/regedit",
    name: "regedit",
    component: regedit
  },
  {
    path: "/articleView",
    name: "articleView",
    component: articleView
  },
  {
    path:"/curDayArticles/:type",
    name: "curDayArticles",
    component: curDayArticles
  },
  {
    path:"/user/:uid",
    name:'user',
    component:user
  }
]

export const router = createRouter({
  history: createWebHistory(),
  routes: routes
})