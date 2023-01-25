<template>
    <template v-for="tag in alltags" :key="tag">
        <a-tooltip v-if="tag.length > 20" :title="tag">
            <a-tag :closable="true" @close="handleClose(tag)">
                {{ `${tag.slice(0, 20)}...` }}
            </a-tag>
        </a-tooltip>
        <a-tag v-else :closable="true" @close="handleClose(tag)">
            {{ tag }}
        </a-tag>
    </template>
    <cascader v-if="casVisible" ref="inputRef" v-model:value="casValue" type="text" size="small"
        :style="{ width: '78px' }" @blur="handleCasConfirm" @keyup.enter="handleCasConfirm" ></cascader>
    <a-input v-if="inputVisible" ref="inputRef" v-model:value="inputValue" type="text" size="small"
        :style="{ width: '78px' }" @blur="handleInputConfirm" @keyup.enter="handleInputConfirm" placeholder="自定义类型" />
    <a-tag v-else style="background: #fff; border-style: dashed" @click="showInput">
        <plus-outlined />
        还有?
    </a-tag>
</template>
<script>
import { defineComponent, ref, reactive, toRefs, nextTick } from 'vue';
import { PlusOutlined } from '@ant-design/icons-vue';
import cascader from './cascader.vue'
export default defineComponent({
    components: {
        PlusOutlined,
        cascader
    },
    setup() {
        const inputRef = ref();
        const state = reactive({
            alltags:[],
            tags: [],
            tagsPer:[],
            inputVisible: false,
            inputValue: '',
            casVisible:false,
            casValue: '',
        });

        const handleClose = removedTag => {
            const tags = state.tags.filter(tag => tag !== removedTag);
            const tagsPer = state.tagsPer.filter(tag => tag !== removedTag);
            const alltags = state.alltags.filter(tag => tag !== removedTag);
            state.tags = tags;
            state.tagsPer = tagsPer;
            state.alltags = alltags;
        };

        const showInput = () => {
            state.inputVisible = true;
            state.casVisible = true;
        };
        const handleInputConfirm = () => {
            const inputValue = state.inputValue;
            let tagsPer = state.tagsPer;
            if (inputValue && tagsPer.indexOf(inputValue) === -1) {
                tagsPer = [...tagsPer, inputValue];
            }
            if (inputValue && state.alltags.indexOf(inputValue) === -1) {
                state.alltags = [...state.alltags, inputValue];
            }
            console.log( state.alltags)
            Object.assign(state, {
                tagsPer,
                inputVisible: false,
                casVisible: false,
                inputValue: '',
            });
        };
         const handleCasConfirm = () => {
            const inputValue = state.casValue;
             console.log(inputValue)
            let tags = state.tags;

            if (inputValue && tags.indexOf(inputValue) === -1) {
                tags = [...tags, inputValue];
            }
            if (inputValue && state.alltags.indexOf(inputValue[inputValue.length-1]) === -1) {
                state.alltags = [...state.alltags, inputValue[inputValue.length-1]];
            }
            console.log(inputValue[inputValue.length-1])
            console.log(tags,'sys');
            Object.assign(state, {
                tags,
                casVisible: false,
                inputVisible: false,
                casValue: '',
            });
        };
        return {
            ...toRefs(state),
            handleClose,
            showInput,
            handleInputConfirm,
            inputRef,
            handleCasConfirm,
        };
    },

});
</script>