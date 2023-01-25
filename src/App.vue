<template>
  
  <a-config-provider :locale="locale">
    <div id="app">
      <router-view></router-view>
    </div>
  </a-config-provider>
</template>

<script>
import locale from 'antd/es/locale/zh_CN';
export default {
  name: 'app',
  methods:{

  },
  data(){
    return {
      locale:locale
    }
  }

}
</script>