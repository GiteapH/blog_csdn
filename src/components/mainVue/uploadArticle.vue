<template>
    <layout :breadcrumbs="breadcrumbs">
        <template #content>
            <div class="shadow" style="height:50px;">
                <div class="fontHead">
                    <h1
                        style="line-height:50px;font-size: 30px;font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;font-weight: 500;margin-left: 15px;">
                        文章编辑</h1>
                </div>
            </div>
            <content style="margin-top:15px"></content>
        </template>
    </layout>
</template>
<script>
import { ReadOutlined, UserOutlined, InfoCircleOutlined, BulbOutlined, UploadOutlined, StarOutlined, HistoryOutlined, SoundOutlined } from '@ant-design/icons-vue';
import Avatar from '../avatar.vue'
import classify from '../classifyTag.vue'
import { defineComponent, ref } from 'vue';
import workList from '../article.vue'
import content from '../upLoadform.vue'
import $ from 'jquery'
import { post } from '../../lib/js/axios'
import cookie from 'js-cookie'
import layout from '../layout/layout'


export default defineComponent({
    data() {
        return {
            breadcrumbs: [{
                href: "/",
                menu: location.search==''?'文章创作': "文章编辑"
            }],
            onSave: false,
            onHand: false,
            classCount: '10',
            value: '',
        };
    },
    mounted() {

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
        classify,
        content,
        layout
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
        }
    }
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
<style>
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

.logo {
    height: 60px;
    width: 60px;
    float: left;
    margin-left: -10px;
}

.logo img {
    height: 60px;
    width: 60px;
    z-index: -1;
}

#search {
    transition: all 0.3s ease;
}

.hor {
    list-style: none;
    position: fixed;
    right: 15px;
    top: 50px;
}

.ver {
    position: fixed;
    list-style-type: none;
    right: 150px;
    color: #fff;
    top: 0;
}

.horLi {
    margin-top: 15px;
    line-height: 0px;
    right: 0px;
}

.verLi {
    float: left;
    line-height: 0px;
    right: 0px;
}

.tinyField {
    height: 60px;
    width: 80px;
    padding-top: 5px;
    line-height: 0px;
}

.icon:hover {
    animation: jumpBoxHandler 0.5s infinite;
    /* 1.8s 事件完成时间周期 infinite无限循环 */
}

@keyframes jumpBoxHandler {

    /* css事件 */
    0% {
        transform: translate(0px, 0px);
    }

    50% {
        transform: translate(0px, 5px);
        /* 可配置跳动方向 */
    }

    100% {
        transform: translate(0px, 0px);
    }
}
</style>