<template>
    <a-layout>
        <a-layout-header class="header">
            <div class="logo"><img src="../../assets/logo.png" alt=""></div>
            <a-menu v-model:selectedKeys="selectHeadMenu" theme="dark" mode="horizontal"
                style="float: left;line-height: 64px;">
                <a-menu-item key="1">首页</a-menu-item>
                <a-menu-item key="2">排行榜</a-menu-item>
                <a-menu-item key="3">发现</a-menu-item>
            </a-menu>
            <a-input-search id="search" v-model:value="value" placeholder="请输入需要的搜索内容"
                style="width: 200px;margin-top: 15px;border: solid;margin-left: 250px;" @search="onSearch"
                @focus="onFocus" @blur="onBlur" />
            <div style="width:750px;height: 60px;position: fixed;right:-100px;top:0">
                <Avatar id="avatar" :login='login' :describe='describe' :userName='userName' :src='src'
                    :BackImgsrc='BackImgsrc'>
                </Avatar>
                <ul id="tinyUl" class='ver'>
                    <li class="verLi">
                        <div class="tinyField">
                            <router-link to="/userArticle">
                                <a-button type="dashed" shape="circle">
                                    <template #icon>
                                        <BulbOutlined class="icon" />
                                    </template>
                                </a-button>
                                <p
                                    style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;margin-top:15px;height: 15px;text-align: left;">
                                    创作
                                </p>
                            </router-link>
                        </div>
                    </li>
                    <li class="verLi">
                        <div class="tinyField">
                            <a-button type="dashed" shape="circle">
                                <template #icon>
                                    <SoundOutlined class="icon" />
                                </template>
                            </a-button>
                            <p
                                style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;margin-top:15px;height: 15px;">
                                动态</p>
                        </div>
                    </li>
                    <li class="verLi">
                        <div class="tinyField">
                            <a-button type="dashed" shape="circle">
                                <template #icon>
                                    <StarOutlined class="icon" />
                                </template>
                            </a-button>
                            <p
                                style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;margin-top:15px;height: 15px;">
                                收藏</p>
                        </div>
                    </li>
                    <li class="verLi">
                        <div class="tinyField">
                            <a-button type="dashed" shape="circle">
                                <template #icon>
                                    <HistoryOutlined class="icon" />
                                </template>
                            </a-button>
                            <p
                                style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;margin-top:15px;height: 15px;">
                                历史</p>
                        </div>
                    </li>
                </ul>
                <a-button type="primary" :size="primary" style="top: 15px;position:fixed;right: 15px;"
                    @click="uploadArticle">
                    <template #icon>
                        <UploadOutlined />
                    </template>
                    发表文章
                </a-button>
            </div>
        </a-layout-header>
        <a-layout>
            <a-layout-sider width="200" :style="menuStyle">
                <a-menu v-model:selectedKeys="selectItemMenu" v-model:openKeys="openItemMenu" mode="inline"
                    :style="{ height: '100%', borderRight: 0 }">
                    <a-sub-menu key="sub1">
                        <template #title>
                            <span>
                                <ReadOutlined />
                                文章
                            </span>
                        </template>
                        <a-menu-item key="1"><router-link to="/">分类</router-link></a-menu-item>
                        <a-menu-item key="2">精华</a-menu-item>
                        <a-menu-item key="3"><router-link to="/curDayArticles/不限">刚刚发布</router-link></a-menu-item>
                    </a-sub-menu>
                    <a-sub-menu key="sub2">
                        <template #title>
                            <span>
                                <UserOutlined />
                                {{other?userName+'的':'我的'}}空间
                            </span>
                        </template>
                        <a-menu-item key="9"><router-link :to="'/user/'+id">首页</router-link></a-menu-item>
                        <a-menu-item key="5" :style="{'display':(other?'none':'block')}"> 我的回复</a-menu-item>
                        <a-menu-item key="6" :style="{'display':(other?'none':'block')}"> 我的评论</a-menu-item>
                        <a-menu-item key="7" :style="{'display':(other?'none':'block')}">编辑资料</a-menu-item>
                        <a-menu-item key="8"> {{other?'ta的':'我的'}}关注</a-menu-item>
                        <a-menu-item key="10"> {{other?'ta的':'我的'}}收藏</a-menu-item>
                        <a-menu-item key="11"> {{other?'ta的':'我的'}}发布</a-menu-item>
                    </a-sub-menu>
                    <a-sub-menu key="sub3">
                        <template #title>
                            <span>
                                <InfoCircleOutlined />
                                优化网站
                            </span>
                        </template>
                        <a-menu-item key="9">提交反馈</a-menu-item>
                        <a-menu-item key="10">用户意见</a-menu-item>
                    </a-sub-menu>
                </a-menu>
            </a-layout-sider>
            <a-layout style="padding: 0 24px 24px;">
                <a-breadcrumb style="margin:10px 15px">
                    <a-breadcrumb-item>
                        <router-link to="/">
                            <home-outlined />
                            <span>首页</span>
                        </router-link>
                    </a-breadcrumb-item>
                    <a-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="index">
                        <router-link :to="item.href">
                            <span>{{ item.menu }}</span>
                        </router-link>
                    </a-breadcrumb-item>
                </a-breadcrumb>
                <a-layout-content :style="content">
                    <slot name="content"></slot>
                </a-layout-content>
            </a-layout>
        </a-layout>
    </a-layout>
</template>
<script>
import { getUserInfo } from '@/api/userRequest'
import { HomeOutlined, ReadOutlined, UserOutlined, InfoCircleOutlined, BulbOutlined, UploadOutlined, StarOutlined, HistoryOutlined, SoundOutlined } from '@ant-design/icons-vue';
import Avatar from '../avatar.vue'
import { defineComponent, ref } from 'vue';
import { post } from '../../lib/js/axios'
import workList from '../article.vue'
import $ from 'jquery'
import cookie from 'js-cookie'
import { message } from 'ant-design-vue';
import dayjs from 'dayjs'
dayjs.locale('zh-cn')
export default defineComponent({
    props: {
        breadcrumbs: {
            default: [],
            type: Array
        },
        itemMenu: {
            type: Array,
            default: ['1']
        },
        headMenu: {
            type: Array,
            default: ['1']
        },
        openKeys: {
            default: ['sub1'],
            type: Array
        },
        other:{
            default:true,
            type:Boolean
        }
    },
    data() {
        return {
            classCount: '10',
            login: false,
            userName: '',
            BackImgsrc: '',
            describe: '',
            src: '',
            value: '',
            id:-1,
            content: {
                'padding': '12px 150px',
                'margin': '0',
                'height': 'auto',
                'background': 'url(' + require('@/assets/img/background.webp') + ')'
            },
            menuStyle: {
                'background': '#fff',
                'height': 'auto',
            },
            // 界面初始化

            selectItemMenu: [],
            openItemMenu: [],
            selectHeadMenu: []
        };
    },
    mounted() {
        // 初始化界面
        let { itemMenu, headMenu, openKeys } = this.$props
        this.selectHeadMenu = headMenu
        this.selectItemMenu = itemMenu
        this.openItemMenu = openKeys
        let password = cookie.get('/user/loginPass')
        if (password) {
            getUserInfo(cookie.get('/user/token')).then(res => {
                res = res.data
                if (res.resultCode == 200) {
                    this.login = true
                    this.src = '/img/userHead/' + res.data.head
                    this.userName = res.data.username
                    this.describe = res.data.describe
                    this.BackImgsrc = '/img/userBackImg/' + res.data.cardimg
                    this.id = res.data.id
                } else {
                    cookie.remove('/user/loginPass')
                    cookie.remove('/user/token')
                    cookie.remove('/user/uid')
                    cookie.remove("/user/email")
                    message.warning(res.message)
                    location.reload()
                }
            })
        }
    },
    components: {
        SoundOutlined,
        HistoryOutlined,
        StarOutlined,
        UploadOutlined,
        BulbOutlined,
        ReadOutlined,
        UserOutlined,
        InfoCircleOutlined,
        Avatar,
        workList,
        HomeOutlined
    },
    methods: {
        // 搜索框点击搜索
        onSearch(value) {
            console.log(value)
        },
        // 搜索框聚焦
        onFocus() {
            console.log("聚焦")
            $('#search').css({
                'width': '300px'
            })
        },
        // 搜索框失焦
        onBlur() {
            console.log("失焦")
            $('#search').css({
                'width': '200px'
            })
        },
        // 更新count
        creaseCount(count) {
            console.log(count)
            this.classCount += count
        },
        // 发表文章
        uploadArticle() {
            console.log('发表文章')
            this.$router.push('/uploadArticle')
        }
    },
    setup() {
        return {

        };
    },

});

window.onscroll = function () {
    //为了保证兼容性，这里取两个值，哪个有值取哪一个
    //scrollTop就是触发滚轮事件时滚轮的高度
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    if (scrollTop > 150) {
        $("#avatar").hide()
        $('#tinyUl').addClass('hor').removeClass('ver')
        $('#tinyUl li').addClass('horLi').removeClass('verLi')
    } else {
        $("#avatar").show()
        $('#tinyUl').addClass('ver').removeClass('hor')
        $('#tinyUl li').addClass('verLi').removeClass('horLi')
    }
}
</script>