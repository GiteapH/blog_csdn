<template>
    <layout :itemMenu="['3']" breadcrumbs="刚刚发布">
        <template #content>
            <div style="margin-bottom:20px;">
                <div class="tagsDiv">
                    <ul class="def">
                        <li v-for="item in tagData" :key="item.name" class="tagItem">
                            <a class="tag" :href="item.href" :style="{ 'color': item.select ? 'red' : 'grey' }">{{
                                item.name
                            }}</a>
                        </li>
                    </ul>
                </div>
                <div class="pull">
                    <UpOutlined v-show="more" style="font-size: 10px;" />
                    <DownOutlined v-show="!more" style="font-size: 10px;" />
                </div>
            </div>
            <a-radio-group v-model:value="orderPath" size="small" @change="getCurDaysContent">
                <a-radio-button value="0">最受欢迎</a-radio-button>
                <a-radio-button value="1">最多观看</a-radio-button>
                <a-radio-button value="2">最多评论</a-radio-button>
                <a-radio-button value="3">最多收藏</a-radio-button>
            </a-radio-group>
            <div id="main">
                <a-spin :spinning="spinning" tip="数据加载中... " style="text-align:center;width:100%">
                    <div class="artItem" v-for="item in articleList">
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
                                    <a-col :span="5" style="margin-left:1rem">
                                        <div class="operation-c">
                                            <span class="el-popover__reference-wrapper">
                                                <p class="feedback el-popover__reference" style="margin-left:16px;">
                                                    <img class="dian" width="180"
                                                        src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png">
                                                </p>
                                            </span>
                                        </div>
                                    </a-col>
                                </a-row>
                            </div>
                        </div>
                        <a-divider />
                    </div>
                </a-spin>
            </div>
            <a-pagination v-model:current="currentPage" show-quick-jumper v-model:pageSize="pageSize" :total="total"
                @change="pageChange" @showSizeChange="getCurDaysContent" />
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
            currentPage: 1,
            spinning: true,
            total: 500,
            tagData: [],
            more: true,
            pageSize: 10,
            orderPath: '0',
            type: '不限',
            articleList: []
        }
    },
    components: {
        layout,
        UpOutlined,
        DownOutlined,
        LikeOutlined,
        DislikeOutlined,
        UserOutlined,
        EyeOutlined,
        MessageOutlined
    },
    methods: {
        pageChange(page) {
            this.currentPage = page
        },
        getCurDaysContent() {
            this.spinning = true
            this.articleList = []
            this.$articles.getCurDaysArticles(this.currentPage, this.pageSize, this.orderPath, this.type == '不限' ? null : this.type).then(res => {
                this.total = res.count

                for (let article of res.list) {
                    this.articleList.push({
                        title: article.title,
                        tinymes: article.tinymes,
                        username: article.username,
                        like: article.like,
                        watched: article.watched,
                        comments: article.comments
                    })
                }
                this.spinning = false
                console.log(this.articleList)
            }).catch(e => {
                console.error(e)
                message.error(e)
            })
        }
    },
    mounted() {
        const type = this.$route.params.type
        this.type = type
        this.tagData.push({
            name: '不限',
            href: '/curDayArticles/不限',
            select: type == '不限'
        })
        this.$articles.getTags().then(res => {
            for (let key in res) {
                this.tagData.push({
                    name: key,
                    href: '/curDayArticles/' + key,
                    count: res[key],
                    select: type == key
                })
            }
        }).catch(e => {
            message.error(e)
        })

        this.getCurDaysContent()

        // 种类下拉
        $('.pull').on("click", () => {
            this.more = !this.more
            $(".tagsDiv").toggleClass('more')
        })
    }
})
</script>
<style>
.tagsDiv {
    background: white;
    border-radius: 3px;
    padding: 15px;
    margin-top: 20px;
    height: 70px;
    transition: all 1.5s ease;
    overflow: hidden;
    background: white;
}

.more {
    height: auto;
}

.def {
    list-style: none;
}

.tagItem {
    display: inline;
    line-height: 28px;
    height: auto;
    word-break: break-all;
    word-wrap: break-word;
    margin-right: 30px;
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

.operation-b-img {
    margin-right: 16px;
    color: grey;
}

.operation-b-img :hover {
    color: darkgrey;
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