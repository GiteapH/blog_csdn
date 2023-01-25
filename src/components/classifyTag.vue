
<template>
    <div style="display: flex;height: 100%;margin-bottom: 3px;">
        <a-button size="large" @hover="visible = !visible" @click="onClick" :type="type" style=" font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;font-weight:400;">{{ value }}
        </a-button>
        <div id="count">
            {{ count }}&nbsp&nbsp条
        </div>
    </div>
</template>
<script>
import { defineComponent, ref } from 'vue';
import $ from 'jquery'
export default defineComponent({
    props: ['value', 'count','type'],
    setup() {
        return {
            visible: ref(false),
        };
    },
    methods: {
        //点击分类
        onClick() {
            console.log('点击分类')
        }
    }
});
$('#count').attr('disabled', "true");
</script>

<style scoped>
#count {
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
    font-weight: 300;
    position: relative;
    text-align: right;
    width: 60px;
    height: 40px;
    background-color: white;
    line-height: 40px;
    border: #dcdcdc 1px solid;
    pointer-events:none
}

#count:before {
    width: 0px;
    height: 0px;
    position: absolute;
    top: 12px;
    left: -16px;
    padding: 0;
    border: 9px solid transparent;
    border-color: transparent #fff transparent transparent;
    content: '';
    z-index: 12;
    pointer-events:none
}

#count:after {
    width: 0px;
    height: 0px;
    position: absolute;
    top: 12px;
    left: -18px;
    padding: 0;
    border: 9px solid transparent;
    border-color: transparent rgb(196, 196, 196) transparent transparent;
    content: '';
    pointer-events:none
}
</style>