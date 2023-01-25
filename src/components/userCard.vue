<template>
    <div style="position:relative;z-index:-1;" >
        <a-card hoverable style="width: 300px">
            <template #cover>
                <img alt="美美的背景" :src='BackImgsrc' />
            </template>
            <template #actions>
                <a>
                    <home-outlined key="setting" />
                </a>
                <a-badge :count="5" title="Custom hover text" dot='true'>
                    <a>
                        <message-outlined key="message" />
                    </a>
                </a-badge>
                <poweroff-outlined key="ellipsis" @click="outLogin" />
            </template>
            <a-card-meta :title='userName' :description='describe'>
                <template #avatar>
                    <a-avatar alt="我的帅/美照" :src='src' />
                </template>
            </a-card-meta>
        </a-card>
    </div>
</template>
<script>
import { HomeOutlined, MessageOutlined, PoweroffOutlined, ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { defineComponent, createVNode } from 'vue';
import { Modal } from 'ant-design-vue';
import cookie from 'js-cookie'
export default defineComponent({
    props: ['src', 'userName', 'describe', 'BackImgsrc'],
    components: {
        HomeOutlined,
        MessageOutlined,
        PoweroffOutlined,
    },
    methods: {
        // 退出登录
        outLogin() {
            Modal.confirm({
                title: '提示',
                icon: createVNode(ExclamationCircleOutlined),
                content: '确定退出登录?',
                onOk() {
                    cookie.remove('/user/loginPass')
                    cookie.remove('/user/uid')
                    cookie.remove('/user/token')
                    window.location.reload()
                },
                // eslint-disable-next-line @typescript-eslint/no-empty-function
                onCancel() { },
            });
        }
    }
});
</script>