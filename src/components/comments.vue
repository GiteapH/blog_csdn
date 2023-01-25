<template>
  <div>
    <a-comment v-for="(item, index) in comments" :key="index">
      <template #actions>
        <span key="comment-basic-like">
          <a-tooltip title="Like">
            <template v-if="item.points.has(parseInt(loginId))">
              <LikeFilled @click="like(item)" />
            </template>
            <template v-else>
              <LikeOutlined @click="like(item, false)" />
            </template>
          </a-tooltip>
          <span style="padding-left: 8px; cursor: auto">
            {{ item.points.size == 0 ? '' : item.points.size }}
          </span>
        </span>
        <span key="comment-basic-dislike">
          <a-tooltip title="Dislike">
            <template v-if="item.dislikes.has(parseInt(loginId))">
              <DislikeFilled @click="dislike(item)" />
            </template>
            <template v-else>
              <DislikeOutlined @click="dislike(item, false)" />
            </template>
          </a-tooltip>
          <span style="padding-left: 8px; cursor: auto">
            {{ item.dislikes.size == 0 ? '' : item.dislikes.size }}
          </span>
        </span>
        <span key="comment-basic-reply-to" @click="replay(item)">回复</span>
        <span key="comment-basic-reply-to" @click="showComments(item)" v-show="item.toUid != null"><a>查看评论详情</a></span>
      </template>
      <div class="shadow" v-show='item.visible'
        :style="{ 'position': 'relative', 'padding': '25px', 'height': '200px' }">
        <a-textarea class="replay" v-model:value="value" :rows="4"/>
        <a-button :loading="submitting" type="primary" @click="Submit(item)" style="margin-top:20px">
          发送评论
        </a-button>
        <a-button @click="close(item)" style="margin-top:20px">
          关闭回复
        </a-button>
      </div>
      <template #author><a>{{ item.author }}</a></template>
      <template #avatar>
        <a-avatar :src="item.head" />
      </template>
      <template #content>
        <p>{{ item.comment }}</p>
      </template>
      <template #datetime>
        <a-tooltip :title="dayjs().format('YYYY-MM-DD HH:mm:ss')">
          <span>{{ item.date }}</span>
        </a-tooltip>
      </template>
      <comment :comments="item.children" ref="preReplay" :uid="uid" :aid="aid"></comment>
    </a-comment>
  </div>
</template>
<script>
import dayjs from 'dayjs';
import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined } from '@ant-design/icons-vue';
import { defineComponent, ref, onMounted, toRefs, getCurrentInstance } from 'vue';
import relativeTime from 'dayjs/plugin/relativeTime';
import { message } from 'ant-design-vue'
import cookie from 'js-cookie'
import { post, get, put, del } from '../lib/js/axios'
import $ from 'jquery'
import { formatDate } from '../lib/js/tools'
dayjs.extend(relativeTime);
export default defineComponent({
  data() {
    return {
      ShowDetail: true
    };
  },
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
  name: 'comment',
  components: {
    LikeFilled,
    LikeOutlined,
    DislikeFilled,
    DislikeOutlined,
  },
  directives: {
    focus: {
      // 指令的定义
      mounted(el, binding) {
        if (binding.value)
          el.focus()
        else
          el.blur()
      }
    }
  },
  setup(props) {
    const self = getCurrentInstance()
    const { uid, aid } = toRefs(props);
    const likes = ref(0);
    const dislikes = ref(0);
    const targetItem = ref({})
    const loginId = ref(0)
    const value = ref('')
    const Submit = (item) => {
      if (loginId.value) {
        console.log(uid.value)
        item.replay(loginId.value, value.value.substring(value.value.indexOf(":")))
        new Promise((resolve) => {
          get(`/comment/getComments?uid=${uid.value}&aid=${aid.value}`).then(res => {
            console.log(res)
            if (res.data.data.length != 0)
              resolve(res.data.data)
            else
              resolve([])
            //   [[from],[to]]
          }).catch(err => {
            console.log(err)
            resolve(err)
          })
        }).then((comments) => {
          console.log(comments)
          let rootComment = new Map()
          let users = new Map()

          for (let comment of comments) {
            users.set(comment.id, [comment.username, comment.head, comment.email, comment.describe, comment.cardimg])
          }

          for (let data of comments) {
            // 为根评论
            let comment = {
              head: 'img/userHead/' + data.head,
              author: data.username,
              likes: data.like == null ? 0 : Json.parse(data.like).length,
              dislikes: '',
              comment: data.toUid == null ? data.content : ('@' + users.get(data.id)[0] + ": " + data.content),
              date: formatDate(data.date),
              replay: (fromId, content) => {
                post('/comment/insertComment', {
                  uid: self.props.uid,
                  aid: self.props.aid,
                  content: content,
                  fromUid: fromId,
                  toUid: data.toUid,
                  date: formatDate(new Date()),
                  precid: data.precid
                }).then(res => {
                  if (res == 200) {
                    this.submitting = false
                    message.success("发表成功")
                  } else {
                    message.error('发送异常，请检查网络')
                    this.submitting = false
                  }
                }).catch(err => {
                  message.error(err)
                  this.submitting = false
                })
              },
              isReplay: false,
              children: [],
              visiblity: false
            }
            if (!data.toUid) {
              //               cid
              rootComment.set(data.cid, comment)
            } else {
              rootComment.get(data.precid).children.push(comment)
            }

          }
          let res = []
          for (let values of rootComment.values()) {
            res.push(values)
          }

          props.comments = res
          this.$forceUpdate();
        })
        value.value = ''
      }
      else
        message.info("请先登录")
    }
    onMounted(() => {
      console.log(self)
      let Id = cookie.get('/user/uid')
      if (Id) {
        loginId.value = parseInt(Id)
      }
    })
    const like = (item, flag = true) => {
      if (loginId.value == 0) {
        message.warning('请先登录')
        return
      }
      if (!flag) {
        put('/GoodPointsParam/onLikeActive', {
          uid: loginId.value,
          cid: item.cid,
          type: 0
        }).then(res => {
          if(res.data !=0){
            message.success('点赞成功')
            item.dislikes.delete(loginId.value)
            item.points.add(loginId.value)
          }
        })
      } else {
        del('/GoodPointsParam/deleteLike',{
          uid: loginId.value,
          cid: item.cid,
          type: 0
        }).then(res=>{
          if(res.data !=0){
            message.success('取消点赞')
            item.points.delete(loginId.value)
          }
        })
      }
      self.proxy.$forceUpdate()
    };
    const dislike = (item, flag = true) => {
      if (loginId.value == 0) {
        message.warning('请先登录')
        return
      }
      if (!flag) {
        put('/GoodPointsParam/onLikeActive', {
          uid: loginId.value,
          cid: item.cid,
          type: 1
        }).then(res => {
          if(res.data != 0){
            message.success('点踩成功')
            item.points.delete(loginId.value)
            item.dislikes.add(loginId.value)
          }
        })
      } else {
        del('/GoodPointsParam/deleteLike',{
          uid: loginId.value,
          cid: item.cid,
          type: 1
        }).then(res=>{
          if(res.data != 0){
            message.success('取消点踩')
            item.dislikes.delete(loginId.value)
          }
        })
      }
      self.proxy.$forceUpdate()
    };
    const replay = (item) => {
      let id = cookie.get('/user/uid')
      console.log(id)
      if (!id) {
        message.error("请先登录")
      } else {
        item.visible = true
        targetItem.value = item
      }
      console.log(item)
    }

    const close = (item) => {
      item.visible = false
    }
    return {
      likes,
      dislikes,
      close,
      like,
      dislike,
      dayjs,
      replay,
      targetItem,
      value,
      Submit,
      loginId,
    };
  },
  methods: {
    showComments(item) {
      item.showDetailComments()
    }
  }
});
</script>