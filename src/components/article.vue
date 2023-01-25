
<template>
    <div style="display: inline-block;margin-bottom:50px;width: 100%;">
        <div class="shadow" style="width:100%">
            <div class="fontHead">
                <p class="tag">分类</p>
                <p class="tagright" style="margin-right: 35px;margin-left: 5px;">条</p>
                <p style="color:orange;float: right;font-size: 30px;line-height: 55.4px;">{{ tagCount }}</p>
                <p class="tagright" style="margin-right:5px">共</p>
            </div>
        </div>
        <div class="shadow hover"
            style="float:left;height: auto;padding:15px;margin-top:-30px;width: 100%;text-align: center;">
            <a-spin :spinning="isLoadTag" />
            <ul style="float:left;height:100%;">
                <li v-for="(item, index) in tagsData" style="height: 42.2px;" :key="index" class="tagLi">
                    <classify :value="item.value" :count="item.count" @click="classifyClick($event, index)"></classify>
                </li>
            </ul>
        </div>
    </div>

    <div class="shadow" style="height:50px;">
        <div class="fontHead">
            <p class="tag">文章列表</p>
            <p class="tagright" style="margin-right: 35px;margin-left: 5px;">篇</p>
            <p style="color:orange;float: right;font-size: 30px;line-height: 55.4px;" @creaseCount='creaseCount'>{{
                classCount
            }}</p>
            <p class="tagright" style="margin-right:5px">共</p>

        </div>
        <div style='float: left;margin-left: 50px;margin-top: 10px;z-index: 5;'>
            <a-radio-group v-model:value="radioValue" option-type="button" :options="plainOptions"
                @change='RadioChange' />
        </div>
    </div>
    <div class="shadow hover" style="float:left;height: auto;padding:15px;margin-top:-35px;width:100%;">
        <a-list class="demo-loadmore-list" :loading="initLoading" item-layout="horizontal" :data-source="list">
            <template #loadMore>
                <div v-if="!initLoading && !loading" v-show="notEnd"
                    :style="{ textAlign: 'center', height: '32px', lineHeight: '35px' }">
                    <a-button @click="onLoadMore" :loading="loading">加载更多</a-button>
                </div>
            </template>
            <template #renderItem="{ item, index }">
                <a-list-item key="item.content" style="text-align:left;display:block;width: 100%;">
                    <!-- description:发布日期 -->
                    <a-list-item-meta
                        :description="'发布时间: ' + item.time + '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp up主:' + item.name">
                        <template #title>
                            <!-- 姓名 -->
                            <a :href="item.href" style="float:left">{{ item.title }}</a>
                            <ul id="class">
                                <li v-for="tag in item.Tags" :key="item.id">
                                    <a-tag color="#2db7f5">{{ tag }}</a-tag>
                                </li>
                            </ul>
                            <br>
                            <br>
                        </template>
                        <template #avatar>
                            <a-avatar :src="item.avatar" />
                        </template>
                    </a-list-item-meta>
                    <template #actions>
                        <!-- 看过的几人，点赞几人，评论几人 -->
                        <div style="margin-top:20px;">
                            <span v-for="{ type, text } in item.actions" :key="type" style="margin-left:18px">
                                <component :is="type" />
                                {{ text }}
                            </span>
                        </div>
                    </template>
                    <!-- 文章简介内容 -->
                    <div style="font-family:'Courier New', Courier, monospace;font-weight:200;height: 50px;white-space: pre-line;margin-top:0.5rem;overflow: hidden;">
                        {{ item.content }}</div>
                </a-list-item>
            </template>
        </a-list>
    </div>
</template>
<script>
import { StarOutlined, LikeOutlined, MessageOutlined, EyeOutlined } from '@ant-design/icons-vue';
import { nextTick } from 'vue';
import classify from './classifyTag.vue'
import { post, get } from '../lib/js/axios'
import { formatDate } from '../lib/js/tools'

import { getURL } from '../lib/js/tools'
export default {
    data() {
        return {
            // tags
            currentPage: 1,
            tagsData: [],
            cieTag: '不限',
            allList: [],
            //table
            plainOptions: ['最受欢迎', '最多观看', '最多评论', '最多收藏'],
            radioValue: '最受欢迎',
            radioMap: {
                '最受欢迎': '0',
                '最多观看': '1',
                '最多评论': '2',
                '最多收藏': '3'
            },
            notEnd: true,
            len: 0,
            uid: 0,
            tagCount: 0,
            classCount: 0,
            data: [],
            list: [],
            loading: false,
            initLoading: true
        };
    },
    components: {
        StarOutlined,
        LikeOutlined,
        MessageOutlined,
        EyeOutlined,
        classify
    },
    mounted() {
        let map = new Map()
        // table
        this.initLoading = true;
        this.isLoadTag = true
        get('/article/getAllActLen').then(res => {
            this.len = res.data.data.len
            // tags
            let sum = 0
            get('/article/getTags').then(({ data }) => {
                let tagsData = data.data
                console.log(tagsData)
                let strMap = new Map();
                for (let k of Object.keys(tagsData)) {
                    strMap.set(k, tagsData[k]);
                }
                var arrayObj = Array.from(strMap);
                arrayObj.sort((o1, o2) => {
                    return o2[1] - o1[1]
                })
                console.log(arrayObj)
                map = new Map(arrayObj.map(i => [i[0], i[1]]));
                let contain = new Map()
                let sumTen = 0, i = 0
                for (let [key, value] of map.entries()) {
                    if (i++ > 15) break
                    sumTen += value
                    contain.set(key, value)
                }
                // if (map.size > 10) {
                //     contain.set('其他', sum - sumTen)
                // }
                this.tagsData[0] = {
                    value: '不限',
                    count: this.len
                }
                for (let [key, value] of contain.entries()) {
                    this.tagsData.push({
                        value: key,
                        count: value
                    })
                }
                this.$nextTick(() => {
                    document.getElementsByClassName('tagLi')[0].style.border = "#40a9ff solid 1px"
                    this.tagCount = this.tagsData.length
                })
                this.isLoadTag = false
            })
            get('/article/getUsers2Article', {
                pageNumber: 1,
                orderPath: '0'
            }).then(({ data }) => {
                //['Ms.', uid, 'CLLrNeMAqg', '高子韬', 'The first step is as good as half over.', '2016-01-25T16:53:36.000+00:00', 821, 33, 769, 691, 'Flexible settings en',aid]
                console.log(data)
                for (let info of data.data) {
                    console.log(info)

                    this.classCount++
                    this.list.push({
                        name: info.username,
                        href: getURL('/articleView', info.id, info.aid),
                        title: info.title,
                        avatar: '/img/userHead/logo.png',
                        time: formatDate(info.date),
                        content: info.tinymes,
                        actions: [{
                            type: 'EyeOutlined',
                            text: info.watched
                        }, {
                            type: 'LikeOutlined',
                            text: info.like
                        }, {
                            type: 'MessageOutlined',
                            text: info.comments
                        }, {
                            type: 'StarOutlined',
                            text: info.collected
                        }],
                        Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                    });
                    this.data.push({
                        name: info.username,
                        href: getURL('/articleView', info.id, info.aid),
                        title: info.title,
                        avatar: '/img/userHead/logo.png',
                        time: formatDate(info.date),
                        content: info.tinymes,
                        actions: [{
                            type: 'EyeOutlined',
                            text: info.watched
                        }, {
                            type: 'LikeOutlined',
                            text: info.like
                        }, {
                            type: 'MessageOutlined',
                            text: info.comments
                        }, {
                            type: 'StarOutlined',
                            text: info.collected
                        }],
                        // info.comments
                        Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                    });
                }

                if (this.data.length == this.len) {
                    this.notEnd = false
                }
                this.initLoading = false;
            })
        })
    },
    methods: {
        RadioChange() {
            this.initLoading = true;
            this.classCount = 0
            console.log(this.cieTag)
            get('/article/getUsers2Article', {
                pageNumber: 1,
                orderPath: this.radioMap[this.radioValue],
                tag: this.cieTag == '不限' ? null : this.cieTag
            }).then(({ data }) => {
                //['Ms.', 3, 'CLLrNeMAqg', '高子韬', 'The first step is as good as half over.', '2016-01-25T16:53:36.000+00:00', 821, 33, 769, 691, 'Flexible settings en']
                this.list = []
                this.data = []
                for (let info of data.data) {
                    this.classCount++
                    this.list.push({
                        name: info.username,
                        href: getURL('/articleView', info.id, info.aid),
                        title: info.title,
                        avatar: '/img/userHead/logo.png',
                        time: formatDate(info.date),
                        content: info.tinymes,
                        actions: [{
                            type: 'EyeOutlined',
                            text: info.watched
                        }, {
                            type: 'LikeOutlined',
                            text: info.like
                        }, {
                            type: 'MessageOutlined',
                            text: info.comments
                        }, {
                            type: 'StarOutlined',
                            text: info.collected
                        }],
                        Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                    });
                    this.data.push({
                        name: info.username,
                        href: getURL('/articleView', info.id, info.aid),
                        title: info.title,
                        avatar: '/img/userHead/logo.png',
                        time: formatDate(info.date),
                        content: info.tinymes,
                        actions: [{
                            type: 'EyeOutlined',
                            text: info.watched
                        }, {
                            type: 'LikeOutlined',
                            text: info.like
                        }, {
                            type: 'MessageOutlined',
                            text: info.comments
                        }, {
                            type: 'StarOutlined',
                            text: info.collected
                        }],
                        Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                    });
                }
                if (this.data.length == this.len) {
                    this.notEnd = false
                }
                this.initLoading = false;
            })

            nextTick(() => {
                // Resetting window's offsetTop so as to display react-virtualized demo underfloor.
                // In real scene, you can using public method of react-virtualized:
                // https://stackoverflow.com/questions/46700726/how-to-use-public-method-updateposition-of-react-virtualized
                window.dispatchEvent(new Event('resize'));
            });
            this.currentPage = 1
        },
        // 点击标签
        classifyClick(e, index) {
            let value = e.target.innerText.replaceAll(" ", '')
            let eles = document.getElementsByClassName('tagLi')
            console.log(eles.length)
            for (let i = 0; i < eles.length; i++) {
                console.log(eles[i])
                if (i == index) {
                    eles[index].style.border = "#40a9ff solid 1px"
                } else {
                    eles[i].style.border = "none"
                }
            }
            this.cieTag = value
            this.notEnd = true
            if (value.indexOf('条') == -1) {
                this.initLoading = true
                this.data = []
                this.list = []
                this.classCount = 0
                get('/article/getUsers2Article', {
                    pageNumber: 1,
                    orderPath: this.radioMap[this.radioValue],
                    tag: value == '不限' ? null : value
                }).then(({ data }) => {
                    //                                                                                                              like com watch coll
                    //['Ms.', 3, 'CLLrNeMAqg', '高子韬', 'The first step is as good as half over.', '2016-01-25T16:53:36.000+00:00', 821, 33, 769, 691, 'Flexible settings en']
                    for (let info of data.data) {

                        this.classCount++
                        this.list.push({
                            name: info.username,
                            href: getURL('/articleView', info.id, info.aid),
                            title: info.title,
                            avatar: '/img/userHead/logo.png',
                            time: formatDate(info.date),
                            content: info.tinymes,
                            actions: [{
                                type: 'EyeOutlined',
                                text: info.watched
                            }, {
                                type: 'LikeOutlined',
                                text: info.like
                            }, {
                                type: 'MessageOutlined',
                                text: info.comments
                            }, {
                                type: 'StarOutlined',
                                text: info.collected
                            }],
                            Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                        });
                        this.data.push({
                            name: info.username,
                            href: getURL('/articleView', info.id, info.aid),
                            title: info.title,
                            avatar: '/img/userHead/logo.png',
                            time: formatDate(info.date),
                            content: info.tinymes,
                            actions: [{
                                type: 'EyeOutlined',
                                text: info.watched
                            }, {
                                type: 'LikeOutlined',
                                text: info.like
                            }, {
                                type: 'MessageOutlined',
                                text: info.comments
                            }, {
                                type: 'StarOutlined',
                                text: info.collected
                            }],
                            Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                        });
                    }

                    if (this.data.length == this.len) {
                        this.notEnd = false
                    }
                    this.initLoading = false;
                })

                this.classCount = this.list.length
                console.log(this.data)
            }
            this.currentPage = 1
        },
        // 加载更多
        onLoadMore() {
            this.loading = true;
            get('/article/getUsers2Article', {
                pageNumber: ++this.currentPage,
                orderPath: this.radioMap[this.radioValue],
                tag: this.cieTag == '不限' ? null : this.cieTag
            }).then(({ data }) => {
                if (data.data.length == 0) this.notEnd = false
                for (let info of data.data) {
                    this.list.push({
                        name: info.username,
                        href: getURL('/articleView', info.id, info.aid),
                        title: info.title,
                        avatar: '/img/userHead/logo.png',
                        time: formatDate(info.date),
                        content: info.tinymes,
                        actions: [{
                            type: 'EyeOutlined',
                            text: info.watched
                        }, {
                            type: 'LikeOutlined',
                            text: info.like
                        }, {
                            type: 'MessageOutlined',
                            text: info.comments
                        }, {
                            type: 'StarOutlined',
                            text: info.collected
                        }],
                        Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                    });
                    this.data.push({
                        name: info.username,
                        href: getURL('/articleView', info.id, info.aid),
                        title: info.title,
                        avatar: '/img/userHead/logo.png',
                        time: formatDate(info.date),
                        content: info.tinymes,
                        actions: [{
                            type: 'EyeOutlined',
                            text: info.watched
                        }, {
                            type: 'LikeOutlined',
                            text: info.like
                        }, {
                            type: 'MessageOutlined',
                            text: info.comments
                        }, {
                            type: 'StarOutlined',
                            text: info.collected
                        }],
                        Tags: info.classsys == "" ? [] : info.classsys.replace(/^\s+|\s+$/g, '').split(" "),
                    });
                }
                this.classCount = this.list.length
                this.loading = false;
            })
        }
    },
};
</script>
<style scoped>
#class {
    list-style: none;
    position: relative;
    top: 0px;
    left: 0px;
    width: 100%;
}

#class li {
    float: left;
}

.demo-loadmore-list {
    min-height: 350px;
}

.shadow li {
    float: left;
    list-style: none;
    display: block;
    white-space: nowrap;
    margin-left: 20px;
    margin-bottom: 5px;
}



.shadow {
    border: solid 1px gainsboro;
    /* Font & Text */
    font-family: Lato, "Helvetica Neue", Arial, Helvetica, sans-serif;
    font-size: 14px;
    font-weight: 400;
    letter-spacing: normal;
    line-height: 19.999px;
    text-align: start;
    vertical-align: baseline;
    /* Color & Background */
    color: rgb(76, 73, 72);
    /* Box */
    height: 55.4219px;
    background-color: #fff;
    border-radius: 6px;
    /* Positioning */
    position: static;
    /* Miscellaneous */
    /* Effects */
    outline-offset: 0px;
    box-sizing: border-box;
}

.hover:hover {
    box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
}

.tag {
    float: left;
    line-height: 55.4px;
    color: seagreen;
    font-size: 25px;
    font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
    font-weight: 700;
    margin-left: 50px;
}

.tagright {
    float: right;
    line-height: 55.4px;
    color: seagreen;
    font-size: 25px;
    font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
    font-weight: 700;
}
</style>