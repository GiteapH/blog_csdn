<template>
    <div>
        <a-drawer v-model:visible="visible" class="custom-class" size="large" style="" title="评论区" placement="right"
            @after-visible-change="afterVisibleChange">
            <comment :comments="comments" ref="comment" @returnRoot="returnRoot" :uid="uid" :aid="aid"></comment>
            <a-divider></a-divider>
            <div class="shadow" style="position: relative;padding:25px;height:200px">
                <a-textarea v-model:value="value" :rows="4" />
                <a-button :loading="submitting" type="primary" @click="handleSubmit" style="margin-top:20px">
                    发送评论
                </a-button>
            </div>
            <!-- asdadiasufvnuaivubduadhuhdhhdhello world what are you doing o shit w i love you so much and i want to look here where are you i come from newyork oh metooo -->
            <a-drawer v-model:visible="ShowDetail" title="评论详情" width="320" :closable="false">
                <comment :comments="detailcomments" ref="detailcomment" @returnRoot="returnRoot" :uid="uid" :aid="aid"></comment>
            </a-drawer>
        </a-drawer>
    </div>
</template>
<script>
import dayjs from 'dayjs';
import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined } from '@ant-design/icons-vue';
import { defineComponent, ref, toRefs, onMounted, getCurrentInstance, reactive } from 'vue';
import { message } from 'ant-design-vue'
import comment from './comments.vue'
import cookie from 'js-cookie'
import relativeTime from 'dayjs/plugin/relativeTime';
import { post, get } from '../lib/js/axios'
import { formatDate } from '../lib/js/tools'
import {parseComments} from '../utils/utils'
dayjs.extend(relativeTime);

export default defineComponent({
    props: {
        comments: {
            type: Object,
            default: () => ({})
        },
        uid: {
            type: String,
            default: ''
        },
        aid: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            ShowDetail: false,
            detailcomments:[]
        };
    },
    components: {
        comment,
        LikeFilled,
        LikeOutlined,
        DislikeFilled,
        DislikeOutlined,
    },
    setup(props) {
        const { uid, aid } = toRefs(props);
        const { proxy, data } = getCurrentInstance();
        const visible = ref(false);
        const userInfo = ref()
        const submitting = ref(false);
        const value = ref('')
        const loginId = ref(0)
        const afterVisibleChange = bool => {
            console.log('visible', bool);
        };

        const showDrawer = () => {
            visible.value = true;
        };

        const getComment = () => {
            return new Promise((resolve) => {
                get(`/comment/getComments?uid=${uid.value}&aid=${aid.value}`).then(res => {
                    let data = parseComments(res.data.data)
                    resolve(data)
                }).catch(err => {
                    resolve(err)
                })
            })
        }

        const setVal = (val) => {
            console.log(val)
        }
        const handleSubmit = () => {
            if (loginId.value == '') {
                message.warning("请先登录")
                return
            }
            if (!value.value) {
                return;
            }
            submitting.value = true;
            post('/comment/insertComment', {
                uid: uid.value,
                aid: aid.value,
                content: value.value,
                fromUid: loginId.value,
                toUid: null,
                date: formatDate(new Date()),
                precid: null
            }).then(res => {
                if (res.resultCode == 200) {
                    // let data = userInfo.value
                    submitting.value = false
                    message.success("发表成功")
                    proxy.$emit('showComment')
                    value.value = ''
                } else {
                    message.error('发送异常，请检查网络')
                    submitting.value = false
                }
            }).catch(err => {
                message.error(err)
                submitting.value = false
            })
        };
        onMounted(() => {
            let Id = cookie.get('/user/uid')
            if (Id) {
                loginId.value = Id
            }
        })
        return {
            value,
            visible,
            afterVisibleChange,
            showDrawer,
            submitting,
            handleSubmit,
            getComment,
            setVal,
            userInfo
        };
    }
});
</script>