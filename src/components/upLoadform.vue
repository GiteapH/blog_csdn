<template>
    <a-form :model="formState" name="basic" :label-col="{ span: 1.2 }" :wrapper-col="{ span: 36 }" autocomplete="un"
        :layout="inline" :rules="rules" @finish="onFinish" @finishFailed="onFinishFailed">
        <div class="hover" style="background-color: white;border-radius: 6px;padding: 15px;margin-bottom: 10px;">
            <a-form-item label="文章标题" name="title">
                <a-input v-model:value="formState.title" />
            </a-form-item>
            <a-form-item label="文章简介" name="tinyMes">
                <a-input v-model:value="formState.tinyMes" />
            </a-form-item>
            <a-form-item label="文章类型" name="tags">
                <tags ref="tags"></tags>
            </a-form-item>
        </div>
        <a-form-item :wrapper-col="{ span: 36 }" name="edit">
            <Edit class="hover" ref="edit" :value="data"></Edit>
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 36 }" style="float:right">
            <a-button type="default" id="show" style="visibility: hidden;" @click="showAct"></a-button>
            <a-button type="default" :loading="onSave" style="margin-right: 15px;" @click="save">保存/更新</a-button>
            <a-button type="primary" :loading="onHand" html-type="submit">提交</a-button>
        </a-form-item>
    </a-form>
</template>
<script>
import { defineComponent } from 'vue';
import { message } from 'ant-design-vue';
import Edit from './tools/imcoder-tinymce.vue'
import { PlusOutlined, SmileOutlined } from '@ant-design/icons-vue';
import tags from './tags.vue'
import $ from 'jquery'
import { post,get } from '../lib/js/axios'
import cookie from 'js-cookie'
export default defineComponent({
    data() {
        return {
            // props---
            aid: null,
            uid: null,
            data: '',
            // props---
            checkThrough: false,
            onSave: false,
            onHand: false,
            formState: {
                title: '',
                tinyMes: '',
                remember: true,
                onCheck: false
            },
            rules: {
                tags: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let tags = this.$refs.tags.alltags
                        if (tags.length == 0) {
                            return Promise.reject('标签不能为空')

                        }
                        else return Promise.resolve()
                    }
                }],
                tinyMes: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let mes = this.formState.tinyMes
                        console.log(mes)
                        if (mes.length == 0) {
                            return Promise.reject('简介不能为空')

                        }
                        else if (mes.length < 15) {
                            return Promise.reject('简介不能少于15字数');
                        }
                        else if (mes.length > 50) {
                            return Promise.reject('简介不能多于50字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                title: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let title = this.formState.title
                        if (title.length == 0) {
                            return Promise.reject('标题不能为空')

                        }
                        else if (title.length < 3) {
                            return Promise.reject('标题不能少于3字数');
                        }
                        else if (title.length > 100) {
                            return Promise.reject('简介不能多于100字数');
                        }
                        else return Promise.resolve()
                    }
                }],
                edit: [{
                    required: true,
                    trigger: 'submit',
                    validator: (rule, value, callback) => {
                        let article = this.$refs.edit.content
                        if (article.length == 0) {
                            return Promise.reject('文章内容不能为空')

                        }
                        else if (article.length < 50) {
                            return Promise.reject('文章内容少于50字数');
                        }
                        else if (article.length > 80000) {
                            return Promise.reject('文章内容不能多于80000字数');
                        }
                        else return Promise.resolve()
                    }
                }]
            }
        }
    },
    components: {
        Edit,
        tags,
        PlusOutlined,
        SmileOutlined
    },
    mounted() {
        let id = cookie.get('/user/uid')
        if (id) {
            this.uid = id
        }
        let url = window.location.search
        if (url != '') {
            if(!this.uid){
                message.destroy("登录已失效，请重新登录后刷新")
                return 
            }
            url = url.substring(1).split("&")
            let UID = parseInt(url[0].split("=")[1].substring(7))
            let AID = parseInt(url[1].split("=")[1].substring(7))
            this.aid = AID
            this.uid = UID
            get('/article/getArticleById', {
                uid: UID,
                aid: AID
            }).then(res => {
                // 内容初始化                       
                //  0   1   2   3    4    5        6     7      8          9       10     11    12   13      14      15   16      17     18
                // uid aid art date like comment watch collect classper classsys title tinyMes id username password head mail describe backImg
                let data = res['data'].data
                console.log(data)
                this.data = data.articles
                // 标签
                let pers = data.classper ? data.classper.split(" ") : []
                let syss = data.classsys ? data.classsys.split(" ") : []
                this.$refs.tags.alltags = pers.concat(syss)
                this.$refs.tags.tags = syss
                this.$refs.tags.tagsPer = pers

                // 题目简介

                this.formState.title = data.title
                this.formState.tinyMes = data.tinymes
            })
        }
    },
    methods: {
        // 手动触发文章
        showAct() {
            this.$nextTick(() => {
                console.log(this.data)
                this.$refs.edit.setContent(`${this.data}`)
            })
        },
        // 验证成功回调
        onFinish(values) {
            console.log(values)
            console.log(this.onHand + " " + this.onSave);
            this.checkThrough = true
            this.hand()
        },
        // 验证失败回调
        onFinishFailed(errorInfo) {
            console.log('Failed:', errorInfo);
        },

        // 保存
        save() {
            let mes = check(this.$refs.tags.alltags, this.formState.tinyMes, this.formState.title, this.$refs.edit.content)
            if (mes == '1') {
                // this.$refs.edit.setContent(``)
                this.onSave = true
                let title = this.formState.title
                let mes = this.formState.tinyMes
                let tagssys = this.$refs.tags.tags
                let str = getSys(tagssys)
                let tagsper = this.$refs.tags.tagsPer.join(" ")
                let text = this.$refs.edit.content
                post('/article/saveArt', {
                    content: text,
                    uid: this.uid,
                    tagsPer: tagsper,
                    tagsSys: str,
                    title: title,
                    tinyMes: mes,
                    aid: this.aid
                }).then(res => {
                    console.log(res)
                    message.success("更新/保存成功")
                    this.onSave = false
                }).catch(res => {
                    message.error("更新/保存失败\n" + res)
                    this.onSave = false
                })
            } else {
                message.info(mes)
            }
        },
        // 提交
        hand(event) {
            if (!this.uid) {
                message.error("请先登录")
                return
            }
            if (this.checkThrough) {
                this.onHand = true
                let title = this.formState.title
                let mes = this.formState.tinyMes
                let tagssys = this.$refs.tags.tags
                let str = getSys(tagssys)
                let tagsper = this.$refs.tags.tagsPer.join(" ")
                let text = this.$refs.edit.content
                post('/article/uploadArticle', {
                    content: text,
                    uid: this.uid,
                    tagsPer: tagsper,
                    tagsSys: str,
                    title: title,
                    tinyMes: mes,
                    aid: this.aid
                }).then(res => {
                    console.log(res)
                    if (res.resultCode == 200) {
                        message.success("文章上传成功")
                    } else {
                        message.error(res.message)
                    }
                    this.onHand = false;
                }).catch((err) => {
                    message.error("文章上传失败\n" + err)
                    this.onHand = false;
                })
            }
        }
    }
});

function getSys(tagssys) {
    let set = new Set()
    for (let proxy of tagssys) {
        for (let i in proxy) {
            if (i == proxy.length - 1) continue
            set.add(proxy[i])
        }
    }
    let str = ''
    for (let tag of set) {
        str += tag + " "
    }
    return str
}

function check(tags, mes, title, article) {

    if (title.length == 0) {
        return ('标题不能为空')

    }
    else if (title.length < 3) {
        return ('标题不能少于3字数');
    }
    else if (title.length > 100) {
        return ('简介不能多于100字数');
    }
    // 简介
    if (mes.length == 0) {
        return ('简介不能为空')

    }
    else if (mes.length < 15) {
        return ('简介不能少于15字数');
    }
    else if (mes.length > 50) {
        return ('简介不能多于50字数');
    }



    if (tags.length == 0) {
        return "标签不能为空"

    }
    console.log(article)
    if (article.length == 0) {
        return ('文章内容不能为空')

    }
    else if (article.length < 50) {
        return ('文章内容少于50字数');
    }
    else if (article.length > 80000) {
        return ('文章内容不能多于80000字数');
    }
    return '1'
}

</script>

<style scoped>

</style>