<template>
    <a-cascader v-model:value="value" :options="options" placeholder="已定义类型" :show-search="{ filter }" @focus="{onBlur}"/>
</template>
<script>
import { defineComponent, ref } from 'vue';
const options = [{
    value: '生活',
    label: '生活',
    children: [{
        value: '美食',
        label: '美食',
        children: [{
            value: '粤菜',
            label: '粤菜',
        }, {
            value: '苏菜',
            label: '苏菜',
        }, {
            value: '鲁菜',
            label: '鲁菜',
        }, {
            value: '川菜',
            label: '川菜',
        }, {
            value: '闽菜',
            label: '闽菜',
        }, {
            value: '浙菜',
            label: '浙菜',
        }, {
            value: '湘菜',
            label: '湘菜',
        }, {
            value: '徽菜',
            label: '徽菜',
        }],
    }, {
        value: '工作',
        label: '工作',
    }, {
        value: '学习',
        label: '学习',
        children: [{
            value: '数学',
            label: '数学',
        }, {
            value: '高考/中考',
            label: '高考/中考',
        }, {
            value: '编程',
            label: '编程',
        }, {
            value: '英语',
            label: '英语',
        }, {
            value: '文学',
            label: '文学',
        }, {
            value: '阅读',
            label: '阅读',
        }, {
            value: '棋',
            label: '棋',
        }, {
            value: '学习方法',
            label: '学习方法',
        }]
    }, {
        value: '健康',
        label: '健康',
    }, {
        value: '旅游',
        label: '旅游',
    }],
}, {
    value: '娱乐',
    label: '娱乐',
    children: [{
        value: '游戏',
        label: '游戏',
        children: [{
            value: '英雄联盟',
            label: '英雄联盟',
        }, {
            value: '王者荣耀',
            label: '王者荣耀',
        }, {
            value: '刀塔',
            label: '刀塔',
        }, {
            value: '炉石传说',
            label: '炉石传说',
        }, {
            value: 'apex',
            label: 'apex',
        }, {
            value: '其他',
            label: '其他',
        }]
    }, {
        value: '运动',
        label: '运动',
        children: [{
            value: '跑步',
            label: '跑步',
        }, {
            value: '徒步旅行',
            label: '徒步旅行',
        }, {
            value: '射箭',
            label: '射箭',
        }]
    }],
}];
export default defineComponent({
    setup() {
        let input = ''
        const onBlur = (value)=>{
            console.log(input)
        }
        const filter = (inputValue, path) => {
            input = inputValue
            console.log(input)
            return path.some(option => option.label.toLowerCase().indexOf(inputValue.toLowerCase()) > -1);
        };
        return {
            value: ref([]),
            options,
            filter
        };
    },

});
</script>