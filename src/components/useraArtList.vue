<template>
    <div v-if="uid">
        <p style="color:gray;font-size:medium">当前共计: {{ actsLen }} 篇</p>
        <a-list class="demo-loadmore-list" :loading="initLoading" item-layout="horizontal" :data-source="list"
            :locale="locale">
            <a-empty :description="emptyText" v-show='empty' />
            <template #loadMore>
                <div v-if="!initLoading && !loading" v-show="!isNone"
                    :style="{ textAlign: 'center', marginTop: '12px', height: '32px', lineHeight: '32px' }">
                    <a-button @click="onLoadMore" :loading="loading">加载更多</a-button>
                </div>
            </template>
            <template #renderItem="{ item, index }">
                <modal :handleOk="handleOk" ref="modal" title="提示" modalText="确定删除该文章?"></modal>
                <a-list-item>
                    <template #actions>
                        <span v-for="{ type, text } in item.actions" :key="type">
                            <component :is="type" style="margin-right: 8px;float: none;" />
                            {{ text }}
                        </span>
                        <a key="list-loadmore-edit" style="color:red" @click="Delete(item.aid,index)">删除</a>
                        <a key="list-loadmore-edit" @click="watch(index)">查看</a>
                        <a key="list-loadmore-more" @click="getURL('/uploadArticle', uid, index)">编辑</a>
                    </template>
                    <a-skeleton avatar :title="false" :loading="!!item.loading" active>
                        <a-list-item-meta :description="item.tinyMes">
                            <template #title>
                                <a>
                                    <!-- 显示文章 -->
                                    <router-link to="/?uid=65145251&54784120" style="color:black;font-size: larger;">{{
        item.title
}}</router-link>
                                </a>
                            </template>
                            <template #avatar>
                                <a-avatar>{{ index + 1 }}</a-avatar>
                            </template>
                        </a-list-item-meta>
                        <div
                            style="font-size:medium;color:grey;position: relative;text-align: left;width: 60%;margin-left: 200px;margin-bottom: 23px;float: right;">
                            发布日期:{{ item.date }}
                        </div>
                        <ul id="tags">
                            <li v-for="tag in item.Tags">
                                <a-tag color="#2db7f5">{{ tag }}</a-tag>
                            </li>
                        </ul>
                    </a-skeleton>
                </a-list-item>
            </template>
        </a-list>
    </div>
    <div v-else>
        <a-empty
            image="https://gw.alipayobjects.com/mdn/miniapp_social/afts/img/A*pevERLJC9v0AAAAAAAAAAABjAQAAAQ/original"
            :image-style="{ height: '60px', }">
            <template #description>
                <span>
                    请先登录
                </span>
            </template>
            <a-button type="success"><a href="/regedit">点击登录</a></a-button>
        </a-empty>
    </div>
</template>
<script>
import { defineComponent } from 'vue';
import modal from './modal.vue'
import { post, get,del } from '../lib/js/axios'
import tags from './tags.vue'
import { formatDate } from '../lib/js/tools'
import { EyeOutlined, LikeOutlined } from '@ant-design/icons-vue';
import { Empty,message } from 'ant-design-vue';
import cookie from 'js-cookie'
const count = 3;
export default defineComponent({
    data() {
        return {
            aid: -1,
            itemIdx:0,
            uid: -1,
            loading: false,
            isNone: false,
            actsLen: 0,
            initLoading: true,
            data: [],
            list: [],
            locale: {
                emptyText: ''
            },
            page: 1,
            emptyText: '暂无数据',
            empty: false
        };
    },
    components: {
        tags,
        modal,
        EyeOutlined,
        LikeOutlined
    },
    mounted() {
        let id = cookie.get('/user/uid')
        this.uid = id
        if (id) {
            get('/article/getAllActLen', { uid: id }).then(({ data }) => {
                this.actsLen = data.data.len
                get('/article/getUserArticle', {
                    uid: id
                }).then(res => {
                    res = res['data'].data
                    console.log(res)
                    for (let data of res) {
                        data.classsys = data.classsys + (data.classper ? " " + data.classper : '')
                        this.initLoading = false;
                        let info = {
                            loading: false,
                            title: data.title,
                            aid: data.aid,
                            tinyMes: data.tinymes,
                            date: formatDate(data.date),
                            Tags: data.classsys.split(" "),
                            actions: [{
                                type: 'EyeOutlined',
                                text: data.watched,
                            }, {
                                type: 'LikeOutlined',
                                text: data.like
                            }]
                        }
                        console.log(info)
                        this.data.push(info);
                        this.list.push(info)
                    }
                    console.log(this.actsLen + " " + this.list.length)
                    if (this.actsLen == this.list.length) {
                        this.isNone = true
                    }
                    this.initLoading = false;
                }).catch(err => {
                    this.initLoading = false;
                })
            })
        } else {
            this.initLoading = false;
            this.isNone = true
            this.emptyText = '请先登录'
        }
    },
    methods: {
        watch(index) {
            this.getURL('/articleView', this.uid, this.data[index].aid)
        },
        getRandomSum(len) {
            let str = ''
            for (let i = 0; i < len; i++) {
                let s = parseInt(Math.random() * 10);
                str = str + '' + s
                console.log(str)
            }
            return str
        },
        getURL(url, uid, index) {
            let aid = this.data[index].aid
            let str = url + '?uid='
            let uidStr = this.getRandomSum(7) + uid
            let aidStr = this.getRandomSum(7) + aid
            str = str + uidStr + "&aid=" + aidStr
            console.log(str)
            this.$router.push(str)
        },
        handleOk() {
            del("/article/deleteAct",{}, {
                aid: this.aid
            }).then(res => {
                if(res.data.resultCode==200){
                    this.$refs.modal.visible = false
                    this.list.splice(this.itemIdx, 1)
                    this.data.splice(this.itemIdx, 1)
                    message.success("删除成功")
                }
            })
        },
        Delete(aid,index) {
            this.aid = aid
            this.itemIdx = index
            this.$refs.modal.visible = true
        },
        onLoadMore() {
            this.loading = true;
            get('/article/getUserArticle', {
                uid: cookie.get('/user/uid'),
                start: ++this.page
            }).then(res => {
                res = res['data'].data
                for (let data of res) {
                    data.classsys = data.classsys + (data.classper ? " " + data.classper : '')
                    this.initLoading = false;
                    let info = {
                        loading: false,
                        title: data.title,
                        aid: data.aid,
                        tinyMes: data.tinymes,
                        date: formatDate(data.date),
                        Tags: data.classsys.split(" "),
                        actions: [{
                            type: 'EyeOutlined',
                            text: data.watched,
                        }, {
                            type: 'LikeOutlined',
                            text: data.like
                        }]
                    }
                    this.data.push(info);
                    this.list.push(info)
                }
                this.loading = false
                console.log(this.actsLen)
                if (this.actsLen == this.list.length) {
                    this.isNone = true
                }
            }).catch(() => {
                this.loading = false
            })
        }
    },
});
</script>
<style scoped>
#tags {
    list-style: none;
    position: relative;
    top: 0px;
    left: 0px;
    width: 100%;
    margin-bottom: 20px;
}

#tags li {
    float: left;
}

* {
    white-space: nowrap;
}
</style>