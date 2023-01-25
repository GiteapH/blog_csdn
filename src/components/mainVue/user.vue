<template>
    <layout :itemMenu="['9']" :openKeys="['sub2']" :breadcrumbs="breadcrumbs" :other="isMyself" ref="layout">
        <template #content>
            <div class="userTitle">
                <el-row :gutter="30">
                    <el-col :span="2">
                        <img :src="'/img/userHead/' + userinfo.head"
                            style="width:80px;border-radius: 50%;border:1px beige solid;margin-top:-25px" />
                    </el-col>
                    <el-col :span="17">
                        <p style="font-weight: 400;font-size:25px;line-height: 25px;">{{ userinfo.username }}</p>
                        <el-input v-model="describe" maxlength="40" placeholder="请输入您的个性签名" show-word-limit
                            v-show="onEnter" @blur="enterBlur" type="text" />
                        <h4 style="color:darkgray;font-weight:300;line-height: 20px;" v-show="!onEnter"
                            @click="enterMethod">{{ userinfo.describe }}</h4>
                    </el-col>
                    <el-col :span="4" style="text-align: right;">

                        <el-button plain @click="setting = true">
                            面板设置
                        </el-button>

                        <el-dialog v-model="setting" width="40%" align-center>
                            <template #header>
                                <h2 style="text-align:left;font-family:system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif">请选择需要开放的板块内容</h2>
                            </template>
                            <el-checkbox-group v-model="checkedModules" :min="1" style="text-align: left;">
                                <el-checkbox v-for="userModule in userModules" :key="userModule.key"
                                    :label="userModule.name">{{ userModule.name }}</el-checkbox>
                                    <el-checkbox disabled >正在持续更新中</el-checkbox>
                            </el-checkbox-group>
                            <template #footer>
                                <span class="dialog-footer">
                                    <el-button @click="setting = false">取消</el-button>
                                    <el-button type="primary" @click="setting = false">
                                        确定
                                    </el-button>
                                </span>
                            </template>
                        </el-dialog>
                    </el-col>
                </el-row>
            </div>
            <div class="contentDiv">
                <a-tabs v-model:activeKey="activeKey" @edit="onEdit">
                    <a-tab-pane key="收藏列表" :tab="'收藏列表' + (!userModules['收藏列表'].checked ? '(未开放)' : '')"
                        :closable="false">
                        <el-empty v-if="!userModules['收藏列表'].checked" description="up尚未开启该模块的访问权限" />
                        <div v-else>
                            <div class="artItem" v-for="item in collections">
                                <a class="title">
                                    <p>
                                        {{ item.title }}
                                    </p>
                                </a>
                                <div>
                                    <div>
                                        <a-row>
                                            <a-col :span="18">
                                                <a>
                                                    <p id="content">
                                                        {{ item.tinymes }}
                                                    </p>
                                                </a>
                                                <div style="display: inline-flex;height:20px;margin-top:20px;">
                                                    <p class="operation-b-img">
                                                        <EyeOutlined />
                                                        <span class="num ">{{ item.watched }}次阅读</span>
                                                    </p>
                                                    <p class="operation-b-img">
                                                        <LikeOutlined />
                                                        <span class="num ">{{ item.like }}人觉得不错</span>
                                                    </p>
                                                    <p class="operation-b-img">
                                                        <DislikeOutlined />
                                                        <span class="num "></span>
                                                    </p>
                                                    <span style="color:grey;margin-right:16px;">|</span>
                                                    <a target="_blank" href="https://blog.csdn.net/weixin_44350337">
                                                        <MessageOutlined />
                                                        <span>{{ item.comments }}</span>
                                                    </a>

                                                    <a target="_blank" href="https://blog.csdn.net/weixin_44350337"
                                                        style="margin-left:15px">
                                                        <UserOutlined />
                                                        <span>{{ item.username }}</span>
                                                    </a>

                                                </div>
                                            </a-col>
                                        </a-row>
                                    </div>
                                </div>
                                <a-divider />
                            </div>
                        </div>
                    </a-tab-pane>
                    <a-tab-pane key="关注列表" :tab="'关注列表' + (!userModules['关注列表'].checked ? '(未开放)' : '')"
                        :closable="false">
                        <el-empty v-if="!userModules['关注列表'].checked" description="up尚未开启该模块的访问权限" />
                        <p v-else>关注列表</p>
                    </a-tab-pane>
                    <a-tab-pane key="发布列表" :tab="'发布列表' + (!userModules['发布列表'].checked ? '(未开放)' : '')"
                        :closable="false">
                        <el-empty v-if="!userModules['发布列表'].checked" description="up尚未开启该模块的访问权限" />
                        <p v-else>发布列表</p>
                    </a-tab-pane>
                    <a-tab-pane key="发布数量打卡" :tab="'发布数量打卡' + (!userModules['发布数量打卡'].checked ? '(未开放)' : '')"
                        :closable="false">
                        <el-empty v-if="!userModules['发布数量打卡'].checked" description="up尚未开启该模块的访问权限" />
                        <p v-else>发布数量打卡</p>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </template>
    </layout>
</template>
<script>
import layout from '../layout/layout.vue'
import { defineComponent } from 'vue'
import { message } from 'ant-design-vue'
import { UpOutlined, DownOutlined, LikeOutlined, DislikeOutlined, UserOutlined, EyeOutlined, MessageOutlined } from '@ant-design/icons-vue'
import $ from 'jquery'
export default defineComponent({
    data() {
        return {
            breadcrumbs: "",
            isMyself: true,
            loginUser: {},

            uid: '',
            userinfo: {},
            describe: '',
            onEnter: false,
            setting: false,


            userModules: {
                "发布列表": {
                    "name": "加载中...",
                    "checked": true
                },
                "发布数量打卡": {
                    "name": "加载中...",
                    "checked": true
                },
                "收藏列表": {
                    "name": "加载中...",
                    "checked": true
                },
                "关注列表": {
                    "name": "加载中...",
                    "checked": true
                },
            },
            checkedModules: [],
            activeKey: '收藏列表',

            collections: []
        }
    },
    mounted() {
        this.uid = parseInt(this.$route.params.uid)
        this.$users.getUserInfoByIds(this.uid).then(({ data }) => {
            this.userinfo = data.list[0]
            this.describe = this.userinfo.describe
            if (this.$refs.layout.id == this.uid) {
                this.isMyself = false
                this.$updateForce()
            }
            this.breadcrumbs = [{
                menu: this.userinfo.username + " 的个人空间",
                href: '/'
            }]
        })
        this.$UMs.getUserModules(this.uid).then(res => {
            this.userModules = res
            for (let UM in res) {

                if (res[UM].checked) {
                    this.checkedModules.push(UM)
                }
            }
        })

        this.$ColReq.getAllColByUid(this.uid).then(res => {
            this.collections = res
        })
    },
    methods: {
        enterMethod() {
            this.onEnter = true

        },
        enterBlur() {
            this.onEnter = false
        }
    },
    components: {
        layout,
        UpOutlined, DownOutlined, LikeOutlined, DislikeOutlined, UserOutlined, EyeOutlined, MessageOutlined
    }
})
</script>
<style>
.userTitle {
    background-color: white;
    padding: 5px;
    border-radius: 2%;
    margin-top: 25px;
}

.contentDiv {
    background-color: white;
    padding: 10px
}

#content {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden;
    color: darkgrey;
    margin-top: 15px;
}

.tag {
    font-size: 13px;
    font-family: PingFang SC, Hiragino Sans GB, Arial, Microsoft YaHei, Verdana, Roboto, Noto, Helvetica Neue, sans-serif;
    width: 55px;

}

.pull {
    margin: 0 auto;
    width: 30px;
    background-color: gainsboro;
    border-bottom-left-radius: 45%;
    border-bottom-right-radius: 45%;
    height: 10px;
    text-align: center;
    line-height: 10px;
}

#main {
    margin: 15px auto;
}

.title {
    padding: 0;
    color: black;
}

.title p {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 320px;
    -o-text-overflow: ellipsis;
    font-size: 20px;
    height: 30px;
    margin-bottom: 0px;
}

.operation-c {
    float: left;
}

.artItem {
    background: white;
    padding: 10px;
    border-radius: 10px;
}
</style>