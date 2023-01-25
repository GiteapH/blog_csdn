<template>
    <layout>
        <template #content>
            <!-- 评论 -->
            <articleComment @showComment="showComment" ref="comment" @close="close" :comments="comments" :uid="upuid"
                :aid="upaid">
            </articleComment>
            <div style="display:flex;width:100%;height:100%;">
                <div class="shadow"
                    style="margin-left: 10px;margin-top: 30px;padding: 10px;width: max-content;width: 100%;height: 100%;">
                    <h1 class="title">{{ title }}</h1>
                    <a-divider></a-divider>
                    <div class="titleCard">
                        <div style="display:inline-flex">
                            <p>
                                <UserOutlined />up主:{{ upPerson }}
                            </p>
                            <p>
                                <history-outlined />发布时间:{{ date }}
                            </p>
                            <p>
                                <EyeOutlined />观看数:{{ watched }}
                            </p>
                            <p style="color: #1a91ff;">
                                <StarFilled v-show="collect.has(loginId)" @click="doCollect()" />
                                <StarOutlined v-show="!collect.has(loginId)" @click="doCollect(false)" />
                                {{ collect.has(loginId) ? '已' : '' }}收藏
                            </p>
                        </div>
                        <div style="display:block;padding-top: 15px">
                            <div style="display:inline-flex;">
                                <p>
                                    <TagOutlined />文章标签
                                </p>
                                <a-tag color="#2db7f5" v-for="tag in tags" :key="tag">{{ tag }}</a-tag>
                            </div>
                        </div>

                    </div>
                    <div>
                        <a-divider></a-divider>
                        <div style="margin-bottom:15px">
                            <h1 style="font-size:20px">小记:</h1>
                            <p style="font-size:larger;font-style: italic;color: darkgray;">{{ tinyMes }}</p>
                        </div>
                        <a-divider></a-divider>
                        <edit :disabled="true" :value="data" ref="edit" :init="init"></edit>

                        <div class="actBottom">
                            <el-row :gutter="30">
                                <el-col :span="10">
                                    <a-avatar size="medium"
                                        style="margin-right: 15px;bottom: 10px;border: solid 1px gainsboro;float:left"
                                        :src="head">USER</a-avatar>
                                    <p>{{ upPerson }}</p>
                                </el-col>
                                <el-col :span="3">
                                    <p class="icon" style="font-size: 15px;">
                                        <LikeFilled v-show="like.has(loginId)" @click="doLike()" />
                                        <LikeOutlined v-show="!like.has(loginId)" @click="doLike(false)" />{{
                                            like.size
                                        }}人点赞
                                    </p>
                                </el-col>
                                <el-col :span="2">
                                    <p class="icon" style="font-size: 15px;">
                                        <DislikeFilled v-show="dislikes.has(loginId)" @click="doDislike()" />
                                        <DislikeOutlined v-show="!dislikes.has(loginId)" @click="doDislike(false)" />
                                        {{ dislikes.size }}
                                    </p>
                                </el-col>
                                <el-col :span="3">
                                    <p class="icon" style="font-size: 15px;">
                                        <StarFilled v-show="collect.has(loginId)" @click="doCollect()" />
                                        <StarOutlined v-show="!collect.has(loginId)" @click="doCollect()" />
                                        {{ collect.size }}人收藏
                                    </p>
                                </el-col>
                                <el-col :span="3">
                                    <p class="icon" style="font-size: 15px;" @click="showComment">
                                        <MessageOutlined />{{!comments.len ? '展开' : comments.len}}
                                    </p>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>
                <div style="width:350px;margin-left: 30px;">
                    <div class="shadow" style="height:300px;margin-top: 30px;width:300px">
                        <div
                            style="padding:10px 10px;display:inline-flex;background: url('/img/webImg/backgroud.png');width: 100%;">
                            <a-avatar size="large" style="margin-top:3px;border: solid 1px silver;" :src="head">
                                USER</a-avatar>
                            <div style="margin-left:15px;height: 50px;">
                                <p style="height:10px;font-size: 15px;">{{ upPerson }}</p>
                                <p style="line-height:20px;color:grey;font-size: 5px;top: 5px;">{{ describe }}
                                </p>
                            </div>
                        </div>
                        <div style="margin-top:15px;text-align: center;padding: 0px 10px;">
                            <ul class="userInfo">
                                <li>
                                    <div style="text-align:center">
                                        <p class="fieldInfo">{{ artCount }}</p>
                                        <p class="fieldName">发布</p>
                                    </div>
                                </li>
                                <li>
                                    <div style="text-align:center">
                                        <p class="fieldInfo">{{ dayOrder }}</p>
                                        <p class="fieldName">日排名</p>
                                    </div>
                                </li>
                                <li>
                                    <div style="text-align:center">
                                        <p class="fieldInfo">{{ visication }}</p>
                                        <p class="fieldName" style="color:darkseagreen">访问</p>
                                    </div>
                                </li>
                            </ul>
                            <a-divider></a-divider>
                            <ul class="userInfo">
                                <li>
                                    <div style="text-align:center">
                                        <p class="fieldInfo">{{ fans }}</p>
                                        <p class="fieldName">粉丝</p>
                                    </div>
                                </li>
                                <li>
                                    <div style="text-align:center">
                                        <p class="fieldInfo">{{ likeCount }}</p>
                                        <p class="fieldName">获赞</p>
                                    </div>
                                </li>
                                <li>
                                    <div style="text-align:center">
                                        <p class="fieldInfo">{{ commentCount }}</p>
                                        <p class="fieldName">评论</p>
                                    </div>
                                </li>
                                <li>
                                    <div style="text-align:center">
                                        <p class="fieldInfo">{{ collectCount }}</p>
                                        <p class="fieldName">收藏</p>
                                    </div>
                                </li>
                            </ul>
                            <div style="text-align: center;margin-top: 50px;">
                                <a-button v-show="!fan" style="width:30%;border-radius: 15px;margin-right: 20px;" @click="addFan()">关注
                                </a-button>
                                <a-button v-show="fan" style="width:30%;border-radius: 15px;margin-right: 20px;" type="danger"  @click="delFan()">取消关注
                                </a-button>
                                <a-button style="width:30%;border-radius: 15px;">私信</a-button>
                            </div>
                        </div>
                    </div>

                    <div class="shadow" style="height:400px;margin-top: 30px;width:300px">
                        <div style="background:orange;height: 30px;"></div>
                    </div>
                    <div class="shadow" style="height:400px;margin-top: 30px;width:300px">

                    </div>
                </div>
            </div>
        </template>
    </layout>
</template>
<script>
import { MessageOutlined, LikeFilled, DislikeFilled, StarFilled, TagOutlined, ReadOutlined, UserOutlined, InfoCircleOutlined, BulbOutlined, UploadOutlined, StarOutlined, HistoryOutlined, SoundOutlined, EyeOutlined, LikeOutlined, DislikeOutlined } from '@ant-design/icons-vue';
import layout from '../layout/layout'
import { defineComponent, ref } from 'vue';
import { post, get, put, del } from '../../lib/js/axios'
import { formatDate } from '../../lib/js/tools'
import edit from '../tools/imcoder-tinymce.vue'
import $ from 'jquery'
import cookie from 'js-cookie'
import { getComments } from '../../utils/utils'
import { message } from 'ant-design-vue'
import articleComment from '../articleComment.vue'
export default defineComponent({
    data() {
        return {
            title: '',
            tinyMes: '',
            date: '',
            watched: '',
            upPerson: '',
            tags: [],
            head: '',
            describe: '',
            like: new Set(),
            collect: new Set(),
            dislikes: new Set(),

            // 评论源
            comments: [],
            commentVisible: false,
            submitting: false,
            src: '',
            data: '',
            value: '',
            init: {
                language_url: "/static/tinymce/langs/zh-Hans.js",
                language: "zh-Hans",
                skin_url: "/static/tinymce/skins/ui/oxide",
                height: 770,
                min_height: 770,
                max_height: 770,
                inline: true,
                toolbar_mode: "wrap",
                plugins: "preview searchreplace autolink directionality visualblocks visualchars fullscreen image link media template code codesample table charmap nonbreaking insertdatetime advlist lists wordcount imagetools autosave autoresize",
                toolbar: "code undo redo restoredraft | cut copy paste pastetext | forecolor backcolor bold italic underline strikethrough link codesample | alignleft aligncenter alignright alignjustify outdent indent lineheight formatpainter | \
    styleselect formatselect fontselect fontsizeselect | bullist numlist | blockquote subscript superscript removeformat | \
    table image media charmap hr pagebreak insertdatetime | bdmap fullscreen preview",
                content_style: "p {margin: 5px 0;}",
                fontsize_formats: "12px 14px 16px 18px 24px 36px 48px 56px 72px",
                font_formats:
                    "微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;",
                branding: false,
                //此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
                //如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler
                images_upload_handler: function (blobInfo, success, failure) {
                    var reader = new FileReader();
                    reader.readAsDataURL(blobInfo.blob());
                    reader.onload = function () {
                        tinymce.util.ImageUploader
                        return Promise.resolve(this.result)
                    };
                },
            },
            upaid: '',
            upuid: '',
            loginId: null,
            fan:false
        };
    },
    mounted() {
        let url = window.location.search
        let Id = cookie.get('/user/uid')
        if (Id) {
            this.loginId = parseInt(Id)
        }

        if (url != '') {
            url = url.substring(1).split("&")
            let UID = parseInt(url[0].split("=")[1].substring(7))
            let AID = parseInt(url[1].split("=")[1].substring(7))
            if (Id) {
                this.$users.getUser2Up(this.loginId,UID).then(res=>{
                    this.fan = res.fanRelation
                })
            }

            get('/article/getArticleById', {
                uid: UID,
                aid: AID
            }).then((res) => {
                let info = res.data.data
                console.log(info)
                //  0   1   2   3    4    5        6     7      8          9       10     11    12   13      14      15   16      17     18
                // uid aid art date like comment watch collect classper classsys title tinyMes id username password head mail describe backImg
                this.data = info.articles
                this.title = info.title
                this.date = formatDate(info.date)
                this.watched = info.watched
                this.tags = ((info.classsys == "" ? "" : (" " + info.classsys)) + info.classper).split(" ")
                this.upPerson = info.username
                this.tinyMes = info.tinymes
                this.head = "img/userHead/" + info.head
                this.describe = info.describe
                this.like = new Set(info.typeUidsMap['2'])
                this.dislikes = new Set(info.typeUidsMap['3'])
                this.upaid = info.aid
                this.upuid = info.id
            })

            get('/goodCollection/getAllByAid', {
                aid: AID
            }).then(res => {
                if (res.data.resultCode == 200) {
                    let collecters = res.data.data.map(e => {
                        return e.goodUid
                    })
                    this.collect = new Set(collecters)
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
        EyeOutlined,
        TagOutlined,
        LikeOutlined, DislikeOutlined, MessageOutlined,
        edit,
        layout,
        articleComment,
        LikeFilled,
        StarFilled,
        DislikeFilled
    },
    methods: {
        showComment() {
            this.$refs.comment.visible = true
            this.$refs.comment.getComment().then((ret) => {
                this.getComments(ret)
            })
        },
        getComments(oraignData) {
            let res = getComments(oraignData, this, true)
            this.comments = res
        },
        doLike(isFilled = true) {
            if (!isFilled) {
                put('/GoodPointsParam/onLikeActive', {
                    uid: this.loginId,
                    aid: this.upaid,
                    type: 2
                }).then(res => {
                    if (res.data != 0) {
                        message.success('点赞成功')
                        this.like.add(this.loginId)
                        this.dislikes.delete(this.loginId)
                    }
                })
            }
            else {
                del('/GoodPointsParam/deleteLike', {
                    uid: this.loginId,
                    aid: this.upaid,
                    type: 2
                }).then(res => {
                    if (res.data != 0) {
                        message.success('取消点赞')
                        this.like.delete(this.loginId)
                    }
                })
            }
        },
        doDislike(isFilled = true) {
            if (!isFilled) {
                put('/GoodPointsParam/onLikeActive', {
                    uid: this.loginId,
                    aid: this.upaid,
                    type: 3
                }).then(res => {
                    if (res.data != 0) {
                        message.success('点踩成功')
                        this.like.delete(this.loginId)
                        this.dislikes.add(this.loginId)
                    }
                })
            }
            else {
                del('/GoodPointsParam/deleteLike', {
                    uid: this.loginId,
                    aid: this.upaid,
                    type: 3
                }).then(res => {
                    if (res.data != 0) {
                        message.success('取消点踩')
                        this.dislikes.delete(this.loginId)
                    }
                })
            }
        },
        doCollect() {
            put('/goodCollection/onAddCollection', {
                uid: this.loginId,
                aid: this.upaid
            }).then(res => {
                console.log(res)
                if (res.data.effectRows != 0) {
                    message.success(res.data.action + "成功")
                    if (res.data.action == '添加收藏') {
                        this.collect.add(this.loginId)
                    } else {
                        this.collect.delete(this.loginId)
                    }
                }
            })
        },
        addFan(){
            if(this.loginId&&this.upaid){
                this.$users.addFan(this.loginId, this.upuid)
            }
            else if(this.loginId==null){
                message.warning("您尚未登录,请先登录")
            }else if(this.upaid==null){
                message.warning("上传用户已注销/不存在")
            }
        },
        delFan(){
            console.log(this.fan)
            this.$users.delFan(this.fan.fid).then(res=>{
                console.log(res)
            })
        }
    },
    setup() {
        return {
            selectedKeys1: ref(['2']),
            selectedKeys2: ref(['3']),
            collapsed: ref(false),
            openKeys: ref(['sub1']),
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

function findRoor(data) {

}
</script>
<style>
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
    color: rgb(52, 124, 190);
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

.userInfo {
    list-style: none;
    list-style-position: outside;
    text-align: center;
    padding: 0px;
    width: 100%;
    display: flex;
    justify-content: space-between;
    height: 30px;
}


.fieldName {
    height: 10px;
    font-size: 15px;
    font-weight: 300;
    font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif
}

.fieldInfo {
    height: 10px;
    font-size: 12px;
    font-weight: 100;
    font-display: initial;
    font-feature-settings: "abvs";
    font-family: "PingFang SC", "Hiragino Sans GB", Arial, "Microsoft YaHei", Verdana, Roboto, Noto, "Helvetica Neue", sans-serif;
}

.title {

    text-align: left;
    font-size: 30px;
    font-weight: 500;
    line-height: 20px;
    padding: 5px 15px;
}

.titleCard {
    border-radius: 3px;
    background-color: aliceblue;
    padding: 5px 15px;
    margin-bottom: 30px;
}

.titleCard p {
    margin-right: 20px;
    color: rgb(151, 148, 148);
    line-height: 10px;
}

.actBottom {
    box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
    height: 50px;
    margin-top: 30px;
    width: 100%;
    padding: 20px;
}

.actBottom p {
    font-size: 20px;
    line-height: 0px;

}
</style>