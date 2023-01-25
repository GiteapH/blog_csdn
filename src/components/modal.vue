<template>
    <div>
        <a-modal v-model:visible="visible" :title="title" :confirm-loading="confirmLoading" @ok="handleOk">
            <p>{{ modalText }}</p>
        </a-modal>
    </div>
</template>
<script>
import { ref, defineComponent } from 'vue';
export default defineComponent({
    props: {
        handleOk: {
            type: Function,
            default: () => { },
        },
        title: {                                         
            type: String,
            default: ''
        },
        confirmLoading: {
            type: Boolean,
            default: false
        },
        modalText: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            visible: false,
        };
    },
});
</script>