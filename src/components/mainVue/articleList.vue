<template>
  <layout :itemMenu="['1']" >
    <template v-slot:content>
      <workList></workList>
    </template>
  </layout>
</template>
<script>
import layout from '../layout/layout'
import workList from '../article.vue'
import {defineComponent} from 'vue';
export default defineComponent({
  components: {
    workList,
    layout
  },
});
</script>